package au.org.emii.portal

import groovy.xml.MarkupBuilder
import grails.converters.JSON
import java.text.SimpleDateFormat
import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH

class MetadataController {
	def metadataService
    static allowedMethods = [downloadDataset: "GET", downloadMetadata: "GET", search: "GET", save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [metadataInstanceList: Metadata.list(params), metadataInstanceTotal: Metadata.count()]
    }

    def create = {
		def grailsApplication
        
		// A "default" metadata, which is used to get id for key and populate default value to DB
		def metadataInstance = new Metadata(
			collectionPeriodFrom: new Date(),
			collectionPeriodTo: new Date(),
			dataAccess: 'Public',
			dataType: 'Sonde survey',
			datasetName: 'sampleData',
			description: '',
			embargo: false,
			key: '123456',
			licence: 0,
			researchCodes: [
				'0405 Oceanography',
				'0502 Environmental Science and Management',
				'0704 Fisheries Sciences',
				'0803 Computer Software',
				'0804 Data Format'
				],
			serviceKey: 'www.sydney.edu.au/sho/svc/1',
			studentOwned: false,
			datasetPath: session.getAttribute('datasetFile'),
			metadataPath: session.getAttribute('metadataFile')
        )
		
		if (metadataInstance.save(flush: true)) {
			
		}
		else {
			log.debug(metadataInstance.errors)
		}
		
        metadataInstance.properties = params
		metadataInstance.key = "www.sydney.edu.au/sho/col/${metadataInstance.id}"
		metadataInstance.collectionPeriodFrom = getPeriodFrom()
		metadataInstance.collectionPeriodTo = getPeriodTo()
		metadataInstance.dataType = Metadata.dataTypeList()[session.getAttribute('datasetType')].name
		metadataInstance.datasetName = "${metadataInstance.dataType} ${formatDate(format: 'yyyy-MM-dd', date: metadataInstance.collectionPeriodFrom)} to ${formatDate(format: 'yyyy-MM-dd', date: metadataInstance.collectionPeriodTo)} ${metadataInstance.id}"
		metadataInstance.points = getPoints()
        return [metadataInstance: metadataInstance, cfg: Config.activeInstance()]
    }

