-- http://mmisw.org/orr/#http://mmisw.org/ont/ioos/parameter

-- sample procedure
INSERT INTO procedure VALUES ('urn:ogc:object:feature:Sensor:IFGI:ifgi-sensor-1', 'standard/ifgi-sensor-1.xml', 'text/xml;subtype="SensorML/1.0.1"');

-- sample offering
INSERT INTO offering VALUES ('GAUGE_HEIGHT','The water level in a river');

--sample proc_off relationship
INSERT INTO proc_off VALUES ('urn:ogc:object:feature:Sensor:IFGI:ifgi-sensor-1','GAUGE_HEIGHT');

-- water temperature
INSERT INTO phenomenon VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:watertemperature', 'water temperature', 'celcius','numericType');
INSERT INTO phen_off VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:watertemperature','GAUGE_HEIGHT');
INSERT INTO proc_phen VALUES ('urn:ogc:object:feature:Sensor:IFGI:ifgi-sensor-1','urn:ogc:def:phenomenon:OGC:1.0.30:watertemperature');

-- conductivity
INSERT INTO phenomenon VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:conductivity', 'conductivity', 'siemens | uS','numericType');
INSERT INTO phen_off VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:conductivity','GAUGE_HEIGHT');
INSERT INTO proc_phen VALUES ('urn:ogc:object:feature:Sensor:IFGI:ifgi-sensor-1','urn:ogc:def:phenomenon:OGC:1.0.30:conductivity');

-- salinity
INSERT INTO phenomenon VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:salinity', 'salinity', 'ppt','numericType');
INSERT INTO phen_off VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:salinity','GAUGE_HEIGHT');
INSERT INTO proc_phen VALUES ('urn:ogc:object:feature:Sensor:IFGI:ifgi-sensor-1','urn:ogc:def:phenomenon:OGC:1.0.30:salinity');

-- depth
INSERT INTO phenomenon VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:depth', 'depth', 'meter','numericType');
INSERT INTO phen_off VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:depth','GAUGE_HEIGHT');
INSERT INTO proc_phen VALUES ('urn:ogc:object:feature:Sensor:IFGI:ifgi-sensor-1','urn:ogc:def:phenomenon:OGC:1.0.30:depth');

-- turbidity
INSERT INTO phenomenon VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:turbidity', 'turbidity', 'NTU','numericType');
INSERT INTO phen_off VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:turbidity','GAUGE_HEIGHT');
INSERT INTO proc_phen VALUES ('urn:ogc:object:feature:Sensor:IFGI:ifgi-sensor-1','urn:ogc:def:phenomenon:OGC:1.0.30:turbidity');

-- battery voltage
INSERT INTO phenomenon VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:batteryvoltage', 'battery voltage', 'volts','numericType');
INSERT INTO phen_off VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:batteryvoltage','GAUGE_HEIGHT');
INSERT INTO proc_phen VALUES ('urn:ogc:object:feature:Sensor:IFGI:ifgi-sensor-1','urn:ogc:def:phenomenon:OGC:1.0.30:batteryvoltage');

-- ph
INSERT INTO phenomenon VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:ph', 'pH', 'pH units','numericType');
INSERT INTO phen_off VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:ph','GAUGE_HEIGHT');
INSERT INTO proc_phen VALUES ('urn:ogc:object:feature:Sensor:IFGI:ifgi-sensor-1','urn:ogc:def:phenomenon:OGC:1.0.30:ph');

-- chlorophyll_a
INSERT INTO phenomenon VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:chlorophylla', 'chlorophyll_a', 'ug/L','numericType');
INSERT INTO phen_off VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:chlorophylla','GAUGE_HEIGHT');
INSERT INTO proc_phen VALUES ('urn:ogc:object:feature:Sensor:IFGI:ifgi-sensor-1','urn:ogc:def:phenomenon:OGC:1.0.30:chlorophylla');

-- chlorophyll_flourescence
INSERT INTO phenomenon VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:chlorophyllflourescence', 'chlorophyll_flourescence', '','numericType');
INSERT INTO phen_off VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:chlorophyllflourescence','GAUGE_HEIGHT');
INSERT INTO proc_phen VALUES ('urn:ogc:object:feature:Sensor:IFGI:ifgi-sensor-1','urn:ogc:def:phenomenon:OGC:1.0.30:chlorophyllflourescence');

-- ODO Percent
INSERT INTO phenomenon VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:odopercent', 'ODO Percent', 'percent','numericType');
INSERT INTO phen_off VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:odopercent','GAUGE_HEIGHT');
INSERT INTO proc_phen VALUES ('urn:ogc:object:feature:Sensor:IFGI:ifgi-sensor-1','urn:ogc:def:phenomenon:OGC:1.0.30:odopercent');

-- ODO Concentration
INSERT INTO phenomenon VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:odoconcentration', 'ODO Concentration', 'ug/L','numericType');
INSERT INTO phen_off VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:odoconcentration','GAUGE_HEIGHT');
INSERT INTO proc_phen VALUES ('urn:ogc:object:feature:Sensor:IFGI:ifgi-sensor-1','urn:ogc:def:phenomenon:OGC:1.0.30:odoconcentration');

-- BP
INSERT INTO phenomenon VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:bp', 'BP', 'psi','numericType');
INSERT INTO phen_off VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:bp','GAUGE_HEIGHT');
INSERT INTO proc_phen VALUES ('urn:ogc:object:feature:Sensor:IFGI:ifgi-sensor-1','urn:ogc:def:phenomenon:OGC:1.0.30:bp');
