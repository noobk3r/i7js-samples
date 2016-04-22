/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

/*
 * This example was written in answer to the following question:
 * http://stackoverflow.com/questions/27294392/itext-create-pdf-a-1a-with-images
 */
package com.itextpdf.samples.sandbox.pdfa;


import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfAConformanceLevel;
import com.itextpdf.kernel.pdf.PdfDocumentInfo;
import com.itextpdf.kernel.pdf.PdfOutputIntent;
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.xmp.XMPException;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.pdfa.PdfADocument;
import com.itextpdf.samples.GenericTest;
import com.itextpdf.test.annotations.type.SampleTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class PdfA1a_images extends GenericTest {
    public static final float MARGIN_OF_ONE_CM = 28.8f;
    public static final String DEST = "./target/test/resources/sandbox/pdfa/pdf_a1a_images.pdf";
    public static final String FONT = "./src/test/resources/font/OpenSans-Regular.ttf";
    public static final String LOGO = "./src/test/resources/img/hero.jpg";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new PdfA1a_images().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException, XMPException {
        PdfFont font = PdfFontFactory.createFont(FONT, PdfEncodings.IDENTITY_H, true);
        InputStream is = new FileInputStream("./src/test/resources/data/sRGB_CS_profile.icm");
        PdfADocument pdfDoc = new PdfADocument(new PdfWriter(dest), PdfAConformanceLevel.PDF_A_1A,
                new PdfOutputIntent("Custom", "", "http://www.color.org", "sRGB IEC61966-2.1", is));
        Document doc = new Document(pdfDoc);
        doc.setMargins(MARGIN_OF_ONE_CM, MARGIN_OF_ONE_CM, MARGIN_OF_ONE_CM, MARGIN_OF_ONE_CM);

        pdfDoc.getCatalog().setLang(new PdfString("nl-nl"));
        PdfDocumentInfo info = pdfDoc.getDocumentInfo();
        info
                .setTitle("title")
                .setAuthor("Author")
                .setSubject("Subject")
                .setCreator("Creator")
                .setKeywords("Metadata, iText, PDF")
                .setCreator("My program using iText")
                .addCreationDate();
        pdfDoc.setTagged();
        pdfDoc.createXmpMetadata();

        Paragraph element = new Paragraph("Hello World").setFont(font).setFontSize(10);
        doc.add(element);
        Image logoImage = new Image(ImageFactory.getImage(LOGO));
        logoImage.getAccessibilityProperties().setAlternateDescription("Logo");
        doc.add(logoImage);
        doc.close();
    }
}
