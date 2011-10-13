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
	
	ArrayList<double[]> coveragePoints;
	int coverageType; //1 = transmit, 2 = receiver, 3 = interference
	
	public AISFixedStationCoverage(){

	}

	
	public void addCoveragePoint(double lat, double lon){
		if(this.coveragePoints == null) coveragePoints = new ArrayList<double[]>();
		
		double[] c = new double[2];
		c[0] = lat;
		c[1] = lon;
		
		coveragePoints.add(c);
		
	}


	public ArrayList<double[]> getCoveragePoints() {
		return coveragePoints;
	}


	public void setCoveragePoints(ArrayList<double[]> coveragePoints) {
		this.coveragePoints = coveragePoints;
	}


	public int getCoverageType() {
		return coverageType;
	}


	public void setCoverageType(int coverageType) {
		this.coverageType = coverageType;
	}

}