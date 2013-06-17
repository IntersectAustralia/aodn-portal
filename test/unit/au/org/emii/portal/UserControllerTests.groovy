package au.org.emii.portal

import grails.test.ControllerUnitTestCase

/**
 * Created with IntelliJ IDEA.
 * User: htxiong
 * Date: 14/06/13
 * Time: 12:35 PM
 * To change this template use File | Settings | File Templates.
 */
class UserControllerTests  extends ControllerUnitTestCase {

	protected void setUp() {
		super.setUp()
	}

	protected void tearDown() {
		super.tearDown()
	}

	void testIndex() {
		controller.index()

		assert  'list' == controller.redirectArgs["action"]
	}

	void testList() {
		def user = new User( openIdUrl: "http://www.example.com/openId1",
				emailAddress: "admin@utas.edu.au",
				fullName: "Joe Bloggs",
				role: new UserRole() )

		def user2 = new User( openIdUrl: "http://www.example.com/openId2", emailAddress: "admin@utas.edu.au" )
		user2.role = new UserRole(name: "Owner")
		mockDomain(User, [user, user2])

		controller.params.emailAddress =  "admin@utas.edu.au"
		def model = controller.list()

		assert model.userInstanceTotal == 2
		assert model.userInstanceList[0].openIdUrl ==  "http://www.example.com/openId1"
		assert model.userInstanceList[1].openIdUrl ==  "http://www.example.com/openId2"
	}

	void testShowValidUser() {
		def user = new User( openIdUrl: "http://www.example.com/openId1",
				emailAddress: "admin@utas.edu.au",
				fullName: "Joe Bloggs",
				role: new UserRole() )

		def user2 = new User( openIdUrl: "http://www.example.com/openId2", emailAddress: "admin@utas.edu.au" )
		user2.role = new UserRole(name: "Owner")
		mockDomain(User, [user, user2])

		// test for show a valid user
		controller.params.id =  user.id
		def model = controller.show()
		assert model.userInstance.openIdUrl == "http://www.example.com/openId1"

	}
}
