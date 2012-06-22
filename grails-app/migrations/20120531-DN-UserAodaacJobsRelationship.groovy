databaseChangeLog = {

    changeSet(author: "dnahodil (generated)", id: "1338444808360-2") {
        dropNotNullConstraint(columnDataType: "varchar(4)", columnName: "job_params_time_of_day_range_end", tableName: "aodaac_job")
    }

    changeSet(author: "dnahodil (generated)", id: "1338444808360-3") {
        dropNotNullConstraint(columnDataType: "varchar(4)", columnName: "job_params_time_of_day_range_start", tableName: "aodaac_job")
    }
}
