/**
 * This example was written by Bruno Lowagie in answer to the following question:
 * http://stackoverflow.com/questions/26648462/how-to-delete-attachment-of-pdf-using-itext
 * This is part one, there's also a part two named RemoveEmbeddedFile (or RemoveEmbeddedFiles)
 */
package com.itextpdf.samples.sandbox.annotations;

import com.itextpdf.basics.geom.Rectangle;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.pdf.annot.PdfFileAttachmentAnnotation;
import com.itextpdf.core.pdf.filespec.PdfFileSpec;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
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
        PdfFileSpec spec = PdfFileSpec.createEmbeddedFileSpec(pdfDoc, "Some test".getBytes(), "test.txt", true);
        // TODO What if i don't like to show annotation? one cannot pass null as the argument (Rectangle)
        PdfFileAttachmentAnnotation fileAttach = new PdfFileAttachmentAnnotation(pdfDoc, new Rectangle(0, 0), spec);
        pdfDoc.getFirstPage().addAnnotation(fileAttach);
        pdfDoc.close();
    }
}
