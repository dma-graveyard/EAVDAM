/*
* Copyright 2011 Danish Maritime Safety Administration. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without
* modification, are permitted provided that the following conditions are met:
*
* 1. Redistributions of source code must retain the above copyright notice,
* this list of conditions and the following disclaimer.
*
* 2. Redistributions in binary form must reproduce the above copyright notice,
* this list of conditions and the following disclaimer in the documentation and/or
* other materials provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY Danish Maritime Safety Administration ``AS IS''
* AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
* IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
* DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> OR CONTRIBUTORS BE LIABLE FOR
* ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
* (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
* LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
* (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
* SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

* The views and conclusions contained in the software and documentation are those
* of the authors and should not be interpreted as representing official policies,
* either expressed or implied, of Danish Maritime Safety Administration.
*
*/
package dk.frv.eavdam.healthcheck;

import java.util.ArrayList;
import java.util.List;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.geom.PrecisionModel;

/**
 * Class for a point in polygon. Used in the AIS VHF health check.
 */
public class PointInPolygon {

	int stationDBID = -1;
	double maxLat = 0;
	double maxLon = 0;
	double minLat = 0;
	double minLon = 0;
	
	public PointInPolygon() {}
	
	@SuppressWarnings("deprecation")	
	public static boolean isPointInPolygon(List<double[]> polygon, double[] point){
		if(polygon == null || point == null || polygon.size() < 3 || point.length != 2) return false;
		
		List<Coordinate> coordinates = new ArrayList<Coordinate>();

		double[] firstPoint = null;
		for(int i = 0; i < polygon.size(); ++i){
			double[] start = polygon.get(i);
			
			if(firstPoint == null) firstPoint = start;
			
			Coordinate c = new Coordinate(start[0],start[1]);
		
			coordinates.add(c);
			
			if(i == polygon.size() - 1 && (firstPoint[0] != start[0] || firstPoint[1] != start[1])){
				Coordinate cfp = new Coordinate(firstPoint[0],firstPoint[1]);
				coordinates.add(cfp);
				
//				System.err.println("First and last point do not match!");
			}
		}
		
	
		LinearRing shell = new LinearRing(coordinates.toArray(new Coordinate[coordinates.size()]), new PrecisionModel(), 0);
		Polygon poly = new Polygon(shell, null, new GeometryFactory());
		
		Coordinate c = new Coordinate(point[0],point[1]);
		Point p = new Point(c, new PrecisionModel(), 0);
		
		return p.intersects(poly);
		
	}

	@SuppressWarnings("deprecation")	
	public static boolean isPolygonIntersection(List<double[]> polygon1, List<double[]> polygon2){
		if(polygon1 == null || polygon2 == null) return false;
			
		List<Coordinate> coordinates = new ArrayList<Coordinate>();
		
		double[] firstPoint1 = null; 
		for(int i = 0; i < polygon1.size(); ++i){
			double[] start = polygon1.get(i);

			if(firstPoint1 == null) firstPoint1 = start;
			
			Coordinate c = new Coordinate(start[0],start[1]);
		
			coordinates.add(c);
			
			if(i == polygon1.size() - 1 && (firstPoint1[0] != start[0] || firstPoint1[1] != start[1])){
				Coordinate cfp = new Coordinate(firstPoint1[0],firstPoint1[1]);
				coordinates.add(cfp);
				
//				System.err.println("First and last point do not match!");
			}
		}
		
		LinearRing shell = new LinearRing(coordinates.toArray(new Coordinate[coordinates.size()]), new PrecisionModel(), 0);
		Polygon poly1 = new Polygon(shell, null, new GeometryFactory());
		
		double[] firstPoint2 = null;
		List<Coordinate> coordinates2 = new ArrayList<Coordinate>();
		for(int i = 0; i < polygon2.size(); ++i){
			double[] start = polygon2.get(i);

			Coordinate c = new Coordinate(start[0],start[1]);
		
			coordinates2.add(c);
			
			if(firstPoint2 == null) firstPoint2 = start;
			
			coordinates.add(c);
			
			if(i == polygon1.size() - 1 && (firstPoint2[0] != start[0] || firstPoint2[1] != start[1])){
				Coordinate cfp = new Coordinate(firstPoint2[0],firstPoint2[1]);
				coordinates.add(cfp);
				
//				System.err.println("First and last point do not match!");
			}
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
		
		
		//System.out.println("Is inside PIP: "+PointInPolygon.isPointInPolygon(points, point));
		
		//System.out.println("Is inside POLY: "+PointInPolygon.isPolygonIntersection(points, points2));
		
	}
}
