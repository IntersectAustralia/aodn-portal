/*
Specifies configuration to specify which authentication method (OpenId Or AAF) should be use.
*/
authentication {
	openid = "OpenID"
	aaf = "AAF"
}

environments {

	development {
		authentication {
			// can be either "OpenID" Or "AAF".
			method = "OpenID"

			// specify your url
			authentication.openIdProvider.url = "https://openid.emii.org.au/login"
		}
	}

	test {
	}

	production {
		authentication {
			// can be either "OpenID" Or "AAF".
			authentication.method = "AAF"

			// specify your url
			authentication.shibboleth.url = "http://sho.sydney.edu.au/Shibboleth.sso"
		}
	}

	uat {
		authentication {
			// can be either "OpenID" Or "AAF".
			authentication.method = "AAF"

			// specify your url
			authentication.shibboleth.url = "http://uat.sho.sydney.edu.au/Shibboleth.sso"
		}
	}

	staging {
		authentication {
			// can be either "OpenID" Or "AAF".
			authentication.method = "AAF"

			// specify your url
			authentication.shibboleth.url = "https://qa.dc2b.intersect.org.au/Shibboleth.sso"
		}
	}

	qa {
		authentication {
			// can be either "OpenID" Or "AAF".
			authentication.method = "AAF"

			// specify your url
			authentication.shibboleth.url = "https://qa.dc2b.intersect.org.au/Shibboleth.sso"
		}
	}
}