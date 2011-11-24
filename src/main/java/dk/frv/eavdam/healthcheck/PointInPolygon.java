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

	int stationDBID = -1;
	double maxLat = 0;
	double maxLon = 0;
	double minLat = 0;
	double minLon = 0;
	
	
	public PointInPolygon() {
		// TODO Auto-generated constructor stub
	}
	
	@SuppressWarnings("deprecation")	
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

	@SuppressWarnings("deprecation")	
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
