databaseChangeLog = {

	changeSet(author: "seanl (generated)", id: "1368758906320-1") {
		dropNotNullConstraint(columnDataType: "int8", columnName: "principal_investigator_id", tableName: "metadata")
	}

	changeSet(author: "seanl (generated)", id: "1368758906320-2") {
		dropForeignKeyConstraint(baseTableName: "metadata", baseTableSchemaName: "public", constraintName: "fke52d7b2f139bc938")
	}

	changeSet(author: "seanl (generated)", id: "1368758906320-3") {
		dropColumn(columnName: "manager_id", tableName: "metadata")
	}
}
