package dk.frv.eavdam.layers;

import java.util.ArrayList;

import com.bbn.openmap.omGraphics.OMCircle;

public class OMBaseStation extends OMCircle {

	private static final long serialVersionUID = 1L;

	private ArrayList<double[]> workingArea;
	private String name;
	private int antennaHeight;
	private double lat, lon;
	
	
	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public OMBaseStation(double lat, double lon, String name, int antennaHeight) {
		super(lat, lon, 0.01);
		this.name = name;
		this.antennaHeight = antennaHeight;
		
		this.lat = lat;
		this.lon = lon;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAntennaHeight() {
		return antennaHeight;
	}

	public void setAntennaHeight(int antennaHeight) {
		this.antennaHeight = antennaHeight;
	}

	public ArrayList<double[]> getReachArea() {
		return workingArea;
	}

	public void setReachArea(ArrayList<double[]> workingArea) {
		this.workingArea = workingArea;
	}
	
	/**
	 * For testing
	 */
	public void randomReachArea(int numberOfPoints){
		this.workingArea = new ArrayList<double[]>();
		for(int i = 0; i < numberOfPoints; ++i){
			double[] points = new double[2];
			int r1 = (Math.random() < 0.5 ? -1 : 1);
			int r2 = (Math.random() < 0.5 ? -1 : 1);
			
			points[0] = 0.5*r1 + lat;
			points[1] = 0.5*r2 + lon;
			this.workingArea.add(points);
		}
	}
	
	public void orderReachArea(){

		ArrayList<double[]> latOrder = new ArrayList<double[]>();
		
		
		for(double[] p : this.workingArea){
			if(latOrder.size() == 0){
				latOrder.add(p);
			}else{
				for(int i = 0; i < latOrder.size(); ++i){
					if(latOrder.get(i)[0] > p[0]){
						latOrder.add(i,p);
						break;
					}
					
					if(i == latOrder.size() - 1){
						latOrder.add(p);
						break;
					}
				}
			}
		}
		
		System.out.println(latOrder.size());
		
		ArrayList<double[]> order = new ArrayList<double[]>();
		//First go through the ascending
		double[] startingPoint = latOrder.get(0);
		order.add(startingPoint);
		for(int i = 1; i < latOrder.size(); ++i){
			if(latOrder.get(i)[1] - startingPoint[1] >= 0) order.add(latOrder.get(i));
		}
		
		//The descending values starting from the bottom
		for(int i = latOrder.size() - 1; i < 0; --i){
			if(latOrder.get(i)[1] - startingPoint[1] <= 0) order.add(latOrder.get(i));
		}
		
		this.workingArea = order;
	}
}
