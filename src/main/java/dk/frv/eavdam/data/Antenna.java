package dk.frv.eavdam.data;

/**
 * @author ttesei
 * @version 1.0
 * @created 26-elo-2011 13:27:24
 */
public class Antenna {

	/**
	 * Heading degrees for AntennaType.DIRECTIONAL. Null for AntennaType.
	 * OMNIDIRECTIONAL.
	 */
	private Integer heading;
	/**
	 * Field of view angle degrees for AntennaType.DIRECTIONAL. Null for AntennaType.
	 * OMNIDIRECTIONAL.
	 */
	private Integer fieldOfViewAngle;
	/**
	 * Antenna gain (dB)
	 */
	private Double gain;
	/**
	 * Antenna height above terrain (meters).
	 */
	private double antennaHeight;
	/**
	 * Terrain height above sealevel (meters).
	 */
	private double terrainHeight;
	/**
	 * Type of the antenna.
	 */
	private AntennaType antennaType;

	public Antenna(){

	}

	public void finalize() throws Throwable {

	}

}