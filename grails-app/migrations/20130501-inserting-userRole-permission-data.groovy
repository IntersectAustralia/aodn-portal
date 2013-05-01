
/**
 * Created with IntelliJ IDEA.
 * User: htxiong
 * Date: 1/05/13
 * Time: 11:35 AM
 * To change this template use File | Settings | File Templates.
 */
databaseChangeLog = {
	changeSet(author: "htxiong", id: "20130501-inserting-userRole-permission-data-1", failOnError: true) {

		insert(tableName: "user_role_permissions") {
			column(name: "user_role_id", valueComputed: "(select id from user_role where name = 'Data Custodian')")

			column(name: "permissions_string", value: "home:*")
		}

		insert(tableName: "user_role_permissions") {
			column(name: "user_role_id", valueComputed: "(select id from user_role where name = 'Data Custodian')")

			column(name: "permissions_string", value: "search:*")
		}

		insert(tableName: "user_role_permissions") {
			column(name: "user_role_id", valueComputed: "(select id from user_role where name = 'Data Custodian')")

			column(name: "permissions_string", value: "splash:*")
		}

		insert(tableName: "user_role_permissions") {
			column(name: "user_role_id", valueComputed: "(select id from user_role where name = 'Data Custodian')")

			column(name: "permissions_string", value: "snapshot:*")
		}
	}

	changeSet(author: "htxiong", id: "20130501-inserting-userRole-permission-data-2", failOnError: true) {

		insert(tableName: "user_role_permissions") {
			column(name: "user_role_id", valueComputed: "(select id from user_role where name = 'Researcher with Upload')")

			column(name: "permissions_string", value: "home:*")
		}

		insert(tableName: "user_role_permissions") {
			column(name: "user_role_id", valueComputed: "(select id from user_role where name = 'Researcher with Upload')")

			column(name: "permissions_string", value: "search:*")
		}

		insert(tableName: "user_role_permissions") {
			column(name: "user_role_id", valueComputed: "(select id from user_role where name = 'Researcher with Upload')")

			column(name: "permissions_string", value: "splash:*")
		}

		insert(tableName: "user_role_permissions") {
			column(name: "user_role_id", valueComputed: "(select id from user_role where name = 'Researcher with Upload')")

			column(name: "permissions_string", value: "snapshot:*")
		}
	}

	changeSet(author: "htxiong", id: "20130501-inserting-userRole-permission-data-3", failOnError: true) {

		insert(tableName: "user_role_permissions") {
			column(name: "user_role_id", valueComputed: "(select id from user_role where name = 'Researcher')")

			column(name: "permissions_string", value: "home:*")
		}

		insert(tableName: "user_role_permissions") {
			column(name: "user_role_id", valueComputed: "(select id from user_role where name = 'Researcher')")

			column(name: "permissions_string", value: "search:*")
		}

		insert(tableName: "user_role_permissions") {
			column(name: "user_role_id", valueComputed: "(select id from user_role where name = 'Researcher')")

			column(name: "permissions_string", value: "splash:*")
		}

		insert(tableName: "user_role_permissions") {
			column(name: "user_role_id", valueComputed: "(select id from user_role where name = 'Researcher')")

			column(name: "permissions_string", value: "snapshot:*")
		}
	}

	changeSet(author: "htxiong", id: "20130501-inserting-userRole-permission-data-4", failOnError: true) {

		insert(tableName: "user_role_permissions") {
			column(name: "user_role_id", valueComputed: "(select id from user_role where name = 'External Researcher')")

			column(name: "permissions_string", value: "home:*")
		}

		insert(tableName: "user_role_permissions") {
			column(name: "user_role_id", valueComputed: "(select id from user_role where name = 'External Researcher')")

			column(name: "permissions_string", value: "search:*")
		}

		insert(tableName: "user_role_permissions") {
			column(name: "user_role_id", valueComputed: "(select id from user_role where name = 'External Researcher')")

			column(name: "permissions_string", value: "splash:*")
		}

		insert(tableName: "user_role_permissions") {
			column(name: "user_role_id", valueComputed: "(select id from user_role where name = 'External Researcher')")

			column(name: "permissions_string", value: "snapshot:*")
		}
	}
}
