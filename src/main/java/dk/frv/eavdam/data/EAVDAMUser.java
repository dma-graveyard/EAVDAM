/*
* Copyright 2011 Danish Maritime Safety Administration. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without
* modification, are permitted provided that the following conditions are met:
*
* 1. Redistributions of source code must retain the above copyright notice,
* this list of conditions and the following disclaimer.
*
* 2. Redistributions in binary form must reproduce the above copyright notice,
* this list of conditions and the following disclaimer in the documentation and/or
* other materials provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY Danish Maritime Safety Administration ``AS IS''
* AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
* IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
* DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> OR CONTRIBUTORS BE LIABLE FOR
* ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
* (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
* LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
* (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
* SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

* The views and conclusions contained in the software and documentation are those
* of the authors and should not be interpreted as representing official policies,
* either expressed or implied, of Danish Maritime Safety Administration.
*
*/
package dk.frv.eavdam.data;

import java.net.URL;
import java.util.List;
import org.w3c.dom.Element;

/**
 * Class for storing EAVDAM user specific data.
 */
public class EAVDAMUser {

	private String organizationName;
	/**
	 * E.g., DK for Denmark
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
	 * Anything can go in here. The schema allows unknown XML content in the end.
	 */
	private List<Element> anything;

	private int userDBID = -1;

	public EAVDAMUser() {}
	
	public int getUserDBID() {
		return userDBID;
	}

	public void setUserDBID(int userDBID) {
		this.userDBID = userDBID;
	}
	
	public String getOrganizationName() {
		return organizationName;
	}

	/**
	 * Also validates the organization name. Throws IllegalArgumentException if organization name is empty.
	 *
	 * @param organizationName  Organization name
	 */	
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

	/**
	 * Also validates the country id. Throws IllegalArgumentException if country id does not consist of two capital letters.
	 *
	 * @param countryID  Country ID
	 */		
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

	/**
	 * Also validates the phone number. Throws IllegalArgumentException if phone number does not start with + followed by digits, space and dashes allowed.
	 *
	 * @param phone  Phone number of the person
	 */		
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

	/**
	 * Also validates the phone number. Throws IllegalArgumentException if phone number does not start with + followed by digits, space and dashes allowed.
	 *
	 * @param fax  Phone number of the person
	 */		
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

	public URL getWww() {
		return www;
	}

	public void setWww(URL www) {
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
		return "EAVDAMUser [organizationName=" + organizationName +
			", countryID=" + countryID + ", phone=" + phone + ", fax=" +
			fax + ", www=" + www + ", description=" + description +
			", contact=" + contact + ", technicalContact=" +
			technicalContact + ", visitingAddress=" + visitingAddress +
			", postalAddress=" + postalAddress + ", anything=" + anything +
			"]";
	}
	
}