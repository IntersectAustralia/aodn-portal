package au.org.emii.portal

import grails.converters.JSON
import grails.util.Environment
import org.apache.shiro.SecurityUtils

import java.text.SimpleDateFormat

class MetadataController {
    static allowedMethods = [canEdit: "GET", downloadDataset: "GET", downloadMetadata: "GET", search: "GET", save: "POST", update: "POST", delete: "POST"]

    def index = {
		if (!checkPermission(params.id, actionName)) {
			return
		}
        redirect(action: "list", params: params)
    }

    def list = {
		if (!checkPermission(params.id, actionName)) {
			return
		}
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [metadataInstanceList: Metadata.list(params), metadataInstanceTotal: Metadata.count()]
    }

    def create = {
		if (!checkPermission(params.id, actionName)) {
			return
		}

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
		if (!checkPermission(params.id, actionName)) {
			return
		}

        def metadataInstance = Metadata.get(params.id)

		if (!checkPermission(params.id, actionName)) {
			return
		}
		
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
		if (!checkPermission(params.id, actionName)) {
			return
		}

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
		if (!checkPermission(params.id, actionName)) {
			return
		}

        def metadataInstance = Metadata.get(params.id)
        if (!metadataInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'metadata.label', default: 'Metadata'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [metadataInstance: metadataInstance, users:User.list()]
        }
    }

