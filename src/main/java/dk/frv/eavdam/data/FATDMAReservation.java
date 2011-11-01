package dk.frv.eavdam.data;

public class FATDMAReservation {
        
	public final static String STATION_OWNERSHIP_LOCAL = "L";
	public final static String STATION_OWNERSHIP_REMOTE = "R";	
		
    private Integer startslot = null;  // 0..2249
    private Integer blockSize = null;  // 1..5
    private Integer increment = null;  // 0..1125
    private String ownership = null;  // L: use by local station, R: use by remote station

    private Integer dbID = null;
    
    public FATDMAReservation() {}
    
    public FATDMAReservation(Integer startslot, Integer blockSize, Integer increment, String ownership) {
        this.startslot = startslot;
        this.blockSize = blockSize;
        this.increment = increment;
        this.ownership = ownership;
    }
   
    public Integer getStartslot() {
        return startslot;
    }
    
    public void setStartslot(Integer startslot) {
		if (startslot != null && (startslot.intValue() < 0 || startslot.intValue() > 2249)) {
			throw new IllegalArgumentException("startslot not in range [-0 2249]");
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
		if (increment != null && (increment.intValue() < 0 || increment.intValue() > 1125)) {
			throw new IllegalArgumentException("increment not in range [0 1125]");
		}
		this.increment = increment;
    }   
    
    public String getOwnership() {
        return ownership;
    }
    
    public void setOwnership(String ownership) {
		if (ownership != null && (!ownership.equals(STATION_OWNERSHIP_LOCAL) && !ownership.equals(STATION_OWNERSHIP_REMOTE))) {
			throw new IllegalArgumentException("ownership not one of allowed values [" + STATION_OWNERSHIP_LOCAL + ", " + STATION_OWNERSHIP_REMOTE +"]");
		}
        this.ownership = ownership;
    }

	public Integer getDbID() {
		return dbID;
	}

	public void setDbID(Integer dbID) {
		this.dbID = dbID;
	}

}
