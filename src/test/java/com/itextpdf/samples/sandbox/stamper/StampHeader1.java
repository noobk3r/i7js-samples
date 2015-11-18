/**
 * Example written by Bruno Lowagie in answer to the following question:
 * http://stackoverflow.com/questions/24678640/itext-pdfdocument-page-size-inaccurate
 */
package com.itextpdf.samples.sandbox.stamper;

import com.itextpdf.basics.font.FontConstants;
import com.itextpdf.basics.font.FontFactory;
import com.itextpdf.basics.font.PdfEncodings;
import com.itextpdf.basics.font.Type1Font;
import com.itextpdf.core.color.Color;
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

import org.junit.experimental.categories.Category;


@Category(SampleTest.class)
public class StampHeader1 extends GenericTest {
    public static final String SRC = "./src/test/resources/sandbox/stamper/Right.pdf";
    public static final String DEST = "./target/test/resources/sandbox/stamper/stamp_header1.pdf";



    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new StampHeader1().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(
                new FileInputStream(SRC)), new PdfWriter(new FileOutputStream(DEST)));
        Document doc = new Document(pdfDoc);
        Paragraph header = new Paragraph("Copy").setFont(new PdfType1Font(pdfDoc,
                (Type1Font) FontFactory.createFont(FontConstants.HELVETICA, PdfEncodings.WINANSI))).setFontSize(14);
        header.setFontColor(Color.RED);
        for (int i = 1; i <= pdfDoc.getNumOfPages(); i++) {
            float x = pdfDoc.getPage(i).getPageSize().getWidth() / 2;
            float y = pdfDoc.getPage(i).getPageSize().getTop() - 20;

            doc.showTextAligned(header.setFontColor(Color.RED), x, y, i,
                    Property.TextAlignment.LEFT, Property.VerticalAlignment.BOTTOM, 0);
        }
        doc.close();
        //pdfDoc.close();
    }
}
