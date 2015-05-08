package com.itextpdf.samples;

import com.itextpdf.basics.PdfException;
import com.itextpdf.core.pdf.*;
import com.itextpdf.core.pdf.action.PdfAction;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Link;
import com.itextpdf.model.element.Paragraph;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Listing_07_02_LinkActions {

    static public final String DEST = "./target/test/resources/Listing_07_02_LinkActions.pdf";

    public static void main(String args[]) throws IOException, PdfException {
        new Listing_07_02_LinkActions().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws FileNotFoundException, PdfException {
        //Initialize writer
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);

        //Initialize document
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);

        Paragraph p = new Paragraph("Click on a country, and you'll get a list of movies, containing links to the ").
                add(new Link("Internet Movie Database", PdfAction.createURI(pdfDoc, "http://www.imdb.com"))).
                add(".");
        doc.add(p);

        p = new Paragraph("This list can be found in a ").
                add(new Link("separate document", PdfAction.createGoToR(pdfDoc, "movie_links_1.pdf", 1))).
                add(".");
        doc.add(p);

        DummyRs rs = new DummyRs();
        p = new Paragraph(rs.getString("country")).
                add(": ").
                add(new Link(String.format("%d movies", rs.getInt("c")), PdfAction.createGoToR(pdfDoc, "movie_links_1.pdf", rs.getString("country_id"), true)));
        doc.add(p);

        p = new Paragraph("Go to ").
                add(new Link("top", PdfAction.createGoTo(pdfDoc, "top"))).
                add(".");
        doc.add(p);

        PdfArray array = new PdfArray();
        array.add(pdfDoc.getLastPage().getPdfObject());
        array.add(PdfName.XYZ);
        array.add(new PdfNumber(36));
        array.add(new PdfNumber(842));
        array.add(new PdfNumber(1));

        pdfDoc.addNewName(new PdfString("top"), array);

        //Close document
        doc.close();
    }

    static private class DummyRs {
        public String getString(String s) {
            return "";
        }

        public int getInt(String in) {
            return 0;
        }

    }

}
