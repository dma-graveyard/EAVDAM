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
package dk.frv.eavdam.layers;

import com.bbn.openmap.InformationDelegator;
import com.bbn.openmap.LayerHandler;
import com.bbn.openmap.gui.OpenMapFrame;
import com.bbn.openmap.layer.OMGraphicHandlerLayer;
import com.bbn.openmap.proj.Projection;
import com.bbn.openmap.omGraphics.OMGraphic;
import com.bbn.openmap.omGraphics.OMGraphicList;
import com.bbn.openmap.omGraphics.OMLine;
import com.bbn.openmap.omGraphics.OMRect;
import com.bbn.openmap.omGraphics.OMText;
import java.awt.geom.Point2D;
import javax.swing.JOptionPane;

public class FATDMAGridLayer extends OMGraphicHandlerLayer {

	private static final long serialVersionUID = 1L;

	private OMGraphicList graphics = new OMGraphicList();
	private OpenMapFrame openMapFrame;
	private LayerHandler layerHandler;
	private InformationDelegator infoDelegator;
	
	public FATDMAGridLayer() {}
	
	public OMGraphicList getGraphicsList() {
	    return graphics;
	}

	@Override
	public synchronized OMGraphicList prepare() {		
	
		graphics.clear();
		
		Projection projection = getProjection();
		
		double scale = projection.getScale();
		
		if (scale > 15000000) {
		
			JOptionPane.showMessageDialog(openMapFrame, "Scale too small for displaying FATDMA grid layer! Please, zoom in!");			
			
		} else {
			
			/*	
			Point2D upperLeft = projection.getUpperLeft();
			Point2D lowerRight = projection.getLowerRight();		
						
			double latTopPos = Math.floor(upperLeft.getY());
			double lonLeftPos = Math.floor(upperLeft.getX());
			double latBottomPos = Math.floor(lowerRight.getY());
			double lonRightPos = Math.floor(lowerRight.getX());

			// if looking at an area (in the Pacific Ocean), where the dateline crosses through
			// the area of interest (LONleft > LONright) the drawing method needs to be called twice
			
			if (lonLeftPos > lonRightPos) {
				addGridToGraphics(latTopPos, lonLeftPos, latBottomPos, 180);
				addGridToGraphics(latTopPos, -180, latBottomPos, lonRightPos);				
			} else {
				addGridToGraphics(latTopPos, lonLeftPos, latBottomPos, lonRightPos);
			}
			*/
				
			Point2D upperLeft = projection.getUpperLeft();
			Point2D lowerRight = projection.getLowerRight();
			
			int singleCellSizeInNauticalMiles = 30;
			int noOfSingleCellsAlongOneSideOfMasterCell = 6;
			int masterCellSizeInNauticalMiles = singleCellSizeInNauticalMiles * noOfSingleCellsAlongOneSideOfMasterCell;		
			int noOfMasterCellsAroundEquator = (int) (360.0d * 60.0d / masterCellSizeInNauticalMiles);
			double masterCellSizeInDegreesLatitude = masterCellSizeInNauticalMiles / 60;  	
			double singleCellHeightInDegrees = masterCellSizeInDegreesLatitude / noOfSingleCellsAlongOneSideOfMasterCell;
		
			int startLat = ((int) (upperLeft.getY()/3)*3) + 6;
			if (startLat > 90) {
				startLat = 90;
			}
			int endLat = ((int) (lowerRight.getY()/3)*3) - 6;
			if (endLat < -90) {
				endLat = -90;
			}
			double startLon = upperLeft.getX() - 6;
			if (startLon < -180) {
				startLon = -180;
			}
			double endLon = lowerRight.getX() + 6;
			if (endLon > 180) {
				endLon = 180;
			}
			
			for (double lat = endLat; lat <= startLat; lat = lat+singleCellHeightInDegrees) { 		
				try {
					int masterCellRowNo = (int) (Math.abs(lat + 0.5*singleCellHeightInDegrees) / masterCellSizeInDegreesLatitude);
					double masterCellMeanLatitude = (masterCellRowNo + 0.5) * masterCellSizeInDegreesLatitude;					
					int noOfMasterCellsAroundMasterCellRow = (int) (noOfMasterCellsAroundEquator * Math.cos(2*Math.PI*masterCellMeanLatitude/360.0));
					double singleCellWidthInDegrees = (double) 360/(noOfSingleCellsAlongOneSideOfMasterCell*noOfMasterCellsAroundMasterCellRow);	
					if (startLon < endLon) {					
						for (int n = -noOfMasterCellsAroundEquator*3; n <noOfMasterCellsAroundEquator*3; n++) {
							double lon = n*singleCellWidthInDegrees;
							if ((lon > startLon) && (lon < endLon)) {  
								OMRect omRect = new OMRect(lat, lon, lat + singleCellHeightInDegrees, lon+singleCellWidthInDegrees, OMGraphic.LINETYPE_RHUMB);
								graphics.add(omRect);					
								int cellno = calculateCellNo(singleCellSizeInNauticalMiles, noOfSingleCellsAlongOneSideOfMasterCell, lat + 0.5*singleCellHeightInDegrees, lon + 0.5*singleCellWidthInDegrees);					
								OMText omText = new OMText(lat + 0.5*singleCellHeightInDegrees, lon +0.5*singleCellWidthInDegrees, String.valueOf(cellno), OMText.JUSTIFY_CENTER);
								omText.setBaseline(OMText.BASELINE_MIDDLE);
								graphics.add(omText);
							}
						}
					} else {				
						for (int n = -noOfMasterCellsAroundEquator*3; n <noOfMasterCellsAroundEquator*3; n++) {							
							double lon = n*singleCellWidthInDegrees;
							if ((lon > startLon) && (lon <= 180)) {  
								OMRect omRect = new OMRect(lat, lon, lat + singleCellHeightInDegrees, lon+singleCellWidthInDegrees, OMGraphic.LINETYPE_RHUMB);
								graphics.add(omRect);					
								int cellno = calculateCellNo(singleCellSizeInNauticalMiles, noOfSingleCellsAlongOneSideOfMasterCell, lat + 0.5*singleCellHeightInDegrees, lon + 0.5*singleCellWidthInDegrees);			
								OMText omText = new OMText(lat + 0.5*singleCellHeightInDegrees, lon + 0.5*singleCellWidthInDegrees, String.valueOf(cellno), OMText.JUSTIFY_CENTER);
								omText.setBaseline(OMText.BASELINE_MIDDLE);
								graphics.add(omText);
							}
						}
						for (int n = -noOfMasterCellsAroundEquator*3; n <noOfMasterCellsAroundEquator*3; n++) {
							double lon = n*singleCellWidthInDegrees;
							if (Math.round(lon) == -180) {
								lon = -180;
							}
							if ((lon >= -180) && (lon < endLon)) {
								OMRect omRect = new OMRect(lat, lon, lat + singleCellHeightInDegrees, lon+singleCellWidthInDegrees, OMGraphic.LINETYPE_RHUMB);
								graphics.add(omRect);		
								int cellno = calculateCellNo(singleCellSizeInNauticalMiles, noOfSingleCellsAlongOneSideOfMasterCell, lat + 0.5*singleCellHeightInDegrees, lon + 0.5*singleCellWidthInDegrees);													
								OMText omText = new OMText(lat + 0.5*singleCellHeightInDegrees, lon + 0.5*singleCellWidthInDegrees, String.valueOf(cellno), OMText.JUSTIFY_CENTER);
								omText.setBaseline(OMText.BASELINE_MIDDLE);
								graphics.add(omText);
							}
						}						
					}
				} catch (Exception ex) {}				
			}
		}
		
		graphics.project(getProjection(), true);
		this.repaint();
		this.validate();
		return graphics;
	}
	
