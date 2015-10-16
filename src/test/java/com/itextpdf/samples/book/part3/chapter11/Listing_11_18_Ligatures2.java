package com.itextpdf.samples.book.part3.chapter11;

import com.itextpdf.basics.font.PdfEncodings;
import com.itextpdf.basics.font.TrueTypeFont;
import com.itextpdf.core.font.PdfType0Font;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.model.Document;
import com.itextpdf.model.Property;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.samples.GenericTest;

import java.io.FileOutputStream;

import org.junit.Ignore;

@Ignore("ligatures not working. Only RTL")
public class Listing_11_18_Ligatures2 extends GenericTest {

    public static final String DEST = "./target/test/resources/book/part3/chapter11/Listing_11_18_Ligatures2.pdf";
    /** Correct movie title. */
    public static final String MOVIE
            = "\u0644\u0648\u0631\u0627\u0646\u0633 \u0627\u0644\u0639\u0631\u0628";
    /** Correct movie title. */
    public static final String MOVIE_WITH_SPACES
            = "\u0644 \u0648 \u0631 \u0627 \u0646 \u0633   \u0627 \u0644 \u0639 \u0631 \u0628";

    public static void main(String[] args) throws Exception {
        new Listing_11_18_Ligatures2().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        //Initialize writer
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);

        //Initialize document
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        TrueTypeFont fonfProgram = new TrueTypeFont("c:/windows/fonts/arialuni.ttf", PdfEncodings.IDENTITY_H);
        fonfProgram.setApplyLigatures(true);
        PdfType0Font bf = new PdfType0Font(pdfDoc, fonfProgram, PdfEncodings.IDENTITY_H);
        document.add(new Paragraph("Movie title: Lawrence of Arabia (UK)"));
        document.add(new Paragraph("directed by David Lean"));
        document.add(new Paragraph("Wrong: " + MOVIE).setFont(bf).setFontSize(20));
        document.add(new Paragraph("Wrong: " + MOVIE_WITH_SPACES).setFont(bf).setFontSize(20).setBaseDirection(Property.BaseDirection.RIGHT_TO_LEFT));
        document.add(new Paragraph(MOVIE).setFont(bf).setFontSize(20).setBaseDirection(Property.BaseDirection.RIGHT_TO_LEFT));

        //Close document
        document.close();
    }
}
