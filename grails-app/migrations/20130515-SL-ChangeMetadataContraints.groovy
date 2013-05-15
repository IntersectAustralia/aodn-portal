databaseChangeLog = {

	changeSet(author: "seanl (generated)", id: "1368625592122-1") {
		dropNotNullConstraint(columnDataType: "int8", columnName: "licence", tableName: "metadata")
	}
}
