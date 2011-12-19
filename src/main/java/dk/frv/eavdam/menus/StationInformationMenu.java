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

import javax.swing.JMenuItem;

/**
 * Class for presenting a menu item that opens a frame where the user can edit
 * station information.
 */
public class StationInformationMenu extends JMenuItem {

    public static final long serialVersionUID = 3L;

	private StationInformationMenuItem stationInformationMenuItem;

	/**
	 * Constructor for the menu.
	 *
	 * @param eavdamMenu  EAVDAM menu where this menu item resides
	 */
    public StationInformationMenu(EavdamMenu eavdamMenu) {
        super("Edit Station Information");
		this.stationInformationMenuItem = new StationInformationMenuItem(eavdamMenu, null, null);
        addActionListener(stationInformationMenuItem);
    }

	/**
	 * Constructor for the menu when the user right clicks a station on the map and selects to edit the station.
	 *
	 * @param eavdamMenu   EAVDAM menu where this menu item resides
	 * @param dataset      NULL value for user's own stations, simulation name (String) for simulations or EAVDAMUser object for other users' stations 
	 * @param stationName  Name of the station that is to be initially selected in the edit station menu
	 */
    public StationInformationMenu(EavdamMenu eavdamMenu, String dataset, String stationName) {
        super("Edit Station Information");     
		this.stationInformationMenuItem = new StationInformationMenuItem(eavdamMenu, dataset, stationName);
        addActionListener(stationInformationMenuItem);
    }
	
	/**
	 * Returns the actual menu item that does the editing of station information.
	 *
	 * @return  The actual menu item that does the editing of station information
	 */
	public StationInformationMenuItem getStationInformationMenuItem() {
		return stationInformationMenuItem;
	}
}
        