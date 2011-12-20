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
package dk.frv.eavdam.buttons;

import com.bbn.openmap.Layer;
import com.bbn.openmap.LayerHandler;
import com.bbn.openmap.MapBean;
import com.bbn.openmap.gui.LayersMenu;
import com.bbn.openmap.gui.OMToolComponent;
import com.bbn.openmap.gui.OpenMapFrame;
import com.bbn.openmap.gui.Tool;
import com.bbn.openmap.proj.Projection;
import dk.frv.eavdam.app.SidePanel;
import dk.frv.eavdam.data.AISDatalinkCheckArea;
import dk.frv.eavdam.data.AISDatalinkCheckIssue;
import dk.frv.eavdam.data.AISDatalinkCheckResult;
import dk.frv.eavdam.data.AISDatalinkCheckRule;
import dk.frv.eavdam.data.EAVDAMData;
import dk.frv.eavdam.data.Options;
import dk.frv.eavdam.io.AISDatalinkCheckListener;
import dk.frv.eavdam.io.EmailSender;
import dk.frv.eavdam.layers.AISDatalinkCheckBandwidthAreasLayer;
import dk.frv.eavdam.layers.AISDatalinkCheckIssueLayer;
import dk.frv.eavdam.menus.EavdamMenu;
import dk.frv.eavdam.menus.IssuesMenuItem;
import dk.frv.eavdam.menus.OptionsMenuItem;
import dk.frv.eavdam.utils.DBHandler;
import dk.frv.eavdam.utils.HealthCheckHandler;
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
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.GregorianCalendar;
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
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JToolBar;
import net.sf.image4j.codec.ico.ICODecoder;

/**
 * Class for the button in the toolbar that starts the AIS VHF Datalink Health Check.
 */
public class InitiateHealthCheckButton extends OMToolComponent implements ActionListener, Tool, AISDatalinkCheckListener {

	private static final long serialVersionUID = 6706092036947101L;
	
	private JButton initiateHealthCheckButton = null;
	private JToolBar jToolBar;
    private OpenMapFrame openMapFrame;
	private MapBean mapBean;
	private LayerHandler layerHandler;
	private LayersMenu layersMenu;
	private EavdamMenu eavdamMenu;
	private SidePanel sidePanel;
	
	private JDialog dialog;
	private JDialog waitDialog;
	private JDialog completedDialog;
	
	private JCheckBox rule1CheckBox;
	private JCheckBox rule2CheckBox;
	private JCheckBox rule3CheckBox;
	private JCheckBox rule4CheckBox;
	private JCheckBox rule5CheckBox;
	private JCheckBox rule6CheckBox;
	private JCheckBox rule7CheckBox;
	private LinkLabel rulesHelpLabel;
	private JCheckBox includePlannedCheckBox;
	private JSlider resolutionSlider;
	
	private JButton goButton;
	private JButton cancelButton;
	private JButton viewIssuesButton;
	private JButton cancelViewIssuesButton;
		
	private EAVDAMData data;
	
	private Double topLeftLatitude = null;
	private Double topLeftLongitude = null;
	private Double lowerRightLatitude = null;
	private Double lowerRightLongitude = null;
	
	/**
	 * Creates the button.
	 */
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
	             
			dialog = new JDialog(openMapFrame, "AIS VHF Datalink Health Check", false);
		
			JPanel panel = new JPanel();
			panel.setLayout(new GridBagLayout());                  
		
			GridBagConstraints c = new GridBagConstraints();
			c.gridx = 0;
			c.gridy = 0;                   
			c.anchor = GridBagConstraints.LINE_START;
			c.insets = new Insets(5,5,5,5);
			
			JPanel rulesPanel = new JPanel(new GridBagLayout());
			rulesPanel.setPreferredSize(new Dimension(520, 330));
			rulesPanel.setMaximumSize(new Dimension(520, 330));
			rulesPanel.setMinimumSize(new Dimension(520, 330));					
			rulesPanel.setBorder(BorderFactory.createTitledBorder("Which rules and stations to process"));

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
			
			c.gridy = 8;
			includePlannedCheckBox = new JCheckBox("Process also planned stations");
			rulesPanel.add(includePlannedCheckBox, c);				
			
			c.gridy = 0;
			panel.add(rulesPanel, c);		
			
			JPanel resolutionPanel = new JPanel(new GridBagLayout());
			resolutionPanel.setBorder(BorderFactory.createTitledBorder("Resolution for area based check in nautical miles"));
			resolutionPanel.setPreferredSize(new Dimension(520, 100));
			resolutionPanel.setMaximumSize(new Dimension(520, 100));
			resolutionPanel.setMinimumSize(new Dimension(520, 100));					
	
