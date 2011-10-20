package dk.frv.eavdam.buttons;

import com.bbn.openmap.gui.OMToolComponent;
import com.bbn.openmap.gui.OpenMapFrame;
import com.bbn.openmap.gui.Tool;
import dk.frv.eavdam.data.FTP;
import dk.frv.eavdam.data.Options;
import dk.frv.eavdam.io.FTPHandler;
import dk.frv.eavdam.io.derby.DerbyDBInterface;
import dk.frv.eavdam.menus.OptionsMenuItem;
import dk.frv.eavdam.utils.XMLHandler;
import java.awt.Color;
import java.awt.Container;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import javax.mail.MessagingException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;

public class SendFTPButton extends OMToolComponent implements ActionListener, Tool {

	private static final long serialVersionUID = 6706092075829947101L;
	protected JButton sendFTPButton = null;
	protected JToolBar jToolBar;
    private OpenMapFrame openMapFrame;
 
	public SendFTPButton() {
		super();
		
		ImageIcon icon = new ImageIcon("share/data/images/ftp.png");
		if (icon != null || icon.getImage() == null) {
			sendFTPButton = new JButton(icon);
		} else {
			sendFTPButton = new JButton("FTP");
		}

        sendFTPButton.setBorder(BorderFactory.createEmptyBorder());
		sendFTPButton.addActionListener(this);
		sendFTPButton.setToolTipText("Send the data file to ftp recipients");
		add(sendFTPButton);
		
	}
	
	 public void actionPerformed(ActionEvent e) {
	    
		 XMLHandler.exportData();
		 
	     int response = JOptionPane.showConfirmDialog(openMapFrame, "This will send the latest data file to all defined ftp directories. Are you sure you want to do this?", "Confirm action", JOptionPane.YES_NO_OPTION);
         if (response == JOptionPane.YES_OPTION) {
            Options options = OptionsMenuItem.loadOptions();
            List<FTP> ftps = options.getFTPs();
            if (ftps != null && !ftps.isEmpty()) {
                boolean errors = false;
                for (FTP ftp : ftps) {
                    try {

                        FTPHandler.sendDataToFTP(ftp, XMLHandler.getLatestDataFileName());                                       
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                        ex.printStackTrace();
                        errors = true;
                    }
                }
                if (!errors) {
                    JOptionPane.showMessageDialog(openMapFrame, "Data was sent succesfully."); 
                } else {                       
                    JOptionPane.showMessageDialog(openMapFrame, "Some errors occurred when sending the data", "Error", JOptionPane.ERROR_MESSAGE); 
                }
            } else {
                JOptionPane.showMessageDialog(openMapFrame, "No FTP sites defined.", "Error", JOptionPane.ERROR_MESSAGE);         
            } 
         } else if (response == JOptionPane.NO_OPTION) { 
            // ignore        
         }
     }

	 public void findAndInit(Object obj) {
		if (obj instanceof OpenMapFrame) {
			this.openMapFrame = (OpenMapFrame) obj;
		}
	}	    

	 public void findAndUndo(Object obj) {}

}