package dk.frv.eavdam.menus;

import dk.frv.eavdam.data.Address;
import dk.frv.eavdam.data.AISFixedStationData;
import dk.frv.eavdam.data.AISFixedStationStatus;
import dk.frv.eavdam.data.AISFixedStationType;
import dk.frv.eavdam.data.Antenna;
import dk.frv.eavdam.data.AntennaType;
import dk.frv.eavdam.data.EAVDAMData;
import dk.frv.eavdam.data.EAVDAMUser;
import dk.frv.eavdam.data.Person;
import dk.frv.eavdam.io.XMLExporter;
import dk.frv.eavdam.io.XMLImporter;
import dk.frv.eavdam.utils.DataFileHandler;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ComboBoxEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.xml.bind.JAXBException;

/**
 * This class represents a menu item that opens a frame where the user can edit
 * stations' information.
 */
public class StationInformationMenuItem extends JMenuItem {

    public static final long serialVersionUID = 3L;

    public StationInformationMenuItem(EavdamMenu eavdamMenu) {
        super("Edit Station Information");       
        addActionListener(new StationInformationMenuItemActionListener(eavdamMenu, null));
    }

    public StationInformationMenuItem(EavdamMenu eavdamMenu, String stationName) {
        super("Edit Station Information");       
        addActionListener(new StationInformationMenuItemActionListener(eavdamMenu, stationName));
    }
}
        
        
class StationInformationMenuItemActionListener implements ActionListener, ChangeListener, DocumentListener {

    private EavdamMenu eavdamMenu;

    private JDialog dialog;
    
    private JPanel selectStationPanel;
    private JTabbedPane tabbedPane;
    
    private JComboBox selectDatasetComboBox;
    private JButton deleteSimulationButton;
    private JTextField newSimulationTextField;
    private JButton addNewSimulationButton;
    
    private JComboBox selectStationComboBox;    
    
    private JTextField stationNameTextField;
    private JComboBox stationTypeComboBox;
    private JTextField latitudeTextField;
    private JTextField longitudeTextField;    
    private JTextField mmsiNumberTextField;
    private JTextField transmissionPowerTextField;
    
    private JComboBox antennaTypeComboBox;
    private JTextField antennaHeightTextField;
    private JTextField terrainHeightTextField;    
    private JTextField headingTextField;
    private JTextField fieldOfViewAngleTextField;
    private JTextField gainTextField;   
    
    private JTextArea additionalInformationJTextArea;

    private JButton planChangesButton;    
    private JButton deleteOperativeButton;
    private JButton makeOperativeButton;
    private JButton savePlansButton;
    private JButton deletePlansButton;
    private JButton acceptProposalButton;

    private JButton cancelButton;
    
    private JPanel notYetImplementedPanel;
    
    private EAVDAMData data;  
    
    private String initiallySelectedStationName;
    private int selectedStationIndex = 0; 
    
    private boolean noListeners = false;

    private static int OPERATIVE = 1;
    private static int PLANNED = 2;

