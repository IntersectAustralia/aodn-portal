databaseChangeLog = {
	changeSet(author: "htxiong", id: "1366074878499-2", failOnError: true) {

		// Inserting the default organization "The University of Sydney"
		insert(tableName: "organization") {
			column(name: "id", valueComputed: "nextval('hibernate_sequence')")
			column(name: "version", valueNumeric: "0")
			column(name: "name", value: "The University of Sydney")
			column(name: "type_id", valueComputed: "(select id from organisation_type where description = 'University/research centre')")
		}

		// Inserting the default organization "The University of Sydney"
		insert(tableName: "organization") {
			column(name: "id", valueComputed: "nextval('hibernate_sequence')")
			column(name: "version", valueNumeric: "0")
			column(name: "name", value: "Other")
			column(name: "type_id", valueComputed: "(select id from organisation_type where description = 'Other')")
		}
	}
}