    def update = {
		if (!checkPermission(params.id, actionName)) {
			return
		}

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

			validateParamsAndBindData(metadataInstance)
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

	private void validateParamsAndBindData(Metadata metadata) {
		if (!params.embargo || params.grantedUsers == 'Enter name of user here') {
			params.remove('grantedUsers')
			params.remove('embargo')
			params.remove('embargoExpiryDate')
			metadata.grantedUsers.clear()
			metadata.embargo = false
			metadata.embargoExpiryDate = null
		}

		if (params.collectors == 'Enter name of the collector here') {
			params.remove('collectors')
			metadata.collectors.clear()
		}

		if (params.publications == 'Enter publication here') {
			params.remove('publications')
			metadata.publications.clear()
		}

		if (params.principalInvestigator.id == null || params.principalInvestigator.id == "") {
			metadata.principalInvestigator = null
		} else {
			metadata.principalInvestigator = User.findById(params.principalInvestigator.id.toLong())
		}
		params.remove('principalInvestigator.id')

		if (!params.studentOwned || params.studentDataOwner.id == null || params.studentDataOwner.id == "") {
			metadata.studentDataOwner = null
			metadata.studentOwned = false
		} else {
			metadata.studentDataOwner = User.findById(params.studentDataOwner.id.toLong())
			metadata.studentOwned = true
		}
		params.remove('studentDataOwner.id')

		metadata.properties = params
	}

	def delete = {
		if (!checkPermission(params.id, actionName)) {
			return
		}

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
		if (!checkPermission(params.id, actionName)) {
			return
		}

		def result = [:]
		def records = []
		def datasetUrl = ""
		def metadataUrl = ""
		def links = []
		def sdf = new SimpleDateFormat("yyyy-MM-dd")
		def dateTo = new Date()

		if (params.extTo) {
			dateTo = sdf.parse(params.extTo)
		}

		def c = Metadata.createCriteria()
		def metadataInstanceList = c.listDistinct {
			if (params.extFrom) {
				ge("collectionPeriodFrom", sdf.parse(params.extFrom))
			}

			le("collectionPeriodTo", dateTo)

			if (params.eastBL && params.northBL && params.southBL && params.westBL) {
				points {
					between("latitude", params.double('southBL'), params.double('northBL'))
					between("longitude", params.double('westBL'), params.double('eastBL'))
				}
			}
		}

		metadataInstanceList.each { metadata ->
			links = []

			datasetUrl = createLink(action: "downloadDataset", params: [dataset: metadata.datasetPath, filename: "${metadata.datasetName}.csv"], absolute: true)
			links << generateLink(datasetUrl, "${metadata.datasetName} - Dataset(CSV)")

			if (metadata.metadataPath != null) {
				metadataUrl = createLink(action: "downloadMetadata", params: [metadata: metadata.metadataPath, filename: "${metadata.datasetName}.txt"], absolute: true)
				links << generateLink(metadataUrl, "${metadata.datasetName} - Metadata(TXT)")
			}

			records << [
				'title': metadata.datasetName,
				'abstract': metadata.dataType,
                'uuid': metadata.id,
                'links': links,
                'source': 'Time Series',
                'canEdit': canEdit(metadata.id),
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
		Metadata metadata = Metadata.findByDatasetPath(params.dataset)

	    if (metadata == null) {
		    render text: "No Dataset file was found, it may be deleted already!", status: 404
		    return
	    }

		if (!checkPermission(metadata.id, actionName)) {
			return
		}

    	def datasetFile = new File("/aodn-portal/data/${params.dataset}")
    	response.setContentType("application/octet-stream")
        response.setHeader("Content-disposition", "attachment; filename=\"${params.filename}\"")
        response.outputStream << datasetFile.newInputStream()
        return
    }

    def downloadMetadata = {
		Metadata metadata = Metadata.findByMetadataPath(params.metadata)

	    if (metadata == null) {
		    render text: "No Metadata file was found, it may be deleted already!", status: 404
		    return
	    }

		if (!checkPermission(metadata.id, actionName)) {
			return
		}

    	def metadataFile = new File("/aodn-portal/data/${params.metadata}")
    	response.setContentType("application/octet-stream")
        response.setHeader("Content-disposition", "attachment; filename=\"${params.filename}\"")
        response.outputStream << metadataFile.newInputStream()
        return
    }

    private Map generateLink(url, title) {
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

	private String canEdit(id) {
		def metadataInstance = Metadata.get(id)
		def currentUser = User.current()

		if (userInRoles(currentUser, ["Administrator", "Data Custodian"]) ||
			metadataInstance.collectors?.contains(currentUser) || 
			metadataInstance.principalInvestigator == currentUser || 
			metadataInstance.studentDataOwner == currentUser) {
			return 'true'
		}

		return 'false'
	}

	/**
	 *  Admin: All
	 *	Data Custodian: All
	 *	Researcher with upload: All except delete
	 *	collectors: All except create & delete
	 *	 principal investigator: All except create & delete
	 *	student owner: All except create & delete
	 *	granted users: read (show, list, search & index) + download
	 *	all others: read (show, list, search & index)
	 * @param id metadata id
	 * @param actionName actionName name
	 * @return
	 */
	private boolean checkPermission(def id, String actionName) {

		if (Environment.current == Environment.TEST) return true

		// For public access actions
		if (['index', 'list', 'search', 'show'].contains(actionName))  {
			return true
		}

		// For non public access actions.
		def passed = true
		def metadataInstance = Metadata.get(id)

		if( metadataInstance && ['downloadDataset', 'downloadMetadata'].contains(actionName)
				&& metadataInstance.dataAccess == Metadata.dataAccessList().get(0).name) {
			return true
		}

		def currentUser = User.current()

		if (currentUser) {
			if (userInRoles(currentUser, [UserRole.ADMINISTRATOR, UserRole.DATACUSTODIAN])) {
				passed = true
			} else if (userInRoles(currentUser, [UserRole.RESEARCHERWITHUPLOAD])) {
				passed = ['create', 'downloadDataset', 'downloadMetadata', 'edit', 'save', 'update'].contains(actionName)
			} else if ( metadataInstance?.collectors?.contains(currentUser) ||
					metadataInstance?.principalInvestigator == currentUser ||
					metadataInstance?.studentDataOwner == currentUser) {
				passed = ['downloadDataset', 'downloadMetadata', 'edit', 'save', 'update'].contains(actionName)
			} else if (metadataInstance?.embargo && metadataInstance?.embargoExpiryDate?.after(new Date()) && metadataInstance?.grantedUsers?.contains(currentUser))  {
				passed = ['downloadDataset', 'downloadMetadata'].contains(actionName)
			} else {
				passed = false
			}

			if (!passed) {
				flash.message = "${message(code: 'default.request.denied', args: [actionName])}"
				redirect(action: "show", id: id)
			}
		} else {
			flash.message = "${message(code: 'default.login.required')}"
			redirect controller: "home"
			passed = false
		}

		passed
	}

	private boolean userInRoles(User user, List<String> roles) {
		if (user && user.active) {
	       	for (String role : roles){
	       		if (role == user.role.name) {
	       			return true
	       		}
	       	}
       }

	   return false
 	}

}
