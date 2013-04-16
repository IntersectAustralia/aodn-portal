package au.org.emii.portal

class Organization {

	String name
	OrganisationType type;

	static constraints = {
		name(nullable: false, blank: false, unique: true)
		type(nullable: false)
	}

	@Override
	public String toString() {
		return name
	}
}
