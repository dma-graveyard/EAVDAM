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

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;

/**
 * Class for presenting a menu item that opens a frame where the user can view
 * the list of AIS VHF Health Check rules.
 */
public class ListOfRulesMenuItem extends JMenuItem {

    public static final long serialVersionUID = 1L;

    public ListOfRulesMenuItem(EavdamMenu eavdamMenu) {        
        super("AIS VHF Datalink Rules");                
        addActionListener(new ListOfRulesActionListener(eavdamMenu));
    }
	
}
 
 
/**
 * Class for viewing the list of AIS VHF Health Check rules. Basically, this only shows the contents of the file share/data/rules.html.
 */
class ListOfRulesActionListener implements ActionListener {

    private EavdamMenu eavdamMenu;                                                   
    private JDialog dialog;
     
    public ListOfRulesActionListener(EavdamMenu eavdamMenu) {
        super();
        this.eavdamMenu = eavdamMenu;
    }
                   		   
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() instanceof ListOfRulesMenuItem) {

            dialog = new JDialog(eavdamMenu.getOpenMapFrame(), "AIS VHF Datalink Rules", false);

			JEditorPane editorPane = new JEditorPane();
			editorPane.setEditable(false);
			URL rulesURL = ListOfRulesMenuItem.class.getClassLoader().getResource("share/data/rules.html");
			if (rulesURL != null) {
				try {
					editorPane.setPage(rulesURL);
				} catch (IOException ex) {
					System.err.println(ex.getMessage());
					ex.printStackTrace();
				}
			} else {
				System.out.println("Did not find rules.html");
			}

			JScrollPane editorScrollPane = new JScrollPane(editorPane);
			editorScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			editorScrollPane.setPreferredSize(new Dimension(600, 440));
			editorScrollPane.setMinimumSize(new Dimension(100, 50));			
			
            dialog.getContentPane().add(editorScrollPane);
                                                                         
            int frameWidth = 640;
            int frameHeight = 480;
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            dialog.setBounds((int) screenSize.getWidth()/2 - frameWidth/2,
                (int) screenSize.getHeight()/2 - frameHeight/2, frameWidth, frameHeight);
            dialog.setVisible(true);
        }
    }

}