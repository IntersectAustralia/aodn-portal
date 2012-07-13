databaseChangeLog = {

    changeSet(author: "dnahodil (generated)", id: "1340062635699-1") {
        addColumn(tableName: "aodaac_job") {
            column(name: "data_file_exists", type: "bool")
        }
    }

    changeSet(author: "dnahodil (generated)", id: "1340062635699-2") {
        addColumn(tableName: "aodaac_job") {
            column(name: "most_recent_data_file_exist_check", type: "timestamp")
        }
    }
}