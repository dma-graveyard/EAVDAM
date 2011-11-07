package dk.frv.eavdam.menus;

import dk.frv.eavdam.data.EAVDAMData;
import dk.frv.eavdam.data.EAVDAMUser;
import dk.frv.eavdam.data.OtherUserStations;
import dk.frv.eavdam.data.Simulation;
import dk.frv.eavdam.utils.DBHandler;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

/**
 * This class represents a menu item where the user can select which stations are shown on the map.
 */
public class ShowOnMapMenu extends JMenu implements ActionListener {

    public static final long serialVersionUID = 3L;
	
	private EavdamMenu eavdamMenu;
	
	private JCheckBoxMenuItem ownOperativeStationsMenuItem;
	private JCheckBoxMenuItem ownPlannedStationsMenuItem;
	private List<JCheckBoxMenuItem> simulationMenuItems;
	private JCheckBoxMenuItem showAllOtherUsersStationsMenuItem;
	private List<JCheckBoxMenuItem> otherUsersStationsMenuItems;
	private JCheckBoxMenuItem showNominalRXCoverageMenuItem;
	private JCheckBoxMenuItem showNominalTXCoverageMenuItem;
	private JCheckBoxMenuItem showNominalInterferenceAreaMenuItem;
	
	public JCheckBoxMenuItem getOwnOperativeStationsMenuItem() {
		return ownOperativeStationsMenuItem;
	}

	public JCheckBoxMenuItem getOwnPlannedStationsMenuItem() {
		return ownPlannedStationsMenuItem;
	}
	
	public List<JCheckBoxMenuItem> getSimulationMenuItems() {
		return simulationMenuItems;
	}
	
	public JCheckBoxMenuItem getShowAllOtherUsersStationsMenuItem() {
		return showAllOtherUsersStationsMenuItem;
	}
	
	public List<JCheckBoxMenuItem> getOtherUsersStationsMenuItems() {
		return otherUsersStationsMenuItems;
	}	
	
    public ShowOnMapMenu(EavdamMenu eavdamMenu) {        
        super("Show On Map");
		
		this.eavdamMenu = eavdamMenu;
 
		updateSimulations();
		updateOtherUsers();		
		
		if (ownOperativeStationsMenuItem == null) {
			ownOperativeStationsMenuItem = new JCheckBoxMenuItem("Show own operative stations", true);
	        ownOperativeStationsMenuItem.addActionListener(this);
	    }
        add(ownOperativeStationsMenuItem);
        if (ownPlannedStationsMenuItem == null) {
            ownPlannedStationsMenuItem = new JCheckBoxMenuItem("Show own planned stations", false);
	        ownPlannedStationsMenuItem.addActionListener(this);
	    }		
        add(ownPlannedStationsMenuItem); 
		if (simulationMenuItems != null && !simulationMenuItems.isEmpty()) { 		
			add(new JSeparator());
		}
        if (simulationMenuItems != null) {
            for (JCheckBoxMenuItem simulationMenuItem : simulationMenuItems) {
                add(simulationMenuItem);
            }
        }
		if (otherUsersStationsMenuItems != null && !otherUsersStationsMenuItems.isEmpty()) { 		
			add(new JSeparator());
			showAllOtherUsersStationsMenuItem = new JCheckBoxMenuItem("Show all other organizations' stations", true);
			showAllOtherUsersStationsMenuItem.addActionListener(this);
			add(showAllOtherUsersStationsMenuItem);
		}
		add(new JSeparator());
		showNominalRXCoverageMenuItem = new JCheckBoxMenuItem("Show Nominal RX coverage");
		showNominalRXCoverageMenuItem.addActionListener(this);
		add(showNominalRXCoverageMenuItem);
		showNominalTXCoverageMenuItem = new JCheckBoxMenuItem("Show Nominal TX coverage");
		showNominalTXCoverageMenuItem.addActionListener(this);
		add(showNominalTXCoverageMenuItem);
		showNominalInterferenceAreaMenuItem = new JCheckBoxMenuItem("Show Nominal Interference area");
		showNominalInterferenceAreaMenuItem.addActionListener(this);
		add(showNominalInterferenceAreaMenuItem);		
		/*
        if (otherUsersStationsMenuItems != null) {
            for (JCheckBoxMenuItem otherUsersStationsMenuItem : otherUsersStationsMenuItems) {
                add(otherUsersStationsMenuItem);
            }
        }
		*/
    }

