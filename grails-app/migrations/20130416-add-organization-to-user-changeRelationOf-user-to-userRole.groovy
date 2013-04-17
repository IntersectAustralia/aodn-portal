databaseChangeLog = {

	changeSet(author: "htxiong (generated)", id: "1366074877370-1") {
		createTable(tableName: "organization") {
			column(name: "id", type: "int8") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "organizationPK")
			}

			column(name: "version", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false", unique: "true")
			}

			column(name: "type_id", type: "int8") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "htxiong (generated)", id: "1366074877370-2-1") {

		// empty table: portal_user_permissions
		delete(tableName: "portal_user_permissions") {
		}

		// empty table: portal_user_roles
		delete(tableName: "portal_user_roles") {
		}

		// empty table: portal_user
		delete(tableName: "portal_user") {
		}
	}

	changeSet(author: "htxiong (generated)", id: "1366074877370-2") {
		addColumn(tableName: "portal_user") {
			column(name: "organization_id", type: "int8")
		}
	}

	changeSet(author: "htxiong (generated)", id: "1366074877370-3") {
		addColumn(tableName: "portal_user") {
			column(name: "role_id", type: "int8") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "htxiong (generated)", id: "1366074877370-4") {
		addNotNullConstraint(columnDataType: "varchar(50)", columnName: "default_dateline_zoom_bbox", tableName: "config")
	}

	changeSet(author: "htxiong (generated)", id: "1366074877370-5") {
		dropNotNullConstraint(columnDataType: "bool", columnName: "enable_default_dateline_zoom", tableName: "config")
	}

	changeSet(author: "htxiong (generated)", id: "1366074877370-6") {
		addNotNullConstraint(columnDataType: "bool", columnName: "enabled", defaultNullValue: "FALSE", tableName: "filter")
	}

	changeSet(author: "htxiong (generated)", id: "1366074877370-7") {
		dropForeignKeyConstraint(baseTableName: "portal_user_roles", baseTableSchemaName: "public", constraintName: "fkc77d2afc761e105a")
	}

	changeSet(author: "htxiong (generated)", id: "1366074877370-8") {
		dropForeignKeyConstraint(baseTableName: "portal_user_roles", baseTableSchemaName: "public", constraintName: "fkc77d2afce5cb2671")
	}

	changeSet(author: "htxiong (generated)", id: "1366074877370-9") {
		createIndex(indexName: "layer_id_unique_1366074875025", tableName: "layer_view_parameters", unique: "true") {
			column(name: "layer_id")
		}
	}

	changeSet(author: "htxiong (generated)", id: "1366074877370-10") {
		createIndex(indexName: "title_unique_1366074875030", tableName: "menu", unique: "true") {
			column(name: "title")
		}
	}

	changeSet(author: "htxiong (generated)", id: "1366074877370-11") {
		createIndex(indexName: "name_unique_1366074875051", tableName: "organization", unique: "true") {
			column(name: "name")
		}
	}

	changeSet(author: "htxiong (generated)", id: "1366074877370-12") {
		createIndex(indexName: "name_unique_1366074875077", tableName: "server", unique: "true") {
			column(name: "name")
		}
	}

	changeSet(author: "htxiong (generated)", id: "1366074877370-13") {
		createIndex(indexName: "short_acron_unique_1366074875079", tableName: "server", unique: "true") {
			column(name: "short_acron")
		}
	}

	changeSet(author: "htxiong (generated)", id: "1366074877370-14") {
		createIndex(indexName: "uri_unique_1366074875079", tableName: "server", unique: "true") {
			column(name: "uri")
		}
	}

	changeSet(author: "htxiong (generated)", id: "1366074877370-15") {
		createIndex(indexName: "name_unique_1366074875100", tableName: "user_role", unique: "true") {
			column(name: "name")
		}
	}

	changeSet(author: "htxiong (generated)", id: "1366074877370-16") {
		addForeignKeyConstraint(baseColumnNames: "wfs_layer_id", baseTableName: "layer", constraintName: "FK61FD551B88AD7F5", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "layer", referencesUniqueColumn: "false")
	}

	changeSet(author: "htxiong (generated)", id: "1366074877370-17") {
		addForeignKeyConstraint(baseColumnNames: "type_id", baseTableName: "organization", constraintName: "FK4644ED3373B05DF4", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "organisation_type", referencesUniqueColumn: "false")
	}

	changeSet(author: "htxiong (generated)", id: "1366074877370-18") {
		addForeignKeyConstraint(baseColumnNames: "organization_id", baseTableName: "portal_user", constraintName: "FKF1617FBE39A3375A", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "organization", referencesUniqueColumn: "false")
	}

	changeSet(author: "htxiong (generated)", id: "1366074877370-19") {
		addForeignKeyConstraint(baseColumnNames: "role_id", baseTableName: "portal_user", constraintName: "FKF1617FBE8ADB8EE5", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user_role", referencesUniqueColumn: "false")
	}

	changeSet(author: "htxiong (generated)", id: "1366074877370-20") {
		dropColumn(columnName: "application_base_url", tableName: "config")
	}

	changeSet(author: "htxiong (generated)", id: "1366074877370-21") {
		dropColumn(columnName: "wms_scanner_base_url", tableName: "config")
	}

	changeSet(author: "htxiong (generated)", id: "1366074877370-22") {
		dropColumn(columnName: "wms_scanner_callback_username", tableName: "config")
	}

	changeSet(author: "htxiong (generated)", id: "1366074877370-23") {
		dropTable(tableName: "portal_user_roles")
	}
}
