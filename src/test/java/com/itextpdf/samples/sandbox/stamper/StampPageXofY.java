/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

package com.itextpdf.samples.sandbox.stamper;

import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.model.Document;
import com.itextpdf.model.Property;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.samples.GenericTest;
import com.itextpdf.test.annotations.type.SampleTest;

import java.io.File;

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
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(SRC), new PdfWriter(DEST));
        Document doc = new Document(pdfDoc);
        int n = pdfDoc.getNumberOfPages();
        for (int i = 1; i <= n; i++) {
            doc.showTextAligned(new Paragraph(String.format("page %s of %s", i, n)),
                    559, 806, i, Property.TextAlignment.RIGHT, Property.VerticalAlignment.TOP, 0);
        }
        doc.close();
    }
}
