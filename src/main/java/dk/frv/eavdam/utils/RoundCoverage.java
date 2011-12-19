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
package dk.frv.eavdam.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for calculating the round coverage area given the antenna height.
 */
public class RoundCoverage {

	public static final double DEFAULT_RECEIVER_HEIGHT = 4.0;
    private static final double DEGREES_TO_RADIANS = (Math.PI / 180.0);
    public static final double EARTH_RADIUS = 6371.0;
	

	/**
	 * Gets the coverage area in a polygon form. 
	 * 
	 * @param antennaHeight Height of the antenna.
	 * @param receiverHeight Height of the receiver. Default is 4.0 meters.
	 * @param centerLat Location of the base station (lat)
	 * @param centerLon Location of the base station (lon)
	 * @param numberOfPoints Number of points in the polygon. Should be at least 10.
	 * @return List of points (lat,lon) in the polygon. The first and the last point is the same. The index 0 in the double array has the latitude and the index 1 has the longitude. 
	 */
	public static List<double[]> getRoundCoverage(double antennaHeight, double receiverHeight, double centerLat, double centerLon, int numberOfPoints){
		List<double[]> points = new ArrayList<double[]>();
		
		double radius = getRoundCoverageRadius(antennaHeight, receiverHeight);
		
		if(numberOfPoints < 10) numberOfPoints = 10;
		
		double partOfCircleAngle = 360.0/numberOfPoints;
		double[] startPoint = null;
		for(double angle = 0; angle <= 360.0; angle += partOfCircleAngle){
			
			double[] point = getCoordinates(centerLat, centerLon, radius, angle);
			
			if(angle == 0){
				startPoint = point;
			}
			
			points.add(point);
		}
		
		if(points.get(points.size()-1)[0] != startPoint[0] || points.get(points.size()-1)[1] != startPoint[1]){
			points.add(startPoint);
		}
		
		return points;
	}
	
	/**
	 * Gets the partial coverage area (for directional antennas) in a polygon form. 
	 * 
	 * @param antennaHeight Height of the antenna.
	 * @param receiverHeight Height of the receiver. Default is 4.0 meters.
	 * @param centerLat Location of the base station (lat)
	 * @param centerLon Location of the base station (lon)
	 * @param heading Heading
	 * @param fieldOfViewAngle Field of view angle
	 * @param numberOfPoints Number of points in the polygon. Should be at least 10.
	 * @return List of points (lat,lon) in the polygon. The first and the last point is the same. The index 0 in the double array has the latitude and the index 1 has the longitude. 
	 */
	public static List<double[]> getRoundCoverage(double antennaHeight, double receiverHeight, double centerLat, double centerLon, double heading, double fieldOfViewAngle, int numberOfPoints) {
		List<double[]> points = new ArrayList<double[]>();
		
        double[] stationPoint = new double[2];
		
		if(numberOfPoints < 10) numberOfPoints = 10;
		
		double partOfCircleAngle = (fieldOfViewAngle/5)/Math.round(numberOfPoints/2);
		for(double angle = heading+(fieldOfViewAngle/10); angle >= heading-(fieldOfViewAngle/10); angle -= partOfCircleAngle){
			double[] point = getCoordinates(centerLat, centerLon, 0.01, angle);
			points.add(point);
		}		
		double[] point = getCoordinates(centerLat, centerLon, 0.01, heading-(fieldOfViewAngle/10));
		points.add(point);
			
		double radius = getRoundCoverageRadius(antennaHeight, receiverHeight);		
		
		partOfCircleAngle = fieldOfViewAngle/Math.round(numberOfPoints/2);
		for(double angle = heading-(fieldOfViewAngle/2); angle <= heading+(fieldOfViewAngle/2); angle += partOfCircleAngle){
			point = getCoordinates(centerLat, centerLon, radius, angle);
			points.add(point);
		}
		point = getCoordinates(centerLat, centerLon, radius, heading+(fieldOfViewAngle/2));
		points.add(point);
		
		points.add(getCoordinates(centerLat, centerLon, 0.01, heading+(fieldOfViewAngle/10)));  // first point
			
		return points;
	}	
	
