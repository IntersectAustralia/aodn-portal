/*
 * Copyright 2012 IMOS
 *
 * The AODN/IMOS Portal is distributed under the terms of the GNU General Public License
 *
 */

package au.org.emii.portal

class UserRole {
    public static String SERVEROWNER = "ServerOwner"
    public static String ADMINISTRATOR = "Administrator"
    public static String SELFREGISTERED = "SelfRegisteredUser"


    String name

    static hasMany = [ permissions: String ]

    static constraints = {
        name(nullable: false, blank: false, unique: true)
    }
    
    @Override
    public String toString() {
        
        return name
    }

	/**
	 * this function checks is the current role is higher than the given one.
	 * based on their ids.
	 * Due to in DB migration changelog, we inserting user roles in the order of
	 * higher to lower, so higher roles always with smaller id.
 	 */
	public boolean isHigherThan( UserRole role ) {
		return this.id < role.id
	}
}
