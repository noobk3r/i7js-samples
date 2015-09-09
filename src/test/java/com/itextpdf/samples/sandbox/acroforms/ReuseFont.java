/**
 * This example is based on an answer given on StackOverflow:
 * http://stackoverflow.com/questions/22186014/itextsharp-re-use-font-embedded-in-acrofield
 */
package com.itextpdf.samples.sandbox.acroforms;

import com.itextpdf.canvas.PdfCanvas;
import com.itextpdf.core.font.PdfFont;
import com.itextpdf.core.pdf.*;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.samples.GenericTest;
import org.junit.Ignore;
import org.junit.experimental.categories.Category;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

// TODO problems with src-file to open. Temporary change src
@Ignore
@Category(SampleTest.class)
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
        Map<String, PdfFormField> fields = form.getFormFields();
        PdfFont font = findFontInForm(pdfDoc, new PdfName("Calibri"));
        PdfCanvas canvas = new PdfCanvas(pdfDoc.addNewPage());
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
                return new PdfFont(pdfDoc,
                        font.getAsDictionary(key));
            }
        }
        return null;
    }
}
