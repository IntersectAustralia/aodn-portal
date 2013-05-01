
/*
 * Copyright 2012 IMOS
 *
 * The AODN/IMOS Portal is distributed under the terms of the GNU General Public License
 *
 */

package au.org.emii.portal

import grails.test.*

class UserTests extends GrailsUnitTestCase {
    
    def User user
    
    protected void setUp() {

        super.setUp()

		mockForConstraintsTests User

        user = new User( openIdUrl: "http://www.example.com/openId",
                         emailAddress: "admin@utas.edu.au",
                         fullName: "Joe Bloggs",
						 role: new UserRole() )

    }

    protected void tearDown() {

        super.tearDown()
    }
    
    void testValidUser() {
        assertTrue "Validation should have succeeded (unchanged, valid instance)", user.validate()
    }

	void testOpenIdUrlUnique() {
		def user2 = new User( openIdUrl: "http://www.example.com/openId" )
		user2.role = new UserRole(name: "Owner");
		mockForConstraintsTests(User, [user, user2])

		assertFalse "validation should have failed on openIdUrl is not unique", user2.validate()
		assertEquals "unique", user2.errors.openIdUrl
	}

	void testOpenIdUrlBlank() {
		user.openIdUrl = ""
		assertFalse "Validation should have failed on openIdUrl is blank", user.validate()
		assertEquals "blank", user.errors.openIdUrl
	}

	void testEmailAddressBlank() {
		user.emailAddress = ""
		assertFalse "validation should have failed on emailAddress is blank", user.validate()
		assertEquals "blank", user.errors.emailAddress
	}

	void testFullNameBlank() {
		user.fullName = ""
		assertFalse "Validation should have failed on fullName is blank", user.validate()
		assertEquals "blank", user.errors.fullName
	}

	void testOrganizationNullable() {
		user.organization = null
		assertTrue user.validate()
	}

	void testRoleNullable() {
		user.role = null
		assertFalse "Validation should have failed on role is null", user.validate()
		assertEquals "nullable", user.errors.role
	}

    void testToString() {
        assertEquals "Joe Bloggs [Role: null] (http://www.example.com/openId)", user.toString()
    }
}
