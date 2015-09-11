package com.itextpdf.samples.book.chapter15;

import com.itextpdf.basics.font.FontConstants;
import com.itextpdf.canvas.PdfCanvas;
import com.itextpdf.core.font.PdfFont;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfName;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.pdf.layer.PdfLayer;
import com.itextpdf.core.pdf.layer.PdfLayerMembership;
import com.itextpdf.core.pdf.layer.PdfVisibilityExpression;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.samples.GenericTest;
import org.junit.experimental.categories.Category;

import java.io.FileOutputStream;
import java.io.IOException;

@Category(SampleTest.class)
public class Listing_15_07_LayerMembershipExample2 extends GenericTest {

    static public final String DEST = "./target/test/resources/Listing_15_07_LayerMembershipExample2.pdf";

    public static void main(String args[]) throws IOException {
        new Listing_15_07_LayerMembershipExample2().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException {
        //Initialize writer
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);

        //Initialize document
        PdfDocument pdfDoc = new PdfDocument(writer);

        PdfCanvas canvas = new PdfCanvas(pdfDoc.addNewPage()).setFontAndSize(PdfFont.createStandardFont(pdfDoc, FontConstants.HELVETICA), 18);

        PdfLayer dog = new PdfLayer("layer 1", pdfDoc);
        PdfLayer tiger = new PdfLayer("layer 2", pdfDoc);
        PdfLayer lion = new PdfLayer("layer 3", pdfDoc);
        PdfLayerMembership cat = new PdfLayerMembership(pdfDoc);
        PdfVisibilityExpression ve1 = new PdfVisibilityExpression(PdfName.Or);
        ve1.addOperand(tiger);
        ve1.addOperand(lion);
        cat.setVisibilityExpression(ve1);
        PdfLayerMembership no_cat = new PdfLayerMembership(pdfDoc);
        PdfVisibilityExpression ve2 = new PdfVisibilityExpression(PdfName.Not);
        ve2.addOperand(ve1);
        no_cat.setVisibilityExpression(ve2);
        canvas.beginLayer(dog).beginText().moveText(50, 725).showText("dog").endText().endLayer();
        canvas.beginLayer(tiger).beginText().moveText(50, 700).showText("tiger").endText().endLayer();
        canvas.beginLayer(lion).beginText().moveText(50, 675).showText("lion").endText().endLayer();
        canvas.beginLayer(cat).beginText().moveText(50, 650).showText("cat").endText().endLayer();
        canvas.beginLayer(no_cat).beginText().moveText(50, 650).showText("no cat").endText().endLayer();


        //Close document
        pdfDoc.close();
    }

}
