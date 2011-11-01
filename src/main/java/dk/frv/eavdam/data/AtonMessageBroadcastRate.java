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
	
    private Integer dbID = null;
    
    public AtonMessageBroadcastRate() {}
    
    public AtonMessageBroadcastRate(String accessScheme, Integer messageID, Integer utcHour, Integer utcMinute, Integer startslot, Integer blockSize, Integer increment) {
        setAccessScheme(accessScheme);
        setMessageID(messageID);
        setUTCHour(utcHour);
        setUTCMinute(utcMinute);
        setStartslot(startslot);
        setBlockSize(blockSize);
        setIncrement(increment);
	}

    public String getAccessScheme() {
        return accessScheme;
    }
    
    public void setAccessScheme(String accessScheme) {
		if (accessScheme != null && (!accessScheme.equals(FATDMA_ACCESS_SCHEME) && !accessScheme.equals(RATDMA_ACCESS_SCHEME) && !accessScheme.equals(CSTDMA_ACCESS_SCHEME))) {
			throw new IllegalArgumentException("Access Scheme not one of allowed values [" + FATDMA_ACCESS_SCHEME + ", " + RATDMA_ACCESS_SCHEME +", " + CSTDMA_ACCESS_SCHEME + "]");
		}
        this.accessScheme = accessScheme;
    }

    public Integer getMessageID() {
        return messageID;
    }
    
    public void setMessageID(Integer messageID) {
		if (messageID != null && (messageID.intValue() < 0 || messageID.intValue() > 64)) {
			throw new IllegalArgumentException("Message ID not in range [0 64]");
		}
        this.messageID = messageID;
    }

    public Integer getUTCHour() {
        return utcHour;
    }
    
    public void setUTCHour(Integer utcHour) {
		if (utcHour != null && (utcHour.intValue() < 0 || utcHour.intValue() > 24)) {
			throw new IllegalArgumentException("UTC Hour not in range [0 24]");
		}
        this.utcHour = utcHour;
    }
    
	public Integer getUTCMinute() {
        return utcMinute;
    }
    
    public void setUTCMinute(Integer utcMinute) {
		if (utcMinute != null && (utcMinute.intValue() < 0 || utcMinute.intValue() > 60)) {
			throw new IllegalArgumentException("UTC Minute not in range [0 60]");
		}
        this.utcMinute = utcMinute;
    }
   
    public Integer getStartslot() {
        return startslot;
    }
    
    public void setStartslot(Integer startslot) {
		if (startslot != null && startslot.intValue() != 4095 && (startslot.intValue() < 0 || startslot.intValue() > 2249)) {
			throw new IllegalArgumentException("Startslot not in range [0 2249] or 4095");
		}
        this.startslot = startslot;
    }
	
    public Integer getBlockSize() {
        return blockSize;
    }
    
    public void setBlockSize(Integer blockSize) {
		if (blockSize != null && (blockSize.intValue() < 1 || blockSize.intValue() > 5)) {
			throw new IllegalArgumentException("Block Size not in range [1 5]");
		}
		this.blockSize = blockSize;
    }
    
	public Integer getIncrement() {
        return increment;
    }
    
    public void setIncrement(Integer increment) {
		if (increment != null && (increment.intValue() < 0 || increment.intValue() > 324000)) {
			throw new IllegalArgumentException("Increment not in range [0 324000]");
		}
		this.increment = increment;
    }


	public Integer getDbID() {
		return dbID;
	}

	public void setDbID(Integer dbID) {
		this.dbID = dbID;
	}


	public boolean equals(Object aThat) {
	
		if (this == aThat) {
			return true;
		}
		
		if (!(aThat instanceof AtonMessageBroadcastRate)) {
			return false;
		}
  
		AtonMessageBroadcastRate that = (AtonMessageBroadcastRate) aThat;

		if (!that.getAccessScheme().equals(accessScheme)) {
			return false;
		}
		
		if (!that.getMessageID().equals(messageID)) {
			return false;
		}

		if (!that.getUTCHour().equals(utcHour)) {
			return false;
		}
		
		if (!that.getUTCMinute().equals(utcMinute)) {
			return false;
		}		
		
		if (!that.getStartslot().equals(startslot)) {
			return false;
		}
  
		if (!that.getBlockSize().equals(blockSize)) {
			return false;
		}
		
		if (!that.getIncrement().equals(increment)) {
			return false;
		}
    
		return true;
	}		

}
