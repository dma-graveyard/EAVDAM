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

/**
 * This class represents a menu item where the user can select which stations are shown on the map.
 */
public class ShowOnMapMenu extends JMenu implements ActionListener {

    public static final long serialVersionUID = 3L;
	
	private EavdamMenu eavdamMenu;
	
	private JCheckBoxMenuItem ownOperativeStationsMenuItem;
	private JCheckBoxMenuItem ownPlannedStationsMenuItem;
	private List<JCheckBoxMenuItem> simulationMenuItems;
	private List<JCheckBoxMenuItem> otherUsersStationsMenuItems;
	
	public JCheckBoxMenuItem getOwnOperativeStationsMenuItem() {
		return ownOperativeStationsMenuItem;
	}

	public JCheckBoxMenuItem getOwnPlannedStationsMenuItem() {
		return ownPlannedStationsMenuItem;
	}
	
	public List<JCheckBoxMenuItem> getSimulationMenuItems() {
		return simulationMenuItems;
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
			ownOperativeStationsMenuItem = new JCheckBoxMenuItem("Own operative stations", true);
	        ownOperativeStationsMenuItem.addActionListener(this);
	    }
        add(ownOperativeStationsMenuItem);
        if (ownPlannedStationsMenuItem == null) {
            ownPlannedStationsMenuItem = new JCheckBoxMenuItem("Own planned stations", false);
	        ownPlannedStationsMenuItem.addActionListener(this);
	    }
        add(ownPlannedStationsMenuItem);  
        if (simulationMenuItems != null) {
            for (JCheckBoxMenuItem simulationMenuItem : simulationMenuItems) {
                add(simulationMenuItem);
            }
        }    
        if (otherUsersStationsMenuItems != null) {
            for (JCheckBoxMenuItem otherUsersStationsMenuItem : otherUsersStationsMenuItems) {
                add(otherUsersStationsMenuItem);
            }
        }
    }

    private void updateSimulations() {        
        simulationMenuItems = new ArrayList<JCheckBoxMenuItem>();
        EAVDAMData data = DBHandler.getData();                        
        if (data != null) {            
            List<Simulation> simulatedStations = data.getSimulatedStations();
            if (simulatedStations != null) {
                for (Simulation s : simulatedStations) {
                    JCheckBoxMenuItem simulationMenuItem = new JCheckBoxMenuItem(StationInformationMenuItem.SIMULATION_LABEL + ": " + s.getName(), false);
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
                    JCheckBoxMenuItem otherUsersStationsMenuItem = new JCheckBoxMenuItem(StationInformationMenuItem.STATIONS_OF_ORGANIZATION_LABEL + " " + user.getOrganizationName(), true);
                    otherUsersStationsMenuItem.addActionListener(this);
                    otherUsersStationsMenuItems.add(otherUsersStationsMenuItem);
                }
            }
        }
    }  
	
	@Override
    public void actionPerformed(ActionEvent e) {
        eavdamMenu.getStationLayer().updateStations();
    }

}

