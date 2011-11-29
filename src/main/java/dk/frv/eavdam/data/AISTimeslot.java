package dk.frv.eavdam.data;

import java.util.List;

public class AISTimeslot {

	private AISFrequency frequency;
	private int slotNumber;  // 0...2249
	private Boolean free;
	private List<AISStation> reservedBy;
	private List<AISStation> usedBy;
	private List<AISStation> interferedBy;
	private Boolean possibleConflicts;

	public AISTimeslot() {}

	public AISTimeslot(AISFrequency frequency, int slotNumber, Boolean free, List<AISStation> reservedBy, List<AISStation> usedBy, List<AISStation> interferedBy, Boolean possibleConflicts) {
		if (slotNumber < 0 || slotNumber > 2249) {
			throw new IllegalArgumentException("Slot number must be between 0 and 2249.");
		}
		this.frequency = frequency;
		this.slotNumber = slotNumber;
		this.free = free;
		this.reservedBy = reservedBy;
		this.usedBy = usedBy;
		this.interferedBy = interferedBy;
		this.possibleConflicts = possibleConflicts;
	}	
	
	public AISFrequency getFrequency() {
		return frequency;
	}
	
	public void setFrequency(AISFrequency frequency) {
		this.frequency = frequency;
	}	
	
	public int getSlotNumber() {
		return slotNumber;
	}

	public void setSlotNumber(int slotNumber) {
		if (slotNumber < 0 || slotNumber > 2249) {
			throw new IllegalArgumentException("Slot number must be between 0 and 2249.");
		}
		this.slotNumber = slotNumber;
	}
	
	public Boolean getFree() {
		return free;
	}

	public void setFree(Boolean free) {
		this.free = free;
	}	
	
	public List<AISStation> getReservedBy() {
		return reservedBy;
	}

	public void setReservedBy(List<AISStation> reservedBy) {
		this.reservedBy = reservedBy;
	}	
	
	public List<AISStation> getUsedBy() {
		return usedBy;
	}

	public void setUsedBy(List<AISStation> usedBy) {
		this.usedBy = usedBy;
	}	

	public List<AISStation> getInterferedBy() {
		return interferedBy;
	}

	public void setInterferedBy(List<AISStation> interferedBy) {
		this.interferedBy = interferedBy;
	}	
	
	public Boolean getPossibleConflicts() {
		return possibleConflicts;
	}

	public void setPossibleConflicts(Boolean possibleConflicts) {
		this.possibleConflicts = possibleConflicts;
	}	
	
	
	public String toString(){
		String stations = "";
		if(interferedBy != null){
			for(AISStation s : interferedBy){
				stations += s.getStationName()+" | ";
			}
		}
		return "Is free: "+this.free+(!free.booleanValue() ? " | In use by "+usedBy.size()+" stations. Conflicts: "+(interferedBy != null ? interferedBy.size()+", Stations: "+stations : 0+"") : "");
	}
}
