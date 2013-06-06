
/*
 * Copyright 2012 IMOS
 *
 * The AODN/IMOS Portal is distributed under the terms of the GNU General Public License
 *
 */

package au.org.emii.portal

import grails.converters.JSON
import grails.util.Environment
import org.apache.catalina.connector.ClientAbortException

class DownloadCartController {

    static allowedMethods = [add: "POST"]

    def bulkDownloadService

    def add = {

        if ( !params.newEntries ) {

            render text: "No items specified to add", status: 500
            return
        }

        def newEntries = JSON.parse( params.newEntries as String )

		if (Environment.current != Environment.TEST)  {
			// check download permission
			for(JSON entry : newEntries) {
				def passed = true

				def id
                try {
                    id = Long.getLong(entry.get('rec_uuid'))
                }
                catch (Exception e) {
                    break
                }

				// Do download permission checking.
				def metadataInstance = Metadata.get(id)
				if (metadataInstance && metadataInstance.dataAccess == Metadata.dataAccessList().get(0).name) {}
				else {
					def currentUser = User.current()
					if (currentUser) {
						if (!currentUser.active || !(currentUser.role.name == UserRole.ADMINISTRATOR ||
								currentUser.role.name == UserRole.DATACUSTODIAN ||
								currentUser.role.name == UserRole.RESEARCHERWITHUPLOAD ||
								metadataInstance?.collectors?.contains(currentUser) ||
								metadataInstance?.principalInvestigator == currentUser ||
								metadataInstance?.studentDataOwner == currentUser ||
								(metadataInstance?.embargo && metadataInstance?.embargoExpiryDate?.after(new Date()) && metadataInstance?.grantedUsers?.contains(currentUser)))) {

							render text: "${message(code: 'default.request.denied', args: [""])}", status: 401
							return
						}
					} else {
						render text: "${message(code: 'default.login.required', args: [""])}", status: 403
						return
					}
				}
			}
		}

        def cart = _getCart()

        cart.addAll newEntries.toArray()
        _setCart( cart )

        render _getCartSize().toString()
    }

    def clear = {

        _setCart null

        render _getCartSize().toString()
    }

    def modifyRecordAvailability = {
        if ( !params.rec_uuid ) {
            render text: "No item specified to remove", status: 500
            return
        }
        if ( !params.disableFlag ) {
            render text: "No flag specified to modify", status: 500
            return
        }

        def uuid = params.rec_uuid
        def cart = _getCart()

        cart.each{
            if (String.valueOf(it.rec_uuid) == uuid) {
                it.disableFlag = params.disableFlag.toBoolean()
            }
        }

        _setCart( cart )
        render _getCartSize().toString()
    }


    def getSize = {

        render _getCartSize().toString()
    }

    def getCartRecords = {

        def cart = _getCart()
        def compiledResult = [:]
        def records = [:]
        cart.each{

            def uuid = it.rec_uuid

            def record = records.get(uuid)
            if (!record){
                record = [:]
                record.title = it.rec_title
                record.disable = it.disableFlag
                record.uuid= uuid
                record.downloads = []
                records.put(uuid,record)
            }

            def download = [:]


            download.protocol = it.protocol
            download.title = it.title
            download.href = it.href
            download.type = it.type

            record.downloads.add(download)
        }

        compiledResult.put("records", records.values())

        render compiledResult as JSON
    }

    def download = {

        // Break early if no cookies
        if ( !_getCartSize() ) {

            flash.message = "No data in cart to download"
            redirect controller: 'home'
            return
        }

        // Prepare response stream and create zip stream
        response.setHeader( "Content-Disposition", "attachment; filename=${bulkDownloadService.getArchiveFilename( request.locale )}" )
        response.contentType = "application/octet-stream"
        def outputStream = response.outputStream

        // Ask Service to create archive to outputStream
        try {
            bulkDownloadService.generateArchiveOfFiles _getCart(), outputStream, request.locale

            // Send response
            outputStream.flush()
        }
        catch (ClientAbortException e) {

            log.info "ClientAbortException caught during bulk download", e
        }
        catch (Exception e) {

            log.error "Unhandled Exception caught during bulk download", e
        }
     }

    def _getCart() {

        return session.downloadCart ?: [] as Set
    }

    // count of items not marked as disabled
    def _getCartSize() {

        def counter = 0
        _getCart().each {
            if (!it.disableFlag) {
               counter += 1
            }
        }
        return counter
    }

    void _setCart( newCart ) {

        session.downloadCart = newCart
    }
}
