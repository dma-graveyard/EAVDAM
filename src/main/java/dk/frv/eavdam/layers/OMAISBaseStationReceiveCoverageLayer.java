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
import com.bbn.openmap.event.MapMouseEvent;
import com.bbn.openmap.event.MapMouseListener;
import com.bbn.openmap.event.NavMouseMode;
import com.bbn.openmap.event.SelectMouseMode;
import com.bbn.openmap.layer.OMGraphicHandlerLayer;
import com.bbn.openmap.omGraphics.OMCircle;
import com.bbn.openmap.omGraphics.OMGraphic;
import com.bbn.openmap.omGraphics.OMGraphicList;
import com.bbn.openmap.omGraphics.OMList;
import com.bbn.openmap.omGraphics.OMPoly;
import dk.frv.eavdam.data.AISFixedStationData;
import com.bbn.openmap.proj.Length;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class OMAISBaseStationReceiveCoverageLayer extends OMGraphicHandlerLayer {

	private static final long serialVersionUID = 1L;

	private OMGraphicList graphics = new OMGraphicList();
	private InformationDelegator infoDelegator;
	
	private List<OMGraphic> coverages = new ArrayList<OMGraphic>();	
	
	public OMAISBaseStationReceiveCoverageLayer() {}

	public void init() {
		coverages = new ArrayList<OMGraphic>();
	}	
	
	public OMGraphicList getGraphicsList() {
	    return graphics;
	}

	@Override
	public synchronized OMGraphicList prepare() {
		graphics.clear();
		for (OMGraphic omg : coverages) {
			graphics.add(omg);
		}
		graphics.project(getProjection(), true);
		this.repaint();
		this.validate();
		return graphics;
	}
	
	public Object addReceiveCoverageArea(OMBaseStation bs) {
	    
		if (bs.getReceiveCoverageArea() == null) {
		    return null;
		}

		if (bs.getReceiveCoverageArea().size() < 2) {
			
			AISFixedStationData stationData = bs.getStationData();
			
			//Get the radius
			double radius = Math.min(Math.abs(stationData.getLat()-bs.getReceiveCoverageArea().get(0)[0]),Math.abs(stationData.getLon()-bs.getReceiveCoverageArea().get(0)[1]));

			//Create a circle
			OMCircle baseCircle = new OMCircle(stationData.getLat(), stationData.getLon(), radius, Length.KM);
			
			Color c = new Color(0, 0, 255, 100);
			baseCircle.setFillPaint(c);
			coverages.add(baseCircle);	
			return baseCircle;
			
		} else if (bs.getReceiveCoverageArea().size() == 2) {
			return null;
			
		} else {

			double[] latlon = new double[bs.getReceiveCoverageArea().size()*2+2];
			int ith = 0;
			for(double[] ll : bs.getReceiveCoverageArea()){
				latlon[ith] = ll[0];
				++ith;
				latlon[ith] = ll[1];
				++ith;
			}

			latlon[latlon.length-2] = bs.getReceiveCoverageArea().get(0)[0];
			latlon[latlon.length-1] = bs.getReceiveCoverageArea().get(0)[1];

			OMPoly baseReach = new OMPoly(latlon, OMGraphic.DECIMAL_DEGREES, OMGraphic.LINETYPE_GREATCIRCLE);

			Color c = new Color(0, 0, 255, 100);
			baseReach.setFillPaint(c);
			coverages.add(baseReach);	
			return baseReach;

		}
	}

	@Override
	public void findAndInit(Object obj) {
		if (obj instanceof InformationDelegator) {
			this.infoDelegator = (InformationDelegator) obj;	    
		}
	}

}