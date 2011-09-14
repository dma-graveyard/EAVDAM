package dk.frv.eavdam.layers;

import com.bbn.openmap.InformationDelegator;
import com.bbn.openmap.MapBean;
import com.bbn.openmap.event.InfoDisplayEvent;
import com.bbn.openmap.event.MapMouseEvent;
import com.bbn.openmap.event.MapMouseListener;
import com.bbn.openmap.event.NavMouseMode;
import com.bbn.openmap.event.SelectMouseMode;
import com.bbn.openmap.layer.OMGraphicHandlerLayer;
import com.bbn.openmap.omGraphics.OMCircle;
import com.bbn.openmap.omGraphics.OMDistance;
import com.bbn.openmap.omGraphics.OMGraphic;
import com.bbn.openmap.omGraphics.OMGraphicList;
import com.bbn.openmap.omGraphics.OMList;
import com.bbn.openmap.omGraphics.OMRect;
import com.bbn.openmap.proj.Length;
import dk.frv.eavdam.app.SidePanel;
import dk.frv.eavdam.data.ActiveStation;
import dk.frv.eavdam.data.AISFixedStationData;
import dk.frv.eavdam.data.AISFixedStationType;
import dk.frv.eavdam.data.EAVDAMData;
import dk.frv.eavdam.data.Options;
import dk.frv.eavdam.io.derby.DerbyDBInterface;
import dk.frv.eavdam.io.XMLImporter;
import dk.frv.eavdam.menus.EavdamMenu;
import dk.frv.eavdam.menus.OptionsMenuItem;
import dk.frv.eavdam.menus.StationInformationMenuItem;
import dk.frv.eavdam.utils.DBHandler;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.util.List;
import javax.xml.bind.JAXBException;
import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.SwingUtilities;

public class StationLayer extends OMGraphicHandlerLayer implements MapMouseListener, ActionListener {

	private static final long serialVersionUID = 1L;

    private MapBean mapBean;
	private OMGraphicList graphics = new OMGraphicList();
	private InformationDelegator infoDelegator;
	private SidePanel sidePanel;
	private OMAISBaseStationReachLayerA reachLayerA;
	private EavdamMenu eavdamMenu;	
	private JMenuItem editStationMenuItem;
	private JRadioButtonMenuItem ownOperativeStationsMenuItem;
	private JRadioButtonMenuItem ownPlannedStationsMenuItem;
	private OMBaseStation currentlySelectedOMBaseStation;
	
	private EAVDAMData data;
	private int currentIcons = -1;
	
    public StationLayer() {}

    public OMAISBaseStationReachLayerA getReachLayer() {
        return reachLayerA;
    }

