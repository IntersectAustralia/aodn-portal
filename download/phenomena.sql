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

-- acidity
INSERT INTO phenomenon VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:acidity', 'pH', 'pH units','numericType');
INSERT INTO phen_off VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:acidity','GAUGE_HEIGHT');
INSERT INTO proc_phen VALUES ('urn:ogc:object:feature:Sensor:IFGI:ifgi-sensor-1','urn:ogc:def:phenomenon:OGC:1.0.30:acidity');

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

-- PO4R
INSERT INTO phenomenon VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:phosphater', 'PO4R', 'mg/L-PO4','numericType');
INSERT INTO phen_off VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:phosphater','GAUGE_HEIGHT');
INSERT INTO proc_phen VALUES ('urn:ogc:object:feature:Sensor:IFGI:ifgi-sensor-1','urn:ogc:def:phenomenon:OGC:1.0.30:phosphater');

-- PO4R-P
INSERT INTO phenomenon VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:phosphaterp', 'PO4R-P', 'mg/L-P','numericType');
INSERT INTO phen_off VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:phosphaterp','GAUGE_HEIGHT');
INSERT INTO proc_phen VALUES ('urn:ogc:object:feature:Sensor:IFGI:ifgi-sensor-1','urn:ogc:def:phenomenon:OGC:1.0.30:phosphaterp');

-- TSS (Photo)
INSERT INTO phenomenon VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:totalsuspendedsolidsphoto', 'Total Suspended Solids (Photo)', 'mg/L','numericType');
INSERT INTO phen_off VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:totalsuspendedsolidsphoto','GAUGE_HEIGHT');
INSERT INTO proc_phen VALUES ('urn:ogc:object:feature:Sensor:IFGI:ifgi-sensor-1','urn:ogc:def:phenomenon:OGC:1.0.30:totalsuspendedsolidsphoto');

-- Si
INSERT INTO phenomenon VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:si', 'Si', 'mg/L-Si','numericType');
INSERT INTO phen_off VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:si','GAUGE_HEIGHT');
INSERT INTO proc_phen VALUES ('urn:ogc:object:feature:Sensor:IFGI:ifgi-sensor-1','urn:ogc:def:phenomenon:OGC:1.0.30:si');

-- PO4
INSERT INTO phenomenon VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:phosphate', 'PO4', 'mg/L-P','numericType');
INSERT INTO phen_off VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:phosphate','GAUGE_HEIGHT');
INSERT INTO proc_phen VALUES ('urn:ogc:object:feature:Sensor:IFGI:ifgi-sensor-1','urn:ogc:def:phenomenon:OGC:1.0.30:phosphate');

-- TP
INSERT INTO phenomenon VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:tp', 'TP', 'mg/L-P','numericType');
INSERT INTO phen_off VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:tp','GAUGE_HEIGHT');
INSERT INTO proc_phen VALUES ('urn:ogc:object:feature:Sensor:IFGI:ifgi-sensor-1','urn:ogc:def:phenomenon:OGC:1.0.30:tp');

-- Nitrite
INSERT INTO phenomenon VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:nitrite', 'Nitrite', 'ug L-1 as N','numericType');
INSERT INTO phen_off VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:nitrite','GAUGE_HEIGHT');
INSERT INTO proc_phen VALUES ('urn:ogc:object:feature:Sensor:IFGI:ifgi-sensor-1','urn:ogc:def:phenomenon:OGC:1.0.30:nitrite');

-- Nitrate
INSERT INTO phenomenon VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:nitrate', 'Nitrate', 'mg/L-N','numericType');
INSERT INTO phen_off VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:nitrate','GAUGE_HEIGHT');
INSERT INTO proc_phen VALUES ('urn:ogc:object:feature:Sensor:IFGI:ifgi-sensor-1','urn:ogc:def:phenomenon:OGC:1.0.30:nitrate');

