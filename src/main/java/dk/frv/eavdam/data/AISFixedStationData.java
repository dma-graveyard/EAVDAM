package dk.frv.eavdam.data;

/**
 * A class for holding all properties of an AIS base station.
 * @author ttesei
 * @version 1.0
 * @created 26-elo-2011 13:27:24
 */
public class AISFixedStationData {

	/**
	 * Descriptive name for the station (E.g. 'Bornholm AIS Base Station' or 'W24 AIS
	 * AtoN Station').
	 */
	private String stationName;
	/**
	 * Latitude in decimal degrees, datum must be WGS84
	 */
	private double lat;
	/**
	 * Longitude in decimal degrees, datum must be WGS84
	 */
	private double lon;
	/**
	 * MMSI number (optional for receivers)
	 */
	private String mmsi;
	/**
	 * Transmission power in Watts.
	 */
	private double transmissionPower;
	/**
	 * A free text description of the station.
	 */
	private String description;
	/**
	 * Coverage information for the station.
	 */
	private AISFixedStationCoverage coverage;
	/**
	 * Antenna information for the station.
	 */
	private Antenna antenna;
	/**
	 * FATDMA allocation information for the station.
	 */
	private FATDMASlotAllocation fatdmaAllocation;
	/**
	 * Type of the fixed AIS station.
	 */
	private AISFixedStationType stationType;
	/**
	 * Responsible operator (user of EAVDAM) of the station.
	 */
	private EAVDAMUser operator;
	private AISFixedStationStatus status;

	public AISFixedStationData(){

	}

	public void finalize() throws Throwable {

	}

}