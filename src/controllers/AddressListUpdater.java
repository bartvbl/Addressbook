package controllers;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import data.Address;
import data.AddressCache;
import events.AddressChangeListener;
import events.DeleteAddressListener;
import gui.AddressBookWindow;
import gui.AddressItemPanel;

public class AddressListUpdater {

	public static void buildAddressList(AddressBookWindow window, AddressCache cache) {
		Address[] addresses = cache.getAll();
		
		if(!window.searchBox.getText().equals("")) {
			addresses = filter(addresses, window.searchBox.getText());
		}
		
		Arrays.sort(addresses, new AddressComparator());
		
		JPanel rootPanel = new JPanel();
		BoxLayout layout = new BoxLayout(rootPanel, BoxLayout.Y_AXIS);
//		GridLayout layout = new GridLayout(addresses.length, 1);
		rootPanel.setLayout(layout);
		
		for(Address address : addresses) {
			AddressItemPanel item = new AddressItemPanel();
			String fullName = address.firstName + (address.middleName.equals("") ? " " : " " + address.middleName + " ") + address.lastName;
			
			item.nameLabel.setText(fullName);
			item.addressLabel.setText(
					"<html>" + address.street + "<br>" + 
					address.postcode + " " + address.city + "<br>" + 
					address.country +
					"</html>");
			item.phone1Label.setText("<html>" + address.phone.replace("\n", "<br>") + "</html>");
			item.phone2Label.setText("<html>" + (address.fax.equals("") ? "" : "fax: ") + address.fax.replace("\n", "<br>") + "</html>");
			
			item.deleteButton.addActionListener(new DeleteAddressListener(window, cache, address));
			item.changeButton.addActionListener(new AddressChangeListener(window, cache, address));
			
			item.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
			rootPanel.add(item);
		}
		
		window.scrollArea.setViewportView(rootPanel);
	}

	private static Address[] filter(Address[] addresses, String query) {
		ArrayList<Address> searchResults = new ArrayList<Address>();
		query = query.toLowerCase();
		for(Address address : addresses) {
			boolean isRelevant = 
					address.firstName.toLowerCase().contains(query) ||
					address.middleName.toLowerCase().contains(query) ||
					address.lastName.toLowerCase().contains(query) ||
					address.postcode.toLowerCase().contains(query) ||
					address.city.toLowerCase().contains(query) ||
					address.country.toLowerCase().contains(query) ||
					address.comment.toLowerCase().contains(query) ||
					address.allNames.toLowerCase().contains(query) ||
					address.street.toLowerCase().contains(query) ||
					address.fax.toLowerCase().contains(query) ||
					address.phone.toLowerCase().contains(query);
			if(isRelevant) {
				searchResults.add(address);
			}
		}
		return searchResults.toArray(new Address[searchResults.size()]);
	}

}
