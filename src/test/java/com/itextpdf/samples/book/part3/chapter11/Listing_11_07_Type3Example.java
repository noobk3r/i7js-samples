package com.itextpdf.samples.book.part3.chapter11;

import com.itextpdf.core.font.PdfFont;
import com.itextpdf.core.pdf.canvas.PdfCanvas;
import com.itextpdf.core.font.PdfType3Font;
import com.itextpdf.core.color.DeviceGray;
import com.itextpdf.core.color.DeviceRgb;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Text;
import com.itextpdf.samples.GenericTest;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class Listing_11_07_Type3Example extends GenericTest {
    public static final String DEST = "./target/test/resources/book/part3/chapter11/Listing_11_07_Type3Example.pdf";

    public static void main(String[] agrs) throws Exception {
        new Listing_11_07_Type3Example().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
        Document doc = new Document(pdfDoc);
        PdfType3Font t3 = PdfFont.createType3Font(pdfDoc, true);

        PdfCanvas d = t3.addGlyph('D', 600, 0, 0, 600, 700);
        d.setStrokeColor(new DeviceRgb(0xFF, 0x00, 0x00));
        d.setFillColor(new DeviceGray(0.75f));
        d.setLineWidth(100);
        d.moveTo(5, 5);
        d.lineTo(300, 695);
        d.lineTo(595, 5);
        d.closePathFillStroke();

        PdfCanvas s = t3.addGlyph('S', 600, 0, 0, 600, 700);
        d.setStrokeColor(new DeviceRgb(0x00, 0x80, 0x80));
        d.setLineWidth(100);
        d.moveTo(595, 5);
        d.lineTo(5, 5);
        d.lineTo(300, 350);
        d.lineTo(5, 695);
        d.lineTo(595, 695);
        d.stroke();

        Paragraph p = new Paragraph();
        p.add("This is a String with a Type3 font that contains a fancy Delta (");
        p.add(new Text("D").setFont(t3));
        p.add(") and a custom Sigma (");
        p.add(new Text("S").setFont(t3));
        p.add(").");
        // TODO Document.add on type3-fonted text throws NullPointerException
        doc.add(p);

        // close the document
        doc.close();
    }
}
