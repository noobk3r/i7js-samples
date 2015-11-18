package com.itextpdf.samples.book.part1.chapter01;

import com.itextpdf.basics.geom.PageSize;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.Property;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.samples.GenericTest;

import java.io.FileOutputStream;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class Listing_01_08_HelloWorldMirroredMarginsTop extends GenericTest {
    static public final String DEST =
            "./target/test/resources/book/part1/chapter01/Listing_01_08_HelloWorldMirroredMarginsTop.pdf";

    public static void main(String[] args) throws Exception {
        new Listing_01_08_HelloWorldMirroredMarginsTop().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        //Initialize writer
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);

        //Initialize document
        PdfDocument pdfDoc = new PdfDocument(writer);
        PageSize customPageSize = PageSize.A5.clone();
        // TODO document.setMarginMirroringTopBottom(true);
        Document doc = new Document(pdfDoc, customPageSize);
        doc.setMargins(108, 72, 180, 36);

        doc.add(new Paragraph(
                "The left margin of this odd page is 36pt (0.5 inch); " +
                        "the right margin 72pt (1 inch); " +
                        "the top margin 108pt (1.5 inch); " +
                        "the bottom margin 180pt (2.5 inch)."));
        Paragraph paragraph = new Paragraph().setHorizontalAlignment(Property.HorizontalAlignment.JUSTIFIED);
        for (int i = 0; i < 20; i++) {
            paragraph.add("Hello World! Hello People! " +
                    "Hello Sky! Hello Sun! Hello Moon! Hello Stars!");
        }
        doc.add(paragraph);
        doc.add(new Paragraph(
                "The top margin 180pt (2.5 inch)."));

        //Close document
        doc.close();
    }
}
