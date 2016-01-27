/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

/**
 * Example written by Bruno Lowagie in answer to the following question:
 * http://stackoverflow.com/questions/24370273/set-initial-view-pdf-document-properties-using-itextsharp-with-c-sharp
 */
package com.itextpdf.samples.sandbox.stamper;

import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfName;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfString;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class AddLanguage extends GenericTest {
    public static final String SRC = "./src/test/resources/sandbox/stamper/hello.pdf";
    public static final String DEST = "./target/test/resources/sandbox/stamper/add_language.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new AddLanguage().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(
                new FileInputStream(SRC)), new PdfWriter(new FileOutputStream(DEST)));
        pdfDoc.getCatalog().put(PdfName.Lang, new PdfString("EN"));
        pdfDoc.close();
    }
}
