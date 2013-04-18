
/*
 * Copyright 2012 IMOS
 *
 * The AODN/IMOS Portal is distributed under the terms of the GNU General Public License
 *
 */

package au.org.emii.portal

//import au.org.emii.portal.config.JsonMarshallingRegistrar
//import au.org.emii.portal.display.MenuJsonCache
//import au.org.emii.portal.display.MenuPresenter
//import grails.converters.JSON

class DatasetController {

    static allowedMethods = [upload: "POST"]

    def upload = {
        return [params: params]
    }

}