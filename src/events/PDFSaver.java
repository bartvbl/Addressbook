package events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.color.DeviceCmyk;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import controllers.AddressComparator;
import data.Address;
import data.AddressCache;
import gui.AddressBookWindow;

public class PDFSaver implements ActionListener {

	private final AddressBookWindow window;
	private final AddressCache cache;

	private static final int rowCount = 15;
	private static final int margin = 15;
	
	private static final float stepSize = 2.0f / rowCount;
	
	public PDFSaver(AddressBookWindow window, AddressCache cache) {
		this.window = window;
		this.cache = cache;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		PdfWriter writer;
		Address[] addresses = cache.getAll();
		Arrays.sort(addresses, new AddressComparator());
		
		try {
			JFileChooser chooser = new JFileChooser();
			int choice = chooser.showSaveDialog(window);
			if(choice == JFileChooser.APPROVE_OPTION) {
				String path = chooser.getSelectedFile().getAbsolutePath();
				if(!path.endsWith(".pdf")) {
					path += ".pdf";
				}
				writer = new PdfWriter(path);
				PdfDocument pdf = new PdfDocument(writer);
				PageSize ps = PageSize.A4;
				
				for(int i = 0; i < addresses.length; i += 2 * rowCount) {
					PdfPage page = pdf.addNewPage(ps);
					PdfCanvas canvas = new PdfCanvas(page);
					createEmptyPage(ps, canvas);
					drawPage(ps, canvas, addresses, i);
				}
				
				
				pdf.close();
				JOptionPane.showMessageDialog(window, "PDF is opgeslagen.");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void drawPage(PageSize ps, PdfCanvas canvas, Address[] addresses, int startIndex) throws IOException {
		for(int i = startIndex; i < Math.min(startIndex + 2 * rowCount, addresses.length); i++) {
			Address address = addresses[i];
			
			float magnitudeX = ps.getWidth() / 2 - margin;
			
			int row = (i % rowCount);
			int column = (i % (2 * rowCount)) / (rowCount);
			
			float magnitude = ps.getHeight() / 2 - margin;
			
			double cellX = -magnitudeX + column * magnitudeX;
			double cellY = magnitude - ((float) row * stepSize * magnitude);
			
			drawSingleAddress(canvas, cellX, cellY, magnitude, address);
		}
	}

	private void drawSingleAddress(PdfCanvas canvas, double x, double y, float magnitude, Address address) throws IOException {
		
		String fullName = address.firstName + (address.middleName.equals("") ? " " : " " + address.middleName + " ") + address.lastName;
		
		String addressLine1 = address.street;
		String addressLine2 = address.postcode + " " + address.city;
		String addressLine3 = address.country;
		
		String[] phoneLines = address.phone.split("\n");
		
		drawString(canvas, x + 4, y - 3, fullName, true);
		
		drawString(canvas, x + 4, y - 15, addressLine1, false);
		drawString(canvas, x + 4, y - 25, addressLine2, false);
		drawString(canvas, x + 4, y - 35, addressLine3, false);
		
		for(int i = 0; i < phoneLines.length; i++) {
			drawString(canvas, x + 180, y - (3 + i * 10), phoneLines[i], false);
		}
	}

	private void createEmptyPage(PageSize ps, PdfCanvas canvas) throws IOException {
		canvas.concatMatrix(1, 0, 0, 1, ps.getWidth() / 2, ps.getHeight() / 2);
		canvas.setLineWidth(0.2f);

		
		float magnitude = ps.getHeight() / 2 - margin;

		float leftX = -(ps.getWidth() / 2 - margin);
		float rightX = ps.getWidth() / 2 - margin;

		for(int i = 0; i <= rowCount; i++) {
			canvas.moveTo(leftX, -magnitude + ((float) i * stepSize) * magnitude)
			.lineTo(rightX, -magnitude + ((float) i * stepSize) * magnitude)
			.stroke();			
		}

		canvas.moveTo(0, -magnitude).lineTo(0, magnitude).stroke();
	}

	private void drawString(PdfCanvas canvas, double x, double y, String string, boolean bold) throws IOException {
		canvas.beginText();
		if(bold) {
			canvas.setFontAndSize(PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD), 8);
		} else {
			canvas.setFontAndSize(PdfFontFactory.createFont(FontConstants.HELVETICA), 8);
		}
		canvas.setLeading(7.5f)
		.moveText(x, y);
			canvas.newlineShowText(string);
		canvas.endText();
	}

}
