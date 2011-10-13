package dk.frv.eavdam.layers;

import com.bbn.openmap.InformationDelegator;
import com.bbn.openmap.MapBean;
import com.bbn.openmap.event.InfoDisplayEvent;
import com.bbn.openmap.event.MapMouseEvent;
import com.bbn.openmap.event.MapMouseListener;
import com.bbn.openmap.event.NavMouseMode;
import com.bbn.openmap.event.SelectMouseMode;
import com.bbn.openmap.layer.OMGraphicHandlerLayer;
import com.bbn.openmap.omGraphics.OMAction;
import com.bbn.openmap.omGraphics.OMCircle;
import com.bbn.openmap.omGraphics.OMDistance;
import com.bbn.openmap.omGraphics.OMGraphic;
import com.bbn.openmap.omGraphics.OMGraphicList;
import com.bbn.openmap.omGraphics.OMList;
import com.bbn.openmap.omGraphics.OMPoly;
import com.bbn.openmap.omGraphics.OMRect;
import com.bbn.openmap.proj.Length;
import com.bbn.openmap.tools.drawing.DrawingTool;
import com.bbn.openmap.tools.drawing.DrawingToolRequestor;
import com.bbn.openmap.tools.drawing.OMDrawingTool;
import dk.frv.eavdam.app.SidePanel;
import dk.frv.eavdam.data.ActiveStation;
import dk.frv.eavdam.data.AISFixedStationCoverage;
import dk.frv.eavdam.data.AISFixedStationData;
import dk.frv.eavdam.data.AISFixedStationStatus;
import dk.frv.eavdam.data.AISFixedStationType;
import dk.frv.eavdam.data.Antenna;
import dk.frv.eavdam.data.EAVDAMData;
import dk.frv.eavdam.data.EAVDAMUser;
import dk.frv.eavdam.data.Options;
import dk.frv.eavdam.data.OtherUserStations;
import dk.frv.eavdam.data.Simulation;
import dk.frv.eavdam.io.derby.DerbyDBInterface;
import dk.frv.eavdam.io.XMLImporter;
import dk.frv.eavdam.menus.EavdamMenu;
import dk.frv.eavdam.menus.OptionsMenuItem;
import dk.frv.eavdam.menus.StationInformationMenuItem;
import dk.frv.eavdam.utils.DBHandler;
import dk.frv.eavdam.utils.RoundCoverage;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.JAXBException;
import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.SwingUtilities;

public class StationLayer extends OMGraphicHandlerLayer implements MapMouseListener, ActionListener, DrawingToolRequestor {

	private static final long serialVersionUID = 1L;

    private MapBean mapBean;
	private OMGraphicList graphics = new OMGraphicList();
	private InformationDelegator infoDelegator;
    private DrawingTool drawingTool;
    private final com.bbn.openmap.tools.drawing.DrawingToolRequestor layer = this;
	private SidePanel sidePanel;
	private OMAISBaseStationReachLayerA reachLayerA;
	private EavdamMenu eavdamMenu;	
	private JMenuItem editStationMenuItem;
	private JMenuItem editCoverageMenuItem;
	//private JCheckBoxMenuItem ownOperativeStationsMenuItem;
	//private JCheckBoxMenuItem ownPlannedStationsMenuItem;
	private OMBaseStation currentlySelectedOMBaseStation;
	
	private EAVDAMData data;
	private int currentIcons = -1;
	//private List<JCheckBoxMenuItem> simulationMenuItems;
	//private List<JCheckBoxMenuItem> otherUsersStationsMenuItems;
	
	private boolean stationsInitiallyUpdated = false;
	
	private Map<OMBaseStation, OMGraphic> reachAreas = new HashMap<OMBaseStation, OMGraphic>();
	
    public StationLayer() {}

    public OMAISBaseStationReachLayerA getReachLayer() {
        return reachLayerA;
    }

