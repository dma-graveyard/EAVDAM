package dk.frv.eavdam.data;

import java.util.List;

import org.w3c.dom.Element;

/**
 * A class for storing EAVDAM user specific data.
 * 
 * @author ttesei
 * @version 1.0
 * @created 26-elo-2011 13:27:25
 */
public class EAVDAMUser {

	private String organizationName;
	/**
	 * E.g. DK for Denmark
	 */
	private String countryID;
	private String phone;
	private String fax;
	private java.net.URL www;
	private String description;
	private Person contact;
	private Person technicalContact;
	private Address visitingAddress;
	private Address postalAddress;
	/**
	 * Anything can go in here. The schema allows unknown XML content in the
	 * end.
	 */
	private List<Element> anything;

	private int userDBID;


	public EAVDAMUser() {
	}
	
	public int getUserDBID() {
		return userDBID;
	}

	public void setUserDBID(int userDBID) {
		this.userDBID = userDBID;
	}
	
	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		if (organizationName == null || organizationName.trim().length() == 0) {
			throw new IllegalArgumentException(
					"Organization name must be given");
		}
		this.organizationName = organizationName.trim();
	}

	public String getCountryID() {
		return countryID;
	}

	public void setCountryID(String countryID) {
		if (countryID == null || !countryID.matches("\\p{Lu}\\p{Lu}")) {
			throw new IllegalArgumentException(
					"Country ID of two capital letters must be given");
		}
		this.countryID = countryID;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		if (phone != null && phone.length() > 0 && !phone.matches("\\+[0-9 -]+")) {
			throw new IllegalArgumentException(
					"Phone number invalid, must start with + followed by digits, space and dashes allowed.");
		}
		if(phone != null && !phone.equals("+0"))
			this.phone = phone;
		else
			this.phone = "";
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		if (fax != null && fax.length() > 0 && !fax.matches("\\+[0-9 -]+")) {
			throw new IllegalArgumentException(
					"Fax number invalid, must start with + followed by digits, space and dashes allowed.");
		}
		
		if(fax != null && !fax.equals("+0"))
			this.fax = fax;
		else
			this.fax = "";
	}

	public java.net.URL getWww() {
		return www;
	}

	public void setWww(java.net.URL www) {
		this.www = www;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		if (description != null) {
			this.description = description.trim();
		} else {
			this.description = null;
		}
	}

	public Person getContact() {
		return contact;
	}

	public void setContact(Person contact) {
		this.contact = contact;
	}

	public Person getTechnicalContact() {
		return technicalContact;
	}

	public void setTechnicalContact(Person technicalContact) {
		this.technicalContact = technicalContact;
	}

	public Address getVisitingAddress() {
		return visitingAddress;
	}

	public void setVisitingAddress(Address visitingAddress) {
		this.visitingAddress = visitingAddress;
	}

	public Address getPostalAddress() {
		return postalAddress;
	}

	public void setPostalAddress(Address postalAddress) {
		this.postalAddress = postalAddress;
	}

	public List<Element> getAnything() {
		return anything;
	}

	public void setAnything(List<Element> anything) {
		this.anything = anything;
	}

	@Override
	public String toString() {
		return "EAVDAMUser [organizationName=" + organizationName
				+ ", countryID=" + countryID + ", phone=" + phone + ", fax="
				+ fax + ", www=" + www + ", description=" + description
				+ ", contact=" + contact + ", technicalContact="
				+ technicalContact + ", visitingAddress=" + visitingAddress
				+ ", postalAddress=" + postalAddress + ", anything=" + anything
				+ "]";
	}
}