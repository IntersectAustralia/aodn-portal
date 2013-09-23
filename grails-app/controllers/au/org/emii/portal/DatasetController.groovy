
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

            def database = 'sosdb' // 52north.org SOS server database name
			def groovy = """/usr/bin/which groovy""".execute().text.trim()

			def csvFilePath = "/aodn-portal/data/${datasetFile}"
			def numOfRecords = getNumOfLines(csvFilePath)

			// call groovy script to insert at most 50 data records every time.
			def hasInsertRecord = false
			def proc
			for(int index = 1; index <= numOfRecords; index = index + 50) {

				int startIndex = index
				int endIndex = (numOfRecords - startIndex) >= 50 ? startIndex + 50 : numOfRecords

				def command = """${groovy} /aodn-portal/scripts/${datasetType}.groovy /aodn-portal/data/${datasetFile} ${database} ${startIndex} ${endIndex}"""
				log.debug(command)

				try {
					proc = command.execute()                 // Call *execute* on the string
					proc.waitFor()                           // Wait for the command to finish
				}
				catch(e) {
					log.debug(e.message)
				}

				if (proc.exitValue() == -1) {
					break
				} else if (proc.exitValue() == 0) {
					hasInsertRecord = true
				}

				log.debug("process exit value: " + proc.exitValue() + " and hasInsertRecord is: " + hasInsertRecord)
			}

			log.debug("process exit value: " + proc.exitValue())
            if (proc.exitValue() == -1) {
				result = [
					success: false,
					message: proc.err.text
				]
            } else {
				if(hasInsertRecord) {
					session.setAttribute('datasetFile', datasetFile)

					if (!m.empty) {
						session.setAttribute('metadataFile', metadataFile)
					}
					else {
						session.setAttribute('metadataFile', null)
					}

					session.setAttribute('datasetType', params.int('dataset-type'))

					result = [
							success: true,
							message: "Successfully uploaded and processed ${f.getOriginalFilename()}"
					]
				}  else {
					result = [
							success: false,
							message: "All records in this file are duplicated"
					]
				}

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


	private int getNumOfLines(String filePath) {
		int numOfLines = 0
		def csvfile = new File(filePath)

		csvfile.eachLine { numOfLines ++ }

		return numOfLines
	}
}