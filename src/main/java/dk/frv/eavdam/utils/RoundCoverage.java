package dk.frv.eavdam.utils;

/**
 * Calculates the round coverage area given the antenna height.
 * 
 * @author TTETMJ
 *
 */
public class RoundCoverage {

	public static final double DEFAULT_RECEIVER_HEIGHT = 4.0;
	
	/**
	 * 
	 * 
	 * @param antennaHeight Height of the antenna. Either antennaHeight or antennaHeight + terrainHeight
	 * @param receiverHeight The height of the receiving antenna. Default: 4m (if less than 0 is given, the default is used).
	 * @return The radius in nautical miles
	 */
	public static double getRoundCoverageRadius(double antennaHeight, double receiverHeight){
		if(receiverHeight < 0) receiverHeight = DEFAULT_RECEIVER_HEIGHT;
		
		return 2.5*(Math.pow(antennaHeight, 0.5) + Math.pow(receiverHeight, 0.5));
		
	}
}
