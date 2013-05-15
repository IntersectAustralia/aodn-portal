databaseChangeLog = {

	changeSet(author: "seanl (generated)", id: "1368630122928-1") {
		addColumn(tableName: "metadata") {
			column(name: "manager_id", type: "int8") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "seanl (generated)", id: "1368630122928-2") {
		addNotNullConstraint(columnDataType: "int8", columnName: "licence", tableName: "metadata")
	}

	changeSet(author: "seanl (generated)", id: "1368630122928-3") {
		addForeignKeyConstraint(baseColumnNames: "manager_id", baseTableName: "metadata", constraintName: "FKE52D7B2F139BC938", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "portal_user", referencesUniqueColumn: "false")
	}
}
