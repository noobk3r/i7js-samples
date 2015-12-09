/**
 * Example written by Bruno Lowagie in answer to the following question:
 * http://stackoverflow.com/questions/23083220/how-to-set-pdf-version-using-itextsharp
 */
package com.itextpdf.samples.sandbox.stamper;

import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfVersion;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class ChangeVersion extends GenericTest {
    public static final String SRC = "./src/test/resources/sandbox/stamper/OCR.pdf";
    public static final String DEST = "./target/test/resources/sandbox/stamper/change_version.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new ChangeVersion().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(
                new FileInputStream(SRC)), new PdfWriter(new FileOutputStream(DEST)), PdfVersion.PDF_1_5);
        pdfDoc.close();
    }
}