    private void updateSimulations() {        
        simulationMenuItems = new ArrayList<JCheckBoxMenuItem>();
        EAVDAMData data = DBHandler.getData();                        
        if (data != null) {            
            List<Simulation> simulatedStations = data.getSimulatedStations();
            if (simulatedStations != null) {
                for (Simulation s : simulatedStations) {
                    JCheckBoxMenuItem simulationMenuItem = new JCheckBoxMenuItem("Show " + StationInformationMenuItem.SIMULATION_LABEL.toLowerCase() + " " + s.getName(), false);
                    simulationMenuItem.addActionListener(this);
                    simulationMenuItems.add(simulationMenuItem);
                }
            }
        }
    }
    
    private void updateOtherUsers() {        
        otherUsersStationsMenuItems = new ArrayList<JCheckBoxMenuItem>();
        EAVDAMData data = DBHandler.getData();
        if (data != null) {            
            List<OtherUserStations> otherUsersStations = data.getOtherUsersStations();
            if (otherUsersStations != null) {
                for (OtherUserStations ous : otherUsersStations) {
                    EAVDAMUser user = ous.getUser();
                    JCheckBoxMenuItem otherUsersStationsMenuItem = new JCheckBoxMenuItem("Show " + StationInformationMenuItem.STATIONS_OF_ORGANIZATION_LABEL.toLowerCase() + " " + user.getOrganizationName(), false);
					System.out.println("added Show " + StationInformationMenuItem.STATIONS_OF_ORGANIZATION_LABEL.toLowerCase());
                    otherUsersStationsMenuItem.addActionListener(this);
                    otherUsersStationsMenuItems.add(otherUsersStationsMenuItem);
                }
            }
        }
    }  
	
	public void updateCoverageItems(boolean nominalRXCoverageSelected, boolean nominalTXCoverageSelected, boolean nominalInterferenceAreaSelected) {
		showNominalRXCoverageMenuItem.setSelected(nominalRXCoverageSelected);
		showNominalTXCoverageMenuItem.setSelected(nominalTXCoverageSelected);
		showNominalInterferenceAreaMenuItem.setSelected(nominalInterferenceAreaSelected);
	}
	
	@Override
    public void actionPerformed(ActionEvent e) {
		if (e.getSource() == showAllOtherUsersStationsMenuItem) {
			if (showAllOtherUsersStationsMenuItem.isSelected()) {
				for (JCheckBoxMenuItem item : otherUsersStationsMenuItems) {
					remove(item);
				}				
			} else {
				boolean isPresent = false;
				for (int i=0; i<getItemCount(); i++) {
					JMenuItem i1 = getItem(i);
					if (i1 != null) {
						for (JCheckBoxMenuItem i2 : otherUsersStationsMenuItems) {
							if (i2 != null) {
								if (i1.equals(i2)) {
									isPresent = true;
									break;
								}
							}
						}
					}
				}
				if (!isPresent) {
					for (JCheckBoxMenuItem item : otherUsersStationsMenuItems) {
						item.setSelected(false);
						add(item, getItemCount()-4);
					}				
				}
			}
			eavdamMenu.getStationLayer().updateStations();			
		} else if (e.getSource() == showNominalRXCoverageMenuItem) {
			if (showNominalRXCoverageMenuItem.isSelected()) {
				eavdamMenu.getLayerHandler().turnLayerOn(true, eavdamMenu.getStationLayer().getReceiveCoverageLayer());				
			} else {
				eavdamMenu.getLayerHandler().turnLayerOn(false, eavdamMenu.getStationLayer().getReceiveCoverageLayer());				
			}
		} else if (e.getSource() == showNominalTXCoverageMenuItem) {
			if (showNominalTXCoverageMenuItem.isSelected()) {
				eavdamMenu.getLayerHandler().turnLayerOn(true, eavdamMenu.getStationLayer().getTransmitCoverageLayer());	
			} else {
				eavdamMenu.getLayerHandler().turnLayerOn(false, eavdamMenu.getStationLayer().getTransmitCoverageLayer());					
			}		
		} else if (e.getSource() == showNominalInterferenceAreaMenuItem) {
			if (showNominalInterferenceAreaMenuItem.isSelected()) {
				eavdamMenu.getLayerHandler().turnLayerOn(true, eavdamMenu.getStationLayer().getInterferenceCoverageLayer());
			} else {
				eavdamMenu.getLayerHandler().turnLayerOn(false, eavdamMenu.getStationLayer().getInterferenceCoverageLayer());			
			}		 
		} else {
			eavdamMenu.getStationLayer().updateStations();
		}
    }

}

