package au.org.emii.portal

class Metadata {
	
	String serviceKey
	String key
	String datasetName
	Date collectionPeriodFrom
	Date collectionPeriodTo
	String description
	String dataType
	String researchCode
	Boolean embargo
	Date embargoExpiryDate
	static hasMany = [
		grantedUsers: User,
		collectors: User,
		principalInvestigators: User,
		publications: Publication
		]
	String dataAccess
	String licence
	User dataOwner
	Date dateCreated
	Date lastUpdated

    static constraints = {
    }
}
