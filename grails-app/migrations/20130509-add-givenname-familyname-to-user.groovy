databaseChangeLog = {

	changeSet(author: "htxiong (generated)", id: "1368080227532-1") {
		addColumn(tableName: "portal_user") {
			column(name: "family_name", type: "varchar(255)") {
				constraints nullable: true
			}
		}
	}

	changeSet(author: "htxiong (generated)", id: "1368080227532-2") {
		addColumn(tableName: "portal_user") {
			column(name: "given_name", type: "varchar(255)") {
				constraints nullable: true
			}
		}
	}
}
