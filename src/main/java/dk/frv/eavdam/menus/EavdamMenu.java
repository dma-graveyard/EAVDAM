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
import javax.swing.JOptionPane;
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
	private ShowOnMapMenu showOnMapMenu;
	private ShapeLayersMenu shapeLayersMenu;
	private IssuesMenuItem issuesMenuItem;
	private ListOfRulesMenuItem listOfRulesMenuItem;
	private OptionsMenuItem optionsMenuItem;
	
    public EavdamMenu() {
        super();
        setText(defaultText);
        setMnemonic(defaultMnemonic);		
		
		userInformationMenuItem = new UserInformationMenuItem(this, false);
        add(userInformationMenuItem);
		stationInformationMenu = new StationInformationMenu(this);
        add(stationInformationMenu);         
		showOnMapMenu = new ShowOnMapMenu(this);
		add(showOnMapMenu);
		shapeLayersMenu = new ShapeLayersMenu(this);
		add(shapeLayersMenu);
		issuesMenuItem = new IssuesMenuItem(this);
		add(issuesMenuItem);
		listOfRulesMenuItem = new ListOfRulesMenuItem(this);
		add(listOfRulesMenuItem);
		optionsMenuItem = new OptionsMenuItem(this);
        add(optionsMenuItem);
        // add(new JSeparator());
		addMenuListener(this);
    }
		
	/*
	public void rebuildShowOnMapMenu() {
		showOnMapMenu = new ShowOnMapMenu(this);
	}
	*/
	
	public void	menuCanceled(MenuEvent e) {}
	
	public void	menuDeselected(MenuEvent e) {}
	
    public void	menuSelected(MenuEvent e) {
		
		removeAll();
		
		userInformationMenuItem = new UserInformationMenuItem(this, false);

		add(userInformationMenuItem);
		if (stationInformationMenu == null) {
			stationInformationMenu = new StationInformationMenu(this);
		}
		add(stationInformationMenu);
		if (showOnMapMenu == null) {
			showOnMapMenu = new ShowOnMapMenu(this);
		}
		add(showOnMapMenu);		
		if (shapeLayersMenu == null) {
			shapeLayersMenu = new ShapeLayersMenu(this);
		}
		add(shapeLayersMenu);	
		if (issuesMenuItem == null) {
			issuesMenuItem = new IssuesMenuItem(this);
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
	
	public ShowOnMapMenu getShowOnMapMenu() {
		return showOnMapMenu;
	}
	
	public void setShowOnMapMenu(ShowOnMapMenu showOnMapMenu) {
		this.showOnMapMenu = showOnMapMenu;
	}

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