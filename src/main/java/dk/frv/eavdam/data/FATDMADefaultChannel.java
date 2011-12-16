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
 * Class for a FATDMA default channel.
 */
public class FATDMADefaultChannel {
	
	String channel = null;  //A or B
	
	private FATDMANode semaphoreNode = null;  //Semaphore node values
	private FATDMANode nonSemaphoreNode = null;  //Non-semaphore node values
	
	public FATDMADefaultChannel() {}

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
		return "Channel " + channel + "\n\t\t" + semaphoreNode.toString() + "\n\t\t" + nonSemaphoreNode.toString();
	}
	
}
