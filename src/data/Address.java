package data;

public class Address {

	public final String firstName;
	public final String middleName;
	public final String lastName;
	public final String street;
	public final String postcode;
	public final String city;
	public final String country;
	public final String phone;
	public final String fax;
	public final String comment;
	public final String allNames;

	public Address(String firstName, String middleName, String lastName, String street, String postcode, String city,
			String country, String phone, String fax, String comment, String allNames) {
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.street = street;
		this.postcode = postcode;
		this.city = city;
		this.country = country;
		this.phone = phone;
		this.fax = fax;
		this.comment = comment;
		this.allNames = allNames;
	}

}
