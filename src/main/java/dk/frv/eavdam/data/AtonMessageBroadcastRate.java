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
 * Class for Aton message broadcast rate (FATDMA information for AIS Aton stations).
 */
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
	private String usage = null;
	
    private Integer dbID = null;
    
    public AtonMessageBroadcastRate() {}
    
    public AtonMessageBroadcastRate(String accessScheme, Integer messageID, Integer utcHour, Integer utcMinute, Integer startslot, Integer blockSize, Integer increment, String usage) {
        setAccessScheme(accessScheme);
        setMessageID(messageID);
        setUTCHour(utcHour);
        setUTCMinute(utcMinute);
        setStartslot(startslot);
        setBlockSize(blockSize);
        setIncrement(increment);
		setUsage(usage);
	}

    public String getAccessScheme() {
        return accessScheme;
    }
    
	/**
	 * Also validates the access scheme. Throws IllegalArgumentException if access scheme is not FATDMA_ACCESS_SCHEME, RATDMA_ACCESS_SCHEME or CSTDMA_ACCESS_SCHEME.
	 *
	 * @param accessScheme  Access scheme
	 */		
    public void setAccessScheme(String accessScheme) {
		if (accessScheme != null && (!accessScheme.equals(FATDMA_ACCESS_SCHEME) && !accessScheme.equals(RATDMA_ACCESS_SCHEME) && !accessScheme.equals(CSTDMA_ACCESS_SCHEME))) {
			throw new IllegalArgumentException("Access Scheme not one of allowed values [" + FATDMA_ACCESS_SCHEME + ", " + RATDMA_ACCESS_SCHEME +", " + CSTDMA_ACCESS_SCHEME + "]");
		}
        this.accessScheme = accessScheme;
    }

    public Integer getMessageID() {
        return messageID;
    }
    
	/**
	 * Also validates the message ID. Throws IllegalArgumentException if message ID is not in range 0-64.
	 *
	 * @param messageID  Message ID
	 */	
    public void setMessageID(Integer messageID) {
		if (messageID != null && (messageID.intValue() < 0 || messageID.intValue() > 64)) {
			throw new IllegalArgumentException("Message ID not in range [0 64]");
		}
        this.messageID = messageID;
    }

    public Integer getUTCHour() {
        return utcHour;
    }
    
	/**
	 * Also validates the UTC hour. Throws IllegalArgumentException if UTC hour is not in range 0-24.
	 *
	 * @param utcHour  UTC hour
	 */		
    public void setUTCHour(Integer utcHour) {
		if (utcHour != null && (utcHour.intValue() < 0 || utcHour.intValue() > 24)) {
			throw new IllegalArgumentException("UTC Hour not in range [0 24]");
		}
        this.utcHour = utcHour;
    }
    
	public Integer getUTCMinute() {
        return utcMinute;
    }
    
	/**
	 * Also validates the UTC minute. Throws IllegalArgumentException if UTC minute is not in range 0-60.
	 *
	 * @param utcMinute  UTC minute
	 */	
    public void setUTCMinute(Integer utcMinute) {
		if (utcMinute != null && (utcMinute.intValue() < 0 || utcMinute.intValue() > 60)) {
			throw new IllegalArgumentException("UTC Minute not in range [0 60]");
		}
        this.utcMinute = utcMinute;
    }
   
    public Integer getStartslot() {
        return startslot;
    }
    
	/**
	 * Also validates the startslot. Throws IllegalArgumentException if startslot is not in range 0-2249 or 4095.
	 *
	 * @param startslot  Startslot
	 */	
    public void setStartslot(Integer startslot) {
		if (startslot != null && startslot.intValue() != 4095 && (startslot.intValue() < 0 || startslot.intValue() > 2249)) {
			throw new IllegalArgumentException("Startslot not in range [0 2249] or 4095");
		}
        this.startslot = startslot;
    }
	
    public Integer getBlockSize() {
        return blockSize;
    }
    
	/**
	 * Also validates the block size. Throws IllegalArgumentException if block size is not in range 1-5.
	 *
	 * @param blockSize  Block size
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
	 * Also validates the increment. Throws IllegalArgumentException if increment is not in range 0-324000.
	 *
	 * @param increment  Increment
	 */		
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
	
		if (!that.getUsage().equals(usage)) {
			return false;
		}
        
		return true;
	}		

}