    public void addBaseStation(AISFixedStationData stationData) {

	    byte[] bytearr = null;
	    if (stationData.getStationType() == AISFixedStationType.BASESTATION) {
	        if (currentIcons == Options.LARGE_ICONS) {
                bytearr = getImage("share/data/images/ais_base_station.png");
            } else if (currentIcons == Options.SMALL_ICONS) {
                bytearr = getImage("share/data/images/ais_base_station_small.png");
            } else {
                currentIcons = Options.LARGE_ICONS;
                bytearr = getImage("share/data/images/ais_base_station.png");
            }                
	    } else if (stationData.getStationType() == AISFixedStationType.REPEATER) {
	        if (currentIcons == Options.LARGE_ICONS) {
                bytearr = getImage("share/data/images/ais_repeater_station.png");
            } else if (currentIcons == Options.SMALL_ICONS) {
                bytearr = getImage("share/data/images/ais_repeater_station_small.png");
            } else {
                currentIcons = Options.LARGE_ICONS;
                bytearr = getImage("share/data/images/ais_repeater_station.png");
            }          
	    } else if (stationData.getStationType() == AISFixedStationType.RECEIVER) {
	        if (currentIcons == Options.LARGE_ICONS) {
                bytearr = getImage("share/data/images/ais_receiver_station.png");
            } else if (currentIcons == Options.SMALL_ICONS) {
                bytearr = getImage("share/data/images/ais_receiver_station_small.png");
            } else {
                currentIcons = Options.LARGE_ICONS;
                bytearr = getImage("share/data/images/ais_receiver_station.png");
            }  	        
	    } else if (stationData.getStationType() == AISFixedStationType.ATON) {
	        if (currentIcons == Options.LARGE_ICONS) {
                bytearr = getImage("share/data/images/ais_aton_station.png");
            } else if (currentIcons == Options.SMALL_ICONS) {
                bytearr = getImage("share/data/images/ais_aton_station_small.png");
            } else {
                currentIcons = Options.LARGE_ICONS;
                bytearr = getImage("share/data/images/ais_aton_station.png");
            }
        }
        
        OMBaseStation base = new OMBaseStation(stationData, bytearr);
//		base.randomReachArea(1);
//		base.orderReachArea();
		
		graphics.add(base);
		graphics.project(getProjection(), true);
		this.repaint();
		this.validate();

		//this.addBaseStationReachArea(base);
		//reachLayerA.addBaseStationReachArea(base);
		
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

		
	@Override
	public String[] getMouseModeServiceList() {
		String[] ret = new String[2];
		ret[0] = NavMouseMode.modeID;
		ret[1] = SelectMouseMode.modeID;
		return ret;
	}

	@Override
	public boolean mouseClicked(MouseEvent e) {			
		if (SwingUtilities.isLeftMouseButton(e)) {
    		OMList<OMGraphic> allClosest = graphics.findAll(e.getX(), e.getY(), 5.0f);
    		for (OMGraphic omGraphic : allClosest) {
    			if (omGraphic instanceof OMBaseStation) {
    				System.out.println("Mouse clicked on omGraphic: " + omGraphic);
    				OMBaseStation omBaseStation = (OMBaseStation) omGraphic;
    				sidePanel.showInfo(omBaseStation);
    				return true;
    			}
    		}
    	} else if (SwingUtilities.isRightMouseButton(e)) {
            OMList<OMGraphic> allClosest = graphics.findAll(e.getX(), e.getY(), 5.0f);
            if (allClosest == null || allClosest.isEmpty()) {			  
	            JPopupMenu popup = new JPopupMenu();
	            JMenu showOnMapMenu = new JMenu("Show on map");
                ButtonGroup group = new ButtonGroup();
                if (ownOperativeStationsMenuItem == null) {
                    ownOperativeStationsMenuItem = new JRadioButtonMenuItem("Own operative stations", true);
	                ownOperativeStationsMenuItem.addActionListener(this);
	            }
                group.add(ownOperativeStationsMenuItem);
                showOnMapMenu.add(ownOperativeStationsMenuItem);
                if (ownPlannedStationsMenuItem == null) {
                    ownPlannedStationsMenuItem = new JRadioButtonMenuItem("Own planned stations");
	                ownPlannedStationsMenuItem.addActionListener(this);
	            }
                group.add(ownPlannedStationsMenuItem);
                showOnMapMenu.add(ownPlannedStationsMenuItem);                      
                popup.add(showOnMapMenu);
                popup.show(mapBean, e.getX(), e.getY());
                return true;            
            } else {
        		for (OMGraphic omGraphic : allClosest) {
        			if (omGraphic instanceof OMBaseStation) {
        			    currentlySelectedOMBaseStation = (OMBaseStation) omGraphic;
        	            JPopupMenu popup = new JPopupMenu();
                        editStationMenuItem = new JMenuItem("Edit station");
                        editStationMenuItem.addActionListener(this);
                        popup.add(editStationMenuItem);
                        popup.show(mapBean, e.getX(), e.getY());
                        return true;
                    }
                }
            }
    	}
		return false;
	}

	@Override
	public boolean mouseDragged(MouseEvent e) {
	    /*
		OMList<OMGraphic> allClosest = graphics.findAll(e.getX(), e.getY(), 5.0f);
		for (OMGraphic omGraphic : allClosest) {
			if (omGraphic instanceof OMBaseStation) {
				System.out.println("Mouse dragged on omGraphic: " + omGraphic);
				OMBaseStation r = (OMBaseStation) omGraphic;
				Point2D p = ((MapMouseEvent) e).getLatLon();
				r.setLatLon(p.getY(), p.getX());
				r.generate(getProjection());
				this.repaint();

				// if (this.infoDelegator != null) {
				// this.infoDelegator.setLabel("FOO");
				// }
				
				// Consumed by this
				return true;
			}
		}
		*/
		return false;		
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mouseMoved() {}

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
		OMList<OMGraphic> allClosest = graphics.findAll(e.getX(), e.getY(), 5.0f);
		for (OMGraphic omGraphic : allClosest) {
			if (omGraphic instanceof OMBaseStation) {	    
                OMBaseStation r = (OMBaseStation) omGraphic;
                AISFixedStationData stationData = r.getStationData();
				String text = 
					"Base station '" + stationData.getStationName() + "' at "
				    + stationData.getLat()
					+ (stationData.getLon() > 0 ? "N" : "S")
					+ ", " + stationData.getLat()
					+ (stationData.getLon() > 0 ? "E" : "W");
				this.infoDelegator.requestShowToolTip(new InfoDisplayEvent(this, text));					
				return true;
		    }
		}
	    this.infoDelegator.requestHideToolTip();		
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
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == editStationMenuItem) {
            new StationInformationMenuItem(eavdamMenu, currentlySelectedOMBaseStation.getName()).doClick();
        } else if (e.getSource() == ownOperativeStationsMenuItem || e.getSource() == ownPlannedStationsMenuItem) {
            updateStations();
        }
    }

