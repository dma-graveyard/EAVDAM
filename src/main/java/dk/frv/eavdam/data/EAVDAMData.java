package dk.frv.eavdam.data;

import java.util.ArrayList;
import java.util.List;

public class EAVDAMData {

	private EAVDAMUser user;
	private List<AISFixedStationData> stations = new ArrayList<AISFixedStationData>();

	public EAVDAMUser getUser() {
		return user;
	}

	public void setUser(EAVDAMUser user) {
		this.user = user;
	}

	public AISFixedStationData[] getStations() {
		return stations.toArray(new AISFixedStationData[stations.size()]);
	}

	public void addStation(AISFixedStationData station) {
		if (station != null) {
			this.stations.add(station);
		}
	}

	public void setStations(List<AISFixedStationData> stations) {
		this.stations.clear();
		if (stations != null) {
			this.stations.addAll(stations);
		}
	}

	@Override
	public String toString() {
		return "EAVDAMData [user=" + user + ", stations=" + stations + "]";
	}
}
