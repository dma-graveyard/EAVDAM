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
import java.awt.BorderLayout;
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
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.xml.bind.JAXBException;

/**
 * This class represents a menu item that opens a frame where the user can add
 * a new stations.
 */
public class AddStationMenuItem extends JMenuItem {

    public static final long serialVersionUID = 3L;

    public AddStationMenuItem(EavdamMenu eavdamMenu) {
        super("Add Station");       
        addActionListener(new AddStationActionListener(eavdamMenu));
    }

}
        
        
class AddStationActionListener implements ActionListener {

    private EavdamMenu eavdamMenu;

    private JDialog dialog;

    private JTextField stationNameTextField;
    private JComboBox stationTypeComboBox;
    private JTextField latitudeTextField;
    private JTextField longitudeTextField;    
    private JTextField mmsiNumberTextField;
    private JTextField transmissionPowerTextField;
    private JComboBox stationStatusComboBox;

    private JComboBox antennaTypeComboBox;
    private JTextField antennaHeightTextField;
    private JTextField terrainHeightTextField;    
    private JLabel headingLabel;
    private JTextField headingTextField;
    private JLabel fieldOfViewAngleLabel;
    private JTextField fieldOfViewAngleTextField;                    
    private JLabel gainLabel;
    private JTextField gainTextField;   
    
    private JTextArea additionalInformationJTextArea;
    
    private JButton saveButton;
    private JButton cancelButton;

    private EAVDAMData data;  
        
