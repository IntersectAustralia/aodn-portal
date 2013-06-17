package au.org.emii.portal
/**
 * Created with IntelliJ IDEA.
 * User: htxiong
 * Date: 14/06/13
 * Time: 4:17 PM
 * To change this template use File | Settings | File Templates.
 */
class UserRoleControllerTests extends ExtendedControllerUnitTestCase {

	UserRole role1, role2

	protected void setUp() {

		super.setUp()
		role1 = new UserRole(name: "Admin")
		role2 = new UserRole(name: "Researcher")

		mockDomain(UserRole, [role1, role2])
	}

	protected void tearDown() {
		super.tearDown()

		AodaacJob.metaClass = null
		AodaacProductLink.metaClass = null
		Layer.metaClass = null
	}

	void testIndex() {
		controller.index()
		assert  'list' == controller.redirectArgs["action"]
	}

	void testList() {
		def model = controller.list()
		assert  model.userRoleInstanceList[0].name == "Admin"
		assert  model.userRoleInstanceList[1].name == "Researcher"
		assert  model.userRoleInstanceTotal == 2
	}

	void testSave() {
		controller.metaClass.getParams { ->
			[name: "Admin"]
		}

		def model = controller.save()
		assert 'create' == controller.renderArgs["view"]
		assert  model.userRoleInstance.name == "Admin"
	}

	void testShow() {
		controller.params.id = role1.id
		def model = controller.show()
		assert  model.userRoleInstance == role1

		controller.params.id = role1.id + 2L
		controller.show()
		assert  controller.flash.message == "null not found with id 3"
		assert  controller.redirectArgs["action"] == "list"
	}

	void testEdit() {
//		def config = ConfigurationHolder.config
//		controller.grailsApplication.config = config
//		controller.params.id = role1.id
//		def model = controller.edit()
//		assert  model.userRoleInstance == role1

		controller.params.id = role1.id + 2L
		controller.edit()
		assert  controller.flash.message == "null not found with id 3"
		assert  controller.redirectArgs["action"] == "list"
	}

	void testUpdateWithInvalidID() {
		controller.params.id = role1.id + 2L
		controller.update()
		assert  controller.flash.message == "null not found with id 3"
		assert  controller.redirectArgs["action"] == "list"
	}

	void testUpdateWithSmallerVersionId() {
		controller.params.id = role1.id
		role1.version = 2L
		controller.params.version = role1.version - 1L
		def model = controller.update()
		assert controller.renderArgs["view"] == "edit"
	}

	void testUpdateWithDuplicatedName() {
		controller.params.id = role1.id
		controller.params.name = role2.name

		def model = controller.update()
		assert controller.renderArgs["view"] == "edit"
		assert model.userRoleInstance == role1
	}

	void testUpdateWithValidParams() {
		controller.params.id = role1.id
		controller.params.permissions = ['controller1:action1,action2', 'controller2:action3']

		def model = controller.update()
		assert controller.redirectArgs["action"] == "show"
	}

	void testDeleteWithInvalidID() {
		controller.params.id = role1.id + 2L
		controller.delete()
		assert  controller.flash.message == "null not found with id 3"
		assert  controller.redirectArgs["action"] == "list"
	}

	void testDeleteWithValidID() {
		controller.params.id = role1.id
		def model = controller.delete()
		assert  controller.flash.message == "null 1 deleted"
		assert  controller.redirectArgs["action"] == "list"
	}
}
