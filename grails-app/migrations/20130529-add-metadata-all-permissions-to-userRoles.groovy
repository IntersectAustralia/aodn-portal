
/**
 * Created with IntelliJ IDEA.
 * User: htxiong
 * Date: 29/05/13
 * Time: 11:43 AM
 * To change this template use File | Settings | File Templates.
 */
databaseChangeLog = {
	changeSet(author: "htxiong", id: "20130529-add-metadata-all-permissions-to-userRoles", failOnError: true) {

		insert(tableName: "user_role_permissions") {
			column(name: "user_role_id", valueComputed: "(select id from user_role where name = 'Data Custodian')")

			column(name: "permissions_string", value: "metadata:create,delete,downloadDataset,downloadMetadata,edit,index,list,search,show,update")
		}

		insert(tableName: "user_role_permissions") {
			column(name: "user_role_id", valueComputed: "(select id from user_role where name = 'Researcher with Upload')")

			column(name: "permissions_string", value: "metadata:create,delete,downloadDataset,downloadMetadata,edit,index,list,search,show,update")
		}

	}
}