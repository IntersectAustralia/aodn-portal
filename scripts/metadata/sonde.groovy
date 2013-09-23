// Extract Sonde Data from CSV file
// args[0]: CSV file path
// args[1]: Database name(generated from grails environment)
import static Constants.*
import groovy.sql.Sql

class Constants { 
    // Sonde Survey Data Schema
    // Date,Time,Latitude,Longitude,Water temperature,Sp Conductivity,Salinity,Depth,Turbidity,Battery,pH,Chlorophyll,Chlorophyll RFU,ODO Percent,ODO Concentration,BP
    static final int              DATE = 0
    static final int              TIME = 1
    static final int          LATITUDE = 2
    static final int         LONGITUDE = 3
    static final int WATER_TEMPERATURE = 4
    static final int   SP_CONDUCTIVITY = 5
    static final int          SALINITY = 6
    static final int             DEPTH = 7
    static final int         TURBIDITY = 8
    static final int           BATTERY = 9
    static final int                PH = 10
    static final int       CHLOROPHYLL = 11
    static final int   CHLOROPHYLL_RFU = 12
    static final int       ODO_PERCENT = 13
    static final int ODO_CONCENTRATION = 14
    static final int                BP = 15
    }

db = [url:"jdbc:postgresql://localhost:5432/${args[1]}", user:'aodn', password:'aodn', driver:'org.postgresql.Driver']
sql = Sql.newInstance(db.url, db.user, db.password, db.driver)

phenomena = [0, 0, 0, 0,
             'urn:ogc:def:phenomenon:OGC:1.0.30:watertemperature',
             'urn:ogc:def:phenomenon:OGC:1.0.30:conductivity',
             'urn:ogc:def:phenomenon:OGC:1.0.30:salinity',
             'urn:ogc:def:phenomenon:OGC:1.0.30:depth',
             'urn:ogc:def:phenomenon:OGC:1.0.30:turbidity',
             'urn:ogc:def:phenomenon:OGC:1.0.30:batteryvoltage',
             'urn:ogc:def:phenomenon:OGC:1.0.30:acidity',
             'urn:ogc:def:phenomenon:OGC:1.0.30:chlorophylla',
             'urn:ogc:def:phenomenon:OGC:1.0.30:chlorophyllflourescence',
             'urn:ogc:def:phenomenon:OGC:1.0.30:odopercent',
             'urn:ogc:def:phenomenon:OGC:1.0.30:odoconcentration',
             'urn:ogc:def:phenomenon:OGC:1.0.30:bp'
            ]

def csvfile = new File(args[0])
int startLineIndex = Integer.parseInt(args[2])
int endLineIndex = Integer.parseInt(args[3])
def hasInsertRecord = false
csvfile.eachLine { line, index ->

	// use while loop to stimulate continue in groovy.
	// Break when 1) any exception thrown, 2) finish process every line of given file.
	while(true) {

		if(index >= startLineIndex && index <= endLineIndex) {

			if (index == 1) { // header line
				// Validate schema
				if (validateSchema(line.split(',')) == false) {
					sql.close()
					System.err << "ERROR: schema validation error"
					System.exit(1) // Validation error number
				}
			}

			if (index > 2) { // Ignore units line, start getting values
				if (line.endsWith(',')) line += '0'
				String[] values = line.split(',')
				String foiId

				try {
					foiId = getFoi(values)
				}
				catch(Exception e) {
					sql.close()
					System.err << "ERROR: geom data error at line: ${index}"
					System.exit(1) // Geom data error number
				}

				try {
					if (!findFoi(foiId)) {
						addFoi(foiId, values)
					}

					// validation against Time.
					String time = "${values[TIME]}"
					if (!time || "24:00".compareTo(time) < 0 ) {
						sql.close()
						System.err << "ERROR: Invalid time value at line: ${index}"
						System.exit(1)
					} else {
						addToFoi(foiId, values)
					}
				}
				catch(Exception e) {
					// ignore all duplicated exception (dc2b-148)
					break
				}

				hasInsertRecord = true
			}
		}

		// finish while loop for every line.
		break
	}
}

sql.close()

if(hasInsertRecord) {
	System.exit(0) // quit normally
} else {
	System.exit(2) // set exit code to 2 if non record is inserted
}

