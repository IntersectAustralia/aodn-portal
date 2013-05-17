package au.org.emii.portal

class Metadata {
	
	String serviceKey
	String key
	String datasetName
	Date collectionPeriodFrom
	Date collectionPeriodTo
	String description
	String dataType
	Boolean embargo
	Date embargoExpiryDate
	static hasMany = [
		researchCodes: String,
		grantedUsers: User,
		collectors: User,
		publications: Publication
	]
	User principalInvestigator
	String dataAccess
	Long licence
	Boolean studentOwned
	User studentDataOwner
	Date dateCreated
	Date lastUpdated
	
	static mapping = {
		description type: "text"
	}

    static constraints = {
		embargoExpiryDate(nullable: true)
		studentDataOwner(nullable: true)
		principalInvestigator(nullable: true)
    }
	
	static dataTypeList() {
		[[id: 0, name: 'Sonde survey'], [id: 1, name: 'Nutrient sample'], [id: 2, name: 'Carbon Dioxide survey']]
	}
	
	static researchCodeList() {
		[
				'0101 Pure Mathematics',
				'0102 Applied Mathematics',
				'0103 Numerical and Computational Mathematics',
				'0104 Statistics',
				'0105 Mathematical Physics',
				'0199 Other Mathematical Sciences',
				'0201 Astronomical and Space Sciences',
				'0202 Atomic, Molecular, Nuclear, Particle and Plasma Physics',
				'0203 Classical Physics',
				'0204 Condensed Matter Physics',
				'0205 Optical Physics',
				'0206 Quantum Physics',
				'0299 Other Physical Sciences',
				'0301 Analytical Chemistry',
				'0302 Inorganic Chemistry',
				'0303 Macromolecular and Materials Chemistry',
				'0304 Medicinal and Biomolecular Chemistry',
				'0305 Organic Chemistry',
				'0306 Physical Chemistry (incl. Structural)',
				'0307 Theoretical and Computational Chemistry',
				'0399 Other Chemical Sciences',
				'0401 Atmospheric Sciences',
				'0402 Geochemistry',
				'0403 Geology',
				'0404 Geophysics',
				'0405 Oceanography',
				'0406 Physical Geography and Environmental Geoscience',
				'0499 Other Earth Sciences',
				'0501 Ecological Applications',
				'0502 Environmental Science and Management',
				'0503 Soil Sciences',
				'0599 Other Environmental Sciences',
				'0601 Biochemistry and Cell Biology',
				'0602 Ecology',
				'0603 Evolutionary Biology',
				'0604 Genetics',
				'0605 Microbiology',
				'0606 Physiology',
				'0607 Plant Biology',
				'0608 Zoology',
				'0701 Agriculture, Land and Farm Management',
				'0702 Animal Production',
				'0703 Crop and Pasture Production',
				'0704 Fisheries Sciences',
				'0705 Forestry Sciences',
				'0706 Horticultural Production',
				'0707 Veterinary Sciences',
				'0799 Other Agricultural and Veterinary Sciences',
				'0801 Artificial Intelligence and Image Processing',
				'0802 Computation Theory and Mathematics',
				'0803 Computer Software',
				'0804 Data Format',
				'0805 Distributed Computing',
				'0806 Information Systems',
				'0807 Library and Information Studies',
				'0899 Other Information and Computing Sciences',
				'0901 Aerospace Engineering',
				'0902 Automotive Engineering',
				'0903 Biomedical Engineering',
				'0904 Chemical Engineering',
				'0905 Civil Engineering',
				'0906 Electrical and Electronic Engineering',
				'0907 Environmental Engineering',
				'0908 Food Sciences',
				'0909 Geomatic Engineering',
				'0910 Manufacturing Engineering',
				'0911 Maritime Engineering',
				'0912 Materials Engineering',
				'0913 Mechanical Engineering',
				'0914 Resources Engineering and Extractive Metallurgy',
				'0915 Interdisciplinary Engineering',
				'0999 Other Engineering',
				'1001 Agricultural Biotechnology',
				'1002 Environmental Biotechnology',
				'1003 Industrial Biotechnology',
				'1004 Medical Biotechnology',
				'1005 Communications Technologies',
				'1006 Computer Hardware',
				'1007 Nanotechnology',
				'1099 Other Technology',
				'1101 Medical Biochemistry and Metabolomics',
				'1102 Cardiorespiratory Medicine and Haematology',
				'1103 Clinical Sciences',
				'1104 Complementary and Alternative Medicine',
				'1105 Dentistry',
				'1106 Human Movement and Sports Science',
				'1107 Immunology',
				'1108 Medical Microbiology',
				'1109 Neurosciences',
				'1110 Nursing',
				'1111 Nutrition and Dietetics',
				'1112 Oncology and Carcinogenesis',
				'1113 Ophthalmology and Optometry',
				'1114 Paediatrics and Reproductive Medicine',
				'1115 Pharmacology and Pharmaceutical Sciences',
				'1116 Medical Physiology',
				'1117 Public Health and Health Services',
				'1199 Other Medical and Health Sciences',
				'1201 Architecture',
				'1202 Building',
				'1203 Design Practice and Management',
				'1204 Engineering Design',
				'1205 Urban and Regional Planning',
				'1299 Other Built Environment and Design',
				'1301 Education Systems',
				'1302 Curriculum and Pedagogy',
				'1303 Specialist Studies in Education',
				'1399 Other Education',
				'1401 Economic Theory',
				'1402 Applied Economics',
				'1403 Econometrics',
				'1499 Other Economics',
				'1501 Accounting, Auditing and Accountability',
				'1502 Banking, Finance and Investment',
				'1503 Business and Management',
				'1504 Commercial Services',
				'1505 Marketing',
				'1506 Tourism',
				'1507 Transportation and Freight Services',
				'1601 Anthropology',
				'1602 Criminology',
				'1603 Demography',
				'1604 Human Geography',
				'1605 Policy and Administration',
				'1606 Political Science',
				'1607 Social Work',
				'1608 Sociology',
				'1699 Other Studies in Human Society',
				'1701 Psychology',
				'1702 Cognitive Sciences',
				'1799 Other Psychology and Cognitive Sciences',
				'1801 Law',
				'1802 Maori Law',
				'1899 Other Law and Legal Studies',
				'1901 Art Theory and Criticism',
				'1902 Film, Television and Digital Media',
				'1903 Journalism and Professional Writing',
				'1904 Performing Arts and Creative Writing',
				'1905 Visual Arts and Crafts',
				'1999 Other Studies in Creative Arts and Writing',
				'2001 Communication and Media Studies',
				'2002 Cultural Studies',
				'2003 Language Studies',
				'2004 Linguistics',
				'2005 Literary Studies',
				'2099 Other Language, Communication and Culture',
				'2101 Archaeology',
				'2102 Curatorial and Related Studies',
				'2103 Historical Studies',
				'2199 Other History and Archaeology',
				'2201 Applied Ethics',
				'2202 History and Philosophy of Specific Fields',
				'2203 Philosophy',
				'2204 Religion and Religious Studies',
				'2299 Other Philosophy and Religious Studies'
			]
	}

