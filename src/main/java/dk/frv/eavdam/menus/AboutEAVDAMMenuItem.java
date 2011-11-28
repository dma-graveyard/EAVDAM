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

    protected InformationDelegator informationDelegator = null;
	
	private OpenMapFrame openMapFrame;
	
	private LinkLabel commonsNetLabel;
	private LinkLabel derbyLabel;
	private LinkLabel image4jLabel;
	private LinkLabel javamailLabel;
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
		
			String version = "x.x";
		
			try {				
				FileInputStream fstream = new FileInputStream("build.xml");
				DataInputStream in = new DataInputStream(fstream);
				BufferedReader br = new BufferedReader(new InputStreamReader(in));
				String strLine;
				while ((strLine = br.readLine()) != null) {		
					if (strLine.indexOf("<property name=\"version\" value=\"") != -1) {
						int start = strLine.indexOf("<property name=\"version\" value=\"") + "<property name=\"version\" value=\"".length();
						int end = strLine.indexOf("\"", start);
						version = strLine.substring(start, end);
						break;
					}
				}
			} catch (FileNotFoundException ex) {
				System.out.println(ex.getMessage());
				ex.printStackTrace();
			} catch (IOException ex) {
				System.out.println(ex.getMessage());
				ex.printStackTrace();
			}
			
			JDialog dialog = new JDialog(openMapFrame, "About EAVDAM", true);

			JPanel panel = new JPanel();
			panel.setLayout(new GridBagLayout());                  
						
			GridBagConstraints c = new GridBagConstraints();
			c.gridx = 0;
			c.gridy = 0;                   
			c.anchor = GridBagConstraints.FIRST_LINE_START;
			c.insets = new Insets(5,5,5,5);
			panel.add(new JLabel("EAVDAM - EfficienSea AIS VHF Datalink Manager"), c);
			c.gridy = 1;
			panel.add(new JLabel("Version: " + version), c);
			c.gridy = 2;                   
			panel.add(new JLabel("This application uses the following open source components:"), c);
			c.gridy = 3;			
			commonsNetLabel = new LinkLabel("Apache Commons Net");
			commonsNetLabel.addActionListener(this);
			panel.add(commonsNetLabel, c);	    
			c.gridy = 4;
			derbyLabel = new LinkLabel("Apache Derby");
			derbyLabel.addActionListener(this);
			panel.add(derbyLabel, c);
			c.gridy = 5;
			image4jLabel = new LinkLabel("Image4j");
			image4jLabel.addActionListener(this);
			panel.add(image4jLabel, c);			
			c.gridy = 6;
			javamailLabel = new LinkLabel("JavaMail");
			javamailLabel.addActionListener(this);
			panel.add(javamailLabel, c);	
			c.gridy = 7;
			openmapLabel = new LinkLabel("OpenMap");
			openmapLabel.addActionListener(this);
			panel.add(openmapLabel, c);				
			dialog.getContentPane().add(panel);
			int frameWidth = 400;
			int frameHeight = 290;
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