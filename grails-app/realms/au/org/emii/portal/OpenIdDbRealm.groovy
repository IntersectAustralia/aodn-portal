/*
 * Copyright 2012 IMOS
 *
 * The AODN/IMOS Portal is distributed under the terms of the GNU General Public License
 *
 */

package au.org.emii.portal

import org.apache.shiro.authc.AccountException
import org.apache.shiro.authc.SimpleAccount
import org.apache.shiro.authc.UnknownAccountException

class OpenIdDbRealm {

    static authTokenClass = au.org.emii.portal.OpenIdAuthenticationToken

    def credentialMatcher
    def shiroPermissionResolver

    def authenticate( authToken ) {

        log.info "Attempting to authenticate ${authToken.openIdUrl}..."
        def userId = authToken.userId
        
        // Null username is invalid
        if ( !userId ) throw new AccountException( "Cannot authenticate User will null userId." )

        // Get the user with the given username. If the user is not
        // found, then they don't have an account and we throw an
        // exception.
        def user = User.findById( userId )
        if ( !user ) throw new UnknownAccountException( "No account found for user with id ${ userId }" )

        log.info "Found user '${user.openIdUrl}' in DB"

        // Now check the user's password against the hashed value stored
        // in the database.
        def account = new SimpleAccount( userId, user.openIdUrl, "OpenIdDbRealm" )
//        if ( !credentialMatcher.doCredentialsMatch( authToken, account ) ) {
//            log.info "Invalid openIdUrl"
//            throw new IncorrectCredentialsException("Invalid openIdUrl for user '${userId}'")
//        }

        return account
    }
}
