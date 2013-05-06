/*
 * Copyright 2012 IMOS
 *
 * The AODN/IMOS Portal is distributed under the terms of the GNU General Public License
 *
 */

package au.org.emii.portal

import grails.converters.JSON
import org.apache.commons.collections.ListUtils

class UserController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
		[ userInstanceList: User.list(params), userInstanceTotal: User.count() ]
    }

    def create = {
        def userInstance = new User()
        userInstance.properties = params
		def visibleUserRoles = getVisibleUserRoles(userInstance)
		return [userInstance: userInstance, visibleUserRoles: visibleUserRoles]
    }

    def save = {

        def userInstance = new User()

		def organization = getOrganizationFromParams( params )
		def role = getUserRoleFromParams( params )

		params.organization = organization
		params.role = role
		userInstance.properties = params

        if ( userInstance.save( flush: true ) ) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'user.label', default: 'User'), userInstance.id])}"
            redirect(action: "show", id: userInstance.id)
        }
        else {
            render(view: "create", model: [userInstance: userInstance])
        }
    }

    def show = {
        def userInstance = User.get(params.id)
        if (!userInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])}"
            redirect(action: "list")
        }
        else {
            [userInstance: userInstance]
        }
    }

    def edit = {
        def userInstance = User.get(params.id)
        if (!userInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])}"
            redirect(action: "list")
        }
        else {
			if (!validateUpdatePermission(userInstance)) return

			def visibleUserRoles = getVisibleUserRoles(userInstance)
			return [userInstance: userInstance, visibleUserRoles: visibleUserRoles]
        }
    }

	/**
	 * there are some permission checking code at here, just because both Administrator and Data Custodian users can update users.
	 * and Data Custodian users has only restricted update permissions.
 	 */
    def update = {

        def userInstance = User.get(params.id)

        if (userInstance) {
			if (!validateUpdatePermission(userInstance)) return

            if (params.version) {
                def version = params.version.toLong()
                if (userInstance.version > version) {
                    
                    userInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'user.label', default: 'User')] as Object[], "Another user has updated this User while you were editing")
                    render(view: "edit", model: [userInstance: userInstance])
                    return
                }
            }

			def organization = getOrganizationFromParams( params )
			def role = getUserRoleFromParams( params )
			if ( !organization || !role ) {
				render(view: "edit", model: [userInstance: userInstance])
				return
			}

			// modify params, change it organization and roles value from String&String[] to objects
			params.organization = organization
			params.role = role

			// for Self updating
			if( isSelfUpdate(userInstance) && role?.id != userInstance.roleId ) {
				flash.message = "${message(code: 'default.updated.not.permitted.changeOwnRole.message', args: [params.id])}"
				render(view: "show", model: [userInstance: userInstance])
				return
			}

            userInstance.properties = params
            if (!userInstance.hasErrors() && userInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'user.label', default: 'User'), userInstance.id])}"
                redirect(action: "show", id: userInstance.id)
            }
            else {
                render(view: "edit", model: [userInstance: userInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def userInstance = User.get(params.id)
        if (userInstance) {
			if (!validateUpdatePermission(userInstance)) return

            try {
                userInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'user.label', default: 'User'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'user.label', default: 'User'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])}"
            redirect(action: "list")
        }
    }

	def current = {
		def result = User.current()
		render result as JSON
	}

	/**
	 * Search function.
	 * The result is the intersection of
	 * 1) search result by keyword on columns [fullname | email | aaf] &
	 * 2) search result by organization &
	 * 3) search result by role.
	 *
	 * Due to the User List is quite small, so performance should not be a matter at here
	 */
	def search = {

		if ( null == params.role && null == params.organization && (null == params.keyword || "" == params.keyword) ) {
			redirect(action: "list")
		} else {
			def result = []
			def terminated = false
			if ( params.keyword != null && params.keyword != "" ) {
				def result1 = User.findAllByFullNameIlikeOrEmailAddressIlike( "%" + params.keyword + "%",  "%" + params.keyword + "%")
				def result2 = User.findAllByOpenIdUrlIlike("%" + params.keyword + "%")  // find by aaf.
				result1.removeAll( result2 )
				result = ListUtils.union( result1, result2 )

				if( result.empty )	terminated = true
			}

			if ( params.role != "null" && !terminated)  {
				def role = UserRole.findById( params.role )
				def searchResult = User.findAllByRole( role );
				if ( result.empty ) {
					result = searchResult
				} else {
					result = ListUtils.intersection( result, searchResult )
				}

				if( result.empty )	terminated = true
			}

			if( "null" != params.organization && !terminated ) {
				def organization = Organization.findById( params.organization )
				def searchResult = User.findAllByOrganization( organization );
				if ( result.empty ) {
					result = searchResult
				} else {
					result = ListUtils.intersection( result, searchResult )
				}
			}

			render(view: "list", model: [ userInstanceList: result, userInstanceTotal: result.size() ]
			)
		}
	}

	def getOrganizationFromParams = {
		params ->
			def organizationId = params.organization
			def organization = Organization.findById(organizationId)
			return organization;
	}

	def getUserRoleFromParams = {
		params ->
			def roleId = params.role
			def role = UserRole.findById( roleId )
			return role
	}

	def getVisibleUserRoles = {
		userInstance ->
		List<UserRole> visibleUserRoles = UserRole.list()
		User currentUser = User.current()
		if( UserRole.DATACUSTODIAN.equalsIgnoreCase( currentUser?.role.name ) ) {
			for( role in UserRole.list() ) {
				if( UserRole.ADMINISTRATOR.equalsIgnoreCase( role?.name ) || UserRole.DATACUSTODIAN.equalsIgnoreCase( role?.name ) ) {
					visibleUserRoles.remove(role)
				}
			}

			if(isSelfUpdate(userInstance)) {
				visibleUserRoles.add(userInstance.role)
			}
		}

		return visibleUserRoles
	}

	def validateUpdatePermission = {
		userInstance ->
			if (!isUpdatePermitted(userInstance)) {
				flash.message = "${message(code: 'default.updated.not.permitted.message', args: [params.id])}"
				redirect(action: "show", id: userInstance.id)
				return false
			}

			true
	}
	def isUpdatePermitted = {
		userInstance ->
			User currentUser = User.current()
			if( UserRole.ADMINISTRATOR.equalsIgnoreCase( currentUser?.role?.name ) || UserRole.DATACUSTODIAN.equalsIgnoreCase( currentUser?.role?.name ) ) {
				return currentUser.role?.isHigherThan(userInstance?.role) || isSelfUpdate(userInstance)

			} else {
				return false
			}
	}

	private boolean isSelfUpdate( User userInstance) {
		User currentUser = User.current()
		return currentUser?.id == userInstance?.id
	}
}
