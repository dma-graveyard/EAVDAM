package dk.frv.eavdam.data;

import java.sql.Date;

/**
 * @author ttesei
 * @version 1.0
 * @created 26-elo-2011 13:27:24
 */
public class AISFixedStationStatus {

	
	
	private int statusID;
	private String statusName;
	private Date startDate;
	private Date endDate;
	
	public AISFixedStationStatus(){
		
	}
	
	public AISFixedStationStatus(int id, String name, Date start, Date end){
		this.statusID = id;
		this.statusName = name;
		this.startDate = start;
		this.endDate = end;
				
	}
	
	public int getStatusID() {
		return statusID;
	}
	public void setStatusID(int statusID) {
		this.statusID = statusID;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
}

