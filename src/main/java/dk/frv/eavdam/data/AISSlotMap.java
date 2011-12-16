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

import java.util.List;

public class AISSlotMap {

	private List<AISTimeslot> ais1Timeslots;  // 2250 timeslots for AIS1 preferably in order from 0 to 2249
	private List<AISTimeslot> ais2Timeslots;  // 2250 timeslots for AIS2 preferably in order from 0 to 2249
	private double lat;
	private double lon;
	private double bandwidthReservation;
	private double bandwidthReservationA;
	private double bandwidthReservationB;
	private double bandwidthUsedByLocalA;
	private double bandwidthUsedByLocalB;
	
	private List<AISDatalinkCheckIssue> issues;
	
	public AISSlotMap() {}
	
	public AISSlotMap(List<AISTimeslot> ais1Timeslots, List<AISTimeslot> ais2Timeslots) {
		this.ais1Timeslots = ais1Timeslots;
		this.ais2Timeslots = ais2Timeslots;
	}

	public List<AISTimeslot> getAIS1Timeslots() {
		return ais1Timeslots;
	}

	public void setAIS1Timeslots(List<AISTimeslot> ais1Timeslots) {		
		this.ais1Timeslots = ais1Timeslots;
	}
	
	public List<AISTimeslot> getAIS2Timeslots() {
		return ais2Timeslots;
	}

	public void setAIS2Timeslots(List<AISTimeslot> ais2Timeslots) {		
		this.ais2Timeslots = ais2Timeslots;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public double getBandwidthReservation() {
		return bandwidthReservation;
	}

	public void setBandwidthReservation(double bandwidthReservation) {
		this.bandwidthReservation = bandwidthReservation;
	}

	public double getBandwidthReservationA() {
		return bandwidthReservationA;
	}

	public void setBandwidthReservationA(double bandwidthReservationA) {
		this.bandwidthReservationA = bandwidthReservationA;
	}

	public double getBandwidthReservationB() {
		return bandwidthReservationB;
	}

	public void setBandwidthReservationB(double bandwidthReservationB) {
		this.bandwidthReservationB = bandwidthReservationB;
	}

	public double getBandwidthUsedByLocalA() {
		return bandwidthUsedByLocalA;
	}

	public void setBandwidthUsedByLocalA(double bandwidthUsedByLocalA) {
		this.bandwidthUsedByLocalA = bandwidthUsedByLocalA;
	}

	public double getBandwidthUsedByLocalB() {
		return bandwidthUsedByLocalB;
	}

	public void setBandwidthUsedByLocalB(double bandwidthUsedByLocalB) {
		this.bandwidthUsedByLocalB = bandwidthUsedByLocalB;
	}

	public List<AISDatalinkCheckIssue> getIssues() {
		return issues;
	}

	public void setIssues(List<AISDatalinkCheckIssue> issues) {
		this.issues = issues;
	}	
	
}
