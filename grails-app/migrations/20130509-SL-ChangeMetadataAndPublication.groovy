databaseChangeLog = {

	changeSet(author: "seanl (generated)", id: "1368056481627-1") {
		createTable(tableName: "metadata_publications") {
			column(name: "metadata_id", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "publication_id", type: "int8") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "seanl (generated)", id: "1368056481627-2") {
		addColumn(tableName: "metadata") {
			column(name: "student_data_owner_id", type: "int8") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "seanl (generated)", id: "1368056481627-3") {
		addColumn(tableName: "metadata") {
			column(name: "student_owned", type: "bool") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "seanl (generated)", id: "1368056481627-4") {
		addColumn(tableName: "publication") {
			column(name: "identifier", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "seanl (generated)", id: "1368056481627-5") {
		addColumn(tableName: "publication") {
			column(name: "identifier_type", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "seanl (generated)", id: "1368056481627-6") {
		addColumn(tableName: "publication") {
			column(name: "notes", type: "text") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "seanl (generated)", id: "1368056481627-7") {
		addColumn(tableName: "publication") {
			column(name: "title", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "seanl (generated)", id: "1368056481627-8") {
		addPrimaryKey(columnNames: "metadata_id, publication_id", tableName: "metadata_publications")
	}

	changeSet(author: "seanl (generated)", id: "1368056481627-9") {
		dropForeignKeyConstraint(baseTableName: "metadata", baseTableSchemaName: "public", constraintName: "fke52d7b2f45d0167")
	}

	changeSet(author: "seanl (generated)", id: "1368056481627-10") {
		dropForeignKeyConstraint(baseTableName: "publication", baseTableSchemaName: "public", constraintName: "fkbfbba22cd58de5da")
	}

	changeSet(author: "seanl (generated)", id: "1368056481627-11") {
		addForeignKeyConstraint(baseColumnNames: "student_data_owner_id", baseTableName: "metadata", constraintName: "FKE52D7B2FB2AF5263", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "portal_user", referencesUniqueColumn: "false")
	}

	changeSet(author: "seanl (generated)", id: "1368056481627-12") {
		addForeignKeyConstraint(baseColumnNames: "metadata_id", baseTableName: "metadata_publications", constraintName: "FK6DE276D7D58DE5DA", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "metadata", referencesUniqueColumn: "false")
	}

	changeSet(author: "seanl (generated)", id: "1368056481627-13") {
		addForeignKeyConstraint(baseColumnNames: "publication_id", baseTableName: "metadata_publications", constraintName: "FK6DE276D743BBABBA", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "publication", referencesUniqueColumn: "false")
	}

	changeSet(author: "seanl (generated)", id: "1368056481627-14") {
		dropColumn(columnName: "data_owner_id", tableName: "metadata")
	}

	changeSet(author: "seanl (generated)", id: "1368056481627-15") {
		dropColumn(columnName: "metadata_id", tableName: "publication")
	}

	changeSet(author: "seanl (generated)", id: "1368056481627-16") {
		dropColumn(columnName: "type", tableName: "publication")
	}

	changeSet(author: "seanl (generated)", id: "1368056481627-17") {
		dropColumn(columnName: "value", tableName: "publication")
	}
}
