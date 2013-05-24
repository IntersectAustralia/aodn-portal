databaseChangeLog = {

	changeSet(author: "seanl (generated)", id: "1369288769493-1") {
		createTable(tableName: "metadata_points") {
			column(name: "metadata_id", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "point_id", type: "int8") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "seanl (generated)", id: "1369288769493-2") {
		createTable(tableName: "point") {
			column(name: "id", type: "int8") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "pointPK")
			}

			column(name: "version", type: "int8") {
				constraints(nullable: "false")
			}

			column(name: "latitude", type: "float8(19)") {
				constraints(nullable: "false")
			}

			column(name: "longitude", type: "float8(19)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "seanl (generated)", id: "1369288769493-3") {
		addPrimaryKey(columnNames: "metadata_id, point_id", tableName: "metadata_points")
	}

	changeSet(author: "seanl (generated)", id: "1369288769493-4") {
		addForeignKeyConstraint(baseColumnNames: "metadata_id", baseTableName: "metadata_points", constraintName: "FK7F5BBF3D58DE5DA", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "metadata", referencesUniqueColumn: "false")
	}

	changeSet(author: "seanl (generated)", id: "1369288769493-5") {
		addForeignKeyConstraint(baseColumnNames: "point_id", baseTableName: "metadata_points", constraintName: "FK7F5BBF37901613A", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "point", referencesUniqueColumn: "false")
	}
}
