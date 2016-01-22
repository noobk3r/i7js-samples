package com.itextpdf.samples.book.part4.chapter15;

import com.itextpdf.basics.font.FontConstants;
import com.itextpdf.core.pdf.canvas.PdfCanvas;
import com.itextpdf.core.font.PdfFont;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfName;
import com.itextpdf.core.pdf.PdfVersion;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.pdf.layer.PdfLayer;
import com.itextpdf.core.pdf.layer.PdfLayerMembership;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.samples.GenericTest;

import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class Listing_15_06_LayerMembershipExample1 extends GenericTest {
    public static final String DEST
            = "./target/test/resources/book/part4/chapter15/Listing_15_06_LayerMembershipExample1.pdf";

    public static void main(String args[]) throws IOException {
        new Listing_15_06_LayerMembershipExample1().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException {
        //Initialize writer
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);

        //Initialize document
        PdfDocument pdfDoc = new PdfDocument(writer, PdfVersion.PDF_1_5);

        PdfCanvas canvas = new PdfCanvas(pdfDoc.addNewPage())
                        .setFontAndSize(PdfFont.createStandardFont(FontConstants.HELVETICA), 18);

        PdfLayer dog = new PdfLayer("layer 1", pdfDoc);
        PdfLayer tiger = new PdfLayer("layer 2", pdfDoc);
        PdfLayer lion = new PdfLayer("layer 3", pdfDoc);
        PdfLayerMembership cat = new PdfLayerMembership(pdfDoc);
        cat.addLayer(tiger);
        cat.addLayer(lion);
        PdfLayerMembership no_cat = new PdfLayerMembership(pdfDoc);
        no_cat.addLayer(tiger);
        no_cat.addLayer(lion);
        no_cat.setVisibilityPolicy(PdfName.AllOff);
        canvas.beginLayer(dog).beginText().moveText(50, 725).showText("dog").endText().endLayer();
        canvas.beginLayer(tiger).beginText().moveText(50, 700).showText("tiger").endText().endLayer();
        canvas.beginLayer(lion).beginText().moveText(50, 675).showText("lion").endText().endLayer();
        canvas.beginLayer(cat).beginText().moveText(50, 650).showText("cat").endText().endLayer();
        canvas.beginLayer(no_cat).beginText().moveText(50, 650).showText("no cat").endText().endLayer();

        //Close document
        pdfDoc.close();
    }
}
