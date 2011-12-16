/*
* Copyright 2011 Danish Maritime Safety Administration. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without
* modification, are permitted provided that the following conditions are met:
*
* 1. Redistributions of source code must retain the above copyright notice,
* this list of conditions and the following disclaimer.
*
* 2. Redistributions in binary form must reproduce the above copyright notice,
* this list of conditions and the following disclaimer in the documentation and/or
* other materials provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY Danish Maritime Safety Administration ``AS IS''
* AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
* IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
* DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> OR CONTRIBUTORS BE LIABLE FOR
* ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
* (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
* LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
* (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
* SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

* The views and conclusions contained in the software and documentation are those
* of the authors and should not be interpreted as representing official policies,
* either expressed or implied, of Danish Maritime Safety Administration.
*
*/
package dk.frv.eavdam.data;

/**
 * Class for a FATDMA reservation.
 */
public class FATDMAReservation {
        
	public final static String STATION_OWNERSHIP_LOCAL = "L";
	public final static String STATION_OWNERSHIP_REMOTE = "R";	
		
    private Integer startslot = null;
    private Integer blockSize = null;
    private Integer increment = null;
    private String ownership = null;
	private String usage = null;

    private Integer dbID = null;
    
    public FATDMAReservation() {}
    
    public FATDMAReservation(Integer startslot, Integer blockSize, Integer increment, String ownership, String usage) {
        setStartslot(startslot);
        setBlockSize(blockSize);
        setIncrement(increment);
        setOwnership(ownership);
		setUsage(usage);
    }
   
    public Integer getStartslot() {
        return startslot;
    }
    
	/**
	 * Also validates the startslot. Throws IllegalArgumentException if startslot is not in range 0-2249.
	 *
	 * @param startslot  FATDMA startslot
	 */
    public void setStartslot(Integer startslot) {
		if (startslot != null && (startslot.intValue() < 0 || startslot.intValue() > 2249)) {
			throw new IllegalArgumentException("Startslot not in range [0 2249]");
		}
        this.startslot = startslot;
    }
    
    public Integer getBlockSize() {
        return blockSize;
    }
    
	/**
	 * Also validates the block size. Throws IllegalArgumentException if block size is not in range 1-5.
	 *
	 * @param blockSize  FATDMA block size
	 */	
    public void setBlockSize(Integer blockSize) {
		if (blockSize != null && (blockSize.intValue() < 1 || blockSize.intValue() > 5)) {
			throw new IllegalArgumentException("Block Size not in range [1 5]");
		}
		this.blockSize = blockSize;
    }
    
	public Integer getIncrement() {
        return increment;
    }
    
	/**
	 * Also validates the increment. Throws IllegalArgumentException if increment is not in range 0-1125.
	 *
	 * @param increment  FATDMA increment
	 */		
    public void setIncrement(Integer increment) {
		if (increment != null && (increment.intValue() < 0 || increment.intValue() > 1125)) {
			throw new IllegalArgumentException("Increment not in range [0 1125]");
		}
		this.increment = increment;
    }   
    
    public String getOwnership() {
        return ownership;
    }
    
	/**
	 * Also validates the ownership. Throws IllegalArgumentException if ownership is not either STATION_OWNERSHIP_LOCAL or STATION_OWNERSHIP_REMOTE.
	 *
	 * @param ownership  FATDMA ownership
	 */		
    public void setOwnership(String ownership) {
		if (ownership != null && (!ownership.equals(STATION_OWNERSHIP_LOCAL) && !ownership.equals(STATION_OWNERSHIP_REMOTE))) {
			throw new IllegalArgumentException("Ownership not one of allowed values [" + STATION_OWNERSHIP_LOCAL + ", " + STATION_OWNERSHIP_REMOTE +"]");
		}
        this.ownership = ownership;
    }
	
	public String getUsage() {
		return usage;
	}
	
	public void setUsage(String usage) {
		this.usage = usage;
	}
	
	public boolean equals(Object aThat) {
	
		if (this == aThat) {
			return true;
		}
		
		if (!(aThat instanceof FATDMAReservation)) {
			return false;
		}
  
		FATDMAReservation that = (FATDMAReservation) aThat;
		
		if (!that.getStartslot().equals(startslot)) {
			return false;
		}
  
		if (!that.getBlockSize().equals(blockSize)) {
			return false;
		}
		
		if (!that.getIncrement().equals(increment)) {
			return false;
		}

		if (!that.getOwnership().equals(ownership)) {
			return false;
		}
		
		if (!that.getUsage().equals(usage)) {
			return false;
		}
    
		return true;
	}

	public Integer getDbID() {
		return dbID;
	}

	public void setDbID(Integer dbID) {
		this.dbID = dbID;
	}
	
	public String toString(){
		return "id: " + this.dbID + ", owner: " + this.ownership + ", increment: " + this.increment + ", block size: " +
			this.blockSize + ", start slot: " + this.startslot + ", usage:" + this.usage;
	}

}
