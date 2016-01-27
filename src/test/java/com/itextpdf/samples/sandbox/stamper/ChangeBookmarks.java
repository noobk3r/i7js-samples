/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

/**
 * Example written by Bruno Lowagie in answer to:
 * http://stackoverflow.com/questions/24217657/set-inherit-zoomaction-property-to-bookmark-in-the-pdf-file
 */
package com.itextpdf.samples.sandbox.stamper;

import com.itextpdf.core.pdf.PdfArray;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfName;
import com.itextpdf.core.pdf.PdfNumber;
import com.itextpdf.core.pdf.PdfOutline;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.samples.GenericTest;
import com.itextpdf.test.annotations.type.SampleTest;

import java.io.File;
import java.util.List;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class ChangeBookmarks extends GenericTest {
    public static final String SRC = "./src/test/resources/sandbox/stamper/bookmarks.pdf";
    public static final String DEST = "./target/test/resources/sandbox/stamper/change_bookmarks.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new ChangeBookmarks().manipulatePdf(DEST);
    }

    public void changeList(List<PdfOutline> list) {
        for (PdfOutline entry : list) {
            PdfArray array = ((PdfArray) entry.getContent().get(PdfName.Dest));
            for (int i = 0; i < array.size(); i++) {
                if (PdfName.Fit.equals(array.get(i))) {
                    // one should be more cautious but since we know the source file...
                    array.set(i, PdfName.FitV);
                    array.add(new PdfNumber(60));
                }
            }
        }
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(SRC), new PdfWriter(DEST));
        PdfOutline outlines = pdfDoc.getOutlines(false);
        List<PdfOutline> children = outlines.getAllChildren().get(0).getAllChildren();
        changeList(children);
        pdfDoc.close();
    }
}
