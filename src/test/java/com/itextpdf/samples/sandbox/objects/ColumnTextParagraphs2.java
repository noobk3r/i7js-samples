/**
 * Example written by Bruno Lowagie in answer to:
 * http://stackoverflow.com/questions/29378407/how-can-you-eliminate-white-space-in-multiple-columns-using-itextsharp
 */
package com.itextpdf.samples.sandbox.objects;

import com.itextpdf.basics.geom.Rectangle;
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

    public void manipulatePdf(String dest) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(new FileOutputStream(dest)));
        Document doc = new Document(pdfDoc);

// TODO Due to absence of ColumnText we cannot perform this sample because of not knowing paragraphs' positions before rendering
//        PdfContentByte canvas = writer.getDirectContent();
//        ColumnText ct = new ColumnText(canvas);
//        addContent(ct);
//        float height = getNecessaryHeight(ct);
//        addContent(ct);
//        Rectangle left;
//        float top = COLUMNS[0].getTop();
//        float middle = (COLUMNS[0].getLeft() + COLUMNS[1].getRight()) / 2;
//        float columnheight;
//        int status = ColumnText.START_COLUMN;
//        while (ColumnText.hasMoreText(status)) {
//            if (checkHeight(height)) {
//                columnheight = COLUMNS[0].getHeight();
//                left = COLUMNS[0];
//            }
//            else {
//                columnheight = (height / 2) + ERROR_MARGIN;
//                left = new Rectangle(
//                        COLUMNS[0].getLeft(),
//                        COLUMNS[0].getTop() - columnheight,
//                        COLUMNS[0].getRight(),
//                        COLUMNS[0].getTop()
//                );
//            }
//            // left half
//            ct.setSimpleColumn(left);
//            ct.go();
//            height -= COLUMNS[0].getTop() - ct.getYLine();
//            // separator
//            canvas.moveTo(middle, top - columnheight);
//            canvas.lineTo(middle, top);
//            canvas.stroke();
//            // right half
//            ct.setSimpleColumn(COLUMNS[1]);
//            status = ct.go();
//            height -= COLUMNS[1].getTop() - ct.getYLine();
//            // new page
//            document.newPage();
//        }

        doc.close();
    }


//    public void addContent(ColumnText ct) {
//        for (int i = 0; i < 35; i++) {
//            ct.addElement(new Paragraph(String.format("Paragraph %s: %s", i, TEXT)));
//        }
//    }

//    public float getNecessaryHeight(ColumnText ct) throws DocumentException {
//        ct.setSimpleColumn(new Rectangle(0, 0, COLUMN_WIDTH, -500000));
//        ct.go(true);
//        return -ct.getYLine();
//    }

//    public boolean checkHeight(float height) {
//        height -= COLUMNS[0].getHeight() + COLUMNS[1].getHeight() + ERROR_MARGIN;
//        return height > 0;
//    }
}
