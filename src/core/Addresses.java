package core;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import data.AddressCache;
import events.CreateAddressListener;
import events.PDFSaver;
import events.SaveAddressListener;
import events.SearchListener;
import gui.AddressBookWindow;
import io.XLSLoadingThread;

public class Addresses {

	public static void main(String[] args) {
		setSwingSettings();
		
		AddressCache cache = new AddressCache();
		AddressBookWindow window = new AddressBookWindow();
		
		window.setTitle("Adresboek");
		window.setVisible(true);
		
		new XLSLoadingThread(window, cache).start();
		
		window.saveButton.setEnabled(false);
		window.newButton.setEnabled(false);
		window.pdfButton.setEnabled(false);
		
		window.searchBox.addKeyListener(new SearchListener(window, cache));
		window.newButton.addActionListener(new CreateAddressListener(window, cache));
		window.saveButton.addActionListener(new SaveAddressListener(window, cache));
		window.pdfButton.addActionListener(new PDFSaver(window, cache));
	}
	
	private static void setSwingSettings() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}
	

}
