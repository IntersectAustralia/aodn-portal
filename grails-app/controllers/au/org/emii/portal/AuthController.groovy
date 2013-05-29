
/*
 * Copyright 2012 IMOS
 *
 * The AODN/IMOS Portal is distributed under the terms of the GNU General Public License
 *
 */

package au.org.emii.portal

import org.apache.shiro.SecurityUtils
import org.openid4java.consumer.ConsumerManager
import org.openid4java.discovery.DiscoveryInformation
import org.openid4java.message.ParameterList
import org.openid4java.message.ax.AxMessage
import org.openid4java.message.ax.FetchRequest
import org.openid4java.message.ax.FetchResponse
import grails.util.Environment

class AuthController {

    static def consumerManager = new ConsumerManager()

    def index = {

        forward action: "logon"
    }

	/**
	 * This is walk around for the boring redirection to /auth/login by
	 * GrailsShiroPlugin when the anonymous user's request be blocked by
	 * SecurityFilter permission checking.
	 *
	 * Because Portal2 does not force anonymous users to login, so just redirect
	 * the request back to home page.
	 *
	 * The real login action is logon. And SP monitors /auth/logon url all the time.
	 */
	def login = {
		flash.message = "You need to login first to do this request."
		redirect controller: "home"
	}

    def logon = {
	switch (Environment.current) {
    	    case Environment.DEVELOPMENT:
                _authenticateWithOpenId(false)
                break
            case Environment.PRODUCTION:
            case Environment.CUSTOM:
                forward action: "verifyResponseAAF"
                break
        }
    }

    def verifyResponseAAF = {

        def emailAddress = request['mail']
        def fullName = request['displayName']
		def givenName = "", familyName = ""
		def cn = request['cn'].toString().split(" ")
		if (cn) {
			givenName = cn[0]
			familyName = cn[cn?.length-1]
		}
		def aafToken = request['auEduPersonSharedToken']
		String organisationName = request['o']

		// In dev openIdURL stores openId info, and in prod we store aaf token in this column.
        def userInstance = User.findByOpenIdUrl(aafToken)

        if ( !userInstance ) {

			UserRole role
			Organization organization;
			if (organisationName.contains("Sydney") && organisationName.contains("University")) {
				organization = Organization.findByName("The University of Sydney")
				role = UserRole.findByName(UserRole.RESEARCHER)
			} else {
				organization = Organization.findByName("Other")
				role = UserRole.findByName(UserRole.EXTERNALRESEARCHER)
			}

			// If there are no users to date make the first user an admin
			if (User.count() < 1) {
				role = UserRole.findByName(UserRole.ADMINISTRATOR)
			}

			userInstance = new User(openIdUrl: aafToken, emailAddress: emailAddress, fullName: fullName, active: true,
									organization: organization, role: role, givenName: givenName, familyName: familyName)

            userInstance.save flush: true, failOnError: true
        }

		if (!userInstance.active) {
			log.info "User login failed. This user is not activated"
			flash.message = "User login failed. This user is not activated"
			redirect controller: "home"
			return
		}

        // Log the User in
        def authToken = new OpenIdAuthenticationToken( userInstance.id, userInstance.openIdUrl ) // Todo - DN: Remember me option
        SecurityUtils.subject.login authToken
		log.debug("Successfully login User with AAF Token ${userInstance.openIdUrl}")

        redirect controller: "home"
    }

