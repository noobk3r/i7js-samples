package com.itextpdf.samples.book.part4.chapter13;

import com.itextpdf.basics.font.FontConstants;
import com.itextpdf.basics.geom.Rectangle;
import com.itextpdf.core.pdf.canvas.PdfCanvas;
import com.itextpdf.core.font.PdfFont;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfName;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.pdf.action.PdfAction;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.core.xmp.XMPException;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfButtonFormField;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.forms.fields.PdfTextFormField;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class Listing_13_16_AddJavaScriptToForm extends GenericTest {
    public static final String DEST
            = "./target/test/resources/book/part4/chapter13/Listing_13_16_AddJavaScriptToForm.pdf";
    public static final String ORIGINAL
            = "./target/test/resources/book/part4/chapter13/Listing_13_16_AddJavaScriptToForm_form_without_js.pdf";
    public static final String RESOURCE
            = "./src/test/resources/book/part4/chapter13/extra.js";

    /**
     * Creates a PDF document.
     * @param dest the path to the new PDF document
     * @throws    IOException
     * @throws    SQLException
     */
    public void createPdf(String dest) throws IOException{
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
        PdfCanvas canvas = new PdfCanvas(pdfDoc.addNewPage());

        canvas.beginText();
        canvas.setFontAndSize(PdfFont.createStandardFont(FontConstants.HELVETICA), 12);
        canvas.moveText(36, 770);
        canvas.showText("Married?");
        canvas.moveText(22, -20) ;// 58, 750);
        canvas.showText("YES");
        canvas.moveText(44, 0); //102, 750);
        canvas.showText("NO");
        canvas.moveText(-66, -20); // 36, 730);
        canvas.showText("Name partner?");
        canvas.endText();

        // create a radio button field
        PdfButtonFormField married = PdfFormField.createRadioGroup(pdfDoc, "Yes", "married");
        Rectangle rectYes = new Rectangle(40, 744, 16, 22);
        PdfFormField yes = PdfFormField.createRadioButton(pdfDoc, rectYes, married, "Yes");
        Rectangle rectNo = new Rectangle(84, 744, 16, 22);
        PdfFormField no = PdfFormField.createRadioButton(pdfDoc, rectNo, married, "No");
        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);
        form.addField(married);
        // create a text field
        Rectangle rect = new Rectangle(40, 710, 160, 16);
        PdfTextFormField partner = PdfFormField.createText(pdfDoc, rect, "partner", "partner");
        // TODO DEVSIX-233
        // partner.setBorderColor(Color.DARK_GRAY);
        partner.setBorderWidth(0.5f);
        form.addField(partner);

        pdfDoc.close();
    }

    /**
     * Manipulates a PDF file src with the file dest as result
     * @param src the original PDF
     * @param dest the resulting PDF
     * @throws IOException
     */
    public void changePdf(String src, String dest) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(src), new PdfWriter(dest));
        // Get the writer from the stamper
        // Add JavaScript
        pdfDoc.getCatalog().setOpenAction(PdfAction.createJavaScript(pdfDoc, readFileToString(RESOURCE)));
        // get the form fields
        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);
        PdfFormField fd = form.getField("married");
        // Get the PDF dictionary of the YES radio button and add an additional action
//        PdfDictionary dictYes =
//                (PdfDictionary)  fd.getPdfObject();
//        PdfDictionary yesAction = dictYes.getAsDictionary(PdfName.AA);
//        if (yesAction == null) {
//            yesAction = new PdfDictionary();
//        }
        fd.getWidgets().get(0).setAdditionalAction(PdfName.Fo, PdfAction.createJavaScript(pdfDoc, "setReadOnly(false);"));
        // yesAction.put(new PdfName("Fo"), PdfAction.createJavaScript(pdfDoc, "setReadOnly(false);"));
        // dictYes.put(PdfName.AA, yesAction);
        // Get the PDF dictionary of the NO radio button and add an additional action
//        PdfDictionary dictNo =
//                (PdfDictionary) PdfReader.getPdfObject(fd.getWidgetRef(1));
//        PdfDictionary noAction = dictNo.getAsDict(PdfName.AA);
//        if (noAction == null) noAction = new PdfDictionary();
//        noAction.put(new PdfName("Fo"),
//                PdfAction.javaScript("setReadOnly(true);", stamper.getWriter()));
//        dictNo.put(PdfName.AA, noAction);
        // Create a submit button and add it to the stamper
        fd.getWidgets().get(1).setAdditionalAction(PdfName.Fo, PdfAction.createJavaScript(pdfDoc, "setReadOnly(true);"));
        PdfButtonFormField button = PdfFormField.createPushButton(pdfDoc, new Rectangle(40, 690, 160, 20), "submit", "validate and submit");
        button.setVisibility(PdfFormField.VISIBLE_BUT_DOES_NOT_PRINT);
        button.setAction(PdfAction.createJavaScript(pdfDoc, "validate();"));
        form.addField(button);
        // close the stamper
        pdfDoc.close();
    }

    protected static String readFileToString(String path) throws IOException {
        File file = new File(path);
        byte[] jsBytes = new byte[(int) file.length()];
        FileInputStream f = new FileInputStream(file);
        f.read(jsBytes);
        return new String(jsBytes);
    }

    public static void main(String args[]) throws IOException, SQLException, XMPException {
        new Listing_13_16_AddJavaScriptToForm().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws IOException, SQLException, XMPException {
        createPdf(ORIGINAL);
        changePdf(ORIGINAL, dest);
    }
}
