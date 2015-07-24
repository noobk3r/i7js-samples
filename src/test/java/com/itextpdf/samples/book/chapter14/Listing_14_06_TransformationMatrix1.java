package com.itextpdf.samples.book.chapter14;

import com.itextpdf.canvas.PdfCanvas;
import com.itextpdf.core.geom.PageSize;
import com.itextpdf.core.geom.Rectangle;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.samples.GenericTest;
import org.junit.Ignore;

import java.io.FileOutputStream;
import java.io.IOException;

@Ignore("Implement copy page as XObject (PdfImportedPage analogue). Then port the sample")
public class Listing_14_06_TransformationMatrix1 extends GenericTest {

    static public final String DEST = "./target/test/resources/Listing_14_06_TransformationMatrix1.pdf";
    public static final String RESOURCE = "src/test/resources/pdfs/logo.pdf";

    public static void main(String args[]) throws IOException {
        new Listing_14_06_TransformationMatrix1().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException {
        //Initialize writer
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);

        //Initialize document and add page
        PdfDocument pdfDoc = new PdfDocument(writer);
        pdfDoc.setDefaultPageSize(new PageSize(new Rectangle(-595, -842, 595 * 2, 842 * 2)));

        PdfCanvas canvas = new PdfCanvas(pdfDoc.addNewPage());
        canvas.moveTo(-595, 0).
            lineTo(595, 0).
            moveTo(0, -842).
            lineTo(0, 842).
            stroke();

        PdfDocument logoDoc = new PdfDocument(new PdfReader(RESOURCE));
        //pdfDoc
        //PdfFormXObject pageContent = new PdfFormXObject()


        //Close document
        pdfDoc.close();
    }

}
