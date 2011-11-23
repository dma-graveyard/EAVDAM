package dk.frv.eavdam.menus;

import dk.frv.eavdam.data.EAVDAMUser;
import dk.frv.eavdam.utils.CountryHandler;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ContactInformationDialog extends JDialog {

    public static final long serialVersionUID = 1L;

	public static int WINDOW_WIDTH = 800;
	public static int WINDOW_HEIGHT = 480;		
	
	public ContactInformationDialog(StationInformationMenuItem menuItem, EAVDAMUser user) {

		super(menuItem.getEavdamMenu().getOpenMapFrame(), "Contact information", true);

		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());

		JPanel p1 = new JPanel(new GridBagLayout());
		p1.setBorder(BorderFactory.createTitledBorder("General information"));
		GridBagConstraints c = new GridBagConstraints();
		c = menuItem.updateGBC(c, 0, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(5,5,5,5));						            
		p1.add(new JLabel("Name of organisation:"), c);
		c = menuItem.updateGBC(c, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(5,5,5,5));							
		JTextField organizationNameTextField = new JTextField(26);
		if (user.getOrganizationName() != null) {
			organizationNameTextField.setText(user.getOrganizationName());		
		}
		organizationNameTextField.setEnabled(false);
		menuItem.configureDisabledTextField(organizationNameTextField);			
		p1.add(organizationNameTextField, c);		
		c = menuItem.updateGBC(c, 0, 1, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(5,5,5,5));	                						
		p1.add(new JLabel("Postal address:"), c);
		c = menuItem.updateGBC(c, 1, 1, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(5,5,5,5));	                  
		JTextField postalAddressTextField = new JTextField(26);
		if (user.getPostalAddress() != null && user.getPostalAddress().getAddressline1() != null) {
			postalAddressTextField.setText(user.getPostalAddress().getAddressline1());			
		}
		postalAddressTextField.setEnabled(false);			
		menuItem.configureDisabledTextField(postalAddressTextField);		
		p1.add(postalAddressTextField, c);								
		c = menuItem.updateGBC(c, 0, 2, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(5,5,5,5));	  						
		p1.add(new JLabel("Zip code:"), c);
		c = menuItem.updateGBC(c, 1, 2, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(5,5,5,5));	                  
		JTextField zipCodeTextField = new JTextField(26);
		if (user.getPostalAddress() != null && user.getPostalAddress().getZip() != null) {
			zipCodeTextField.setText(user.getPostalAddress().getZip());
		}
		zipCodeTextField.setEnabled(false);			
		menuItem.configureDisabledTextField(zipCodeTextField);		
		p1.add(zipCodeTextField, c);
		c = menuItem.updateGBC(c, 0, 3, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(5,5,5,5));	 
		p1.add(new JLabel("City:"), c);
		c = menuItem.updateGBC(c, 1, 3, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(5,5,5,5));	                  
		JTextField cityTextField = new JTextField(26);
		if (user.getPostalAddress() != null && user.getPostalAddress().getCity() != null) {
			cityTextField.setText(user.getPostalAddress().getCity());
		}
		cityTextField.setEnabled(false);				
		menuItem.configureDisabledTextField(cityTextField);		
		p1.add(cityTextField, c);
		c = menuItem.updateGBC(c, 0, 4, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(5,5,5,5));	 
		p1.add(new JLabel("Country:"), c);
		c = menuItem.updateGBC(c, 1, 4, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(5,5,5,5));	                  
		JComboBox countryComboBox = new JComboBox();
		List<String> countries = CountryHandler.getCountries();
		if (countries != null) {
			for (String country : countries) {
				countryComboBox.addItem(country);
			}
		}	
		if (user.getCountryID() != null) {
			countryComboBox.setSelectedItem(CountryHandler.getCountryName(user.getCountryID()));
		}
		countryComboBox.setEnabled(false);				
		menuItem.configureDisabledComboBox(countryComboBox);		
		p1.add(countryComboBox, c);									
		c = menuItem.updateGBC(c, 0, 5, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(5,5,5,5));			
		p1.add(new JLabel("Telephone number:"), c);
		c = menuItem.updateGBC(c, 1, 5, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(5,5,5,5));	                  
		JTextField telephoneNumberTextField = new JTextField(26);
		if (user.getPhone() != null) {
			telephoneNumberTextField.setText(user.getPhone());
		}	
		telephoneNumberTextField.setEnabled(false);			
		menuItem.configureDisabledTextField(telephoneNumberTextField);
		p1.add(telephoneNumberTextField, c);		
		c = menuItem.updateGBC(c, 0, 6, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(5,5,5,5));			
		p1.add(new JLabel("Fax number:"), c);
		c = menuItem.updateGBC(c, 1, 6, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(5,5,5,5));	                  
		JTextField faxNumberTextField = new JTextField(26);
		if (user.getFax() != null) {
			faxNumberTextField.setText(user.getFax());
		}	 
		faxNumberTextField.setEnabled(false);			
		menuItem.configureDisabledTextField(faxNumberTextField);
		p1.add(faxNumberTextField, c);		
		c = menuItem.updateGBC(c, 0, 7, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(5,5,5,5));			
		p1.add(new JLabel("Website:"), c);
		c = menuItem.updateGBC(c, 1, 7, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(5,5,5,5));	                  
		JTextField websiteTextField = new JTextField(26);
		if (user.getWww() != null) {
			websiteTextField.setText(user.getWww().toString());
		}	 
		websiteTextField.setEnabled(false);				
		menuItem.configureDisabledTextField(websiteTextField);
		p1.add(websiteTextField, c);		
		c = menuItem.updateGBC(c, 0, 0, 0, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE, new Insets(5,5,5,5));	
        c.gridheight = 2;
        panel.add(p1, c);                    
  
        JPanel p2 = new JPanel(new GridBagLayout());
        p2.setBorder(BorderFactory.createTitledBorder("Point of contact"));
		c = menuItem.updateGBC(c, 0, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(5,5,5,5));
		c.gridheight = 1;
		p2.add(new JLabel("Name:"), c);
		c = menuItem.updateGBC(c, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(5,5,5,5));	                  
		JTextField pointOfContactNameTextField = new JTextField(16);
		if (user.getContact() != null && user.getContact().getName() != null) {
			pointOfContactNameTextField.setText(user.getContact().getName());
		}		
		pointOfContactNameTextField.setEnabled(false);			
		menuItem.configureDisabledTextField(pointOfContactNameTextField);
		p2.add(pointOfContactNameTextField, c);		
		c = menuItem.updateGBC(c, 0, 1, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(5,5,5,5));
		p2.add(new JLabel("Phone:"), c);
		c = menuItem.updateGBC(c, 1, 1, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(5,5,5,5));	                  
		JTextField pointOfContactPhoneTextField = new JTextField(16);
		if (user.getContact() != null && user.getContact().getPhone() != null) {
			pointOfContactPhoneTextField.setText(user.getContact().getPhone());
		}	
		pointOfContactPhoneTextField.setEnabled(false);			
		menuItem.configureDisabledTextField(pointOfContactPhoneTextField);
		p2.add(pointOfContactPhoneTextField, c);		
		c = menuItem.updateGBC(c, 0, 2, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(5,5,5,5));
		p2.add(new JLabel("E-mail:"), c);
		c = menuItem.updateGBC(c, 1, 2, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(5,5,5,5));	                  
		JTextField pointOfContactEmailTextField = new JTextField(16);
		if (user.getContact() != null && user.getContact().getEmail() != null) {
			pointOfContactEmailTextField.setText(user.getContact().getEmail());
		}	
		pointOfContactEmailTextField.setEnabled(false);			
		menuItem.configureDisabledTextField(pointOfContactEmailTextField);
		p2.add(pointOfContactEmailTextField, c);		
		c = menuItem.updateGBC(c, 1, 0, 0, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE, new Insets(5,5,5,5));	
        panel.add(p2, c);   
  
        JPanel p3 = new JPanel(new GridBagLayout());
        p3.setBorder(BorderFactory.createTitledBorder("Point of technical contact"));
		c = menuItem.updateGBC(c, 0, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(5,5,5,5));
		c.gridheight = 1;
		p3.add(new JLabel("Name:"), c);
		c = menuItem.updateGBC(c, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(5,5,5,5));	                  
		JTextField pointOfTechnicalContactNameTextField = new JTextField(16);
		if (user.getTechnicalContact() != null && user.getTechnicalContact().getName() != null) {
			pointOfTechnicalContactNameTextField.setText(user.getTechnicalContact().getName());
		}		
		pointOfTechnicalContactNameTextField.setEnabled(false);			
		menuItem.configureDisabledTextField(pointOfTechnicalContactNameTextField);
		p3.add(pointOfTechnicalContactNameTextField, c);		
		c = menuItem.updateGBC(c, 0, 1, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(5,5,5,5));
		p3.add(new JLabel("Phone:"), c);
		c = menuItem.updateGBC(c, 1, 1, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(5,5,5,5));	                  
		JTextField pointOfTechnicalContactPhoneTextField = new JTextField(16);
		if (user.getTechnicalContact() != null && user.getTechnicalContact().getPhone() != null) {
			pointOfTechnicalContactPhoneTextField.setText(user.getTechnicalContact().getPhone());
		}	
		pointOfTechnicalContactPhoneTextField.setEnabled(false);			
		menuItem.configureDisabledTextField(pointOfTechnicalContactPhoneTextField);
		p3.add(pointOfTechnicalContactPhoneTextField, c);		
		c = menuItem.updateGBC(c, 0, 2, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(5,5,5,5));
		p3.add(new JLabel("E-mail:"), c);
		c = menuItem.updateGBC(c, 1, 2, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(5,5,5,5));	                  
		JTextField pointOfTechnicalContactEmailTextField = new JTextField(16);
		if (user.getTechnicalContact() != null && user.getTechnicalContact().getEmail() != null) {
			pointOfTechnicalContactEmailTextField.setText(user.getTechnicalContact().getEmail());
		}	
		pointOfTechnicalContactEmailTextField.setEnabled(false);			
		menuItem.configureDisabledTextField(pointOfTechnicalContactEmailTextField);
		p3.add(pointOfTechnicalContactEmailTextField, c);		
		c = menuItem.updateGBC(c, 1, 1, 0, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE, new Insets(5,5,5,5));	
        panel.add(p3, c);                      
            
        JTextArea additionalInformationJTextArea = new JTextArea();
		if (user.getDescription() != null) {
			additionalInformationJTextArea.setText(user.getDescription());
		}
		additionalInformationJTextArea.setLineWrap(true);
        additionalInformationJTextArea.setWrapStyleWord(true);    
		additionalInformationJTextArea.setEnabled(false);
		menuItem.configureDisabledTextArea(additionalInformationJTextArea);
        JScrollPane p4 = new JScrollPane(additionalInformationJTextArea);
        p4.setBorder(BorderFactory.createTitledBorder("Additional information"));
        p4.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        p4.setPreferredSize(new Dimension(580, 90));
        p4.setMaximumSize(new Dimension(580, 90));            
		c = menuItem.updateGBC(c, 0, 2, 0, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE, new Insets(5,5,5,5));	
        c.gridwidth = 2;		
        panel.add(p4, c);      
				
        getContentPane().add(panel);
		pack();
	}
	
}