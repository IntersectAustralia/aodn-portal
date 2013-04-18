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
	Organization organization
	UserRole role

    // Relationships
    static hasMany = [ permissions: String ]

    static constraints = {

        openIdUrl unique: true, blank: false
        emailAddress blank: false
        fullName blank: false
		organization nullable: true
		role nullable: false
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

        return "${fullName} (${openIdUrl})"
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
