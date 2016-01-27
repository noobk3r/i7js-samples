/**
 * Example written by Bruno Lowagie in answer to:
 * http://stackoverflow.com/questions/24830060/send-file-to-server-through-itext-pdf
 */
package com.itextpdf.samples.sandbox.acroforms;

import com.itextpdf.basics.geom.Rectangle;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfName;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.pdf.action.PdfAction;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.forms.fields.PdfTextFormField;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileOutputStream;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class FileSelectionExample extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/acroforms/file_selection_example.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new FileSelectionExample().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);

        PdfTextFormField field = PdfFormField.createText(pdfDoc,
                new Rectangle(36, 788, 559 - 36, 806 - 788), "myfile", "");
        field.setFileSelect(true);
        field.setAdditionalAction(PdfName.U, PdfAction.createJavaScript(
                "this.getField('myfile').browseForFileToSubmit();"
                        + "this.getField('mytitle').setFocus();"));
        PdfAcroForm.getAcroForm(pdfDoc, true).addField(field);
        PdfTextFormField title = PdfFormField.createText(pdfDoc,
                new Rectangle(36, 752, 559 - 36, 770 - 752), "mytitle", "");
        PdfAcroForm.getAcroForm(pdfDoc, true).addField(title);

        pdfDoc.close();
    }

}
