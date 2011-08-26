package dk.frv.eavdam.data;

/**
 * Class for one contact point information
 * 
 * @author ttesei
 * @version 1.0
 * @created 26-elo-2011 13:27:25
 */
public class Person {

	private String name;
	private String email;
	private String phone;
	private String fax;
	private String description;
	private Address visitingAddress;
	private Address postalAddress;

	public Person() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	@Override
	public String toString() {
		return "Person [name=" + name + ", email=" + email + ", phone=" + phone
				+ ", fax=" + fax + ", description=" + description
				+ ", visitingAddress=" + visitingAddress + ", postalAddress="
				+ postalAddress + "]";
	}
}