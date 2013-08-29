
/*
 * Copyright 2012 IMOS
 *
 * The AODN/IMOS Portal is distributed under the terms of the GNU General Public License
 *
 */

import javax.naming.InitialContext

// locations to search for config files that get merged into the main config
// config files can either be Java properties files or ConfigSlurper scripts

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if(System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }
grails.config.locations = [AuthenticationMethod]

grails.project.groupId = appName // change this to alter the default package name and Maven publishing destination
grails.mime.file.extensions = true // enables the parsing of file extensions from URLs into the request format
grails.mime.use.accept.header = false
grails.mime.types = [ html: ['text/html','application/xhtml+xml'],
                      xml: ['text/xml', 'application/xml'],
                      text: 'text/plain',
                      js: 'text/javascript',
                      rss: 'application/rss+xml',
                      atom: 'application/atom+xml',
                      css: 'text/css',
                      csv: 'text/csv',
                      all: '*/*',
                      json: ['application/json','text/json'],
                      form: 'application/x-www-form-urlencoded',
                      multipartForm: 'multipart/form-data'
                    ]

// The default codec used to encode data with ${}
grails.views.default.codec = "none" // none, html, base64
grails.views.gsp.encoding = "UTF-8"
grails.converters.encoding = "UTF-8"
// enable Sitemesh preprocessing of GSP pages
grails.views.gsp.sitemesh.preprocess = true
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// This is required to avoid org.codehaus.groovy.grails.web.json.JSONException: Misplaced key.
grails.converters.json.circular.reference.behaviour = "INSERT_NULL"
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// whether to install the java.util.logging bridge for sl4j. Disable for AppEngine!
grails.logging.jul.usebridge = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password']

// Database migration.
grails.plugin.databasemigration.updateOnStart = true
grails.plugin.databasemigration.updateOnStartFileNames = ['changelog.groovy']

// Portal help site
help.url = "http://staff.ask.sydney.edu.au/app/answers/detail/a_id/761"

// AODAAC Aggregator
aodaacAggregator {
	url = "http://aodaac.emii.org.au/"
	environment = "prod"
	idleJobTimeout = 1 // In hours
	errorLookup = [
		/.*java\.lang\.Exception: requested ~ [0-9]+ bytes; limit = [0-9]+/: {

			errorMessage ->

			def numBytes = (errorMessage =~ /[0-9]+/)
			assert(numBytes.count == 2): "Expecting 2 numerical values in error string: " + errorMessage
			def actualBytes = Long.valueOf(numBytes[0])
			def limitBytes = Long.valueOf(numBytes[1])

			def amountOver = Math.round(actualBytes/limitBytes)

			return "The requested job will have too much data. You have requested roughly ${amountOver} times the maximum output size."
		}
	]
}

// Depth service
depthService.url = "http://depthservice.aodn.org.au/depth"

