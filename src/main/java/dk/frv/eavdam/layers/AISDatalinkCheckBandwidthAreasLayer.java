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
import dk.frv.eavdam.data.AISDatalinkCheckArea;
import dk.frv.eavdam.menus.IssuesMenuItem;
import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class AISDatalinkCheckBandwidthAreasLayer extends OMGraphicHandlerLayer {

	private static final long serialVersionUID = 1L;
	
	private OMGraphicList graphics = new OMGraphicList();
	private OpenMapFrame openMapFrame;
	private LayerHandler layerHandler;
	private InformationDelegator infoDelegator;
	
	private List<AISDatalinkCheckArea> areas = null;	
	
	public static Boolean ignoreHealthCheckMayBeOutdated = null;	
	
	public AISDatalinkCheckBandwidthAreasLayer() {}
	
	public List<AISDatalinkCheckArea> getAreas() {
		return areas;
	}
	
	public void setAreas(List<AISDatalinkCheckArea> areas) {
		this.areas = areas;
	}
	
	public OMGraphicList getGraphicsList() {
	    return graphics;
	}

	@Override
	public synchronized OMGraphicList prepare() {	
		
		graphics.clear();
		
		// XXX: for testing
		/*
		areas = new ArrayList<AISDatalinkCheckArea>();
		areas.add(new AISDatalinkCheckArea(60.5, 10, 59, 11, 0));
		areas.add(new AISDatalinkCheckArea(60.5, 11, 59, 12, 0.05));
		areas.add(new AISDatalinkCheckArea(60.5, 12, 59, 13, 0.15));
		areas.add(new AISDatalinkCheckArea(60.5, 13, 59, 14, 0.25));
		areas.add(new AISDatalinkCheckArea(60.5, 14, 59, 15, 0.35));
		areas.add(new AISDatalinkCheckArea(60.5, 15, 59, 16, 0.45));
		areas.add(new AISDatalinkCheckArea(60.5, 16, 59, 17, 0.55));
		areas.add(new AISDatalinkCheckArea(60.5, 17, 59, 18, 0.65));		
		areas.add(new AISDatalinkCheckArea(60.5, 18, 59, 19, 0.75));
		areas.add(new AISDatalinkCheckArea(60.5, 19, 59, 20, 0.85));
		areas.add(new AISDatalinkCheckArea(60.5, 20, 59, 21, 0.95));
		areas.add(new AISDatalinkCheckArea(60.5, 21, 59, 22, 1));
		*/
		
		if (areas != null) {
		
			for (AISDatalinkCheckArea area : areas) {
		
				double topLeftLatitude = area.getTopLeftLatitude();
				double topLeftLongitude = area.getTopLeftLongitude();
				double lowerRightLatitude = area.getLowerRightLatitude();
				double lowerRightLongitude = area.getLowerRightLongitude();
				//double bandwithUsageLevel = area.getBandwithUsageLevel();
				double bandwithUsageLevel = area.getMaxChannelBandwithUsageLevel();
				
				OMRect omRect = new OMRect(topLeftLatitude, topLeftLongitude, lowerRightLatitude, lowerRightLongitude, OMGraphic.LINETYPE_RHUMB);
				Color c = null;
				if (bandwithUsageLevel >= 0 && bandwithUsageLevel <= 0.1) {
					c = new Color(0, 255, 0, (int) Math.round(2.55*10+bandwithUsageLevel*10*2.55*10));  // 0-10% BW loading: green color  90-80% transparency
				} else if (bandwithUsageLevel > 0.1 && bandwithUsageLevel <= 0.2) {	
					c = new Color(0, 255, 0, (int) Math.round(2.55*21+(bandwithUsageLevel-0.1)*10*2.55*9));  // 11-20% BW loading: green color 79-70% transparency
				} else if (bandwithUsageLevel > 0.2 && bandwithUsageLevel <= 0.3) {	
					c = new Color(0, 100, 0, (int) Math.round(2.55*31+(bandwithUsageLevel-0.2)*10*2.55*9));  // 21-30% BW loading: dark green color 69-60% transparency
				} else if (bandwithUsageLevel > 0.3 && bandwithUsageLevel <= 0.4) {	
					c = new Color(255, 0, 0, (int) Math.round(2.55*41+(bandwithUsageLevel-0.3)*10*2.55*9));  // 31-40% BW loading: red color 59-50% transparency
				} else if (bandwithUsageLevel > 0.4 && bandwithUsageLevel <= 0.5) {	
					c = new Color(255, 0, 0, (int) Math.round(2.55*51+(bandwithUsageLevel-0.4)*10*2.55*9));  // 41-50% BW loading: red color 49-40% transparency			
				} else if (bandwithUsageLevel > 0.5 && bandwithUsageLevel <= 1) {	
					c = new Color(204, 0, 0, (int) Math.round(2.55*60+(bandwithUsageLevel-0.5)*2*2.55*40));  // Above 50% BW loading: dark red color 40-0% transparency
				}
				if (c != null) {
					omRect.setFillPaint(c);
					omRect.setLinePaint(com.bbn.openmap.omGraphics.OMColor.clear);
					graphics.add(omRect);
				}		
			}

		}
		
		graphics.project(getProjection(), true);
		this.repaint();
		this.validate();		
		
		if (IssuesMenuItem.healthCheckMayBeOutdated != null && IssuesMenuItem.healthCheckMayBeOutdated.booleanValue() == true &&
				AISDatalinkCheckBandwidthAreasLayer.ignoreHealthCheckMayBeOutdated != null && AISDatalinkCheckBandwidthAreasLayer.ignoreHealthCheckMayBeOutdated.booleanValue() == false &&
				IssuesMenuItem.issues != null && !IssuesMenuItem.issues.isEmpty()) {
			int response = JOptionPane.showConfirmDialog(openMapFrame, "Data has changed after the latest health check and\n" +
		        "the bandwidth areas layer may therefore be outdated.\n\nIgnore this warning in the future?", "Warning", JOptionPane.YES_NO_OPTION);
			if (response == JOptionPane.YES_OPTION) {					
				AISDatalinkCheckBandwidthAreasLayer.ignoreHealthCheckMayBeOutdated = new Boolean(true);
			}
		}		
		
		return graphics;
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