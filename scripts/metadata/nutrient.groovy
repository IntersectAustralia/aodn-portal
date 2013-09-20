// Extract Nutrient Sample from CSV file
// args[0]: CSV file path
// args[1]: Database name(generated from grails environment)
import static Constants.*
import groovy.sql.Sql

class Constants {
    // Nutrient Survey Data Schema
    // Date,Time,Latitude,Longitude,Water temperature,Sp Conductivity,Salinity,Secci Depth,Turbid+,PO4R,PO4R-P,TSS(Photo),Si,PO4,TP,Nitrite,Nitrate,NH4-N,TN,TSS (Grav),AFDW,Weathr Station Site,24 hr Rain,72 hr Rain,Tide,Cloud,Wind Direction,Wind Speed,eta,pH,Chlorophyll,Dosat,DO,Sampling Run comment,Code comment,VertLoc comment
    static final int                 DATE = 0
    static final int                 TIME = 1
    static final int             LATITUDE = 2
    static final int            LONGITUDE = 3
    static final int    WATER_TEMPERATURE = 4
    static final int      SP_CONDUCTIVITY = 5
    static final int             SALINITY = 6
    static final int          SECCI_DEPTH = 7
    static final int        TURBIDITYPLUS = 8
    static final int                 PO4R = 9
    static final int               PO4R_P = 10
    static final int            TSS_PHOTO = 11
    static final int                   SI = 12
    static final int                  PO4 = 13
    static final int                   TP = 14
    static final int              NITRITE = 15
    static final int              NITRATE = 16
    static final int                NH4_N = 17
    static final int                   TN = 18
    static final int             TSS_GRAV = 19
    static final int                 AFDW = 20
    static final int WEATHER_STATION_SITE = 21
    static final int         RAINFALL_24H = 22
    static final int         RAINFALL_72H = 23
    static final int                 TIDE = 24
    static final int                CLOUD = 25
    static final int       WIND_DIRECTION = 26
    static final int           WIND_SPEED = 27
    static final int                  ETA = 28
    static final int                   PH = 29
    static final int          CHLOROPHYLL = 30
    static final int                DOSAT = 31
    static final int                   DO = 32
    static final int SAMPLING_RUN_COMMENT = 33
    static final int         CODE_COMMENT = 34
    static final int      VERTLOC_COMMENT = 35
    }

db = [url:"jdbc:postgresql://localhost:5432/${args[1]}", user:'aodn', password:'aodn', driver:'org.postgresql.Driver']
sql = Sql.newInstance(db.url, db.user, db.password, db.driver)

phenomena = [
    0, 0, 0, 0,
    'urn:ogc:def:phenomenon:OGC:1.0.30:watertemperature',
    'urn:ogc:def:phenomenon:OGC:1.0.30:conductivity',
    'urn:ogc:def:phenomenon:OGC:1.0.30:salinity',
    'urn:ogc:def:phenomenon:OGC:1.0.30:depth',
    'urn:ogc:def:phenomenon:OGC:1.0.30:turbidity',
    'urn:ogc:def:phenomenon:OGC:1.0.30:phosphater',
    'urn:ogc:def:phenomenon:OGC:1.0.30:phosphaterp',
    'urn:ogc:def:phenomenon:OGC:1.0.30:totalsuspendedsolidsphoto',
    'urn:ogc:def:phenomenon:OGC:1.0.30:si',
    'urn:ogc:def:phenomenon:OGC:1.0.30:phosphate',
    'urn:ogc:def:phenomenon:OGC:1.0.30:tp',
    'urn:ogc:def:phenomenon:OGC:1.0.30:nitrite',
    'urn:ogc:def:phenomenon:OGC:1.0.30:nitrate',
    'urn:ogc:def:phenomenon:OGC:1.0.30:ammonium',
    'urn:ogc:def:phenomenon:OGC:1.0.30:totalnitrogen',
    'urn:ogc:def:phenomenon:OGC:1.0.30:totalsuspendedsolidsgrav',
    'urn:ogc:def:phenomenon:OGC:1.0.30:afdw',
    'urn:ogc:def:phenomenon:OGC:1.0.30:weatherstationsite',
    'urn:ogc:def:phenomenon:OGC:1.0.30:rainfall24h',
    'urn:ogc:def:phenomenon:OGC:1.0.30:rainfall72h',
    'urn:ogc:def:phenomenon:OGC:1.0.30:tide',
    'urn:ogc:def:phenomenon:OGC:1.0.30:cloud',
    'urn:ogc:def:phenomenon:OGC:1.0.30:winddirection',
    'urn:ogc:def:phenomenon:OGC:1.0.30:windspeed',
    'urn:ogc:def:phenomenon:OGC:1.0.30:eta',
    'urn:ogc:def:phenomenon:OGC:1.0.30:acidity',
    'urn:ogc:def:phenomenon:OGC:1.0.30:chlorophylla',
    'urn:ogc:def:phenomenon:OGC:1.0.30:dosat',
    'urn:ogc:def:phenomenon:OGC:1.0.30:do',
    'urn:ogc:def:phenomenon:OGC:1.0.30:sampleruncomment',
    'urn:ogc:def:phenomenon:OGC:1.0.30:codecomment',
    'urn:ogc:def:phenomenon:OGC:1.0.30:vertloccomment'
]
phenomenaDataType = [
    '', '', '', '',
    'numericType',
    'numericType',
    'numericType',
    'numericType',
    'numericType',
    'numericType',
    'numericType',
    'numericType',
    'numericType',
    'numericType',
    'numericType',
    'numericType',
    'numericType',
    'numericType',
    'numericType',
    'numericType',
    'numericType',
    'textType',
    'numericType',
    'numericType',
    'textType',
    'textType',
    'textType',
    'numericType',
    'numericType',
    'numericType',
    'numericType',
    'numericType',
    'numericType',
    'textType',
    'textType',
    'textType'
]

