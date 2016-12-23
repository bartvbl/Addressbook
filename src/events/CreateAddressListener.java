package events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import controllers.AddressListUpdater;
import data.Address;
import data.AddressCache;
import gui.AddressBookWindow;
import gui.AddressChangeWindow;

public class CreateAddressListener implements ActionListener {

	private final AddressBookWindow window;
	private final AddressCache cache;

	public CreateAddressListener(AddressBookWindow window, AddressCache cache) {
		this.window = window;
		this.cache = cache;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		final AddressChangeWindow changeWindow = new AddressChangeWindow();
		
		changeWindow.setResizable(false);
		changeWindow.setTitle("Adres aanmaken");
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
						"",
						changeWindow.commentTextArea.getText(),
						changeWindow.allNamesTextArea.getText());
				cache.put(newAddress);
				AddressListUpdater.buildAddressList(window, cache);
				changeWindow.dispose();
			}
			
		});
	}

}
