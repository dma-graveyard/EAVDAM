package dk.frv.eavdam.data;

/**
 * This class is used when querying for slotmap information.
 */
public class AISStation {

	private String organizationName;
	private String stationName;
	// should have more information?
	
	public AISStation() {}
	
	public AISStation(String organizationName, String stationName) {
		this.organizationName = organizationName;
		this.stationName = stationName;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	
}
