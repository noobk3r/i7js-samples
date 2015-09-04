package com.itextpdf.samples.book.chapter03;

import com.itextpdf.basics.image.Image;
import com.itextpdf.basics.image.ImageFactory;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.samples.GenericTest;
import org.junit.Ignore;
import org.junit.experimental.categories.Category;

import java.io.FileOutputStream;

@Ignore("Wait for image refactoring - now there is no way to know image width and height without PdfImageXObject creation")
@Category(SampleTest.class)
public class Listing_03_24_ImageSkew extends GenericTest {

    public static final String DEST = "./target/test/resources/Listing_03_24_ImageSkew.pdf";

    public static final String RESOURCE = "src/test/resources/img/loa.jpg";

    public static void main(String[] args) throws Exception {
        new Listing_03_24_ImageSkew().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        //Initialize writer
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);

        //Initialize document and add page
        PdfDocument pdfDoc = new PdfDocument(writer);

        Image img = ImageFactory.getImage(RESOURCE);
        //new PdfCanvas(pdfDoc.addNewPage()).addImage(img, )

        //Close document
        pdfDoc.close();
    }
}
