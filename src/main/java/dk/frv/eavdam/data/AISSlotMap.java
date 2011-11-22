package dk.frv.eavdam.data;

import java.util.List;

public class AISSlotMap {

	private List<AISTimeslot> ais1Timeslots;  // 2250 timeslots for AIS1 preferably in order from 0 to 2249
	private List<AISTimeslot> ais2Timeslots;  // 2250 timeslots for AIS2 preferably in order from 0 to 2249
	
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
	
}
