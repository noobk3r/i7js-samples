/**
 * Example written for JavaOne 2016.
 * Differences between iText 5 and iText 7 are discussed in the JavaOne talk
 * "Oops, I broke my API" by Raf Hens and Bruno Lowagie.
 * This is the iText 7 version of one of the examples.
 */
package com.itextpdf.sandbox.javaone16;

import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.forms.fields.PdfTextFormField;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import java.io.File;
import java.io.IOException;

public class FormCreation {
    public static final String SRC = "src/main/resources/pdfs/hello.pdf";
    public static final String DEST = "results/javaone16/form_creation.pdf";
    
    public static void main(String[] args) throws IOException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new FormCreation().manipulatePdf(SRC, DEST);
    }

    public void manipulatePdf(String src, String dest) throws IOException {
        PdfReader reader = new PdfReader(src);
        PdfDocument pdf = new PdfDocument(reader, new PdfWriter(dest));
        PdfAcroForm form = PdfAcroForm.getAcroForm(pdf, true);
        PdfFormField tf = PdfTextFormField.createText(
            pdf, new Rectangle(110, 780, 70, 26), "text", "Text Field")
            .setBorderColor(Color.BLUE)
            .setBorderWidth(2)
            .setColor(Color.RED)
            .setFontSize(12);
        form.addField(tf);
        pdf.close();
    }
}