	static dataAccessList() {
		[[id: 0, name: 'Public'], [id: 1, name: 'Mediated'], [id: 2, name: 'Private']]
	}

	static licenceList() {
		[
			[id: 0, name: 'Creative Commons: Attribution + Allow Remixing', text: 'This work is licensed under a Creative Commons Attribution Australia 3.0 License', url: 'http://creativecommons.org/licenses/by/3.0/au/'],
			[id: 1, name: 'Creative Commons: Attribution + Prohibit Commercial Use', text: 'This work is licensed under a Creative Commons Attribution-NonCommercial-NoDerivs Australia 3.0 License', url: 'http://creativecommons.org/licenses/by-nc-nd/2.5/au/'],
			[id: 2, name: 'Creative Commons: Attribution + Allow Remixing + Prohibit Commercial', text: 'This work is licensed under a Creative Commons Attribution-NonCommercial Australia 3.0 License', url: 'http://creativecommons.org/licenses/by-nc/3.0/au/'],
			[id: 3, name: 'Creative Commons: Attribution + Allow Remixing + Requires Share-Alike', text: 'This work is licensed under a Creative Commons Attribution-ShareAlike Australia 3.0 License', url: 'http://creativecommons.org/licenses/by-sa/3.0/au/'],
			[id: 4, name: 'Creative Commons: Attribution + Allow Remixing + Requires Share-Alike + Prohibit Commercial Use', text: 'This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike Australia 2.5 License', url: 'http://creativecommons.org/licenses/by-nc-sa/3.0/au/'],
			[id: 5, name: 'No Licence', text: 'No licence needed', url: '']
		]
	}
	
	static relatedPartyTypeList() {
		[[id: 0, name: 'Collector'], [id: 1, name: 'Principal investigator']]
	}
	
}
