package au.org.emii.portal

class Publication {
	
	String identifierType
	String identifier
	String title
	String notes
	
	static mapping = {
		notes type: "text"
	}
	
	static belongsTo = Metadata
	static hasMany = [metadatas: Metadata]

    static constraints = {
    }

	static identifierTypeList() {
		[
			[id: 0, name: 'abn: Australian Business Number'],
			[id: 1, name: 'arc: Australian Research Council identifier'],
			[id: 2, name: 'ark: ARK Persistent Identifier Scheme'],
			[id: 3, name: 'doi: Digital Object Identifier'],
			[id: 4, name: 'handle: HANDLE System Identifier'],
			[id: 5, name: 'infouri: \'info\' URI scheme'],
			[id: 6, name: 'isil: International Standard Identifier for Libraries'],
			[id: 7, name: 'local: identifer unique within a local context'],
			[id: 8, name: 'AU-ANL:PEAU: National Library of Australia identifier'],
			[id: 9, name: 'purl: Persistent Uniform Resource Locator'],
			[id: 10, name: 'uri: Uniform Resource Identifier']
		]
	}
}
