package dk.frv.eavdam.data;

public class FATDMAChannel {

	private String channelName;

    public FATDMAChannel() {}

	public FATDMAChannel(String channelName) {
		this.channelName = channelName;		
	}
	
	public String getChannelName() {
		return channelName;
	}
	
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

}
