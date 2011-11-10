package dk.frv.eavdam.data;

public class FATDMANode {
	private Boolean semaphore = null;
	
	private Integer startingSlot = null;
	private Integer blockSize = null;  // 1..5, 6 indicates selection between 0,1,2,3, and 7 indicates 1 or 2
	private Integer increment = null;  // 0..324000
	private String usage = null;
	
	
	
	public FATDMANode() {
		// TODO Auto-generated constructor stub
	}

	public boolean isSemaphore() {
		return semaphore;
	}

	public void setSemaphore(boolean semaphore) {
		this.semaphore = semaphore;
	}

	public Integer getStartingSlot() {
		return startingSlot;
	}

	public void setStartingSlot(Integer startingSlot) {
		this.startingSlot = startingSlot;
	}

	public Integer getBlockSize() {
		return blockSize;
	}

	public void setBlockSize(Integer blockSize) {
		this.blockSize = blockSize;
	}

	public Integer getIncrement() {
		return increment;
	}

	public void setIncrement(Integer increment) {
		this.increment = increment;
	}

	public String getUsage() {
		return usage;
	}

	public void setUsage(String usage) {
		this.usage = usage;
	}
	
	public String getReservationBlockSizeString(){
		if(this.blockSize == 6) return "0, 1, 2 or 3";
		else if(this.blockSize == 7) return "1 or 2";
		else return this.blockSize+"";
	}

	public String toString(){
		String ut = (semaphore ? "" : "Non-")+"Semaphore node: "+startingSlot+" | "+this.getReservationBlockSizeString()+" | "+increment+" | "+usage;
		
		
		return ut;
	}
}
