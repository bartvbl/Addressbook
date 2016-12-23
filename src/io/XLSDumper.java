package io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import data.Address;
import data.AddressCache;

public class XLSDumper {

	public static void dump(AddressCache cache) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");
		LocalDateTime now = LocalDateTime.now();
		String fileName = dtf.format(now) + ".xls";
		File destinationFile = new File("addressen/" + fileName);
		
		try {
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("Addressen");
			
			HSSFRow headerRow = sheet.createRow(0);
			headerRow.createCell(0).setCellValue("Voornaam");
			headerRow.createCell(1).setCellValue("Tussenvoegsel");
			headerRow.createCell(2).setCellValue("Achternaam");
			headerRow.createCell(3).setCellValue("Straat");
			headerRow.createCell(4).setCellValue("Postcode");
			headerRow.createCell(5).setCellValue("Woonplaats");
			headerRow.createCell(6).setCellValue("Land");
			headerRow.createCell(7).setCellValue("Telefoon");
			headerRow.createCell(8).setCellValue("Fax");
			headerRow.createCell(9).setCellValue("Commentaar");
			headerRow.createCell(10).setCellValue("Alle namen");
			
			Address[] addresses = cache.getAll();
			for(int i = 0; i < addresses.length; i++) {
				HSSFRow row = sheet.createRow(i + 1);
				Address address = addresses[i];
				
				HSSFCell cell = row.createCell(0);
				cell.setCellValue(address.firstName);
				cell = row.createCell(1);
				cell.setCellValue(address.middleName);
				cell = row.createCell(2);
				cell.setCellValue(address.lastName);
				cell = row.createCell(3);
				cell.setCellValue(address.street);
				cell = row.createCell(4);
				cell.setCellValue(address.postcode);
				cell = row.createCell(5);
				cell.setCellValue(address.city);
				cell = row.createCell(6);
				cell.setCellValue(address.country);
				cell = row.createCell(7);
				cell.setCellValue(address.phone);
				cell = row.createCell(8);
				cell.setCellValue(address.fax);
				cell = row.createCell(9);
				cell.setCellValue(address.comment);
				cell = row.createCell(10);
				cell.setCellValue(address.allNames);
				
			}
			
			FileOutputStream outStream = new FileOutputStream(destinationFile);
			wb.write(outStream);
			outStream.close();
			wb.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
