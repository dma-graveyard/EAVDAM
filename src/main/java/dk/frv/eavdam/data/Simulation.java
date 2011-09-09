package dk.frv.eavdam.data;

import java.util.ArrayList;
import java.util.List;

public class Simulation {

    private String name;
    private List<AISFixedStationData> stations = new ArrayList<AISFixedStationData>(); 

	public Simulation() {}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<AISFixedStationData> getStations() {
		return stations;
	}

	public void setStations(List<AISFixedStationData> stations) {
		this.stations = stations;
	}

}