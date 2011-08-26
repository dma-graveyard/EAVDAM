package dk.frv.eavdam.data;

/**
 * Class for one contact point information
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

	public Person(){

	}

	public void finalize() throws Throwable {

	}

}