	private void addGridToGraphics(double latTopPos, double lonLeftPos, double latBottomPos, double lonRightPos) {
	
		// make sure to draw the grid a little larger than the area in view
		int latStart = ((int) (latBottomPos/3)*3) - 6;
		int latStop = ((int) (latTopPos/3)*3) + 6;
		double lonStart = lonLeftPos - 6;
		double lonStop = lonRightPos + 6;
		
		//... using the calculations from above 
		int singleCellSizeInNauticalMiles = 30;
		int noOfSingleCellsAlongOneSideOfMasterCell = 6;
		int masterCellSizeInNauticalMiles = singleCellSizeInNauticalMiles * noOfSingleCellsAlongOneSideOfMasterCell;
		int noOfMasterCellsAroundEquator = 360 * 60 / masterCellSizeInNauticalMiles;
		double masterCellSizeInDegreesLatitude = (double) masterCellSizeInNauticalMiles / 60;

		for (double latBottom=latStart; latBottom <= latStop; latBottom +=0.5) {  //i.e. run through the longitude interval in view in increments of one layer of cells.
		
			double lat = latBottom + 0.25;
			
			// draw the bottom line of the layer of cells at (latbottom, LONstart) to (latbottom,LONstop).
			OMLine omLine = new OMLine(latBottom, lonStart, latBottom, lonStop, OMLine.LINETYPE_RHUMB);
			graphics.add(omLine);
			
			//... using the algorithm from above at this particular latitude
			//Note: abs(x) denotes the absolute value of x; int(x) denotes truncated integer value of x 
			int masterCellRowNo = (int) (Math.abs(lat) / masterCellSizeInDegreesLatitude); 
			double masterCellMeanLatitude = (masterCellRowNo+0.5) * masterCellSizeInDegreesLatitude;

			//Note: cos(x) denotes cosine of x in radians
			int noOfMasterCellsAroundMasterCellRow = (int) (noOfMasterCellsAroundEquator * Math.cos(2 * Math.PI * masterCellMeanLatitude / 360 ));

			double singleCellWidthInDegrees = 360 / (noOfSingleCellsAlongOneSideOfMasterCell * noOfMasterCellsAroundMasterCellRow); 

			for (int n = -noOfMasterCellsAroundEquator*3; n <noOfMasterCellsAroundEquator*3; n++) {

				if ((n*singleCellWidthInDegrees > lonStart) && (n*singleCellWidthInDegrees < lonStop)) {  // we are now inside the drawing area)

					// draw the line separating two neighbouring cells of the same layer
					
					OMLine omLine2 = new OMLine(latBottom, n*singleCellWidthInDegrees, latBottom+0.5, n*singleCellWidthInDegrees, OMLine.LINETYPE_RHUMB);
					graphics.add(omLine2);
	
					//Now calculate the cell number
					Double lon = n*singleCellWidthInDegrees+singleCellWidthInDegrees*0.5;

					int rowNumberInsideMasterCell = (int) (Math.abs(lat) * 60 / singleCellSizeInNauticalMiles) - noOfSingleCellsAlongOneSideOfMasterCell * masterCellRowNo;

					// Note: first Southern Hemisphere is assumed, then compensate if Northern Hemisphere
					if (lat > 0) {
						rowNumberInsideMasterCell = noOfSingleCellsAlongOneSideOfMasterCell - rowNumberInsideMasterCell - 1;
					}
			
					int columnNumberInsideMasterCell = (int) (Math.abs(lon) / singleCellWidthInDegrees);
			
					while (columnNumberInsideMasterCell > (noOfSingleCellsAlongOneSideOfMasterCell - 1 )) {
						columnNumberInsideMasterCell = columnNumberInsideMasterCell - noOfSingleCellsAlongOneSideOfMasterCell;
					}

					// Note: first positive longitude is assumed, compensate if negative longitude (west of Greenwich)
					if (lon < 0) {
						columnNumberInsideMasterCell = (noOfSingleCellsAlongOneSideOfMasterCell - 1) - columnNumberInsideMasterCell;
					}

					int resultingCellNumber = rowNumberInsideMasterCell * noOfSingleCellsAlongOneSideOfMasterCell + columnNumberInsideMasterCell + 1;

					// now draw the cell number at the centre position of the cell (LAT, LON)
					OMText omText = new OMText(lat, lon, String.valueOf(resultingCellNumber), OMText.JUSTIFY_CENTER);
					omText.setBaseline(OMText.BASELINE_MIDDLE);
					graphics.add(omText);	

				}
		
			}

		}
	}
	
