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
import com.bbn.openmap.omGraphics.OMCircle;
import com.bbn.openmap.omGraphics.OMGraphic;
import com.bbn.openmap.omGraphics.OMGraphicList;
import com.bbn.openmap.omGraphics.OMLine;
import com.bbn.openmap.omGraphics.OMRect;
import com.bbn.openmap.omGraphics.OMText;
import com.bbn.openmap.proj.Length;
import dk.frv.eavdam.data.AISDatalinkCheckIssue;
import dk.frv.eavdam.data.AISStation;
import dk.frv.eavdam.data.EAVDAMData;
import dk.frv.eavdam.menus.IssuesMenuItem;
import dk.frv.eavdam.utils.DBHandler;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * Class for displaying the issues found in an AIS VHF datalink health check.
 * Draws red circles around the stations that are involved with issues.
 */
public class AISDatalinkCheckIssueLayer extends OMGraphicHandlerLayer {

	private static final long serialVersionUID = 1L;

	private OMGraphicList graphics = new OMGraphicList();
	private OpenMapFrame openMapFrame;
	private LayerHandler layerHandler;
	private InformationDelegator infoDelegator;
	
	public static Boolean ignoreHealthCheckMayBeOutdated = null;		
	
    public AISDatalinkCheckIssueLayer() {}

	public OMGraphicList getGraphicsList() {
	    return graphics;
	}

	@Override
	public synchronized OMGraphicList prepare() {		
	
		List<AISDatalinkCheckIssue> issues = IssuesMenuItem.issues;
		if (issues == null) {
			EAVDAMData data = DBHandler.getData();
		    issues = data.getAISDatalinkCheckIssues();
		}
		
		graphics.clear();
		
		// draw red circles around issues
		if (issues != null) {
			for (AISDatalinkCheckIssue issue : issues) {
				List<AISStation> involvedStations = issue.getInvolvedStations();
				if (involvedStations != null) {
					for (AISStation station : involvedStations) {
						if (station.getLatitude() != null && station.getLongitude() != null) {
							OMCircle issueCircle = new OMCircle(station.getLatitude().doubleValue(), station.getLongitude().doubleValue(), 100, Length.KM);
							Color c = new Color(255, 0, 0, 255);
							issueCircle.setLinePaint(c);
							issueCircle.setStroke(new BasicStroke(3));							
							graphics.add(issueCircle);
						}
					}
				}
			}
		}
		
		graphics.project(getProjection(), true);
		this.repaint();
		this.validate();
		
		if (IssuesMenuItem.healthCheckMayBeOutdated != null && IssuesMenuItem.healthCheckMayBeOutdated.booleanValue() == true &&
				AISDatalinkCheckIssueLayer.ignoreHealthCheckMayBeOutdated != null && AISDatalinkCheckIssueLayer.ignoreHealthCheckMayBeOutdated.booleanValue() == false &&
				IssuesMenuItem.issues != null && !IssuesMenuItem.issues.isEmpty()) {
			int response = JOptionPane.showConfirmDialog(openMapFrame, "Data has changed after the latest health check and\n" +
		        "the issues layer may therefore be outdated.\n\nIgnore this warning in the future?", "Warning", JOptionPane.YES_NO_OPTION);
			if (response == JOptionPane.YES_OPTION) {					
				AISDatalinkCheckIssueLayer.ignoreHealthCheckMayBeOutdated = new Boolean(true);
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