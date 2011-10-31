package dk.frv.eavdam.data;

public class AtonMessageBroadcastRate {
        
	public final static String FATDMA_ACCESS_SCHEME = "FATDMA";
	public final static String RATDMA_ACCESS_SCHEME = "RATDMA";
	public final static String CSTDMA_ACCESS_SCHEME = "CSTDMA";	
	
    private String accessScheme = null;  // FATDMA, RATDMA or CSTDMA
	private Integer messageID = null;  // 0..64 (Identifies which message type this transmission relates to)
    private Integer utcHour = null;  // 0-23; 24 = UTC hour not available (UTC hour of first transmission of the day)
    private Integer utcMinute = null;  // 0-59; 60 = UTC minute not available (UTC minute of first transmission of the day)
    private Integer startslot = null;  // 0-2249; 4095 = discontinue broadcast (Only relevant for FATDMA)
    private Integer blockSize = null;  // 1..5
    private Integer increment = null;  // 0..324000
	
    public AtonMessageBroadcastRate() {}
    
    public AtonMessageBroadcastRate(String accessScheme, Integer messageID, Integer utcHour, Integer utcMinute, Integer startslot, Integer blockSize, Integer increment) {
        this.accessScheme = accessScheme;
        this.messageID = messageID;
        this.utcHour = utcHour;
        this.utcMinute = utcMinute;
        this.startslot = startslot;
        this.blockSize = blockSize;
        this.increment = increment;
	}

    public String getAccessScheme() {
        return accessScheme;
    }
    
    public void settAccessScheme(String accessScheme) {
		if (accessScheme != null && (!accessScheme.equals(FATDMA_ACCESS_SCHEME) && !accessScheme.equals(RATDMA_ACCESS_SCHEME) && !accessScheme.equals(CSTDMA_ACCESS_SCHEME))) {
			throw new IllegalArgumentException("accessScheme not one of allowed values [" + FATDMA_ACCESS_SCHEME + ", " + RATDMA_ACCESS_SCHEME +", " + CSTDMA_ACCESS_SCHEME + "]");
		}
        this.accessScheme = accessScheme;
    }

    public Integer getMessageID() {
        return messageID;
    }
    
    public void setMessageID(Integer messageID) {
		if (messageID != null && (messageID.intValue() < 0 || messageID.intValue() > 64)) {
			throw new IllegalArgumentException("messageID not in range [0 64]");
		}
        this.messageID = messageID;
    }

    public Integer getUTCHour() {
        return utcHour;
    }
    
    public void setUTCHour(Integer utcHour) {
		if (utcHour != null && (utcHour.intValue() < 0 || utcHour.intValue() > 24)) {
			throw new IllegalArgumentException("utcHour not in range [0 24]");
		}
        this.utcHour = utcHour;
    }
    
	public Integer getUTMinute() {
        return utcMinute;
    }
    
    public void setUTCMinute(Integer utcMinute) {
		if (utcMinute != null && (utcMinute.intValue() < 0 || utcMinute.intValue() > 60)) {
			throw new IllegalArgumentException("utcMinute not in range [0 60]");
		}
        this.utcMinute = utcMinute;
    }
   
    public Integer getStartslot() {
        return startslot;
    }
    
    public void setStartslot(Integer startslot) {
		if (startslot != null && startslot.intValue() != 4095 && (startslot.intValue() < 0 || startslot.intValue() > 2249)) {
			throw new IllegalArgumentException("startslot not in range [-0 2249] or 4095");
		}
        this.startslot = startslot;
    }
	
    public Integer getBlockSize() {
        return blockSize;
    }
    
    public void setBlockSize(Integer blockSize) {
		if (blockSize != null && (blockSize.intValue() < 1 || blockSize.intValue() > 5)) {
			throw new IllegalArgumentException("blockSize not in range [1 5]");
		}
		this.blockSize = blockSize;
    }
    
	public Integer getIncrement() {
        return increment;
    }
    
    public void setIncrement(Integer increment) {
		if (increment != null && (increment.intValue() < 0 || increment.intValue() > 324000)) {
			throw new IllegalArgumentException("increment not in range [0 324000]");
		}
		this.increment = increment;
    }

}