			resolutionSlider = new JSlider(JSlider.HORIZONTAL, 10, 50, 20);
			resolutionSlider.setPreferredSize(new Dimension(480, 50));
			resolutionSlider.setMaximumSize(new Dimension(480, 50));
			resolutionSlider.setMinimumSize(new Dimension(480, 50));				
			Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
			labelTable.put(new Integer(10), new JLabel("1"));
			labelTable.put(new Integer(20), new JLabel("2"));
			labelTable.put(new Integer(30), new JLabel("3"));
			labelTable.put(new Integer(40), new JLabel("4"));
			labelTable.put(new Integer(50), new JLabel("5"));
			resolutionSlider.setLabelTable(labelTable);
			resolutionSlider.setMajorTickSpacing(10);
			resolutionSlider.setMinorTickSpacing(1);
			resolutionSlider.setPaintTicks(true);
			resolutionSlider.setPaintLabels(true);
			resolutionPanel.add(resolutionSlider, c);
			
			c.gridx = 0;
			c.gridy = 1;
			panel.add(resolutionPanel, c);
			
			JPanel buttonPanel = new JPanel(new GridBagLayout());        
			buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
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
			dialog.setBounds((int) screenSize.getWidth()/2 - 560/2, (int) screenSize.getHeight()/2 - 600/2, 600, 560);
			dialog.setVisible(true);
	
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
		
			Projection projection = mapBean.getProjection();
			Point2D topLeft = projection.getUpperLeft();
			Point2D lowerRight = projection.getLowerRight();
			
			topLeftLatitude = new Double(topLeft.getY());
			topLeftLongitude = new Double(topLeft.getX());
			lowerRightLatitude = new Double(lowerRight.getY());
			lowerRightLongitude = new Double(lowerRight.getX());
			
			data = DBHandler.getData();
			
			boolean checkRule1 = false;
			if (rule1CheckBox.isSelected()) {
				checkRule1 = true;
			}
			boolean checkRule2 = false;
			if (rule2CheckBox.isSelected()) {
				checkRule2 = true;
			}
			boolean checkRule3 = false;
			if (rule3CheckBox.isSelected()) {
				checkRule3 = true;
			}
			boolean checkRule4 = false;
			if (rule4CheckBox.isSelected()) {
				checkRule4 = true;
			}
			boolean checkRule5 = false;
			if (rule5CheckBox.isSelected()) {
				checkRule5 = true;
			}
			boolean checkRule6 = false;
			if (rule6CheckBox.isSelected()) {
				checkRule6 = true;
			}
			boolean checkRule7 = false;
			if (rule7CheckBox.isSelected()) {
				checkRule7 = true;
			}
			boolean includePlanned = false;
			if (includePlannedCheckBox.isSelected()) {
				includePlanned = true;
			}
			
			double resolution = (double) resolutionSlider.getValue() / 10;
			
