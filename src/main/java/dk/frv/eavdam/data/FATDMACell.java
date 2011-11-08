package dk.frv.eavdam.data;

public class FATDMACell {

	private String cell = null; //Cell ID: e.g., 1-I, 2-II, etc.
	private Integer baseStationReportStartingSlot = null;
	
	private FATDMADefaultChannel channelA = null; //Values for channel A
	private FATDMADefaultChannel channelB = null; //Values for channel B
	
	public FATDMACell() {
		// TODO Auto-generated constructor stub
	}


	public FATDMADefaultChannel getChannelA() {
		return channelA;
	}


	public void setChannelA(FATDMADefaultChannel channelA) {
		this.channelA = channelA;
	}


	public FATDMADefaultChannel getChannelB() {
		return channelB;
	}


	public void setChannelB(FATDMADefaultChannel channelB) {
		this.channelB = channelB;
	}


	public String getCell() {
		return cell;
	}


	public void setCell(String cell) {
		this.cell = cell;
	}


	public Integer getBaseStationReportStartingSlot() {
		return baseStationReportStartingSlot;
	}


	public void setBaseStationReportStartingSlot(
			Integer baseStationReportStartingSlot) {
		this.baseStationReportStartingSlot = baseStationReportStartingSlot;
	}
	
	public String toString(){
		String ut = cell+" (bs start: "+this.baseStationReportStartingSlot+")\n\t"+channelA.toString()+"\n\t"+channelB.toString();
				
				
		return ut;
	}
	
}
