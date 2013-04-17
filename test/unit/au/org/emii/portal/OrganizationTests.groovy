package au.org.emii.portal

import grails.test.GrailsUnitTestCase

/**
 * Created with IntelliJ IDEA.
 * User: htxiong
 * Date: 16/04/13
 * Time: 4:20 PM
 * To change this template use File | Settings | File Templates.
 */
class OrganizationTests extends GrailsUnitTestCase {
	def Organization organization


	protected void setUp() {
		super.setUp()
		organization = new Organization( name: "UNSW" )
		mockForConstraintsTests(Organization, [organization])
	}

	protected void tearDown() {
		super.tearDown()
	}

	void testValidOrganization() {

		organization.name = "UNSW"
		organization.type = new OrganisationType()
		assertTrue organization.validate()
	}

	void testNameNullable() {
		organization.name = null
		assertFalse "Validation should have failed for null name", organization.validate()
		assertEquals "nullable", organization.errors.name
	}

	void testNameBlank() {
		organization.name = ""
		assertFalse "Validation should have failed for blank name", organization.validate()
		assertEquals "blank", organization.errors.name
	}

	void testNameUnique() {
		def organization2 = new Organization( name: "UNSW" )
		mockForConstraintsTests(Organization, [organization, organization2])

		assertFalse "Validation should have failed for unique name", organization2.validate()
		assertEquals "unique", organization2.errors.name
	}

	void testTypeNullable() {
		organization.type = null
		assertFalse "Validation should have failed for nullable type", organization.validate()
		assertEquals "nullable", organization.errors.type
	}

	void testToString() {
		assertEquals "UNSW", organization.toString()
	}
}
