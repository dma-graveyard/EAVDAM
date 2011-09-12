package dk.frv.eavdam.data;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class ActiveStation {

    private List<AISFixedStationData> stations = new ArrayList<AISFixedStationData>(); 
    Map<EAVDAMUser, AISFixedStationData> proposals = new HashMap<EAVDAMUser, AISFixedStationData>();

	public ActiveStation() {}

	public List<AISFixedStationData> getStations() {
		return stations;
	}

	public void setStations(List<AISFixedStationData> stations) {
		this.stations = stations;
	}
	
	public Map<EAVDAMUser, AISFixedStationData> getProposals() {
		return proposals;
	}

	public void setProposals(Map<EAVDAMUser, AISFixedStationData> proposals) {
		this.proposals = proposals;
	}	

}