    def verifyResponse = {

        // Todo - DN: How to redirect them back to where they were before they clicked 'login' (or 'register', 'logout', etc.)?

        // extract the parameters from the authentication response
        // (which comes in as a HTTP request from the OpenID provider)

        log.debug "request.parameterMap: ${ request.parameterMap }"

        def openidResp = new ParameterList( request.parameterMap )

        // retrieve the previously stored discovery information
        def discovered = session.getAttribute( "discovered" )

        // extract the receiving URL from the HTTP request
        def portalUrl = grailsApplication.config.grails.serverURL
        def receivingURL = portalUrl + ( request.forwardURI - request.contextPath )
        def queryString = request.queryString

        if ( queryString ) {

            receivingURL += "?${ request.queryString }"
        }

        log.debug "receivingURL: $receivingURL"

        // verify the response
        def verification = consumerManager.verify( receivingURL as String,
                                                   openidResp,
                                                   discovered as DiscoveryInformation )

        // examine the verification result and extract the verified identifier
        def verified = verification.getVerifiedId()

        if ( verified ) { // success, use the verified identifier to identify the user

            def userInstance = User.findByOpenIdUrl( verified.identifier )

            if ( !userInstance ) {

                userInstance = new User( openIdUrl: verified.identifier, active: true )

	            // If there are no users to date make the first user an admin
				if (User.count() < 1) {
					userInstance.role = UserRole.findByName(UserRole.ADMINISTRATOR)
				}
	            else {
                    userInstance.role = UserRole.findByName(UserRole.RESEARCHER)
				}
            }

			if (!userInstance.active) {
				log.info "User login failed. This user is not activated"
				flash.message = "User login failed. This user is not activated"
				redirect controller: "home"
				return
			}

            // Get values from attribute exchange
            def authResponse = verification.authResponse

            log.debug "authResponse: ${ authResponse }"

            if ( authResponse.hasExtension( AxMessage.OPENID_NS_AX ) )
            {
                // Validate response
                authResponse.validate()

                def ext = authResponse.getExtension( AxMessage.OPENID_NS_AX )

                if ( ext instanceof FetchResponse ) {

                    log.debug "Setting attributes from '$ext'"

                    userInstance.fullName = ext.getAttributeValueByTypeUri( "http://schema.openid.net/namePerson" ) ?: "Unk."
                    userInstance.emailAddress = ext.getAttributeValueByTypeUri( "http://schema.openid.net/contact/email" ) ?: "Unk."
                }
                else {

                    log.warn "Unknown response type from OpenID (ie. not a FetchResponse). ext: '$ext' (${ ext?.class?.name })"
                }
            }
            else {

                log.warn "Response doesn't have extension AxMessage.OPENID_NS_AX. Unable to set/update User fields."
            }

            // Save updated User
            userInstance.save flush: true, failOnError: true

            // Log the User in
            def authToken = new OpenIdAuthenticationToken( userInstance.id, userInstance.openIdUrl ) // Todo - DN: Remember me option
            SecurityUtils.subject.login authToken
        }
        else { // OpenID authentication failed

            log.info "OpenID authentication failed. verification.statusMsg: ${ verification.statusMsg }; params: $params"

            flash.message = ( params["openid.mode"] == "cancel" ) ? "Log in cancelled." : "Could not log in (${ verification.statusMsg })"
        }
        redirect controller: "home"
    }

    def logOut = {
       	SecurityUtils.subject?.logout()

	switch (Environment.current) {
    	    case Environment.DEVELOPMENT:
        	// Log the user out of the application.
		redirect(url: "${grailsApplication.config.openIdProvider.url}/logout")
                break
            case Environment.PRODUCTION:
            case Environment.CUSTOM:
		redirect(url: "${grailsApplication.config.shibboleth.url}/Logout")
                break
        }
    }

    def unauthorized = {
		flash.message = "You do not have permission to do this request"
        redirect controller: "home"
    }

	def _authenticateWithOpenId(register) {

		def openIdProviderUrl = grailsApplication.config.openIdProvider.url
		def portalUrl = grailsApplication.config.grails.serverURL

		// Perform discovery on our OpenID provider
		def discoveries = consumerManager.discover( openIdProviderUrl ) // User-supplied String

		// Attempt to associate with the OpenID provider
		// and retrieve one service endpoint for authentication
		def discovered = consumerManager.associate( discoveries )

		// Store the discovery information in the user's session for later use
		// leave out for stateless operation / if there is no session
		session.setAttribute "discovered", discovered

		// Retrieve accounts details w/ attribute exchange (http://code.google.com/p/openid4java/wiki/AttributeExchangeHowTo)
		def fetch = FetchRequest.createFetchRequest()
		fetch.addAttribute "email", "http://schema.openid.net/contact/email", true /* required */
		fetch.addAttribute "fullname", "http://schema.openid.net/namePerson", true /* required */

		// obtain a AuthRequest message to be sent to the OpenID provider
		def returnUrl = "${portalUrl}/auth/verifyResponse"
		def authReq = consumerManager.authenticate( discovered, returnUrl )
		authReq.addExtension fetch

		def url = authReq.getDestinationUrl( true )
		if (register) {
			url += "&r=true"
		}

		redirect url: url
	}

    def beforeInterceptor = {
        request.exceptionHandler = { ex ->
            flash.message = "There was a problem with authentication."
            redirect controller: "home"
        }
    }
}
