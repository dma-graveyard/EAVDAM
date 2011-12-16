/*
* Copyright 2011 Danish Maritime Safety Administration. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without
* modification, are permitted provided that the following conditions are met:
*
* 1. Redistributions of source code must retain the above copyright notice,
* this list of conditions and the following disclaimer.
*
* 2. Redistributions in binary form must reproduce the above copyright notice,
* this list of conditions and the following disclaimer in the documentation and/or
* other materials provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY Danish Maritime Safety Administration ``AS IS''
* AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
* IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
* DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> OR CONTRIBUTORS BE LIABLE FOR
* ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
* (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
* LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
* (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
* SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

* The views and conclusions contained in the software and documentation are those
* of the authors and should not be interpreted as representing official policies,
* either expressed or implied, of Danish Maritime Safety Administration.
*
*/
package dk.frv.eavdam.data;

/**
 * Class for AIS station's antenna information.
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

	public Antenna() {}

	public Integer getHeading() {
		return heading;
	}

	/**
	 * Also validates the heading. Throws IllegalArgumentException if heading is not in range 0-359.
	 *
	 * @param heading  Heading
	 */		
	public void setHeading(Integer heading) {
		if (heading != null && (heading < 0 || heading > 359)) {
			throw new IllegalArgumentException("Heading not in range [0 359]");
		}
		this.heading = heading;
	}

	public Integer getFieldOfViewAngle() {
		return fieldOfViewAngle;
	}

	/**
	 * Also validates the field of view angle. Throws IllegalArgumentException if field of view angle is not in range 0-359.
	 *
	 * @param fieldOfViewAngle  Field of view angle
	 */			
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

	/**
	 * Also validates the gain. Throws IllegalArgumentException if gain is negative.
	 *
	 * @param gain  Gain
	 */		
	public void setGain(Double gain) {
		if (gain != null && gain < 0) {
			throw new IllegalArgumentException("Gain must be non-negative");
		}
		this.gain = gain;
	}

	public double getAntennaHeight() {
		return antennaHeight;
	}

	/**
	 * Also validates the antenna height. Throws IllegalArgumentException if antenna height is negative.
	 *
	 * @param antennaHeight  Antenna height
	 */		
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
		return "Antenna [heading=" + heading + ", fieldOfViewAngle=" +
			fieldOfViewAngle + ", gain=" + gain + ", antennaHeight=" +
			antennaHeight + ", terrainHeight=" + terrainHeight +
			", antennaType=" + antennaType + "]";
	}
}