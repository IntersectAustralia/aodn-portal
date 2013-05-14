databaseChangeLog = {

	changeSet(author: "seanl (generated)", id: "1368451050066-1") {
		createTable(tableName: "metadata_research_codes") {
			column(name: "metadata_id", type: "int8")

			column(name: "research_codes_string", type: "varchar(255)")
		}
	}

	changeSet(author: "seanl (generated)", id: "1368451050066-2") {
		addForeignKeyConstraint(baseColumnNames: "metadata_id", baseTableName: "metadata_research_codes", constraintName: "FK1C2BA392D58DE5DA", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "metadata", referencesUniqueColumn: "false")
	}

	changeSet(author: "seanl (generated)", id: "1368451050066-3") {
		dropColumn(columnName: "research_code", tableName: "metadata")
	}
}
