package dk.frv.eavdam.data;

import java.util.List;

/**
 * This class contains a bandwith usage area of a AIS VHF Datalink Health Check operation.
 */
public class AISDatalinkCheckArea {

	private double topLeftLatitude;
	private double topLeftLongitude;
	private double lowerRightLatitude;
	private double lowerRightLongitude;
	private double bandwithUsageLevel;
	
	public AISDatalinkCheckArea() {}
	
	public AISDatalinkCheckArea(double topLeftLatitude, double topLeftLongitude, double lowerRightLatitude, double lowerRightLongitude, double bandwithUsageLevel) {
		this.topLeftLatitude = topLeftLatitude;
		this.topLeftLongitude = topLeftLongitude;
		this.lowerRightLatitude = lowerRightLatitude;
		this.lowerRightLongitude = lowerRightLongitude;	
		this.bandwithUsageLevel = bandwithUsageLevel;	
	}

	public double getTopLeftLatitude() {
		return topLeftLatitude;
	}

	public void setTopLeftLatitude(double topLeftLatitude) {
		this.topLeftLatitude = topLeftLatitude;
	}
	
	public double getTopLeftLongitude() {
		return topLeftLongitude;
	}

	public void setTopLeftLongitude(double topLeftLongitude) {
		this.topLeftLongitude = topLeftLongitude;
	}
		
	public double getLowerRightLatitude() {
		return lowerRightLatitude;
	}

	public void setLowerRightLatitude(double lowerRightLatitude) {
		this.lowerRightLatitude = lowerRightLatitude;
	}
	
	public double getLowerRightLongitude() {
		return lowerRightLongitude;
	}

	public void setLowerRightLongitude(double lowerRightLongitude) {
		this.lowerRightLongitude = lowerRightLongitude;
	}

	public double getBandwithUsageLevel() {
		return bandwithUsageLevel;
	}

	public void setBandwithUsageLevel(double bandwithUsageLevel) {
		this.bandwithUsageLevel = bandwithUsageLevel;
	}		
	
	public String toString(){
		return "("+topLeftLatitude+";"+topLeftLongitude+") - ("+lowerRightLatitude+";"+lowerRightLongitude+"): "+bandwithUsageLevel;
	}
	
}