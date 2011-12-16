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
 * Class for postal address.
 */
public class Address {

	private String addressline1;
	private String addressline2;
	private String zip;
	private String city;
	private String country;

	public Address() {
	}

	public String getAddressline1() {
		return addressline1;
	}

	public void setAddressline1(String addressline1) {
		if (addressline1 != null) {
			this.addressline1 = addressline1.trim();
		} else {
			this.addressline1 = null;
		}
	}

	public String getAddressline2() {
		return addressline2;
	}

	public void setAddressline2(String addressline2) {
		if (addressline2 != null) {
			this.addressline2 = addressline2.trim();
		} else {
			this.addressline2 = null;
		}
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		if (zip != null) {
			this.zip = zip.trim();
		} else {
			this.zip = null;
		}
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		if (city != null) {
			this.city = city.trim();
		} else {
			this.city = null;
		}
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		if (country != null) {
			this.country = country.trim();
		} else {
			this.country = null;
		}
	}

	@Override
	public String toString() {
		return "Address [addressline1=" + addressline1 + ", addressline2=" +
			addressline2 + ", zip=" + zip + ", city=" + city +
			", country=" + country + "]";
	}
}