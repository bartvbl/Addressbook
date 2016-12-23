package data;

import java.util.ArrayList;

public class AddressCache {
	private ArrayList<Address> addressList = new ArrayList<Address>();

	public void put(Address address) {
		this.addressList.add(address);
	}

	public Address[] getAll() {
		return addressList.toArray(new Address[addressList.size()]);
	}

	public void delete(Address address) {
		addressList.remove(address);
	}

}
