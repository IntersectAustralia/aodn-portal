// Extract Nutrient Sample from CSV file
// args[0]: CSV file path
// args[1]: Database name(generated from grails environment)
import static Constants.*
import groovy.sql.Sql
this.class.classLoader.rootLoader.addURL(new URL("file:///usr/share/java/postgresql-jdbc-8.4.701.jar"))

class Constants { 
    // Nutrient Survey Data Schema
    // Date,Time,Latitude,Longitude,Water temperature,Sp Conductivity,Salinity,Secci Depth,Turbid+,PO4R,PO4R-P,TSS(Photo),Si,PO4,TP,Nitrite,Nitrate,NH4-N,TN,TSS (Grav),AFDW,Weathr Station Site,24 hr Rain,72 hr Rain,Tide,Cloud,Wind Direction,Wind Speed,eta,pH,Chlorophyll,Dosat,DO,Sampling Run comment,Code comment,VertLoc comment
    static final int                  DATE = 0
    static final int                  TIME = 1
    static final int              LATITUDE = 2
    static final int             LONGITUDE = 3
    static final int     WATER_TEMPERATURE = 4
    static final int       SP_CONDUCTIVITY = 5
    static final int              SALINITY = 6
    static final int           SECCI_DEPTH = 7
    static final int         TURBIDITYPLUS = 8
    static final int                  PO4R = 9
    static final int                PO4R_P = 10
    static final int             TSS_PHOTO = 11
    static final int                    SI = 12
    static final int                   PO4 = 13
    static final int                    TP = 14
    static final int               NITRITE = 15
    static final int               NITRATE = 16
    static final int                 NH4_N = 17
    static final int                    TN = 18
    static final int              TSS_GRAV = 19
    static final int                  AFDW = 20
    static final int  WEATHER_STATION_SITE = 21
    static final int          RAINFALL_24H = 22
    static final int          RAINFALL_72H = 23
    static final int                  TIDE = 24
    static final int                 CLOUD = 25
    static final int        WIND_DIRECTION = 26
    static final int            WIND_SPEED = 27
    static final int                   ETA = 28
    static final int                    PH = 29
    static final int           CHLOROPHYLL = 30
    static final int                 DOSTA = 31
    static final int                    DO = 32
    static final int SAMPLEING_RUN_COMMENT = 33
    static final int          CODE_COMMENT = 34
    static final int       VERTLOC_COMMENT = 35
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
             'urn:ogc:def:phenomenon:OGC:1.0.30:ph',
             'urn:ogc:def:phenomenon:OGC:1.0.30:chlorophylla',
             'urn:ogc:def:phenomenon:OGC:1.0.30:chlorophyllflourescence',
             'urn:ogc:def:phenomenon:OGC:1.0.30:odopercent',
             'urn:ogc:def:phenomenon:OGC:1.0.30:odoconcentration',
             'urn:ogc:def:phenomenon:OGC:1.0.30:bp'
            ]

def csvfile = new File(args[0])
csvfile.eachLine { line, index ->

    if (index == 1) { // header line
        // Validate schema
        if (validateSchema(line.split(',')) == false) {
            System.exit(-1)
        }
    }

    if (index > 2) { // Ignore units line, start getting values
        String[] values = line.split(',')
        String foiId = getFoi(values)

        if (!findFoi(foiId)) {
            addFoi(foiId, values)
        }

        addToFoi(foiId, values)
    }

}

sql.close()
System.exit(0) // quit normally

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
        sql.execute("INSERT INTO observation (time_stamp, procedure_id, feature_of_interest_id, phenomenon_id, offering_id, numeric_value) VALUES ('" + timestamp + "', 'urn:ogc:object:feature:Sensor:IFGI:ifgi-sensor-1', '" + foiId + "','" + phenomena[phenomenon] + "', 'GAUGE_HEIGHT', '" + (attrs[phenomenon] ?: 0) + "')")
    } 
}