    public void addBaseStation(Object datasetSource, AISFixedStationData stationData) {

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
        
        OMBaseStation base = new OMBaseStation(datasetSource, stationData, bytearr);		
		Antenna antenna = stationData.getAntenna();
		if (antenna != null) {			
			if (stationData.getCoverage() != null && stationData.getCoverage().getCoveragePoints() != null && stationData.getCoverage().getCoveragePoints().size() > 2) {
				base.setReachArea(stationData.getCoverage().getCoveragePoints());
			} else {
				ArrayList<double[]> points = (ArrayList<double[]>) RoundCoverage.getRoundCoverage(antenna.getAntennaHeight()+antenna.getTerrainHeight(), 4, stationData.getLat(), stationData.getLon(), 25);  // XXX: receiverHeight?				
				base.setReachArea(points);
			}
		}
//		base.randomReachArea(1);
//		base.orderReachArea();
		
		graphics.add(base);
		graphics.project(getProjection(), true);
		this.repaint();
		this.validate();

		Object reachAreaGraphics = reachLayerA.addBaseStationReachArea(base);
		if (reachAreaGraphics != null) {
			if (reachAreaGraphics instanceof OMCircle) {
				reachAreas.put(base, (OMCircle) reachAreaGraphics);
			} else if (reachAreaGraphics instanceof OMPoly) {
				reachAreas.put(base, (OMPoly) reachAreaGraphics);
			}		
		}
		
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
	
	 public DrawingTool getDrawingTool() {
        // Usually set in the findAndInit() method.
        return drawingTool;
    }

    public void setDrawingTool(DrawingTool dt) {
        // Called by the findAndInit method.
        drawingTool = dt;
    }
	
    /**
     * Called when the DrawingTool is complete, providing the layer with the
     * modified OMGraphic.
     */
	@Override	 
    public void drawingComplete(OMGraphic omg, OMAction action) {
		if (omg instanceof OMCircle) {
			// circles are currently presented as OMPolys
		} else if (omg instanceof OMPoly) {
			// saves the data
			
			ArrayList<double[]> points = new ArrayList<double[]>();
			double[] latlonArray = ((OMPoly) omg).getLatLonArray();
			int index=0;
			while (index+1<latlonArray.length) {
				double lat = latlonArray[index];
				double lon = latlonArray[index+1];
				double[] latlon = new double[2];
				latlon[0] = RoundCoverage.radians2degrees(lat);
				latlon[1] = RoundCoverage.radians2degrees(lon);
				points.add(latlon);
				index = index+2;
			}

			if (data == null) {
				data = DBHandler.getData();                        
			}
			if (currentlySelectedOMBaseStation.getDatasetSource() == null) {
				List<ActiveStation> activeStations = data.getActiveStations();
				if (activeStations != null) {
					for (int i=0; i< activeStations.size(); i++) {
						ActiveStation as = activeStations.get(i);
						if (as.getStations() != null) {
							for (int j=0; j<as.getStations().size(); j++) {
								AISFixedStationData stationData = as.getStations().get(j);
								if (stationData.getStationDBID() == currentlySelectedOMBaseStation.getStationData().getStationDBID()) {
									AISFixedStationCoverage coverage = stationData.getCoverage();
									if (coverage == null) {
										coverage = new AISFixedStationCoverage();
									}
									coverage.setCoveragePoints(points);
									stationData.setCoverage(coverage);
									as.getStations().set(j, stationData);
									data.getActiveStations().set(i, as);		
									break;									
								}
							}
						}
					}
				}
			} else if (currentlySelectedOMBaseStation.getDatasetSource() instanceof String) {  // simulation
				String selectedSimulation = (String) currentlySelectedOMBaseStation.getDatasetSource();				
                List<Simulation> simulatedStations = data.getSimulatedStations();
                for (Simulation s : data.getSimulatedStations()) {
                    if (selectedSimulation.equals(s.getName())) {
                        List<AISFixedStationData> stations = s.getStations();
						for (int i=0; i<stations.size(); i++) {
							AISFixedStationData stationData = stations.get(i);
							if (stationData.getStationDBID() == currentlySelectedOMBaseStation.getStationData().getStationDBID()) {
								AISFixedStationCoverage coverage = stationData.getCoverage();
								if (coverage == null) {
									coverage = new AISFixedStationCoverage();
								}
								coverage.setCoveragePoints(points);
								stationData.setCoverage(coverage);
								stations.set(i, stationData);
								s.setStations(stations);
								data.setSimulatedStations(simulatedStations);
								break;																	
							}	
						}
                    }
                }
		    } else if (currentlySelectedOMBaseStation.getDatasetSource() instanceof EAVDAMUser) {  // Other user's dataset
				String selectedOrganization = ((EAVDAMUser) currentlySelectedOMBaseStation.getDatasetSource()).getOrganizationName();           
                for (int i=0; i<data.getOtherUsersStations().size(); i++) {
                    OtherUserStations ous = data.getOtherUsersStations().get(i);
                    if (selectedOrganization.equals(ous.getUser().getOrganizationName())) {
                        List<ActiveStation> stations = ous.getStations();
						for (int j=0; j< stations.size(); j++) {
							ActiveStation as = stations.get(j);
							if (as.getStations() != null) {
								for (int k=0; k<as.getStations().size(); k++) {
									AISFixedStationData stationData = as.getStations().get(k);
									if (stationData.getStationDBID() == currentlySelectedOMBaseStation.getStationData().getStationDBID()) {
										AISFixedStationCoverage coverage = stationData.getCoverage();
										if (coverage == null) {
											coverage = new AISFixedStationCoverage();
										}
										coverage.setCoveragePoints(points);
										stationData.setCoverage(coverage);
										as.getStations().set(k, stationData);										
										ous.getStations().set(j, as);
										data.getOtherUsersStations().set(i, ous);		
										break;									
									}
								}
							}
						}
					}
                }
            }          
		     
			DBHandler.saveData(data);    		
			
		}
	
/*
        Object obj = omg.getAppObject();

        if (obj != null && (obj == internalKey || obj == externalKey)
                && !action.isMask(OMGraphicConstants.DELETE_GRAPHIC_MASK)) {

            java.awt.Shape filterShape = omg.getShape();
            OMGraphicList filteredList = filter(filterShape,
                    (omg.getAppObject() == internalKey));
            if (Debug.debugging("demo")) {
                Debug.output("DemoLayer filter: "
                        + filteredList.getDescription());
            }
        } else {
            if (!doAction(omg, action)) {
                // null OMGraphicList on failure, should only occur if
                // OMGraphic is added to layer before it's ever been
                // on the map.
                setList(new OMGraphicList());
                doAction(omg, action);
            }
        }
*/
        repaint();
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
    			} else {
					System.out.println("Mouse clicked on omGraphic: " + omGraphic);
				}
    		}
    	} else if (SwingUtilities.isRightMouseButton(e)) {
            OMList<OMGraphic> allClosest = graphics.findAll(e.getX(), e.getY(), 5.0f);
            if (allClosest == null || allClosest.isEmpty()) {			  
	            JPopupMenu popup = new JPopupMenu();
				/*
	            JMenu showOnMapMenu = new JMenu("Show on map");
                //ButtonGroup group = new ButtonGroup();
                if (ownOperativeStationsMenuItem == null) {
                    ownOperativeStationsMenuItem = new JCheckBoxMenuItem("Own operative stations", true);
	                ownOperativeStationsMenuItem.addActionListener(this);
	            }
                //group.add(ownOperativeStationsMenuItem);
                showOnMapMenu.add(ownOperativeStationsMenuItem);
                if (ownPlannedStationsMenuItem == null) {
                    ownPlannedStationsMenuItem = new JCheckBoxMenuItem("Own planned stations", false);
	                ownPlannedStationsMenuItem.addActionListener(this);
	            }
                //group.add(ownPlannedStationsMenuItem);
                showOnMapMenu.add(ownPlannedStationsMenuItem);  
                if (simulationMenuItems != null) {
                    for (JCheckBoxMenuItem simulationMenuItem : simulationMenuItems) {
                        //group.add(simulationMenuItem);
                        showOnMapMenu.add(simulationMenuItem);
                    }
                }    
               if (otherUsersStationsMenuItems != null) {
                    for (JCheckBoxMenuItem otherUsersStationsMenuItem : otherUsersStationsMenuItems) {
                        //group.add(otherUsersStationsMenuItem);
                        showOnMapMenu.add(otherUsersStationsMenuItem);
                    }
                }                        
				*/				
                popup.add(eavdamMenu.getShowOnMapMenu());
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
                        editCoverageMenuItem = new JMenuItem("Edit coverage");
                        editCoverageMenuItem.addActionListener(this);
                        popup.add(editCoverageMenuItem);							
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
		
		    if (currentlySelectedOMBaseStation.getDatasetSource() == null) {
				new StationInformationMenuItem(eavdamMenu, StationInformationMenuItem.OWN_ACTIVE_STATIONS_LABEL + "/" +
					StationInformationMenuItem.OPERATIVE_LABEL, currentlySelectedOMBaseStation.getName()).doClick();        
			} else if (currentlySelectedOMBaseStation.getDatasetSource() instanceof String) {  // simulation
				String selectedSimulation = (String) currentlySelectedOMBaseStation.getDatasetSource();
				new StationInformationMenuItem(eavdamMenu, StationInformationMenuItem.SIMULATION_LABEL + ": " +
					selectedSimulation, currentlySelectedOMBaseStation.getName()).doClick(); 
			} else if (currentlySelectedOMBaseStation.getDatasetSource() instanceof EAVDAMUser) {  // Other user's dataset
				String selectedOrganization = ((EAVDAMUser) currentlySelectedOMBaseStation.getDatasetSource()).getOrganizationName();
				new StationInformationMenuItem(eavdamMenu, StationInformationMenuItem.STATIONS_OF_ORGANIZATION_LABEL + " " +
					selectedOrganization, currentlySelectedOMBaseStation.getName()).doClick(); 							
            }

		} else if (e.getSource() == editCoverageMenuItem) {

			DrawingTool dt = getDrawingTool();
			if (dt != null) {
				dt.edit(reachAreas.get(currentlySelectedOMBaseStation), this);
			}
			
        } else {
            updateStations();
        }
    }

	@Override
	public void findAndInit(Object obj) {
	    if (obj instanceof MapBean) {
			this.mapBean = (MapBean) obj;
		} else if (obj instanceof InformationDelegator) {
			this.infoDelegator = (InformationDelegator) obj;
		} else if (obj instanceof OMAISBaseStationReachLayerA) {
			this.reachLayerA = (OMAISBaseStationReachLayerA) obj;
		} else if (obj instanceof EavdamMenu) {
		    this.eavdamMenu = (EavdamMenu) obj;
		} else if (obj instanceof SidePanel) {
		    this.sidePanel = (SidePanel) obj;
		} else if (obj instanceof DrawingTool) {
            setDrawingTool((DrawingTool) obj);
        }
		if (eavdamMenu != null && reachLayerA != null && sidePanel != null && !stationsInitiallyUpdated) {
			updateStations();
			stationsInitiallyUpdated = true;
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
	
		if (eavdamMenu == null || eavdamMenu.getShowOnMapMenu() == null) {
			return;
		}

        data = DBHandler.getData();                        
        if (data != null) {
             Options options = OptionsMenuItem.loadOptions();
             currentIcons = options.getIconsSize();
             graphics.clear();
			 reachLayerA.getGraphicsList().clear();
            if (eavdamMenu.getShowOnMapMenu().getOwnOperativeStationsMenuItem().isSelected() || eavdamMenu.getShowOnMapMenu().getOwnPlannedStationsMenuItem().isSelected()) {
                List<ActiveStation> activeStations = data.getActiveStations();
                if (activeStations != null) {
                    for (ActiveStation as : activeStations) {
                        if (as.getStations() != null) {
                            for (AISFixedStationData stationData : as.getStations()) {
                                if (stationData.getStatus().getStatusID() == DerbyDBInterface.STATUS_ACTIVE &&
                                        eavdamMenu.getShowOnMapMenu().getOwnOperativeStationsMenuItem().isSelected()) {
                                    this.addBaseStation(null, stationData);
								}
                                if (stationData.getStatus().getStatusID() == DerbyDBInterface.STATUS_PLANNED &&
                                        eavdamMenu.getShowOnMapMenu().getOwnPlannedStationsMenuItem().isSelected()) {
                                     this.addBaseStation(null, stationData);
                                }
                            }
                        }
                    }
                }
			}
            if (eavdamMenu.getShowOnMapMenu().getSimulationMenuItems() != null) {
				for (JCheckBoxMenuItem simulationMenuItem : eavdamMenu.getShowOnMapMenu().getSimulationMenuItems()) {
					if (simulationMenuItem.isSelected()) {
						String temp = StationInformationMenuItem.SIMULATION_LABEL + ": ";
						String selectedSimulation = simulationMenuItem.getText().substring(temp.length());
						if (data.getSimulatedStations() != null) {
							for (Simulation s : data.getSimulatedStations()) {
								if (s.getName().equals(selectedSimulation)) {
									List<AISFixedStationData> stations = s.getStations();
									for (AISFixedStationData stationData : stations) {
										this.addBaseStation(s.getName(), stationData);
									}
									break;
								}
							}
						}
					}   
				}
			}			
            if (eavdamMenu.getShowOnMapMenu().getOtherUsersStationsMenuItems() != null) {
				for (JCheckBoxMenuItem otherUsersStationsMenuItem : eavdamMenu.getShowOnMapMenu().getOtherUsersStationsMenuItems()) {
					if (otherUsersStationsMenuItem.isSelected()) {
						String temp = StationInformationMenuItem.STATIONS_OF_ORGANIZATION_LABEL + " ";
						String selectedOtherUser = otherUsersStationsMenuItem.getText().substring(temp.length());
						if (data.getOtherUsersStations() != null) {
							for (OtherUserStations ous : data.getOtherUsersStations()) {
								EAVDAMUser user = ous.getUser();
								if (user.getOrganizationName().equals(selectedOtherUser)) {
									List<ActiveStation> activeStations = ous.getStations();
									for (ActiveStation as : activeStations) {
										List<AISFixedStationData> stations = as.getStations();
										for (AISFixedStationData station : stations) {
											if (station.getStatus().getStatusID() == DerbyDBInterface.STATUS_ACTIVE) {
												this.addBaseStation(user, station);
											}
										}
									}
								}
								break;
							}
						}
					}
				}               
            }                           
            this.repaint();
		    this.validate();
        }
    }

}