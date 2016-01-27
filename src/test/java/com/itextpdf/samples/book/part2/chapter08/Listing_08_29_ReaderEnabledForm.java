/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

package com.itextpdf.samples.book.part2.chapter08;

import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfVersion;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.samples.GenericTest;

import java.io.IOException;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class Listing_08_29_ReaderEnabledForm extends GenericTest {
    public static final String DEST
            = "./target/test/resources/book/part2/chapter08/Listing_08_29_ReaderEnabledForm.pdf";
    /**
     * The original PDF.
     */
    public static final String RESOURCE = "./src/test/resources/book/part2/chapter08/xfa_enabled.pdf";
    /**
     * The resulting PDF.
     */
    public static final String RESULT1 = "./target/test/resources/book/part2/chapter08/xfa_broken.pdf";
    /**
     * The resulting PDF.
     */
    public static final String RESULT2 = "./target/test/resources/book/part2/chapter08/xfa_removed.pdf";
    /**
     * The resulting PDF.
     */
    public static final String RESULT3 = "./target/test/resources/book/part2/chapter08/xfa_preserved.pdf";

    public void manipulatePdf2(String src, String dest, boolean remove, boolean preserve) throws IOException {
        // create the reader
        PdfReader reader = new PdfReader(src);
        // remove the usage rights (or not)
        if (remove) {
            // TODO No removeUsageRights
            // reader.removeUsageRights();
        }
        // create the pdfDoc
        PdfDocument pdfDoc;
        // preserve the reader enabling by creating a PDF in append mode (or not)
        if (preserve) {
            pdfDoc = new PdfDocument(reader, new PdfWriter(dest), true, PdfVersion.PDF_1_0);
        } else {
            pdfDoc = new PdfDocument(reader, new PdfWriter(dest));
        }
        // fill out the fields
        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);
        form.getField("title[0]").setValue("The Misfortunates");
        form.getField("original[0]").setValue("De helaasheid der dingen");
        form.getField("duration[0]").setValue("108");
        form.getField("year[0]").setValue("2009");
        // close the pdfDoc
        // TODO Exception on getStructParentIndex
        pdfDoc.close();
    }

    public static void main(String[] args) throws Exception {
        new Listing_08_29_ReaderEnabledForm().manipulatePdf(DEST);
    }

    protected void manipulatePdf(String dest) throws Exception {
        manipulatePdf2(RESOURCE, RESULT1, false, false);
        manipulatePdf2(RESOURCE, RESULT2, true, false);
        manipulatePdf2(RESOURCE, RESULT3, false, true);
    }
}
