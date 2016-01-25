package com.itextpdf.samples.book.part4.chapter16;

import com.itextpdf.basics.geom.Rectangle;
import com.itextpdf.core.color.DeviceGray;
import com.itextpdf.core.pdf.PdfDeveloperExtension;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfVersion;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.pdf.action.PdfAction;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfButtonFormField;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.forms.fields.PdfTextFormField;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class Listing_16_22_FestivalCalendar2 extends GenericTest {
    public static final String DEST = "./target/test/resources/book/part4/chapter16/Listing_16_22_FestivalCalendar2.pdf";
//    public static final String RESOURCE = "resources/swf/FestivalCalendar2.swf";
    public static final String JS = "./src/test/resources/book/part4/chapter16/show_date.js";

    protected static String readFileToString(String path) throws IOException {
        File file = new File(path);
        byte[] jsBytes = new byte[(int) file.length()];
        FileInputStream f = new FileInputStream(file);
        f.read(jsBytes);
        return new String(jsBytes);
    }

    public static void main(String args[]) throws Exception {
        new Listing_16_22_FestivalCalendar2().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest), PdfVersion.PDF_1_7);
        pdfDoc.getCatalog().addDeveloperExtension(PdfDeveloperExtension.ADOBE_1_7_EXTENSIONLEVEL3);
        pdfDoc.getCatalog().setOpenAction(PdfAction.createJavaScript(readFileToString(JS)));
        // // we prepare a RichMediaAnnotation
        // TODO No RichMedia: RichMediaAnnotation, RichMediaParams, RichMediaActivation and so on
        // RichMediaAnnotation richMedia = new RichMediaAnnotation(writer, new Rectangle(36, 560, 561, 760));
        // // we embed the swf file
        // PdfFileSpecification fs
        //         = PdfFileSpecification.fileEmbedded(writer, RESOURCE, "FestivalCalendar2.swf", null);
        // // we declare the swf file as an asset
        // PdfIndirectReference asset = richMedia.addAsset("FestivalCalendar2.swf", fs);
        // // we create a configuration
        // RichMediaConfiguration configuration = new RichMediaConfiguration(PdfName.FLASH);
        // RichMediaInstance instance = new RichMediaInstance(PdfName.FLASH);
        // instance.setAsset(asset);
        // configuration.addInstance(instance);
        // // we add the configuration to the annotation
        // PdfIndirectReference configurationRef = richMedia.addConfiguration(configuration);
        // // activation of the rich media
        // RichMediaActivation activation = new RichMediaActivation();
        // activation.setConfiguration(configurationRef);
        // richMedia.setActivation(activation);
        // // we add the annotation
        // PdfAnnotation richMediaAnnotation = richMedia.createAnnotation();
        // richMediaAnnotation.setFlags(PdfAnnotation.FLAGS_PRINT);
        // writer.addAnnotation(richMediaAnnotation);
        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);
        String[] days = new String[]{"2011-10-12", "2011-10-13", "2011-10-14", "2011-10-15",
                "2011-10-16", "2011-10-17", "2011-10-18", "2011-10-19"};
        for (int i = 0; i < days.length; i++) {
            Rectangle rect = new Rectangle(36 + (65 * i), 765, 64, 15);
            PdfButtonFormField button = PdfFormField.createPushButton(pdfDoc, rect, "button" + i, days[i]);
            button.setBackgroundColor(new DeviceGray(0.75f));
            // TODO No setBorderStyle, setLayout, setScaleIcon, setProportionalIcon, seticonHorizontalAdjustment on PdfFormField
            // button.setsetBorderStyle(PdfBorderDictionary.STYLE_BEVELED);
            // button.setTextColor(GrayColor.GRAYBLACK);
            // button.setFontSize(12);
            // button.setLayout(PushbuttonField.LAYOUT_ICON_LEFT_LABEL_RIGHT);
            // button.setScaleIcon(PushbuttonField.SCALE_ICON_ALWAYS);
            // button.setProportionalIcon(true);
            // button.setIconHorizontalAdjustment(0);
            // PdfFormField field = button.getField();
            // RichMediaCommand command = new RichMediaCommand(new PdfString("getDateInfo"));
            // command.setArguments(new PdfString(days[i]));
            // RichMediaExecuteAction action
            //         = new RichMediaExecuteAction(richMediaAnnotation.getIndirectReference(), command);
            // field.setAction(action);
            form.add(button);
        }
        PdfTextFormField text = PdfFormField.createText(pdfDoc, new Rectangle(36, 785, 523, 21), "date", "date");
        text.setReadOnly(true);
        form.addField(text);
        pdfDoc.close();
        pdfDoc.close();
    }
}
