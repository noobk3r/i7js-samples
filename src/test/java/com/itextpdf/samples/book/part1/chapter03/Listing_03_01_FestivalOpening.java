package com.itextpdf.samples.book.part1.chapter03;

import com.itextpdf.basics.font.FontConstants;
import com.itextpdf.basics.geom.PageSize;
import com.itextpdf.basics.image.ImageFactory;
import com.itextpdf.canvas.PdfCanvas;
import com.itextpdf.core.color.DeviceRgb;
import com.itextpdf.core.font.PdfFont;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.pdf.xobject.PdfImageXObject;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.Property;
import com.itextpdf.model.element.AreaBreak;
import com.itextpdf.model.element.Image;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.samples.GenericTest;

import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class Listing_03_01_FestivalOpening extends GenericTest {

    public static final String DEST = "./target/test/resources/book/part1/chapter03/Listing_03_01_FestivalOpening.pdf";

    private static final String RESOURCE = "src/test/resources/img/loa.jpg";

    private static final float pageWidth = PageSize.A6.getWidth();
    private static final float pageHeight = PageSize.A6.getHeight();

    public static void main(String args[]) throws IOException {
        new Listing_03_01_FestivalOpening().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException {
        //Initialize writer
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);

        //Initialize document
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc, PageSize.A6);

        //Initialize paragraph, add it to document, add new page, add paragraph again
        Paragraph p = new Paragraph("Foobar Film Festival").
                        setTextAlignment(Property.TextAlignment.CENTER).
                setFont(PdfFont.createStandardFont(pdfDoc, FontConstants.HELVETICA)).
                setFontSize(22);

        PdfImageXObject imageXObject = new PdfImageXObject(ImageFactory.getImage(RESOURCE));
        Image img = new Image(imageXObject);
        img.setFixedPosition((pageWidth - imageXObject.getWidth()) / 2, (pageHeight - imageXObject.getHeight()) / 2);

        doc.add(img).add(p);

        // AreaBreak is used to move to the next page
        doc.add(new AreaBreak()).add(img).add(p);

        //Initialize canvas and write to it
        PdfCanvas canvas = new PdfCanvas(pdfDoc.getLastPage());
        float sine = (float) Math.sin(Math.PI / 60);
        float cosine = (float) Math.cos(Math.PI / 60);
        canvas.saveState().beginText().setTextRenderingMode(2).
                setLineWidth(1.5f).setFillColor(DeviceRgb.WHITE).setStrokeColor(DeviceRgb.RED).
                setFontAndSize(PdfFont.createStandardFont(pdfDoc, FontConstants.HELVETICA), 36).setTextMatrix(cosine, sine, -sine, cosine, 50, 324).
                showText("SOLD OUT").endText().restoreState();

        //Initialize "under" canvas and write to it
        PdfCanvas underCanvas = new PdfCanvas(pdfDoc.getLastPage().newContentStreamBefore(), pdfDoc.getLastPage().getResources(), pdfDoc);
        underCanvas.saveState().setFillColor(new DeviceRgb(0xFF, 0xD7, 0x00)).
                rectangle(5, 5, pageWidth - 10, pageHeight - 10).fill().restoreState();
        canvas.release();
        //Close document
        doc.close();
    }

}
