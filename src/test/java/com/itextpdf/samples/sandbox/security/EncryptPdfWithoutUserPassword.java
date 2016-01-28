/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

/**
 * Example written by Bruno Lowagie in answer to:
 * http://stackoverflow.com/questions/27867868/how-can-i-decrypt-a-pdf-document-with-the-owner-password
 */
package com.itextpdf.samples.sandbox.security;

import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfOutputStream;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.samples.GenericTest;
import com.itextpdf.test.annotations.type.SampleTest;

import java.io.File;
import java.io.FileOutputStream;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class EncryptPdfWithoutUserPassword extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/security/encrypt_pdf_without_user_password.pdf";
    public static final String SRC = "./src/test/resources/sandbox/security/hello_encrypted.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new EncryptPdfWithoutUserPassword().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfReader reader = new PdfReader(SRC, "World".getBytes());
        PdfWriter writer = new PdfWriter(new FileOutputStream(DEST));
        writer.setEncryption(null, "World".getBytes(), PdfOutputStream.ALLOW_PRINTING,
                PdfOutputStream.ENCRYPTION_AES_128 | PdfOutputStream.DO_NOT_ENCRYPT_METADATA);
        PdfDocument pdfDoc = new PdfDocument(reader, writer);
        pdfDoc.close();
    }
}
