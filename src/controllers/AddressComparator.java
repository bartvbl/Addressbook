package controllers;

import java.util.Comparator;

import data.Address;

public class AddressComparator implements Comparator<Address> {

	@Override
	public int compare(Address address1, Address address2) {
		String fullName1 = address1.lastName + (address1.middleName.equals("") ? " " : " " + address1.middleName + " ") + address1.firstName;
		String fullName2 = address2.lastName + (address2.middleName.equals("") ? " " : " " + address2.middleName + " ") + address2.firstName;
		
		return fullName1.compareTo(fullName2);
	}

}
