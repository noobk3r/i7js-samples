/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

package com.itextpdf.samples.book.part4.chapter13;

import com.itextpdf.basics.font.PdfEncodings;
import com.itextpdf.core.font.PdfFont;
import com.itextpdf.core.font.PdfFontFactory;
import com.itextpdf.core.pdf.PdfAConformanceLevel;
import com.itextpdf.core.pdf.PdfOutputIntent;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.core.xmp.XMPException;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.pdfa.PdfADocument;
import com.itextpdf.samples.GenericTest;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class Listing_13_01_PdfXPdfA extends GenericTest {
    // !!! Override comparePdf when PdfX'll be implemented
    public static final String DEST = "./target/test/resources/book/part4/chapter13/Listing_13_01_PdfXPdfAa.pdf";
    public static final String RESULT1 = "./target/test/resources/book/part4/chapter13/Listing_13_01_PdfXPdfAx.pdf";
    public static final String FONT = /*"c:/windows/fonts/arial.ttf"*/"./src/test/resources/font/FreeSans.ttf";
    public static final String sourceFolder = "./src/test/resources/book/part4/chapter13/";

    public static void main(String args[]) throws IOException, SQLException, XMPException {
        new Listing_13_01_PdfXPdfA().manipulatePdf(RESULT1);
    }

    @Override
    protected void manipulatePdf(String dest) throws IOException, SQLException, XMPException {
//        createPdfX(RESULT1);
        createPdfA(DEST);
    }

    // TODO NO PdfX
//    /**
//     * Creates a PDF document.
//     * @param dest the path to the new PDF document
//     * @throws IOException
//     */
//    public void createPdfX(String dest) throws IOException {
//        PdfDocument pddDoc = new PdfDocument(new PdfWriter(dest));
//        writer.setPDFXConformance(PdfWriter.PDFX1A2001);
//        // step 3
//        document.open();
//        // step 4
//        Font font = FontFactory.getFont(
//                FONT, BaseFont.CP1252, BaseFont.EMBEDDED, Font.UNDEFINED,
//                Font.UNDEFINED, new CMYKColor(255, 255, 0, 0));
//        document.add(new Paragraph("Hello World", font));
//        // step 5
//        document.close();
//    }

    public void createPdfA(String dest) throws IOException, XMPException {
        InputStream is = new FileInputStream(sourceFolder + "sRGB Color Space Profile.icm");

        PdfADocument pdfADocument = new PdfADocument(new PdfWriter(dest),
                PdfAConformanceLevel.PDF_A_1B,
                new PdfOutputIntent("Custom", "", "http://www.color.org", "sRGB IEC61966-2.1", is));
        pdfADocument.setXmpMetadata();
        pdfADocument.addNewPage();

        PdfFont font = PdfFontFactory.createFont(FONT, PdfEncodings.CP1252, true);
        Document doc = new Document(pdfADocument).add(new Paragraph("Hello World").setFont(font));
        doc.close();
    }
}
