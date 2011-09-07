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
		if (heading != null && (heading < 0 || heading > 359)) {
			throw new IllegalArgumentException("Heading not in range [0 359]");
		}
		this.heading = heading;
	}

	public Integer getFieldOfViewAngle() {
		return fieldOfViewAngle;
	}

	public void setFieldOfViewAngle(Integer fieldOfViewAngle) {
		if (fieldOfViewAngle != null
				&& (fieldOfViewAngle < 0 || fieldOfViewAngle > 359)) {
			throw new IllegalArgumentException(
					"Field of view angle not in range [0 359]");
		}
		this.fieldOfViewAngle = fieldOfViewAngle;
	}

	public Double getGain() {
		return gain;
	}

	public void setGain(Double gain) {
		if (gain != null && gain < 0) {
			throw new IllegalArgumentException("Gain must be non-negative");
		}
		this.gain = gain;
	}

	public double getAntennaHeight() {
		return antennaHeight;
	}

	public void setAntennaHeight(double antennaHeight) {
		if (antennaHeight < 0) {
			throw new IllegalArgumentException("Antenna height must be non-negative");
		}
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