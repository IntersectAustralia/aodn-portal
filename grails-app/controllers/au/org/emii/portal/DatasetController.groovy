
/*
 * Copyright 2012 IMOS
 *
 * The AODN/IMOS Portal is distributed under the terms of the GNU General Public License
 *
 */

package au.org.emii.portal

//import au.org.emii.portal.config.JsonMarshallingRegistrar
//import au.org.emii.portal.display.MenuJsonCache
//import au.org.emii.portal.display.MenuPresenter
import grails.converters.JSON
import grails.util.Environment

class DatasetController {

    static allowedMethods = [upload: "POST"]

    def upload = {
        def datasetTypes = ['sonde', 'nutrient', 'carbondioxide']
        def f = request.getFile('dataset-path')   // Dataset
        def m = request.getFile('metadata-path')  // Metadata
        def result = [:]
        def datasetType = datasetTypes[params.int('dataset-type')]
        def filenamePrefix = datasetType + "_" + session.id + "_" + System.nanoTime()

        if (!f.empty) {
            // Destination file should be: datasettype_sessionid.csv
            def datasetFile = filenamePrefix + ".csv"
            def metadataFile = filenamePrefix + ".ifo"
            f.transferTo(new File("/aodn-portal/data/${datasetFile}"))
            m.transferTo(new File("/aodn-portal/data/${metadataFile}"))

            def database = 'aodnportal' // Default set to production
            
            switch (Environment.getCurrent()) {
            case Environment.DEVELOPMENT:
                database = "aodn_portal"
                break
            case Environment.PRODUCTION:
                database = "aodnportal"
                break
            case Environment.CUSTOM: // qa
                database = "aodnportal-qa"
                break
            }

			def groovy = """/usr/bin/which groovy""".execute().text.trim()
            def command = """${groovy} /aodn-portal/scripts/${datasetType}.groovy /aodn-portal/data/${datasetFile} ${database}"""
            log.debug(command)
			
			def proc
			
			try {
				proc = command.execute()                 // Call *execute* on the string
				proc.waitFor()                               // Wait for the command to finish
			}
			catch(e) {
				log.debug(e.message)
			}

            if (proc.exitValue() == 0) {
                result = [
                    success: true,
                    message: "Success uploaded and processed ${f.getOriginalFilename()}"
                ]
            }
            else {
                result = [
                    success: false,
                    message: proc.err.text
                ]
            }
        }
        else {
            result = [
                success: false,
                message: "ERROR: empty file"
            ]
        }

        render text: result as JSON, contentType: "text/html"
    }

}