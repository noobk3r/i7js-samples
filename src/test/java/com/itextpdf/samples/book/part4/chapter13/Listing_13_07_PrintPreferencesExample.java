/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

package com.itextpdf.samples.book.part4.chapter13;

import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfVersion;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.core.xmp.XMPException;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.samples.GenericTest;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class Listing_13_07_PrintPreferencesExample extends GenericTest {
    public static final String DEST =
            "./target/test/resources/book/part4/chapter13/Listing_13_07_PrintPreferencesExample.pdf";

    public static void main(String args[]) throws IOException, SQLException, XMPException {
        new Listing_13_07_PrintPreferencesExample().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws IOException, SQLException, XMPException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest), PdfVersion.PDF_1_5);
        Document doc = new Document(pdfDoc);
        // TODO No setViewerPreferences
        // writer.addViewerPreference(PdfName.PRINTSCALING, PdfName.NONE);
        // writer.addViewerPreference(PdfName.NUMCOPIES, new PdfNumber(3));
        // writer.addViewerPreference(PdfName.PICKTRAYBYPDFSIZE, PdfBoolean.PDFTRUE);
        doc.add(new Paragraph("Hello World"));
        doc.close();
    }
}
