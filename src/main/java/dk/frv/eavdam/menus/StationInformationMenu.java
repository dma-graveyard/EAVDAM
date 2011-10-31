package dk.frv.eavdam.menus;

import javax.swing.JMenuItem;

/**
 * This class represents a menu item that opens a frame where the user can edit
 * station information.
 */
public class StationInformationMenu extends JMenuItem {

    public static final long serialVersionUID = 3L;

    public StationInformationMenu(EavdamMenu eavdamMenu) {
        super("Edit Station Information");       
        addActionListener(new StationInformationMenuItem(eavdamMenu, null, null));
    }

    public StationInformationMenu(EavdamMenu eavdamMenu, String dataset, String stationName) {
        super("Edit Station Information");       
        addActionListener(new StationInformationMenuItem(eavdamMenu, dataset, stationName));
    }
}
        