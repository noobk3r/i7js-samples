package com.itextpdf.samples.book.part3.chapter11;

import com.itextpdf.basics.font.PdfEncodings;
import com.itextpdf.basics.geom.PageSize;
import com.itextpdf.core.font.PdfFont;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.Property;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.samples.GenericTest;

import java.io.FileOutputStream;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class Listing_11_11_RightToLeftExample extends GenericTest {

    public static final String DEST = "./target/test/resources/book/part3/chapter11/Listing_11_11_RightToLeftExample.pdf";
    /** A movie title. */
    public static final String MOVIE
            = "\u05d4\u05d0\u05e1\u05d5\u05e0\u05d5\u05ea \u05e9\u05dc \u05e0\u05d9\u05e0\u05d4";
    private static final String FONT = "src/test/resources/font/FreeSans.ttf";

    public static void main(String[] agrs) throws Exception {
        new Listing_11_11_RightToLeftExample().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        //Initialize writer
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);

        //Initialize document
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc, PageSize.A4);

        PdfFont font = PdfFont.createFont(FONT, PdfEncodings.IDENTITY_H, true);
        document.add(new Paragraph("Movie title: Nina's Tragedies"));
        document.add(new Paragraph("directed by Savi Gabizon"));
        document.add(new Paragraph(MOVIE).setFont(font).setFontSize(14).setBaseDirection(Property.BaseDirection.RIGHT_TO_LEFT));

        //Close document
        document.close();
    }
}
