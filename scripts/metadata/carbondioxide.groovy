// Extract Carbon Dioxide Data from CSV file
// args[0]: CSV file path
// args[1]: Database name(generated from grails environment)
import static Constants.*
import groovy.sql.Sql

class Constants { 
    // Carbon Dioxide Survey Data Schema
    // Date,Time,Latitude,Longitude,Water temperature,Salinity,CO2,RecNo,mb Ref,mbR Temp,Oxygen,Input D,Input E,Input F,Input G,Input H,ATMP,Probe Type
    static final int              DATE = 0
    static final int              TIME = 1
    static final int          LATITUDE = 2
    static final int         LONGITUDE = 3
    static final int WATER_TEMPERATURE = 4
    static final int          SALINITY = 5
    static final int               CO2 = 6
    static final int             RECNO = 7
    static final int            MB_REF = 8
    static final int          MBR_TEMP = 9
    static final int            OXYGEN = 10
    static final int           INPUT_D = 11
    static final int           INPUT_E = 12
    static final int           INPUT_F = 13
    static final int           INPUT_G = 14
    static final int           INPUT_H = 15
    static final int              ATMP = 16
    static final int        PROBE_TYPE = 17
    }

db = [url:"jdbc:postgresql://localhost:5432/${args[1]}", user:'aodn', password:'aodn', driver:'org.postgresql.Driver']
sql = Sql.newInstance(db.url, db.user, db.password, db.driver)

phenomena = [0, 0, 0, 0,
             'urn:ogc:def:phenomenon:OGC:1.0.30:watertemperature',
             'urn:ogc:def:phenomenon:OGC:1.0.30:salinity',
             'urn:ogc:def:phenomenon:OGC:1.0.30:co2',
             'urn:ogc:def:phenomenon:OGC:1.0.30:recno',
             'urn:ogc:def:phenomenon:OGC:1.0.30:mbref',
             'urn:ogc:def:phenomenon:OGC:1.0.30:mbrtemp',
             'urn:ogc:def:phenomenon:OGC:1.0.30:oxygen',
             'urn:ogc:def:phenomenon:OGC:1.0.30:inputd',
             'urn:ogc:def:phenomenon:OGC:1.0.30:inpute',
             'urn:ogc:def:phenomenon:OGC:1.0.30:inputf',
             'urn:ogc:def:phenomenon:OGC:1.0.30:inputg',
             'urn:ogc:def:phenomenon:OGC:1.0.30:inputh',
             'urn:ogc:def:phenomenon:OGC:1.0.30:atmp',
             'urn:ogc:def:phenomenon:OGC:1.0.30:probetype'
            ]

def csvfile = new File(args[0])
def lines = [:]
csvfile.eachLine { line, index ->

    if (index == 1) { // header line
        // Validate schema
        if (validateSchema(line.split(',')) == false) {
            System.err << "ERROR: schema validation error"
            System.exit(-1) // Validation error number: -1
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
			recover(lines)
            System.err << "ERROR: geom data error at line: ${index}"
            System.exit(-2) // Geom data error number: -2
        }

        if (!findFoi(foiId)) {
            try {
                addFoi(foiId, values)
            }
            catch(Exception e) {
				recover(lines)
                System.err << "ERROR: Duplicate feature of interest at line: ${index}"
                System.exit(-3) // Duplicate feature of interest error number: -3
            }
        }

        try {

			// validation against Time.
			String time = "${values[TIME]}"
			if (!time || "24:00".compareTo(time) < 0 ) {
				recover(lines)
				System.err << "ERROR: Invalid time value at line: ${index}"
				System.exit(-5) // Invalid time value error number: -5
			} else {
				addToFoi(foiId, values)
			}
        }
        catch(Exception e) {
			recover(lines)
            System.err << "ERROR: Duplicate observation at line: ${index}"
            System.exit(-4) // Duplicate observation error number: -4
        }

		lines.put(index, values)
    }

}

sql.close()
System.exit(0) // quit normally

private void recover(Map<Integer, String[]> lines) {

	for (Integer index : lines.keySet()) {

		String[] values = lines.get(index)
		String foiId

		try {
			foiId = getFoi(values)
		}
		catch(Exception e) {
		}

		try {
			removeFromFoi(foiId, values)
		}
		catch(Exception e) {
		}

		if (findFoi(foiId)) {
			try {
				removeFoi(foiId, values)
			}
			catch(Exception e) {
			}
		}
	}
}

