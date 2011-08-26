package dk.frv.eavdam.data;

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
	 * Anything can go in here...
	 */
	private String freeText;

	public EAVDAMUser() {
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getCountryID() {
		return countryID;
	}

	public void setCountryID(String countryID) {
		this.countryID = countryID;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
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
		this.description = description;
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

	public String getFreeText() {
		return freeText;
	}

	public void setFreeText(String freeText) {
		this.freeText = freeText;
	}

	@Override
	public String toString() {
		return "EAVDAMUser [organizationName=" + organizationName
				+ ", countryID=" + countryID + ", phone=" + phone + ", fax="
				+ fax + ", www=" + www + ", description=" + description
				+ ", contact=" + contact + ", technicalContact="
				+ technicalContact + ", visitingAddress=" + visitingAddress
				+ ", postalAddress=" + postalAddress + ", freeText=" + freeText
				+ "]";
	}
}