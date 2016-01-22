/**
 * Example written by Bruno Lowagie in answer to the following question:
 * http://stackoverflow.com/questions/24678640/itext-pdfdocument-page-size-inaccurate
 */
package com.itextpdf.samples.sandbox.stamper;

import com.itextpdf.basics.font.FontConstants;
import com.itextpdf.basics.font.FontFactory;
import com.itextpdf.basics.font.Type1Font;
import com.itextpdf.core.font.PdfType1Font;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.Property;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class StampHeader2 extends GenericTest {
    public static final String SRC = "./src/test/resources/sandbox/stamper/Wrong.pdf";
    public static final String DEST = "./target/test/resources/sandbox/stamper/stamp_header2.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new StampHeader2().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(
                new FileInputStream(SRC)), new PdfWriter(new FileOutputStream(DEST)));
        Document doc = new Document(pdfDoc);
        // TODO Implement setRotateContent(boolean)
        // stamper.setRotateContents(false);
        Paragraph header = new Paragraph("Copy").setFont(
                new PdfType1Font((Type1Font) FontFactory.createFont(FontConstants.HELVETICA))).setFontSize(14);
        for (int i = 1; i <= pdfDoc.getNumberOfPages(); i++) {
            float x = pdfDoc.getPage(i).getPageSize().getWidth() / 2;
            float y = pdfDoc.getPage(i).getPageSize().getTop() - 20;
            // TODO Implement showTextAligned to show text over content
            doc.showTextAligned(header, x, y, i, Property.TextAlignment.CENTER, null, 0);
        }
        pdfDoc.close();
    }
}
