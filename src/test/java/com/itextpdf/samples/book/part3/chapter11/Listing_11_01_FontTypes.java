/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

package com.itextpdf.samples.book.part3.chapter11;

import com.itextpdf.basics.font.FontConstants;
import com.itextpdf.basics.font.PdfEncodings;
import com.itextpdf.core.font.PdfFont;
import com.itextpdf.core.font.PdfFontFactory;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.samples.GenericTest;
import com.itextpdf.test.annotations.type.SampleTest;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class Listing_11_01_FontTypes extends GenericTest {
    public static final String DEST
            = "./target/test/resources/book/part3/chapter11/Listing_11_01_FontTypes.pdf";
    public static String TEXT
            = "quick brown fox jumps over the lazy dog\nQUICK BROWN FOX JUMPS OVER THE LAZY DOG";
    public static String[][] FONTS = {
            {FontConstants.HELVETICA, PdfEncodings.WINANSI},
            // TODO problems with CMR fonts
            {"./src/test/resources/book/part3/chapter11/cmr10.afm", PdfEncodings.WINANSI},
            {"./src/test/resources/book/part3/chapter11/cmr10.pfm", PdfEncodings.WINANSI},
            //{"c:/windows/fonts/ARBLI__.TTF", PdfEncodings.WINANSI},
            {/*"c:/windows/fonts/arial.ttf"*/"./src/test/resources/font/FreeSans.ttf", PdfEncodings.WINANSI},
            {/*"c:/windows/fonts/arial.ttf"*/"./src/test/resources/font/FreeSans.ttf", PdfEncodings.IDENTITY_H},
            {"./src/test/resources/book/part3/chapter11/Puritan2.otf", PdfEncodings.WINANSI},
            // TODO Notice that we'va changed windows MS Gothic to IPA Gothic so the results in comparison with itext5 are different
            {"./src/test/resources/book/part3/chapter11/ipam.ttc,0", PdfEncodings.IDENTITY_H},
            // TODO Do not render
            {"KozMinPro-Regular", "UniJIS-UCS2-H"}
    };

    public static void main(String[] agrs) throws Exception {
        new Listing_11_01_FontTypes().manipulatePdf(DEST);
    }

    @Override
    public void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
        Document doc = new Document(pdfDoc);
        PdfFont font;
        for (int i = 0; i < FONTS.length; i++) {
            font = PdfFontFactory.createFont(FONTS[i][0], FONTS[i][1], true);
            doc.add(new Paragraph(
                    String.format("Font file: %s with encoding %s", FONTS[i][0], FONTS[i][1])));
            doc.add(new Paragraph(
                    String.format("iText class: %s", font.getClass().getName())));
            doc.add(new Paragraph(TEXT).setFont(font).setFontSize(12));
            // TODO No LineSeparator
            // doc.add(new LineSeparator(0.5f, 100, null, 0, -5));
        }
        doc.close();
    }
}