// set per-environment serverURL stem for creating absolute links
environments {

    development {
		grails.resources.debug = true

        // URLs
		grails.serverURL = "http://localhost:8080/$appName"
		spatialsearch.url = "http://geo.intersect.org.au:8080/geonetwork/srv/eng/q"
		wmsScanner.url = "http://staging.dc2b.intersect.org.au/wmsscanner/"

	    facetedSearch.enabled = false

        grails {
            mail {
                host = "localhost"
                port = 25
                username = "username"
                props = ["mail.smtp.auth":"false"]
            }
        }
    }

    test {

        // URLs
        grails.serverURL = "http://localhost:8080/$appName"

        grails.mail.disabled = true

        grails.plugin.databasemigration.updateOnStart = false
    }

    production {

	    // URLs
		grails.serverURL = "https://sho.sydney.edu.au"
		spatialsearch.url = "https://sho.sydney.edu.au/geonetwork/srv/eng/q"
		wmsScanner.url = "https://sho.sydney.edu.au/wmsscanner/"
		wfsScanner.url = "https://sho.sydney.edu.au/wfsscanner/"
	    facetedSearch.enabled = false
        grails {
            mail {
                host = "localhost"
                port = 25
                props = ["mail.smtp.auth":"false"]
            }
        }
    }

    uat {

	    // URLs
		grails.serverURL = "http://uat.sho.sydney.edu.au"
		spatialsearch.url = "http://uat.sho.sydney.edu.au/geonetwork/srv/eng/q"
		wmsScanner.url = "http://uat.sho.sydney.edu.au/wmsscanner/"
		wfsScanner.url = "http://uat.sho.sydney.edu.au/wfsscanner/"
	    facetedSearch.enabled = false
        grails {
            mail {
                host = "localhost"
                port = 25
                props = ["mail.smtp.auth":"false"]
            }
        }
    }

	staging {
		// URLs
		grails.serverURL = "http://staging.dc2b.intersect.org.au"
		spatialsearch.url = "http://geo.intersect.org.au:8080/geonetwork/srv/eng/q"
		wmsScanner.url = "http://staging.dc2b.intersect.org.au/wmsscanner/"
		wfsScanner.url = "http://staging.dc2b.intersect.org.au/wfsscanner/"
		facetedSearch.enabled = false
		grails {
			mail {
				host = "localhost"
				port = 25
				props = ["mail.smtp.auth":"false"]
			}
		}
	}

    qa {

		// URLs
		grails.serverURL = "http://qa.dc2b.intersect.org.au"
		spatialsearch.url = "http://geo.intersect.org.au:8080/geonetwork/srv/eng/q"
		wmsScanner.url = "http://staging.dc2b.intersect.org.au/wmsscanner/"
		wfsScanner.url = "http://staging.dc2b.intersect.org.au/wfsscanner/"

        facetedSearch.enabled = false

        grails {
            mail {
                host = "localhost"
                port = 25
                props = ["mail.smtp.auth":"false"]
            }
        }

    }
}

def defaultInstanceName = "SHO"

portal {
	header {
		logo = "${defaultInstanceName}_logo.png"
		externalLinks = [
		    [
				linkText: "eMII",
				tooltipText: "e-Marine Information Infrastructure",
				href: "http://www.emii.org.au"
			],
		    [
				linkText: "AODN",
				tooltipText: "Australian Ocean Data Network",
				href: "http://imos.org.au/aodn.html"
			],
		    [
				linkText: "Help",
				tooltipText: "Portal help files",
				href: help.url
			]
		]
	}
	// Change authentication emails for IMOS
	systemEmail {
		fromAddress = "info@example.com"
	}
}

/**
 * Instance specific customisation, clearly stolen from:
 * http://phatness.com/2010/03/how-to-externalize-your-grails-configuration/
 *
 * To use set for a specific instance, either set the environment variable "INSTANCE_NAME", or add this in the grails
 * commandline like so:
 *
 * grails -DINSTANCE_NAME=WA run-app
 *
 * Instance specific config files are located in $project_home/instances/
 *
 * Any configuration found in these instance specific file will OVERRIDE values set in Config.groovy and
 * application.properties.
 *
 * NOTE: app.name and version is ignored in external application.properties
 */
if(!grails.config.locations || !(grails.config.locations instanceof List)) {
    grails.config.locations = []
}

try {
	configurationPath = new InitialContext().lookup('java:comp/env/aodn.configuration')
	grails.config.locations << "file:${configurationPath}"

	def startupConfig = new ConfigSlurper(grailsSettings.grailsEnv).parse(new File(configurationPath).toURI().toURL())
	System.setProperty("INSTANCE_NAME", startupConfig.portal.instance.name ?: defaultInstanceName)
}
catch (e) {
	portal.instance.name = defaultInstanceName
	System.setProperty "INSTANCE_NAME", portal.instance.name
}

log4j = {

	appenders {
        'null' name: "stacktrace"
    }
    
    debug  'grails.app'
}