-- NH4-N
INSERT INTO phenomenon VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:ammonium', 'NH4-N', 'ug/L-N','numericType');
INSERT INTO phen_off VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:ammonium','GAUGE_HEIGHT');
INSERT INTO proc_phen VALUES ('urn:ogc:object:feature:Sensor:IFGI:ifgi-sensor-1','urn:ogc:def:phenomenon:OGC:1.0.30:ammonium');

-- TN
INSERT INTO phenomenon VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:totalnitrogen', 'Total Nitrogen', 'ug/L-N','numericType');
INSERT INTO phen_off VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:totalnitrogen','GAUGE_HEIGHT');
INSERT INTO proc_phen VALUES ('urn:ogc:object:feature:Sensor:IFGI:ifgi-sensor-1','urn:ogc:def:phenomenon:OGC:1.0.30:totalnitrogen');

-- TSS (Grav)
INSERT INTO phenomenon VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:totalsuspendedsolidsgrav', 'Total Suspended Solids (Grav)', 'mg/L','numericType');
INSERT INTO phen_off VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:totalsuspendedsolidsgrav','GAUGE_HEIGHT');
INSERT INTO proc_phen VALUES ('urn:ogc:object:feature:Sensor:IFGI:ifgi-sensor-1','urn:ogc:def:phenomenon:OGC:1.0.30:totalsuspendedsolidsgrav');

-- AFDW
INSERT INTO phenomenon VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:afdw', 'AFDW', 'mg/L','numericType');
INSERT INTO phen_off VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:afdw','GAUGE_HEIGHT');
INSERT INTO proc_phen VALUES ('urn:ogc:object:feature:Sensor:IFGI:ifgi-sensor-1','urn:ogc:def:phenomenon:OGC:1.0.30:afdw');

-- Weather Station Site
INSERT INTO phenomenon VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:weatherstationsite', 'Weather Station Site', '','textType');
INSERT INTO phen_off VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:weatherstationsite','GAUGE_HEIGHT');
INSERT INTO proc_phen VALUES ('urn:ogc:object:feature:Sensor:IFGI:ifgi-sensor-1','urn:ogc:def:phenomenon:OGC:1.0.30:weatherstationsite');

-- 24 Hour Rainfall
INSERT INTO phenomenon VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:rainfall24h', '24 Hour Rainfall', 'mm','numericType');
INSERT INTO phen_off VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:rainfall24h','GAUGE_HEIGHT');
INSERT INTO proc_phen VALUES ('urn:ogc:object:feature:Sensor:IFGI:ifgi-sensor-1','urn:ogc:def:phenomenon:OGC:1.0.30:rainfall24h');

-- 72 Hour Rainfall
INSERT INTO phenomenon VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:rainfall72h', '72 Hour Rainfall', 'mm','numericType');
INSERT INTO phen_off VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:rainfall72h','GAUGE_HEIGHT');
INSERT INTO proc_phen VALUES ('urn:ogc:object:feature:Sensor:IFGI:ifgi-sensor-1','urn:ogc:def:phenomenon:OGC:1.0.30:rainfall72h');

-- Tide
INSERT INTO phenomenon VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:tide', 'Tide', '','textType');
INSERT INTO phen_off VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:tide','GAUGE_HEIGHT');
INSERT INTO proc_phen VALUES ('urn:ogc:object:feature:Sensor:IFGI:ifgi-sensor-1','urn:ogc:def:phenomenon:OGC:1.0.30:tide');

-- Cloud
INSERT INTO phenomenon VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:cloud', 'Cloud', '','textType');
INSERT INTO phen_off VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:cloud','GAUGE_HEIGHT');
INSERT INTO proc_phen VALUES ('urn:ogc:object:feature:Sensor:IFGI:ifgi-sensor-1','urn:ogc:def:phenomenon:OGC:1.0.30:cloud');

-- Wind Direction
INSERT INTO phenomenon VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:winddirection', 'Wind Direction', '','textType');
INSERT INTO phen_off VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:winddirection','GAUGE_HEIGHT');
INSERT INTO proc_phen VALUES ('urn:ogc:object:feature:Sensor:IFGI:ifgi-sensor-1','urn:ogc:def:phenomenon:OGC:1.0.30:winddirection');

