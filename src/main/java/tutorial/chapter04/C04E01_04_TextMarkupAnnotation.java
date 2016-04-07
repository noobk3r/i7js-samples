/*
 * This example is part of the iText 7 tutorial.
 */
package tutorial.chapter04;

import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.kernel.pdf.annot.PdfTextMarkupAnnotation;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Property;
import com.itextpdf.layout.element.Paragraph;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Simple text markup annotation example.
 */
public class C04E01_04_TextMarkupAnnotation {

    public static final String DEST = "results/chapter04/textmarkup_annotation.pdf";

    public static void main(String args[]) throws IOException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new C04E01_04_TextMarkupAnnotation().createPdf(DEST);
    }

    public void createPdf(String dest) throws IOException {

        //Initialize PDF writer
        OutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);

        //Initialize PDF document
        PdfDocument pdfDoc = new PdfDocument(writer);

        //Initialize document
        Document doc = new Document(pdfDoc);

        Paragraph p = new Paragraph("The example of text markup annotation.");
        doc.showTextAligned(p, 20, 795, 1, Property.TextAlignment.LEFT,
                Property.VerticalAlignment.MIDDLE, 0);

        //Create text markup annotation
        PdfTextMarkupAnnotation ann = PdfTextMarkupAnnotation.createHighLight(new Rectangle(105, 790, 64, 10),
                new float[]{169, 790, 105, 790, 169, 800, 105, 800})
                .setColor(Color.YELLOW)
                .setTitle(new PdfString("Hello!"))
                .setContents(new PdfString("I'm a popup."))
                .setTitle(new PdfString("iText"))
                .setOpen(true)
                .setRectangle(new PdfArray(new float[]{100, 600, 200, 100}));
        pdfDoc.getFirstPage().addAnnotation(ann);

        //Close document
        doc.close();

    }
}