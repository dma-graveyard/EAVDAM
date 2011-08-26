package dk.frv.eavdam.data;

/**
 * A class for storing EAVDAM user specific data.
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

	public EAVDAMUser(){

	}

	public void finalize() throws Throwable {

	}

}