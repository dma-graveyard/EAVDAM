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

import java.util.ArrayList;
import java.util.List;

/**
 * Class for a bandwith usage area of an AIS VHF Datalink Health Check operation.
 */
public class AISDatalinkCheckArea {

	private double topLeftLatitude;
	private double topLeftLongitude;
	private double lowerRightLatitude;
	private double lowerRightLongitude;
	private double bandwithUsageLevel;
	private double maxChannelBandwithUsageLevel;
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
		String ut = "(" + topLeftLatitude + ";" + topLeftLongitude + ") - (" + lowerRightLatitude + ";"+lowerRightLongitude +
			"): " + bandwithUsageLevel + " " + (issues != null ? issues.size() + " issues " : "");
		if( this.issues != null && issues.size() > 0) {
			 ut += "\n\t" + issues.get(0).getRuleViolated();
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
		if (this.issues == null) {
			this.issues = new ArrayList<AISDatalinkCheckIssue>();
		}
		issues.add(issue);
	}

	public AISSlotMap getSlotmap() {
		return slotmap;
	}

	public void setSlotmap(AISSlotMap slotmap) {
		this.slotmap = slotmap;
	}

	public double getMaxChannelBandwithUsageLevel() {
		return maxChannelBandwithUsageLevel;
	}

	public void setMaxChannelBandwithUsageLevel(double maxChannelBandwithUsageLevel) {
		this.maxChannelBandwithUsageLevel = maxChannelBandwithUsageLevel;
	}
	
}