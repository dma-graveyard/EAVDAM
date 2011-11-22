package dk.frv.eavdam.data;

import java.util.List;

public class AISTimeslot {

	private int slotNumber;  // 0...2249
	private boolean free;
	private List<AISStation> reservedBy;
	private List<AISStation> usedBy;
	private List<AISStation> interferedBy;
	private boolean possibleConflicts;

	public AISTimeslot() {}

	public AISTimeslot(int slotNumber, boolean free, List<AISStation> reservedBy, List<AISStation> usedBy, List<AISStation> interferedBy, boolean possibleConflicts) {
		if (slotNumber < 0 || slotNumber > 2249) {
			throw new IllegalArgumentException("Slot number must be between 0 and 2249.");
		}
		this.slotNumber = slotNumber;
		this.free = free;
		this.reservedBy = reservedBy;
		this.usedBy = usedBy;
		this.interferedBy = interferedBy;
		this.possibleConflicts = possibleConflicts;
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
	
	public boolean isFree() {
		return free;
	}

	public void setFree(boolean free) {
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
	
	public boolean hasPossibleConflicts() {
		return possibleConflicts;
	}

	public void setPossibleConflicts(boolean possibleConflicts) {
		this.possibleConflicts = possibleConflicts;
	}	
	
}
