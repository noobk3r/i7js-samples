/**
 * This example was written by Bruno Lowagie in answer to the following question:
 * http://stackoverflow.com/questions/26648462/how-to-delete-attachment-of-pdf-using-itext
 * (This is part two, there's also a part one named AddEmbeddedFile)
 */
package com.itextpdf.samples.sandbox.annotations;

import com.itextpdf.core.pdf.*;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.samples.GenericTest;
import org.junit.experimental.categories.Category;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

@Category(SampleTest.class)
public class RemoveEmbeddedFiles extends GenericTest {
    public static final String SRC = "./src/test/resources/sandbox/annotations/hello_with_attachment.pdf";
    public static final String DEST = "./target/test/resources/sandbox/annotations/remove_embedded_files.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new RemoveEmbeddedFiles().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(new FileInputStream(SRC)),
                new PdfWriter(new FileOutputStream(DEST)));
        PdfDictionary root = pdfDoc.getCatalog().getPdfObject();
        PdfDictionary names = root.getAsDictionary(PdfName.Names);
        names.remove(PdfName.EmbeddedFiles);
        pdfDoc.close();
    }
}
