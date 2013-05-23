package au.org.emii.portal

import groovy.xml.MarkupBuilder
import grails.converters.JSON
import java.text.SimpleDateFormat
import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH

class MetadataController {

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

			// create RIF-CS 1.4 compliant Party records
			if ("Mediated".equals(metadataInstance.dataAccess) || "Public".equals(metadataInstance.dataAccess)) {
				createCompliantPartyRecords(metadataInstance)
			}

			// create collection description record
			createColletionDescriptionRecord(metadataInstance)

            redirect(controller: "home")
        }
        else {
			log.debug(metadataInstance.errors)
            render(view: "create", model: [metadataInstance: metadataInstance, cfg: Config.activeInstance()])
        }
    }

	private String getCollectionPath() {
		return "/aodn-portal/data/colRecords/"
	}

	private String getCollectionFileName(Metadata metadata) {
		return getCollectionPath() + "www.sydney.edu.au%2Fsho%2Fcol%2F" + metadata.id + ".xml"
	}

	private void createColletionDescriptionRecord(Metadata metadata) {
		File dir = new File(getCollectionPath())
		if(!dir || !dir.exists()) {
			dir.mkdir()
		}

		String fileName = getCollectionFileName(metadata)
		File file = new File(fileName)
		if (file && file.exists()) {
			file.delete()
		}

		createColRecordXml(metadata, fileName)
	}

	private void createColRecordXml(Metadata metadata, String fileName) {
		String id = "www.sydney.edu.au/sho/col/" + metadata.id

		def writer = new StringWriter()
		def xml = new MarkupBuilder(writer)

		W3CDateFormat dateFormat = new W3CDateFormat(W3CDateFormat.Pattern.MILLISECOND)

		xml.getMkp().xmlDeclaration(version:"1.0")
		xml.registryObjects("xmlns":"http://ands.org.au/standards/rif-cs/registryObjects",
				"xmlns:xsi":"http://www.w3.org/2001/XMLSchema-instance",
				"xsi:schemaLocation":"http://ands.org.au/standards/rif-cs/registryObjects http://services.ands.org.au/documentation/rifcs/schema/registryObjects.xsd") {
			registryObject(group:"The University of Sydney") {
				key(id)
				originatingSource("www.sydney.edu.au/sho")

				def dateAccessioned = dateFormat.format(metadata.dateCreated)
				def dateModified = dateFormat.format(metadata.lastUpdated)
				collection(type:"dataset", dateAccessioned:dateAccessioned, dateModified:dateModified) {

					name(type:"primary") {
						namePart(type:"title","Sydney Harbor Environmental Data Facility " + metadata.dataType + " Data " + metadata.id)
					}

					identifier(type:"local", metadata.id)

					description(type:"full", metadata.description)

					location() {
						address() {
							electronic(type:"url") {
								value("www.sydney.edu.au/sho")
							}
						}
					}

					coverage() {
						temporal() {

							def dateFrom = dateFormat.format(metadata.collectionPeriodFrom)
							def dateTo = dateFormat.format(metadata.collectionPeriodTo)
							date(type:"dateFrom", dateFormat:"W3CDTF", dateFrom)
							date(type:"dateTo", dateFormat:"W3CDTF", dateTo)
						}

						def ploygonValues = getLatitudeLongitudeValues()
						spatial(type:"kmlPolyCoords",ploygonValues)
					}

					rights() {

						Map<String, String> rightsDescriptionMap = new HashMap<String, String>()
						rightsDescriptionMap.put("Public", "This work is Open Access. Download from http://www.sydney.edu.au/sho.")
						rightsDescriptionMap.put("Mediated", "Contact the manager of this dataset to discuss the terms and conditions of access.")
						rightsDescriptionMap.put("Private", "Access to this dataset is restricted. Contact the manager of this dataset to discuss the terms and conditions of access.")

						accessRights(rightsDescriptionMap.get(metadata.dataAccess))
						licence(type:Metadata.licenceList().get(metadata.licence.intValue()).type, rightsUri:Metadata.licenceList().get(metadata.licence.intValue()).url, Metadata.licenceList().get(metadata.licence.intValue()).text)
					}

					// Hard Code for USYD
					relatedObject() {
						key("http://nla.gov.au/nla.collector-593941")
						relation(type:"isManagerBy")
					}

					// Data Custodian
					Collection<User> dataCustodians = User.findAllByRole(UserRole.findByName(UserRole.DATACUSTODIAN))
					for (User dataCustodian : dataCustodians) {
						relatedObject() {
							key("www.sydney.edu.au/sho/pty/" + dataCustodian.id)
							relation(type:"isManagedBy")
						}
					}

					// Collector
					Set<User> collectors = metadata.collectors
					for (User collector : collectors) {
						relatedObject() {
							key("www.sydney.edu.au/sho/pty/" + collector.id)
							relation(type:"hasCollector")
						}
					}

					// principalInvestigator
					if (metadata.principalInvestigator) {
						relatedObject() {
							key("www.sydney.edu.au/sho/pty/" + metadata.principalInvestigatorId)
							relation(type:"isPrinicipalInvestigatorOf")
						}
					}

					// Student Owner OR Manager of USYD
					if (metadata.studentOwned && metadata.studentDataOwner) {
						relatedObject() {
							key("www.sydney.edu.au/sho/pty/" + metadata.studentDataOwner.id)
							relation(type:"isOwnerBy")
						}
					}

					// Publications
					def publications = metadata.publications
					for (Publication publication : publications) {
						relatedInfo(type:"publication") {
							//TODO: ugly hack: the next line code should be removed when Publication class be changed to provide the right identifierType.
							String identifierType = publication.identifierType.split(":")[0]
							identifier(type:identifierType,publication.identifier)
							title(publication.title)
							notes(publication.notes)
						}
					}

					//For all ANZSRC FoR Codes linked to he dataset
					Set<String> codes = metadata.researchCodes
					for (String code : codes) {
						//TODO: ugly hack: the next line code should be removed when Metadata class be changed to provide the right research codes.
						code = code.split(" ")[0]
						subject(type:"anzsrc-for", code)
					}
				}
			}
		}

		File file = new File(fileName)
		file.createNewFile()
		file.setWritable(true, false)
		file.write(writer.toString())
	}

	private String getCompliantPartyPath() {
		return "/aodn-portal/data/ptyRecords/"
	}

	private String getCompliantPartyFileName(User user) {
		return getCompliantPartyPath() + "www.sydney.edu.au%2Fsho%2Fpty%2F" + user.id + ".xml"
	}

	private void createCompliantPartyRecords(Metadata metadata) {
		def relatedParties = new HashSet<User>()

		if (metadata.collectors) {
			relatedParties.addAll(metadata.collectors)
		}

		if (metadata.grantedUsers) {
			relatedParties.addAll(metadata.grantedUsers)
		}

		if (metadata.principalInvestigator) {
			relatedParties.addAll(metadata.principalInvestigator)
		}

		if (metadata.studentOwned && metadata.studentDataOwner) {
			relatedParties.add(metadata.studentDataOwner)
		}

		Collection<User> dataCustodians = User.findAllByRole(UserRole.findByName(UserRole.DATACUSTODIAN))
		if (dataCustodians && !dataCustodians.empty) {
			relatedParties.addAll(dataCustodians)
		}

		for (User user : relatedParties) {
			File dir = new File(getCompliantPartyPath())
			if(!dir || !dir.exists()) {
				dir.mkdir()
			}

			String fileName = getCompliantPartyFileName(user)
			File file = new File(fileName)
			if (file==null || !file.exists()) {
				createPtyRecordXml(user, fileName)
			}
		}
	}

	private void createPtyRecordXml(User user, String filePath) {
		String id = "www.sydney.edu.au/sho/pty/" + user.id

		def writer = new StringWriter()
		def xml = new MarkupBuilder(writer)

		xml.getMkp().xmlDeclaration(version:"1.0")
		xml.registryObjects("xmlns":"http://ands.org.au/standards/rif-cs/registryObjects",
				"xmlns:xsi":"http://www.w3.org/2001/XMLSchema-instance",
				"xsi:schemaLocation":"http://ands.org.au/standards/rif-cs/registryObjects http://services.ands.org.au/documentation/rifcs/schema/registryObjects.xsd") {
			registryObject(group:"The University of Sydney") {
				key(id)
				originatingSource("http://www.sydney.edu.au/sho")
				party(type:"person") {
					identifier(type:"AAF-token", user.openIdUrl)
					name(type:"primary") {
						namePart(type:"title","")
						namePart(type:"given",user.givenName)
						namePart(type:"family",user.familyName)
					}
					location() {
						address() {
							electronic(type:"email") {
								value(user.emailAddress)
							}
						}
					}
				}
			}
		}

		File file = new File(filePath)
		file.createNewFile()
		file.setWritable(true, false)
		file.write(writer.toString())
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


	private String getLatitudeLongitudeValues() {
		def csvfile = getCsvFile()
		StringBuffer result = new StringBuffer()
		boolean updated = false

		csvfile.eachLine { line, index ->

			if (index > 2) { // Ignore header line and units line, start getting values
				String[] values = line.split(',')
				if(updated) {
					result.append(" ")
				}
				result.append(values[2] + "," + values[3])
				updated = true
			}
		}

		return result.toString()
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

		Metadata.list().each { metadata ->
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
}
