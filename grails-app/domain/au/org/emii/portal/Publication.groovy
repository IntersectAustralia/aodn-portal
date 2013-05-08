package au.org.emii.portal

class Publication {
	
	String identifierType
	String identifier
	String title
	
	static mapping = {
		notes type: "text"
	}
	
	static belongsTo = [metadata: Metadata]

    static constraints = {
    }
}
