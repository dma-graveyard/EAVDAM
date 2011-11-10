package dk.frv.eavdam.data;

public class TimeslotReservation {

	private int startslot;
	private int blockSize;
	private int increment;

	public TimeslotReservation(int startslot, int blockSize, int increment) {
		this.startslot = startslot;
		this.blockSize = blockSize;
		this.increment = increment;		
	}
	
	public int getStartslot() {
		return startslot;
	}
	
	public void setStartslot(int startslot) {
		this.startslot = startslot;
	}
	
	public int getBlockSize() {
		return blockSize;
	}
	
	public void setBlockSize(int blockSize) {
		this.blockSize = blockSize;
	}

	public int getIncrement() {
		return increment;
	}
	
	public void setIncrement(int increment) {
		this.increment = increment;
	}
	
}