    public StationInformationMenuItemActionListener(EavdamMenu eavdamMenu, String stationName) {
        this.eavdamMenu = eavdamMenu;
        this.initiallySelectedStationName = stationName;
    }
     
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() instanceof StationInformationMenuItem) {
                                        
            dialog = new JDialog(eavdamMenu.getOpenMapFrame(), "Edit Station Information", true);

            selectDatasetComboBox = getComboBox(null);
            // testing
            selectDatasetComboBox.addItem("Own active stations");
            selectDatasetComboBox.addItem("Simulation 1");
            selectDatasetComboBox.addItem("Simulation 2");
            selectDatasetComboBox.addItem("Stations of organisation XXX");
            selectDatasetComboBox.addItem("Stations of organisation YYY");
            selectDatasetComboBox.addActionListener(this);

            deleteSimulationButton = getButton("Delete selected simulation", 200);        
            deleteSimulationButton.setVisible(false);
            newSimulationTextField = new JTextField(20);
            addNewSimulationButton = getButton("Add new simulation dataset", 200);

            selectStationComboBox = getComboBox(null);
            
            stationNameTextField = getTextField(16);
            stationTypeComboBox = getComboBox(new String[] {"AIS Base Station", "AIS Repeater", "AIS Receiver station", "AIS AtoN station"});
            latitudeTextField = getTextField(16);          
            longitudeTextField = getTextField(16);
            mmsiNumberTextField = getTextField(16);
            transmissionPowerTextField = getTextField(16);            
    
            antennaTypeComboBox = getComboBox(new String[] {"No antenna", "Omnidirectional", "Directional"});                     
            antennaHeightTextField = getTextField(16);           
            terrainHeightTextField = getTextField(16);
            headingTextField = getTextField(16);
            fieldOfViewAngleTextField = getTextField(16);           
            gainTextField = getTextField(16);
            
            additionalInformationJTextArea = getTextArea("");

            cancelButton = getButton("Cancel", 80);            
            planChangesButton = getButton("Plan changes", 120);    
            deleteOperativeButton = getButton("Delete operative station", 200);   
            makeOperativeButton = getButton("Make operative", 140);    
            savePlansButton = getButton("Save plans", 120);    
            deletePlansButton = getButton("Delete plans", 120);                
            acceptProposalButton = getButton("Accept proposal", 120);                                      
         
            notYetImplementedPanel = new JPanel();
            notYetImplementedPanel.add(new JLabel("Not yet implemented!"));            
         
            loadStation(selectedStationIndex);
            
            if (data == null || (data != null && data.getStations() == null) ||
                    (data != null && data.getStations() != null && data.getStations().length == 0)) {
                dialog = new JDialog(eavdamMenu.getOpenMapFrame(), "No stations available", true);
                JPanel panel = new JPanel();
                panel.add(new JLabel("There are no stations. Please, add at least one to be able to edit them."));
                dialog.getContentPane().add(panel);
                
            } else {
                JPanel panel = new JPanel();
                panel.setLayout(new GridBagLayout());                  

                JPanel p1 = new JPanel(new GridBagLayout());
                p1.setBorder(BorderFactory.createTitledBorder("Select dataset"));
                GridBagConstraints c = new GridBagConstraints();
                c.insets = new Insets(5,5,5,5);
                c.gridx = 0;
                c.gridy = 0;                  
                c.anchor = GridBagConstraints.LINE_START;     
                c.weightx = 0.5;
                p1.add(selectDatasetComboBox, c);
                c.gridx = 1;
                c.anchor = GridBagConstraints.LINE_END;   
                c.weightx = 0;
                p1.add(deleteSimulationButton, c);
                c.gridx = 2;
                p1.add(newSimulationTextField, c);
                c.gridx = 3;
                p1.add(addNewSimulationButton, c);
                                              
                c.gridx = 0; 
                c.weightx = 0.5;
                c.anchor = GridBagConstraints.FIRST_LINE_START;
                c.fill = GridBagConstraints.HORIZONTAL;
                panel.add(p1, c);                                                             
                
                selectStationPanel = new JPanel(new GridBagLayout());
                selectStationPanel.setBorder(BorderFactory.createTitledBorder("Select station"));                
                c.gridx = 0;
                c.gridy = 0;                  
                c.fill = GridBagConstraints.NONE;
                selectStationPanel.add(selectStationComboBox, c);    
                c.gridx = 0;
                c.gridy = 1;
                c.anchor = GridBagConstraints.FIRST_LINE_START;
                c.fill = GridBagConstraints.HORIZONTAL;
                panel.add(selectStationPanel, c);

                tabbedPane = new JTabbedPane();
                tabbedPane.addTab("Operative", null, new JPanel(), "Operative");
                tabbedPane.addTab("Planned", null, new JPanel(), "Planned");
                tabbedPane.addTab("1. proposal", null, new JPanel(), "1. proposal");  // testing
                tabbedPane.addTab("2. proposal", null, new JPanel(), "2. proposal");  // testing
                tabbedPane.setSelectedIndex(1);
                tabbedPane.addChangeListener(this);
                tabbedPane.setSelectedIndex(0);
                c.gridy = 2;
                panel.add(tabbedPane, c);
                
                c.gridy = 3;
                panel.add(notYetImplementedPanel, c);
                notYetImplementedPanel.setVisible(false);

                JPanel contentPanel = new JPanel();
                contentPanel.add(panel, BorderLayout.NORTH);
                dialog.getContentPane().add(contentPanel);
            }
            
            int frameWidth = 920;
            int frameHeight = 660;
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            dialog.setBounds((int) screenSize.getWidth()/2 - frameWidth/2,
                (int) screenSize.getHeight()/2 - frameHeight/2, frameWidth, frameHeight);
            dialog.setVisible(true);
        
        } else if (e.getSource() == selectDatasetComboBox) {
            
            if (((String) selectDatasetComboBox.getSelectedItem()).startsWith("Own active stations")) {
                if (selectStationPanel != null && tabbedPane != null && deleteSimulationButton != null) {
                    notYetImplementedPanel.setVisible(false);
                    selectStationPanel.setVisible(true);
                    tabbedPane.setVisible(true);
                    deleteSimulationButton.setVisible(false);
                }
            } else if (((String) selectDatasetComboBox.getSelectedItem()).startsWith("Simulation")) {
                if (selectStationPanel != null && tabbedPane != null && deleteSimulationButton != null) {
                    selectStationPanel.setVisible(false);  // simulations not yet implemented
                    tabbedPane.setVisible(false);
                    deleteSimulationButton.setVisible(true);
                    notYetImplementedPanel.setVisible(true);
                }
            } else if (((String) selectDatasetComboBox.getSelectedItem()).startsWith("Stations of organisation")) {
                if (selectStationPanel != null && tabbedPane != null && deleteSimulationButton != null) {
                    selectStationPanel.setVisible(false);  // stations of other organisations not yet implemented
                    tabbedPane.setVisible(false);
                    deleteSimulationButton.setVisible(false);
                    notYetImplementedPanel.setVisible(true);
                }
            }       
        
        } else if (deleteSimulationButton != null && e.getSource() == deleteSimulationButton) {            
            JOptionPane.showMessageDialog(dialog, "Not yet implemented!");

        } else if (addNewSimulationButton != null && e.getSource() == addNewSimulationButton) {            
            JOptionPane.showMessageDialog(dialog, "Not yet implemented!");

        } else if (planChangesButton != null && e.getSource() == planChangesButton) {            
            JOptionPane.showMessageDialog(dialog, "Not yet implemented!");

        } else if (makeOperativeButton != null && e.getSource() == makeOperativeButton) {            
            JOptionPane.showMessageDialog(dialog, "Not yet implemented!");            
                
        } else if (savePlansButton != null && e.getSource() == savePlansButton) {
         
            boolean success = saveStation(selectedStationIndex);

            if (success) {
                selectStationComboBox.removeActionListener(this);
                selectStationComboBox.removeItemAt(selectedStationIndex);
                if (selectedStationIndex < selectStationComboBox.getItemCount()) {
                    selectStationComboBox.insertItemAt(stationNameTextField.getText(), selectedStationIndex);
                } else {
                    selectStationComboBox.addItem(stationNameTextField.getText());
                }
                selectStationComboBox.setSelectedItem(stationNameTextField.getText());
                savePlansButton.setEnabled(false);;
                selectStationComboBox.addActionListener(this);
                eavdamMenu.getStationLayer().updateStations();
            }
            
        } else if ((deleteOperativeButton != null && e.getSource() == deleteOperativeButton) ||
                (deletePlansButton != null && e.getSource() == deletePlansButton)) {
            int response = JOptionPane.showConfirmDialog(dialog, "Are you sure you want to delete the current station?", "Confirm action", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                noListeners = true;
                deleteStation(selectedStationIndex);
                selectStationComboBox.removeItemAt(selectedStationIndex);
                if (selectStationComboBox.getItemCount() == 0) {
                    dialog.setVisible(false);                    
                    dialog = new JDialog(eavdamMenu.getOpenMapFrame(), "No stations available", true);
                    JPanel panel = new JPanel();
                    panel.add(new JLabel("There are no stations. Please, add at least one to be able to edit them."));
                    dialog.getContentPane().add(panel);
                    int frameWidth = 620;
                    int frameHeight = 770;
                    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                    dialog.setBounds((int) screenSize.getWidth()/2 - frameWidth/2,
                        (int) screenSize.getHeight()/2 - frameHeight/2, frameWidth, frameHeight);
                    dialog.setVisible(true);
                } else {
                    selectedStationIndex = 0;
                    loadStation(selectedStationIndex);
                    savePlansButton.setEnabled(false);
                }
                noListeners = false;
                eavdamMenu.getStationLayer().updateStations();
            } else if (response == JOptionPane.NO_OPTION) {                        
                // do nothing
            }    
        } else if (cancelButton != null && e.getSource() == cancelButton) {
            if (savePlansButton.isEnabled()) {
                int response = JOptionPane.showConfirmDialog(dialog,
                    "Do you want to save the changes made to the current station?",
                    "Confirm action", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    boolean success = saveStation(selectedStationIndex);                    
                    if (success) {
                        eavdamMenu.getStationLayer().updateStations();
                        dialog.dispose();
                    }
                } else if (response == JOptionPane.NO_OPTION) {                        
                    dialog.dispose();
                }
            } else {       
                dialog.dispose();                  
            }        
        } else if (!noListeners && selectStationComboBox != null && e.getSource() == selectStationComboBox) {
            if (selectStationComboBox.getSelectedIndex() != selectedStationIndex) {
                noListeners = true;
                if (((String) selectDatasetComboBox.getSelectedItem()).startsWith("Own active stations") &&
                        tabbedPane.getSelectedIndex() == 1) {
                    if (savePlansButton.isEnabled()) {
                        int response = JOptionPane.showConfirmDialog(dialog,
                            "Do you want to save the changes made to the current planned station?",
                            "Confirm action", JOptionPane.YES_NO_CANCEL_OPTION);
                        if (response == JOptionPane.YES_OPTION) {
                            boolean success = saveStation(selectedStationIndex);
                            if (success) {
                                selectedStationIndex = selectStationComboBox.getSelectedIndex();
                                loadStation(selectedStationIndex);
                                updateView();
                                savePlansButton.setEnabled(false);
                            }
                        } else if (response == JOptionPane.NO_OPTION) {                        
                            selectedStationIndex = selectStationComboBox.getSelectedIndex();
                            loadStation(selectedStationIndex);
                            updateView();
                            savePlansButton.setEnabled(false); 
                        } else if (response == JOptionPane.CANCEL_OPTION) {
                            // do nothing
                        }                    
                    } else {
                        selectedStationIndex = selectStationComboBox.getSelectedIndex();
                        loadStation(selectedStationIndex);                    
                        updateView();
                    }
                } else {
                    selectedStationIndex = selectStationComboBox.getSelectedIndex();
                    loadStation(selectedStationIndex);                     
                    updateView();
                }
                noListeners = false;
            }                  
        
        } else if (!noListeners && antennaTypeComboBox != null && e.getSource() == antennaTypeComboBox) {
            if (((String) selectDatasetComboBox.getSelectedItem()).startsWith("Own active stations") &&
                    tabbedPane.getSelectedIndex() == 1) {
                savePlansButton.setEnabled(true);
                updateAntennaTypeComboBox();
            }
        
        } else if (savePlansButton != null && tabbedPane != null && selectDatasetComboBox != null) {            
            if (((String) selectDatasetComboBox.getSelectedItem()).startsWith("Own active stations") &&
                    tabbedPane.getSelectedIndex() == 1) {
                if (isChanged(selectedStationIndex)) {
                    savePlansButton.setEnabled(true);
                } else {
                    savePlansButton.setEnabled(false);
                }
            }
        }
    }
    
    private JButton getButton(String title, int width) {
        JButton b = new JButton(title, null);        
        b.setVerticalTextPosition(AbstractButton.BOTTOM);
        b.setHorizontalTextPosition(AbstractButton.CENTER);
        b.setPreferredSize(new Dimension(width, 20));
        b.setMaximumSize(new Dimension(width, 20));
        b.addActionListener(this);     
        return b;
    }
    
    private JComboBox getComboBox(String[] components) {
        JComboBox cb = new JComboBox();
        if (components != null) {
            for (String c : components) {
                cb.addItem(c);
            }
        }
        cb.addActionListener(this);
        return cb;
    }
    
    private JTextField getTextField(int width) {
        JTextField tf = new JTextField(width);
        tf.getDocument().addDocumentListener(this);
        return tf;
    }
    
    private JTextArea getTextArea(String contents) {
        JTextArea ta = new JTextArea("");
        ta.getDocument().addDocumentListener(this);
        return ta;
    }
    
    private JComponent makeStationPanel(int status) {
        
        JPanel panel = new JPanel(new GridBagLayout());                      
    
        // adds form fields
    
        JPanel p1 = new JPanel(new GridBagLayout());
        p1.setBorder(BorderFactory.createTitledBorder("General information"));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5,5,5,5);
        c.gridx = 0;
        c.gridy = 0;                   
        c.anchor = GridBagConstraints.LINE_START;
        c.fill = GridBagConstraints.NONE;
        c.weightx = 0.5;
        p1.add(new JLabel("Station name:"), c);
        c.gridx = 1;                
        p1.add(stationNameTextField, c);                    
        c.gridx = 0;
        c.gridy = 1;                  
        p1.add(new JLabel("Station type:"), c);
        c.gridx = 1;
        p1.add(stationTypeComboBox, c);                                                                       
        c.gridx = 0;
        c.gridy = 2;                                         
        p1.add(new JLabel("Latitude (WGS84):"), c);
        c.gridx = 1;
        p1.add(latitudeTextField, c);                    
        c.gridx = 0;
        c.gridy = 3;                  
        p1.add(new JLabel("Longitude (WGS84):"), c);
        c.gridx = 1;       
        p1.add(longitudeTextField, c);        
        c.gridx = 0;
        c.gridy = 4;                  
        p1.add(new JLabel("MMSI number:"), c);
        c.gridx = 1;        
        p1.add(mmsiNumberTextField, c);
        c.gridx = 0;
        c.gridy = 5;                 
        p1.add(new JLabel("Transmission power (Watt):"), c);
        c.gridx = 1;                          
        p1.add(transmissionPowerTextField, c);

        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.fill = GridBagConstraints.HORIZONTAL;
        panel.add(p1, c);                    
          
        JPanel p2 = new JPanel(new GridBagLayout());
        p2.setBorder(BorderFactory.createTitledBorder("Antenna information"));        
        c.gridx = 0;
        c.gridy = 0;                 
        c.anchor = GridBagConstraints.LINE_START;                    
        c.fill = GridBagConstraints.NONE;
        p2.add(new JLabel("Antenna type:"), c);
        c.gridx = 1;                    
        p2.add(antennaTypeComboBox, c);
        c.gridx = 0;
        c.gridy = 1;                  
        p2.add(new JLabel("Antenna height above terrain (m):"), c);
        c.gridx = 1;                    
        p2.add(antennaHeightTextField, c);                    
        c.gridx = 0;
        c.gridy = 2;                  
        p2.add(new JLabel("Terrain height above sealevel (m):"), c);
        c.gridx = 1;                    
        p2.add(terrainHeightTextField, c); 
        c.gridx = 0;
        c.gridy = 3;
        p2.add(new JLabel("Heading (degrees - integer):"), c);
        c.gridx = 1;                    
        p2.add(headingTextField, c); 
        c.gridx = 0;
        c.gridy = 4;                  
        p2.add(new JLabel("Field of View angle (degrees - integer):"), c);
        c.gridx = 1;                    
        p2.add(fieldOfViewAngleTextField, c);                                         
        c.gridx = 0;
        c.gridy = 5;                  
        p2.add(new JLabel("Gain (dB):"), c);
        c.gridx = 1;                    
        p2.add(gainTextField, c);                       

        c.gridx = 1;
        c.gridy = 0;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.fill = GridBagConstraints.HORIZONTAL;
        panel.add(p2, c);                      
                                                 
        additionalInformationJTextArea.setLineWrap(true);
        additionalInformationJTextArea.setWrapStyleWord(true);
        JScrollPane p3 = new JScrollPane(additionalInformationJTextArea);
        p3.setBorder(BorderFactory.createTitledBorder("Additional information"));
        p3.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        p3.setPreferredSize(new Dimension(580, 90));
        p3.setMaximumSize(new Dimension(580, 90));
        
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.fill = GridBagConstraints.HORIZONTAL;
        panel.add(p3, c);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.anchor = GridBagConstraints.LINE_START;
        c.fill = GridBagConstraints.NONE;             
        if (status == OPERATIVE) {
            buttonPanel.add(planChangesButton, c);
            c.gridx = 1;
            buttonPanel.add(deleteOperativeButton, c);
            c.gridx = 2;
            buttonPanel.add(cancelButton, c);                    
        } else if (status == PLANNED) {
            buttonPanel.add(savePlansButton, c);
            c.gridx = 1;
            buttonPanel.add(makeOperativeButton, c);
            c.gridx = 2;
            buttonPanel.add(deletePlansButton, c);
            c.gridx = 3;
            buttonPanel.add(cancelButton, c);
        }
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.CENTER;                    
        panel.add(buttonPanel, c);
        
        // updates form fields' statuses (enabled or disabled)
        
        if (status == OPERATIVE) {
            stationNameTextField.setEnabled(false);
            configureDisabledTextField(stationNameTextField);
            stationTypeComboBox.setEnabled(false);                            
            configureDisabledComboBox(stationTypeComboBox);
            latitudeTextField.setEnabled(false);                            
            configureDisabledTextField(latitudeTextField);
            longitudeTextField.setEnabled(false);                             
            configureDisabledTextField(longitudeTextField);            
            mmsiNumberTextField.setEnabled(false);
            configureDisabledTextField(mmsiNumberTextField);            
            transmissionPowerTextField.setEnabled(false);  
            configureDisabledTextField(transmissionPowerTextField);
            antennaTypeComboBox.setEnabled(false);   
            configureDisabledComboBox(antennaTypeComboBox);
            antennaHeightTextField.setEnabled(false);   
            configureDisabledTextField(antennaHeightTextField); 
            terrainHeightTextField.setEnabled(false);   
            configureDisabledTextField(terrainHeightTextField); 
            headingTextField.setEnabled(false);   
            configureDisabledTextField(headingTextField); 
            fieldOfViewAngleTextField.setEnabled(false);                                                   
            configureDisabledTextField(fieldOfViewAngleTextField); 
            gainTextField.setEnabled(false); 
            configureDisabledTextField(gainTextField); 
            additionalInformationJTextArea.setEnabled(false);               
            configureDisabledTextArea(additionalInformationJTextArea);            
        } else if (status == PLANNED) {
            stationNameTextField.setEnabled(true);
            stationTypeComboBox.setEnabled(true);   
            stationTypeComboBox.setEditable(false);                                     
            latitudeTextField.setEnabled(true);                            
            longitudeTextField.setEnabled(true);                                         
            mmsiNumberTextField.setEnabled(true);           
            transmissionPowerTextField.setEnabled(true);         
            antennaTypeComboBox.setEnabled(true);   
            antennaTypeComboBox.setEditable(false); 
            antennaHeightTextField.setEnabled(true);   
            terrainHeightTextField.setEnabled(true);   
            headingTextField.setEnabled(true);   
            fieldOfViewAngleTextField.setEnabled(true);                                                   
            gainTextField.setEnabled(true); 
            additionalInformationJTextArea.setEnabled(true);                          
        }

        // updates form fields contents
                
        noListeners = true;

        if (data != null && selectedStationIndex < data.getStations().length) {
            AISFixedStationData station = data.getStations()[selectedStationIndex];

            if (station.getStationName() != null) {
                stationNameTextField.setText(station.getStationName());
            } else {
                stationNameTextField.setText("");                
            }
            if (station.getStationType() != null) {
                if (station.getStationType() == AISFixedStationType.BASESTATION) {
                    stationTypeComboBox.setSelectedIndex(0);
                } else if (station.getStationType() == AISFixedStationType.REPEATER) {
                    stationTypeComboBox.setSelectedIndex(1);
                } else if (station.getStationType() == AISFixedStationType.RECEIVER) {
                    stationTypeComboBox.setSelectedIndex(2);
                } else if (station.getStationType() == AISFixedStationType.ATON) {
                    stationTypeComboBox.setSelectedIndex(3);
                }
            } else {
                stationTypeComboBox.setSelectedIndex(0);
            }
            if (!Double.isNaN(station.getLat())) {                
                latitudeTextField.setText(String.valueOf(station.getLat()));                         
            } else {
                latitudeTextField.setText("");
            }
            if (!Double.isNaN(station.getLon())) {  
                longitudeTextField.setText(String.valueOf(station.getLon()));
            } else {
                longitudeTextField.setText("");
            }
            if (station.getMmsi() != null) {
                mmsiNumberTextField.setText(station.getMmsi());
            } else {
                mmsiNumberTextField.setText("");
            }
            if (station.getTransmissionPower() != null) {
                transmissionPowerTextField.setText(station.getTransmissionPower().toString());
            } else {
                transmissionPowerTextField.setText("");
            }
            if (station.getAntenna() != null) {
                Antenna antenna = station.getAntenna();
                if (antenna.getAntennaType() != null) {
                    if (antenna.getAntennaType() == AntennaType.OMNIDIRECTIONAL) {
                        antennaTypeComboBox.setSelectedIndex(1);
                    } else if (antenna.getAntennaType() == AntennaType.DIRECTIONAL) {
                        antennaTypeComboBox.setSelectedIndex(2);
                    }
                } else {
                    antennaTypeComboBox.setSelectedIndex(0);
                }
                if (!Double.isNaN(antenna.getAntennaHeight())) {
                    antennaHeightTextField.setText(String.valueOf(antenna.getAntennaHeight()));
                } else {
                    antennaHeightTextField.setText("");
                }
                if (!Double.isNaN(antenna.getTerrainHeight())) {
                    terrainHeightTextField.setText(String.valueOf(antenna.getTerrainHeight()));
                } else {
                    terrainHeightTextField.setText("");
                }                
                if (antenna.getHeading() != null) {
                    headingTextField.setText(antenna.getHeading().toString());
                } else {
                    headingTextField.setText("");
                }
                if (antenna.getFieldOfViewAngle() != null) {
                    fieldOfViewAngleTextField.setText(antenna.getFieldOfViewAngle().toString());
                } else {
                    fieldOfViewAngleTextField.setText("");
                }
                if (antenna.getGain() != null) {
                    gainTextField.setText(antenna.getGain().toString());
                } else {
                    gainTextField.setText("");
                }                    
            } else {           
                antennaTypeComboBox.setSelectedIndex(0);
            }
            if (station.getDescription() != null) {
                additionalInformationJTextArea.setText(station.getDescription());
            } else {
                additionalInformationJTextArea.setText("");
            }
            updateAntennaTypeComboBox();
        }
        
        noListeners = false;
        
        return panel;    
    }

    private void configureDisabledTextField(JTextField textField) {
        Color bgColor = UIManager.getColor("TextField.background");  
        textField.setBackground(bgColor);  
        Color fgColor = UIManager.getColor("TextField.foreground");  
        textField.setDisabledTextColor(fgColor);
    }
    
    private void configureDisabledComboBox(JComboBox comboBox) {
        comboBox.setEditable(true);
        ComboBoxEditor editor = comboBox.getEditor();
        JTextField temp = (JTextField) editor.getEditorComponent();
        temp.setDisabledTextColor(UIManager.getColor("ComboBox.foreground"));
        temp.setBackground(UIManager.getColor("ComboBox.background"));
        comboBox.setEnabled(false);
    }    
    
    private void configureDisabledTextArea(JTextArea textArea) {
        Color bgColor = UIManager.getColor("TextArea.background");  
        textArea.setBackground(bgColor);  
        Color fgColor = UIManager.getColor("TextArea.foreground");  
        textArea.setDisabledTextColor(fgColor);
    }

    public void changedUpdate(DocumentEvent e) {
        checkForChanges();
    }
    
    public void insertUpdate(DocumentEvent e) {
        checkForChanges();
    } 
    
    public void removeUpdate(DocumentEvent e) {
        checkForChanges();
    }    
    
    private void checkForChanges() {
        if (savePlansButton != null) {                    
            if (((String) selectDatasetComboBox.getSelectedItem()).startsWith("Own active stations") &&
                tabbedPane.getSelectedIndex() == 1) {
                if (isChanged(selectedStationIndex)) {
                    savePlansButton.setEnabled(true);
                } else {
                    savePlansButton.setEnabled(false);
                }
            }
        }
    }
    
    public void stateChanged(ChangeEvent evt) {
        updateView();
    }
    
    private void updateView() {
        int sel = tabbedPane.getSelectedIndex();    
        if (sel == 0) {  // operative
            JComponent operativePanel = makeStationPanel(OPERATIVE);            
            tabbedPane.setComponentAt(0, operativePanel);        
        } else if (sel == 1) {  // planned
            JComponent plannedPanel = makeStationPanel(PLANNED);
            tabbedPane.setComponentAt(1, plannedPanel);  
        }        
    }    

    private void updateAntennaTypeComboBox() {
        if (antennaTypeComboBox.getSelectedIndex() == 0) {  // no antenna         
            antennaHeightTextField.setText("");
            antennaHeightTextField.setEnabled(false);
            terrainHeightTextField.setText("");
            terrainHeightTextField.setEnabled(false);
            headingTextField.setText("");
            headingTextField.setEnabled(false);
            fieldOfViewAngleTextField.setText("");
            fieldOfViewAngleTextField.setEnabled(false);
            gainTextField.setText("");
            gainTextField.setEnabled(false); 
        } else if (antennaTypeComboBox.getSelectedIndex() == 1) {  // omnidirectional
            antennaHeightTextField.setEnabled(true);
            terrainHeightTextField.setEnabled(true);
            headingTextField.setText("");
            headingTextField.setEnabled(false);
            fieldOfViewAngleTextField.setText("");
            fieldOfViewAngleTextField.setEnabled(false);
            gainTextField.setText("");
            gainTextField.setEnabled(false); 
        } else if (antennaTypeComboBox.getSelectedIndex() == 2) {  // directional
            antennaHeightTextField.setEnabled(true);
            terrainHeightTextField.setEnabled(true);
            headingTextField.setEnabled(true);
            fieldOfViewAngleTextField.setEnabled(true);
            gainTextField.setEnabled(true); 
        } 
    }    

    /** 
     * Loads a station.
     *
     * @param stationIndex Index of the station to be loaded
     */    
    private void loadStation(int stationIndex) {
        
        noListeners = true;
        
        try {
            data = XMLImporter.readXML(new File(DataFileHandler.getLatestDataFileName()));
        } catch (MalformedURLException ex) {
            System.out.println(ex.getMessage());
        } catch (JAXBException ex) {
            System.out.println(ex.getMessage());
        }

        AISFixedStationData[] stations = null;
        if (data != null) {
            stations = data.getStations();
        }

        if (stations != null && stations.length > 0 && stationIndex >= stations.length) {
            return;
        }
        
        if (initiallySelectedStationName != null) {
            for (int i=0; i< stations.length; i++) {
                if (stations[i].getStationName().equals(initiallySelectedStationName)) {
                    stationIndex = i;
                    selectedStationIndex = i;
                    break;
                }
            }
            initiallySelectedStationName = null;
        }
        
        if (stationIndex < stations.length) {
            AISFixedStationData station = stations[stationIndex];
            
            selectStationComboBox.removeAllItems();
            for (int i=0; i<stations.length; i++) {                    
                AISFixedStationData temp = stations[i];
                if (temp.getStationName() != null) {
                    selectStationComboBox.addItem(temp.getStationName());
                } else {                        
                    selectStationComboBox.addItem("Undefined");
                }
            }
            selectStationComboBox.setSelectedIndex(stationIndex);            
        }
        
        noListeners = false;       
    }
    
    /**
     * Saves station's data.
     *
     * @param stationIndex  Index of the station in the stations list
     * @return True if the data was saved or false if it was not
     */
    private boolean saveStation(int stationIndex) {

        if (data == null) {
            data = new EAVDAMData();
        }
        AISFixedStationData[] stations = data.getStations();
        for (int i=0; i<stations.length; i++) {
            if (i != stationIndex && stations[i].getStationName().equals(stationNameTextField.getText().trim())) {
                JOptionPane.showMessageDialog(dialog, "A station with the given name already exists. " +
                    "Please, select another name for the station.");                 
                return false;
            }
        }
        if (latitudeTextField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(dialog, "Latitude is mandatory.");
            return false;
        } else {
            if (!latitudeTextField.getText().trim().isEmpty()) {
                try {
                    Double.parseDouble(latitudeTextField.getText().trim());                    
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dialog, "Latitude is not a valid number.");
                    return false;
                }
            }
        }            
        if (longitudeTextField.getText().trim().isEmpty()) {          
            JOptionPane.showMessageDialog(dialog, "Longitude is mandatory.");                    
            return false;
        } else {                                                        
            if (!longitudeTextField.getText().trim().isEmpty()) {
                try {
                    Double.parseDouble(longitudeTextField.getText().trim());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dialog, "Longitude is not a valid number.");
                    return false;
                }
            }
        }                    
        try {
            if (!transmissionPowerTextField.getText().trim().isEmpty()) {
                Double.parseDouble(transmissionPowerTextField.getText().trim());
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(dialog, "Transmission power is not a valid number.");
            return false;
        }   
        if (antennaTypeComboBox.getSelectedIndex() == 1 &&
                (antennaHeightTextField.getText().trim().isEmpty() ||
                terrainHeightTextField.getText().trim().isEmpty())) {
            JOptionPane.showMessageDialog(dialog, "Antenna height and terrain height must both be given.");
            return false;
        }
        if (antennaTypeComboBox.getSelectedIndex() == 2 &&
                (antennaHeightTextField.getText().trim().isEmpty() ||
                terrainHeightTextField.getText().trim().isEmpty() ||
                headingTextField.getText().trim().isEmpty() ||
                fieldOfViewAngleTextField.getText().trim().isEmpty() ||
                gainTextField.getText().trim().isEmpty())) {
            JOptionPane.showMessageDialog(dialog, "Antenna height, terrain height, heading, field of view angle and gain must all be given.");
            return false;
        }
        try {
            if (!antennaHeightTextField.getText().trim().isEmpty()) {
                Double.parseDouble(antennaHeightTextField.getText().trim());
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(dialog, "Antenna height is not a valid number.");                     
            return false;
        }  
        try {
            if (!terrainHeightTextField.getText().trim().isEmpty()) {
                Double.parseDouble(terrainHeightTextField.getText().trim());
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(dialog, "Terrain height is not a valid number.");
            return false;            
        }                                 
        try {
            if (!headingTextField.getText().trim().isEmpty()) {
                Integer.parseInt(headingTextField.getText().trim());
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(dialog, "Heading is not a valid integer.");
            return false;
        }  
        try {
            if (!fieldOfViewAngleTextField.getText().trim().isEmpty()) {
                Integer.parseInt(fieldOfViewAngleTextField.getText().trim());
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(dialog, "Field of view angle is not a valid integer.");                        
            return false;
        }      
        try {
            if (!gainTextField.getText().trim().isEmpty()) {
                Double.parseDouble(gainTextField.getText().trim());
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(dialog, "Gain is not a valid number.");
            return false;
        }                          
    
        AISFixedStationData station = new AISFixedStationData();
        if (stations != null && stations.length > 0 && stationIndex >=stations.length) {
            return false;
        } else if (stations != null && stations.length > 0) {
            station = stations[stationIndex];
        }

        try {
            station.setStationName(stationNameTextField.getText().trim());
            if (stationTypeComboBox.getSelectedIndex() == 0) {
                station.setStationType(AISFixedStationType.BASESTATION);
            } else if (stationTypeComboBox.getSelectedIndex() == 1) {
                station.setStationType(AISFixedStationType.REPEATER); 
            } else if (stationTypeComboBox.getSelectedIndex() == 2) {
                station.setStationType(AISFixedStationType.RECEIVER); 
            } else if (stationTypeComboBox.getSelectedIndex() == 3) {
                station.setStationType(AISFixedStationType.ATON); 
            }  
            station.setLat(new Double(latitudeTextField.getText().trim()).doubleValue());                                
            station.setLon(new Double(longitudeTextField.getText().trim()).doubleValue());  
            if (mmsiNumberTextField.getText().trim().isEmpty()) {
                station.setMmsi(null);
            } else {
                station.setMmsi(mmsiNumberTextField.getText().trim());
            }
            if (transmissionPowerTextField.getText().trim().isEmpty()) {
                station.setTransmissionPower(null);
            } else {
                station.setTransmissionPower(new Double(transmissionPowerTextField.getText().trim()));
            }
            Antenna antenna = station.getAntenna();
            if (antennaTypeComboBox.getSelectedIndex() == 0) {
                station.setAntenna(null);
            } else if (antennaTypeComboBox.getSelectedIndex() == 1) {
                if (antenna == null) {
                    antenna = new Antenna();
                }
                antenna.setAntennaType(AntennaType.OMNIDIRECTIONAL);                    
            } else if (antennaTypeComboBox.getSelectedIndex() == 2) {
                if (antenna == null) {
                    antenna = new Antenna();
                }
                antenna.setAntennaType(AntennaType.DIRECTIONAL);
            }
            if (antennaTypeComboBox.getSelectedIndex() == 1 ||
                    antennaTypeComboBox.getSelectedIndex() == 2) {
                if (!antennaHeightTextField.getText().trim().isEmpty()) {
                    antenna.setAntennaHeight(new Double(antennaHeightTextField.getText().trim()).doubleValue());
                }
                if (!terrainHeightTextField.getText().trim().isEmpty()) {
                    antenna.setTerrainHeight(new Double(terrainHeightTextField.getText().trim()).doubleValue());
                }
            }
            if (antennaTypeComboBox.getSelectedIndex() == 2) {
                if (headingTextField.getText().trim().isEmpty()) {                        
                    antenna.setHeading(null);
                } else {
                    antenna.setHeading(new Integer(headingTextField.getText().trim()));
                }
                if (fieldOfViewAngleTextField.getText().trim().isEmpty()) { 
                    antenna.setFieldOfViewAngle(null);
                } else {
                    antenna.setFieldOfViewAngle(new Integer(fieldOfViewAngleTextField.getText().trim()));
                }
                if (gainTextField.getText().trim().isEmpty()) {             
                    antenna.setGain(null);
                } else {
                    antenna.setGain(new Double(gainTextField.getText().trim()));
                }
            }
            if (antennaTypeComboBox.getSelectedIndex() == 1 ||
                    antennaTypeComboBox.getSelectedIndex() == 2) {
                station.setAntenna(antenna);
            }
            if (additionalInformationJTextArea.getText().trim().isEmpty()) {
                station.setDescription(null);
            } else {
                station.setDescription(additionalInformationJTextArea.getText().trim());
            }
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(dialog, e.getMessage());              
            return false;
        }
                        
        stations[stationIndex] = station;
        List<AISFixedStationData> stationList = new ArrayList<AISFixedStationData>(Arrays.asList(stations));
        data.setStations(stationList);     
        saveData(data);    
        
        return true;   
    }

    /** 
     * Deletes a station.
     *
     * @param stationIndex  Index of the station to be deleted
     */
    private void deleteStation(int stationIndex) {        
        if (data != null) {
            AISFixedStationData[] stations = data.getStations();
            if (stations != null && stationIndex < stations.length) {                
                AISFixedStationData[] result = new AISFixedStationData[stations.length-1];
                if (stationIndex > 0) {
                    System.arraycopy(stations, 0, result, 0, stationIndex);
                }
                if (stationIndex+1 < stations.length) {
                    System.arraycopy(stations, stationIndex+1, result, stationIndex, stations.length-1-stationIndex);
                }                
                List<AISFixedStationData> stationList = new ArrayList<AISFixedStationData>(Arrays.asList(result));               
                data.setStations(stationList);
                saveData(data);
            }
        }
    }
    
    /** 
     * Saves data to XML file.
     *
     * @param data  Data to be saved
     */    
    private void saveData(EAVDAMData data) {
        try {
            File file = new File("data");
            if (!file.exists()) {
                file.mkdir();
            }
            String organisationName = null;
            if (data != null) {
                EAVDAMUser user = data.getUser();
                if (user != null) {
                    organisationName = user.getOrganizationName();
                }
            }
            DataFileHandler.currentEAVDAMData = data;
            XMLExporter.writeXML(data, new File(DataFileHandler.getNewDataFileName(organisationName)));            
            DataFileHandler.deleteOldDataFiles();
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (JAXBException ex) {
            System.out.println(ex.getMessage());
        }            
    }

    /** 
     * Checks whether the form fields have changed.
     *
     * @param stationIndex Index of the station
     * @return  True if the fields have changed, false if not
     */
    private boolean isChanged(int stationIndex) {

        if (noListeners) {
            return false;
        }

        if (data == null) {
            return true;
        }

        AISFixedStationData station = new AISFixedStationData();
        AISFixedStationData[] stations = data.getStations();
                
        if (stations != null && stations.length > 0 && stationIndex >= stations.length) {
            return true;
        } else if (stations != null && stations.length > 0) {
            station = stations[stationIndex];
        }                                

        if (station.getStationName() == null && !stationNameTextField.getText().isEmpty()) {
            return true;
        }         
        if (!station.getStationName().equals(stationNameTextField.getText())) {
            return true;
        }
        if (station.getStationType() == null) {
            return true;
        }
        if (station.getStationType() == AISFixedStationType.BASESTATION && stationTypeComboBox.getSelectedIndex() != 0) {
            return true;
        }
        if (station.getStationType() == AISFixedStationType.REPEATER && stationTypeComboBox.getSelectedIndex() != 1) {
            return true;
        }
        if (station.getStationType() == AISFixedStationType.RECEIVER && stationTypeComboBox.getSelectedIndex() != 2) {
            return true;
        }
        if (station.getStationType() == AISFixedStationType.ATON && stationTypeComboBox.getSelectedIndex() != 3) {
            return true;
        }  
        try {
            if (Double.isNaN(station.getLat()) && !latitudeTextField.getText().isEmpty()) {
                return true;
            }
            if (!Double.isNaN(station.getLat()) && station.getLat() != (new Double(latitudeTextField.getText()).doubleValue())) {
                return true;
            }
        } catch (NumberFormatException ex) {
            return true;
        }
        try {
            if (Double.isNaN(station.getLon()) && !longitudeTextField.getText().isEmpty()) {
                return true;
            }
            if (!Double.isNaN(station.getLon()) && station.getLon() != (new Double(longitudeTextField.getText()).doubleValue())) {
                return true;
            }
        } catch (NumberFormatException ex) {
            return true;
        }
        if (station.getMmsi() == null && !mmsiNumberTextField.getText().isEmpty()) {
            return true;
        }         
        if (station.getMmsi() != null && !station.getMmsi().equals(mmsiNumberTextField.getText())) {
            return true;
        }
        if (station.getTransmissionPower() == null && !transmissionPowerTextField.getText().isEmpty()) {
            return true;
        }
        try {       
            if (station.getTransmissionPower() != null && !station.getTransmissionPower().equals(new Double(transmissionPowerTextField.getText()))) {
                return true;
            }
        } catch (NumberFormatException ex) {
                return true;          
        }
        if (station.getStatus() == null) {
            return true;
        }
        Antenna antenna = station.getAntenna();
        if (antenna == null) {
            antenna = new Antenna();
        }
        if (antenna.getAntennaType() == null && antennaTypeComboBox.getSelectedIndex() != 0) {
            return true;
        }
        if (antenna.getAntennaType() == AntennaType.OMNIDIRECTIONAL && antennaTypeComboBox.getSelectedIndex() != 1) {
            return true;
        }
        if (antenna.getAntennaType() == AntennaType.DIRECTIONAL && antennaTypeComboBox.getSelectedIndex() != 2) {
            return true;
        }
        if (Double.isNaN(antenna.getAntennaHeight()) && !antennaHeightTextField.getText().isEmpty()) {
            return true;
        }
        try {       
            if (antenna.getAntennaHeight() != new Double(antennaHeightTextField.getText()).doubleValue()) {
                return true;
            }
        } catch (NumberFormatException ex) {
                return true;
        }        
        if (Double.isNaN(antenna.getTerrainHeight()) && !terrainHeightTextField.getText().isEmpty()) {
            return true;
        }
        try {       
            if (antenna.getTerrainHeight() != new Double(terrainHeightTextField.getText()).doubleValue()) {
                return true;
            }
        } catch (NumberFormatException ex) {
            return true;
        }                         
        if (antenna.getAntennaType() == AntennaType.DIRECTIONAL) {
            if (antenna.getHeading() == null && !headingTextField.getText().isEmpty()) {
                return true;
            }
            try {       
                if (antenna.getHeading() != null && !antenna.getHeading().equals(new Integer(headingTextField.getText()))) {
                    return true;
                }
            } catch (NumberFormatException ex) {
                return true;
            }              
            if (antenna.getFieldOfViewAngle() == null && !fieldOfViewAngleTextField.getText().isEmpty()) {
                return true;
            }
            try {       
                if (antenna.getFieldOfViewAngle() != null && !antenna.getFieldOfViewAngle().equals(new Integer(fieldOfViewAngleTextField.getText()))) {
                    return true;
                }
            } catch (NumberFormatException ex) {
                return true;
            }         
            if (antenna.getGain() == null && !gainTextField.getText().isEmpty()) {
                return true;
            }
            try {       
                if (antenna.getGain() != null && !antenna.getGain().equals(new Integer(gainTextField.getText()))) {
                    return true;
                }
            } catch (NumberFormatException ex) {
                return true;
            }
        }
        if (station.getDescription() == null && !additionalInformationJTextArea.getText().isEmpty()) {
            return true;
        }         
        if (station.getDescription() != null && !station.getDescription().equals(additionalInformationJTextArea.getText())) {
            return true;
        }
        return false;
    }

}
