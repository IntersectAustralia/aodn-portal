databaseChangeLog = {
	changeSet(author: "htxiong", id: "1366074877399-0", failOnError: true) {
		// empty table: user_role_permissions
		delete(tableName: "user_role_permissions") {
		}
	}

	changeSet(author: "htxiong", id: "1366074877399-1", failOnError: true) {

		// empty table: user_role
		grailsChange {
			change {
				sql.execute("delete FROM user_role")
			}
			rollback {
			}
		}
	}

	changeSet(author: "htxiong", id: "1366074877399-2", failOnError: true) {

		// Inserting user roles: Administrator, Data Custodian, Researcher, Researcher with Upload, External Researcher
		insert(tableName: "user_role") {
			column(name: "id", valueComputed: "nextval('hibernate_sequence')")
			column(name: "version", valueNumeric: "0")
			column(name: "name", value: "Administrator")
		}

		insert(tableName: "user_role") {
			column(name: "id", valueComputed: "nextval('hibernate_sequence')")
			column(name: "version", valueNumeric: "0")
			column(name: "name", value: "Data Custodian")
		}

		insert(tableName: "user_role") {
			column(name: "id", valueComputed: "nextval('hibernate_sequence')")
			column(name: "version", valueNumeric: "0")
			column(name: "name", value: "Researcher with Upload")
		}

		insert(tableName: "user_role") {
			column(name: "id", valueComputed: "nextval('hibernate_sequence')")
			column(name: "version", valueNumeric: "0")
			column(name: "name", value: "Researcher")
		}

		insert(tableName: "user_role") {
			column(name: "id", valueComputed: "nextval('hibernate_sequence')")
			column(name: "version", valueNumeric: "0")
			column(name: "name", value: "External Researcher")
		}
	}

	changeSet(author: "htxiong", id: "1366074877399-3", failOnError: true) {

		// Administrator users have unrestricted permissions.
		insert(tableName: "user_role_permissions") {
			column(name: "user_role_id", valueComputed: "(select id from user_role where name = 'Administrator')")
			column(name: "permissions_string", value: "*")
		}

		//
		insert(tableName: "user_role_permissions") {
			column(name: "user_role_id", valueComputed: "(select id from user_role where name = 'Data Custodian')")
			column(name: "permissions_string", value: "user:list,index,save,show,edit,update,search,current,create")
		}
	}

}
