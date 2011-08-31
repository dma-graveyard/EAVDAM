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
		if (name == null || name.trim().length() == 0) {
			throw new IllegalArgumentException("Name must be given");
		}
		this.name = name.trim();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		if (email != null) {
			this.email = email.trim();
		} else {
			this.email = null;
		}
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		if (phone != null && !phone.matches("\\+[0-9 -]+")) {
			throw new IllegalArgumentException(
					"Phone number invalid, must start with + followed by digits, space and dashes allowed.");
		}
		this.phone = phone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		if (fax != null && !fax.matches("\\+[0-9 -]+")) {
			throw new IllegalArgumentException(
					"Fax number invalid, must start with + followed by digits, space and dashes allowed.");
		}
		this.fax = fax;
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