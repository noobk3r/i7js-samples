/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

/**
 * This example was written by Bruno Lowagie in answer to the following question:
 * https://www.linkedin.com/groups/Script-Change-Author-Name-Comments-159987.S.5984062085800144899
 */
package com.itextpdf.samples.sandbox.parse;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.test.annotations.type.SampleTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class ParseCzech {
    public static final String DEST = "./target/test/resources/sandbox/interactive/czech.txt";
    public static final String SRC = "./src/test/resources/sandbox/parse/czech.pdf";

    @BeforeClass
    public static void beforeClass() throws IOException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new ParseCzech().manipulatePdf();
    }

    @Test
    public void manipulatePdf() throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(new FileInputStream(SRC)));
        FileOutputStream fos = new FileOutputStream(DEST);
        // TODO There is no PdfTextExtractor
//        for (int page = 1; page <= 1; page++) {
//            fos.write(PdfTextExtractor.getTextFromPage(reader, page).getBytes("UTF-8"));
//        }
        fos.flush();
        fos.close();
    }
}
