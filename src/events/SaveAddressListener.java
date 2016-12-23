package events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import data.AddressCache;
import gui.AddressBookWindow;
import io.XLSDumper;

public class SaveAddressListener implements ActionListener {

	private final AddressBookWindow window;
	private final AddressCache cache;

	public SaveAddressListener(AddressBookWindow window, AddressCache cache) {
		this.window = window;
		this.cache = cache;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		window.saveButton.setEnabled(false);
		XLSDumper.dump(cache);
		window.saveButton.setEnabled(true);
		JOptionPane.showMessageDialog(window, "Addressen zijn opgeslagen.");
	}

}
