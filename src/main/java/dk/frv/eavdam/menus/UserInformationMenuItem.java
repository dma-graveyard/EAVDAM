package dk.frv.eavdam.menus;

import dk.frv.eavdam.data.Address;
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
import java.net.URL;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.xml.bind.JAXBException;

/**
 * This class represents a menu item that opens a frame where the user can edit
 * user information.
 */
public class UserInformationMenuItem extends JMenuItem {

    public static final long serialVersionUID = 3L;

    public UserInformationMenuItem(EavdamMenu eavdamMenu) {        
        super("Edit User Information");                
        addActionListener(new UserInformationActionListener(eavdamMenu));
    }

}
 
class UserInformationActionListener implements ActionListener {

    private EavdamMenu eavdamMenu;
                                                         
    private JDialog dialog;
    
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
    
    private EAVDAMData data;                   
                   
    public UserInformationActionListener(EavdamMenu eavdamMenu) {
        super();
        this.eavdamMenu = eavdamMenu;
    }
                   
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() instanceof UserInformationMenuItem) {

            dialog = new JDialog(eavdamMenu.getOpenMapFrame(), "User Information", true);
        
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

            try {
                data = XMLImporter.readXML(new File("data/eavdam_data.xml"));
            } catch (MalformedURLException ex) {
                System.out.println(ex.getMessage());
            } catch (JAXBException ex) {
                System.out.println(ex.getMessage());
            }
            
            EAVDAMUser user = null;                    
            if (data != null) {
                user = data.getUser();
            }                                   
            
            if (user != null) {
                if (user.getOrganizationName() != null) {
                    organizationNameTextField.setText(user.getOrganizationName());
                }
                if (user.getPostalAddress() != null) {
                    Address address = user.getPostalAddress();
                    if (address.getAddressline1() != null) {
                        postalAddressTextField.setText(address.getAddressline1());
                    }                            
                }
                if (user.getPostalAddress() != null) {
                    Address address = user.getPostalAddress();
                    if (address.getZip() != null) {
                        zipCodeTextField.setText(address.getZip());
                    }                            
                }
                if (user.getPostalAddress() != null) {
                    Address address = user.getPostalAddress();
                    if (address.getCountry() != null) {
                        countryComboBox.setSelectedItem(address.getCountry());
                    }                            
                }
                if (user.getPhone() != null) {
                    telephoneNumberTextField.setText(user.getPhone());
                }
                if (user.getFax() != null) {
                    faxNumberTextField.setText(user.getFax());
                }
                if (user.getWww() != null) {
                    websiteTextField.setText(user.getWww().toString());
                }
                if (user.getContact() != null) {
                    Person contact = user.getContact();
                    if (contact.getName() != null) {
                        pointOfContactNameTextField.setText(contact.getName());
                    }
                }
                if (user.getContact() != null) {
                    Person contact = user.getContact();
                    if (contact.getPhone() != null) {
                        pointOfContactPhoneTextField.setText(contact.getPhone());
                    }
                }
                if (user.getContact() != null) {
                    Person contact = user.getContact();
                    if (contact.getEmail() != null) {
                        pointOfContactEmailTextField.setText(contact.getEmail());
                    }
                }                                                                                                                                                                      
                if (user.getTechnicalContact() != null) {
                    Person technicalContact = user.getTechnicalContact();
                    if (technicalContact.getName() != null) {
                        pointOfTechnicalContactNameTextField.setText(technicalContact.getName());
                    }
                }
                if (user.getTechnicalContact() != null) {
                    Person technicalContact = user.getTechnicalContact();
                    if (technicalContact.getPhone() != null) {
                        pointOfTechnicalContactPhoneTextField.setText(technicalContact.getPhone());
                    }
                }
                if (user.getTechnicalContact() != null) {
                    Person technicalContact = user.getTechnicalContact();
                    if (technicalContact.getEmail() != null) {
                        pointOfTechnicalContactEmailTextField.setText(technicalContact.getEmail());
                    }
                }                      
                if (user.getDescription() != null) {
                    additionalInformationJTextArea.setText(user.getDescription());
                }
            }

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

            dialog.getContentPane().add(panel);
                                                                         
            int frameWidth = 640;
            int frameHeight = 480;
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            dialog.setBounds((int) screenSize.getWidth()/2 - frameWidth/2,
                (int) screenSize.getHeight()/2 - frameHeight/2, frameWidth, frameHeight);
            dialog.setVisible(true);
        
        } else if (saveButton != null && e.getSource() == saveButton) {

            // validates                    
            
            boolean errors = false;
                              
            try {
                if (!websiteTextField.getText().isEmpty()) {
                    URL temp = new URL(websiteTextField.getText());
                }
            } catch (MalformedURLException ex) {
                errors = true;
                JOptionPane.showMessageDialog(dialog, "Website address is not a valid URL.");
            }

            if (!errors) {
                 
                if (data == null) {
                    data = new EAVDAMData();
                }
    
                EAVDAMUser user = data.getUser();
                if (user == null) {
                    user = new EAVDAMUser();
                }
                
                user.setOrganizationName(organizationNameTextField.getText());
                Address address = user.getPostalAddress();
                if (address == null) {
                    address = new Address();
                }
                address.setAddressline1(postalAddressTextField.getText());
                address.setZip(zipCodeTextField.getText());
                address.setCountry((String) countryComboBox.getSelectedItem());
                user.setPostalAddress(address);
                user.setPhone(telephoneNumberTextField.getText());
                user.setFax(faxNumberTextField.getText());
                try {
                    user.setWww(new URL(websiteTextField.getText()));
                } catch (MalformedURLException ex) {}
                Person contact = user.getContact();
                if (contact == null) {
                    contact = new Person();
                }
                contact.setName(pointOfContactNameTextField.getText());
                contact.setPhone(pointOfContactPhoneTextField.getText());
                contact.setEmail(pointOfContactEmailTextField.getText());
                user.setContact(contact);
                Person technicalContact = user.getTechnicalContact();
                if (technicalContact == null) {
                    technicalContact = new Person();
                }
                technicalContact.setName(pointOfTechnicalContactNameTextField.getText());
                technicalContact.setPhone(pointOfTechnicalContactPhoneTextField.getText());
                technicalContact.setEmail(pointOfTechnicalContactEmailTextField.getText());
                user.setTechnicalContact(technicalContact);                
                user.setDescription(additionalInformationJTextArea.getText());
                
                data.setUser(user);                                
                
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
    
                dialog.dispose();
            }
            
        } else if (cancelButton != null && e.getSource() == cancelButton) {
            dialog.dispose();  
        }            
    }
        
}

