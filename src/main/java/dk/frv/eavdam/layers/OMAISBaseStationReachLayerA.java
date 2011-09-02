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
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

public class OMAISBaseStationReachLayerA extends OMGraphicHandlerLayer implements MapMouseListener {

	private static final long serialVersionUID = 1L;

	private OMGraphicList graphics = new OMGraphicList();
	private InformationDelegator infoDelegator;
	//private SidePanel sidePanel;

	public OMAISBaseStationReachLayerA() {
		// TODO Auto-generated constructor stub
	}

	public void addReachArea(OMBaseStation base) {	
		this.addBaseStationReachArea(base);		
	}
	
	public OMGraphicList getGraphicsList() {
	    return graphics;
	}

	// public void addRect(double latN, double lonW, double latS, double lonE) {
	// OMRect r = new OMRect(latN, lonW, latS, lonE, OMGraphic.LINETYPE_RHUMB);
	// r.setFillPaint(Color.blue);
	// graphics.add(r);
	// graphics.project(getProjection(), true);
	// this.repaint();
	// }

	@Override
	public synchronized OMGraphicList prepare() {
		graphics.project(getProjection(), true);
		return graphics;
	}

	public MapMouseListener getMapMouseListener() {
		return this;
	}

	public void addBaseStationReachArea(OMBaseStation bs) {
	    
		if (bs.getReachArea() == null) {
		    return;
		}

		if (bs.getReachArea().size() < 2) {
			
			AISFixedStationData stationData = bs.getStationData();
			
			//Get the radius
			double radius = Math.min(Math.abs(stationData.getLat()-bs.getReachArea().get(0)[0]),Math.abs(stationData.getLon()-bs.getReachArea().get(0)[1]));

			//Create a circle
			OMCircle baseCircle = new OMCircle(stationData.getLat(), stationData.getLon(), radius);
			
			Color c = new Color(0, 255, 0, 100);
			baseCircle.setFillPaint(c);
			graphics.add(baseCircle);
			graphics.project(getProjection(), true);
			this.repaint();
			this.validate();
			
		} else if (bs.getReachArea().size() == 2) {
			return;
			
		} else {

			double[] latlon = new double[bs.getReachArea().size()*2+2];
			int ith = 0;
			for(double[] ll : bs.getReachArea()){
				latlon[ith] = ll[0];
				++ith;
				latlon[ith] = ll[1];
				++ith;
			}

			latlon[latlon.length-2] = bs.getReachArea().get(0)[0];
			latlon[latlon.length-1] = bs.getReachArea().get(0)[1];

			OMPoly baseReach = new OMPoly(latlon, OMGraphic.DECIMAL_DEGREES, OMGraphic.LINETYPE_GREATCIRCLE);

			System.out.println("Drawing reach area. There are "+latlon.length+" coordinates...");

			baseReach.setFillPaint(Color.GREEN);
			graphics.add(baseReach);
			graphics.project(getProjection(), true);
			this.repaint();

		}
	}

	@Override
	public String[] getMouseModeServiceList() {
		String[] ret = new String[2];
		ret[0] = NavMouseMode.modeID;
		ret[1] = SelectMouseMode.modeID;
		return ret;
	}

	@Override
	public boolean mouseClicked(MouseEvent e) {
		//if (this.sidePanel != null) {
		//	this.sidePanel.setText(null);
		//}
		OMList<OMGraphic> allClosest = graphics.findAll(e.getX(), e.getY(), 5.0f);
		for (OMGraphic omGraphic : allClosest) {
			if (omGraphic instanceof OMBaseStation) {
				System.out.println("Mouse clicked on omGraphic: " + omGraphic);

                /*
				if (this.sidePanel != null) {
					OMBaseStation r = (OMBaseStation) omGraphic;
					String text = 
							"<html>Base station at<br>"
									+ r.getLatLon().getLatitude()
									+ (r.getLatLon().getLatitude() > 0 ? "N" : "S")
									+ "," + r.getLatLon().getLongitude()
									+ (r.getLatLon().getLongitude() > 0 ? "E" : "W")
									+ "<br><br>Name:" + r.getName() + "<br>Antenna height:"
									+ r.getAntennaHeight()+"<br>" 
									+"<br>"
									+"Coverage Area:"+"<br>";

					for(double[] latlon : r.getReachArea()){
						text += latlon[0]+";"+latlon[1]+"<br>";
					}

					text+= "</html>";
					this.sidePanel.setText(text);
				}
				*/
				// Consumed by this
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean mouseDragged(MouseEvent e) {
		OMList<OMGraphic> allClosest = graphics.findAll(e.getX(), e.getY(),
				5.0f);
		for (OMGraphic omGraphic : allClosest) {
			if (omGraphic instanceof OMBaseStation) {
				System.out.println("Mouse dragged on omGraphic: " + omGraphic);
				OMBaseStation r = (OMBaseStation) omGraphic;
				Point2D p = ((MapMouseEvent) e).getLatLon();
				r.setLocation(p.getY(), p.getX());
				r.generate(getProjection());
				this.repaint();

				// if (this.infoDelegator != null) {
				// this.infoDelegator.setLabel("FOO");
				// }

				// Consumed by this
				return true;
			}
		}
		return false;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved() {

	}

	@Override
	public boolean mouseMoved(MouseEvent e) {
		// OMList<OMGraphic> allClosest = graphics.findAll(e.getX(), e.getY(),
		// 5.0f);
		// for (OMGraphic omGraphic : allClosest) {
		// if (omGraphic instanceof OMRect) {
		// System.out.println("Mouse over omGraphic: " + omGraphic);
		// // Consumed by this
		// return true;
		// }
		// }
		return false;
	}

	@Override
	public boolean mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void findAndInit(Object obj) {
		System.out.println("Other object in MapHandler: " + obj.getClass());
		if (obj instanceof InformationDelegator) {
			this.infoDelegator = (InformationDelegator) obj;	    
		//} else if (obj instanceof SidePanel) {
		//	this.sidePanel = (SidePanel) obj;
		//	this.sidePanel.setStationReachLayerA(this);
		}
	}

}