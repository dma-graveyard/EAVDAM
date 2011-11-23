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
	
}