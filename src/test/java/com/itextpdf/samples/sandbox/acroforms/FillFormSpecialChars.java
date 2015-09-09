/**
 * Example written by Bruno Lowagie in answer to:
 * http://stackoverflow.com/questions/20401125/overlapping-characters-in-text-field-itext-pdf
 *
 * Sometimes you need to change the font of a field.
 */
package com.itextpdf.samples.sandbox.acroforms;

import com.itextpdf.basics.Utilities;
import com.itextpdf.basics.font.PdfEncodings;
import com.itextpdf.basics.font.TrueTypeFont;
import com.itextpdf.core.font.PdfFont;
import com.itextpdf.core.font.PdfType0Font;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.samples.GenericTest;
import org.junit.Ignore;
import org.junit.experimental.categories.Category;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

// TODO itext5 see more fields than itext6 in this document
@Ignore
@Category(SampleTest.class)
public class FillFormSpecialChars extends GenericTest {
    public static final String SRC = "./src/test/resources/sandbox/acroforms/test.pdf";
    public static final String DEST = "./target/test/resources/sandbox/acroforms/fill_form_special_chars.pdf";
    public static final String VALUE = "\u011b\u0161\u010d\u0159\u017e\u00fd\u00e1\u00ed\u00e9";
    public static final String FONT = "./src/test/resources/sandbox/acroforms/FreeSans.ttf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new FillFormSpecialChars().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(
                new FileInputStream(SRC)), new PdfWriter(new FileOutputStream(DEST)));
        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);
        form.setGenerateAppearance(true);
        PdfFont font = new PdfType0Font(
                pdfDoc,
                new TrueTypeFont(
                        "Free Sans",
                        PdfEncodings.IDENTITY_H,
                        Utilities.inputStreamToArray(new FileInputStream(FONT))),
                "Identity-H");
        form.getFormFields().get("test").setFont(font);
        form.getFormFields().get("test").setValue(VALUE);
        // TODO THERE IS NO SUCH FIELD in itext6 but in itext5
        // form.getFormFields().get("test2").setFont(font);
        // form.getFormFields().get("test2").setValue(VALUE);
        pdfDoc.close();
    }
}
