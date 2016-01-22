/**
 * This example was written by Bruno Lowagie in answer to the following question:
 * http://stackoverflow.com/questions/27823189/add-multiple-attachments-in-a-pdf-using-itext-pdf-stamper
 */
package com.itextpdf.samples.sandbox.annotations;

import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.pdf.filespec.PdfFileSpec;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.samples.GenericTest;
import org.junit.experimental.categories.Category;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

@Category(SampleTest.class)
public class AddEmbeddedFiles extends GenericTest {
    public static final String SRC = "./src/test/resources/sandbox/annotations/hello.pdf";
    public static final String DEST = "./target/test/resources/sandbox/annotations/add_embedded_files.pdf";
    public static final String[] ATTACHMENTS = {
            "hello", "world", "what", "is", "up"
    };

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new AddEmbeddedFiles().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(new FileInputStream(SRC)),
                new PdfWriter(new FileOutputStream(DEST)));
        for (String s : ATTACHMENTS) {
            PdfFileSpec spec = PdfFileSpec.createEmbeddedFileSpec(pdfDoc,
                    String.format("Some test: %s", s).getBytes(), null, String.format("%s.txt", s), null, null, null, true);
            pdfDoc.addFileAttachment(String.format("Some test: %s", s), spec);
            //PdfFileAttachmentAnnotation fileAttach = new PdfFileAttachmentAnnotation(pdfDoc, new Rectangle(0, 0), spec);
            //pdfDoc.getFirstPage().addAnnotation(fileAttach);
        }
        pdfDoc.close();
    }
}