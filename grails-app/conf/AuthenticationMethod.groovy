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
			openIdProvider.url = "https://openid.emii.org.au/login"
		}
	}

	test {
	}

	production {
		authentication {
			// can be either "OpenID" Or "AAF".
			method = "AAF"

			// specify your url
			shibboleth.url = "https://sho.sydney.edu.au/Shibboleth.sso"
		}
	}

	uat {
		authentication {
			// can be either "OpenID" Or "AAF".
			method = "AAF"

			// specify your url
			shibboleth.url = "https://uat.sho.sydney.edu.au/Shibboleth.sso"
		}
	}

	staging {
		authentication {
			// can be either "OpenID" Or "AAF".
			method = "AAF"

			// specify your url
			shibboleth.url = "https://staging.dc2b.intersect.org.au/Shibboleth.sso"
		}
	}

	qa {
		authentication {
			// can be either "OpenID" Or "AAF".
			method = "AAF"

			// specify your url
			shibboleth.url = "https://qa.dc2b.intersect.org.au/Shibboleth.sso"
		}
	}
}