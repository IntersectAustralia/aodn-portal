
/**
 * Created with IntelliJ IDEA.
 * User: htxiong
 * Date: 7/05/13
 * Time: 12:48 PM
 * To change this template use File | Settings | File Templates.
 */
databaseChangeLog = {
	changeSet(author: "htxiong", id: "20130507-add-metadata-create-permission-to-userRoles", failOnError: true) {

		// add dataset upload permission to UserRoles: Administrator, Data Custodian and Researcher with Upload.
		insert(tableName: "user_role_permissions") {
			column(name: "user_role_id", valueComputed: "(select id from user_role where name = 'Data Custodian')")

			column(name: "permissions_string", value: "metadata:create")
		}

		insert(tableName: "user_role_permissions") {
			column(name: "user_role_id", valueComputed: "(select id from user_role where name = 'Researcher with Upload')")

			column(name: "permissions_string", value: "metadata:create")
		}

		insert(tableName: "user_role_permissions") {
			column(name: "user_role_id", valueComputed: "(select id from user_role where name = 'Administrator')")

			column(name: "permissions_string", value: "metadata:create")
		}
	}
}