private Boolean validateSchema(String[] attrs) {
    // Date,Time,Latitude,Longitude,Water temperature,Salinity,CO2,RecNo,mb Ref,mbR Temp,Oxygen,Input D,Input E,Input F,Input G,Input H,ATMP,Probe Type
    assertion = true
    assertion = assertion && (attrs[DATE].equalsIgnoreCase('Date')) \
    && (attrs[TIME].equalsIgnoreCase('Time')) \
    && (attrs[LATITUDE].equalsIgnoreCase('Latitude')) \
    && (attrs[LONGITUDE].equalsIgnoreCase('Longitude')) \
    && (attrs[WATER_TEMPERATURE].equalsIgnoreCase('Water temperature')) \
    && (attrs[SALINITY].equalsIgnoreCase('Salinity')) \
    && (attrs[CO2].equalsIgnoreCase('CO2')) \
    && (attrs[RECNO].equalsIgnoreCase('RecNo')) \
    && (attrs[MB_REF].equalsIgnoreCase('mb Ref')) \
    && (attrs[MBR_TEMP].equalsIgnoreCase('mbR Temp')) \
    && (attrs[OXYGEN].equalsIgnoreCase('Oxygen')) \
    && (attrs[INPUT_D].equalsIgnoreCase('Input D')) \
    && (attrs[INPUT_E].equalsIgnoreCase('Input E')) \
    && (attrs[INPUT_F].equalsIgnoreCase('Input F')) \
    && (attrs[INPUT_G].equalsIgnoreCase('Input G')) \
    && (attrs[INPUT_H].equalsIgnoreCase('Input H')) \
    && (attrs[ATMP].equalsIgnoreCase('ATMP')) \
    && (attrs[PROBE_TYPE].equalsIgnoreCase('Probe Type'))
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

    for (phenomenon in WATER_TEMPERATURE..PROBE_TYPE) {
        sql.execute("INSERT INTO observation (time_stamp, procedure_id, feature_of_interest_id, phenomenon_id, offering_id, numeric_value) VALUES (to_timestamp('" + timestamp + "', 'DD/MM/YYYY HH24:MI:SS'), 'urn:ogc:object:feature:Sensor:IFGI:ifgi-sensor-1', '" + foiId + "','" + phenomena[phenomenon] + "', 'GAUGE_HEIGHT', '" + (attrs[phenomenon] ?: 0) + "')")
    } 
}
private void removeFoi(String foiId, String[] attrs) {
	sql.execute("DELETE FROM proc_foi WHERE procedure_id='urn:ogc:object:feature:Sensor:IFGI:ifgi-sensor-1' and feature_of_interest_id='"+foiId+"'")
	sql.execute("DELETE FROM foi_off WHERE feature_of_interest_id='"+foiId+"' and offering_id='GAUGE_HEIGHT' ")
	sql.execute("DELETE FROM feature_of_interest WHERE feature_of_interest_id='"+foiId+"'")
}

private void removeFromFoi(String foiId, String[] attrs) {
	// INSERT INTO observation (time_stamp, procedure_id, feature_of_interest_id,phenomenon_id,offering_id,numeric_value) values ('2013-04-20 01:16', 'urn:ogc:object:feature:Sensor:IFGI:ifgi-sensor-1', 'foi_1001','urn:ogc:def:phenomenon:OGC:1.0.30:waterlevel','GAUGE_HEIGHT','50.0');
	// INSERT INTO quality(observation_id, quality_unit, quality_value, quality_type, quality_name) values (currval(pg_get_serial_sequence('observation','observation_id')),'mm', '1','category', 'accuracy');
	// INSERT INTO quality(observation_id, quality_unit, quality_value, quality_type, quality_name) values (currval(pg_get_serial_sequence('observation','observation_id')),'percent', '10','quantity', 'completeness');

	def timestamp = "${attrs[DATE]} ${attrs[TIME]}"

	for (phenomenon in WATER_TEMPERATURE..PROBE_TYPE) {
		sql.execute("DELETE FROM observation WHERE time_stamp=to_timestamp('" + timestamp + "', 'DD/MM/YYYY HH24:MI:SS') and procedure_id='urn:ogc:object:feature:Sensor:IFGI:ifgi-sensor-1' and feature_of_interest_id='" + foiId + "' and phenomenon_id='" + phenomena[phenomenon] + "' and offering_id='GAUGE_HEIGHT'")
	}
}
