// Check if efdc model generates netCDF file successfully
// Call REST API to update metadata record if so
// args[0]: REST service base URL, e.g.: localhost:8080/restapi
// args[1]: action
import groovyx.net.http.RESTClient
//import groovy.util.slurpersupport.GPathResult
//import static groovyx.net.http.ContentType.URLENC

metadata = new RESTClient("https://$args[0]/")

try {
    metadata.post path : args[1]
    assert false, 'Expected exception'
}
// The exception is used for flow control but has access to the response as well:
catch(ex) {
	assert ex.response.status == 404
}