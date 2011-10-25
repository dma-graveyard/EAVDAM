package dk.frv.eavdam.layers;

import com.bbn.openmap.InformationDelegator;
import com.bbn.openmap.layer.OMGraphicHandlerLayer;
import com.bbn.openmap.omGraphics.OMGraphic;
import com.bbn.openmap.omGraphics.OMGraphicList;
import com.bbn.openmap.omGraphics.OMRect;
import com.bbn.openmap.omGraphics.OMText;

public class FATDMAGridLayer extends OMGraphicHandlerLayer {

	private static final long serialVersionUID = 1L;

	private OMGraphicList graphics = new OMGraphicList();
	private InformationDelegator infoDelegator;
	
	public FATDMAGridLayer() {}
	
	public OMGraphicList getGraphicsList() {
	    return graphics;
	}

	@Override
	public synchronized OMGraphicList prepare() {		
		
		int singleCellSizeInNauticalMiles = 30;
		int noOfSingleCellsAlongOneSideOfMasterCell = 6;
		int masterCellSizeInNauticalMiles = singleCellSizeInNauticalMiles * noOfSingleCellsAlongOneSideOfMasterCell;		
		int noOfMasterCellsAroundEquator = (int) (360.0d * 60.0d / masterCellSizeInNauticalMiles);
		float masterCellSizeInDegreesLatitude = (float) masterCellSizeInNauticalMiles / 60;  	
		float singleCellHeightInDegrees = masterCellSizeInDegreesLatitude / noOfSingleCellsAlongOneSideOfMasterCell;
			
		for (float lat = 67; lat>48; lat = lat-singleCellHeightInDegrees) {  // covers northern Europe
			try {
				int masterCellRowNo = (int) (Math.abs(lat + (float) 0.5*singleCellHeightInDegrees) / masterCellSizeInDegreesLatitude);
				double masterCellMeanLatitude = (masterCellRowNo + 0.5) * masterCellSizeInDegreesLatitude;
				int noOfMasterCellsAroundMasterCellRow = (int) (noOfMasterCellsAroundEquator * Math.cos(2*Math.PI*masterCellMeanLatitude/360.0));
				float singleCellWidthInDegrees = (float) 360/(noOfSingleCellsAlongOneSideOfMasterCell*noOfMasterCellsAroundMasterCellRow);
				for (float lon = -25; lon < 30; lon = lon + singleCellWidthInDegrees) {
					OMRect omRect = new OMRect(lat, lon, lat + singleCellHeightInDegrees, lon+singleCellWidthInDegrees, OMGraphic.LINETYPE_RHUMB);
					graphics.add(omRect);					
					int cellno = getCellNo(lat + (float) 0.5*singleCellHeightInDegrees, lon + (float) 0.5*singleCellWidthInDegrees,
						singleCellSizeInNauticalMiles, noOfSingleCellsAlongOneSideOfMasterCell, masterCellRowNo, singleCellWidthInDegrees);					
					OMText omText = new OMText(lat + (float) 0.5*singleCellHeightInDegrees, lon + (float) 0.5*singleCellWidthInDegrees, String.valueOf(cellno), OMText.JUSTIFY_CENTER);
					omText.setBaseline(OMText.BASELINE_MIDDLE);
					graphics.add(omText);
				}
			} catch (Exception ex) {}				
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
		if (obj instanceof InformationDelegator) {
			this.infoDelegator = (InformationDelegator) obj;	    
		}
	}

}