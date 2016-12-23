package events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import controllers.AddressListUpdater;
import data.Address;
import data.AddressCache;
import gui.AddressBookWindow;
import gui.AddressChangeWindow;

public class AddressChangeListener implements ActionListener {

	private AddressBookWindow window;
	private AddressCache cache;
	private Address address;

	public AddressChangeListener(AddressBookWindow window, AddressCache cache, Address address) {
		this.window = window;
		this.cache = cache;
		this.address = address;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		final AddressChangeWindow changeWindow = new AddressChangeWindow();
		
		changeWindow.firstNameTextArea.setText(address.firstName);
		changeWindow.middleNameTextArea.setText(address.middleName);
		changeWindow.lastNameTextArea.setText(address.lastName);
		
		changeWindow.streetTextArea.setText(address.street);
		changeWindow.postcodeTextArea.setText(address.postcode);
		changeWindow.cityTextArea.setText(address.city);
		changeWindow.countryTextArea.setText(address.country);
		
		changeWindow.phoneTextArea.setText(address.phone);
		changeWindow.commentTextArea.setText(address.comment);
		changeWindow.allNamesTextArea.setText(address.allNames);
		
		
		changeWindow.setResizable(false);
		changeWindow.setTitle("Adres aanpassen");
		changeWindow.setVisible(true);
		changeWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		changeWindow.setLocation(100, 100);
		
		changeWindow.cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changeWindow.dispose();
			}
		});
		
		changeWindow.okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Address newAddress = new Address(changeWindow.firstNameTextArea.getText(),
						changeWindow.middleNameTextArea.getText(),
						changeWindow.lastNameTextArea.getText(),
						changeWindow.streetTextArea.getText(),
						changeWindow.postcodeTextArea.getText(),
						changeWindow.cityTextArea.getText(),
						changeWindow.countryTextArea.getText(),
						changeWindow.phoneTextArea.getText(),
						address.fax,
						changeWindow.commentTextArea.getText(),
						changeWindow.allNamesTextArea.getText());
				cache.delete(address);
				cache.put(newAddress);
				AddressListUpdater.buildAddressList(window, cache);
				changeWindow.dispose();
			}
			
		});
	}

}