def lines = [:]
def csvfile = new File(args[0])
int startLineIndex = Integer.parseInt(args[2])
int endLineIndex = Integer.parseInt(args[3])
int numOfFailedInsertRecords = 0
csvfile.eachLine { line, index ->

	// use while loop to stimulate continue in groovy.
	// Break when 1) any exception thrown, 2) finish process every line of given file.
	while(true) {

		if(index >= startLineIndex && index < endLineIndex) {

			if (index == 1) { // header line
				// Validate schema
				if (validateSchema(line.split(',')) == false) {
					sql.close()
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
					sql.close()
					System.err << "ERROR: geom data error at line: ${index}"
					System.exit(-1)
				}

				if (!findFoi(foiId)) {
					try {
						addFoi(foiId, values)
					}
					catch(Exception e) {
						//recover(lines)
						//System.err << "ERROR: Duplicate feature of interest at line: ${index}"
						//System.exit(-1) // Duplicate feature of interest error number: -3
						numOfFailedInsertRecords ++
						break
					}
				}

				try {
					// validation against Time.
					String time = "${values[TIME]}"
					if (!time || "24:00".compareTo(time) < 0 ) {
						recover(lines)
						sql.close()
						System.err << "ERROR: Invalid time value at line: ${index}"
						System.exit(-5) // Invalid time value error number: -5
					} else {
						addToFoi(foiId, values)
					}
				}
				catch(Exception e) {
					//recover(lines)
					//System.err << "ERROR: Duplicate observation at line: ${index}"
					//System.exit(-1) // Duplicate observation error number: -4
					numOfFailedInsertRecords ++
					break
				}

				lines.put(index, values)
			}
		}
		break
	}
}

sql.close()

