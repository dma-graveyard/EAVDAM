package dk.frv.eavdam.data;

import java.util.ArrayList;
import java.util.List;

/**
 * A class for holding all information for AIS base station coverage area.
 * @author ttesei
 * @version 1.0
 * @created 26-elo-2011 13:27:24
 */
public class AISFixedStationCoverage {
	
	List<Object> coveragePoints;
	
	
	public AISFixedStationCoverage(){

	}

	
	public void addCoveragePoint(double lat, double lon){
		if(this.coveragePoints == null) coveragePoints = new ArrayList<Object>();
		
		//TODO Add a coverage object.
	}


	public List<Object> getCoveragePoints() {
		return coveragePoints;
	}


	public void setCoveragePoints(List<Object> coveragePoints) {
		this.coveragePoints = coveragePoints;
	}

}