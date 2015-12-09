/**
 * Example written by Bruno Lowagie in answer to the following question:
 * http://stackoverflow.com/questions/24678640/itext-pdfdocument-page-size-inaccurate
 */
package com.itextpdf.samples.sandbox.stamper;

import com.itextpdf.basics.font.FontConstants;
import com.itextpdf.basics.font.FontFactory;
import com.itextpdf.basics.font.PdfEncodings;
import com.itextpdf.basics.font.Type1Font;
import com.itextpdf.core.font.PdfType1Font;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
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
public class StampHeader3 extends GenericTest {
    public static final String SRC = "./src/test/resources/sandbox/stamper/Wrong.pdf";
    public static final String DEST = "./target/test/resources/sandbox/stamper/stamp_header3.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new StampHeader3().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(
                new FileInputStream(SRC)), new PdfWriter(new FileOutputStream(DEST)));
        Document doc = new Document(pdfDoc);

        Paragraph header = new Paragraph("Copy").setFont(new PdfType1Font(pdfDoc,
                (Type1Font) FontFactory.createFont(FontConstants.HELVETICA, PdfEncodings.WINANSI))).setFontSize(14);
        float x, y;
        for (int i = 1; i <= pdfDoc.getNumOfPages(); i++) {
            System.out.println(pdfDoc.getPage(i).getRotation());
            if (pdfDoc.getPage(i).getRotation() % 180 == 0) {
                x = pdfDoc.getPage(i).getPageSize().getWidth() / 2;
                y = pdfDoc.getPage(i).getPageSize().getTop() - 20;
            }
            else {
                System.out.println("rotated");
                x = pdfDoc.getPage(i).getPageSize().getHeight() / 2;
                y = pdfDoc.getPage(i).getPageSize().getRight() - 20;
            }
            // TODO need to get OVER content to write properly
            doc.showTextAligned(header, x, y, i, Property.TextAlignment.CENTER, null, 0);
        }

        pdfDoc.close();
    }
}
