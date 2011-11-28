package dk.frv.eavdam.menus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;

/**
 * This class represents a menu item that opens a frame where the user can edit
 * station information.
 */
public class ShowOnMapMenu extends JMenuItem {

    public static final long serialVersionUID = 4L;

	private ShowOnMapMenuActionListener showOnMapMenuActionListener;
	
    public ShowOnMapMenu(EavdamMenu eavdamMenu) {        
        super("Show On Map");                
		this.showOnMapMenuActionListener = new ShowOnMapMenuActionListener(eavdamMenu);
        addActionListener(showOnMapMenuActionListener);
    }
	
}
 
class ShowOnMapMenuActionListener implements ActionListener {

	private EavdamMenu eavdamMenu;

    public ShowOnMapMenuActionListener(EavdamMenu eavdamMenu) {
        super();
        this.eavdamMenu = eavdamMenu;
    }	

    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() instanceof ShowOnMapMenu) {
			eavdamMenu.getStationLayer().actionPerformed(new ActionEvent(eavdamMenu.getStationLayer().getShowOnMapMenuItem(), 1, "Show on map"));
		}
	
	}
	
}