    def save = {
        def metadataInstance = Metadata.get(params.id)
		
		if (params.grantedUsers == 'Enter name of user here') {
			params.remove('grantedUsers')
		}
		
		if (params.collectors == 'Enter name of the collector here') {
			params.remove('collectors')
		}
		
		if (params.publications == 'Enter publication here') {
			params.remove('publications')
		}
		
		metadataInstance.properties = params
		
        if (metadataInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'metadata.label', default: 'Metadata'), metadataInstance.id])}"
            redirect(controller: "home")
        }
        else {
			log.debug(metadataInstance.errors)
            render(view: "create", model: [metadataInstance: metadataInstance, cfg: Config.activeInstance()])
        }
    }

	def show = {
        def metadataInstance = Metadata.get(params.id)
        if (!metadataInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'metadata.label', default: 'Metadata'), params.id])}"
            redirect(action: "list")
        }
        else {
            [metadataInstance: metadataInstance]
        }
    }

    def edit = {
        def metadataInstance = Metadata.get(params.id)
        if (!metadataInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'metadata.label', default: 'Metadata'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [metadataInstance: metadataInstance]
        }
    }

    def update = {
        def metadataInstance = Metadata.get(params.id)
        if (metadataInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (metadataInstance.version > version) {
                    
                    metadataInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'metadata.label', default: 'Metadata')] as Object[], "Another user has updated this Metadata while you were editing")
                    render(view: "edit", model: [metadataInstance: metadataInstance])
                    return
                }
            }
            metadataInstance.properties = params
            if (!metadataInstance.hasErrors() && metadataInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'metadata.label', default: 'Metadata'), metadataInstance.id])}"
                redirect(action: "show", id: metadataInstance.id)
            }
            else {
                render(view: "edit", model: [metadataInstance: metadataInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'metadata.label', default: 'Metadata'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def metadataInstance = Metadata.get(params.id)
        if (metadataInstance) {
            try {
                metadataInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'metadata.label', default: 'Metadata'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'metadata.label', default: 'Metadata'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'metadata.label', default: 'Metadata'), params.id])}"
            redirect(action: "list")
        }
    }

	private Date getPeriodFrom() {
		def csvfile = getCsvFile()
		def sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss")
		def periodFrom = null
		def tempTimestamp

		csvfile.eachLine { line, index ->

			if (index > 2) { // Ignore header line and units line, start getting values
				String[] values = line.split(',')
				tempTimestamp = sdf.parse("${values[0]} ${values[1]}")

				if (periodFrom) {
					if (tempTimestamp < periodFrom) {
						periodFrom = tempTimestamp
					}
				}
				else {
					periodFrom = tempTimestamp
				}
			}
		}

		return periodFrom
	}

	private Date getPeriodTo() {
		def csvfile = getCsvFile()
		def sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss")
		def periodTo = null
		def tempTimestamp

		csvfile.eachLine { line, index ->

			if (index > 2) { // Ignore header line and units line, start getting values
				String[] values = line.split(',')
				tempTimestamp = sdf.parse("${values[0]} ${values[1]}")

				if (periodTo) {
					if (tempTimestamp > periodTo) {
						periodTo = tempTimestamp
					}
				}
				else {
					periodTo = tempTimestamp
				}
			}
		}

		return periodTo
	}

	private File getCsvFile() {
		def datasetPath = "/aodn-portal/data/"
		def datasetFile = session.getAttribute('datasetFile')
		return new File("${datasetPath}${datasetFile}")
	}

	def search = {
		def result = [:]
		def records = []
		def datasetUrl = ""
		def metadataUrl = ""
		def links = []
		def sdf = new SimpleDateFormat("yyyy-MM-dd")
		def c = Metadata.createCriteria()
		def metadataInstanceList = c.list {
			if (params.extFrom) ge("collectionPeriodFrom", sdf.parse(params.extFrom))
			if (params.extTo) le("collectionPeriodTo", sdf.parse(params.extTo) + 1)
			if (params.eastBL && params.northBL && params.southBL && params.westBL) {
				points {
					between("latitude", params.double('southBL'), params.double('northBL'))
					between("longitude", params.double('westBL'), params.double('eastBL'))
				}
			}
		}

		metadataInstanceList.each { metadata ->
			links = []

			datasetUrl = createLink(action: "downloadDataset", params: [dataset: metadata.datasetPath, filename: "${metadata.datasetName}.csv"])
			links << generateLink(datasetUrl, "${metadata.datasetName} - Dataset(CSV)")

			log.debug(metadata.metadataPath)

			if (metadata.metadataPath != null) {
				metadataUrl = createLink(action: "downloadMetadata", params: [metadata: metadata.metadataPath, filename: "${metadata.datasetName}.txt"])
				links << generateLink(metadataUrl, "${metadata.datasetName} - Metadata(TXT)")
			}

			records << [
				'title': metadata.datasetName,
				'abstract': metadata.dataType,
                'uuid': '',
                'links': links,
                'source': '',
                'canDownload': '',
                'bbox': ''
			]
		}

		result = [
				success: true,
				records: records
		]
        render text: result as JSON, contentType: "text/html"
    }

    def downloadDataset = {
    	def datasetFile = new File("/aodn-portal/data/${params.dataset}")
    	response.setContentType("application/octet-stream")
        response.setHeader("Content-disposition", "attachment; filename=\"${params.filename}\"")
        response.outputStream << datasetFile.newInputStream()
        return
    }

    def downloadMetadata = {
    	def metadataFile = new File("/aodn-portal/data/${params.metadata}")
    	response.setContentType("application/octet-stream")
        response.setHeader("Content-disposition", "attachment; filename=\"${params.filename}\"")
        response.outputStream << metadataFile.newInputStream()
        return
    }

    private generateLink(url, title) {
		return [href: "${url}", name: "${title}", protocol: "WWW:LINK-1.0-http--downloaddata", title: "${title}", type: "text/html"]
	}

	private getPoints() {
		def csvfile = getCsvFile()
		def pointList = []

		csvfile.eachLine { line, index ->

			if (index > 2) { // Ignore header line and units line, start getting values
				String[] values = line.split(',')
				pointList << new Point(latitude: values[2], longitude: values[3])
			}
		}

		return pointList
	}

}
