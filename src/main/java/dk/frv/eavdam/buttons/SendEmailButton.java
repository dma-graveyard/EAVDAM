package dk.frv.eavdam.buttons;

import com.bbn.openmap.gui.OMToolComponent;
import com.bbn.openmap.gui.OpenMapFrame;
import com.bbn.openmap.gui.Tool;
import dk.frv.eavdam.data.EAVDAMData;
import dk.frv.eavdam.data.Options;
import dk.frv.eavdam.io.EmailSender;
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
import javax.mail.MessagingException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;

public class SendEmailButton extends OMToolComponent implements ActionListener, Tool {

	private static final long serialVersionUID = 6706092075829947101L;
	protected JButton sendEmailButton = null;
	protected JToolBar jToolBar;
    private OpenMapFrame openMapFrame;
 
	public SendEmailButton() {
		super();
		
		ImageIcon icon = new ImageIcon("share/data/images/email.png");
		if (icon != null || icon.getImage() == null) {
			sendEmailButton = new JButton(icon);
		} else {
			sendEmailButton = new JButton("E-mail");
		}

        sendEmailButton.setBorder(BorderFactory.createEmptyBorder());
		sendEmailButton.addActionListener(this);
		sendEmailButton.setToolTipText("Send the data file to e-mail recipients");
		//sendEmailButton.setEnabled(true);
		add(sendEmailButton);
		
	}
	
	 public void actionPerformed(ActionEvent e) {
	    
		EAVDAMData exportData = XMLHandler.exportData();	

		if (exportData.getActiveStations() == null || exportData.getActiveStations().isEmpty()) {
            JOptionPane.showMessageDialog(openMapFrame, "You have no own stations", "Error", JOptionPane.ERROR_MESSAGE);
		} else {
			 int response = JOptionPane.showConfirmDialog(openMapFrame, "This will e-mail the latest data file to all defined recipients. Are you sure you want to do this?", "Confirm action", JOptionPane.YES_NO_OPTION);
			 if (response == JOptionPane.YES_OPTION) {
				Options options = OptionsMenuItem.loadOptions();
				try {
					if (options.getEmailTo() == null || options.getEmailTo().isEmpty()) {
						JOptionPane.showMessageDialog(openMapFrame, "No e-mail recipients defined!"); 
					} else {
						EmailSender.sendDataToEmail(options.getEmailTo(), options.getEmailFrom(), options.getEmailSubject(),
							options.getEmailHost(), options.isEmailAuth(), options.getEmailUsername(), options.getEmailPassword(),
							XMLHandler.getLatestDataFileName());
						JOptionPane.showMessageDialog(openMapFrame, "E-mails were sent succesfully."); 
					}
				} catch (IOException ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(openMapFrame, "The following error occurred when trying to send the e-mails: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);      
				} catch (MessagingException ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(openMapFrame, "The following error occurred when trying to send the e-mails: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); 
				}
			 } else if (response == JOptionPane.NO_OPTION) { 
				// ignore        
			 }
		}
     }

	 public void findAndInit(Object obj) {
		if (obj instanceof OpenMapFrame) {
			this.openMapFrame = (OpenMapFrame) obj;
		}
	}	    

	 public void findAndUndo(Object obj) {}

}