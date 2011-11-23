package dk.frv.eavdam.healthcheck;

import java.util.ArrayList;
import java.util.List;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.geom.PrecisionModel;

public class PointInPolygon {

	List<AISVertex> orderedList = null;
	
	int stationDBID = -1;
	double maxLat = 0;
	double maxLon = 0;
	double minLat = 0;
	double minLon = 0;
	
	
	public PointInPolygon() {
		// TODO Auto-generated constructor stub
	}
	
	public static boolean isPointInPolygon(List<double[]> polygon, double[] point){
		if(polygon == null || point == null || polygon.size() < 3 || point.length != 2) return false;
		
		List<Coordinate> coordinates = new ArrayList<Coordinate>();

		for(int i = 0; i < polygon.size(); ++i){
			double[] start = polygon.get(i);


			Coordinate c = new Coordinate(start[0],start[1]);
		

			
			coordinates.add(c);
		}
		

		
		LinearRing shell = new LinearRing(coordinates.toArray(new Coordinate[coordinates.size()]), new PrecisionModel(), 0);
		Polygon poly = new Polygon(shell, null, new GeometryFactory());
		
		Coordinate c = new Coordinate(point[0],point[1]);
		Point p = new Point(c, new PrecisionModel(), 0);
		
		return p.intersects(poly);
		
	}
	
	public static boolean isPolygonIntersection(List<double[]> polygon1, List<double[]> polygon2){
		List<Coordinate> coordinates = new ArrayList<Coordinate>();
		for(int i = 0; i < polygon1.size(); ++i){
			double[] start = polygon1.get(i);

			Coordinate c = new Coordinate(start[0],start[1]);
		
			coordinates.add(c);
		}
		
		LinearRing shell = new LinearRing(coordinates.toArray(new Coordinate[coordinates.size()]), new PrecisionModel(), 0);
		Polygon poly1 = new Polygon(shell, null, new GeometryFactory());
		
		List<Coordinate> coordinates2 = new ArrayList<Coordinate>();
		for(int i = 0; i < polygon2.size(); ++i){
			double[] start = polygon2.get(i);

			Coordinate c = new Coordinate(start[0],start[1]);
		
			coordinates2.add(c);
		}

		LinearRing shell2 = new LinearRing(coordinates2.toArray(new Coordinate[coordinates2.size()]), new PrecisionModel(), 0);
		Polygon poly2 = new Polygon(shell2, null, new GeometryFactory());
		
		return poly1.intersects(poly2);
		
	}
	
	
	public Polygon createOrderedPolygon(List<double[]> points, int stationDBID){
		if(points == null || points.size() < 2) return null;
		
		maxLat = Double.MIN_VALUE;
		maxLon = Double.MIN_VALUE;
		minLat = Double.MAX_VALUE;
		minLon = Double.MAX_VALUE;
		
		this.stationDBID = stationDBID;
		

		
		List<AISVertex> tempList = new ArrayList<AISVertex>();
		

		//p[0] = lat, p[1] = lon
		for(int i = 0; i < points.size() - 1; ++i){
			if(true) break;
			
			double[] start = points.get(i);
			double[] end = points.get(i+1);
			
			Coordinate c = new Coordinate(start[0],start[1]);
			
			if(start[0] < minLat) minLat = start[0];
			else if(start[0] > maxLat) maxLat = start[0];
			
			if(start[1] < minLon) minLon = start[1];
			else if(start[1] > maxLon) maxLon = start[1];
			
			AISVertex v = new AISVertex();
			v.setDbID(new Integer(stationDBID));
			v.setStartLat(start[0]);
			v.setStartLon(start[1]);
			v.setEndLat(end[0]);
			v.setEndLon(end[1]);
				
			tempList.add(v);
		}
		
		List<Coordinate> coordinates = new ArrayList<Coordinate>();
		for(int i = 0; i < points.size(); ++i){
			double[] start = points.get(i);

			Coordinate c = new Coordinate(start[0],start[1]);
		
			coordinates.add(c);
		}

		
		
//		
//		orderedList = new ArrayList<AISVertex>();
//		for(AISVertex v : tempList){
//			if(orderedList.size() == 0) orderedList.add(v);
//			else{
//				for(int i = 0; i < orderedList.size(); ++i){
//					AISVertex ov = orderedList.get(i);
//					if(ov.getStartLat() > v.getStartLat()){
//						orderedList.add(i, v);
//						break;
//					}else if(ov.getStartLat() == v.getStartLat()){
//						if(ov.getStartLon() > v.getStartLon()){
//							orderedList.add(i,v);
//						}
//					}
//					
//					if(i == orderedList.size() - 1){
//						orderedList.add(v);
//						break;
//					}
//				}
//			}
//		
//		}
		
		
		
		return null;
	}

	
	public static void main(String[] args){
		PointInPolygon p = new PointInPolygon();
		
		List<double[]> points = new ArrayList<double[]>();
		double[] d1 = {0,0};
		points.add(d1);
		
		double[] d2 = {1,1};
		points.add(d2);
		
		double[] d3 = {2,0};
		points.add(d3);
		
		double[] d4 = {2,-1};
		points.add(d4);
		
		double[] d5 = {1,-1};
		points.add(d5);
		
		double[] d6 = {0,0};
		points.add(d6);
		
		
		List<double[]> points2 = new ArrayList<double[]>();
		double[] d1_2 = {1,1};
		points2.add(d1_2);
		
		double[] d2_2 = {-1,1};
		points2.add(d2_2);
		
		double[] d3_2 = {-2,0};
		points2.add(d3_2);
		
		double[] d4_2 = {-2,-1};
		points2.add(d4_2);
		
		double[] d5_2 = {-1,-1};
		points2.add(d5_2);
		
		double[] d6_2 = {1,1};
		points2.add(d6_2);
		
		
		double[] point = {1.0,1.0};
		
		
		System.out.println("Is inside PIP: "+PointInPolygon.isPointInPolygon(points, point));
		
		System.out.println("Is inside POLY: "+PointInPolygon.isPolygonIntersection(points, points2));
		
	}
}
