package dk.frv.eavdam.menus;

import com.bbn.openmap.gui.AbstractOpenMapMenu;
import com.bbn.openmap.gui.OpenMapFrame;
import dk.frv.eavdam.io.EmailSender;
import dk.frv.eavdam.layers.StationLayer;
import javax.swing.JSeparator;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

/**
 * This class represents a menu containing eavdam related items.
 */
public class EavdamMenu extends AbstractOpenMapMenu implements MenuListener {

    public static final long serialVersionUID = 3L;

    private String defaultText = "Eavdam";
    private int defaultMnemonic = 'E';
    
    private OpenMapFrame openMapFrame;
    private StationLayer stationLayer;
	
	private UserInformationMenuItem userInformationMenuItem;
	private StationInformationMenuItem stationInformationMenuItem;
	private ShowOnMapMenu showOnMapMenu;
	private OptionsMenuItem optionsMenuItem;

    public EavdamMenu() {
        super();
        setText(defaultText);
        setMnemonic(defaultMnemonic);

		userInformationMenuItem = new UserInformationMenuItem(this);
        add(userInformationMenuItem);
        //add(new AddStationMenuItem(this));
		stationInformationMenuItem = new StationInformationMenuItem(this);
        add(stationInformationMenuItem);         
		showOnMapMenu = new ShowOnMapMenu(this);
		add(showOnMapMenu);
		optionsMenuItem = new OptionsMenuItem(this);
        add(optionsMenuItem);
        // add(new JSeparator());
		addMenuListener(this);
    }
	
	public void	menuCanceled(MenuEvent e) {}
	public void	menuDeselected(MenuEvent e) {}
    public void	menuSelected(MenuEvent e) {
		removeAll();
		add(userInformationMenuItem);
		add(stationInformationMenuItem);
		add(showOnMapMenu);
		add(optionsMenuItem);
	}
	
    public OpenMapFrame getOpenMapFrame() {
        return openMapFrame;
    }
    
    public StationLayer getStationLayer() {
        return stationLayer;
    }
	
	public ShowOnMapMenu getShowOnMapMenu() {
		return showOnMapMenu;
	}

	@Override
	public void findAndInit(Object obj) {
		if (obj instanceof OpenMapFrame) {
			this.openMapFrame = (OpenMapFrame) obj;
		} else if (obj instanceof StationLayer) {
		    this.stationLayer = (StationLayer) obj;
		}
	}

}