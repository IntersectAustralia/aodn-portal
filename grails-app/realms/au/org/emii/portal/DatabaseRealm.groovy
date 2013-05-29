package au.org.emii.portal

import org.apache.shiro.authz.permission.WildcardPermission

class DatabaseRealm {

	def credentialMatcher
	def shiroPermissionResolver

	def hasRole(principal, roleName) {
		log.debug "Checking hasRole($principal, $roleName)"

		User user = User.findById(principal)
		if (!user) return false

		return user.role?.name?.equalsIgnoreCase(roleName)
	}

	def hasAllRoles(principal, roles) {
		log.debug "Checking hasAllRoles($principal, $roles)"

		User user = User.findById(principal)
		if (!user) return false

		for (String role : roles) {
			if (!user.role?.name?.equalsIgnoreCase(role)) return false
		}

		return true
	}

	def isPermitted(principal, requiredPermission) {
		// Does the user have the given permission directly associated
		// with himself?
		//
		// First find all the permissions that the user has that match
		// the required permission's type and project code.
		def user = User.get(principal)
		log.debug "Calling isPermitted($principal, $requiredPermission); Found user: $user"

		if (!user) {

			log.error "Called isPermitted() but could not find User for principal: '$principal'; requiredPermission: '$requiredPermission'"
			return false
		}

		if (!user.active) {

			log.error "Called isPermitted() but User for principal: '$principal' is not activated"
			return false
		}

		def permissions = user.permissions

		// Try each of the permissions found and see whether any of
		// them confer the required permission.
		def permitted = permissions?.find { permString ->
			// Create a real permission instance from the database
			// permission.
			def perm = shiroPermissionResolver.resolvePermission(permString)

			// Now check whether this permission implies the required
			// one.
			return perm.implies(requiredPermission)
		}

		if (permitted) {
			// Found a matching permission!
			return true
		}

		// If not, does he gain it through a role?
		//
		// Get the permissions from the roles that the user does have.
		def results = User.executeQuery("select distinct p from User as user join user.role as role join role.permissions as p where user.id = '$principal'")

		// There may be some duplicate entries in the results, but
		// at this stage it is not worth trying to remove them. Now,
		// create a real permission from each result and check it
		// against the required one.
		permitted = results.find { permString ->
			// Create a real permission instance from the database
			// permission.
			def perm = shiroPermissionResolver.resolvePermission(permString)

			// Now check whether this permission implies the required
			// one.
			return perm.implies(requiredPermission)
		}

		return permitted != null
	}
}
