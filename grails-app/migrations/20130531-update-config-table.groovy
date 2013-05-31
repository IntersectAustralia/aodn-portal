
/**
 * Created with IntelliJ IDEA.
 * User: htxiong
 * Date: 31/05/13
 * Time: 4:01 PM
 * To change this template use File | Settings | File Templates.
 */
databaseChangeLog = {

	changeSet(author: "htxiong", id: "20130531-update-config-table") {
		update(tableName: "config") {
			column(name: "footer_content", value: '''<p>You accept all risks and responsibility for losses, damages, costs and other consequences resulting directly or indirectly from using this site and any information or material available from it. If you have any concerns about the veracity of the data, please make inquiries via <a href="mailto:ict.shocontact@sydney.edu.au ">ict.shocontact@sydney.edu.au </a> to be directed to the data custodian.</p>''')
		}
//		sql('''update config set footer_content = <p>You accept all risks and responsibility for losses, damages, costs and other consequences resulting directly or indirectly from using this site and any information or material available from it. If you have any concerns about the veracity of the data, please make inquiries via <a href="mailto:ict.shocontact@sydney.edu.au ">ict.shocontact@sydney.edu.au </a> to be directed to the data custodian.</p>''')
		sql("update config set name='Sydney Harbour Observatory'")
	}
}