			HealthCheckHandler hch = new HealthCheckHandler(data);
			double minResolution = HealthCheckHandler.getMinResolution(topLeftLatitude.doubleValue(),
				topLeftLongitude.doubleValue(), lowerRightLatitude.doubleValue(), lowerRightLongitude.doubleValue());
			if (resolution < minResolution) {
				JOptionPane.showMessageDialog(openMapFrame, "The selected resolution is too small for the area in view. Please, choose a bigger resolution or a smaller area.", "Error!", JOptionPane.ERROR_MESSAGE); 			
			} else {			
				new InitiateHealthCheckThread(data, this, openMapFrame, sidePanel, hch, checkRule1, checkRule2, checkRule3, checkRule4,
				checkRule5, checkRule6, checkRule7, includePlanned, topLeftLatitude.doubleValue(), topLeftLongitude.doubleValue(),
				lowerRightLatitude.doubleValue(), lowerRightLongitude.doubleValue(), resolution).start();
				sidePanel.setHealthCheckHandler(hch);
				sidePanel.getProgressIndicatorPane().setVisible(true);				
				dialog.dispose();
			}
			
		} else if (e.getSource() == cancelButton) {		
			dialog.dispose();	
	
		} else if (e.getSource() == viewIssuesButton) {
			completedDialog.dispose();
			new IssuesMenuItem(eavdamMenu).doClick();
			
		} else if (e.getSource() == cancelViewIssuesButton) {
			completedDialog.dispose();
		}
	}

	/**
	 * AISDatalinkCheckListener method. Updates the progress bar in the side panel.
	 *
	 * @param progress  How much of the health check process is done. Value between 0-1, 0 meaning that
	 *                  the process is just started and 1 meaning that the process is completed.
	 */
	public void progressed(double progress) {
		if (sidePanel.getProgressBar() != null) {
			sidePanel.getProgressBar().setValue((int) Math.round(100*progress));
		}
	}

	/**
	 * AISDatalinkCheckListener method. Displays the health check results in the issues menu and layer and
	 * bandwidth areas layer.
	 *
	 * @param result  Health check results.
	 */
	public void completed(AISDatalinkCheckResult result) {	

		if (result == null) {
			return;
		}
	
		if (data == null) {
			data = DBHandler.getData();		
		}
		
		data.setAISDatalinkCheckIssues(null);
		IssuesMenuItem.issues = null;
		
		if (result != null) {
			data.setAISDatalinkCheckIssues(result.getIssues());
			IssuesMenuItem.issues = result.getIssues();
		}

		DBHandler.saveData(data);
	
		IssuesMenuItem.healthCheckMayBeOutdated = new Boolean(false);
		IssuesMenuItem.ignoreHealthCheckMayBeOutdated = new Boolean(false);
		AISDatalinkCheckIssueLayer.ignoreHealthCheckMayBeOutdated = new Boolean(false);
		AISDatalinkCheckBandwidthAreasLayer.ignoreHealthCheckMayBeOutdated = new Boolean(false);
		IssuesMenuItem.lastHealthCheckDoneAt = new GregorianCalendar();
		List<AISDatalinkCheckRule> rules = new ArrayList<AISDatalinkCheckRule>();
		if (rule1CheckBox.isSelected()) {
			rules.add(AISDatalinkCheckRule.RULE1);
		}
		if (rule2CheckBox.isSelected()) {
			rules.add(AISDatalinkCheckRule.RULE2);
		}
		if (rule3CheckBox.isSelected()) {
			rules.add(AISDatalinkCheckRule.RULE3);
		}
		if (rule4CheckBox.isSelected()) {
			rules.add(AISDatalinkCheckRule.RULE4);
		}
		if (rule5CheckBox.isSelected()) {
			rules.add(AISDatalinkCheckRule.RULE5);
		}
		if (rule6CheckBox.isSelected()) {
			rules.add(AISDatalinkCheckRule.RULE6);
		}
		if (rule7CheckBox.isSelected()) {
			rules.add(AISDatalinkCheckRule.RULE7);
		}		
		IssuesMenuItem.lastHealthCheckRules = rules;
		IssuesMenuItem.lastHealthCheckTopLeftLatitude = topLeftLatitude;
		IssuesMenuItem.lastHealthCheckTopLeftLongitude = topLeftLongitude;
		IssuesMenuItem.lastHealthCheckLowerRightLatitude = lowerRightLatitude;
		IssuesMenuItem.lastHealthCheckLowerRightLongitude = lowerRightLongitude;	
		
		int numberOfOldIssues = 0;		
		int numberOfNewIssues = 0;
		if (IssuesMenuItem.issues != null) {
			for (AISDatalinkCheckIssue issue : IssuesMenuItem.issues) {
				if (issue.isAcknowledged()) {
					numberOfOldIssues++;
				} else {
					numberOfNewIssues++;
				}
			}
		}
						
		Layer[] layers = layerHandler.getLayers();
		Layer aisDatalinkCheckIssueLayer = null;
		for (Layer layer : layers) {
			if (layer.getClass().getCanonicalName().equals("dk.frv.eavdam.layers.AISDatalinkCheckIssueLayer")) {
				layerHandler.turnLayerOn(true, layer);				
				aisDatalinkCheckIssueLayer = layer;
			} else if (layer.getClass().getCanonicalName().equals("dk.frv.eavdam.layers.AISDatalinkCheckBandwidthAreasLayer")) {
				if (result != null && result.getAreas() != null && !result.getAreas().isEmpty()) {
					((AISDatalinkCheckBandwidthAreasLayer) layer).setAreas(result.getAreas());				
					layerHandler.turnLayerOn(true, layer);					
					((AISDatalinkCheckBandwidthAreasLayer) layer).doPrepare();
				}
			}
		}
		if (aisDatalinkCheckIssueLayer != null) {
			layerHandler.moveLayer(aisDatalinkCheckIssueLayer, 0);
			((AISDatalinkCheckIssueLayer) aisDatalinkCheckIssueLayer).doPrepare();
		}
		layersMenu.setLayers(layers);

		sidePanel.getProgressIndicatorPane().setVisible(false);
		
		completedDialog = new JDialog(openMapFrame, "AIS VHF datalink health check completed", true);
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.LINE_START;
		c.insets = new Insets(5,5,5,5);
		JLabel titleLabel = new JLabel("<html><body><p>The AIS VHF datalink health check is now completed. Found " + numberOfNewIssues +
			" new issues and " + numberOfOldIssues + " acknowledged issues. View issues now or later through the Eavdam menu. The AIS "+
			"VHF datalink issues and bandwidth areas layers are also activated.<p></body></html>");
		titleLabel.setPreferredSize(new Dimension(330, 90));
		titleLabel.setMaximumSize(new Dimension(330, 90));
		titleLabel.setMinimumSize(new Dimension(330, 90));
		panel.add(titleLabel, c);
		c.gridy = 1;
		JPanel buttonPanel = new JPanel();
		viewIssuesButton = new JButton("View issues now");
		viewIssuesButton.addActionListener(this);
		cancelViewIssuesButton = new JButton("Cancel");
		cancelViewIssuesButton.addActionListener(this);
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		buttonPanel.add(viewIssuesButton);
		buttonPanel.add(cancelViewIssuesButton);
		c.anchor = GridBagConstraints.CENTER;
		panel.add(buttonPanel, c);
		completedDialog.getContentPane().add(panel);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		completedDialog.setBounds((int) screenSize.getWidth()/2 - 380/2, (int) screenSize.getHeight()/2 - 200/2, 380, 200);
		completedDialog.setVisible(true);
	}
	
	public void findAndInit(Object obj) {
		if (obj instanceof OpenMapFrame) {
			this.openMapFrame = (OpenMapFrame) obj;
		} else if (obj instanceof MapBean) {
			this.mapBean = (MapBean) obj;
		} else if (obj instanceof EavdamMenu) {
			this.eavdamMenu = (EavdamMenu) obj;
        } else if (obj instanceof LayerHandler) {
			this.layerHandler = (LayerHandler) obj;		
		} else if (obj instanceof LayersMenu) {
			this.layersMenu = (LayersMenu) obj;
		} else if (obj instanceof SidePanel) {
			this.sidePanel = (SidePanel) obj;
		}
	}	    

	public void findAndUndo(Object obj) {}

}


