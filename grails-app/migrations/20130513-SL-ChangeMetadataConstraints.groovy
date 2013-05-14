databaseChangeLog = {

	changeSet(author: "seanl (generated)", id: "1368407723024-1") {
		dropNotNullConstraint(columnDataType: "timestamp", columnName: "embargo_expiry_date", tableName: "metadata")
	}

	changeSet(author: "seanl (generated)", id: "1368407723024-2") {
		dropNotNullConstraint(columnDataType: "int8", columnName: "student_data_owner_id", tableName: "metadata")
	}
}
