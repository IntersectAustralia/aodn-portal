package au.org.emii.portal

import grails.test.ControllerUnitTestCase

import java.text.MessageFormat

/**
 * Created with IntelliJ IDEA.
 * User: htxiong
 * Date: 17/06/13
 * Time: 10:11 AM
 *
 * This is an extension of ControllerUnitTestCase, and it supports
 * unit test for i18n messages.
 *
 * More info about this solution can be found: http://jira.grails.org/browse/GRAILS-5926
 */
class ExtendedControllerUnitTestCase extends ControllerUnitTestCase {
	def props

	protected void setUp() {
		super.setUp()

		props = new Properties()
		def stream = new FileInputStream("grails-app/i18n/messages.properties")
		props.load stream
		stream.close()

		mockI18N(controller)
	}

	def mockI18N = { controller ->
		controller.metaClass.message = { Map map ->
			if (!map.code)
				return ""
			if (map.args) {
				def formatter = new MessageFormat("")
				formatter.applyPattern props.getProperty(map.code)
				return formatter.format(map.args.toArray())
			} else {
				return props.getProperty(map.code)
			}
		}
	}
}