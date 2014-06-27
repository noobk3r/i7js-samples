package com.itextpdf.samples;

import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.pdf.actions.PdfAction;
import com.itextpdf.model.Document;
import com.itextpdf.model.elements.Link;
import com.itextpdf.model.elements.Paragraph;

import java.io.FileOutputStream;
import java.io.IOException;

public class Listing_07_02_LinkActions {

    static private final String RESULT = "./result.pdf";

    public static void main(String args[]) throws IOException {

        //Initialize writer
        FileOutputStream fos = new FileOutputStream(RESULT);
        PdfWriter writer = new PdfWriter(fos);
        writer.setCloseStream(true);

        //Initialize document
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);

        Paragraph p = new Paragraph("Click on a country, and you'll get a list of movies, containing links to the ").
                add(new Link("Internet Movie Database", PdfAction.createURI("http://www.imdb.com"))).
                add(".");
        doc.add(p);

        p = new Paragraph("This list can be found in a ").
                add(new Link("separate document", PdfAction.createGoToR("movie_links_1.pdf", 1))).
                add(".");
        doc.add(p);

        DummyRs rs = new DummyRs();
        p = new Paragraph(rs.getString("country")).
                add(": ").
                add(new Link(String.format("%d movies", rs.getInt("c")), PdfAction.createGoToR("movie_links_1.pdf", rs.getString("country_id"), true)));
        doc.add(p);

        p = new Paragraph("Go to ").
                add(new Link("top", PdfAction.createGoTo("top"))).
                add(".");
        doc.add(p);

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
