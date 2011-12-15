package dk.frv.eavdam.menus;

import com.bbn.openmap.Environment;
import com.bbn.openmap.InformationDelegator;
import com.bbn.openmap.LightMapHandlerChild;
import com.bbn.openmap.gui.OpenMapFrame;
import dk.frv.eavdam.utils.LinkLabel;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Properties;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * This class represents the about menu.
 */
public class AboutEAVDAMMenuItem extends JMenuItem implements ActionListener, LightMapHandlerChild {

    public static final long serialVersionUID = 1L;

	public static String version = "xx.xx";
	
    protected InformationDelegator informationDelegator = null;
	
	private OpenMapFrame openMapFrame;
	
	private LinkLabel commonsNetLabel;
	private LinkLabel derbyLabel;
	private LinkLabel image4jLabel;
	private LinkLabel javamailLabel;
	private LinkLabel openCSVLabel;
	private LinkLabel openmapLabel;
	
    public AboutEAVDAMMenuItem() {
        super("About EAVDAM");
        //setMnemonic('e');
        addActionListener(this);
        setEnabled(false); // enabled when InformationDelegator found.
    }

    public void setInformationDelegator(InformationDelegator in_informationDelegator) {
        informationDelegator = in_informationDelegator;
        setEnabled(informationDelegator != null);
    }

    protected InformationDelegator getInformationDelegator() {
        return informationDelegator;
    }

    public void actionPerformed(ActionEvent ae) {
        
		if (ae.getSource() instanceof AboutEAVDAMMenuItem) {
		
			JDialog dialog = new JDialog(openMapFrame, "About EAVDAM", true);

			JPanel panel = new JPanel();
			panel.setLayout(new GridBagLayout());                  
						
			GridBagConstraints c = new GridBagConstraints();
			c.gridx = 0;
			c.gridy = 0;         
			c.anchor = GridBagConstraints.FIRST_LINE_START;
			c.insets = new Insets(5,5,5,5);
			panel.add(new JLabel("<html><body><<h2>EAVDAM - EfficienSea AIS VHF Datalink Manager</h2></body></html>"), c);
			c.gridy = 1;
			c.insets = new Insets(5,5,5,5);
			panel.add(new JLabel("<html><body><em>Version: " + version + "</em></body></html>"), c);
			c.gridy = 2;                   
			panel.add(new JLabel("<html><body><p width=600>" +
				"The EAVDAM application is provided under the FreeBSD license:<br><br>" +
				"Copyright 2011 Danish Maritime Authority. All rights reserved.<br><br>" +
				"Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:<br><br>" +
				"1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.<br><br>" +
				"2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.<br><br>" +
				"THIS SOFTWARE IS PROVIDED BY THE DANISH MARITIME AUTHORITY ``AS IS'' AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE DANISH MARITIME AUTHORITY OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.<br><br>" +
				"The views and conclusions contained in the software and documentation are those of the authors and should not be interpreted as representing official policies, either expressed or implied, of the Danish Maritime Authority.<br></p></body></html>"), c);
			c.gridy = 3;
			panel.add(new JLabel("The application uses the following open source components:"), c);
			JPanel componentsPanel = new JPanel();
			componentsPanel.setLayout(new GridBagLayout());
			c.gridy = 0; 
			c.insets = new Insets(0,0,0,20);			
			commonsNetLabel = new LinkLabel("Apache Commons Net");
			commonsNetLabel.addActionListener(this);
			componentsPanel.add(commonsNetLabel, c);	    
			c.gridx = 1;
			derbyLabel = new LinkLabel("Apache Derby");
			derbyLabel.addActionListener(this);
			componentsPanel.add(derbyLabel, c);
			c.gridx = 2;			
			image4jLabel = new LinkLabel("Image4j");
			image4jLabel.addActionListener(this);
			componentsPanel.add(image4jLabel, c);			
			c.gridx = 3;	
			javamailLabel = new LinkLabel("JavaMail");
			javamailLabel.addActionListener(this);
			componentsPanel.add(javamailLabel, c);	
			c.gridx = 4;			
			openCSVLabel = new LinkLabel("opencsv");
			openCSVLabel.addActionListener(this);
			componentsPanel.add(openCSVLabel, c);				
			c.gridx = 5;
			openmapLabel = new LinkLabel("OpenMap");
			openmapLabel.addActionListener(this);
			componentsPanel.add(openmapLabel, c);				
			c.gridx = 0;
			c.gridy = 4;
			c.insets = new Insets(5,5,5,5);
			panel.add(componentsPanel, c);
			dialog.getContentPane().add(panel);
			int frameWidth = 680;
			int frameHeight = 640;
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			dialog.setBounds((int) screenSize.getWidth()/2 - frameWidth/2,
				(int) screenSize.getHeight()/2 - frameHeight/2, frameWidth, frameHeight);
			dialog.setVisible(true);				
	
		} else if (ae.getSource() == commonsNetLabel) {
			openURL("http://commons.apache.org/net/");
		} else if (ae.getSource() == derbyLabel) {
			openURL("http://db.apache.org/derby/");		
		} else if (ae.getSource() == image4jLabel) {
			openURL("http://image4j.sourceforge.net/");				
		} else if (ae.getSource() == javamailLabel) {
			openURL("http://www.oracle.com/technetwork/java/javamail/index.html");	
		} else if (ae.getSource() == openCSVLabel) {
			openURL("http://opencsv.sourceforge.net/");	
		} else if (ae.getSource() == openmapLabel) {
			openURL("http://openmap.bbn.com/");	
		}
    }

    public void findAndInit(Object someObj) {
        if (someObj instanceof InformationDelegator) {
            setInformationDelegator((InformationDelegator) someObj);
        } else if (someObj instanceof OpenMapFrame) {
			this.openMapFrame = (OpenMapFrame) someObj;
		}
    }

    public void findAndUndo(Object someObj) {
        if (someObj instanceof InformationDelegator
                && getInformationDelegator() == (InformationDelegator) someObj) {
            setInformationDelegator(null);
        }
    }
	
	public static void openURL(String url) {
		String osName = System.getProperty("os.name");
        try {
			if (osName.startsWith("Windows")) {
				Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
            } else {
				String[] browsers = { "firefox", "opera", "konqueror", "epiphany", "mozilla", "netscape" };
                String browser = null;
                for (int count = 0; count < browsers.length && browser == null; count++) {
					if (Runtime.getRuntime().exec(new String[] { "which", browsers[count] }).waitFor() == 0) {
						browser = browsers[count];
					}
				}
                Runtime.getRuntime().exec(new String[] { browser, url });
            }
        } catch (Exception e) {
            System.out.println("Error in opening browser:\n" + e.getLocalizedMessage());
        }
    }
	
}