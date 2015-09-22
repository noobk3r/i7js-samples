/**
 * Example written by Bruno Lowagie in answer to:
 * http://stackoverflow.com/questions/27020542/rotating-pdf-90-degrees-using-itextsharp-in-c-sharp
 */
package com.itextpdf.samples.sandbox.stamper;

import com.itextpdf.core.pdf.*;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.samples.GenericTest;
import org.junit.Ignore;
import org.junit.experimental.categories.Category;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

@Ignore
@Category(SampleTest.class)
public class Rotate90Degrees extends GenericTest {
    public static final String SRC = "./src/test/resources/sandbox/stamper/pages.pdf";
    public static final String DEST = "./target/test/resources/sandbox/stamper/rotate90degrees.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new Rotate90Degrees().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(
                new FileInputStream(SRC)), new PdfWriter(new FileOutputStream(DEST)));

        int n = pdfDoc.getNumOfPages();
        PdfPage page;
        PdfNumber rotate;
        for (int p = 1; p <= n; p++) {
            page = pdfDoc.getPage(p);
            // TODO Implement PdfName.ROTATE and its usage
            // TODO or PageSize getPageSize() or summat
//            rotate = page.getPageSize().
//            if (rotate == null) {
//                page.put(PdfName.ROTATE, new PdfNumber(90));
//            }
//            else {
//                page.put(PdfName.ROTATE, new PdfNumber((rotate.intValue() + 90) % 360));
//            }
        }
        pdfDoc.close();
    }
}