	@Override
	public void findAndInit(Object obj) {
		System.out.println("Other object in MapHandler: " + obj.getClass());
	    if (obj instanceof MapBean) {
			this.mapBean = (MapBean) obj;
		} else if (obj instanceof InformationDelegator) {
			this.infoDelegator = (InformationDelegator) obj;
		//} else if (obj instanceof SidePanel) {
		//	this.sidePanel = (SidePanel) obj;
		//	this.sidePanel.setStationLayer(this);
		} else if (obj instanceof OMAISBaseStationReachLayerA) {
			this.reachLayerA = (OMAISBaseStationReachLayerA) obj;
//			this.sidePanel.setStationLayer(this);

            updateStations();
        
            /*
            this.addBaseStation(60.1, 23.8, "base 1", AISFixedStationType.BASESTATION);
            this.addBaseStation(60.3, 23.6, "base 2", AISFixedStationType.REPEATER);
            this.addBaseStation(60.5, 24.0, "base 3", AISFixedStationType.RECEIVER);            
            this.addBaseStation(61.0, 23.8, "base 4", AISFixedStationType.ATON);  
            */
                        
            /*
            OMCircle o1 = new OMCircle(60.1, 23.8, 0.01);
            o1.setFillPaint(Color.blue);
            graphics.add(o1);
            OMCircle o2 = new OMCircle(60.3, 23.6, 0.01);
            o2.setFillPaint(Color.blue);
            graphics.add(o2);
            OMCircle o3 = new OMCircle(60.5, 24.0, 0.01);
            o3.setFillPaint(Color.blue);                        
            graphics.add(o3);
            this.repaint();
            */
		} else if (obj instanceof EavdamMenu) {
		    this.eavdamMenu = (EavdamMenu) obj;
		} else if (obj instanceof SidePanel) {
		    this.sidePanel = (SidePanel) obj;
		}
	}

	private byte[] getImage(String filename) {    
        try {
            File file = new File(filename); 
            int size = (int) file.length(); 
            byte[] bytes = new byte[size]; 
            DataInputStream dis = new DataInputStream(new FileInputStream(file)); 
            int read = 0;
            int numRead = 0;
            while (read < bytes.length && (numRead=dis.read(bytes, read, bytes.length-read)) >= 0) {
                read = read + numRead;
            }
            return bytes;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void updateStations() {

        EAVDAMData data = DBHandler.getData();                        
        if (data != null) {
             Options options = OptionsMenuItem.loadOptions();
             currentIcons = options.getIconsSize();
             graphics.clear();            
            //reachLayerA.getGraphicsList().clear();  
            if (ownOperativeStationsMenuItem == null) {
                ownOperativeStationsMenuItem = new JRadioButtonMenuItem("Own operative stations", true);
	            ownOperativeStationsMenuItem.addActionListener(this);
	        }
	        if (ownPlannedStationsMenuItem == null) {
                ownPlannedStationsMenuItem = new JRadioButtonMenuItem("Own planned stations");
	            ownPlannedStationsMenuItem.addActionListener(this);	            
	        }
            if (ownOperativeStationsMenuItem.isSelected() || ownPlannedStationsMenuItem.isSelected()) {
                List<ActiveStation> activeStations = data.getActiveStations();
                if (activeStations != null) {
                    for (ActiveStation as : activeStations) {
                        if (as.getStations() != null) {
                            for (AISFixedStationData stationData : as.getStations()) {
                                if (stationData.getStatus().getStatusID() == DerbyDBInterface.STATUS_ACTIVE &&
                                        ownOperativeStationsMenuItem.isSelected()) {
                                    this.addBaseStation(stationData);
                                } else if (stationData.getStatus().getStatusID() == DerbyDBInterface.STATUS_PLANNED &&
                                        ownPlannedStationsMenuItem.isSelected()) {
                                     this.addBaseStation(stationData);
                                }
                            }
                        }
                    }
                }
            }            
        }
    }

}