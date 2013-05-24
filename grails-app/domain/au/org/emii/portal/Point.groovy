package au.org.emii.portal

class Point {
	
	Double latitude
	Double longitude

	static belongsTo = Metadata
	static hasMany = [metadatas: Metadata]

    static constraints = {
    }

}