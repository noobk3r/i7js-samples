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
import com.itextpdf.core.utils.CompareTool;
import com.itextpdf.samples.GenericTest;
import com.itextpdf.test.annotations.type.SampleTest;

import java.io.File;
import java.io.FileOutputStream;

import org.junit.Assert;
import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class EncryptPdf extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/security/encrypt_pdf.pdf";
    public static String SRC = "./src/test/resources/sandbox/security/hello_encrypted.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new EncryptPdf().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfReader reader = new PdfReader(SRC, "World".getBytes());
        PdfWriter writer = new PdfWriter(new FileOutputStream(DEST));
        writer.setEncryption("Hello".getBytes(), "World".getBytes(), PdfOutputStream.ALLOW_PRINTING,
                PdfOutputStream.ENCRYPTION_AES_128 | PdfOutputStream.DO_NOT_ENCRYPT_METADATA);
        PdfDocument pdfDoc = new PdfDocument(reader, writer);
        pdfDoc.close();
    }

    // Override the comparison method in order to pass owner passwords
    @Override
    protected void comparePdf(String dest, String cmp) throws Exception {
        if (cmp == null || cmp.length() == 0) return;
        CompareTool compareTool = new CompareTool();
        String outPath = new File(dest).getParent();
        new File(outPath).mkdirs();
        if (compareXml){
            if(!compareTool.compareXmls(dest,cmp)){
                addError("The XML structures are different.");
            }
        } else {
            if (compareRenders) {
                addError(compareTool.compareVisually(dest, cmp, outPath, differenceImagePrefix));
                addError(compareTool.compareLinkAnnotations(dest, cmp));
            } else {
                addError(compareTool.compareByContent(dest, cmp, outPath, differenceImagePrefix, "World".getBytes(), "World".getBytes()));
            }
            addError(compareTool.compareDocumentInfo(dest, cmp, "World".getBytes(), "World".getBytes()));
        }

        if (errorMessage != null) Assert.fail(errorMessage);
    }
}
