package dk.frv.eavdam.menus;

import dk.frv.eavdam.data.ActiveStation;
import dk.frv.eavdam.data.AISFixedStationData;
import dk.frv.eavdam.data.AISFixedStationStatus;
import dk.frv.eavdam.data.AISFixedStationType;
import dk.frv.eavdam.data.Antenna;
import dk.frv.eavdam.data.AntennaType;
import dk.frv.eavdam.data.EAVDAMData;
import dk.frv.eavdam.data.Simulation;
import dk.frv.eavdam.io.derby.DerbyDBInterface;
import dk.frv.eavdam.utils.DBHandler;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class AddStationDialog extends JDialog implements ActionListener, ItemListener {

    public static final long serialVersionUID = 1L;

	private StationInformationMenuItem menuItem;
	
    private JTextField addStationNameTextField;
    private JComboBox addStationTypeComboBox;
	private JComboBox addStationStatusComboBox;
    private JTextField addLatitudeTextField;
    private JTextField addLongitudeTextField;    
    private JTextField addMMSINumberTextField;
    private JTextField addTransmissionPowerTextField;

    private JComboBox addAntennaTypeComboBox;
    private JTextField addAntennaHeightTextField;
    private JTextField addTerrainHeightTextField;    
    private JTextField addHeadingTextField;
    private JTextField addFieldOfViewAngleTextField;
    private JTextField addGainTextField;   
	
	private JPanel addStationFATDMAPanel;
	private JComboBox addStationChannelAComboBox;
	private JScrollPane addStationChannelAScrollPane;
	private JPanel channelAPanel;
	private JComboBox addStationChannelBComboBox;
	private JScrollPane addStationChannelBScrollPane;
	private JPanel channelBPanel;

    private JTextArea addAdditionalInformationJTextArea;
    
    private JButton doAddStationButton;
    private JButton cancelAddStationButton;	
	
	private int oldAddStationTypeIndex = -1;
	private int oldAddStationChannelAIndex = -1;
	private int oldAddStationChannelBIndex = -1;

	public AddStationDialog(StationInformationMenuItem menuItem) {

		super(menuItem.getEavdamMenu().getOpenMapFrame(), "Add Station", false);  // true for modal dialog
		
		this.menuItem = menuItem;

		addStationNameTextField = menuItem.getTextField(16);
		addStationTypeComboBox = menuItem.getComboBox(new String[] {"AIS Base Station", "AIS Repeater", "AIS Receiver station", "AIS AtoN station"});
		addStationTypeComboBox.addItemListener(this);
		addStationStatusComboBox = menuItem.getComboBox(new String[] {"Operative", "Planned"});
		addStationStatusComboBox.addItemListener(this);

		addLatitudeTextField = menuItem.getTextField(16);
		addLongitudeTextField = menuItem.getTextField(16);
		addMMSINumberTextField = menuItem.getTextField(16);
		addTransmissionPowerTextField = menuItem.getTextField(16);
		addStationStatusComboBox = menuItem.getComboBox(new String[] {StationInformationMenuItem.OPERATIVE_LABEL, StationInformationMenuItem.PLANNED_LABEL});
		addStationStatusComboBox.addItemListener(this);

		addAntennaTypeComboBox = menuItem.getComboBox(new String[] {"No antenna", "Omnidirectional", "Directional"});
		addAntennaTypeComboBox.addItemListener(this);
		addAntennaTypeComboBox.setSelectedIndex(0);      
		addAntennaHeightTextField = menuItem.getTextField(16);            
		addTerrainHeightTextField = menuItem.getTextField(16);
		addHeadingTextField = menuItem.getTextField(16);
		addFieldOfViewAngleTextField = menuItem.getTextField(16);           
		addGainTextField = menuItem.getTextField(16);
		
		addStationChannelAComboBox = menuItem.getComboBox(new String[] {"NULL", "AIS1", "AIS2"});
		addStationChannelAComboBox.addItemListener(this);
		addStationChannelBComboBox = menuItem.getComboBox(new String[] {"NULL", "AIS1", "AIS2"});
		addStationChannelBComboBox.addItemListener(this);
		
		addAdditionalInformationJTextArea = menuItem.getTextArea("");
		
		doAddStationButton = menuItem.getButton("Add station", 140);  
		doAddStationButton.addActionListener(this);
		cancelAddStationButton = menuItem.getButton("Cancel", 100);  
		cancelAddStationButton.addActionListener(this);          
		
		updateAntennaTypeComboBox();
			
		if (((String) menuItem.getSelectDatasetComboBox().getSelectedItem()).startsWith(StationInformationMenuItem.OWN_ACTIVE_STATIONS_LABEL)) {   
			addStationStatusComboBox = menuItem.getComboBox(new String[] {"Operative", "Planned"});	
		} else if (((String) menuItem.getSelectDatasetComboBox().getSelectedItem()).startsWith(StationInformationMenuItem.SIMULATION_LABEL)) {				
			addStationStatusComboBox = menuItem.getComboBox(new String[] {"Simulation"});	
		}						
					
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
						  
		JPanel p2 = new JPanel(new GridBagLayout());
		p2.setBorder(BorderFactory.createTitledBorder("General information"));
		GridBagConstraints c = new GridBagConstraints();
		c = menuItem.updateGBC(c, 0, 0, 0.5, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(5,5,5,5));        
		p2.add(new JLabel("Station name:"), c);
		c = menuItem.updateGBC(c, 1, 0, 0.5, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(5,5,5,5));               
		p2.add(addStationNameTextField, c);                    
		c = menuItem.updateGBC(c, 0, 1, 0.5, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(5,5,5,5));                    
		p2.add(new JLabel("Station type:"), c);
		c = menuItem.updateGBC(c, 1, 1, 0.5, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(5,5,5,5));    
		p2.add(addStationTypeComboBox, c);     
		c = menuItem.updateGBC(c, 0, 2, 0.5, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(5,5,5,5));                    
		p2.add(new JLabel("Station status:"), c);
		c = menuItem.updateGBC(c, 1, 2, 0.5, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(5,5,5,5));    
		p2.add(addStationStatusComboBox, c);     			
		c = menuItem.updateGBC(c, 0, 3, 0.5, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(5,5,5,5));                                        
		p2.add(new JLabel("Latitude (WGS84):"), c);
		c = menuItem.updateGBC(c, 1, 3, 0.5, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(5,5,5,5));                   
		p2.add(addLatitudeTextField, c);                    
		c = menuItem.updateGBC(c, 0, 4, 0.5, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(5,5,5,5));                 
		p2.add(new JLabel("Longitude (WGS84):"), c);
		c = menuItem.updateGBC(c, 1, 4, 0.5, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(5,5,5,5));                   
		p2.add(addLongitudeTextField, c);        
		c = menuItem.updateGBC(c, 0, 5, 0.5, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(5,5,5,5));                
		p2.add(new JLabel("MMSI number:"), c);
		c = menuItem.updateGBC(c, 1, 5, 0.5, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(5,5,5,5));                    
		p2.add(addMMSINumberTextField, c);
		c = menuItem.updateGBC(c, 0, 6, 0.5, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(5,5,5,5));                
		p2.add(new JLabel("Transmission power (Watt):"), c);
		c = menuItem.updateGBC(c, 1, 6, 0.5, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(5,5,5,5));                   
		p2.add(addTransmissionPowerTextField, c);
		c = menuItem.updateGBC(c, 0, 7, 0.5, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(5,5,5,5));              
		/*
		p2.add(new JLabel("Status of the fixed AIS station:"), c);
		c = updateGBC(c, 1, 6, 0.5, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(5,5,5,5));               
		p2.add(addStationStatusComboBox, c);
		*/

		c = menuItem.updateGBC(c, 0, 0, 0.5, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, new Insets(5,5,5,5)); 
		panel.add(p2, c);                    
		  
		JPanel p3 = new JPanel(new GridBagLayout());
		p3.setBorder(BorderFactory.createTitledBorder("Antenna information"));
		c = menuItem.updateGBC(c, 0, 0, 0.5, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(5,5,5,5)); 
		p3.add(new JLabel("Antenna type:"), c);
		c = menuItem.updateGBC(c, 1, 0, 0.5, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(5,5,5,5));                    
		p3.add(addAntennaTypeComboBox, c);
		c = menuItem.updateGBC(c, 0, 1, 0.5, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(5,5,5,5));                      
		p3.add(new JLabel("Antenna height above terrain (m):"), c);
		c = menuItem.updateGBC(c, 1, 1, 0.5, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(5,5,5,5));                
		p3.add(addAntennaHeightTextField, c);                    
		c = menuItem.updateGBC(c, 0, 2, 0.5, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(5,5,5,5));                                  
		p3.add(new JLabel("Terrain height above sealevel (m):"), c);
		c = menuItem.updateGBC(c, 1, 2, 0.5, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(5,5,5,5));                  
		p3.add(addTerrainHeightTextField, c); 
		c = menuItem.updateGBC(c, 0, 3, 0.5, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(5,5,5,5));                     
		p3.add(new JLabel("Heading (degrees - integer):"), c);
		c = menuItem.updateGBC(c, 1, 3, 0.5, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(5,5,5,5));                                
		p3.add(addHeadingTextField, c); 
		c = menuItem.updateGBC(c, 0, 4, 0.5, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(5,5,5,5));                                        
		p3.add(new JLabel("Field of View angle (degrees - integer)"), c);
		c = menuItem.updateGBC(c, 1, 4, 0.5, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(5,5,5,5));                        
		p3.add(addFieldOfViewAngleTextField, c);                                         
		c = menuItem.updateGBC(c, 0, 5, 0.5, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(5,5,5,5));                                   
		p3.add(new JLabel("Gain (dB)"), c);
		c = menuItem.updateGBC(c, 1, 5, 0.5, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(5,5,5,5));                      
		p3.add(addGainTextField, c);                       

		c = menuItem.updateGBC(c, 1, 0, 0.5, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, new Insets(5,5,5,5)); 
		panel.add(p3, c);                      

		addStationFATDMAPanel = new JPanel(new GridBagLayout());
		addStationFATDMAPanel.setBorder(BorderFactory.createTitledBorder("FATDMA information"));
		c = menuItem.updateGBC(c, 0, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(5,5,5,5));
		addStationFATDMAPanel.add(new JLabel("Channel A:"), c);
		c = menuItem.updateGBC(c, 1, 0, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(5,5,5,5));                    
		addStationFATDMAPanel.add(addStationChannelAComboBox, c);

		c = menuItem.updateGBC(c, 0, 1, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(5,5,5,5));
		addStationFATDMAPanel.add(new JLabel("Channel B:"), c);
		c = menuItem.updateGBC(c, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(5,5,5,5));                    
		addStationFATDMAPanel.add(addStationChannelBComboBox, c);

		c = menuItem.updateGBC(c, 0, 1, 0.5, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, new Insets(5,5,5,5)); 
		c.gridwidth = 2; 
		panel.add(addStationFATDMAPanel, c); 
		c.gridwidth = 1;
		
		addAdditionalInformationJTextArea.setLineWrap(true);
		addAdditionalInformationJTextArea.setWrapStyleWord(true);                    
		JScrollPane p5 = new JScrollPane(addAdditionalInformationJTextArea);
		p5.setBorder(BorderFactory.createTitledBorder("Additional information"));
		p5.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		p5.setPreferredSize(new Dimension(580, 90));
		p5.setMaximumSize(new Dimension(580, 90));

		c = menuItem.updateGBC(c, 0, 2, 0.5, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, new Insets(5,5,5,5)); 
		c.gridwidth = 2; 
		panel.add(p5, c);
					
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(doAddStationButton);          
		buttonPanel.add(cancelAddStationButton);
		c = menuItem.updateGBC(c, 0, 3, 0.5, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5,5,5,5));             
		c.gridwidth = 2; 
		panel.add(buttonPanel, c);

		getContentPane().add(panel);

		addStationChannelAComboBox.setSelectedIndex(1);
		addStationChannelBComboBox.setSelectedIndex(2);		
	}
	
    public void itemStateChanged(ItemEvent e) {
        
        if (menuItem.ignoreListeners) {
            return;
        }	
        if (addAntennaTypeComboBox != null && e.getItemSelectable() == addAntennaTypeComboBox && e.getStateChange() == ItemEvent.SELECTED) {            
            updateAntennaTypeComboBox();

		} else if (addStationTypeComboBox != null && e.getItemSelectable() == addStationTypeComboBox && e.getStateChange() == ItemEvent.DESELECTED) {
			if (oldAddStationTypeIndex == -1) {
				for (int i=0; i< addStationTypeComboBox.getItemCount(); i++) {			
					if (e.getItem() == addStationTypeComboBox.getItemAt(i)) {
						oldAddStationTypeIndex = i;
						break;
					}	
				}		
			}		

		} else if ((addStationTypeComboBox != null && e.getItemSelectable() == addStationTypeComboBox && e.getStateChange() == ItemEvent.SELECTED)) {
			if (oldAddStationTypeIndex != -1) {
				if ((channelAPanel == null || channelAPanel.getComponents().length < 10) &&
						(channelBPanel == null || channelBPanel.getComponents().length < 10)) {
					updateAddStationChannelComboBoxesAndScrollPanes(true, true, false, false);
				} else {
					int response = JOptionPane.showConfirmDialog(this, "Are you sure you want change the station type? This will reset the FATDMA information.", "Confirm action", JOptionPane.YES_NO_OPTION);
					if (response == JOptionPane.YES_OPTION) {
						updateAddStationChannelComboBoxesAndScrollPanes(true, true, false, false);
					} else {
						menuItem.ignoreListeners = true;
						addStationTypeComboBox.setSelectedIndex(oldAddStationTypeIndex);						
						menuItem.ignoreListeners = false;						
					}				
				}
				oldAddStationTypeIndex = -1;	
			}		
			
		} else if (addStationChannelAComboBox != null && e.getItemSelectable() == addStationChannelAComboBox && e.getStateChange() == ItemEvent.DESELECTED) {
			if (oldAddStationChannelAIndex == -1) {
				for (int i=0; i< addStationChannelAComboBox.getItemCount(); i++) {			
					if (e.getItem() == addStationChannelAComboBox.getItemAt(i)) {
						oldAddStationChannelAIndex = i;
						break;
					}
				}
			}			
			
		} else if (addStationChannelAComboBox != null && e.getItemSelectable() == addStationChannelAComboBox && e.getStateChange() == ItemEvent.SELECTED) {			
			if (oldAddStationChannelAIndex != -1) {
				if (channelAPanel == null || channelAPanel.getComponents().length < 10) {
					updateAddStationChannelComboBoxesAndScrollPanes(true, false, false, false);
				} else {
					int response = JOptionPane.showConfirmDialog(this, "Are you sure you want change the channel A? This will reset the FATDMA information for it.", "Confirm action", JOptionPane.YES_NO_OPTION);
					if (response == JOptionPane.YES_OPTION) {
						updateAddStationChannelComboBoxesAndScrollPanes(true, false, false, false);
					} else {
						menuItem.ignoreListeners = true;
						addStationChannelAComboBox.setSelectedIndex(oldAddStationChannelAIndex);
						menuItem.ignoreListeners = false;
					}
				}
				oldAddStationChannelAIndex = -1;				
			}		
		
		} else if (addStationChannelBComboBox != null && e.getItemSelectable() == addStationChannelBComboBox && e.getStateChange() == ItemEvent.DESELECTED) {
			if (oldAddStationChannelBIndex == -1) {
				for (int i=0; i< addStationChannelBComboBox.getItemCount(); i++) {			
					if (e.getItem() == addStationChannelBComboBox.getItemAt(i)) {
						oldAddStationChannelBIndex = i;
						break;
					}
				}
			}					
			
		} else if (addStationChannelBComboBox != null && e.getItemSelectable() == addStationChannelBComboBox && e.getStateChange() == ItemEvent.SELECTED) {     
			if (oldAddStationChannelBIndex != -1) {
				if (channelBPanel == null || channelBPanel.getComponents().length < 10) {
					updateAddStationChannelComboBoxesAndScrollPanes(false, true, false, false);
				} else {
					int response = JOptionPane.showConfirmDialog(this, "Are you sure you want change the channel B? This will reset the FATDMA information for it.", "Confirm action", JOptionPane.YES_NO_OPTION);
					if (response == JOptionPane.YES_OPTION) {
						updateAddStationChannelComboBoxesAndScrollPanes(false, true, false, false);
					} else {
						menuItem.ignoreListeners = true;					
						addStationChannelBComboBox.setSelectedIndex(oldAddStationChannelBIndex);
						menuItem.ignoreListeners = false;
					}		
				}
				oldAddStationChannelBIndex = -1;				
			}
		}
		
	}
	
    public void actionPerformed(ActionEvent e) {
        
        if (menuItem.ignoreListeners) {
            return;
        }
        
        if (doAddStationButton != null && e.getSource() == doAddStationButton) {                     
            boolean success = addStation();
            if (success) {
				menuItem.stationAdded(addStationNameTextField.getText());
            }
            
        } else if (cancelAddStationButton != null && e.getSource() == cancelAddStationButton) {
            int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to cancel?", "Confirm action", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                this.dispose();
            } else if (response == JOptionPane.NO_OPTION) {                        
                // do nothing
            }	
		
		} else {
				
			if (channelAPanel != null) {
				Component[] components = channelAPanel.getComponents();
				for (int i=0; i<components.length; i++) {
					if (e.getSource() instanceof JButton && e.getSource() == components[i]) {				
						JButton button = (JButton) e.getSource();
						if (button.getText().equals("Add row")) {						
							updateAddStationChannelComboBoxesAndScrollPanes(false, false, true, false);					
						} else if (button.getText().equals("Delete row")) {
							int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete the row?", "Confirm action", JOptionPane.YES_NO_OPTION);
							if (response == JOptionPane.YES_OPTION) {
								deleteRow("A", i);
							}
						}
					}
				}
			}

			if (channelBPanel != null) {
				Component[] components = channelBPanel.getComponents();
				for (int i=0; i<components.length; i++) {						
					if (e.getSource() instanceof JButton && e.getSource() == components[i]) {
						JButton button = (JButton) e.getSource();
						if (button.getText().equals("Add row")) {
							updateAddStationChannelComboBoxesAndScrollPanes(false, false, false, true);	
						} else if (button.getText().equals("Delete row")) {
							int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete the row?", "Confirm action", JOptionPane.YES_NO_OPTION);
							if (response == JOptionPane.YES_OPTION) {							
								deleteRow("B", i);
							}
						}			
					}				
				}
			}
		}
	}
	
    private void updateAntennaTypeComboBox() {
        if (addAntennaTypeComboBox.getSelectedIndex() == 0) {  // no antenna         
            addAntennaHeightTextField.setText("");
            addAntennaHeightTextField.setEnabled(false);
            addTerrainHeightTextField.setText("");
            addTerrainHeightTextField.setEnabled(false);
            addHeadingTextField.setText("");
            addHeadingTextField.setEnabled(false);
            addFieldOfViewAngleTextField.setText("");
            addFieldOfViewAngleTextField.setEnabled(false);
            addGainTextField.setText("");
            addGainTextField.setEnabled(false); 
        } else if (addAntennaTypeComboBox.getSelectedIndex() == 1) {  // omnidirectional
			addAntennaHeightTextField.setEnabled(true);
			addTerrainHeightTextField.setEnabled(true);
			addHeadingTextField.setText("");
            addHeadingTextField.setEnabled(false);
            addFieldOfViewAngleTextField.setText("");
            addFieldOfViewAngleTextField.setEnabled(false);
            addGainTextField.setText("");
            addGainTextField.setEnabled(false); 
        } else if (addAntennaTypeComboBox.getSelectedIndex() == 2) {  // directional 
			addAntennaHeightTextField.setEnabled(true);
			addTerrainHeightTextField.setEnabled(true);
            addHeadingTextField.setEnabled(true);
            addFieldOfViewAngleTextField.setEnabled(true);
            addGainTextField.setEnabled(true); 
        } 
    }	
	
	private void updateAddStationChannelComboBoxesAndScrollPanes(boolean clearChannelA, boolean clearChannelB, boolean addRowToChannelA, boolean addRowToChannelB) {
	
		menuItem.ignoreListeners = true;
	
		GridBagConstraints c = new GridBagConstraints();	

		Component[] channelAComponents = new Component[0];

		if (channelAPanel != null) {
			if (!clearChannelA) {
				channelAComponents = channelAPanel.getComponents();		
			}
			channelAPanel.removeAll();
		}
		
		Component[] channelBComponents = new Component[0];	
		
		if (channelBPanel != null) {
			if (!clearChannelB) {
				channelBComponents = channelBPanel.getComponents();				
			}
			channelBPanel.removeAll();	
		}
		
		if (addStationFATDMAPanel != null) {
			addStationFATDMAPanel.removeAll();		
		}
	
		if (addStationTypeComboBox.getSelectedIndex() == 2) {  // receiver
			
			// receivers don't have any FATDMA information
			
		} else if (addStationTypeComboBox.getSelectedIndex() == 0 || addStationTypeComboBox.getSelectedIndex() == 1) {  // base station or repeater	

            c = menuItem.updateGBC(c, 0, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(5,5,5,5));
            addStationFATDMAPanel.add(new JLabel("Channel A:"), c);
            c = menuItem.updateGBC(c, 1, 0, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(5,5,5,5));                    
            addStationFATDMAPanel.add(addStationChannelAComboBox, c);
				
			if (!((String) addStationChannelAComboBox.getSelectedItem()).equals("NULL")) {

				channelAPanel = new JPanel(new GridBagLayout());
				c = menuItem.updateGBC(c, 0, 0, 0.5, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5,5,5,5));			
				JLabel startSlotLabel = new JLabel("<html><u>Startslot</u></html>");
				startSlotLabel.setToolTipText("FATDMA_startslot (0..2249)");
				channelAPanel.add(startSlotLabel, c);			
				c = menuItem.updateGBC(c, 1, 0, 0.5, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5,5,5,5));			
				JLabel blockSizeLabel = new JLabel("<html><u>Block size</u></html>");
				blockSizeLabel.setToolTipText("FATDMA_block_size (1..5)");				
				channelAPanel.add(blockSizeLabel, c);	
				c = menuItem.updateGBC(c, 2, 0, 0.5, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5,5,5,5));			
				JLabel incrementLabel = new JLabel("<html><u>Increment</u></html>");
				incrementLabel.setToolTipText("FATDMA_increment (0..1125)");		
				channelAPanel.add(incrementLabel, c);	
				c = menuItem.updateGBC(c, 3, 0, 0.5, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5,5,5,5));			
				JLabel ownershipLabel = new JLabel("<html><u>Ownership</u></html>");
				ownershipLabel.setToolTipText("FATDMA_ownership (L: use by local station, R: use by remote station)");
				channelAPanel.add(ownershipLabel, c);
				c = menuItem.updateGBC(c, 4, 0, 0.5, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5,5,5,5));			
				channelAPanel.add(new JLabel(""), c);	
				
				for (int i=5; i<channelAComponents.length-1; i++) {
					if (channelAComponents[i] instanceof JButton) {
						c = menuItem.updateGBC(c, i%5, (channelAPanel.getComponents().length)/5, 0.5, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5,5,5,5));
					} else {
						c = menuItem.updateGBC(c, i%5, (channelAPanel.getComponents().length)/5, 0.5, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5,5,5,5));						
					}
					channelAPanel.add(channelAComponents[i], c);
				}
				
				if (addRowToChannelA) {
					for (int cols=0; cols<5; cols++) {						
						JComponent component = null;						
						if (cols < 3) {
							component = new JTextField(8);				
							c = menuItem.updateGBC(c, cols, (channelAPanel.getComponents().length)/5, 0.5, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5,5,5,5));	
						} else if (cols == 3) {
							component = new JComboBox();									
							((JComboBox) component).addItem("L");
							((JComboBox) component).addItem("R");
							((JComboBox) component).setBorder(new EmptyBorder(0, 3, 0, 3));
							c = menuItem.updateGBC(c, cols, (channelAPanel.getComponents().length)/5, 0.5, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5,5,5,5));					
						} else if (cols == 4) {
							component = new JButton("Delete row");	
							((JButton) component).setMargin(new Insets(0, 3, 0, 3));
							((JButton) component).addActionListener(this);
							c = menuItem.updateGBC(c, cols, (channelAPanel.getComponents().length)/5, 0.5, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5,5,5,5));
						}		
						channelAPanel.add(component, c);						
					}
				}
				
				JButton button = new JButton("Add row");
				button.addActionListener(this);				
				button.setMargin(new Insets(0, 3, 0, 3));
				c = menuItem.updateGBC(c, 4, (channelAPanel.getComponents().length)/5, 0.5, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5,5,5,5));			
				channelAPanel.add(button, c);

				addStationChannelAScrollPane = new JScrollPane(channelAPanel);
				addStationChannelAScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
				addStationChannelAScrollPane.setPreferredSize(new Dimension(630, 100));
				addStationChannelAScrollPane.setMaximumSize(new Dimension(630, 100));
				addStationChannelAScrollPane.setMinimumSize(new Dimension(630, 100));	
								
				c = menuItem.updateGBC(c, 0, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(5,5,5,5));   				
				c.gridwidth = 2;
				addStationFATDMAPanel.add(addStationChannelAScrollPane, c);
				c.gridwidth = 1;
			}

			c = menuItem.updateGBC(c, 0, 2, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(5,5,5,5));
            addStationFATDMAPanel.add(new JLabel("Channel B:"), c);
            c = menuItem.updateGBC(c, 1, 2, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(5,5,5,5));                    
            addStationFATDMAPanel.add(addStationChannelBComboBox, c);			
			
			if (!((String) addStationChannelBComboBox.getSelectedItem()).equals("NULL")) {

				channelBPanel = new JPanel(new GridBagLayout());
				c = menuItem.updateGBC(c, 0, 0, 0.5, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5,5,5,5));			
				JLabel startSlotLabel = new JLabel("<html><u>Startslot</u></html>");
				startSlotLabel.setToolTipText("FATDMA_startslot (0..2249)");
				channelBPanel.add(startSlotLabel, c);			
				c = menuItem.updateGBC(c, 1, 0, 0.5, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5,5,5,5));			
				JLabel blockSizeLabel = new JLabel("<html><u>Block size</u></html>");
				blockSizeLabel.setToolTipText("FATDMA_block_size (1..5)");				
				channelBPanel.add(blockSizeLabel, c);	
				c = menuItem.updateGBC(c, 2, 0, 0.5, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5,5,5,5));			
				JLabel incrementLabel = new JLabel("<html><u>Increment</u></html>");
				incrementLabel.setToolTipText("FATDMA_increment (0..1125)");		
				channelBPanel.add(incrementLabel, c);	
				c = menuItem.updateGBC(c, 3, 0, 0.5, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5,5,5,5));			
				JLabel ownershipLabel = new JLabel("<html><u>Ownership</u></html>");
				ownershipLabel.setToolTipText("FATDMA_ownership (L: use by local station, R: use by remote station)");
				channelBPanel.add(ownershipLabel, c);
				c = menuItem.updateGBC(c, 4, 0, 0.5, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5,5,5,5));			
				channelBPanel.add(new JLabel(""), c);	

				for (int i=5; i<channelBComponents.length-1; i++) {
					if (channelBComponents[i] instanceof JButton) {
						c = menuItem.updateGBC(c, i%5, (channelBPanel.getComponents().length)/5, 0.5, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5,5,5,5));
					} else {
						c = menuItem.updateGBC(c, i%5, (channelBPanel.getComponents().length)/5, 0.5, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5,5,5,5));						
					}
					channelBPanel.add(channelBComponents[i], c);
				}			

				if (addRowToChannelB) {
					for (int cols=0; cols<5; cols++) {						
						JComponent component = null;						
						if (cols < 3) {
							component = new JTextField(8);				
							c = menuItem.updateGBC(c, cols, (channelBPanel.getComponents().length)/5, 0.5, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5,5,5,5));	
						} else if (cols == 3) {
							component = new JComboBox();									
							((JComboBox) component).addItem("L");
							((JComboBox) component).addItem("R");
							((JComboBox) component).setBorder(new EmptyBorder(0, 3, 0, 3));
							c = menuItem.updateGBC(c, cols, (channelBPanel.getComponents().length)/5, 0.5, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5,5,5,5));	
						} else if (cols == 4) {
							component = new JButton("Delete row");	
							((JButton) component).setMargin(new Insets(0, 3, 0, 3));
							((JButton) component).addActionListener(this);
							c = menuItem.updateGBC(c, cols, (channelBPanel.getComponents().length)/5, 0.5, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5,5,5,5));
						}		
						channelBPanel.add(component, c);						
					}
				}
				
				JButton button = new JButton("Add row");
				button.addActionListener(this);				
				button.setMargin(new Insets(0, 3, 0, 3));
				c = menuItem.updateGBC(c, 4, (channelBPanel.getComponents().length)/5, 0.5, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5,5,5,5));			
				channelBPanel.add(button, c);		
				
				addStationChannelBScrollPane = new JScrollPane(channelBPanel);
				addStationChannelBScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
				addStationChannelBScrollPane.setPreferredSize(new Dimension(630, 100));
				addStationChannelBScrollPane.setMaximumSize(new Dimension(630, 100));
				addStationChannelBScrollPane.setMinimumSize(new Dimension(630, 100));	

				c = menuItem.updateGBC(c, 0, 3, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(5,5,5,5));   				
				c.gridwidth = 2;
				addStationFATDMAPanel.add(addStationChannelBScrollPane, c);
				c.gridwidth = 1;
			}
			
		} else if (addStationTypeComboBox.getSelectedIndex() == 3) {  // aton station

            c = menuItem.updateGBC(c, 0, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(5,5,5,5));
            addStationFATDMAPanel.add(new JLabel("Channel A:"), c);
            c = menuItem.updateGBC(c, 1, 0, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(5,5,5,5));                    
            addStationFATDMAPanel.add(addStationChannelAComboBox, c);

			if (!((String) addStationChannelAComboBox.getSelectedItem()).equals("NULL")) {

				channelAPanel = new JPanel(new GridBagLayout());
				c = menuItem.updateGBC(c, 0, 0, 0.5, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5,5,5,5));			
				JLabel accessSchemeLabel = new JLabel("<html><u>Access scheme</u></html>");
				accessSchemeLabel.setToolTipText("Access_scheme (FATDMA, RATDMA, CSTDMA)");
				channelAPanel.add(accessSchemeLabel, c);
				c = menuItem.updateGBC(c, 1, 0, 0.5, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5,5,5,5));			
				JLabel messageIDLabel = new JLabel("<html><u>Message ID</u></html>");
				messageIDLabel.setToolTipText("Message_ID (0..64) (Identifies which message type this transmission relates to)");
				channelAPanel.add(messageIDLabel, c);
				c = menuItem.updateGBC(c, 2, 0, 0.5, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5,5,5,5));			
				JLabel utcHourLabel = new JLabel("<html><u>UTC Hour</u></html>");
				utcHourLabel.setToolTipText("UTC_Hour (0-23; 24 = UTC hour not available) (UTC hour of first transmission of the day)");
				channelAPanel.add(utcHourLabel, c);	
				c = menuItem.updateGBC(c, 3, 0, 0.5, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5,5,5,5));	
				JLabel utcMinuteLabel = new JLabel("<html><u>UTC Minute</u></html>");
				utcMinuteLabel.setToolTipText("UTC_Minute (0-59; 60 = UTC minute not available) (UTC minute of first transmission of the day)");
				channelAPanel.add(utcMinuteLabel, c);		
				c = menuItem.updateGBC(c, 4, 0, 0.5, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5,5,5,5));			
				JLabel startslotLabel = new JLabel("<html><u>Startslot</u></html>");
				startslotLabel.setToolTipText("startslot (0-2249; 4095 = discontinue broadcast) (Only relevant for FATDMA)");
				channelAPanel.add(startslotLabel, c);
				c = menuItem.updateGBC(c, 5, 0, 0.5, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5,5,5,5));			
				JLabel blockSizeLabel = new JLabel("<html><u>Block size</u></html>");
				blockSizeLabel.setToolTipText("block_size (1..5)");
				channelAPanel.add(blockSizeLabel, c);							
				c = menuItem.updateGBC(c, 6, 0, 0.5, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5,5,5,5));			
				JLabel incrementLabel = new JLabel("<html><u>Increment</u></html>");
				incrementLabel.setToolTipText("increment (0..324000), (No. of slots in FATDMA, no. of seconds in RATDMA/CSTDMA)");
				channelAPanel.add(incrementLabel, c);	
				c = menuItem.updateGBC(c, 7, 0, 0.5, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5,5,5,5));			
				channelAPanel.add(new JLabel(""), c);	

				for (int i=8; i<channelAComponents.length-1; i++) {
					if (channelAComponents[i] instanceof JButton) {
						c = menuItem.updateGBC(c, i%8, (channelAPanel.getComponents().length)/8, 0.5, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5,5,5,5));						
					} else {
						c = menuItem.updateGBC(c, i%8, (channelAPanel.getComponents().length)/8, 0.5, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5,5,5,5));
					}
					channelAPanel.add(channelAComponents[i], c);
				}
				
				if (addRowToChannelA) {
					for (int cols=0; cols<8; cols++) {						
						JComponent component = null;
						if  (cols == 0) {
							component = new JComboBox();									
							((JComboBox) component).addItem("FATDMA");
							((JComboBox) component).addItem("RATDMA");
							((JComboBox) component).addItem("CSTDMA");
							((JComboBox) component).setBorder(new EmptyBorder(0, 3, 0, 3));
							c = menuItem.updateGBC(c, cols, (channelAPanel.getComponents().length)/8, 0.5, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5,5,5,5));		
						} else if (cols > 0 && cols < 7) {
							component = new JTextField(8);				
							c = menuItem.updateGBC(c, cols, (channelAPanel.getComponents().length)/8, 0.5, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5,5,5,5));
						} else if (cols == 7) {
							component = new JButton("Delete row");	
							((JButton) component).setMargin(new Insets(0, 3, 0, 3));
							((JButton) component).addActionListener(this);
							c = menuItem.updateGBC(c, cols, (channelAPanel.getComponents().length)/8, 0.5, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5,5,5,5));
						}		
						channelAPanel.add(component, c);						
					}
				}				
				
				JButton button = new JButton("Add row");
				button.addActionListener(this);				
				button.setMargin(new Insets(0, 3, 0, 3));
				c = menuItem.updateGBC(c, 7, (channelAPanel.getComponents().length)/8, 0.5, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5,5,5,5));			
				channelAPanel.add(button, c);				
				
				addStationChannelAScrollPane = new JScrollPane(channelAPanel);
				addStationChannelAScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
				addStationChannelAScrollPane.setPreferredSize(new Dimension(830, 100));
				addStationChannelAScrollPane.setMaximumSize(new Dimension(830, 100));
				addStationChannelAScrollPane.setMinimumSize(new Dimension(830, 100));	
				
				c = menuItem.updateGBC(c, 0, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(5,5,5,5));   				
				c.gridwidth = 2;
				addStationFATDMAPanel.add(addStationChannelAScrollPane, c);
				c.gridwidth = 1;				
			}

			c = menuItem.updateGBC(c, 0, 2, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(5,5,5,5));
            addStationFATDMAPanel.add(new JLabel("Channel B:"), c);
            c = menuItem.updateGBC(c, 1, 2, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(5,5,5,5));                    
            addStationFATDMAPanel.add(addStationChannelBComboBox, c);			
			
			if (!((String) addStationChannelBComboBox.getSelectedItem()).equals("NULL")) {
		 
				channelBPanel = new JPanel(new GridBagLayout());
				c = menuItem.updateGBC(c, 0, 0, 0.5, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5,5,5,5));			
				JLabel accessSchemeLabel = new JLabel("<html><u>Access scheme</u></html>");
				accessSchemeLabel.setToolTipText("Access_scheme (FATDMA, RATDMA, CSTDMA)");
				channelBPanel.add(accessSchemeLabel, c);
				c = menuItem.updateGBC(c, 1, 0, 0.5, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5,5,5,5));			
				JLabel messageIDLabel = new JLabel("<html><u>Message ID</u></html>");
				messageIDLabel.setToolTipText("Message_ID (0..64) (Identifies which message type this transmission relates to)");
				channelBPanel.add(messageIDLabel, c);
				c = menuItem.updateGBC(c, 2, 0, 0.5, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5,5,5,5));			
				JLabel utcHourLabel = new JLabel("<html><u>UTC Hour</u></html>");
				utcHourLabel.setToolTipText("UTC_Hour (0-23; 24 = UTC hour not available) (UTC hour of first transmission of the day)");
				channelBPanel.add(utcHourLabel, c);	
				c = menuItem.updateGBC(c, 3, 0, 0.5, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5,5,5,5));	
				JLabel utcMinuteLabel = new JLabel("<html><u>UTC Minute</u></html>");
				utcMinuteLabel.setToolTipText("UTC_Minute (0-59; 60 = UTC minute not available) (UTC minute of first transmission of the day)");
				channelBPanel.add(utcMinuteLabel, c);		
				c = menuItem.updateGBC(c, 4, 0, 0.5, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5,5,5,5));			
				JLabel startslotLabel = new JLabel("<html><u>Startslot</u></html>");
				startslotLabel.setToolTipText("startslot (0-2249; 4095 = discontinue broadcast) (Only relevant for FATDMA)");
				channelBPanel.add(startslotLabel, c);
				c = menuItem.updateGBC(c, 5, 0, 0.5, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5,5,5,5));			
				JLabel blockSizeLabel = new JLabel("<html><u>Block size</u></html>");
				blockSizeLabel.setToolTipText("block_size (1..5)");
				channelBPanel.add(blockSizeLabel, c);							
				c = menuItem.updateGBC(c, 6, 0, 0.5, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5,5,5,5));			
				JLabel incrementLabel = new JLabel("<html><u>Increment</u></html>");
				incrementLabel.setToolTipText("increment (0..324000), (No. of slots in FATDMA, no. of seconds in RATDMA/CSTDMA)");
				channelBPanel.add(incrementLabel, c);			 
				c = menuItem.updateGBC(c, 7, 0, 0.5, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5,5,5,5));			
				channelBPanel.add(new JLabel(""), c);					

				for (int i=8; i<channelBComponents.length-1; i++) {
					if (channelBComponents[i] instanceof JButton) {				
						c = menuItem.updateGBC(c, i%8, (channelBPanel.getComponents().length)/8, 0.5, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5,5,5,5));
					} else {
						c = menuItem.updateGBC(c, i%8, (channelBPanel.getComponents().length)/8, 0.5, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5,5,5,5));						
					}
					channelBPanel.add(channelBComponents[i], c);
				}				
				
				if (addRowToChannelB) {
					for (int cols=0; cols<8; cols++) {						
						JComponent component = null;
						if  (cols == 0) {
							component = new JComboBox();									
							((JComboBox) component).addItem("FATDMA");
							((JComboBox) component).addItem("RATDMA");
							((JComboBox) component).addItem("CSTDMA");
							((JComboBox) component).setBorder(new EmptyBorder(0, 3, 0, 3));
							c = menuItem.updateGBC(c, cols, (channelBPanel.getComponents().length)/8, 0.5, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5,5,5,5));		
						} else if (cols > 0 && cols < 7) {
							component = new JTextField(8);				
							c = menuItem.updateGBC(c, cols, (channelBPanel.getComponents().length)/8, 0.5, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5,5,5,5));
						} else if (cols == 7) {
							component = new JButton("Delete row");	
							((JButton) component).setMargin(new Insets(0, 3, 0, 3));
							((JButton) component).addActionListener(this);
							c = menuItem.updateGBC(c, cols, (channelBPanel.getComponents().length)/8, 0.5, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5,5,5,5));
						}		
						channelBPanel.add(component, c);						
					}
				}
				
				JButton button = new JButton("Add row");
				button.addActionListener(this);
				button.setMargin(new Insets(0, 3, 0, 3));
				c = menuItem.updateGBC(c, 7, (channelBPanel.getComponents().length)/8, 0.5, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5,5,5,5));			
				channelBPanel.add(button, c);				
							
				addStationChannelBScrollPane = new JScrollPane(channelBPanel);
				addStationChannelBScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
				addStationChannelBScrollPane.setPreferredSize(new Dimension(830, 100));
				addStationChannelBScrollPane.setMaximumSize(new Dimension(830, 100));
				addStationChannelBScrollPane.setMinimumSize(new Dimension(830, 100));	

				c = menuItem.updateGBC(c, 0, 3, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(5,5,5,5));   				
				c.gridwidth = 2;
				addStationFATDMAPanel.add(addStationChannelBScrollPane, c);
				c.gridwidth = 1;
			}
		
		}
		
		this.pack();		
		addStationFATDMAPanel.repaint();
		addStationFATDMAPanel.validate();
		
		// scrolls to bottom
		if (addRowToChannelA) {
			addStationChannelAScrollPane.getViewport().setViewPosition(new Point(0, 10000));
		}
		if (addRowToChannelB) {
			addStationChannelBScrollPane.getViewport().setViewPosition(new Point(0, 10000));
		}
		
		menuItem.ignoreListeners = false;		
				
	}	
	
	private void deleteRow(String channel, int deleteRowButtonIndex) {
			
		menuItem.ignoreListeners = true;			
			
		if (addStationTypeComboBox.getSelectedIndex() == 0 || addStationTypeComboBox.getSelectedIndex() == 1) {  // base station or repeater
			for (int i=deleteRowButtonIndex; i>deleteRowButtonIndex-5; i--) {
				if (channel.equals("A")) {
					channelAPanel.remove(i);
				} else if (channel.equals("B")) {
					channelBPanel.remove(i);
				}
			}
		} else if (addStationTypeComboBox.getSelectedIndex() == 3) {  // aton station
			for (int i=deleteRowButtonIndex; i>deleteRowButtonIndex-8; i--) {
				if (channel.equals("A")) {
					channelAPanel.remove(i);
				} else if (channel.equals("B")) {
					channelBPanel.remove(i);
				}
			}
		}
		updateAddStationChannelComboBoxesAndScrollPanes(false, false, false, false);
		
		menuItem.ignoreListeners = false;
	}			
		
    private boolean addStation() {
        
        if (menuItem.getData() == null) {
            menuItem.setData(new EAVDAMData());
        }
        
        if (menuItem.alreadyExists(-1, addStationNameTextField.getText())) {
            JOptionPane.showMessageDialog(this, "A station with the given name already exists. " +
                "Please, select another name for the station.");                 
            return false;
        }

        if (addLatitudeTextField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Latitude is mandatory.");
            return false;
        } else {
            if (!addLatitudeTextField.getText().trim().isEmpty()) {
                try {
                    
                    Double.parseDouble(addLatitudeTextField.getText().replace(",", ".").trim());                    
                } catch (NumberFormatException ex) {                
                    JOptionPane.showMessageDialog(this, "Latitude is not a valid number.");
                    return false;
                }
            }
        }            
        if (addLongitudeTextField.getText().trim().isEmpty()) {            
            JOptionPane.showMessageDialog(this, "Longitude is mandatory.");                    
            return false;
        } else {                                                        
            if (!addLongitudeTextField.getText().trim().isEmpty()) {
                try {
                    Double.parseDouble(addLongitudeTextField.getText().replace(",", ".").trim());
                } catch (NumberFormatException ex) {                
                    JOptionPane.showMessageDialog(this, "Longitude is not a valid number.");
                    return false;
                }
            }
        }                    
        try {
            if (!addTransmissionPowerTextField.getText().trim().isEmpty()) {
                Double.parseDouble(addTransmissionPowerTextField.getText().replace(",", ".").trim());
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Transmission power is not a valid number.");
            return false;
        }   
        if (addAntennaTypeComboBox.getSelectedIndex() == 1 &&
                (addAntennaHeightTextField.getText().trim().isEmpty() ||
                addTerrainHeightTextField.getText().trim().isEmpty())) {
            JOptionPane.showMessageDialog(this, "Antenna height and terrain height must both be given.");
            return false;
        }
        if (addAntennaTypeComboBox.getSelectedIndex() == 2 &&
                (addAntennaHeightTextField.getText().trim().isEmpty() ||
                addTerrainHeightTextField.getText().trim().isEmpty() ||
                addHeadingTextField.getText().trim().isEmpty() ||
                addFieldOfViewAngleTextField.getText().trim().isEmpty() ||
                addGainTextField.getText().trim().isEmpty())) {
            JOptionPane.showMessageDialog(this, "Antenna height, terrain height, heading, field of view angle and gain must all be given.");
            return false;
        }
        try {
            if (!addAntennaHeightTextField.getText().trim().isEmpty()) {
                Double.parseDouble(addAntennaHeightTextField.getText().replace(",", ".").trim());
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Antenna height is not a valid number.");                     
            return false;
        }  
        try {
            if (!addTerrainHeightTextField.getText().trim().isEmpty()) {
                Double.parseDouble(addTerrainHeightTextField.getText().replace(",", ".").trim());
            }
        } catch (NumberFormatException ex) {        
            JOptionPane.showMessageDialog(this, "Terrain height is not a valid number.");
            return false;
        }                                 
        try {
            if (!addHeadingTextField.getText().trim().isEmpty()) {
                Integer.parseInt(addHeadingTextField.getText().trim());
            }
        } catch (NumberFormatException ex) {        
            JOptionPane.showMessageDialog(this, "Heading is not a valid integer.");
            return false;
        }  
        try {
            if (!addFieldOfViewAngleTextField.getText().trim().isEmpty()) {
                Integer.parseInt(addFieldOfViewAngleTextField.getText().trim());
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Field of view angle is not a valid integer.");                        
            return false;
        }      
        try {
            if (!addGainTextField.getText().trim().isEmpty()) {
                Double.parseDouble(addGainTextField.getText().replace(",", ".").trim());
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Gain is not a valid number.");
            return false;
        }
        
        AISFixedStationData stationData = new AISFixedStationData();

        try {                 
            stationData.setStationName(addStationNameTextField.getText().trim());
            if (addStationTypeComboBox.getSelectedIndex() == 0) {
                stationData.setStationType(AISFixedStationType.BASESTATION);
            } else if (addStationTypeComboBox.getSelectedIndex() == 1) {
                stationData.setStationType(AISFixedStationType.REPEATER); 
            } else if (addStationTypeComboBox.getSelectedIndex() == 2) {
                stationData.setStationType(AISFixedStationType.RECEIVER); 
            } else if (addStationTypeComboBox.getSelectedIndex() == 3) {
                stationData.setStationType(AISFixedStationType.ATON); 
            }  
            stationData.setLat(new Double(addLatitudeTextField.getText().replace(",", ".").trim()).doubleValue());                                
            stationData.setLon(new Double(addLongitudeTextField.getText().replace(",", ".").trim()).doubleValue());  
            if (addMMSINumberTextField.getText().trim().isEmpty()) {
                stationData.setMmsi(null);
            } else {
                stationData.setMmsi(addMMSINumberTextField.getText().trim());
            }
            if (addTransmissionPowerTextField.getText().trim().isEmpty()) {
                stationData.setTransmissionPower(null);
            } else {
                stationData.setTransmissionPower(new Double(addTransmissionPowerTextField.getText().replace(",", ".").trim()));               
            }
            Antenna antenna = stationData.getAntenna();
            if (addAntennaTypeComboBox.getSelectedIndex() == 0) {
                stationData.setAntenna(null);
            } else if (addAntennaTypeComboBox.getSelectedIndex() == 1) {
                if (antenna == null) {
                    antenna = new Antenna();
                }
                antenna.setAntennaType(AntennaType.OMNIDIRECTIONAL);                    
            } else if (addAntennaTypeComboBox.getSelectedIndex() == 2) {
                if (antenna == null) {
                    antenna = new Antenna();
                }
                antenna.setAntennaType(AntennaType.DIRECTIONAL);
            }
            if (addAntennaTypeComboBox.getSelectedIndex() == 1 ||
                    addAntennaTypeComboBox.getSelectedIndex() == 2) {
                if (!addAntennaHeightTextField.getText().trim().isEmpty()) {
                    antenna.setAntennaHeight(new Double(addAntennaHeightTextField.getText().replace(",", ".").trim()).doubleValue());
                }
                if (!addTerrainHeightTextField.getText().trim().isEmpty()) {
                    antenna.setTerrainHeight(new Double(addTerrainHeightTextField.getText().replace(",", ".").trim()).doubleValue());
                }
            }
            if (addAntennaTypeComboBox.getSelectedIndex() == 2) {
                if (addHeadingTextField.getText().trim().isEmpty()) {                        
                    antenna.setHeading(null);
                } else {
                    antenna.setHeading(new Integer(addHeadingTextField.getText().trim()));
                }
                if (addFieldOfViewAngleTextField.getText().trim().isEmpty()) { 
                    antenna.setFieldOfViewAngle(null);
                } else {
                    antenna.setFieldOfViewAngle(new Integer(addFieldOfViewAngleTextField.getText().trim()));
                }
                if (addGainTextField.getText().trim().isEmpty()) {             
                    antenna.setGain(null);
                } else {
                    antenna.setGain(new Double(addGainTextField.getText().replace(",", ".").trim()));
                }
            }
            if (addAntennaTypeComboBox.getSelectedIndex() == 1 ||
                    addAntennaTypeComboBox.getSelectedIndex() == 2) {
                stationData.setAntenna(antenna);
            }
            if (addAdditionalInformationJTextArea.getText().trim().isEmpty()) {
                stationData.setDescription(null);
            } else {
                stationData.setDescription(addAdditionalInformationJTextArea.getText().trim());
            }
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());              
            return false;
        }            

        if (((String) menuItem.getSelectDatasetComboBox().getSelectedItem()).startsWith(StationInformationMenuItem.OWN_ACTIVE_STATIONS_LABEL)) {            

			if (addStationStatusComboBox.getSelectedIndex() == 0) {  // operative
				AISFixedStationStatus status = new AISFixedStationStatus();
				status.setStatusID(DerbyDBInterface.STATUS_ACTIVE);
				stationData.setStatus(status);
			} else if (addStationStatusComboBox.getSelectedIndex() == 1) {  // planned
				AISFixedStationStatus status = new AISFixedStationStatus();
				status.setStatusID(DerbyDBInterface.STATUS_PLANNED);				
				stationData.setStatus(status);
			}
		
            ActiveStation activeStation = new ActiveStation();
            List<AISFixedStationData> stations = new ArrayList<AISFixedStationData>();
            stations.add(stationData);            
            activeStation.setStations(stations);
						
            List<ActiveStation> activeStations = null;
            if (menuItem.getData() == null || menuItem.getData().getActiveStations() == null) {
                activeStations = new ArrayList<ActiveStation>();
            } else {
                activeStations = menuItem.getData().getActiveStations();
            }
            activeStations.add(activeStation);                
            menuItem.getData().setActiveStations(activeStations);  
  
        } else if (((String) menuItem.getSelectDatasetComboBox().getSelectedItem()).startsWith(StationInformationMenuItem.SIMULATION_LABEL)) {

            AISFixedStationStatus status = new AISFixedStationStatus();
            status.setStatusID(DerbyDBInterface.STATUS_SIMULATED);
            stationData.setStatus(status);

			if (menuItem.getData() != null && menuItem.getData().getSimulatedStations() != null) {                
                for (Simulation s : menuItem.getData().getSimulatedStations()) {
                    if (((String) menuItem.getSelectDatasetComboBox().getSelectedItem()).endsWith(s.getName())) {
                        List<AISFixedStationData> stations = s.getStations();
                        if (stations == null) {
                            stations = new ArrayList<AISFixedStationData>();
                        }
                        stations.add(stationData);
                    }
                }
            }
        }

        DBHandler.saveData(menuItem.getData());

        return true;
    }
	
}