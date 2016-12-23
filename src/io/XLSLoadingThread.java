package io;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import controllers.AddressListUpdater;
import data.Address;
import data.AddressCache;
import gui.AddressBookWindow;

public class XLSLoadingThread extends Thread {

	private final AddressBookWindow window;
	private final AddressCache cache;

	public XLSLoadingThread(AddressBookWindow window, AddressCache cache) {
		this.window = window;
		this.cache = cache;
	}

	public void run() {
		File sourceFile = lastFileModified("addressen/");
		System.out.println("Loading XLS file: " + sourceFile);
		try {
			loadFile(sourceFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

		AddressListUpdater.buildAddressList(window, cache);

		window.saveButton.setEnabled(true);
		window.newButton.setEnabled(true);
		window.pdfButton.setEnabled(true);

		System.out.println("Complete.");
	}

	public static File lastFileModified(String dir) {
		File fl = new File(dir);
		File[] files = fl.listFiles(new FileFilter() {          
			public boolean accept(File file) {
				return file.isFile();
			}
		});
		Arrays.sort(files, new Comparator<File>()
		{
			@Override
			public int compare(File f1, File f2) {
				return f1.getName().compareTo(f2.getName());
			}
		});
		return files[files.length - 1];
	}

	private void loadFile(File sourceFile) throws IOException, FileNotFoundException {
		POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(sourceFile));
		HSSFWorkbook wb = new HSSFWorkbook(fs);
		HSSFSheet sheet = wb.getSheetAt(0);

		int rowCount = sheet.getPhysicalNumberOfRows();

		// Ship the first row because it contains column labels
		for(int rowIndex = 1; rowIndex < rowCount; rowIndex++) {
			HSSFRow row = sheet.getRow(rowIndex);

			String firstName = 	row.getCell(0).getStringCellValue().trim();
			String middleName = row.getCell(1).getStringCellValue().trim();
			String lastName = 	row.getCell(2).getStringCellValue().trim();

			String street = 	row.getCell(3).getStringCellValue().trim();
			String postcode = 	row.getCell(4).getStringCellValue().trim();
			String city = 		row.getCell(5).getStringCellValue().trim();
			String country = 	row.getCell(6).getStringCellValue().trim();

			String phone = 		row.getCell(7).getStringCellValue().trim();
			String fax = 		row.getCell(8).getStringCellValue().trim();

			String comment = 	row.getCell(9).getStringCellValue().trim();
			String allNames = 	row.getCell(10).getStringCellValue().trim();

			Address address = new Address(firstName, middleName, lastName, street, postcode, city, country, phone, fax, comment, allNames);

			cache.put(address);
		}

		wb.close();
	}
}
