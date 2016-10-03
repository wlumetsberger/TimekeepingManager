package w.lumetsberger.timekeeper;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class Application {

	private static void addEmptyLine(Document doc, int number) throws DocumentException {
		for (int i = 0; i < number; i++) {
			doc.add(new Paragraph(" "));
		}
	}

	public static void main(String[] args) {
		// write your code here
		try {
			Document doc = new Document();
			FileOutputStream out = new FileOutputStream("auswertung.pdf");
			PdfWriter.getInstance(doc, out);
			doc.open();
			doc.addTitle("Ergebnisse");
			doc.addAuthor("Enduro Stammtisch Perg");
			Paragraph perface = new Paragraph();
			perface.add(new Paragraph("Ergebnisse:"));
			doc.add(perface);
			Connection con = DriverManager
					.getConnection("jdbc:mysql://10.0.0.80/timekeeper?" + "user=root&password=wolfgang");
			PreparedStatement stmt = con.prepareStatement("select * from person");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String name = rs.getString("name");
				String startNummer = rs.getString("number");
				int id = rs.getInt("id");
				Paragraph p = new Paragraph();
				p.add(new Paragraph("Teilnehmer: " + name));
				doc.add(p);
				p = new Paragraph();
				p.add(new Paragraph("Startnummer: "+ startNummer));
				doc.add(p);
				addEmptyLine(doc, 1);
				PreparedStatement result = con
						.prepareStatement("select * from lap where person =? order by timestamp asc");
				result.setInt(1, id);
				ResultSet laps = result.executeQuery();
				PdfPTable table = new PdfPTable(2);
				PdfPCell c = new PdfPCell(new Phrase("Runde"));
				c.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(c);
				c = new PdfPCell(new Phrase("Zeit"));
				c.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(c);
				table.setHeaderRows(1);

				ArrayList<Date> rlist = new ArrayList<Date>();
				while (laps.next()) {
					rlist.add(laps.getTimestamp("timestamp"));
					System.out.println(laps.getTimestamp("timestamp"));
				}
				for (int i = 0; i <= rlist.size() - 2; i++) {
					Date d1 = rlist.get(i);
					Date d2 = rlist.get(i + 1);
					Date d3 = new Date();
					d3.setTime(d2.getTime() - d1.getTime());
					table.addCell("Runde: " + (i + 1));
					table.addCell("" + (d3.getHours() - 1) + ":" + d3.getMinutes() + ":" + d3.getSeconds());
				}
				doc.add(table);
				addEmptyLine(doc, 1);
				// System.out.println("found entry: "+ rs.getString("name"));
				laps.close();
				result.close();
			}
			rs.close();
			stmt.close();
			con.close();
			doc.close();
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
