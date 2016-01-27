/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

package com.itextpdf.samples.book.part2.chapter08;

import com.itextpdf.basics.font.PdfEncodings;
import com.itextpdf.basics.geom.Rectangle;
import com.itextpdf.core.font.PdfFont;
import com.itextpdf.core.font.PdfFontFactory;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.forms.fields.PdfTextFormField;
import com.itextpdf.samples.GenericTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class Listing_08_09_TextFieldFonts extends GenericTest {
    public static final String DEST = "./target/test/resources/book/part2/chapter08/Listing_08_09_TextFieldFonts.pdf";
    /**
     * The resulting PDF.
     */
    public static final String RESULT1
            = "./target/test/resources/book/part2/chapter08/Listing_08_09_TextFieldFonts_unicode_field_1.pdf";
    /**
     * The resulting PDF.
     */
    public static final String RESULT2
            = "./target/test/resources/book/part2/chapter08/Listing_08_09_TextFieldFonts_unicode_field_2.pdf";
    /**
     * The resulting PDF.
     */
    public static final String RESULT3
            = "./target/test/resources/book/part2/chapter08/Listing_08_09_TextFieldFonts_unicode_field_3.pdf";
    /**
     * The resulting PDF.
     */
    public static final String RESULT4
            = "./target/test/resources/book/part2/chapter08/Listing_08_09_TextFieldFonts_unicode_field_4.pdf";
    /**
     * The resulting PDF.
     */
    public static final String RESULT5
            = "./target/test/resources/book/part2/chapter08/Listing_08_09_TextFieldFonts_unicode_field_5.pdf";
    /**
     * The resulting PDF.
     */
    public static final String RESULT6
            = "./target/test/resources/book/part2/chapter08/Listing_08_09_TextFieldFonts_unicode_field_6.pdf";
    /**
     * The resulting PDF.
     */
    public static final String RESULT7
            = "./target/test/resources/book/part2/chapter08/Listing_08_09_TextFieldFonts_unicode_field_7.pdf";
    /**
     * The resulting PDF.
     */
    public static final String RESULT8
            = "./target/test/resources/book/part2/chapter08/Listing_08_09_TextFieldFonts_unicode_field_8.pdf";
    /**
     * A String containing Chinese characters/
     */
    public static final String TEXT = "These are the protagonists in 'Hero', a movie by Zhang Yimou:\n"
            + "\u7121\u540d (Nameless), \u6b98\u528d (Broken Sword), "
            + "\u98db\u96ea (Flying Snow), \u5982\u6708 (Moon), "
            + "\u79e6\u738b (the King), and \u9577\u7a7a (Sky).";
    /**
     * A String containing Korean characters.
     */
    public static final String BINJIP = "The Korean title of the movie 3-Iron is \ube48\uc9d1 (Bin-Jip)";

    public static void main(String[] args) throws Exception {
        new Listing_08_09_TextFieldFonts().manipulatePdf(DEST);
    }

    // TODO Text do not render if false == appearances
    public void createPdf(String dest, boolean appearances, boolean font) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true).setNeedAppearances(appearances);
        PdfTextFormField text = PdfFormField.createText(pdfDoc,
                new Rectangle(36, 806, 559 - 36, 780 - 806), "description", TEXT);
        text.setMultiline(true);
        if (font) {
            PdfFont unicode = PdfFontFactory.createFont("c:/windows/fonts/arialuni.ttf", PdfEncodings.IDENTITY_H, true);
            // TODO NO setExtensionFont & setSubstitutionFont
            text.setFont(unicode);
            List<PdfFont> list = new ArrayList<>();
            list.add(unicode);
            // text.setSubstitutionFonts(list);
        }
        form.addField(text);
        pdfDoc.close();
    }

    /**
     * Manipulates a PDF file src with the file dest as result
     *
     * @param src  the original PDF
     * @param dest the resulting PDF
     * @throws IOException
     */
    public void manipulatePdfFont1(String src, String dest) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(src), new PdfWriter(dest));
        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);
        PdfFont unicode = PdfFontFactory.createFont("HYSMyeongJoStd-Medium", "UniKS-UCS2-H", false);
        form.getField("description").setFont(unicode);
        form.getField("description").setValue(BINJIP);
        pdfDoc.close();
    }

    /**
     * Manipulates a PDF file src with the file dest as result
     *
     * @param src  the original PDF
     * @param dest the resulting PDF
     * @throws IOException
     */
    public void manipulatePdf2(String src, String dest) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(src), new PdfWriter(dest));
        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);
        // TODO Get rid of next 2 lines
        PdfFont unicode = PdfFontFactory.createFont("c:/windows/fonts/arialuni.ttf", PdfEncodings.IDENTITY_H, true);
        form.getField("description").setFont(unicode);

        form.getField("description").setValue(BINJIP);
        pdfDoc.close();
    }

    /**
     * Manipulates a PDF file src with the file dest as result
     *
     * @param src  the original PDF
     * @param dest the resulting PDF
     * @throws IOException
     */
    public void manipulatePdfFont2(String src, String dest) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(src), new PdfWriter(dest));
        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);
        PdfFont unicode = PdfFontFactory.createFont("c:/windows/fonts/arialuni.ttf", PdfEncodings.IDENTITY_H, true);
        // TODO No addSubstitutionFont
        // form.addSubstitutionFont(unicode);
        form.getField("description").setFont(unicode);
        form.getField("description").setValue(BINJIP);
        pdfDoc.close();
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        Listing_08_09_TextFieldFonts application = new Listing_08_09_TextFieldFonts();
        application.createPdf(RESULT1, false, false);
        application.createPdf(RESULT2, true, false);
        application.createPdf(RESULT3, false, true);
        application.manipulatePdf2(RESULT1, RESULT4);
        application.manipulatePdf2(RESULT2, RESULT5);
        application.manipulatePdf2(RESULT3, RESULT6);
        application.manipulatePdfFont1(RESULT3, RESULT7);
        application.manipulatePdfFont2(RESULT3, RESULT8);
    }
}


