package dk.frv.eavdam.layers;

import com.bbn.openmap.InformationDelegator;
import com.bbn.openmap.LayerHandler;
import com.bbn.openmap.gui.OpenMapFrame;
import com.bbn.openmap.layer.OMGraphicHandlerLayer;
import com.bbn.openmap.proj.Projection;
import com.bbn.openmap.omGraphics.OMGraphic;
import com.bbn.openmap.omGraphics.OMGraphicList;
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
		
		float scale = projection.getScale();
		
		if (scale > 15000000) {
		
			JOptionPane.showMessageDialog(openMapFrame, "Scale too small for displaying FATDMA grid layer! Please, zoom in!");			
			
		} else {
					
			int singleCellSizeInNauticalMiles = 30;
			int noOfSingleCellsAlongOneSideOfMasterCell = 6;
			int masterCellSizeInNauticalMiles = singleCellSizeInNauticalMiles * noOfSingleCellsAlongOneSideOfMasterCell;		
			int noOfMasterCellsAroundEquator = (int) (360.0d * 60.0d / masterCellSizeInNauticalMiles);
			float masterCellSizeInDegreesLatitude = (float) masterCellSizeInNauticalMiles / 60;  	
			float singleCellHeightInDegrees = masterCellSizeInDegreesLatitude / noOfSingleCellsAlongOneSideOfMasterCell;
			
			Point2D upperLeft = projection.getUpperLeft();
			Point2D lowerRight = projection.getLowerRight();
						
			// covers Europe, but starting point of the grid probably not correct !!
			/*
			float startLat = 73;
			float endLat = 28;
			float startLon = -23;
			float endLon = 42;
			*/
						
			float startLat = (float) Math.floor(upperLeft.getY());
			float endLat = (float) Math.floor(lowerRight.getY());
			float startLon = (float) Math.floor(upperLeft.getX());
			float endLon = (float) Math.floor(lowerRight.getX());
		
			System.out.println(startLat + "; " + endLat + ";" + startLon + ";" + endLon);
		
			for (float lat = startLat; lat > endLat; lat = lat-singleCellHeightInDegrees) { 		
				try {
					int masterCellRowNo = (int) (Math.abs(lat + (float) 0.5*singleCellHeightInDegrees) / masterCellSizeInDegreesLatitude);
					double masterCellMeanLatitude = (masterCellRowNo + 0.5) * masterCellSizeInDegreesLatitude;
					int noOfMasterCellsAroundMasterCellRow = (int) (noOfMasterCellsAroundEquator * Math.cos(2*Math.PI*masterCellMeanLatitude/360.0));
					float singleCellWidthInDegrees = (float) 360/(noOfSingleCellsAlongOneSideOfMasterCell*noOfMasterCellsAroundMasterCellRow);	
					if (startLon < endLon) {
						for (float lon = startLon; lon < endLon; lon = lon + singleCellWidthInDegrees) {
							OMRect omRect = new OMRect(lat, lon, lat + singleCellHeightInDegrees, lon+singleCellWidthInDegrees, OMGraphic.LINETYPE_RHUMB);
							graphics.add(omRect);					
							int cellno = getCellNo(lat + (float) 0.5*singleCellHeightInDegrees, lon + (float) 0.5*singleCellWidthInDegrees,
								singleCellSizeInNauticalMiles, noOfSingleCellsAlongOneSideOfMasterCell, masterCellRowNo, singleCellWidthInDegrees);					
							OMText omText = new OMText(lat + (float) 0.5*singleCellHeightInDegrees, lon + (float) 0.5*singleCellWidthInDegrees, String.valueOf(cellno), OMText.JUSTIFY_CENTER);
							omText.setBaseline(OMText.BASELINE_MIDDLE);
							graphics.add(omText);
						}
					} else {
						float lon = startLon;
						while (true) {
							if (lon < 0 && lon >= endLon) {
								break;
							}
							if (lon > 180) {
								lon = lon - 360;
							}					
							float lon2 = lon+singleCellWidthInDegrees;
							if (lon2 > 180) {
								lon2 = lon2 - 360;
							}
							OMRect omRect = new OMRect(lat, lon, lat + singleCellHeightInDegrees, lon2, OMGraphic.LINETYPE_RHUMB);
							graphics.add(omRect);					
							int cellno = getCellNo(lat + (float) 0.5*singleCellHeightInDegrees, lon + (float) 0.5*singleCellWidthInDegrees,
								singleCellSizeInNauticalMiles, noOfSingleCellsAlongOneSideOfMasterCell, masterCellRowNo, singleCellWidthInDegrees);					
							OMText omText = new OMText(lat + (float) 0.5*singleCellHeightInDegrees, lon + (float) 0.5*singleCellWidthInDegrees, String.valueOf(cellno), OMText.JUSTIFY_CENTER);
							omText.setBaseline(OMText.BASELINE_MIDDLE);
							graphics.add(omText);	
							lon = lon + singleCellWidthInDegrees;
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
	
    public int getCellNo(float lat, float lon, int singleCellSizeInNauticalMiles, int noOfSingleCellsAlongOneSideOfMasterCell, int masterCellRowNo, float singleCellWidthInDegrees) {

        int rowNumberInsideMasterCell =-1;
        int columnNumberInsideMasterCell =-1;

        // default: Southern Hemisphere
        rowNumberInsideMasterCell = ((int) (Math.abs(lat) * 60.0 / singleCellSizeInNauticalMiles)) - noOfSingleCellsAlongOneSideOfMasterCell * masterCellRowNo; // positive integer

		if (lat > 0) { //in case of Northern Hemisphere      
			rowNumberInsideMasterCell = noOfSingleCellsAlongOneSideOfMasterCell - rowNumberInsideMasterCell - 1;
        }

		//default: Positive longitude - east of Greenwich
		columnNumberInsideMasterCell = (int) (Math.abs(lon) / singleCellWidthInDegrees);
		while (columnNumberInsideMasterCell > (noOfSingleCellsAlongOneSideOfMasterCell-1)) {
			columnNumberInsideMasterCell -= noOfSingleCellsAlongOneSideOfMasterCell;
		}
          
		if (lon < 0) {  // In case of Negative longitude - west of Greenwich      
			columnNumberInsideMasterCell = (noOfSingleCellsAlongOneSideOfMasterCell-1)-columnNumberInsideMasterCell;
		}
      
		return rowNumberInsideMasterCell*noOfSingleCellsAlongOneSideOfMasterCell+columnNumberInsideMasterCell+1;           
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