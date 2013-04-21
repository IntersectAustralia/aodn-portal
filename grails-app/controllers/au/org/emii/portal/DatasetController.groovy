
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

class DatasetController {

    static allowedMethods = [upload: "POST"]

    def upload = {
        def datasetTypes = ['sonde', 'nutrient', 'carbondioxide']
        def f = request.getFile('dataset-path')
        def result = [:]

        if (!f.empty) {
            // Destination file should be: datasettype_sessionid.csv
            def datasetType = datasetTypes[params.int('dataset-type')]
            def filename =  datasetType + "_" + session.id + ".csv"
            f.transferTo(new File("/aodn-portal/data/${filename}"))

            def command = """/home/devel/.gvm/groovy/current/bin/groovy /aodn-portal/scripts/${datasetType}.groovy /aodn-portal/data/${filename} aodnportal"""
            log.info(command)
            def proc = command.execute()                 // Call *execute* on the string
            proc.waitFor()                               // Wait for the command to finish

            if (proc.exitValue() == 0) {
                result = [
                    success: true,
                    file: filename
                ]
            }
            else {
                result = [
                    success: false,
                    message: "Validation failed"
                ]
            }
        }
        else {
            result = [
                success: false,
                message: "Empty file"
            ]
        }

        render text: result as JSON, contentType: "text/html"
    }

}