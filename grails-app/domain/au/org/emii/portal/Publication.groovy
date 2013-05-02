package au.org.emii.portal

class Publication {
	
	String type
	String value
	
	static belongsTo = [metadata: Metadata]

    static constraints = {
    }
}
