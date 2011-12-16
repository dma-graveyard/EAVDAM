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
 * This class contains a single issue of a AIS VHF Datalink Health Check operation.
 */
public class AISDatalinkCheckIssue {

	private int id;  // to be able to delete or acknowledge the issue
	private AISDatalinkCheckRule ruleViolated;
	private AISDatalinkCheckSeverity severity;
	private List<AISStation> involvedStations;
	private List<AISTimeslot> involvedTimeslots;
	private boolean acknowledged;
	private boolean deleted;
	
	public AISDatalinkCheckIssue() {}
	
	public AISDatalinkCheckIssue(int id, AISDatalinkCheckRule ruleViolated, AISDatalinkCheckSeverity severity, List<AISStation> involvedStations, List<AISTimeslot> involvedTimeslots) {
		this.id = id;
		this.ruleViolated = ruleViolated;
		this.severity = severity;
		this.involvedStations = involvedStations;	
		this.involvedTimeslots = involvedTimeslots;	
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public AISDatalinkCheckRule getRuleViolated() {
		return ruleViolated;
	}

	public void setRuleViolated(AISDatalinkCheckRule ruleViolated) {
		this.ruleViolated = ruleViolated;
	}	

	public AISDatalinkCheckSeverity getSeverity() {
		return severity;
	}

	public void setSeverity(AISDatalinkCheckSeverity severity) {
		this.severity = severity;
	}	
	
	public List<AISStation> getInvolvedStations() {
		return involvedStations;
	}

	public void setInvolvedStations(List<AISStation> involvedStations) {
		this.involvedStations = involvedStations;
	}		
	
	public List<AISTimeslot> getInvolvedTimeslots() {
		return involvedTimeslots;
	}

	public void setInvolvedTimeslots(List<AISTimeslot> involvedTimeslots) {
		this.involvedTimeslots = involvedTimeslots;
	}	
	
	public boolean isAcknowledged() {
		return acknowledged;
	}
	
	public void setAcknowledged(boolean acknowledged) {
		this.acknowledged = acknowledged;
	}

	public String toString(){
		if(getInvolvedStations() == null) this.involvedStations = new ArrayList<AISStation>();
		if(getInvolvedTimeslots() == null) this.involvedTimeslots = new ArrayList<AISTimeslot>();
		
		String ut = id+": "+severity+" "+ruleViolated+". There are "+getInvolvedStations().size()+" involved stations ";
		if(getInvolvedStations().size() > 0)
			ut += "("+getInvolvedStations().get(0).getStationName()+(getInvolvedStations().size() > 1 ? " and "+getInvolvedStations().get(1).getStationName() : "")+") ";
		if(this.involvedTimeslots != null)
			ut += "and "+involvedTimeslots.size()+" involved timeslots...";
		return ut;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
}