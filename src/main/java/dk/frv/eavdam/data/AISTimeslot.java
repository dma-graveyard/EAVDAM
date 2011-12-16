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

/**
 * Class for a time slot in slotmap.
 */
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

	/**
	 * Also validates the slot number. Throws IllegalArgumentException if slot number is not in range 0-2249.
	 *
	 * @param slotNumber  slot number
	 */	
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
		if (interferedBy != null) {
			for (AISStation s : interferedBy) {
				stations += s.getStationName() + " | ";
			}
		}
		return "Is free: " + this.free + (!free.booleanValue() ? " | In use by " + usedBy.size() + " stations. Conflicts: " + 
			(interferedBy != null ? interferedBy.size() + ", Stations: " + stations : 0 + "") : "");
	}

}