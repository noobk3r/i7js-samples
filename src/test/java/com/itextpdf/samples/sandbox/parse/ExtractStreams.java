/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

/**
 * Example written by Bruno Lowagie in answer to the following question:
 * http://stackoverflow.com/questions/30286601/extracting-an-embedded-object-from-a-pdf
 */
package com.itextpdf.samples.sandbox.parse;

import com.itextpdf.basics.PdfException;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfObject;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfStream;
import com.itextpdf.test.annotations.type.SampleTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class ExtractStreams {
    public static final String DEST = "./target/test/resources/sandbox/parse/extract_streams%s";
    public static final String SRC = "./src/test/resources/sandbox/parse/image.pdf";
    public static final String RES = "./src/test/resources/sandbox/parse/extract_streams%s";

    @BeforeClass
    public static void before() {
        new File(DEST).getParentFile().mkdirs();
    }

    public static void main(String[] args) throws IOException {
        before();
        new ExtractStreams().manipulatePdf();
    }

    @Test
    public void manipulatePdf() throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(new FileInputStream(SRC)));
        PdfObject obj;
        List<Integer> streamLengths = new ArrayList<>();
        for (int i = 1; i <= pdfDoc.getNumberOfPdfObjects(); i++) {
            obj = pdfDoc.getPdfObject(i);
            if (obj != null && obj.isStream()) {
                byte[] b;
                try {
                    b = ((PdfStream) obj).getBytes();
                } catch (PdfException exc) {
                    b = ((PdfStream)obj).getBytes(false);
                }
                System.out.println(b.length);
                FileOutputStream fos = new FileOutputStream(String.format(DEST, i));
                fos.write(b);

                streamLengths.add(b.length);
                fos.close();
            }
        }
        Assert.assertArrayEquals(new Integer[] {30965, 74}, streamLengths.toArray(new Integer[streamLengths.size()]));
        pdfDoc.close();
    }
}
