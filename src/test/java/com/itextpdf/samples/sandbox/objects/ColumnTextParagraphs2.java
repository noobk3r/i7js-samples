/**
 * Example written by Bruno Lowagie in answer to:
 * http://stackoverflow.com/questions/29378407/how-can-you-eliminate-white-space-in-multiple-columns-using-itextsharp
 */
package com.itextpdf.samples.sandbox.objects;

import com.itextpdf.basics.geom.Rectangle;
import com.itextpdf.core.pdf.canvas.PdfCanvas;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.layout.LayoutArea;
import com.itextpdf.model.layout.LayoutResult;
import com.itextpdf.model.renderer.DocumentRenderer;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class ColumnTextParagraphs2 extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/objects/column_text_paragraphs2.pdf";
    public static final String TEXT = "This is some long paragraph " +
            "that will be added over and over again to prove a point.";
    public static final float COLUMN_WIDTH = 254;
    public static final float ERROR_MARGIN = 16;
    public static final Rectangle[] COLUMNS = {
            new Rectangle(36, 36, 36 + COLUMN_WIDTH, 806),
            new Rectangle(305, 36, 305 + COLUMN_WIDTH, 806)
    };

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new ColumnTextParagraphs2().manipulatePdf(DEST);
    }

    // TODO Smth like ColumnText simulation is needed here. See stackoverflow link provided above to find what is this sample exactly about
    public void manipulatePdf(String dest) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(new FileOutputStream(dest)));
        final Document doc = new Document(pdfDoc);

        doc.setRenderer(new DocumentRenderer(doc) {
            int nextAreaNumber = 0;
            int currentPageNumber;

            @Override
            public LayoutArea getNextArea(LayoutResult overflowResult) {
                if (nextAreaNumber % 2 == 0) {
                    currentPageNumber = super.getNextArea(overflowResult).getPageNumber();
                } else {
                    new PdfCanvas(document.getPdfDocument(), document.getPdfDocument().getNumberOfPages())
                            .moveTo(297.5f, 36)
                            .lineTo(297.5f, 806)
                            .stroke();
                }
                return (currentArea = new LayoutArea(currentPageNumber, COLUMNS[nextAreaNumber++ % 2].clone()));
            }
        });

        pdfDoc.addNewPage();
        int paragraphs = 0;
        while (paragraphs < 30) {
            doc.add(new Paragraph(String.format("Paragraph %s: %s", ++paragraphs, TEXT)));
        }
        doc.close();
    }
}
