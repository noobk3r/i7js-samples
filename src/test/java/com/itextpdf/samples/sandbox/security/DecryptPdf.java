/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

/**
* Example written by Bruno Lowagie in answer to:
* http://stackoverflow.com/questions/27867868/how-can-i-decrypt-a-pdf-document-with-the-owner-password
*/
package com.itextpdf.samples.sandbox.security;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.samples.GenericTest;
import com.itextpdf.test.annotations.type.SampleTest;

import java.io.File;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class DecryptPdf extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/security/decrypt_pdf.pdf";
    public static String SRC = "./src/test/resources/sandbox/security/hello_encrypted.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new DecryptPdf().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfReader reader = new PdfReader(SRC, "World".getBytes());
        PdfDocument pdfDoc = new PdfDocument(reader, new PdfWriter(DEST));
        System.out.println(new String(reader.computeUserPassword()));
        pdfDoc.close();
    }
}
