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
	
}
