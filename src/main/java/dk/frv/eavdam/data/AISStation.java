package dk.frv.eavdam.data;

/**
 * This class is used when querying for slotmap information.
 */
public class AISStation {

	private String organizationName;
	private String stationName;
	private Double latitude;
	private Double longitude;
	// should have more information?
	
	public AISStation() {}
	
	public AISStation(String organizationName, String stationName, Double latitude, Double longitude) {
		this.organizationName = organizationName;
		this.stationName = stationName;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	
	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}	
	
	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}		

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}		
	
}