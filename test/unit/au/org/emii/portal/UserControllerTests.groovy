package au.org.emii.portal
/**
 * Created with IntelliJ IDEA.
 * User: htxiong
 * Date: 14/06/13
 * Time: 12:35 PM
 * To change this template use File | Settings | File Templates.
 */
class UserControllerTests  extends ExtendedControllerUnitTestCase {

	User user, user2

	protected void setUp() {
		super.setUp()

		user = new User( openIdUrl: "http://www.example.com/openId1",
				emailAddress: "admin@utas.edu.au",
				fullName: "Joe Bloggs",
				role: new UserRole() )

		user2 = new User( openIdUrl: "http://www.example.com/openId2", emailAddress: "admin@utas.edu.au" )
		user2.role = new UserRole(name: "Owner")
		mockDomain(User, [user, user2])
	}

	protected void tearDown() {
		super.tearDown()

		user = null
		user2 = null
	}

	void testIndex() {
		controller.index()

		assert  'list' == controller.redirectArgs["action"]
	}

	void testList() {
		controller.params.emailAddress =  "admin@utas.edu.au"
		def model = controller.list()

		assert model.userInstanceTotal == 2
		assert model.userInstanceList[0].openIdUrl ==  "http://www.example.com/openId1"
		assert model.userInstanceList[1].openIdUrl ==  "http://www.example.com/openId2"
	}

	void testShowValidUser() {
		// test for show a valid user
		controller.params.id =  user.id
		def model = controller.show()
		assert model.userInstance.openIdUrl == "http://www.example.com/openId1"
	}

	void testShowInvalidUser() {
		controller.params.id = user.id + 2L
		def model = controller.show()

		assert controller.flash.message == "null not found with id 3"
		assert controller.redirectArgs["action"] == "list"
	}

	void testEdit() {
		controller.params.id = user.id + 2L
		def model = controller.edit()

		assert controller.flash.message == "null not found with id 3"
		assert controller.redirectArgs["action"] == "list"
	}
}
