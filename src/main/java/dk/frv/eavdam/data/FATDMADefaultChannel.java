package dk.frv.eavdam.data;

public class FATDMADefaultChannel {
	
	String channel = null; //A or B
	
	private FATDMANode semaphoreNode = null; //Semaphore node values
	private FATDMANode nonSemaphoreNode = null; //Non-semaphore node values
	
	public FATDMADefaultChannel(){
		
	}

	public FATDMANode getSemaphoreNode() {
		return semaphoreNode;
	}
	public void setSemaphoreNode(FATDMANode semaphoreNode) {
		this.semaphoreNode = semaphoreNode;
	}
	public FATDMANode getNonSemaphoreNode() {
		return nonSemaphoreNode;
	}
	public void setNonSemaphoreNode(FATDMANode nonSemaphoreNode) {
		this.nonSemaphoreNode = nonSemaphoreNode;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String toString(){
		String ut = "Channel "+channel+"\n\t\t"+semaphoreNode.toString()+"\n\t\t"+nonSemaphoreNode.toString();
		
		return ut;
	}
	
}
