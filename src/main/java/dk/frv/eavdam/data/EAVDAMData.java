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

import java.util.ArrayList;
import java.util.List;

/**
 * Class for all the application data.
 */
public class EAVDAMData {

	private EAVDAMUser user;
	
	private List<AISFixedStationData> stations = new ArrayList<AISFixedStationData>();

	private List<ActiveStation> activeStations = new ArrayList<ActiveStation>();
    private List<OtherUserStations> otherUsersStations = new ArrayList<OtherUserStations>();
    private List<Simulation> simulatedStations = new ArrayList<Simulation>();
    private List<AISFixedStationData> oldStations = new ArrayList<AISFixedStationData>();	

	private List<AISDatalinkCheckIssue> aisDatalinkCheckIssues;
    
	public EAVDAMUser getUser() {
		return user;
	}

	public void setUser(EAVDAMUser user) {
		this.user = user;
	}
    
    public List<ActiveStation> getActiveStations() {
        return activeStations;
    }
    
    public void setActiveStations(List<ActiveStation> activeStations) {
        this.activeStations = activeStations;
    }

    public List<OtherUserStations> getOtherUsersStations() {
        return otherUsersStations;
    }
    
    public void setOtherUsersStations(List<OtherUserStations> otherUsersStations) {
        this.otherUsersStations = otherUsersStations;
    }    

    public List<Simulation> getSimulatedStations() {
        return simulatedStations;
    }
    
    public void setSimulatedStations(List<Simulation> simulatedStations) {
        this.simulatedStations = simulatedStations;
    }    
    
    public List<AISFixedStationData> getOldStations() {
        return oldStations;
    }
    
    public void setOldStations(List<AISFixedStationData> oldStations) {
        this.oldStations = oldStations;
    }
	
	public List<AISDatalinkCheckIssue> getAISDatalinkCheckIssues() {
		return aisDatalinkCheckIssues;
	}
	
	public void setAISDatalinkCheckIssues(List<AISDatalinkCheckIssue> aisDatalinkCheckIssues) {
		this.aisDatalinkCheckIssues = aisDatalinkCheckIssues;
	}	
    
    // DO NOT REMOVE. NEEDED FOR XML TO SIMPLIFY PARSING
	public AISFixedStationData[] getStations() {
		return stations.toArray(new AISFixedStationData[stations.size()]);
	}
	
	public void addStation(AISFixedStationData station) {
	    if (station == null) {
	        stations = new ArrayList<AISFixedStationData>();
	    }
	    stations.add(station);
	}
		
	public void setStations(List<AISFixedStationData> stations) {
		this.stations.clear();
		if (stations != null) {
			this.stations.addAll(stations);
		}
	}
	// DO NOT REMOVE. NEEDED FOR XML

}
