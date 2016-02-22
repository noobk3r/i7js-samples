/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

/**
 * Example written by Bruno Lowagie in answer to a question on StackOverflow
 *
 * When concatenating documents, we add a named destination every time
 * a new document is started. After we've finished merging, we add an extra
 * page with the table of contents and links to the named destinations.
 */
package com.itextpdf.samples.sandbox.merge;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfOutline;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.navigation.PdfExplicitDestination;
import com.itextpdf.kernel.utils.PdfMerger;
import com.itextpdf.samples.GenericTest;
import com.itextpdf.test.annotations.type.SampleTest;

import java.io.File;
import java.io.IOException;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class MergeWithOutlines extends GenericTest {
    public static final String DEST
            = "./target/test/resources/sandbox/merge/merge_with_outlines.pdf";
    public static final String SRC1
            = "./src/test/resources/sandbox/merge/hello.pdf";
    public static final String SRC2
            = "./src/test/resources/sandbox/merge/links1.pdf";
    public static final String SRC3
            = "./src/test/resources/sandbox/merge/links2.pdf";

    public static void main(String[] args) throws IOException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new MergeWithOutlines().manipulatePdf(DEST);
    }

    public void manipulatePdf(String destFolder) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(DEST));
        PdfMerger merger = new PdfMerger(pdfDoc);
        PdfOutline rootOutline = pdfDoc.getOutlines(false);
        int page = 1;
        PdfDocument pdfDoc1 = new PdfDocument(new PdfReader(SRC1));
        merger.addPages(pdfDoc1, 1, pdfDoc1.getNumberOfPages());
        PdfDocument pdfDoc2 = new PdfDocument(new PdfReader(SRC2));
        merger.addPages(pdfDoc2, 1, pdfDoc2.getNumberOfPages());
        PdfDocument pdfDoc3 = new PdfDocument(new PdfReader(SRC3));
        merger.addPages(pdfDoc3, 1, pdfDoc3.getNumberOfPages());
        merger.merge();

        PdfOutline helloWorld = rootOutline.addOutline("Hello World");
        helloWorld.addDestination(PdfExplicitDestination.createFit(pdfDoc.getPage(page)));
        page += pdfDoc1.getNumberOfPages();
        PdfOutline link1 = helloWorld.addOutline("link1");
        link1.addDestination(PdfExplicitDestination.createFit(pdfDoc.getPage(page)));
        page += pdfDoc1.getNumberOfPages();
        PdfOutline link2 = rootOutline.addOutline("Link 2");
        link2.addDestination(PdfExplicitDestination.createFit(pdfDoc.getPage(page)));
        pdfDoc1.close();
        pdfDoc2.close();
        pdfDoc3.close();
        pdfDoc.close();
    }
}
