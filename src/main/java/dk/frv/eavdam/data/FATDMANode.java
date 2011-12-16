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
 * Class for a FATDMA node.
 */
public class FATDMANode {
	
	private Boolean semaphore = null;
	
	private Integer startingSlot = null;
	private Integer blockSize = null;  // 1..5, 6 indicates selection between 0,1,2,3, and 7 indicates 1 or 2
	private Integer increment = null;  // 0..324000
	private String usage = null;
	
	public FATDMANode() {}

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
	
	public String getReservationBlockSizeString() {
		if (this.blockSize == 6) {
			return "0, 1, 2 or 3";
		} else if (this.blockSize == 7) {
			return "1 or 2";
		} else {
			return this.blockSize + "";
		}
	}

	public String toString(){
		return (semaphore ? "" : "Non-") + "Semaphore node: " + startingSlot+" | " +
			this.getReservationBlockSizeString() + " | " + increment+" | " + usage;
	}

}