/**
 * Class for executing the health check in a separate thread.
 */
class InitiateHealthCheckThread extends Thread {
	
	private EAVDAMData data;
	private AISDatalinkCheckListener listener;
	private OpenMapFrame openMapFrame;
	private SidePanel sidePanel;
	private HealthCheckHandler hch;
	private boolean checkRule1;
	private boolean checkRule2;
	private boolean checkRule3;
	private boolean checkRule4;
	private boolean checkRule5;
	private boolean checkRule6;
	private boolean checkRule7;
	private boolean includePlanned;
	private double topLeftLatitude;
	private double topLeftLongitude;
	private double lowerRightLatitude;
	private double lowerRightLongitude;
	private double resolution;
	
	InitiateHealthCheckThread(EAVDAMData data, AISDatalinkCheckListener listener, OpenMapFrame openMapFrame, SidePanel sidePanel,
			HealthCheckHandler hch, boolean checkRule1,	boolean checkRule2, boolean checkRule3, boolean checkRule4, boolean checkRule5,
			boolean checkRule6,	boolean checkRule7, boolean includePlanned,	double topLeftLatitude, double topLeftLongitude,
			double lowerRightLatitude, double lowerRightLongitude,double resolution) {
		this.data = data;
		this.listener = listener;
		this.openMapFrame = openMapFrame;
		this.sidePanel = sidePanel;
		this.hch = hch;
		this.checkRule1 = checkRule1;
		this.checkRule2 = checkRule2;
		this.checkRule3 = checkRule3;
		this.checkRule4 = checkRule4;
		this.checkRule5 = checkRule5;
		this.checkRule6 = checkRule6;
		this.checkRule7 = checkRule7;
		this.includePlanned = includePlanned;
		this.topLeftLatitude = topLeftLatitude;
		this.topLeftLongitude = topLeftLongitude;
		this.lowerRightLatitude = lowerRightLatitude;
		this.lowerRightLongitude = lowerRightLongitude;
		this.resolution = resolution;
	}
	
	/**
	 * Starts the health check process.
	 */
	public void run() {
		try {
			hch.startAISDatalinkCheck(listener, checkRule1, checkRule2, checkRule3, checkRule4, checkRule5, checkRule6,
				checkRule7, includePlanned, topLeftLatitude, topLeftLongitude, lowerRightLatitude, lowerRightLongitude, resolution);				
		} catch (OutOfMemoryError e) {
			sidePanel.getProgressIndicatorPane().setVisible(false);
			JOptionPane.showMessageDialog(openMapFrame, "The health check process ran out of memory. Please, try again with a bigger resolution / smaller area.", "Error!", JOptionPane.ERROR_MESSAGE); 
		}
	}

}