package au.org.emii.portal

import groovy.xml.MarkupBuilder
import org.springframework.web.context.request.RequestContextHolder

import javax.servlet.http.HttpSession
import java.text.SimpleDateFormat

class MetadataService {

	static transactional = true

	static DATASETPATH = "/aodn-portal/data/"
	static COLLECTIONPATH = DATASETPATH + "colRecords/"
	static COMPLIANTPARTYPATH = DATASETPATH + "ptyRecords/"

	// a lock variable for write xml content to file.
	final Boolean fileWriteLock = false

	public void updateCollectionAndPartyRecords(Metadata metadata) {
		updateColletionDescriptionRecord(metadata)
		updateCompliantPartyRecords(metadata)
	}

	private String getCollectionFileName(Metadata metadata) {
		return COLLECTIONPATH + "www.sydney.edu.au%2Fsho%2Fcol%2F" + metadata.id + ".xml"
	}

	private void updateColletionDescriptionRecord(Metadata metadata) {
		File dir = new File(COLLECTIONPATH)
		if(!dir || !dir.exists()) {
			dir.mkdir()
		}

		String fileName = getCollectionFileName(metadata)

		synchronized (fileWriteLock) {
			File file = new File(fileName)
			if (file && file.exists()) {
				file.delete()
			}
		}

		def writer = createColRecordXml(metadata)
		writeToFile(fileName, writer)
	}

	private StringWriter createColRecordXml(Metadata metadata) {
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

						def ploygonValues = getLatitudeLongitudeValues(metadata)
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

		return writer
	}

	private String getCompliantPartyFileName(User user) {
		return  COMPLIANTPARTYPATH + "www.sydney.edu.au%2Fsho%2Fpty%2F" + user.id + ".xml"
	}

	private void updateCompliantPartyRecords(Metadata metadata) {
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
			File dir = new File(COMPLIANTPARTYPATH)
			if(!dir || !dir.exists()) {
				dir.mkdir()
			}

			String fileName = getCompliantPartyFileName(user)

			def writer = new StringWriter()
			synchronized (fileWriteLock) {
				File file = new File(fileName)
				if (file==null || !file.exists()) {
					writer = createPtyRecordXml(user)
				}
			}

			writeToFile(fileName, writer)
		}
	}

	private StringWriter createPtyRecordXml(User user) {
		String id = "www.sydney.edu.au/sho/pty/" + user.id

		def writer = new StringWriter()
		def xml = new MarkupBuilder(writer)

		xml.getMkp().xmlDeclaration(version:"1.0")
		xml.registryObjects("xmlns":"http://ands.org.au/standards/rif-cs/registryObjects",
				"xmlns:xsi":"http://www.w3.org/2001/XMLSchema-instance",
				"xsi:schemaLocation":"http://ands.org.au/standards/rif-cs/registryObjects http://services.ands.org.au/documentation/rifcs/schema/registryObjects.xsd") {
			registryObject(group:"The University of Sydney") {
				key(id)
				originatingSource("www.sydney.edu.au/sho")
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

		return writer
	}

	private void writeToFile(String fileName, StringWriter writer) {
		synchronized (fileWriteLock) {
			File file = new File(fileName)
			file.createNewFile()
			file.setWritable(true, false)
			file.write(writer.toString())
		}
	}

	private String getLatitudeLongitudeValues(Metadata metadata) {
		def datasetFile = metadata.datasetPath
		def csvfile = new File("${DATASETPATH}${datasetFile}")
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

}
