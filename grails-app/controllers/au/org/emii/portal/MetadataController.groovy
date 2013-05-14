package au.org.emii.portal

import groovy.xml.MarkupBuilder

import java.text.SimpleDateFormat

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
		def datasetPath = "/aodn-portal/data/"
		def datasetFile = session.getAttribute('datasetFile')
		def seq = Metadata.count() + 1
        def metadataInstance = new Metadata()
        metadataInstance.properties = params
		metadataInstance.serviceKey = "www.sydney.edy.au/shed/col/${seq}"
		metadataInstance.key = metadataInstance.serviceKey
		metadataInstance.datasetName = datasetFile
		metadataInstance
		metadataInstance.collectionPeriodFrom = getPeriodFrom("${datasetPath}${datasetFile}")
		metadataInstance.collectionPeriodTo = getPeriodTo("${datasetPath}${datasetFile}")
        return [metadataInstance: metadataInstance, cfg: Config.activeInstance()]
    }

	private void createCompliantPrtryRecords(Metadata metadata) {
		def relatedParties = new HashSet<User>()

		if (metadata.collectors) {
			relatedParties.addAll(metadata.collectors)
		}

		if (metadata.grantedUsers) {
			relatedParties.addAll(metadata.grantedUsers)
		}

		if (metadata.principalInvestigators) {
            relatedParties.addAll(metadata.principalInvestigators)
        }

		if (metadata.studentOwned && metadata.studentDataOwner) {
			relatedParties.add(metadata.studentDataOwner)
		}

		for (User user : relatedParties) {
			String path = "/aodn-portal/data/"
			String fileName = "www.sydney.edy.au-shed-pty-" + user.id + ".xml"
			File file = new File(path+fileName)
			if (file==null || !file.exists()) {
				createXmlRecord(user, path+fileName)
			}
		}
	}

	private void createXmlRecord(User user, String filePath) {
		String id = "www.sydney.edy.au-shed-pty-" + user.id

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

    def save = {
		redirect(action: "list")
		return
		/*
        def metadataInstance = new Metadata(params)
		
        if (metadataInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'metadata.label', default: 'Metadata'), metadataInstance.id])}"
            redirect(action: "show", id: metadataInstance.id)
        }
        else {
            render(view: "create", model: [metadataInstance: metadataInstance])
        }*/
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
	
	def getPeriodFrom(file) {
		def csvfile = new File(file)
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

	def getPeriodTo(file) {
		def csvfile = new File(file)
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

}
