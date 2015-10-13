package com.itextpdf.samples.sandbox.objects;

import com.itextpdf.basics.geom.PageSize;
import com.itextpdf.canvas.PdfCanvas;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class Grid extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/objects/grid.pdf";

    public static void main(String[] args) throws IOException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new FullDottedLine().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(new FileOutputStream(DEST)));
        PageSize pageSize = new PageSize(612, 792);
        Document doc = new Document(pdfDoc, pageSize);

        PdfCanvas canvas = new PdfCanvas(pdfDoc.addNewPage());
        for (float x = 0; x < pageSize.getWidth(); ) {
            for (float y = 0; y < pageSize.getHeight(); ) {
                canvas.circle(x, y, 1f);
                y += 72f;
            }
            x += 72f;
        }
        canvas.fill();

        doc.close();
    }
}
