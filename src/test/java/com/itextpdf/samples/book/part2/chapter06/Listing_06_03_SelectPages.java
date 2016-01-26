package com.itextpdf.samples.book.part2.chapter06;

import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.samples.GenericTest;
import com.itextpdf.test.annotations.type.SampleTest;

import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class Listing_06_03_SelectPages extends GenericTest {
    public static final String DEST =
            "./target/test/resources/book/part2/chapter06/Listing_06_03_SelectPages_stamper.pdf";
    public static final String DEST2 =
            "./target/test/resources/book/part2/chapter06/Listing_06_03_SelectPages_copy.pdf";
    public static final String MOVIE_TEMPLATES =
            "./src/test/resources/book/part1/chapter03/cmp_Listing_03_29_MovieTemplates.pdf";

    public static void main(String args[]) throws IOException {
        new Listing_06_03_SelectPages().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException {
        PdfReader reader = new PdfReader(MOVIE_TEMPLATES);
        PdfDocument pdfDoc = new PdfDocument(reader);
        // TODO No selectPages in order to reduce the size of the document to be read
        // reader.selectPages("4-8");
        manipulateWithStamper(reader);
        manipulateWithCopy(reader);
        pdfDoc.close();
    }

    private static void manipulateWithStamper(PdfReader reader) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(reader, new PdfWriter(new FileOutputStream(DEST)));
        pdfDoc.setCloseReader(false);
        pdfDoc.close();
    }

    private static void manipulateWithCopy(PdfReader reader) throws IOException {
        PdfDocument srcDoc = new PdfDocument(reader);
        PdfDocument copy = new PdfDocument(new PdfWriter(DEST2));
        srcDoc.copyPages(1, srcDoc.getNumberOfPages(), copy);
        copy.close();
        srcDoc.setCloseReader(true);
        srcDoc.close();
    }
}
