package com.itextpdf.samples.book.part2.chapter06;

import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.forms.PdfPageFormCopier;
import com.itextpdf.samples.GenericTest;
import com.itextpdf.samples.book.part1.chapter03.Listing_03_29_MovieTemplates;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class Listing_06_23_Burst extends GenericTest {
    public static final String DEST = "./target/test/resources/book/part2/chapter06/Listing_06_23_Burst.pdf";
    public static final String FORMATTEDDEST = "./target/test/resources/book/part2/chapter06/Listing_06_23_Burst%d.pdf";

    public static void main(String args[]) throws IOException, SQLException {
        new Listing_06_23_Burst().manipulatePdf(FORMATTEDDEST);
    }

    public void manipulatePdf(String dest) throws IOException, SQLException {
        new Listing_03_29_MovieTemplates().manipulatePdf(Listing_03_29_MovieTemplates.DEST);

        FileInputStream fis = new FileInputStream(Listing_03_29_MovieTemplates.DEST);
        PdfReader reader = new PdfReader(fis);
        PdfDocument srcDoc = new PdfDocument(reader);

        for (int i = 1; i <= srcDoc.getNumOfPages(); i++) {
            FileOutputStream fos = new FileOutputStream(String.format(FORMATTEDDEST, i));
            PdfWriter writer = new PdfWriter(fos);
            PdfDocument pdfDoc = new PdfDocument(writer);

            srcDoc.copyPages(i, i, pdfDoc, new PdfPageFormCopier());
            pdfDoc.close();
        }

        // Make file to compare via CompareTool
        String[] names = new String[srcDoc.getNumOfPages()];
        for (int i = 0; i < names.length; i++) {
            names[i] = String.format(FORMATTEDDEST, i+1);
        }
        srcDoc.close();
        // Create file to compare via CompareTool
        concatenateResults(DEST, names);
    }

    // Only for testing reasons
    protected void concatenateResults(String dest, String[] names) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
        PdfDocument tempDoc;
        for (String name : names) {
            tempDoc = new PdfDocument(new PdfReader(name));
            tempDoc.copyPages(1, tempDoc.getNumOfPages(), pdfDoc);
            tempDoc.close();
        }
        pdfDoc.close();
    }
}
