databaseChangeLog = {

	changeSet(author: "seanl (generated)", id: "1367413082916-1") {
		createTable(tableName: "metadata") {
			column(name: "id", type: "int8") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "metadataPK")
			}

			column(name: "version", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "collection_period_from", type: "timestamp") {
				constraints(nullable: "false")
			}

			column(name: "collection_period_to", type: "timestamp") {
				constraints(nullable: "false")
			}

			column(name: "data_access", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "data_owner_id", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "data_type", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "dataset_name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "timestamp") {
				constraints(nullable: "false")
			}

			column(name: "description", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "embargo", type: "bool") {
				constraints(nullable: "false")
			}

			column(name: "embargo_expiry_date", type: "timestamp") {
				constraints(nullable: "false")
			}

			column(name: "key", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "timestamp") {
				constraints(nullable: "false")
			}

			column(name: "licence", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "research_code", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "service_key", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "seanl (generated)", id: "1367413082916-2") {
		createTable(tableName: "metadata_portal_user") {
			column(name: "metadata_collectors_id", type: "int8")

			column(name: "user_id", type: "int8")

			column(name: "metadata_granted_users_id", type: "int8")

			column(name: "metadata_principal_investigators_id", type: "int8")
		}
	}

	changeSet(author: "seanl (generated)", id: "1367413082916-3") {
		createTable(tableName: "publication") {
			column(name: "id", type: "int8") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "publicationPK")
			}

			column(name: "version", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "metadata_id", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "type", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "value", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "seanl (generated)", id: "1367413082916-4") {
		addForeignKeyConstraint(baseColumnNames: "data_owner_id", baseTableName: "metadata", constraintName: "FKE52D7B2F45D0167", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "portal_user", referencesUniqueColumn: "false")
	}

	changeSet(author: "seanl (generated)", id: "1367413082916-5") {
		addForeignKeyConstraint(baseColumnNames: "metadata_collectors_id", baseTableName: "metadata_portal_user", constraintName: "FK14290AAE96617713", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "metadata", referencesUniqueColumn: "false")
	}

	changeSet(author: "seanl (generated)", id: "1367413082916-6") {
		addForeignKeyConstraint(baseColumnNames: "metadata_granted_users_id", baseTableName: "metadata_portal_user", constraintName: "FK14290AAE397AA075", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "metadata", referencesUniqueColumn: "false")
	}

	changeSet(author: "seanl (generated)", id: "1367413082916-7") {
		addForeignKeyConstraint(baseColumnNames: "metadata_principal_investigators_id", baseTableName: "metadata_portal_user", constraintName: "FK14290AAED026B440", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "metadata", referencesUniqueColumn: "false")
	}

	changeSet(author: "seanl (generated)", id: "1367413082916-8") {
		addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "metadata_portal_user", constraintName: "FK14290AAE761E105A", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "portal_user", referencesUniqueColumn: "false")
	}

	changeSet(author: "seanl (generated)", id: "1367413082916-9") {
		addForeignKeyConstraint(baseColumnNames: "metadata_id", baseTableName: "publication", constraintName: "FKBFBBA22CD58DE5DA", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "metadata", referencesUniqueColumn: "false")
	}
}
