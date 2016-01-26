/**
 * Example written by Bruno Lowagie in answer to
 * http://stackoverflow.com/questions/28554413/how-to-add-overlay-text-with-link-annotations-to-existing-pdf
 */
package com.itextpdf.samples.sandbox.annotations;

import com.itextpdf.basics.font.FontConstants;
import com.itextpdf.basics.geom.AffineTransform;
import com.itextpdf.core.font.PdfFontFactory;
import com.itextpdf.core.pdf.canvas.PdfCanvas;
import com.itextpdf.core.font.PdfFont;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.Property;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Text;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class AddLinkAnnotation4 extends GenericTest {
    public static final String SRC = "./src/test/resources/sandbox/annotations/hello.pdf";
    public static final String DEST = "./target/test/resources/sandbox/annotations/add_link_annotation4.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new AddLinkAnnotation4().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(new FileInputStream(SRC)),
                new PdfWriter(new FileOutputStream(DEST)));
        AffineTransform transform = AffineTransform.getRotateInstance((float) Math.PI / 2);
        // stamper.getWriter().setPageEvent(new AddAnnotation(stamper, transform));
        PdfCanvas canvas = new PdfCanvas(pdfDoc.getFirstPage());
        PdfFont bold = PdfFontFactory.createStandardFont(FontConstants.HELVETICA_BOLD);
        Text chunk = new Text("The Best iText Questions on StackOverflow").setFont(bold).setFontSize(12);
        // TODO Implement setGenericTag
        //chunk.setGenericTag("http://pages.itextpdf.com/ebook-stackoverflow-questions.html");
        Paragraph p = new Paragraph("Download ");
        p.add(chunk);
        p.add(" and discover more than 200 questions and answers.");
        canvas.saveState();
        float[] transformMatrix = null;
        transform.getMatrix(transformMatrix);
        canvas.concatMatrix(transformMatrix[0], transformMatrix[1], transformMatrix[2],
                transformMatrix[3], transformMatrix[4], transformMatrix[5]);
        // TODO showText with canvas in order to use new matrix
        new Document(pdfDoc).showTextAligned(p, 36, -559, 1, Property.TextAlignment.LEFT,
                Property.VerticalAlignment.MIDDLE, 0);
//        ColumnText ct = new ColumnText(canvas);
//        ct.setSimpleColumn(36, -559, 806, -36);
//        ct.addText(p);
//        ct.go();
        canvas.restoreState();
        pdfDoc.close();
    }

    // TODO there is no renderer with PdfPageEventHelper features
//    public class AddAnnotation extends PdfPageEventHelper {
//        protected PdfStamper stamper;
//        protected AffineTransform transform;
//
//        public AddAnnotation(PdfStamper stamper, AffineTransform transform) {
//            this.stamper = stamper;
//            this.transform = transform;
//        }
//
//        @Override
//        public void onGenericTag(PdfWriter writer, Document document, Rectangle rect, String text) {
//            float[] pts = {rect.getLeft(), rect.getBottom(), rect.getRight(), rect.getTop()};
//            transform.transform(pts, 0, pts, 0, 2);
//            float[] dstPts = {pts[0], pts[1], pts[2], pts[3]};
//            rect = new Rectangle(dstPts[0], dstPts[1], dstPts[2], dstPts[3]);
//            PdfAnnotation annot = PdfAnnotation.createLink(writer, rect,
//                      PdfAnnotation.HIGHLIGHT_INVERT, new PdfAction(text));
//            annot.setBorder(new PdfBorderArray(0, 0, 0));
//            stamper.addAnnotation(annot, 1);
//        }
//    }
}
