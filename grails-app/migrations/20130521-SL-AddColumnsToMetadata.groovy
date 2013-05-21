databaseChangeLog = {

	changeSet(author: "seanl (generated)", id: "1369108147608-1") {
		addColumn(tableName: "metadata") {
			column(name: "dataset_path", type: "varchar(255)")
		}
	}

	changeSet(author: "seanl (generated)", id: "1369108147608-2") {
		addColumn(tableName: "metadata") {
			column(name: "metadata_path", type: "varchar(255)")
		}
	}
}
