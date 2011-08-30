package dk.frv.eavdam.menus;

import dk.frv.eavdam.data.Address;
import dk.frv.eavdam.data.EAVDAMData;
import dk.frv.eavdam.data.EAVDAMUser;
import dk.frv.eavdam.data.Person;
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
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
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

/**
 * This class represents a menu item that opens a frame where the user can edit
 * stations' information.
 */
public class StationInformationMenuItem extends JMenuItem {

    public static final long serialVersionUID = 3L;

    public StationInformationMenuItem(EavdamMenu eavdamMenu) {
        super("Edit Station Information");       
        addActionListener(new StationInformationActionListener(eavdamMenu));
    }

}
        
        
class StationInformationActionListener implements ActionListener {

    private EavdamMenu eavdamMenu;

    private JDialog dialog;
    
    private JComboBox selectStationComboBox;
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

    
    public StationInformationActionListener(EavdamMenu eavdamMenu) {
        this.eavdamMenu = eavdamMenu;
    }
            
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() instanceof StationInformationMenuItem) {
                                        
            dialog = new JDialog(eavdamMenu.getOpenMapFrame(), "Station Information", true);

            selectStationComboBox = new JComboBox(new String[] {"Bornholm AIS Base Station", "W24 AIS AtoN Station"});
            selectStationComboBox.addActionListener(this);
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

            // just for testing
/*
            try {
                FileInputStream fstream = new FileInputStream("data/eavdam_user.txt");
                DataInputStream in = new DataInputStream(fstream);
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String strLine;
                while ((strLine = br.readLine()) != null) {
                    if (strLine.startsWith("<1>")) {
                        int temp = strLine.indexOf("</1>");                                
                        organizationNameTextField.setText(strLine.substring(3, temp));
                    } else if (strLine.startsWith("<2>")) {
                        int temp = strLine.indexOf("</2>");                                
                        postalAddressTextField.setText(strLine.substring(3, temp));
                    } else if (strLine.startsWith("<3>")) {
                        int temp = strLine.indexOf("</3>");                                
                        zipCodeTextField.setText(strLine.substring(3, temp));
                    } else if (strLine.startsWith("<4>")) {
                        int temp = strLine.indexOf("</4>");                                
                        countryComboBox.setSelectedItem(strLine.substring(3, temp));
                    } else if (strLine.startsWith("<5>")) {
                        int temp = strLine.indexOf("</5>");                                
                        telephoneNumberTextField.setText(strLine.substring(3, temp));
                    } else if (strLine.startsWith("<6>")) {
                        int temp = strLine.indexOf("</6>");                                
                        faxNumberTextField.setText(strLine.substring(3, temp));
                    } else if (strLine.startsWith("<7>")) {
                        int temp = strLine.indexOf("</7>");                                
                        websiteTextField.setText(strLine.substring(3, temp));
                    } else if (strLine.startsWith("<8>")) {
                        int temp = strLine.indexOf("</8>");                                
                        pointOfContactNameTextField.setText(strLine.substring(3, temp));
                    } else if (strLine.startsWith("<9>")) {
                        int temp = strLine.indexOf("</9>");                                
                        pointOfContactPhoneTextField.setText(strLine.substring(3, temp));
                    } else if (strLine.startsWith("<10>")) {
                        int temp = strLine.indexOf("</10>");                                
                        pointOfContactEmailTextField.setText(strLine.substring(4, temp));
                    } else if (strLine.startsWith("<11>")) {
                        int temp = strLine.indexOf("</11>");                                
                        pointOfTechnicalContactNameTextField.setText(strLine.substring(4, temp));
                    } else if (strLine.startsWith("<12>")) {
                        int temp = strLine.indexOf("</12>");                                
                        pointOfTechnicalContactPhoneTextField.setText(strLine.substring(4, temp));
                    } else if (strLine.startsWith("<13>")) {
                        int temp = strLine.indexOf("</13>");                                
                        pointOfTechnicalContactEmailTextField.setText(strLine.substring(4, temp));
                    } else if (strLine.startsWith("<14>")) {
                        int temp = strLine.indexOf("</14>");                                
                        additionalInformationJTextArea.setText(strLine.substring(4, temp));
                    }
                }
                in.close();
            } catch (Exception ex) {}
*/

            stationNameTextField.setText((String) selectStationComboBox.getSelectedItem());

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
            
            JPanel p1 = new JPanel(new GridBagLayout());
            p1.setBorder(BorderFactory.createTitledBorder("Select station"));
            GridBagConstraints c = new GridBagConstraints();
            c.insets = new Insets(5,5,5,5);
            c.gridx = 0;
            c.gridy = 0;                  
            c.anchor = GridBagConstraints.LINE_START;     
            c.weightx = 0.5;
            p1.add(selectStationComboBox, c);

            c.gridx = 0;
            c.gridy = 0;
            c.gridheight = 1;
            c.anchor = GridBagConstraints.FIRST_LINE_START;
            c.fill = GridBagConstraints.HORIZONTAL;
            panel.add(p1, c);
                              
            JPanel p2 = new JPanel(new GridBagLayout());
            p2.setBorder(BorderFactory.createTitledBorder("General information"));
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
            c.gridy = 1;
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
            c.gridy = 2;
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
            c.gridy = 3;
            //c.gridwidth = 2;
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
            c.gridy = 4;
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
           
            // validates                    
            
            boolean errors = false;
                              
            try {
                if (!latitudeTextField.getText().isEmpty()) {
                    Double.parseDouble(latitudeTextField.getText());
                }
            } catch (NumberFormatException ex) {
                errors = true;
                JOptionPane.showMessageDialog(dialog, "Latitude is not a valid number.");
            }
            try {
                if (!errors && !longitudeTextField.getText().isEmpty()) {
                    Double.parseDouble(longitudeTextField.getText());
                }
            } catch (NumberFormatException ex) {
                errors = true;
                JOptionPane.showMessageDialog(dialog, "Longitude is not a valid number.");
            }                    
            try {
                if (!errors && !transmissionPowerTextField.getText().isEmpty()) {
                    Double.parseDouble(transmissionPowerTextField.getText());
                }
            } catch (NumberFormatException ex) {
                errors = true;
                JOptionPane.showMessageDialog(dialog, "Transmission power is not a valid number.");
            }   
            try {
                if (!errors && !antennaHeightTextField.getText().isEmpty()) {
                    Double.parseDouble(antennaHeightTextField.getText());
                }
            } catch (NumberFormatException ex) {
                errors = true;
                JOptionPane.showMessageDialog(dialog, "Antenna height is not a valid number.");                     
            }  
            try {
                if (!errors && !terrainHeightTextField.getText().isEmpty()) {
                    Double.parseDouble(terrainHeightTextField.getText());
                }
            } catch (NumberFormatException ex) {
                errors = true;
                JOptionPane.showMessageDialog(dialog, "Terrain height is not a valid number.");
            }                                 
            try {
                if (!errors && !headingTextField.getText().isEmpty()) {
                    Integer.parseInt(headingTextField.getText());
                }
            } catch (NumberFormatException ex) {
                errors = true;
                JOptionPane.showMessageDialog(dialog, "Heading is not a valid integer.");
            }  
            try {
                if (!errors && !fieldOfViewAngleTextField.getText().isEmpty()) {
                    Integer.parseInt(fieldOfViewAngleTextField.getText());
                }
            } catch (NumberFormatException ex) {
                errors = true;
                JOptionPane.showMessageDialog(dialog, "Field of view angle is not a valid integer.");                        
            }      
            try {
                if (!errors && !gainTextField.getText().isEmpty()) {
                    Double.parseDouble(gainTextField.getText());
                }
            } catch (NumberFormatException ex) {
                errors = true;
                JOptionPane.showMessageDialog(dialog, "Gain is not a valid number.");
            }                          

            if (!errors) {
            
                /*
                try {
                    
                    if (!new File("data").exists()) {
                        new File("data").mkdir();
                    }
                    // just for testing
                    FileWriter fstream = new FileWriter("data/eavdam_user.txt");
                    BufferedWriter out = new BufferedWriter(fstream);
                    out.write("<1>" + organizationNameTextField.getText() + "</1>\n");
                    out.write("<2>" + postalAddressTextField.getText() + "</2>\n");
                    out.write("<3>" + zipCodeTextField.getText() + "</3>\n");
                    out.write("<4>" + (String) countryComboBox.getSelectedItem() + "</4>\n");
                    out.write("<5>" + telephoneNumberTextField.getText() + "</5>\n");
                    out.write("<6>" + faxNumberTextField.getText() + "</6>\n");
                    out.write("<7>" + websiteTextField.getText() + "</7>\n");
                    out.write("<8>" + pointOfContactNameTextField.getText() + "</8>\n");
                    out.write("<9>" + pointOfContactPhoneTextField.getText() + "</9>\n");
                    out.write("<10>" + pointOfContactEmailTextField.getText() + "</10>\n");
                    out.write("<11>" + pointOfTechnicalContactNameTextField.getText() + "</11>\n");
                    out.write("<12>" + pointOfTechnicalContactPhoneTextField.getText() + "</12>\n");
                    out.write("<13>" + pointOfTechnicalContactEmailTextField.getText() + "</13>\n");                                                                                                                                                                                                                                                                                                
                    out.write("<14>" + additionalInformationJTextArea.getText() + "</14>\n");
                    out.close();
                } catch (Exception ex) {
                    System.err.println("Error: " + ex.getMessage());
                }
                */
                dialog.dispose();
            }
        } else if (cancelButton != null && e.getSource() == cancelButton) {
            dialog.dispose();                  
        
        } else if (selectStationComboBox != null && e.getSource() == selectStationComboBox) {
            // ask whether to save current data if not saved
            // load selected station's data and fill the fields with it
            stationNameTextField.setText((String) selectStationComboBox.getSelectedItem());
        
        } else if (antennaTypeComboBox != null && e.getSource() == antennaTypeComboBox) {
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
        }
    }  

}
