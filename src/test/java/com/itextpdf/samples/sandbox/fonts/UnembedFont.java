/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

/**
 * Example written by Bruno Lowagie in answer to a question by a customer.
 */
package com.itextpdf.samples.sandbox.fonts;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class UnembedFont extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/fonts/unembed_font.pdf";
    public static final String SRC = "./target/test/resources/sandbox/fonts/withSerifFont.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new UnembedFont().manipulatePdf(DEST);
    }

    /**
     * Creates a PDF with an embedded font.
     */
    public void createPdf(String file) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(new FileOutputStream(file)));
        PdfFont font = PdfFontFactory.createFont("./src/test/resources/sandbox/fonts/PT_Serif-Web-Regular.ttf", "WINANSI", true);
        new Document(pdfDoc).add(new Paragraph("This is a test with Times New Roman.").setFont(font));
        pdfDoc.close();
    }

    /**
     * Processes a dictionary.
     * In case of font dictionaries, the dictionary is processed.
     */
    public void unembedTTF(PdfDictionary dict) {
        // we ignore all dictionaries that aren't font dictionaries
        if (!PdfName.Font.equals(dict.getAsName(PdfName.Type))) {
            return;
        }
        // we only remove TTF fonts
        if (dict.getAsDictionary(PdfName.FontFile2) != null) {
            return;
        }
        // check if a subset was used (in which case we remove the prefix)
        PdfName baseFont = dict.getAsName(PdfName.BaseFont);
        if (baseFont.getValue().getBytes()[6] == '+') {
            baseFont = new PdfName(baseFont.getValue().substring(7));
            dict.put(PdfName.BaseFont, baseFont);
        }
        // we check if there's a font descriptor
        PdfDictionary fontDescriptor = dict.getAsDictionary(PdfName.FontDescriptor);
        if (fontDescriptor == null)
            return;
        // is there is, we replace the fontname and remove the font file
        fontDescriptor.put(PdfName.FontName, baseFont);
        fontDescriptor.remove(PdfName.FontFile2);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        createPdf(SRC);
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(
                new FileInputStream(SRC)), new PdfWriter(new FileOutputStream(DEST)));

        // / we loop over all objects
        PdfObject obj;
        for (int i = 1; i < pdfDoc.getNumberOfPdfObjects(); i++) {
            obj = pdfDoc.getPdfObject(i);
            // we skip all objects that aren't a dictionary
            if (obj == null || !obj.isDictionary())
                continue;
            // we process all dictionaries
            unembedTTF((PdfDictionary) obj);
        }
        // The unused objects will be removed automatically

        // we persist the altered document
        pdfDoc.close();
    }
}
