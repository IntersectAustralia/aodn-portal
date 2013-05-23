databaseChangeLog = {

	changeSet(author: "seanl (generated)", id: "1369285992754-1") {
		modifyDataType(tableName: "metadata", columnName: "description", newDataType: "text")
		modifyDataType(tableName: "publication", columnName: "notes", newDataType: "text")
	}
}
