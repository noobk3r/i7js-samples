/**
 * This example was written by Bruno Lowagie in answer to the following question:
 * http://stackoverflow.com/questions/27906725/itext-placement-of-phrase-within-columntext
 */
package com.itextpdf.samples.sandbox.objects;

import com.itextpdf.basics.geom.Rectangle;
import com.itextpdf.canvas.PdfCanvas;
import com.itextpdf.canvas.color.Color;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class ColumnTextAscender extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/objects/column_text_ascender.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new ColumnTextAscender().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(new FileOutputStream(dest)));
        Document doc = new Document(pdfDoc);

        pdfDoc.addNewPage();
        Rectangle rect = new Rectangle(50, 750, 250, 800);
        addColumn(pdfDoc, rect, false);
        rect = new Rectangle(300, 750, 500, 800);
        addColumn(pdfDoc, rect, true);

        doc.close();
    }

    public void addColumn(PdfDocument pdfDoc, Rectangle rect, boolean useAscender) {
        PdfCanvas canvas = new PdfCanvas(pdfDoc.getFirstPage());
        canvas.setLineWidth(0.5f).setStrokeColor(Color.RED).rectangle(rect).stroke();
        Paragraph p = new Paragraph("This text is added at the top of the column.");
        // TODO Tehre is no ColumnText therefore there is no setUseAscender(boolean)
        // ColumnText ct = new ColumnText(canvas);
        // ct.setSimpleColumn(rect);
        // ct.setUseAscender(useAscender);
        new Document(pdfDoc).add(p
                .setWidth(rect.getWidth())
                .setHeight(rect.getHeight())
                .setMarginLeft(rect.getLeft()));
        // ct.addText(p);
        //ct.go();
    }
}
