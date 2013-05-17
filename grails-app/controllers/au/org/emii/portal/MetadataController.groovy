package au.org.emii.portal

import groovy.xml.MarkupBuilder

import java.text.SimpleDateFormat
import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH

class MetadataController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

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
			studentOwned: false
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

	private void createColletionDescriptionRecord(Metadata metadata) {
		String path = "/aodn-portal/data/colRecords/"

		File dir = new File(path)
		if(!dir || !dir.exists()) {
			dir.mkdir()
		}

		String fileName = path + "www.sydney.edu.au%2Fsho%2Fcol-" + metadata.id + ".xml"
		File file = new File(path+fileName)
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

					identifier(type:"local", metadata.id)

					name(type:"primary") {
						namePart(type:"title","Sydney Harbor Environmental Data Facility " + metadata.dataType + " Data " + metadata.id)
					}

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

					// Data Custodian
					Collection<User> dataCustodians = User.findAllByRole(UserRole.findByName(UserRole.DATACUSTODIAN))
					for (User dataCustodian : dataCustodians) {
						relatedObject() {
							key("www.sydney.edu.au/sho/pty/" + dataCustodian.id)
							relation(type:"isManagedBy")
						}
					}

					// related Parties
					Set<User> parties = getRelatedParties(metadata)
					for (User party : parties) {
						relatedObject() {
							key("www.sydney.edu.au/sho/pty/" + party.id)
							relation(type:"[parties.relation]")
						}
					}

					// Owner
					if (metadata.studentOwned && metadata.studentDataOwner) {
						relatedObject() {
							key("www.sydney.edu.au/sho/pty/" + metadata.studentDataOwner.id)
							relation(type:"isOwnerOf")
						}
					}

					//For all ANZSRC FoR Codes linked to he dataset
					Set<String> codes = metadata.researchCodes
					for (String code : codes) {
						subject(type:"anzsrc-for", code)
					}

					description(type:"full", metadata.description)

					rights() {
						accessRights(metadata.dataAccess)
						licence(rightsUri:Metadata.licenceList().get(metadata.licence.intValue()).url, Metadata.licenceList().get(metadata.licence.intValue()).name)
					}

					// Publications
					def publications = metadata.publications
					for (Publication publication : publications) {
						relatedInfo(type:"publication") {
							identifier(type:"[identifier type]",publication.identifier)
							title(publication.title)
							notes(publication.notes)
						}
					}
				}
			}
		}

		File file = new File(fileName)
		file.createNewFile()
		file.setWritable(true, false)
		file.write(writer.toString())
	}

	private Set<User> getRelatedParties(Metadata metadata) {
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

		return relatedParties
	}

	private void createCompliantPartyRecords(Metadata metadata) {
		def relatedParties = getRelatedParties(metadata)
		if (metadata.studentOwned && metadata.studentDataOwner) {
			relatedParties.add(metadata.studentDataOwner)
		}

		for (User user : relatedParties) {
			String path = "/aodn-portal/data/ptyRecords/"

			File dir = new File(path)
			if(!dir || !dir.exists()) {
				dir.mkdir()
			}

			String fileName = "www.sydney.edu.au%2Fsho%2Fpty-" + user.id + ".xml"
			File file = new File(path+fileName)
			if (file==null || !file.exists()) {
				createPtyRecordXml(user, path+fileName)
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
				originatingSource("http://sho.sydney.edu.au")
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
}
