/**
 * Example written by Bruno Lowagie in answer to the following question:
 * http://stackoverflow.com/questions/30286601/extracting-an-embedded-object-from-a-pdf
 */
package com.itextpdf.samples.sandbox.parse;

import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfObject;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfStream;
import com.itextpdf.core.testutils.annotations.type.SampleTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class ExtractStreams {
    public static final String DEST = "./target/test/resources/sandbox/parse/extract_streams%s";
    public static final String SRC = "./src/test/resources/sandbox/parse/image.pdf";
    public static final String RES = "./src/test/resources/sandbox/parse/extract_streams%s";

    @BeforeClass
    public static void beforeClass() throws IOException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new ExtractStreams().manipulatePdf();
    }

    @Test
    public void manipulatePdf() throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(new FileInputStream(SRC)));
        PdfObject obj;
        for (int i = 1; i <= pdfDoc.getNumOfPdfObjects(); i++) {
            obj = pdfDoc.getPdfObject(i);
            if (obj != null && obj.isStream()) {
                byte[] b;
                b = ((PdfStream) obj).getBytes();
                System.out.println(b.length);
                FileOutputStream fos = new FileOutputStream(String.format(DEST, i));
                fos.write(b);
                Path path = Paths.get(String.format(RES, i));
                byte[] result = Files.readAllBytes(path);
                fos.flush();
                fos.close();
                Assert.assertArrayEquals(b, result);
            }
        }
        pdfDoc.close();
    }
}
