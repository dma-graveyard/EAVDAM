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
	 * Field of view angle degrees for AntennaType.DIRECTIONAL. Null for
	 * AntennaType. OMNIDIRECTIONAL.
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

	public Antenna() {
	}

	public Integer getHeading() {
		return heading;
	}

	public void setHeading(Integer heading) {
		this.heading = heading;
	}

	public Integer getFieldOfViewAngle() {
		return fieldOfViewAngle;
	}

	public void setFieldOfViewAngle(Integer fieldOfViewAngle) {
		this.fieldOfViewAngle = fieldOfViewAngle;
	}

	public Double getGain() {
		return gain;
	}

	public void setGain(Double gain) {
		this.gain = gain;
	}

	public double getAntennaHeight() {
		return antennaHeight;
	}

	public void setAntennaHeight(double antennaHeight) {
		this.antennaHeight = antennaHeight;
	}

	public double getTerrainHeight() {
		return terrainHeight;
	}

	public void setTerrainHeight(double terrainHeight) {
		this.terrainHeight = terrainHeight;
	}

	public AntennaType getAntennaType() {
		return antennaType;
	}

	public void setAntennaType(AntennaType antennaType) {
		this.antennaType = antennaType;
	}

	@Override
	public String toString() {
		return "Antenna [heading=" + heading + ", fieldOfViewAngle="
				+ fieldOfViewAngle + ", gain=" + gain + ", antennaHeight="
				+ antennaHeight + ", terrainHeight=" + terrainHeight
				+ ", antennaType=" + antennaType + "]";
	}
}