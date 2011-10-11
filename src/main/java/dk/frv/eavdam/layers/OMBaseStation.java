package dk.frv.eavdam.layers;

import com.bbn.openmap.layer.location.ByteRasterLocation;
import dk.frv.eavdam.data.AISFixedStationData;
import java.util.ArrayList;


public class OMBaseStation extends ByteRasterLocation {

	private static final long serialVersionUID = 1L;

	private ArrayList<double[]> workingArea;
	private Object datasetSource;
	private AISFixedStationData stationData;	

	public OMBaseStation(Object datasetSource, AISFixedStationData stationData, byte[] bytearr) {	    	    
		super(stationData.getLat(), stationData.getLon(), stationData.getStationName(), bytearr);		
		this.datasetSource = datasetSource;
		this.stationData = stationData;		
        this.setShowName(false);
	}
	
	public Object getDatasetSource() {
		return datasetSource;
	}
	
	public void setDatasetSource(Object datasetSource) {
		this.datasetSource = datasetSource;
	}
	
	public AISFixedStationData getStationData() {
		return stationData;
	}

	public void setStationData(AISFixedStationData stationData) {
		this.stationData = stationData;
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
			
			points[0] = 0.5*r1 + stationData.getLat();
			points[1] = 0.5*r2 + stationData.getLon();
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