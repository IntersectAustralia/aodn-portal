package au.org.emii.portal

import org.springframework.dao.DataIntegrityViolationException

class MetadataController {
	def portalInstance

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [metadataInstanceList: Metadata.list(params), metadataInstanceTotal: Metadata.count()]
    }

    def create() {
        [metadataInstance: new Metadata(params), cfg: Config.activeInstance(), portalBuildInfo: _portalBuildInfo()]
    }

    def save() {
        def metadataInstance = new Metadata(params)
        if (!metadataInstance.save(flush: true)) {
            render(view: "create", model: [metadataInstance: metadataInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'metadata.label', default: 'Metadata'), metadataInstance.id])
        redirect(action: "show", id: metadataInstance.id)
    }

    def show(Long id) {
        def metadataInstance = Metadata.get(id)
        if (!metadataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'metadata.label', default: 'Metadata'), id])
            redirect(action: "list")
            return
        }

        [metadataInstance: metadataInstance]
    }

    def edit(Long id) {
        def metadataInstance = Metadata.get(id)
        if (!metadataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'metadata.label', default: 'Metadata'), id])
            redirect(action: "list")
            return
        }

        [metadataInstance: metadataInstance]
    }

    def update(Long id, Long version) {
        def metadataInstance = Metadata.get(id)
        if (!metadataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'metadata.label', default: 'Metadata'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (metadataInstance.version > version) {
                metadataInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'metadata.label', default: 'Metadata')] as Object[],
                          "Another user has updated this Metadata while you were editing")
                render(view: "edit", model: [metadataInstance: metadataInstance])
                return
            }
        }

        metadataInstance.properties = params

        if (!metadataInstance.save(flush: true)) {
            render(view: "edit", model: [metadataInstance: metadataInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'metadata.label', default: 'Metadata'), metadataInstance.id])
        redirect(action: "show", id: metadataInstance.id)
    }

    def delete(Long id) {
        def metadataInstance = Metadata.get(id)
        if (!metadataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'metadata.label', default: 'Metadata'), id])
            redirect(action: "list")
            return
        }

        try {
            metadataInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'metadata.label', default: 'Metadata'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'metadata.label', default: 'Metadata'), id])
            redirect(action: "show", id: id)
        }
    }
	
	def _portalBuildInfo() {
		
		def md = grailsApplication.metadata
		return "${ portalInstance.name() } Portal v${ md.'app.version' }, build date: ${md.'app.build.date'?:'<i>not recorded</i>'}"
	}
	
}
