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

/**
 * Class for a contact person. 
 */
public class Person {

	private String name;
	private String email;
	private String phone;
	private String fax;
	private String description;
	private Address visitingAddress;
	private Address postalAddress;

	public Person() {}

	public String getName() {
		return name;
	}

	/**
	 * Also validates the name. Throws IllegalArgumentException if name is empty.
	 *
	 * @param name  Name of the person
	 */
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
	 * Also validates the fax number. Throws IllegalArgumentException if fax number does not start with + followed by digits, space and dashes allowed.
	 *
	 * @param fax  Fax number of the person 
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
		return "Person [name=" + name + ", email=" + email + ", phone=" + phone +
			", fax=" + fax + ", description=" + description +
			", visitingAddress=" + visitingAddress + ", postalAddress=" +
			postalAddress + "]";
	}
}