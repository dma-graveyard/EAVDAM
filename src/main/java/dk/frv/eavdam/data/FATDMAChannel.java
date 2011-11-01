package dk.frv.eavdam.data;

public class FATDMAChannel {

	private String channelName;
	private int dbid;
	
    public FATDMAChannel() {}

	public FATDMAChannel(String channelName) {
		this.channelName = channelName;		
	}
	

	public String getChannelName(){
		return this.channelName;
	}

	public void setChannelName(String name){
		this.channelName = name;
	}
	
	public int getDBID() {
		return dbid;
	}

	public void setDBID(int dbid) {
		this.dbid = dbid;
	}
}
