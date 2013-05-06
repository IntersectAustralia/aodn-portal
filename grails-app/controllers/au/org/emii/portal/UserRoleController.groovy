
/*
 * Copyright 2012 IMOS
 *
 * The AODN/IMOS Portal is distributed under the terms of the GNU General Public License
 *
 */

package au.org.emii.portal

import org.codehaus.groovy.grails.commons.DefaultGrailsControllerClass

class UserRoleController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [userRoleInstanceList: UserRole.list(params), userRoleInstanceTotal: UserRole.count()]
    }

    def create = {
        def userRoleInstance = new UserRole()
        userRoleInstance.properties = params
        return [userRoleInstance: userRoleInstance]
    }

    def save = {
        def userRoleInstance = new UserRole(params)
        if (userRoleInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'userRole.label', default: 'UserRole'), userRoleInstance.id])}"
            redirect(action: "show", id: userRoleInstance.id)
        }
        else {
            render(view: "create", model: [userRoleInstance: userRoleInstance])
        }
    }

    def show = {
        def userRoleInstance = UserRole.get(params.id)
        if (!userRoleInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'userRole.label', default: 'UserRole'), params.id])}"
            redirect(action: "list")
        }
        else {
            [userRoleInstance: userRoleInstance]
        }
    }

    def edit = {
        def userRoleInstance = UserRole.get(params.id)
        if (!userRoleInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'userRole.label', default: 'UserRole'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [userRoleInstance: userRoleInstance, controllerActions: getAllControllerActions()]
        }
    }

    def update = {
        def userRoleInstance = UserRole.get(params.id)
        if (userRoleInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (userRoleInstance.version > version) {
                    
                    userRoleInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'userRole.label', default: 'UserRole')] as Object[], "Another user has updated this UserRole while you were editing")
                    render(view: "edit", model: [userRoleInstance: userRoleInstance])
                    return
                }
            }

			// get the permission from textfields and remove the blank one.
			ArrayList<String> permissions = new ArrayList<>()
			for(int i=0; i< params.getList("permissions").size(); i++){
				if(""!=params.getList("permissions").get(i) && "Add permissions at here"!=params.getList("permissions").get(i)) {
					permissions.add(params.getList("permissions").get(i))
				}
			}
            userRoleInstance.properties = params
			userRoleInstance.permissions = permissions

			if (!userRoleInstance.hasErrors() && userRoleInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'userRole.label', default: 'UserRole'), userRoleInstance.id])}"
                redirect(action: "show", id: userRoleInstance.id)
            }
            else {
                render(view: "edit", model: [userRoleInstance: userRoleInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'userRole.label', default: 'UserRole'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def userRoleInstance = UserRole.get(params.id)
        if (userRoleInstance) {
            try {
                userRoleInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'userRole.label', default: 'UserRole'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'userRole.label', default: 'UserRole'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'userRole.label', default: 'UserRole'), params.id])}"
            redirect(action: "list")
        }
    }

	private HashMap<String, List<String>> getAllControllerActions() {
		DefaultGrailsControllerClass[] controllers = grailsApplication.controllerClasses

		HashMap<String, List<String>> controllerActions = new HashMap<String, List<String>>()

		controllers.each {
			String controllerName = it.name

			List<String> actions = controllerActions.get(controllerName)
			if (!actions) {
				actions = []
				controllerActions.put(controllerName, actions)
			}

			for (String uri : it.getURIs() ) {
				actions.add(it.getMethodActionName(uri) )
			}
		}

		return controllerActions
	}
}
