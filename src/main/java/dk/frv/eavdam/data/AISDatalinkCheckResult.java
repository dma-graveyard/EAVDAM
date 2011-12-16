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

import java.util.List;

/**
 * This class contains the result of a AIS VHF Datalink Health Check operation.
 */
public class AISDatalinkCheckResult {

	private List<AISDatalinkCheckIssue> issues;
	private List<AISDatalinkCheckArea> areas;
	
	public AISDatalinkCheckResult() {}
	
	public AISDatalinkCheckResult(List<AISDatalinkCheckIssue> issues, List<AISDatalinkCheckArea> areas) {
		this.issues = issues;
		this.areas = areas;
	}

	public List<AISDatalinkCheckIssue> getIssues() {
		return issues;
	}

	public void setIssues(List<AISDatalinkCheckIssue> issues) {
		this.issues = issues;
	}
	
	public List<AISDatalinkCheckArea> getAreas() {
		return areas;
	}

	public void setAreas(List<AISDatalinkCheckArea> areas) {
		this.areas = areas;
	}	
	
}