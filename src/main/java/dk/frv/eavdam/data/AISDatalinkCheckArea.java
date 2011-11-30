package dk.frv.eavdam.data;

import java.util.ArrayList;
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
	private List<AISDatalinkCheckIssue> issues;
	private AISSlotMap slotmap;
	
	public AISDatalinkCheckArea() {}
	
	public AISDatalinkCheckArea(double topLeftLatitude, double topLeftLongitude, double lowerRightLatitude, double lowerRightLongitude, double bandwithUsageLevel) {
		this.topLeftLatitude = topLeftLatitude;
		this.topLeftLongitude = topLeftLongitude;
		this.lowerRightLatitude = lowerRightLatitude;
		this.lowerRightLongitude = lowerRightLongitude;	
		this.bandwithUsageLevel = bandwithUsageLevel;	
	}

	public AISDatalinkCheckArea(double topLeftLatitude, double topLeftLongitude, double lowerRightLatitude, double lowerRightLongitude) {
		this.topLeftLatitude = topLeftLatitude;
		this.topLeftLongitude = topLeftLongitude;
		this.lowerRightLatitude = lowerRightLatitude;
		this.lowerRightLongitude = lowerRightLongitude;	
	}

	public AISDatalinkCheckArea(double topLeftLatitude, double topLeftLongitude) {
		this.topLeftLatitude = topLeftLatitude;
		this.topLeftLongitude = topLeftLongitude;
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
		String ut = "("+topLeftLatitude+";"+topLeftLongitude+") - ("+lowerRightLatitude+";"+lowerRightLongitude+"): "+bandwithUsageLevel+" "+(issues != null ? issues.size() +" issues " : "");
		if(this.issues != null && issues.size() > 0){
			 ut+= "\n\t"+issues.get(0).getRuleViolated();
		
		}
		
		return ut;
	}

	public List<AISDatalinkCheckIssue> getIssues() {
		return issues;
	}

	public void setIssues(List<AISDatalinkCheckIssue> issues) {
		this.issues = issues;
	}
	
	public void addIssue(AISDatalinkCheckIssue issue){
		if(this.issues == null) this.issues = new ArrayList<AISDatalinkCheckIssue>();
		
		issues.add(issue);
	}

	public AISSlotMap getSlotmap() {
		return slotmap;
	}

	public void setSlotmap(AISSlotMap slotmap) {
		this.slotmap = slotmap;
	}
	
}