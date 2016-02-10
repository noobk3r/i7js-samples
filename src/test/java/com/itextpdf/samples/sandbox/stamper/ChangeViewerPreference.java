/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

package com.itextpdf.samples.sandbox.stamper;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.samples.GenericTest;
import com.itextpdf.test.annotations.type.SampleTest;

import java.io.File;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore("DEVSIX-457")
@Category(SampleTest.class)
public class ChangeViewerPreference extends GenericTest {
    public static final String SRC = "./src/test/resources/sandbox/stamper/OCR.pdf";
    public static final String DEST = "./target/test/resources/sandbox/stamper/change_viewer_preference.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new ChangeViewerPreference().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(SRC), new PdfWriter(DEST));
        // TODO No viewer preferences
        // stamper.addViewerPreference(PdfName.DUPLEX, PdfName.DUPLEXFLIPLONGEDGE);
        pdfDoc.close();
    }
}
