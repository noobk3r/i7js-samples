/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

package com.itextpdf.samples.book.part2.chapter08;

import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.samples.GenericTest;
import com.itextpdf.test.annotations.type.SampleTest;
import org.junit.Ignore;
import org.junit.experimental.categories.Category;

import java.io.IOException;

@Ignore
@Category(SampleTest.class)
public class Listing_08_29_ReaderEnabledForm extends GenericTest {
    public static final String DEST
            = "./target/test/resources/book/part2/chapter08/Listing_08_29_ReaderEnabledForm.pdf";
    /**
     * The original PDF.
     */
    public static final String RESOURCE = "./src/test/resources/pdfs/xfa_enabled.pdf";
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

    /**
     * Removes any usage rights that this PDF may have. Only Adobe can grant usage rights
     * and any PDF modification with iText will invalidate them. Invalidated usage rights may
     * confuse Acrobat and it's advisable to remove them altogether.
     */
    public void removeUsageRights(PdfDocument pdfDoc) {
        PdfDictionary perms = pdfDoc.getCatalog().getPdfObject().getAsDictionary(PdfName.Perms);
        if (perms == null) {
            return;
        }
        perms.remove(new PdfName("UR"));
        perms.remove(PdfName.UR3);
        if (perms.size() == 0) {
            pdfDoc.getCatalog().remove(PdfName.Perms);
        }
    }

    public void manipulatePdf2(String src, String dest, boolean remove, boolean preserve) throws IOException {
        // create the reader
        PdfReader reader = new PdfReader(src);
        // create the pdfDoc
        PdfDocument pdfDoc;
        // preserve the reader enabling by creating a PDF in append mode (or not)
        if (preserve) {
            pdfDoc = new PdfDocument(reader, new PdfWriter(dest), true);
        } else {
            pdfDoc = new PdfDocument(reader, new PdfWriter(dest));
        }
        // TODO is this move valid ?
        // remove the usage rights (or not)
        if (remove) {
            removeUsageRights(pdfDoc);
            // TODO No removeUsageRights
            // reader.removeUsageRights();
        }
        // fill out the fields
        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);
        form.getField("movie[0].#subform[0].title[0]").setValue("The Misfortunates");
        form.getField("movie[0].#subform[0].original[0]").setValue("De helaasheid der dingen");
        form.getField("movie[0].#subform[0].duration[0]").setValue("108");
        form.getField("movie[0].#subform[0].year[0]").setValue("2009");
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
