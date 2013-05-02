grails.servlet.version = "2.5" // Change depending on target container compliance (2.5 or 3.0)
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.target.level = 1.6
grails.project.source.level = 1.6
grails.project.war.file = "target/${appName}-${appVersion}-${grails.util.Environment.current.name}.war"

grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // uncomment to disable ehcache
        // excludes 'ehcache'
		excludes "xml-apis"
		excludes("catalina")
//		excludes("grails-plugin-log4j")
    }
    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    checksums true // Whether to verify checksums on resolve

    repositories {
        inherits true // Whether to inherit repository definitions from plugins

        grailsPlugins()
        grailsHome()
        grailsCentral()

        // uncomment the below to enable remote dependency resolution
        // from public Maven repositories
        mavenLocal()
        mavenCentral()

        // uncomment these (or add new ones) to enable remote dependency resolution from public Maven repositories
        //mavenRepo "http://snapshots.repository.codehaus.org"
        //mavenRepo "http://repository.codehaus.org"
        //mavenRepo "http://download.java.net/maven/2/"
        //mavenRepo "http://repository.jboss.com/maven2/"
    }
    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes eg.

        // runtime 'mysql:mysql-connector-java:5.1.13'
    }
	
	// Allow grails commands to be run as usual, see: http://grails.org/doc/latest/guide/conf.html#mavenIntegration
	pom true
	plugins {
		test ":code-coverage:1.2.6"

		build ":tomcat:2.1.0"

		compile ":build-test-data:2.0.5"
		compile ":svn:1.0.2"
		compile ":shiro:1.1.3"
		compile ":quartz:0.4.2"
		compile ":jquery-mobile-scaffolding:0.1"
		compile ":jquery-mobile:1.1.0.5"
		compile ":jquery:1.8.0"
		compile ":plugin-config:0.1.8"
		compile ":mail:1.0.1"
		compile ":hibernate:2.1.0"
		compile ":cache-headers:1.1.5"
		compile ":federated-grails:0.3.1"

		runtime ":cached-resources:1.0"
		runtime ":resources:1.1.6"
		compile ":database-migration:1.1"
	}
}

coverage {
    enabledByDefault = false
    xml = true
}

grails.war.resources = { stagingDir ->
    
	delete(file:"${stagingDir}/WEB-INF/lib/catalina-6.0.32.jar")
	delete(file:"${stagingDir}/WEB-INF/lib/servlet-api-2.5.jar")
	delete(file:"${stagingDir}/WEB-INF/lib/servlet-api-6.0.32.jar")

    // The jars are being inserted by the hudson/tomcat build process, and
    // are causing errors on startup for the app on tomcat6.
    delete(file:"${stagingDir}/WEB-INF/lib/commons-collections-3.1.jar")
    delete(file:"${stagingDir}/WEB-INF/lib/slf4j-api-1.5.2.jar")
}
