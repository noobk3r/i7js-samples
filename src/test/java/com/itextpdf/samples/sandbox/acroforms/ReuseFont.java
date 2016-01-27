/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

/**
 * This example is based on an answer given on StackOverflow:
 * http://stackoverflow.com/questions/22186014/itextsharp-re-use-font-embedded-in-acrofield
 */
package com.itextpdf.samples.sandbox.acroforms;

import com.itextpdf.core.font.PdfFontFactory;
import com.itextpdf.core.pdf.canvas.PdfCanvas;
import com.itextpdf.core.font.PdfFont;
import com.itextpdf.core.pdf.PdfDictionary;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfName;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
@Ignore("Document Fonts")
public class ReuseFont extends GenericTest {

    public static final String SRC = "./src/test/resources/sandbox/acroforms/form.pdf";
    public static final String DEST = "./target/test/resources/sandbox/acroforms/reuse_font.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new ReuseFont().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(
                new FileInputStream(SRC)), new PdfWriter(new FileOutputStream(DEST)));
        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);
        PdfFont font = findFontInForm(pdfDoc, new PdfName("Calibri"));
        PdfCanvas canvas = new PdfCanvas(pdfDoc.getFirstPage());
        canvas.beginText();
        canvas.setFontAndSize(font, 13);
        canvas.moveText(36, 806);
        canvas.showText("Some text in Calibri");
        canvas.endText();
        canvas.stroke();
        pdfDoc.close();
    }

    public PdfFont findFontInForm(PdfDocument pdfDoc, PdfName fontName) throws IOException {
        PdfDictionary acroForm = pdfDoc.getCatalog().getPdfObject().getAsDictionary(PdfName.AcroForm);
        if (acroForm == null) return null;
        PdfDictionary dr = acroForm.getAsDictionary(PdfName.DR);
        if (dr == null) return null;
        PdfDictionary font = dr.getAsDictionary(PdfName.Font);
        if (font == null) return null;
        for (PdfName key : font.keySet()) {
            if (key.equals(fontName)) {
                return PdfFontFactory.createFont(font.getAsDictionary(key));
            }
        }
        return null;
    }
}