private Boolean validateSchema(String[] attrs) {
    // Date,Time,Latitude,Longitude,Water temperature,Sp Conductivity,Salinity,Depth,Turbidity,Battery,pH,Chlorophyll,Chlorophyll RFU,ODO Percent,ODO Concentration,BP
    assertion = true
    assertion = assertion && (attrs[DATE].equalsIgnoreCase('Date')) \
    && (attrs[TIME].equalsIgnoreCase('Time')) \
    && (attrs[LATITUDE].equalsIgnoreCase('Latitude')) \
    && (attrs[LONGITUDE].equalsIgnoreCase('Longitude')) \
    && (attrs[WATER_TEMPERATURE].equalsIgnoreCase('Water temperature')) \
    && (attrs[SP_CONDUCTIVITY].equalsIgnoreCase('Sp Conductivity')) \
    && (attrs[SALINITY].equalsIgnoreCase('Salinity')) \
    && (attrs[DEPTH].equalsIgnoreCase('Depth')) \
    && (attrs[TURBIDITY].equalsIgnoreCase('Turbidity')) \
    && (attrs[BATTERY].equalsIgnoreCase('Battery')) \
    && (attrs[PH].equalsIgnoreCase('pH')) \
    && (attrs[CHLOROPHYLL].equalsIgnoreCase('Chlorophyll')) \
    && (attrs[CHLOROPHYLL_RFU].equalsIgnoreCase('Chlorophyll RFU')) \
    && (attrs[ODO_PERCENT].equalsIgnoreCase('ODO Percent')) \
    && (attrs[ODO_CONCENTRATION].equalsIgnoreCase('ODO Concentration')) \
    && (attrs[BP].equalsIgnoreCase('BP'))
}

private String getFoi(String[] attrs) {
    sqlString = "SELECT GeometryFromText('POINT(" + (attrs[LONGITUDE] ?: 0) + " " + (attrs[LATITUDE] ?: 0) + ")', 4326) AS foi_id"

    def foiId

    sql.eachRow(sqlString) { row ->
        foiId = row.foi_id
    }

    return foiId
}

private Boolean findFoi(String foiId) {
    sqlString = "SELECT * FROM feature_of_interest WHERE feature_of_interest_id = ${foiId}"
    rows = sql.rows(sqlString)

    return rows.size() > 0
}

private void addFoi(String foiId, String[] attrs) {
    sql.execute("INSERT INTO feature_of_interest (feature_of_interest_id, feature_of_interest_name, feature_of_interest_description, geom, feature_type, schema_link) VALUES ('" + foiId + "', 'SHED', 'Sydney Harbour Environment Data', GeometryFromText('POINT(" + (attrs[LONGITUDE] ?: 0) + " " + (attrs[LATITUDE] ?: 0) + ")', 4326), 'sa:SamplingPoint', 'http://xyz.org/reference-url2.html')")
    sql.execute('INSERT INTO foi_off VALUES (?, ?)', [foiId, 'GAUGE_HEIGHT'])
    sql.execute('INSERT INTO proc_foi VALUES (?, ?)', ['urn:ogc:object:feature:Sensor:IFGI:ifgi-sensor-1', foiId])
}

private void addToFoi(String foiId, String[] attrs) {
    // INSERT INTO observation (time_stamp, procedure_id, feature_of_interest_id,phenomenon_id,offering_id,numeric_value) values ('2013-04-20 01:16', 'urn:ogc:object:feature:Sensor:IFGI:ifgi-sensor-1', 'foi_1001','urn:ogc:def:phenomenon:OGC:1.0.30:waterlevel','GAUGE_HEIGHT','50.0'); 
    // INSERT INTO quality(observation_id, quality_unit, quality_value, quality_type, quality_name) values (currval(pg_get_serial_sequence('observation','observation_id')),'mm', '1','category', 'accuracy');
    // INSERT INTO quality(observation_id, quality_unit, quality_value, quality_type, quality_name) values (currval(pg_get_serial_sequence('observation','observation_id')),'percent', '10','quantity', 'completeness');

    def timestamp = "${attrs[DATE]} ${attrs[TIME]}"

    for (phenomenon in WATER_TEMPERATURE..BP) {
        sql.execute("INSERT INTO observation (time_stamp, procedure_id, feature_of_interest_id, phenomenon_id, offering_id, numeric_value) VALUES (to_timestamp('" + timestamp + "', 'DD/MM/YYYY HH24:MI:SS'), 'urn:ogc:object:feature:Sensor:IFGI:ifgi-sensor-1', '" + foiId + "','" + phenomena[phenomenon] + "', 'GAUGE_HEIGHT', '" + (attrs[phenomenon] ?: 0) + "')")
    } 
}