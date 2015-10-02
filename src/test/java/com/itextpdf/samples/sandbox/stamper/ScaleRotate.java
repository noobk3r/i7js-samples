/**
 * Example written by Bruno Lowagie in answer to:
 * http://stackoverflow.com/questions/21871027/rotating-in-itextsharp-while-preserving-comment-location-orientation
 *
 * Example that shows how to scale an existing PDF using the UserUnit and how to remove the rotation of a page.
 */
package com.itextpdf.samples.sandbox.stamper;

import com.itextpdf.core.pdf.PdfDictionary;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class ScaleRotate extends GenericTest {
    public static final String SRC = "./src/test/resources/sandbox/stamper/pages.pdf";
    public static final String DEST = "./target/test/resources/sandbox/stamper/scale_rotate.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new ScaleRotate().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(
                new FileInputStream(SRC)), new PdfWriter(new FileOutputStream(DEST)));

        int n = pdfDoc.getNumOfPages();
        PdfDictionary page;
        for (int p = 1; p <= n; p++) {
            page = pdfDoc.getPage(p).getPdfObject();
            // TODO Implement PdfName.UserUnit & PdfName.Rotate
//            if (page.getAsNumber(PdfName.UserUnit) == null)
//                page.put(PdfName.USERUNIT, new PdfNumber(2.5f));
//            page.remove(PdfName.ROTATE);
        }
        pdfDoc.close();
    }
}
