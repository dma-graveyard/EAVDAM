package dk.frv.eavdam.data;

import java.util.List;

public class AISSlotMap {

	private AISFrequency frequency;  // AIS1 or AIS2
	private List<AISTimeslot> timeslots;  // 2250 timeslots preferably in order from 0 to 2249

	public AISSlotMap() {}
	
	public AISSlotMap(AISFrequency frequency, List<AISTimeslot> timeslots) {
		this.frequency = frequency;
		this.timeslots = timeslots;
	}

	public AISFrequency getFrequency() {
		return frequency;
	}

	public void setFrequency(AISFrequency frequency) {
		this.frequency = frequency;
	}
	
	public List<AISTimeslot> getTimeslots() {
		return timeslots;
	}

	public void setTimeslots(List<AISTimeslot> timeslots) {		
		this.timeslots = timeslots;
	}	
	
}
