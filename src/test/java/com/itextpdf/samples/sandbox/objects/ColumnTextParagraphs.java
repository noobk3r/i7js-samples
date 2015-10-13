/**
 * Example written by Bruno Lowagie in answer to:
 * http://stackoverflow.com/questions/19976343/how-to-set-the-paragraph-of-itext-pdf-file-as-rectangle-with-background-color-in
 */
package com.itextpdf.samples.sandbox.objects;

import com.itextpdf.basics.geom.Rectangle;
import com.itextpdf.canvas.PdfCanvas;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class ColumnTextParagraphs extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/objects/column_text_paragraphs.pdf";
    public static final String TEXT = "This is some long paragraph " +
            "that will be added over and over again to prove a point.";
    public static final Rectangle[] COLUMNS = {
            new Rectangle(36, 36, 290 - 36, 806 - 36),
            new Rectangle(305, 36, 559 - 305, 806 - 36)
    };

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new ColumnTextParagraphs().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(new FileOutputStream(dest)));
        Document doc = new Document(pdfDoc);
        pdfDoc.addNewPage();
        PdfCanvas canvas = new PdfCanvas(pdfDoc.getFirstPage());
// TODO Due to absence of ColumnText we cannot perform this sample because of not knowing paragraphs' positions before rendering
//        int side_of_the_page = 0;
//        int paragraphs = 0;
//        while (paragraphs < 30) {
//            doc.add(new Paragraph(String.format("Paragraph %s: %s", ++paragraphs, TEXT))
//                    .setWidth(COLUMNS[side_of_the_page].getWidth())
//                    .setHeight(COLUMNS[side_of_the_page].getHeight())
//                    .setMarginLeft(COLUMNS[side_of_the_page].getLeft()));
//            while (ColumnText.hasMoreText(ct.go())) {
//                if (side_of_the_page == 0) {
//                    side_of_the_page = 1;
//                    canvas.moveTo(297.5f, 36);
//                    canvas.lineTo(297.5f, 806);
//                    canvas.stroke();
//                }
//                else {
//                    side_of_the_page = 0;
//                    document.newPage();
//                }
//                ct.setSimpleColumn(COLUMNS[side_of_the_page]);
//            }
//        }
        doc.close();
    }
}
