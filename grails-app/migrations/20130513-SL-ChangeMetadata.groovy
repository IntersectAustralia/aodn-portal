databaseChangeLog = {

	changeSet(author: "seanl (generated)", id: "1368447170238-1") {
		addColumn(tableName: "metadata") {
			column(name: "principal_investigator_id", type: "int8") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "seanl (generated)", id: "1368447170238-2") {
		dropForeignKeyConstraint(baseTableName: "metadata_portal_user", baseTableSchemaName: "public", constraintName: "fk14290aaed026b440")
	}

	changeSet(author: "seanl (generated)", id: "1368447170238-3") {
		addForeignKeyConstraint(baseColumnNames: "principal_investigator_id", baseTableName: "metadata", constraintName: "FKE52D7B2FC49D75AB", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "portal_user", referencesUniqueColumn: "false")
	}

	changeSet(author: "seanl (generated)", id: "1368447170238-4") {
		dropColumn(columnName: "metadata_principal_investigators_id", tableName: "metadata_portal_user")
	}
}
