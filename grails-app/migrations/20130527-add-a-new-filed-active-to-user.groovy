databaseChangeLog = {

	changeSet(author: "htxiong (generated)", id: "1369620031546-1") {
		addColumn(tableName: "portal_user") {
			column(name: "active", type: "bool") {
				constraints(nullable: "true")
			}
		}

		sql("update portal_user set active=true")
		addNotNullConstraint(tableName: "portal_user", columnName: "active")
	}
}