-- Wind Speed
INSERT INTO phenomenon VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:windspeed', 'Wind Speed', 'Knots','numericType');
INSERT INTO phen_off VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:windspeed','GAUGE_HEIGHT');
INSERT INTO proc_phen VALUES ('urn:ogc:object:feature:Sensor:IFGI:ifgi-sensor-1','urn:ogc:def:phenomenon:OGC:1.0.30:windspeed');

-- eta
INSERT INTO phenomenon VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:eta', 'eta', 'm(DATUM)','numericType');
INSERT INTO phen_off VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:eta','GAUGE_HEIGHT');
INSERT INTO proc_phen VALUES ('urn:ogc:object:feature:Sensor:IFGI:ifgi-sensor-1','urn:ogc:def:phenomenon:OGC:1.0.30:eta');

-- Dosat
INSERT INTO phenomenon VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:dosat', 'Dosat', 'percent','numericType');
INSERT INTO phen_off VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:dosat','GAUGE_HEIGHT');
INSERT INTO proc_phen VALUES ('urn:ogc:object:feature:Sensor:IFGI:ifgi-sensor-1','urn:ogc:def:phenomenon:OGC:1.0.30:dosat');

-- DO
INSERT INTO phenomenon VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:do', 'DO', 'mg/L','numericType');
INSERT INTO phen_off VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:do','GAUGE_HEIGHT');
INSERT INTO proc_phen VALUES ('urn:ogc:object:feature:Sensor:IFGI:ifgi-sensor-1','urn:ogc:def:phenomenon:OGC:1.0.30:do');

-- Sample Run Comment
INSERT INTO phenomenon VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:sampleruncomment', 'Sample Run Comment', '','textType');
INSERT INTO phen_off VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:sampleruncomment','GAUGE_HEIGHT');
INSERT INTO proc_phen VALUES ('urn:ogc:object:feature:Sensor:IFGI:ifgi-sensor-1','urn:ogc:def:phenomenon:OGC:1.0.30:sampleruncomment');

-- Code Comment
INSERT INTO phenomenon VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:codecomment', 'Code Comment', '','textType');
INSERT INTO phen_off VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:codecomment','GAUGE_HEIGHT');
INSERT INTO proc_phen VALUES ('urn:ogc:object:feature:Sensor:IFGI:ifgi-sensor-1','urn:ogc:def:phenomenon:OGC:1.0.30:codecomment');

-- VertLoc Comment
INSERT INTO phenomenon VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:vertloccomment', 'VertLoc Comment', '','textType');
INSERT INTO phen_off VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:vertloccomment','GAUGE_HEIGHT');
INSERT INTO proc_phen VALUES ('urn:ogc:object:feature:Sensor:IFGI:ifgi-sensor-1','urn:ogc:def:phenomenon:OGC:1.0.30:vertloccomment');

-- Plot
INSERT INTO phenomenon VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:plot', 'Plot', '','numericType');
INSERT INTO phen_off VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:plot','GAUGE_HEIGHT');
INSERT INTO proc_phen VALUES ('urn:ogc:object:feature:Sensor:IFGI:ifgi-sensor-1','urn:ogc:def:phenomenon:OGC:1.0.30:plot');

-- CO2
INSERT INTO phenomenon VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:co2', 'CO2', 'ppm','numericType');
INSERT INTO phen_off VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:co2','GAUGE_HEIGHT');
INSERT INTO proc_phen VALUES ('urn:ogc:object:feature:Sensor:IFGI:ifgi-sensor-1','urn:ogc:def:phenomenon:OGC:1.0.30:co2');

-- RecNo
INSERT INTO phenomenon VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:recno', 'Rec No', '','numericType');
INSERT INTO phen_off VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:recno','GAUGE_HEIGHT');
INSERT INTO proc_phen VALUES ('urn:ogc:object:feature:Sensor:IFGI:ifgi-sensor-1','urn:ogc:def:phenomenon:OGC:1.0.30:recno');

