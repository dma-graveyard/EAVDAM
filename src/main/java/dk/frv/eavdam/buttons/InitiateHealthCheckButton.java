package dk.frv.eavdam.buttons;

import com.bbn.openmap.gui.OMToolComponent;
import com.bbn.openmap.gui.OpenMapFrame;
import com.bbn.openmap.gui.Tool;
import dk.frv.eavdam.data.Options;
import dk.frv.eavdam.io.EmailSender;
import dk.frv.eavdam.menus.OptionsMenuItem;
import dk.frv.eavdam.utils.LinkLabel;
import dk.frv.eavdam.utils.XMLHandler;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Hashtable;
import java.util.List;
import javax.mail.MessagingException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JToolBar;
import net.sf.image4j.codec.ico.ICODecoder;

public class InitiateHealthCheckButton extends OMToolComponent implements ActionListener, Tool {

	private static final long serialVersionUID = 6706092036947101L;
	protected JButton initiateHealthCheckButton = null;
	protected JToolBar jToolBar;
    private OpenMapFrame openMapFrame;
	
	private JDialog dialog;
	
	private JCheckBox rule1CheckBox;
	private JCheckBox rule2CheckBox;
	private JCheckBox rule3CheckBox;
	private JCheckBox rule4CheckBox;
	private JCheckBox rule5CheckBox;
	private JCheckBox rule6CheckBox;
	private JCheckBox rule7CheckBox;
	private LinkLabel rulesHelpLabel;
	
	private JSlider resolutionSlider;
	
	private JButton goButton;
	private JButton cancelButton;
	
