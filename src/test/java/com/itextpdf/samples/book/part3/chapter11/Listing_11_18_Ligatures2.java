package com.itextpdf.samples.book.part3.chapter11;

import com.itextpdf.basics.font.PdfEncodings;
import com.itextpdf.core.color.Color;
import com.itextpdf.core.font.PdfFont;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.model.Document;
import com.itextpdf.model.Property;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Text;
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

    private static final String FONT = "src/test/resources/font/arabtype.volt.ttf";
    // "c:/windows/fonts/arialuni.ttf"
    // c:/windows/fonts/arial.ttf


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

        PdfFont bf = PdfFont.createFont(FONT, PdfEncodings.IDENTITY_H, true);
        document.add(new Paragraph("Movie title: Lawrence of Arabia (UK)"));
        document.add(new Paragraph("directed by David Lean"));
        document.add(new Paragraph("Wrong: " + MOVIE).setFont(bf).setFontSize(20));
        document.add(new Paragraph("Wrong: " + MOVIE_WITH_SPACES).setFont(bf).setFontSize(20).setBaseDirection(Property.BaseDirection.RIGHT_TO_LEFT));
        document.add(new Paragraph(new Text(MOVIE).setBackgroundColor(Color.RED)).setFont(bf).setFontSize(20).setBaseDirection(Property.BaseDirection.RIGHT_TO_LEFT).
                setFontScript(Character.UnicodeScript.ARABIC));

        //Close document
        document.close();
    }
}
