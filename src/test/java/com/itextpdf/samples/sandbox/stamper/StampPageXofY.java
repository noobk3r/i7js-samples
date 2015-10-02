package com.itextpdf.samples.sandbox.stamper;

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
public class StampPageXofY extends GenericTest {
    public static final String SRC = "./src/test/resources/sandbox/stamper/nameddestinations.pdf";
    public static final String DEST = "./target/test/resources/sandbox/stamper/stamp_page_x_of_y.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new StampPageXofY().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(
                new FileInputStream(SRC)), new PdfWriter(new FileOutputStream(DEST)));
        Document doc = new Document(pdfDoc);

        int n = pdfDoc.getNumOfPages();
        for (int i = 1; i <= n; i++) {
            doc.showTextAligned(new Paragraph(String.format("page %s of %s", i, n)),
                    559, 806, i, Property.HorizontalAlignment.RIGHT, Property.VerticalAlignment.TOP, 0);
        }
        pdfDoc.close();
    }
}
