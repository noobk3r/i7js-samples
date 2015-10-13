/**
 * Example written by Bruno Lowagie in answer to:
 * http://stackoverflow.com/questions/27752409/using-itext-to-draw-separator-line-as-continuous-hypen-in-a-table-row
 */
package com.itextpdf.samples.sandbox.objects;

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
public class CustomDashedLine extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/objects/custom_dashed_line.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new CustomDashedLine().manipulatePdf(DEST);
    }

    // TODO There is no DottedLineSeparator
//    class CustomDashedLineSeparator extends DottedLineSeparator {
//        protected float dash = 5;
//        protected float phase = 2.5f;
//
//        public float getDash() {
//            return dash;
//        }
//
//        public float getPhase() {
//            return phase;
//        }
//
//        public void setDash(float dash) {
//            this.dash = dash;
//        }
//
//        public void setPhase(float phase) {
//            this.phase = phase;
//        }
//
//        public void draw(PdfContentByte canvas, float llx, float lly, float urx, float ury, float y) {
//            canvas.saveState();
//            canvas.setLineWidth(lineWidth);
//            canvas.setLineDash(dash, gap, phase);
//            drawLine(canvas, llx, urx, y);
//            canvas.restoreState();
//        }
//    }

    public void manipulatePdf(String dest) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(new FileOutputStream(dest)));
        Document doc = new Document(pdfDoc);
        doc.add(new Paragraph("Before dashed line"));
        // CustomDashedLineSeparator separator = new CustomDashedLineSeparator();
        // separator.setDash(10);
        // separator.setGap(7);
        // separator.setLineWidth(3);
        // Paragraph linebreak = new Paragraph(separator);
        // doc.add(linebreak);
        doc.add(new Paragraph("After dashed line"));
        doc.close();
    }
}
