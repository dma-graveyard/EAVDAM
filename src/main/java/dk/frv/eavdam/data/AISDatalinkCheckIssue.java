package dk.frv.eavdam.data;

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
		return id+": "+severity+" "+ruleViolated+". There are "+getInvolvedStations().size()+" involved stations " +
				"("+getInvolvedStations().get(0).getStationName()+(getInvolvedStations().size() > 1 ? " and "+getInvolvedStations().get(1).getStationName() : "")+") and "+involvedTimeslots.size()+" involved timeslots...";
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
}