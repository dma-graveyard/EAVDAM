package dk.frv.eavdam.data;

import java.util.ArrayList;
import java.util.List;

public class OtherUserStations {

    private EAVDAMUser user;
    private List<ActiveStation> stations = new ArrayList<ActiveStation>(); 

	public OtherUserStations() {}

	public EAVDAMUser getUser() {
		return user;
	}

	public void setUser(EAVDAMUser user) {
		this.user = user;
	}


	public List<ActiveStation> getStations() {
		return stations;
	}

	public void setStations(List<ActiveStation> stations) {
		this.stations = stations;
	}

}