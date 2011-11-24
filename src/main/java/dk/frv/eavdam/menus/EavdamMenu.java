package dk.frv.eavdam.menus;

import com.bbn.openmap.LayerHandler;
import com.bbn.openmap.gui.AbstractOpenMapMenu;
import com.bbn.openmap.gui.OpenMapFrame;
import dk.frv.eavdam.data.EAVDAMData;
import dk.frv.eavdam.data.EAVDAMUser;
import dk.frv.eavdam.io.EmailSender;
import dk.frv.eavdam.layers.StationLayer;
import dk.frv.eavdam.utils.DBHandler;
import dk.frv.eavdam.utils.IconChanger;
import javax.swing.JSeparator;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

/**
 * This class represents a menu containing eavdam related items.
 */
public class EavdamMenu extends AbstractOpenMapMenu implements MenuListener {

    public static final long serialVersionUID = 4L;

    private String defaultText = "Eavdam";
    private int defaultMnemonic = 'E';
    
    private OpenMapFrame openMapFrame;
    private StationLayer stationLayer;
	private LayerHandler layerHandler;
	
	private UserInformationMenuItem userInformationMenuItem;
	private StationInformationMenu stationInformationMenu;
	//private ShowOnMapMenu showOnMapMenu;
	private ShapeLayersMenu shapeLayersMenu;
	private IssuesMenuItem issuesMenuItem;
	private ListOfRulesMenuItem listOfRulesMenuItem;
	private OptionsMenuItem optionsMenuItem;
	
    public EavdamMenu() {
        super();
        setText(defaultText);
        setMnemonic(defaultMnemonic);
		
        EAVDAMData data = DBHandler.getData(); 		
		
		userInformationMenuItem = new UserInformationMenuItem(this);
        add(userInformationMenuItem);
        //add(new AddStationMenuItem(this));
		stationInformationMenu = new StationInformationMenu(this);
        add(stationInformationMenu);         
		//showOnMapMenu = new ShowOnMapMenu(this);
		//add(showOnMapMenu);
		shapeLayersMenu = new ShapeLayersMenu(this);
		add(shapeLayersMenu);
		issuesMenuItem = new IssuesMenuItem(this, data.getAISDatalinkCheckIssues());
		add(issuesMenuItem);
		listOfRulesMenuItem = new ListOfRulesMenuItem(this);
		add(listOfRulesMenuItem);
		optionsMenuItem = new OptionsMenuItem(this);
        add(optionsMenuItem);
        // add(new JSeparator());
		addMenuListener(this);
		
		// if user is not defined, open the edit user information dialog                                        
        if (data != null) {
            EAVDAMUser user = data.getUser();
			if (user == null || user.getOrganizationName() == null || user.getOrganizationName().isEmpty()) {
				userInformationMenuItem.doClick();
			}
		}
    }
		
	/*
	public void rebuildShowOnMapMenu() {
		showOnMapMenu = new ShowOnMapMenu(this);
	}
	*/
	
	public void	menuCanceled(MenuEvent e) {}
	
	public void	menuDeselected(MenuEvent e) {}
	
    public void	menuSelected(MenuEvent e) {
		
		EAVDAMData data = DBHandler.getData(); 	
		
		removeAll();
		
		if (userInformationMenuItem == null) {
			userInformationMenuItem = new UserInformationMenuItem(this);
		}
		add(userInformationMenuItem);
		if (stationInformationMenu == null) {
			stationInformationMenu = new StationInformationMenu(this);
		}
		add(stationInformationMenu);
		/*
		if (showOnMapMenu == null) {
			showOnMapMenu = new ShowOnMapMenu(this);
		}
		add(showOnMapMenu);
		*/
		if (shapeLayersMenu == null) {
			shapeLayersMenu = new ShapeLayersMenu(this);
		}
		add(shapeLayersMenu);	
		if (issuesMenuItem == null) {
			issuesMenuItem = new IssuesMenuItem(this, data.getAISDatalinkCheckIssues());
		}
		add(issuesMenuItem);
		if (listOfRulesMenuItem == null) {
			listOfRulesMenuItem = new ListOfRulesMenuItem(this);
		}
		add(listOfRulesMenuItem);
		if (optionsMenuItem == null) {
			optionsMenuItem = new OptionsMenuItem(this);
		}	
		add(optionsMenuItem);			
	}
	
    public OpenMapFrame getOpenMapFrame() {
        return openMapFrame;
    }
    
    public StationLayer getStationLayer() {
        return stationLayer;
    }

    public LayerHandler getLayerHandler() {
        return layerHandler;
    }    
	
	/*
	public ShowOnMapMenu getShowOnMapMenu() {
		return showOnMapMenu;
	}
	
	public void setShowOnMapMenu(ShowOnMapMenu showOnMapMenu) {
		this.showOnMapMenu = showOnMapMenu;
	}
	*/

	public ShapeLayersMenu getShapeLayersMenu() {
		return shapeLayersMenu;
	}
	
	public StationInformationMenu getStationInformationMenu() {
		return stationInformationMenu;
	}
	
	public UserInformationMenuItem getUserInformationMenuItem() {
		return userInformationMenuItem;
	}	
	
	@Override
	public void findAndInit(Object obj) {
		if (obj instanceof OpenMapFrame) {
			this.openMapFrame = (OpenMapFrame) obj;		
			IconChanger.changeToEAVDAMIcon((OpenMapFrame) obj);
		} else if (obj instanceof StationLayer) {
		    this.stationLayer = (StationLayer) obj;
		} else if (obj instanceof LayerHandler) {
		    this.layerHandler = (LayerHandler) obj;
		}
	}

}