	public static int calculateCellNo(int singleCellSizeInNauticalMiles, int noOfSingleCellsAlongOneSideOfMasterCell, double lat, double lon) {
	
		// assuming mastercell consists of rectangular grid of singlecells
		// lat / lon in decimal degrees
    
		int result = -1;

		int masterCellSizeInNauticalMiles = singleCellSizeInNauticalMiles * noOfSingleCellsAlongOneSideOfMasterCell;
		int noOfMasterCellsAroundEquator = (int) (360.0d * 60.0d / masterCellSizeInNauticalMiles);
		double MasterCellSizeInDegreesLatitude = (double)masterCellSizeInNauticalMiles / 60.0d;  

		try {
			int masterCellRowNo = (int) (Math.abs(lat) / MasterCellSizeInDegreesLatitude);  // integer: 0 near equator, positive value increasing towards the poles
			double masterCellMeanLatitude = (masterCellRowNo+0.5) * MasterCellSizeInDegreesLatitude;  // positive value, denotes the vertical middle of the master cell
			int noOfMasterCellsAroundMasterCellRow = (int) (noOfMasterCellsAroundEquator * Math.cos(2*Math.PI*masterCellMeanLatitude/360.0));
			double singleCellWidthInDegrees = 360.0d/(noOfSingleCellsAlongOneSideOfMasterCell*noOfMasterCellsAroundMasterCellRow);  // Assuming 5 cells per mastercell

			int rowNumberInsideMasterCell =-1;  //legal values [0;noOfSingleCellsAlongOneSideOfMasterCell-1]
			int columnNumberInsideMasterCell =-1;  //legal values [0;noOfSingleCellsAlongOneSideOfMasterCell-1]

			// default: Southern Hemisphere
			rowNumberInsideMasterCell = ((int) (Math.abs(lat) * 60.0 / singleCellSizeInNauticalMiles)) - noOfSingleCellsAlongOneSideOfMasterCell * masterCellRowNo; // positive integer
	
			if (lat>0) {  //in case of Northern Hemisphere
				rowNumberInsideMasterCell = noOfSingleCellsAlongOneSideOfMasterCell - rowNumberInsideMasterCell - 1;
			}

			//default: Positive longitude - east of Greenwich
			columnNumberInsideMasterCell = (int) (Math.abs(lon) / singleCellWidthInDegrees);
			while (columnNumberInsideMasterCell >(noOfSingleCellsAlongOneSideOfMasterCell-1)) {
				columnNumberInsideMasterCell -= noOfSingleCellsAlongOneSideOfMasterCell;
			}
          
			if (lon<0) {  // In case of Negative longitude - west of Greenwich
				columnNumberInsideMasterCell = (noOfSingleCellsAlongOneSideOfMasterCell-1)-columnNumberInsideMasterCell;
			}

			result = rowNumberInsideMasterCell*noOfSingleCellsAlongOneSideOfMasterCell+columnNumberInsideMasterCell+1;
		
		} catch (Exception ex) {
			result = -1;
		}
		return result;
	
	}

	@Override
	public void findAndInit(Object obj) {
		if (obj instanceof OpenMapFrame) {
			this.openMapFrame = (OpenMapFrame) obj;	
		} else if (obj instanceof LayerHandler) {
		    this.layerHandler = (LayerHandler) obj;
		} else if (obj instanceof InformationDelegator) {
			this.infoDelegator = (InformationDelegator) obj;	    
		}
	}

}