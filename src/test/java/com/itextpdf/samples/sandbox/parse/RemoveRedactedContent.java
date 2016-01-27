/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

/**
 * Example written by Bruno Lowagie in answer to:
 * http://stackoverflow.com/questions/24037282/any-way-to-create-redactions
 */
package com.itextpdf.samples.sandbox.parse;

import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class RemoveRedactedContent extends GenericTest {
    public static final String SRC = "./src/test/resources/sandbox/parse/page229_redacted.pdf";
    public static final String DEST = "./target/test/resources/sandbox/parse/remove_redacted_content.pdf";

    public static void main(String[] args) throws IOException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new RemoveRedactedContent().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(new FileInputStream(SRC)),
                new PdfWriter(new FileOutputStream(dest)));
        // TODO There is no CleanUp functionality in itext6
        // PdfCleanUpProcessor cleaner = new PdfCleanUpProcessor(stamper);
        // cleaner.cleanUp();
        pdfDoc.close();
    }

}
