package com.itextpdf.samples.book.part2.chapter08;

import com.itextpdf.basics.font.FontConstants;
import com.itextpdf.basics.font.PdfEncodings;
import com.itextpdf.basics.geom.Rectangle;
import com.itextpdf.core.color.DeviceGray;
import com.itextpdf.core.font.PdfFont;
import com.itextpdf.core.font.PdfFontFactory;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfButtonFormField;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.model.Document;
import com.itextpdf.model.Property;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.samples.GenericTest;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class Listing_08_02_RadioButtons extends GenericTest {
    public static final String DEST = "./target/test/resources/book/part2/chapter08/Listing_08_02_RadioButtons.pdf";
    /**
     * Possible values of a Choice field.
     */
    public static final String[] LANGUAGES = {"English", "German", "French", "Spanish", "Dutch"};

    public static void main(String[] args) throws Exception {
        new Listing_08_02_RadioButtons().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(DEST));
        Document doc = new Document(pdfDoc);
        PdfFont font = PdfFontFactory.createFont(FontConstants.HELVETICA, PdfEncodings.WINANSI, false);
        PdfButtonFormField radioGroup = PdfFormField.createRadioGroup(pdfDoc, "", "language");
        radioGroup.setFieldName("language");
        Rectangle rect = new Rectangle(40, 806, 60 - 40, 788 - 806);
        PdfFormField radio;
        for (int page = 1; page <= LANGUAGES.length; page++) {
            pdfDoc.addNewPage();
            radio = PdfFormField.createRadioButton(pdfDoc, rect, radioGroup, LANGUAGES[page - 1]);
            radio.setPage(page);
            doc.showTextAligned(new Paragraph(LANGUAGES[page - 1]).setFont(font).setFontSize(18),
                    70, 790, page, Property.TextAlignment.LEFT, Property.VerticalAlignment.BOTTOM, 0);
            radio.getWidgets().get(0).setColor(new DeviceGray(0.8f).getColorValue());
        }
        PdfAcroForm.getAcroForm(pdfDoc, true).addField(radioGroup);
        doc.close();
    }
}
