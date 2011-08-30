package dk.frv.eavdam.menus;

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
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * This class represents a menu item that opens a frame where the user can edit
 * user information.
 */
public class UserInformationMenuItem extends JMenuItem {

    public static final long serialVersionUID = 3L;

    private JFrame frame;
    
    private JTextField organizationNameTextField;
    private JTextField postalAddressTextField;
    private JTextField zipCodeTextField;
    private JComboBox countryComboBox;
    private JTextField telephoneNumberTextField;
    private JTextField faxNumberTextField;
    private JTextField websiteTextField;                    

    private JTextField pointOfContactNameTextField; 
    private JTextField pointOfContactPhoneTextField;
    private JTextField pointOfContactEmailTextField;           

    private JTextField pointOfTechnicalContactNameTextField; 
    private JTextField pointOfTechnicalContactPhoneTextField;
    private JTextField pointOfTechnicalContactEmailTextField;    
    
    private JTextArea additionalInformationJTextArea;
    
    private JButton saveButton;
    private JButton cancelButton;

    public UserInformationMenuItem() {
        
        super("Edit User Information");
        
        addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                
                if (e.getSource() instanceof UserInformationMenuItem) {
                                                
                    frame = new JFrame("User Information");
                
                    organizationNameTextField = new JTextField(16);
                    postalAddressTextField = new JTextField(16);             
                    zipCodeTextField = new JTextField(16);             
                    countryComboBox = new JComboBox
                        (new String[] {"Denmark", "Finland", "Norway" , "Sweden"});
                    telephoneNumberTextField = new JTextField(16);
                    faxNumberTextField = new JTextField(16);
                    websiteTextField = new JTextField(16);  
                    pointOfContactNameTextField = new JTextField(16);  
                    pointOfContactPhoneTextField = new JTextField(16);                                                                                                
                    pointOfContactEmailTextField = new JTextField(16);  
                    pointOfTechnicalContactNameTextField = new JTextField(16);  
                    pointOfTechnicalContactPhoneTextField = new JTextField(16);                                          
                    pointOfTechnicalContactEmailTextField = new JTextField(16); 
                    additionalInformationJTextArea = new JTextArea("");                

                    // just for testing
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

                    JPanel panel = new JPanel();
                    panel.setLayout(new GridBagLayout());                  
                    
                    JPanel p1 = new JPanel(new GridBagLayout());
                    p1.setBorder(BorderFactory.createTitledBorder("General information"));
                    GridBagConstraints c = new GridBagConstraints();
                    c.gridx = 0;
                    c.gridy = 0;                   
                    c.anchor = GridBagConstraints.LINE_START;
                    c.insets = new Insets(5,5,5,5);
                    p1.add(new JLabel("Name of organisation:"), c);
                    c.gridx = 1;                
                    p1.add(organizationNameTextField, c);
                    c.gridx = 0;
                    c.gridy = 1;                  
                    p1.add(new JLabel("Postal address:"), c);
                    c.gridx = 1;                    
                    p1.add(postalAddressTextField, c);                    
                    c.gridx = 0;
                    c.gridy = 2;                  
                    p1.add(new JLabel("Zip code:"), c);
                    c.gridx = 1;                    
                    p1.add(zipCodeTextField, c);        
                    c.gridx = 0;
                    c.gridy = 3;                  
                    p1.add(new JLabel("Country:"), c);
                    c.gridx = 1;                    
                    p1.add(countryComboBox, c);
                    c.gridx = 0;
                    c.gridy = 4;                  
                    p1.add(new JLabel("Telephone number:"), c);
                    c.gridx = 1;                    
                    p1.add(telephoneNumberTextField, c);  
                    c.gridx = 0;
                    c.gridy = 5;                  
                    p1.add(new JLabel("Fax number:"), c);
                    c.gridx = 1;                    
                    p1.add(faxNumberTextField, c);  
                    c.gridx = 0;
                    c.gridy = 6;                  
                    p1.add(new JLabel("Website:"), c);
                    c.gridx = 1;                    
                    p1.add(websiteTextField, c);  
                    
                    c.gridx = 0;
                    c.gridy = 0;
                    c.gridheight = 2;
                    c.anchor = GridBagConstraints.FIRST_LINE_START;
                    panel.add(p1, c);                    
          
                    JPanel p2 = new JPanel(new GridBagLayout());
                    p2.setBorder(BorderFactory.createTitledBorder("Point of contact"));
                    c.gridheight = 1;
                    c.gridx = 0;
                    c.gridy = 0;                   
                    c.anchor = GridBagConstraints.LINE_START;
                    p2.add(new JLabel("Name:"), c);
                    c.gridx = 1;                    
                    p2.add(pointOfContactNameTextField, c);
                    c.gridx = 0;
                    c.gridy = 1;                  
                    p2.add(new JLabel("Phone:"), c);
                    c.gridx = 1;                    
                    p2.add(pointOfContactPhoneTextField, c);                    
                    c.gridx = 0;
                    c.gridy = 2;                  
                    p2.add(new JLabel("E-mail:"), c);
                    c.gridx = 1;                    
                    p2.add(pointOfContactEmailTextField, c);
          
                    c.gridx = 1;
                    c.gridy = 0;
                    c.anchor = GridBagConstraints.FIRST_LINE_START;
                    panel.add(p2, c);                    
                                                  
                    JPanel p3 = new JPanel(new GridBagLayout());
                    p3.setBorder(BorderFactory.createTitledBorder("Point of technical contact"));
                    c.gridx = 0;
                    c.gridy = 0;                  
                    c.anchor = GridBagConstraints.LINE_START;                    
                    p3.add(new JLabel("Name:"), c);
                    c.gridx = 1;                    
                    p3.add(pointOfTechnicalContactNameTextField, c);
                    c.gridx = 0;
                    c.gridy = 1;                  
                    p3.add(new JLabel("Phone:"), c);
                    c.gridx = 1;                    
                    p3.add(pointOfTechnicalContactPhoneTextField, c);                    
                    c.gridx = 0;
                    c.gridy = 2;                  
                    p3.add(new JLabel("E-mail:"), c);
                    c.gridx = 1;                    
                    p3.add(pointOfTechnicalContactEmailTextField, c);                                        
                    
                    c.gridx = 1;
                    c.gridy = 1;
                    c.anchor = GridBagConstraints.FIRST_LINE_START;
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
                    c.gridwidth = 2;
                    c.anchor = GridBagConstraints.FIRST_LINE_START;
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
                    c.gridwidth = 2;
                    c.anchor = GridBagConstraints.CENTER;                    
                    panel.add(buttonPanel, c);

                    frame.getContentPane().add(panel);
                                                                                 
                    int frameWidth = 640;
                    int frameHeight = 480;
                    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                    frame.setBounds((int) screenSize.getWidth()/2 - frameWidth/2,
                        (int) screenSize.getHeight()/2 - frameHeight/2, frameWidth, frameHeight);
                    frame.setVisible(true);
                
                } else if (saveButton != null && e.getSource() == saveButton) {
                    
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
                    frame.dispose();
                    
                } else if (cancelButton != null && e.getSource() == cancelButton) {
                    frame.dispose();  
                }            
            }
        });

    }     

}
