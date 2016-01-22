/**
 * This example was written by Bruno Lowagie in answer to the following question:
 * http://stackoverflow.com/questions/26648462/how-to-delete-attachment-of-pdf-using-itext
 * This is part one, there's also a part two named RemoveEmbeddedFile (or RemoveEmbeddedFiles)
 */
package com.itextpdf.samples.sandbox.annotations;

import com.itextpdf.core.pdf.*;
import com.itextpdf.core.pdf.filespec.PdfFileSpec;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.samples.GenericTest;
import org.junit.experimental.categories.Category;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

@Category(SampleTest.class)
public class AddEmbeddedFile extends GenericTest {
    public static final String SRC = "./src/test/resources/sandbox/annotations/hello.pdf";
    public static final String DEST = "./target/test/resources/sandbox/annotations/add_embedded_file.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new AddEmbeddedFile().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(new FileInputStream(SRC)),
                new PdfWriter(new FileOutputStream(DEST)));
        PdfFileSpec spec = PdfFileSpec.createEmbeddedFileSpec(pdfDoc, "Some test".getBytes(), null, "test.txt", null, null, null, true);
        pdfDoc.addFileAttachment("some_test", spec);
        pdfDoc.close();
    }
}
