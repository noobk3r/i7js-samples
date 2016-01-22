package com.itextpdf.samples.book.part2.chapter08;

import com.itextpdf.basics.font.FontConstants;
import com.itextpdf.basics.geom.Rectangle;
import com.itextpdf.canvas.PdfCanvas;
import com.itextpdf.core.color.Color;
import com.itextpdf.core.color.DeviceGray;
import com.itextpdf.core.font.PdfFont;
import com.itextpdf.core.pdf.PdfDictionary;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfName;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.pdf.action.PdfAction;
import com.itextpdf.core.pdf.annot.PdfAnnotation;
import com.itextpdf.core.pdf.xobject.PdfFormXObject;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfButtonFormField;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.model.Document;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class Listing_08_01_Buttons extends GenericTest {
    public static final String DEST = "./target/test/resources/book/part2/chapter08/Listing_08_01_Buttons.pdf";
    /**
     * The resulting PDF.
     */
    public static final String FILLED = "./target/test/resources/book/part2/chapter08/Listing_08_01_Buttons_filled.pdf";
    /**
     * Path to a JavaScript resource.
     */
    public static final String RESOURCE = "./src/test/resources/book/part2/chapter08/buttons.js";
    /**
     * Path to an image used as button icon.
     */
    public static final String IMAGE = "./src/test/resources/book/part2/chapter08/info.png";
    /**
     * Possible values of a radio field / checkboxes
     */
    public static final String[] LANGUAGES = {"English", "German", "French", "Spanish", "Dutch"};

    public static void main(String[] args) throws Exception {
        new Listing_08_01_Buttons().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        createPdf(DEST);
        fillPdf(DEST, FILLED);
    }

    protected static String readFileToString(String path) throws IOException {
        File file = new File(path);
        byte[] jsBytes = new byte[(int) file.length()];
        FileInputStream f = new FileInputStream(file);
        f.read(jsBytes);
        return new String(jsBytes);
    }

    public void createPdf(String filename) throws IOException {
        FileOutputStream fos = new FileOutputStream(filename);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);
        pdfDoc.getCatalog().setOpenAction(PdfAction.createJavaScript(pdfDoc, readFileToString(RESOURCE)));
        PdfCanvas canvas = new PdfCanvas(pdfDoc.addNewPage());
        PdfFont font = PdfFont.createStandardFont(FontConstants.HELVETICA);
        Rectangle rect;
        PdfButtonFormField radioGroup = PdfFormField.createRadioGroup(pdfDoc, "", "language");
        PdfFormField radio;
        for (int i = 0; i < LANGUAGES.length; i++) {
            rect = new Rectangle(40, 806 - i * 40, 60 - 40, 806 - 788);
            radio = PdfFormField.createRadioButton(pdfDoc, rect, radioGroup, LANGUAGES[i]);
            radio.setBorderColor(Color.DARK_GRAY);
            radio.setBackgroundColor(Color.LIGHT_GRAY);
            radio.setCheckType(PdfFormField.TYPE_CIRCLE);
            canvas
                    .beginText()
                    .setFontAndSize(font, 18)
                    .moveText(70, 790 - i * 40 + 20)
                    .showText(LANGUAGES[i])
                    .endText();
        }
        PdfAcroForm.getAcroForm(pdfDoc, true).addField(radioGroup);

        PdfButtonFormField checkBox;
        for (int i = 0; i < LANGUAGES.length; i++) {
            PdfFormXObject xObjectApp1 = new PdfFormXObject(new Rectangle(0, 0, 200 - 180, 806 - 788));
            PdfCanvas canvasApp1 = new PdfCanvas(xObjectApp1, pdfDoc);
            canvasApp1
                    .saveState()
                    .rectangle(1, 1, 18, 18)
                    .stroke()
                    .restoreState();
            PdfFormXObject xObjectApp2 = new PdfFormXObject(new Rectangle(0, 0, 200 - 180, 806 - 788));
            PdfCanvas canvasApp2 = new PdfCanvas(xObjectApp2, pdfDoc);
            canvasApp2
                    .saveState()
                    .setFillColorRgb(255, 128, 128)
                    .rectangle(1, 1, 18, 18)
                    .rectangle(180, 806 - i * 40, 200 - 180, 806 - 788)
                    .fillStroke()
                    .moveTo(1, 1)
                    .lineTo(19, 19)
                    .moveTo(1, 19)
                    .lineTo(19, 1)
                    .stroke()
                    .restoreState();

            rect = new Rectangle(180, 806 - i * 40, 200 - 180, 806 - 788);
            checkBox = PdfFormField.createCheckBox(pdfDoc, rect, "Off", LANGUAGES[i]);
            checkBox.getWidgets().get(0).getNormalAppearanceObject().put(new PdfName("Off"),
                    xObjectApp1.getPdfObject());
            checkBox.getWidgets().get(0).getNormalAppearanceObject().put(new PdfName("Yes"),
                    xObjectApp2.getPdfObject());
            // Write text
            canvas
                    .beginText()
                    .setFontAndSize(font, 18)
                    .moveText(210, 790 - i * 40 + 20)
                    .showText(LANGUAGES[i])
                    .endText();
            PdfAcroForm.getAcroForm(pdfDoc, true).addField(checkBox);
        }

        // Add the push button
        rect = new Rectangle(300, 806, 370 - 300, 806 - 788);
        PdfButtonFormField button = PdfFormField.createPushButton(pdfDoc, rect, "Buttons", "Push me");
        button.setBackgroundColor(new DeviceGray(0.75f));
        // TODO DEVSIX-233
        // button.setBorderColor(Color.DARK_GRAY);
        button.setBorderWidth(1);
        PdfDictionary borderStyleDict = new PdfDictionary();
        borderStyleDict.put(PdfName.S, PdfName.B);
        button.getWidgets().get(0).setBorderStyle(borderStyleDict);
//        button.setFontSize(12);
        // TODO No setTextColor & setLayout & setScaledIcon & setProportonalIcon & setIconHorizontalAdjustment & setImage on PdfFormField
        // button.setTextColor(GrayColor.GRAYBLACK);
        // button.setLayout(PushbuttonField.LAYOUT_ICON_LEFT_LABEL_RIGHT);
        // button.setScaleIcon(PushbuttonField.SCALE_ICON_ALWAYS);
        // button.setProportionalIcon(true);
        // button.setIconHorizontalAdjustment(0);
        // button.setImage(Image.getInstance(IMAGE));

        PdfAnnotation ann = button.getWidgets().get(0);
        ann.setAction(PdfAction.createJavaScript(pdfDoc, "this.showButtonState()"));
        pdfDoc.getFirstPage().addAnnotation(ann);

        doc.close();
    }

    /**
     * Manipulates a PDF file src with the file dest as result
     *
     * @param src  the original PDF
     * @param dest the resulting PDF
     * @throws IOException
     */
    public void fillPdf(String src, String dest) throws IOException {
        PdfReader reader = new PdfReader(src);
        PdfDocument pdfDoc = new PdfDocument(reader, new PdfWriter(new FileOutputStream(dest)));
        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);
        String[] radiostates = form.getField("language").getAppearanceStates();
        form.getField("language").setValue(radiostates[4]);
        for (int i = 0; i < LANGUAGES.length; i++) {
            String[] checkboxStates = form.getField("English").getAppearanceStates();
            form.getField(LANGUAGES[i]).setValue(checkboxStates[i % 2 == 0 ? 1 : 0], false);
        }
        pdfDoc.close();
        reader.close();
    }
}
