
/*
 * Copyright 2012 IMOS
 *
 * The AODN/IMOS Portal is distributed under the terms of the GNU General Public License
 *
 */

package au.org.emii.portal

import org.apache.shiro.SecurityUtils

class User {

    String openIdUrl
    String emailAddress
    String fullName
	String givenName
	String familyName
	Organization organization
	UserRole role
	boolean active

    // Relationships
    static hasMany = [ permissions: String ]

	// defines cascading update-delete relation,
	// save-delete a UserRole object will delete
	// all the users beloginsTo that role as well,
	// But non for the reverse.
	static belongsTo = [role: UserRole]

    static constraints = {

        openIdUrl unique: true, blank: false
        emailAddress blank: false
        fullName blank: false
		organization nullable: true
		role nullable: false
		givenName nullable: true, blank: true
		familyName nullable: true, blank: true
    }

    User() {

        emailAddress = "<Not set>"
        fullName = "<Not set>"
    }

    // db mapping
    static mapping = {
        table 'portal_user'
    }
    
    @Override
    public String toString() {

		def rolePart = role ? "[Role: ${role}]" : "[No Roles]"

        return "$fullName $rolePart ($openIdUrl)"
    }

    void beforeDelete(){
        Snapshot.withNewSession{
            def snapshots = Snapshot.findAllByOwner(this)
            snapshots*.delete()
        }

        Search.withNewSession{
            def savedSearches = Search.findAllByOwner(this)
            savedSearches*.delete()
        }
    }

    static def current = {
        def currentSubject = SecurityUtils.getSubject()
        def principal = currentSubject?.getPrincipal()
        if (principal) {
            User userInstance = User.get(principal)

            if (!userInstance)
            {
                log.error("No user found with id: " + principal)
            }
            return userInstance
        }
        return null
    }
}