	/**
	 * Gets the coverage area in a polygon form. 
	 * 
	 * @param centerLat Location of the base station (lat)
	 * @param centerLon Location of the base station (lon)
	 * @param numberOfPoints Number of points in the polygon. Should be at least 10.
	 * @return List of points (lat,lon) in the polygon. The first and the last point is the same. The index 0 in the double array has the latitude and the index 1 has the longitude. 
	 */
	public static List<double[]> getRoundInterferenceCoverage(double centerLat, double centerLon, int numberOfPoints){
		List<double[]> points = new ArrayList<double[]>();
		
		double radius = 120*1.852;
		
		if(numberOfPoints < 10) numberOfPoints = 10;
		
		double partOfCircleAngle = 360.0/numberOfPoints;
		double[] startPoint = null;
		for(double angle = 0; angle <= 360.0; angle += partOfCircleAngle){
			
			double[] point = getCoordinates(centerLat, centerLon, radius, angle);
			
			if(angle == 0){
				startPoint = point;
			}
			
			points.add(point);
		}
		
		if(points.get(points.size()-1)[0] != startPoint[0] || points.get(points.size()-1)[1] != startPoint[1]){
			points.add(startPoint);
		}
		
		return points;
	}
	
	/**
	 * Gets the coverage area (for directional antennas) in a polygon form. 
	 * 
	 * @param antennaHeight Height of the antenna.
	 * @param receiverHeight Height of the receiver. Default is 4.0 meters.
	 * @param centerLat Location of the base station (lat)
	 * @param centerLon Location of the base station (lon)
	 * @param heading Heading
	 * @param fieldOfViewAngle Field of view angle	 
	 * @param numberOfPoints Number of points in the polygon. Should be at least 10.
	 * @return List of points (lat,lon) in the polygon. The first and the last point is the same. The index 0 in the double array has the latitude and the index 1 has the longitude. 
	 */
	public static List<double[]> getRoundInterferenceCoverage(double antennaHeight, double receiverHeight, double centerLat, double centerLon, double heading, double fieldOfViewAngle, int numberOfPoints){
		List<double[]> points = new ArrayList<double[]>();
	
		double radius1 = getRoundCoverageRadius(antennaHeight, receiverHeight);
		
		if(numberOfPoints < 10) numberOfPoints = 10;
		
		double partOfCircleAngle = 180.0/Math.round(numberOfPoints/2);
		double radius2 = 120*1.852;	
		
		
		double startAngle = heading + 90;
		if (startAngle > 360) {
			startAngle = startAngle - 360;
		}
		double endAngle = heading - 90;
		if (endAngle < 0) {
			endAngle = 360 + endAngle; //e.g., 360 + (-60)
		}
		
		double temp = -1;
		
		if(endAngle < startAngle) endAngle += 360;
		
		// first, do the half circle behind the heading
		for (double angle = startAngle; angle <= endAngle; angle += partOfCircleAngle){	
			double realAngle = angle;
			if(realAngle > 360) realAngle -= 360;
			
			double[] point = getCoordinates(centerLat, centerLon, radius1, realAngle);
			points.add(point);
			temp = realAngle;
		}
		
		if(endAngle > 360) endAngle -= 360;
		
		if (temp != endAngle) {  // adds the last point if the points were not evenly distributed
			double[] point = getCoordinates(centerLat, centerLon, radius1, endAngle);
			points.add(point);		
		}
		
		double partStartAngle = heading-(fieldOfViewAngle/2);
		double partEndAngle = heading+(fieldOfViewAngle/2);
		
		if(partStartAngle > partEndAngle) partEndAngle += 360;
		
		// coverage to the front of the heading
		partOfCircleAngle = fieldOfViewAngle/Math.round(numberOfPoints/2);
		for(double angle = partStartAngle; angle <= partEndAngle; angle += partOfCircleAngle){			
			double realAngle = angle;
			if(realAngle > 360) realAngle -= 360;
			
			double[] point = getCoordinates(centerLat, centerLon, radius2, realAngle);
			points.add(point);
			temp = realAngle;			
		}
		
		if(partEndAngle > 360) partEndAngle -= 360;
		
		if (temp != partEndAngle) {
			double[] point = getCoordinates(centerLat, centerLon, radius2, partEndAngle);
			points.add(point);		
		}

		double[] point = getCoordinates(centerLat, centerLon, radius1, startAngle);
		
		points.add(point);
		
		return points;
	}	
	
