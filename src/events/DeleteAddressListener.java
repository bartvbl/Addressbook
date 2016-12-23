package events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import controllers.AddressListUpdater;
import data.Address;
import data.AddressCache;
import gui.AddressBookWindow;

public class DeleteAddressListener implements ActionListener {

	private final AddressBookWindow window;
	private final AddressCache cache;
	private final Address address;

	public DeleteAddressListener(AddressBookWindow window, AddressCache cache, Address address) {
		this.window = window;
		this.cache = cache;
		this.address = address;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int choice = JOptionPane.showConfirmDialog(window, "Weet je zeker dat je dit adres wilt verwijderen?", "Adres verwijderen?", JOptionPane.YES_NO_OPTION);
		if(choice == JOptionPane.YES_OPTION) {
			cache.delete(address);
			AddressListUpdater.buildAddressList(window, cache);
		}
		
	}

}
