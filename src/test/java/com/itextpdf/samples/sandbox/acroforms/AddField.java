/**
 * Example written by Bruno Lowagie in answer to the following question:
 * http://stackoverflow.com/questions/27206327/itext-add-new-acrofields-form-feilds-in-to-a-pdf-using-itext
 */
package com.itextpdf.samples.sandbox.acroforms;

import com.itextpdf.basics.font.FontConstants;
import com.itextpdf.basics.geom.Rectangle;
import com.itextpdf.canvas.PdfCanvas;
import com.itextpdf.core.font.PdfFont;
import com.itextpdf.core.pdf.*;
import com.itextpdf.core.pdf.action.PdfAction;
import com.itextpdf.core.pdf.annot.PdfLinkAnnotation;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.samples.GenericTest;
import org.junit.experimental.categories.Category;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

@Category(SampleTest.class)
public class AddField extends GenericTest {
    public static final String SRC = "./src/test/resources/sandbox/acroforms/form.pdf";
    public static final String DEST = "./target/test/resources/sandbox/acroforms/add_field.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new AddField().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(
                new FileInputStream(SRC)), new PdfWriter(new FileOutputStream(DEST)));
        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);
        PdfPage page = pdfDoc.getFirstPage();

        PdfCanvas canvas = new PdfCanvas(page);
        canvas.beginText();
        canvas.setFontAndSize(PdfFont.createStandardFont(pdfDoc, FontConstants.COURIER_BOLD), 14);
        canvas.moveText(36, 700 + 10);
        canvas.showText("POST");
        canvas.endText();
        canvas.release();

        // TODO Implement PdfAction.SUBMIT_HTML_FORMAT & PdfAction.SUBMIT_COORDINATES constants
        page.addAnnotation(new PdfLinkAnnotation(pdfDoc, new Rectangle(36, 700, 36, 30))
                .setAction(PdfAction.createURI(pdfDoc, "http://itextpdf.com:8180/book/request"))
                .setName(new PdfString("post"))
                .setBorder(new PdfArray(new float[]{0, 0, 1})));
        // TODO Implement VISIBLE_BUT_DOES_NOT_PRINT constant usage
        // button.setVisibility(PushbuttonField.VISIBLE_BUT_DOES_NOT_PRINT);

        pdfDoc.close();
    }
}
