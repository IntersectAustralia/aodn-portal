package au.org.emii.portal

import groovy.xml.MarkupBuilder

class MetadataService {

	static transactional = false

	static DATASETPATH = "/aodn-portal/data/"
	static COLLECTIONPATH = DATASETPATH + "colRecords/"
	static COMPLIANTPARTYPATH = DATASETPATH + "ptyRecords/"

	public void updateCollectionAndPartyRecords(Metadata metadata) {
		updateColletionDescriptionRecord(metadata, true)
		updateCompliantPartyRecords(metadata)
	}

	public void deleteDataSet(Metadata metadata) {
		deleteSOSDBData(metadata)
		deleteCollectionAndPartyRecords(metadata)
		deleteCsvAndIfoFiles(metadata)
	}

	private void deleteSOSDBData(Metadata metadata) {
		def database = 'sosdb' // 52north.org SOS server database name
		def groovy = """/usr/bin/which groovy""".execute().text.trim()

		def dataType = "sonde"
		if("Sonde survey" == metadata.dataType) {
			dataType = "sonde"
		} else if ("Nutrient sample" == metadata.dataType) {
			dataType = "nutrient"
		} else if ("Carbon Dioxide survey" == metadata.dataType) {
			dataType = "carbondioxide"
		} else {
			return
		}

		def scriptFileName = dataType + "-delete"
		def datasetFile = metadata.datasetPath

		def command = """${groovy} /aodn-portal/scripts/${scriptFileName}.groovy /aodn-portal/data/${datasetFile} ${database}"""
		log.debug(command)

		def proc

		try {
			proc = command.execute()                 // Call *execute* on the string
			proc.waitFor()                           // Wait for the command to finish
		}
		catch(e) {
			log.debug(e.message)
		}
	}

	private void deleteCollectionAndPartyRecords(Metadata metadata) {
		// update collection records only, party record will last forever.
		updateColletionDescriptionRecord(metadata, false)
	}

	private void deleteCsvAndIfoFiles(Metadata metadata) {
		String csvFileName = DATASETPATH + metadata.datasetPath

		File csvfile = new File(csvFileName)
		if (csvfile && csvfile.exists()) {
			csvfile.delete()
		}

		String ifoFileName = DATASETPATH + metadata.metadataPath

		File ifofile = new File(ifoFileName)
		if (ifofile && ifofile.exists()) {
			ifofile.delete()
		}
	}

	private String getCollectionFileName(Metadata metadata) {
		return COLLECTIONPATH + "www.sydney.edu.au%2Fsho%2Fcol%2F" + metadata.id + ".xml"
	}

	private void updateColletionDescriptionRecord(Metadata metadata, boolean isUpdate) {
		File dir = new File(COLLECTIONPATH)
		if(!dir || !dir.exists()) {
			dir.mkdir()
		}

		String fileName = getCollectionFileName(metadata)

		File file = new File(fileName)
		if (file && file.exists()) {
			file.delete()
		}

		if (isUpdate) {
			def writer = createColRecordXml(metadata)
			writeToFile(fileName, writer)
		}
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
				originatingSource("http://sho.sydney.edu.au")

				def dateAccessioned = dateFormat.format(metadata.dateCreated)
				def dateModified = dateFormat.format(metadata.lastUpdated)
				collection(type:"dataset", dateAccessioned:dateAccessioned, dateModified:dateModified) {

					name(type:"primary") {
						namePart(type:"title","Sydney Harbour Environmental Data Facility " + metadata.dataType + " Data " + metadata.id)
					}

					identifier(type:"local", metadata.id)

					description(type:"full", metadata.description)

					location() {
						address() {
							electronic(type:"url") {
								value("http://sho.sydney.edu.au/")
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
						rightsDescriptionMap.put("Public", "This work is Open Access. Download from http://sho.sydney.edu.au.")
						rightsDescriptionMap.put("Mediated", "Contact the manager of this dataset to discuss the terms and conditions of access.")
						rightsDescriptionMap.put("Private", "Access to this dataset is restricted. Contact the manager of this dataset to discuss the terms and conditions of access.")

						accessRights(rightsDescriptionMap.get(metadata.dataAccess))
						licence(type:Metadata.licenceList().get(metadata.licence.intValue()).type, rightsUri:Metadata.licenceList().get(metadata.licence.intValue()).url, Metadata.licenceList().get(metadata.licence.intValue()).text)
					}

					// Hard Code for USYD
					relatedObject() {
						key("http://nla.gov.au/nla.party-593941")
						relation(type:"isManagedBy")
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
			File file = new File(fileName)
			if (file==null || !file.exists()) {
				writer = createPtyRecordXml(user)
				writeToFile(fileName, writer)
			}
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

		return writer
	}

	private void writeToFile(String fileName, StringWriter writer) {
		File file = new File(fileName)
		file.createNewFile()
		file.setWritable(true, true)
		file.write(writer.toString())
	}

	private String getLatitudeLongitudeValues(Metadata metadata) {
		// the result will be long/lat pairs.

		if (metadata?.aNetCDFMetadata()) {
			// because there is only on lan/lat pair for netcdf metadata, so hard code at here.
			return '151.16793823242188,-33.81262207031250'
		}

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
				result.append(values[3] + "," + values[2])
				updated = true
			}
		}

		return result.toString()
	}

}
