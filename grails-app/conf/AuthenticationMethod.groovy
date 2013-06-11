/*
Specifies configuration to specify which authentication method (OpenId Or AAF) should be use.
*/
authentication {

	openid = "OpenID"
	openIdProvider.url = "https://openid.emii.org.au/login"

	aaf = "AAF"
	shibboleth.url = "https://qa.dc2b.intersect.org.au/Shibboleth.sso"
}

environments {

	development {
		// can be either "OpenID" Or "AAF".
		authentication.method = "OpenID"
	}

	test {
	}

	production {
		// can be either "OpenID" Or "AAF".
		authentication.method = "AAF"
	}

	staging {
		// can be either "OpenID" Or "AAF".
		authentication.method = "AAF"
	}

	qa {
		// can be either "OpenID" Or "AAF".
		authentication.method = "AAF"
	}
}