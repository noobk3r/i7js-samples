/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

/**
 * Example written by Bruno Lowagie in answer to:
 * http://stackoverflow.com/questions/27905740/remove-text-occurrences-contained-in-a-specified-area-with-itext
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
public class RemoveContentInRectangle extends GenericTest {
    public static final String SRC = "./src/test/resources/sandbox/parse/page229.pdf";
    public static final String DEST = "./target/test/resources/sandbox/parse/remove_content_in_rectangle.pdf";

    public static void main(String[] args) throws IOException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new RemoveContentInRectangle().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(new FileInputStream(SRC)),
                new PdfWriter(new FileOutputStream(dest)));
        // TODO There is no CleanUp functionality in itext6
        // List<PdfCleanUpLocation> cleanUpLocations = new ArrayList<PdfCleanUpLocation>();
        // cleanUpLocations.add(new PdfCleanUpLocation(1, new Rectangle(97f, 405f, 480f, 445f), BaseColor.GRAY));
        // PdfCleanUpProcessor cleaner = new PdfCleanUpProcessor(cleanUpLocations, stamper);
        // cleaner.cleanUp();
        // stamper.close();
        //reader.close();
        pdfDoc.close();
    }
}