    /**
	 * Calculates the radius for the circle.
	 * 
	 * @param antennaHeight Height of the antenna. Either antennaHeight or antennaHeight + terrainHeight
	 * @param receiverHeight The height of the receiving antenna. Default: 4m (if less than 0 is given, the default is used).
	 * @return The radius in kilometers
	 */
	public static double getRoundCoverageRadius(double antennaHeight, double receiverHeight){
		if(receiverHeight < 0) receiverHeight = DEFAULT_RECEIVER_HEIGHT;
		
		return 2.5*(Math.pow(antennaHeight, 0.5) + Math.pow(receiverHeight, 0.5))*1.852;
		
	}
	
	/**
	 * Converts degrees to radians.
	 *
	 * @param d  Degrees value
	 * @return   Given value converted to radians
	 */
    public static double degrees2radians(double d) {
        return d * Math.PI / 180;
    }

	/**
	 * Converts radians to degrees.
	 *
	 * @param r  Radians value
	 * @return   Given value converted to degrees
	 */	
    public static double radians2degrees(double r) {
        return r * 180 / Math.PI;
    }
    
    /**
     * Converts the D'M.S degree to the decimal degree.
     * 
     * @param D Degree
     * @param M Minutes
     * @param S Seconds
     * @return Decimal degree
     */
    public static double convertToDecimalDegrees(double D, double M, double S) {
        return (D + M / 60 + S / 3600);
    }

    /**
     * Returns the distance between two coordinates.
     * 
     * @param lat1  Latitude of the first coordinate
     * @param lon1  Longitude of the first coordinate
     * @param lat2  Latitude of the second coordinate
     * @param lon2  Longitude of the second coordinate
     * @return      Distance between the coordinates
     */
    private static double greatCircleDistance(double lat1, double lon1, double lat2, double lon2) {
        lat1 = degrees2radians(lat1);
        lat2 = degrees2radians(lat2);
        lon1 = degrees2radians(lon1);
        lon2 = degrees2radians(lon2);
        double p1 = Math.cos(lat1) * Math.cos(lon1) * Math.cos(lat2) * Math.cos(lon2);
        double p2 = Math.cos(lat1) * Math.sin(lon1) * Math.cos(lat2) * Math.sin(lon2);
        double p3 = Math.sin(lat1) * Math.sin(lat2);
        return (Math.acos(p1 + p2 + p3) * EARTH_RADIUS);
    }
    
    /**
     * Returns the coordinates  
     * 
     * @param lat1   Starting point (lat)
     * @param lon1   Starting point (lon)
     * @param dist   Distance to the other coordinate.
     * @param angle  Angle to the other coordinate.
     * @return       Double array where index 0 has the latitude and the index 1 longitude.
     */
    public static double[] getCoordinates(double lat1, double lon1, double dist, double angle) {
        lat1 = degrees2radians(lat1);
        lon1 = degrees2radians(lon1);
        angle = degrees2radians(angle);
        dist = dist / EARTH_RADIUS;
        double lat = 0;
        double lon = 0;
        lat = Math.asin(Math.sin(lat1) * Math.cos(dist) + Math.cos(lat1) * Math.sin(dist) * Math.cos(angle));
        if (Math.cos(lat) == 0 || Math.abs(Math.cos(lat)) < 0.000001) {
            lon = lon1;
        } else {
            lon = lon1 + Math.atan2(Math.sin(angle) * Math.sin(dist) * Math.cos(lat1), Math.cos(dist) - Math.sin(lat1) * Math.sin(lat));
            //lon = ((lon1 - Math.asin(Math.sin(angle) * Math.sin(dist) / Math.cos(lat)) + Math.PI) % (2 * Math.PI)) - Math.PI;
        }
        lat = radians2degrees(lat);
        lon = radians2degrees(lon);
        //System.out.println(lat + ";" + lon);
        double[] coord = new double[2];
        coord[0] = lat;
        coord[1] = lon;
        
        return coord;
    }
        
    /**
     * Just for testing... 
     * 
     * @param args
     */
    public static void main(String[] args) {
    	List<double[]> points = getRoundCoverage(10, 4, 12.0, 55.0, 11);
    	
    	for (double[] p : points) {
    		System.out.println(p[0]+","+p[1]);
    	}
    }
	
}