if(numOfFailedInsertRecords == endLineIndex - startLineIndex) {
	System.exit(-1000) // quit exceptionally if non record is inserted
} else {
	System.exit(0) // quit normally
}

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
    // Date,Time,Latitude,Longitude,Water temperature,Sp Conductivity,Salinity,Secci Depth,Turbid+,PO4R,PO4R-P,TSS(Photo),Si,PO4,TP,Nitrite,Nitrate,NH4-N,TN,TSS (Grav),AFDW,Weather Station Site,24 hr Rain,72 hr Rain,Tide,Cloud,Wind Direction,Wind Speed,eta,pH,Chlorophyll,Dosat,DO,Sampling Run comment,Code comment,VertLoc comment
    assertion = true
    assertion = assertion && (attrs[DATE].equalsIgnoreCase('Date')) \
    && (attrs[TIME].equalsIgnoreCase('Time')) \
    && (attrs[LATITUDE].equalsIgnoreCase('Latitude')) \
    && (attrs[LONGITUDE].equalsIgnoreCase('Longitude')) \
    && (attrs[WATER_TEMPERATURE].equalsIgnoreCase('Water temperature')) \
    && (attrs[SP_CONDUCTIVITY].equalsIgnoreCase('Sp Conductivity')) \
    && (attrs[SALINITY].equalsIgnoreCase('Salinity')) \
    && (attrs[SECCI_DEPTH].equalsIgnoreCase('Secci Depth')) \
    && (attrs[TURBIDITYPLUS].equalsIgnoreCase('Turbid+')) \
    && (attrs[PO4R].equalsIgnoreCase('PO4R')) \
    && (attrs[PO4R_P].equalsIgnoreCase('PO4R-P')) \
    && (attrs[TSS_PHOTO].equalsIgnoreCase('TSS(Photo)')) \
    && (attrs[SI].equalsIgnoreCase('Si')) \
    && (attrs[PO4].equalsIgnoreCase('PO4')) \
    && (attrs[TP].equalsIgnoreCase('TP')) \
    && (attrs[NITRITE].equalsIgnoreCase('Nitrite')) \
    && (attrs[NITRATE].equalsIgnoreCase('Nitrate')) \
    && (attrs[NH4_N].equalsIgnoreCase('NH4-N')) \
    && (attrs[TN].equalsIgnoreCase('TN')) \
    && (attrs[TSS_GRAV].equalsIgnoreCase('TSS (Grav)')) \
    && (attrs[AFDW].equalsIgnoreCase('AFDW')) \
    && (attrs[WEATHER_STATION_SITE].equalsIgnoreCase('Weather Station Site')) \
    && (attrs[RAINFALL_24H].equalsIgnoreCase('24 hr Rain')) \
    && (attrs[RAINFALL_72H].equalsIgnoreCase('72 hr Rain')) \
    && (attrs[TIDE].equalsIgnoreCase('Tide')) \
    && (attrs[CLOUD].equalsIgnoreCase('Cloud')) \
    && (attrs[WIND_DIRECTION].equalsIgnoreCase('Wind Direction')) \
    && (attrs[WIND_SPEED].equalsIgnoreCase('Wind Speed')) \
    && (attrs[ETA].equalsIgnoreCase('eta')) \
    && (attrs[PH].equalsIgnoreCase('pH')) \
    && (attrs[CHLOROPHYLL].equalsIgnoreCase('Chlorophyll')) \
    && (attrs[DOSAT].equalsIgnoreCase('Dosat')) \
    && (attrs[DO].equalsIgnoreCase('DO')) \
    && (attrs[SAMPLING_RUN_COMMENT].equalsIgnoreCase('Sampling Run comment')) \
    && (attrs[CODE_COMMENT].equalsIgnoreCase('Code comment')) \
    && (attrs[VERTLOC_COMMENT].equalsIgnoreCase('VertLoc comment'))
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

    for (phenomenon in WATER_TEMPERATURE..VERTLOC_COMMENT) {
        if (phenomenaDataType[phenomenon] == 'numericType') {
            sql.execute("INSERT INTO observation (time_stamp, procedure_id, feature_of_interest_id, phenomenon_id, offering_id, numeric_value) VALUES (to_timestamp('" + timestamp + "', 'DD/MM/YYYY HH24:MI:SS'), 'urn:ogc:object:feature:Sensor:IFGI:ifgi-sensor-1', '" + foiId + "','" + phenomena[phenomenon] + "', 'GAUGE_HEIGHT', '" + (attrs[phenomenon] ?: 0) + "')")
        }
        else if (phenomenaDataType[phenomenon] == 'textType') {
            sql.execute("INSERT INTO observation (time_stamp, procedure_id, feature_of_interest_id, phenomenon_id, offering_id, text_value) VALUES (to_timestamp('" + timestamp + "', 'DD/MM/YYYY HH24:MI:SS'), 'urn:ogc:object:feature:Sensor:IFGI:ifgi-sensor-1', '" + foiId + "','" + phenomena[phenomenon] + "', 'GAUGE_HEIGHT', '" + (attrs[phenomenon] ?: '') + "')")
        }
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

	for (phenomenon in WATER_TEMPERATURE..VERTLOC_COMMENT) {
		sql.execute("DELETE FROM observation WHERE time_stamp=to_timestamp('" + timestamp + "', 'DD/MM/YYYY HH24:MI:SS') and procedure_id='urn:ogc:object:feature:Sensor:IFGI:ifgi-sensor-1' and feature_of_interest_id='" + foiId + "' and phenomenon_id='" + phenomena[phenomenon] + "' and offering_id='GAUGE_HEIGHT'")
	}
}