    public AddStationActionListener(EavdamMenu eavdamMenu) {
        this.eavdamMenu = eavdamMenu;
    }
     
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() instanceof AddStationMenuItem) {
                                    
            loadData();
                                        
            dialog = new JDialog(eavdamMenu.getOpenMapFrame(), "Add Station", true);

            stationNameTextField = new JTextField(16);
            stationTypeComboBox = new JComboBox(new String[] {"AIS Base Station", "AIS Repeater", "AIS Receiver station", "AIS AtoN station"});
            latitudeTextField = new JTextField(16);
            longitudeTextField = new JTextField(16);
            mmsiNumberTextField = new JTextField(16);
            transmissionPowerTextField = new JTextField(16);
            stationStatusComboBox = new JComboBox(new String[] {"Operative", "Inoperative"});
    
            antennaTypeComboBox = new JComboBox(new String[] {"Omnidirectional", "Directional"});
            antennaTypeComboBox.addActionListener(this);
            antennaHeightTextField = new JTextField(16);
            terrainHeightTextField = new JTextField(16);
            headingLabel = new JLabel("Heading (degrees - integer):");
            headingTextField = new JTextField(16);
            fieldOfViewAngleLabel = new JLabel("Field of View angle (degrees - integer)");
            fieldOfViewAngleTextField = new JTextField(16);           
            gainLabel = new JLabel("Gain (dB)");
            gainTextField = new JTextField(16);
            
            additionalInformationJTextArea = new JTextArea("");
            
            if (antennaTypeComboBox.getSelectedIndex() == 0) {  // omnidirectional
                headingLabel.setVisible(false);
                headingTextField.setVisible(false);
                fieldOfViewAngleLabel.setVisible(false);
                fieldOfViewAngleTextField.setVisible(false);
                gainLabel.setVisible(false);
                gainTextField.setVisible(false);                        
            } else if (antennaTypeComboBox.getSelectedIndex() == 1) {  // directional
                headingLabel.setVisible(true);
                headingTextField.setVisible(true);
                fieldOfViewAngleLabel.setVisible(true);
                fieldOfViewAngleTextField.setVisible(true);
                gainLabel.setVisible(true);
                gainTextField.setVisible(true);
            } 

            JPanel panel = new JPanel();
            panel.setLayout(new GridBagLayout());
                              
            JPanel p2 = new JPanel(new GridBagLayout());
            p2.setBorder(BorderFactory.createTitledBorder("General information"));
            GridBagConstraints c = new GridBagConstraints();
            c.insets = new Insets(5,5,5,5);
            c.gridx = 0;
            c.gridy = 0;                   
            c.anchor = GridBagConstraints.LINE_START;
            c.fill = GridBagConstraints.NONE;
            p2.add(new JLabel("Descriptive name for the station:"), c);
            c.gridx = 1;                
            p2.add(stationNameTextField, c);                    
            c.gridx = 0;
            c.gridy = 1;                  
            p2.add(new JLabel("Type of the fixed AIS station:"), c);
            c.gridx = 1;
            p2.add(stationTypeComboBox, c);                                                                       
            c.gridx = 0;
            c.gridy = 2;                                         
            p2.add(new JLabel("Latitude in decimal degrees, datum must be WGS84:"), c);
            c.gridx = 1;                    
            p2.add(latitudeTextField, c);                    
            c.gridx = 0;
            c.gridy = 3;                  
            p2.add(new JLabel("Longitude in decimal degrees, datum must be WGS84:"), c);
            c.gridx = 1;                    
            p2.add(longitudeTextField, c);        
            c.gridx = 0;
            c.gridy = 4;                  
            p2.add(new JLabel("MMSI number (optional for receivers):"), c);
            c.gridx = 1;                    
            p2.add(mmsiNumberTextField, c);
            c.gridx = 0;
            c.gridy = 5;                 
            p2.add(new JLabel("Transmission power (Watt):"), c);
            c.gridx = 1;                    
            p2.add(transmissionPowerTextField, c);
            c.gridx = 0;
            c.gridy = 6;                 
            p2.add(new JLabel("Status of the fixed AIS station:"), c);
            c.gridx = 1;                    
            p2.add(stationStatusComboBox, c);

            c.gridx = 0;
            c.gridy = 0;
            c.anchor = GridBagConstraints.FIRST_LINE_START;
            c.fill = GridBagConstraints.HORIZONTAL;
            panel.add(p2, c);                    
              
            JPanel p3 = new JPanel(new GridBagLayout());
            p3.setBorder(BorderFactory.createTitledBorder("Antenna information"));
            c.gridx = 0;
            c.gridy = 0;                 
            c.anchor = GridBagConstraints.LINE_START;                    
            c.fill = GridBagConstraints.NONE;
            p3.add(new JLabel("Antenna type:"), c);
            c.gridx = 1;                    
            p3.add(antennaTypeComboBox, c);
            c.gridx = 0;
            c.gridy = 1;                  
            p3.add(new JLabel("Antenna height above terrain (meters):"), c);
            c.gridx = 1;                    
            p3.add(antennaHeightTextField, c);                    
            c.gridx = 0;
            c.gridy = 2;                  
            p3.add(new JLabel("Terrain height above sealevel (meters):"), c);
            c.gridx = 1;                    
            p3.add(terrainHeightTextField, c); 
            c.gridx = 0;
            c.gridy = 3;
            p3.add(headingLabel, c);
            c.gridx = 1;                    
            p3.add(headingTextField, c); 
            c.gridx = 0;
            c.gridy = 4;                  
            p3.add(fieldOfViewAngleLabel, c);
            c.gridx = 1;                    
            p3.add(fieldOfViewAngleTextField, c);                                         
            c.gridx = 0;
            c.gridy = 5;                  
            p3.add(gainLabel, c);
            c.gridx = 1;                    
            p3.add(gainTextField, c);                       

            c.gridx = 0;
            c.gridy = 1;
            c.anchor = GridBagConstraints.FIRST_LINE_START;
            c.fill = GridBagConstraints.HORIZONTAL;
            panel.add(p3, c);                      
                                                     
            additionalInformationJTextArea.setLineWrap(true);
            additionalInformationJTextArea.setWrapStyleWord(true);                    
            JScrollPane p4 = new JScrollPane(additionalInformationJTextArea);
            p4.setBorder(BorderFactory.createTitledBorder("Additional information"));
            p4.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            p4.setPreferredSize(new Dimension(580, 90));
            p4.setMaximumSize(new Dimension(580, 90));
            
            c.gridx = 0;
            c.gridy = 2;
            c.anchor = GridBagConstraints.FIRST_LINE_START;
            c.fill = GridBagConstraints.HORIZONTAL;
            panel.add(p4, c);
            
            saveButton = new JButton("Save", null);        
            saveButton.setVerticalTextPosition(AbstractButton.BOTTOM);
            saveButton.setHorizontalTextPosition(AbstractButton.CENTER);
            saveButton.setPreferredSize(new Dimension(100, 20));
            saveButton.setMaximumSize(new Dimension(100, 20));
            saveButton.addActionListener(this);      
            
            cancelButton = new JButton("Cancel", null);        
            cancelButton.setVerticalTextPosition(AbstractButton.BOTTOM);
            cancelButton.setHorizontalTextPosition(AbstractButton.CENTER);
            cancelButton.setPreferredSize(new Dimension(100, 20));
            cancelButton.setMaximumSize(new Dimension(100, 20));                              
            cancelButton.addActionListener(this);

            JPanel buttonPanel = new JPanel();
            buttonPanel.add(saveButton);          
            buttonPanel.add(cancelButton);                    
            c.gridx = 0;
            c.gridy = 3;
            c.fill = GridBagConstraints.NONE;
            c.anchor = GridBagConstraints.CENTER;                    
            panel.add(buttonPanel, c);

            dialog.getContentPane().add(panel);
                                                                         
            int frameWidth = 620;
            int frameHeight = 770;
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            dialog.setBounds((int) screenSize.getWidth()/2 - frameWidth/2,
                (int) screenSize.getHeight()/2 - frameHeight/2, frameWidth, frameHeight);
            dialog.setVisible(true);
        
        } else if (saveButton != null && e.getSource() == saveButton) {         
            boolean success = saveStation();
            if (success) {
                eavdamMenu.getStationLayer().updateStations();;
                dialog.dispose();
            }
            
        } else if (cancelButton != null && e.getSource() == cancelButton) {
            int response = JOptionPane.showConfirmDialog(dialog,
                "Are you sure you want to cancel?", "Confirm action", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                dialog.dispose();
            } else if (response == JOptionPane.NO_OPTION) {                        
                // do nothing
            }
        
        } else if (antennaTypeComboBox != null && e.getSource() == antennaTypeComboBox) {
            if (antennaTypeComboBox.getSelectedIndex() == 0) {  // omnidirectional
                headingLabel.setVisible(false);
                headingTextField.setText("");
                headingTextField.setVisible(false);
                fieldOfViewAngleLabel.setVisible(false);
                fieldOfViewAngleTextField.setText("");
                fieldOfViewAngleTextField.setVisible(false);
                gainLabel.setVisible(false);
                gainTextField.setText("");
                gainTextField.setVisible(false);                        
            } else if (antennaTypeComboBox.getSelectedIndex() == 1) {  // directional
                headingLabel.setVisible(true);
                headingTextField.setVisible(true);
                fieldOfViewAngleLabel.setVisible(true);
                fieldOfViewAngleTextField.setVisible(true);
                gainLabel.setVisible(true);
                gainTextField.setVisible(true);
            }
        }   
    }    
    
    private boolean saveStation() {

        // validates                    
        
        boolean errors = false;

        if (!errors && stationNameTextField.getText().trim().isEmpty()) {
            errors = true;
            JOptionPane.showMessageDialog(dialog, "Station name is mandatory.");                                          
        }
        if (data == null) {
            data = new EAVDAMData();
        }
        AISFixedStationData[] stations = data.getStations();
        for (int i=0; i<stations.length; i++) {
            if (stations[i].getStationName().equals(stationNameTextField.getText().trim())) {
                errors = true;
                JOptionPane.showMessageDialog(dialog, "A station with the given name already exists. " +
                    "Please, select another name for the station.");                 
                break;
            }
        }
        if (!errors && latitudeTextField.getText().trim().isEmpty()) {
            errors = true;
            JOptionPane.showMessageDialog(dialog, "Latitude is mandatory.");
        } else {
            if (!errors && !latitudeTextField.getText().trim().isEmpty()) {
                try {
                    Double.parseDouble(latitudeTextField.getText().trim());                    
                } catch (NumberFormatException ex) {
                    errors = true;
                    JOptionPane.showMessageDialog(dialog, "Latitude is not a valid number.");
                }
            }
        }            
        if (!errors && longitudeTextField.getText().trim().isEmpty()) {
            errors = true;
            JOptionPane.showMessageDialog(dialog, "Longitude is mandatory.");                    
        } else {                                                        
            if (!errors && !longitudeTextField.getText().trim().isEmpty()) {
                try {
                    Double.parseDouble(longitudeTextField.getText().trim());
                } catch (NumberFormatException ex) {
                    errors = true;
                    JOptionPane.showMessageDialog(dialog, "Longitude is not a valid number.");
                }
            }
        }                    
        try {
            if (!errors && !transmissionPowerTextField.getText().trim().isEmpty()) {
                Double.parseDouble(transmissionPowerTextField.getText().trim());
            }
        } catch (NumberFormatException ex) {
            errors = true;
            JOptionPane.showMessageDialog(dialog, "Transmission power is not a valid number.");
        }   
        if (!errors && antennaTypeComboBox.getSelectedIndex() == 0 &&
                (!antennaHeightTextField.getText().trim().isEmpty() ||
                !terrainHeightTextField.getText().trim().isEmpty()) &&
                (antennaHeightTextField.getText().trim().isEmpty() ||
                terrainHeightTextField.getText().trim().isEmpty())) {
            errors = true;
            JOptionPane.showMessageDialog(dialog, "Antenna height and terrain height must both be given.");                        
        }
        if (!errors && antennaTypeComboBox.getSelectedIndex() == 1 &&
                (!antennaHeightTextField.getText().trim().isEmpty() ||
                !terrainHeightTextField.getText().trim().isEmpty() ||
                !headingTextField.getText().trim().isEmpty() ||
                !fieldOfViewAngleTextField.getText().trim().isEmpty() ||
                !gainTextField.getText().trim().isEmpty()) &&
                (antennaHeightTextField.getText().trim().isEmpty() ||
                terrainHeightTextField.getText().trim().isEmpty() ||
                headingTextField.getText().trim().isEmpty() ||
                fieldOfViewAngleTextField.getText().trim().isEmpty() ||
                gainTextField.getText().trim().isEmpty())) {
            errors = true;
            JOptionPane.showMessageDialog(dialog, "Antenna height, terrain height, heading, field of view angle and gain must all be given.");
        }
        try {
            if (!errors && !antennaHeightTextField.getText().trim().isEmpty()) {
                Double.parseDouble(antennaHeightTextField.getText().trim());
            }
        } catch (NumberFormatException ex) {
            errors = true;
            JOptionPane.showMessageDialog(dialog, "Antenna height is not a valid number.");                     
        }  
        try {
            if (!errors && !terrainHeightTextField.getText().trim().isEmpty()) {
                Double.parseDouble(terrainHeightTextField.getText().trim());
            }
        } catch (NumberFormatException ex) {
            errors = true;
            JOptionPane.showMessageDialog(dialog, "Terrain height is not a valid number.");
        }                                 
        try {
            if (!errors && !headingTextField.getText().trim().isEmpty()) {
                Integer.parseInt(headingTextField.getText().trim());
            }
        } catch (NumberFormatException ex) {
            errors = true;
            JOptionPane.showMessageDialog(dialog, "Heading is not a valid integer.");
        }  
        try {
            if (!errors && !fieldOfViewAngleTextField.getText().trim().isEmpty()) {
                Integer.parseInt(fieldOfViewAngleTextField.getText().trim());
            }
        } catch (NumberFormatException ex) {
            errors = true;
            JOptionPane.showMessageDialog(dialog, "Field of view angle is not a valid integer.");                        
        }      
        try {
            if (!errors && !gainTextField.getText().trim().isEmpty()) {
                Double.parseDouble(gainTextField.getText().trim());
            }
        } catch (NumberFormatException ex) {
            errors = true;
            JOptionPane.showMessageDialog(dialog, "Gain is not a valid number.");
        }                          
    
        if (errors) {
            return false;
        }
        
        AISFixedStationData station = new AISFixedStationData();
                          
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
        station.setMmsi(mmsiNumberTextField.getText().trim());
        if (!transmissionPowerTextField.getText().trim().isEmpty()) {
            station.setTransmissionPower(new Double(transmissionPowerTextField.getText().trim()));
        }
        if (stationStatusComboBox.getSelectedIndex() == 0) {
            station.setStatus(AISFixedStationStatus.OPERATIVE);
        } else if (stationStatusComboBox.getSelectedIndex() == 1) {
            station.setStatus(AISFixedStationStatus.INOPERATIVE);
        }
        Antenna antenna = station.getAntenna();
        if (antennaTypeComboBox.getSelectedIndex() == 0) {
            if (!antennaHeightTextField.getText().trim().isEmpty() && !terrainHeightTextField.getText().trim().isEmpty()) {
                if (antenna == null) {
                    antenna = new Antenna();
                }
                antenna.setAntennaType(AntennaType.OMNIDIRECTIONAL);                    
            }
        } else if (antennaTypeComboBox.getSelectedIndex() == 1) {
            if (!antennaHeightTextField.getText().trim().isEmpty() && !terrainHeightTextField.getText().trim().isEmpty() &&
                    !headingTextField.getText().trim().isEmpty() && !fieldOfViewAngleTextField.getText().trim().isEmpty() &&
                    !gainTextField.getText().trim().isEmpty()) {
                if (antenna == null) {
                    antenna = new Antenna();
                }
                antenna.setAntennaType(AntennaType.DIRECTIONAL);
            }
        }
        if (antenna != null && antenna.getAntennaType() != null) {
            if (!antennaHeightTextField.getText().trim().isEmpty()) {
                antenna.setAntennaHeight(new Double(antennaHeightTextField.getText().trim()).doubleValue());
            }
            if (!terrainHeightTextField.getText().trim().isEmpty()) {
                antenna.setTerrainHeight(new Double(terrainHeightTextField.getText().trim()).doubleValue());
            }
            if (antenna.getAntennaType() == AntennaType.DIRECTIONAL) {
                if (!headingTextField.getText().trim().isEmpty()) {                        
                    antenna.setHeading(new Integer(headingTextField.getText().trim()));
                }
                if (!fieldOfViewAngleTextField.getText().trim().isEmpty()) { 
                    antenna.setFieldOfViewAngle(new Integer(fieldOfViewAngleTextField.getText().trim()));
                }
                if (!gainTextField.getText().trim().isEmpty()) {             
                    antenna.setGain(new Double(gainTextField.getText().trim()));
                }
            }        
            station.setAntenna(antenna);
        }
        
        station.setDescription(additionalInformationJTextArea.getText().trim());
                        
        if (stations == null) {                        
            stations = new AISFixedStationData[1];
            stations[0] = station;
            data.setStations(new ArrayList<AISFixedStationData>(Arrays.asList(stations)));
        } else {
            ArrayList<AISFixedStationData> stationList = new ArrayList<AISFixedStationData>(Arrays.asList(stations));
            stationList.add(station);
            data.setStations(stationList);
        }                     

        saveData(data);
        
        return true;   
    }

    private void loadData() {   
        try {
            data = XMLImporter.readXML(new File("data/eavdam_data.xml"));
        } catch (MalformedURLException ex) {
            System.out.println(ex.getMessage());
        } catch (JAXBException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    private void saveData(EAVDAMData data) {
        try {
            File file = new File("data");
            if (!file.exists()) {
                file.mkdir();
            }
            XMLExporter.writeXML(data, new File("data/eavdam_data.xml"));
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (JAXBException ex) {
            System.out.println(ex.getMessage());
        }            
    }

}
