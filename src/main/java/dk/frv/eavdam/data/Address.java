package dk.frv.eavdam.data;

/**
 * @author ttesei
 * @version 1.0
 * @created 26-elo-2011 13:27:24
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
		return "Address [addressline1=" + addressline1 + ", addressline2="
				+ addressline2 + ", zip=" + zip + ", city=" + city
				+ ", country=" + country + "]";
	}
}