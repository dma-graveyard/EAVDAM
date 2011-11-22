package dk.frv.eavdam.data;

import java.util.List;

/**
 * This class contains the result of a AIS VHF Datalink Health Check operation.
 */
public class AISDatalinkCheckResult {

	private List<AISDatalinkCheckIssue> issues;
	private List<AISDatalinkCheckArea> areas;
	
	public AISDatalinkCheckResult() {}
	
	public AISDatalinkCheckResult(List<AISDatalinkCheckIssue> issues, List<AISDatalinkCheckArea> areas) {
		this.issues = issues;
		this.areas = areas;
	}

	public List<AISDatalinkCheckIssue> getIssues() {
		return issues;
	}

	public void setIssues(List<AISDatalinkCheckIssue> issues) {
		this.issues = issues;
	}
	
	public List<AISDatalinkCheckArea> getAreas() {
		return areas;
	}

	public void setAreas(List<AISDatalinkCheckArea> areas) {
		this.areas = areas;
	}	
	
}