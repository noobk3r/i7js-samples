package com.itextpdf.samples.book.part2.chapter06;

import com.itextpdf.basics.font.FontConstants;
import com.itextpdf.basics.font.PdfEncodings;
import com.itextpdf.basics.geom.PageSize;
import com.itextpdf.basics.image.ImageFactory;
import com.itextpdf.canvas.PdfCanvas;
import com.itextpdf.canvas.PdfCanvasConstants;
import com.itextpdf.core.color.DeviceRgb;
import com.itextpdf.core.font.PdfFont;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfPage;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.pdf.xobject.PdfFormXObject;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.Property;
import com.itextpdf.model.element.AreaBreak;
import com.itextpdf.model.element.Image;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.samples.GenericTest;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class Listing_06_07_Superimposing extends GenericTest {
    public static final String DEST
            = "./target/test/resources/book/part2/chapter06/Listing_06_07_Superimposing.pdf";
    public static final String SOURCE
            = "./target/test/resources/book/part2/chapter06/Listing_06_07_Superimposing_opening.pdf";
    public static final String RESOURCE
            = "./src/test/resources/book/part2/chapter06/loa.jpg";

    // No POST_CARD constant in itext6
    public static final PageSize postCard = new PageSize(283, 416);

    public static void main(String args[]) throws IOException, SQLException {
        new Listing_06_07_Superimposing().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException, SQLException {
        // First create the original file
        createOriginalPdf(SOURCE);

        // Initialize source document
        PdfReader reader = new PdfReader(SOURCE);
        PdfDocument srcDoc = new PdfDocument(reader);

        // Initialize result document
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument resultDoc = new PdfDocument(writer);
        Document doc = new Document(resultDoc, postCard);

        PdfFont font = PdfFont.createFont(resultDoc, FontConstants.ZAPFDINGBATS, PdfEncodings.WINANSI, true);
        PdfCanvas canvas = new PdfCanvas(resultDoc.addNewPage());
        for (int i = 1; i <= srcDoc.getNumOfPages(); i++) {
            PdfFormXObject layer = srcDoc.getPage(i).copyAsFormXObject(resultDoc);
            canvas.addXObject(layer, 1f, 0, 0, 1, 0, 0);
        }

        // Close documents
        doc.close();
        srcDoc.close();
    }

    public void createOriginalPdf(String filename) throws IOException {
        // Initialize document
        FileOutputStream fos = new FileOutputStream(filename);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc, postCard);
        doc.setMargins(30, 30, 30, 30);

        // Page 1: a rectangle
        PdfPage page = pdfDoc.addNewPage();
        PdfCanvas under = new PdfCanvas(page.newContentStreamBefore(), page.getResources(), pdfDoc);
        under.setFillColor(new DeviceRgb(0xFF, 0xD7, 0x00));
        under.rectangle(5, 5, postCard.getWidth() - 10, postCard.getHeight() - 10);
        under.fillStroke();

        doc.add(new AreaBreak());

        // Page 2: an image
        Image img = new Image(ImageFactory.getImage(RESOURCE));
        img.setFixedPosition((postCard.getWidth() - img.getImageScaledWidth()) / 2,
                (postCard.getHeight() - img.getImageScaledHeight()) / 2);
        doc.add(img);
        doc.add(new AreaBreak());

        // Page 3: the words "Foobar Film Festival"
        Paragraph p = new Paragraph("Foobar Film Festival")
                .setFont(PdfFont.createStandardFont(pdfDoc, FontConstants.HELVETICA))
                .setFontSize(22)
                .setHorizontalAlignment(Property.HorizontalAlignment.CENTER);
        doc.add(p);
        doc.add(new AreaBreak());

        // Page 4: the words "SOLD OUT"
        page = pdfDoc.getLastPage();
        PdfCanvas over = new PdfCanvas(page.newContentStreamAfter(), page.getResources(), pdfDoc);
        over.saveState();
        float sinus = (float) Math.sin(Math.PI / 60);
        float cosinus = (float) Math.cos(Math.PI / 60);
        over.beginText();
        over.setTextRenderingMode(PdfCanvasConstants.TextRenderingMode.FILL_STROKE);
        over.setLineWidth(1.5f);
        over.setStrokeColor(new DeviceRgb(0xFF, 0x00, 0x00));
        over.setFillColor(new DeviceRgb(0xFF, 0xFF, 0xFF));
        over.setFontAndSize(PdfFont.createStandardFont(pdfDoc, FontConstants.HELVETICA), 36);
        over.setTextMatrix(cosinus, sinus, -sinus, cosinus, 50, 324);
        over.showText("SOLD OUT");
        over.setTextMatrix(0, 0);
        over.endText();
        over.restoreState();

        doc.close();
    }
}
