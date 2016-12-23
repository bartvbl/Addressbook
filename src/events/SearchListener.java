package events;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import controllers.AddressListUpdater;
import data.AddressCache;
import gui.AddressBookWindow;

public class SearchListener implements KeyListener {

	private final AddressBookWindow window;
	private final AddressCache cache;

	public SearchListener(AddressBookWindow window, AddressCache cache) {
		this.window = window;
		this.cache = cache;
	}

	@Override
	public void keyPressed(KeyEvent arg0) {

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		AddressListUpdater.buildAddressList(window, cache);
	}

	@Override
	public void keyTyped(KeyEvent arg0) {

	}

}