	public InitiateHealthCheckButton() {
		super();
		boolean foundIcon = false;
		try {
			List<BufferedImage> images = ICODecoder.read(new File("share/data/images/EAVDAM.ico"));
			for (BufferedImage img : images) {
				if (img.getWidth() == 32) {
					initiateHealthCheckButton = new JButton(new ImageIcon(img));
					foundIcon = true;
				}
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}		
		if (!foundIcon) {
			initiateHealthCheckButton = new JButton("Health check");
		}

        initiateHealthCheckButton.setBorder(BorderFactory.createEmptyBorder());
		initiateHealthCheckButton.addActionListener(this);
		initiateHealthCheckButton.setToolTipText("Initiate AIS VHF datalink health check");
		add(initiateHealthCheckButton);
	}
	
	 public void actionPerformed(ActionEvent e) {
	 
		if (e.getSource() == initiateHealthCheckButton) {
	    
			int response = JOptionPane.showConfirmDialog(openMapFrame, "This will initiate the AIS VHF datalink health check process,\nwhich may take some time. Are you sure you want to do this?", "Confirm action", JOptionPane.YES_NO_OPTION);
         
			if (response == JOptionPane.YES_OPTION) {
         
				dialog = new JDialog(openMapFrame, "AIS VHF Datalink Health Check", false);  // true for modal dialog
			
				JPanel panel = new JPanel();
				panel.setLayout(new GridBagLayout());                  
			
				GridBagConstraints c = new GridBagConstraints();
				c.gridx = 0;
				c.gridy = 0;                   
				c.anchor = GridBagConstraints.LINE_START;
				c.insets = new Insets(5,5,5,5);
				
				JPanel rulesPanel = new JPanel(new GridBagLayout());
				rulesPanel.setPreferredSize(new Dimension(520, 300));
				rulesPanel.setMaximumSize(new Dimension(520, 300));
				rulesPanel.setMinimumSize(new Dimension(520, 300));					
				rulesPanel.setBorder(BorderFactory.createTitledBorder("Which rules to process"));

				rule1CheckBox = new JCheckBox("Rule 1: Conflicting stations");
				rule1CheckBox.setSelected(true);
				rulesPanel.add(rule1CheckBox, c);
				
				c.gridy = 1;
				rule2CheckBox = new JCheckBox("Rule 2: Reservation, but no intended use");
				rule2CheckBox.setSelected(true);
				rulesPanel.add(rule2CheckBox, c);				

				c.gridy = 2;
				rule3CheckBox = new JCheckBox("Rule 3: Intended FATDMA use, but no reservation");
				rule3CheckBox.setSelected(true);
				rulesPanel.add(rule3CheckBox, c);		

				c.gridy = 3;
				rule4CheckBox = new JCheckBox("Rule 4: Simultaneous use of several frequencies");
				rule4CheckBox.setSelected(true);
				rulesPanel.add(rule4CheckBox, c);		

				c.gridy = 4;
				rule5CheckBox = new JCheckBox("Rule 5: Slots reserved outside IALA A-124 recommended default FATDMA schemes");
				rule5CheckBox.setSelected(true);
				rulesPanel.add(rule5CheckBox, c);		

				c.gridy = 5;
				rule6CheckBox = new JCheckBox("Rule 6: Slots reserved outside overall slot pattern for fixed statons (IALA A-124)");
				rule6CheckBox.setSelected(true);
				rulesPanel.add(rule6CheckBox, c);					

				c.gridy = 6;
				rule7CheckBox = new JCheckBox("Rule 7: Free Bandwith below 50%");
				rule7CheckBox.setSelected(true);
				rulesPanel.add(rule7CheckBox, c);
				
				c.gridy = 7;
				rulesHelpLabel = new LinkLabel("View detailed descriptions of rules");
				rulesHelpLabel.addActionListener(this);
				rulesPanel.add(rulesHelpLabel, c);
				
				c.gridy = 0;
				panel.add(rulesPanel, c);
				
				JPanel resolutionPanel = new JPanel(new GridBagLayout());
				resolutionPanel.setBorder(BorderFactory.createTitledBorder("Resolution for area based check in nautical miles"));
				resolutionPanel.setPreferredSize(new Dimension(520, 100));
				resolutionPanel.setMaximumSize(new Dimension(520, 100));
				resolutionPanel.setMinimumSize(new Dimension(520, 100));					

				c.gridx = 0;
				c.gridy = 0;			
				resolutionSlider = new JSlider(JSlider.HORIZONTAL, 1, 50, 10);
				resolutionSlider.setPreferredSize(new Dimension(480, 50));
				resolutionSlider.setMaximumSize(new Dimension(480, 50));
				resolutionSlider.setMinimumSize(new Dimension(480, 50));				
                Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
				labelTable.put(new Integer(1), new JLabel("0.1"));
				labelTable.put(new Integer(10), new JLabel("1"));
				labelTable.put(new Integer(20), new JLabel("2"));
				labelTable.put(new Integer(30), new JLabel("3"));
				labelTable.put(new Integer(40), new JLabel("4"));
				labelTable.put(new Integer(50), new JLabel("5"));				
				resolutionSlider.setLabelTable( labelTable );
				resolutionSlider.setMajorTickSpacing(10);
				resolutionSlider.setMinorTickSpacing(1);
				resolutionSlider.setPaintTicks(true);
				resolutionSlider.setPaintLabels(true);
				resolutionPanel.add(resolutionSlider, c);
				c.gridx = 1;
				
				c.gridx = 0;
				c.gridy = 1;
				panel.add(resolutionPanel, c);
				
				JPanel buttonPanel = new JPanel(new GridBagLayout());        
				buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
				c.gridx = 0;
				c.gridy = 0;
				c.gridwidth = 1;
				goButton = new JButton("Go");
				goButton.addActionListener(this);
				buttonPanel.add(goButton, c);
				c.gridx = 1;
				cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(this);
				buttonPanel.add(cancelButton, c);			

				c.gridx = 0;
				c.gridy = 2;
				c.anchor = GridBagConstraints.CENTER;
				panel.add(buttonPanel, c);			
				
				dialog.getContentPane().add(panel);
			
				Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				dialog.setBounds((int) screenSize.getWidth()/2 - 640/2, (int) screenSize.getHeight()/2 - 550/2, 640, 550);
				dialog.setVisible(true);

			 } else if (response == JOptionPane.NO_OPTION) { 
				// ignore        
			 }
		
		} else if (e.getSource() == rulesHelpLabel) {
		
			JDialog rulesDialog = new JDialog(openMapFrame, "AIS VHF Datalink Rules", false);

			JEditorPane editorPane = new JEditorPane();
			editorPane.setEditable(false);
			URL rulesURL = InitiateHealthCheckButton.class.getClassLoader().getResource("share/data/rules.html");
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
			
            rulesDialog.getContentPane().add(editorScrollPane);
                                                                         
            int frameWidth = 640;
            int frameHeight = 480;
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            rulesDialog.setBounds((int) screenSize.getWidth()/2 - frameWidth/2,
                (int) screenSize.getHeight()/2 - frameHeight/2, frameWidth, frameHeight);
            rulesDialog.setVisible(true);			
		
		} else if (e.getSource() == goButton) {
		
		} else if (e.getSource() == cancelButton) {		
			dialog.dispose();	
		}
    
	}

	public void findAndInit(Object obj) {
		if (obj instanceof OpenMapFrame) {
			this.openMapFrame = (OpenMapFrame) obj;
		}
	}	    

	public void findAndUndo(Object obj) {}

}