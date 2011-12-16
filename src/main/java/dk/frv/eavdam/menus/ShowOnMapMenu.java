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