-- mb Ref
INSERT INTO phenomenon VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:mbref', 'mb Ref', '','numericType');
INSERT INTO phen_off VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:mbref','GAUGE_HEIGHT');
INSERT INTO proc_phen VALUES ('urn:ogc:object:feature:Sensor:IFGI:ifgi-sensor-1','urn:ogc:def:phenomenon:OGC:1.0.30:mbref');

-- mb Temp
INSERT INTO phenomenon VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:mbrtemp', 'mbR Temp', '','numericType');
INSERT INTO phen_off VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:mbrtemp','GAUGE_HEIGHT');
INSERT INTO proc_phen VALUES ('urn:ogc:object:feature:Sensor:IFGI:ifgi-sensor-1','urn:ogc:def:phenomenon:OGC:1.0.30:mbrtemp');

-- Oxygen
INSERT INTO phenomenon VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:oxygen', 'Oxygen', 'percent','numericType');
INSERT INTO phen_off VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:oxygen','GAUGE_HEIGHT');
INSERT INTO proc_phen VALUES ('urn:ogc:object:feature:Sensor:IFGI:ifgi-sensor-1','urn:ogc:def:phenomenon:OGC:1.0.30:oxygen');

-- input D
INSERT INTO phenomenon VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:inputd', 'input D', '','numericType');
INSERT INTO phen_off VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:inputd','GAUGE_HEIGHT');
INSERT INTO proc_phen VALUES ('urn:ogc:object:feature:Sensor:IFGI:ifgi-sensor-1','urn:ogc:def:phenomenon:OGC:1.0.30:inputd');

-- input E
INSERT INTO phenomenon VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:inpute', 'input E', '','numericType');
INSERT INTO phen_off VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:inpute','GAUGE_HEIGHT');
INSERT INTO proc_phen VALUES ('urn:ogc:object:feature:Sensor:IFGI:ifgi-sensor-1','urn:ogc:def:phenomenon:OGC:1.0.30:inpute');

-- input F
INSERT INTO phenomenon VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:inputf', 'input F', '','numericType');
INSERT INTO phen_off VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:inputf','GAUGE_HEIGHT');
INSERT INTO proc_phen VALUES ('urn:ogc:object:feature:Sensor:IFGI:ifgi-sensor-1','urn:ogc:def:phenomenon:OGC:1.0.30:inputf');

-- input G
INSERT INTO phenomenon VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:inputg', 'input G', '','numericType');
INSERT INTO phen_off VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:inputg','GAUGE_HEIGHT');
INSERT INTO proc_phen VALUES ('urn:ogc:object:feature:Sensor:IFGI:ifgi-sensor-1','urn:ogc:def:phenomenon:OGC:1.0.30:inputg');

-- input H
INSERT INTO phenomenon VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:inputh', 'input H', '','numericType');
INSERT INTO phen_off VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:inputh','GAUGE_HEIGHT');
INSERT INTO proc_phen VALUES ('urn:ogc:object:feature:Sensor:IFGI:ifgi-sensor-1','urn:ogc:def:phenomenon:OGC:1.0.30:inputh');

-- ATMP
INSERT INTO phenomenon VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:atmp', 'ATMP', '','numericType');
INSERT INTO phen_off VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:atmp','GAUGE_HEIGHT');
INSERT INTO proc_phen VALUES ('urn:ogc:object:feature:Sensor:IFGI:ifgi-sensor-1','urn:ogc:def:phenomenon:OGC:1.0.30:atmp');

-- Probe Type
INSERT INTO phenomenon VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:probetype', 'Probe Type', '','numericType');
INSERT INTO phen_off VALUES ('urn:ogc:def:phenomenon:OGC:1.0.30:probetype','GAUGE_HEIGHT');
INSERT INTO proc_phen VALUES ('urn:ogc:object:feature:Sensor:IFGI:ifgi-sensor-1','urn:ogc:def:phenomenon:OGC:1.0.30:probetype');

