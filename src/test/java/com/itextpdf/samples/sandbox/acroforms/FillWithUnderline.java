/**
 * Example written by Bruno Lowagie in answer to the following question:
 * http://stackoverflow.com/questions/28579382/underline-portion-of-text-using-itextsharp
 */
package com.itextpdf.samples.sandbox.acroforms;

import com.itextpdf.core.pdf.PdfArray;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.model.Document;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Map;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class FillWithUnderline extends GenericTest {
    public static final String SRC = "./src/test/resources/sandbox/acroforms/form.pdf";
    public static final String DEST = "./target/test/resources/sandbox/acroforms/fill_with_underline.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new FillWithUnderline().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(
                new FileInputStream(SRC)), new PdfWriter(new FileOutputStream(DEST)));
        Document doc = new Document(pdfDoc);
        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);
        form.flatFields();
        Map<String, PdfFormField> fields = form.getFormFields();
        PdfArray pos = form.getField("Name").getWidgets().get(0).getRectangle();
        // TODO Implement smth like ColumnText with area defining
        // ColumnText ct = new ColumnText(stamper.getOverContent(pos.page));
        // ct.setSimpleColumn(pos.position);
        // TODO Implement smth like XMLWorkerHelper.parseToElementList
        //ElementList elements = XMLWorkerHelper.parseToElementList("<div>Bruno <u>Lowagie</u></div>", null);
        //for (Element element : elements) {
        //    ct.addElement(element);
        //}
        //ct.go();
        pdfDoc.close();
    }
}
