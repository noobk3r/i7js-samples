/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

package com.itextpdf.samples.book.part2.chapter08;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfButtonFormField;
import com.itextpdf.samples.GenericTest;

import java.io.IOException;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

// !!!!! Before unignore revise Listing_07_27_Advertisement  and put its result as this example source
@Ignore
@Category(SampleTest.class)
public class Listing_08_06_ReplaceIcon extends GenericTest {
    public static final String DEST = "./target/test/resources/book/part2/chapter08/Listing_08_06_ReplaceIcon.pdf";

    public static final String ADVERTISEMENT = "./src/test/resources/book/part2/chapter07/cmp_Listing_07_27_Advertisement.pdf";

    /** Image that will be used as an icon. */
    public static final String RESOURCE = "./src/test/resources/book/part2/chapter08/iia2.jpg";
    /**
     * Possible values of a Choice field.
     */

    protected String[] arguments;

    public static void main(String[] args) throws Exception {
        Listing_08_06_ReplaceIcon application = new Listing_08_06_ReplaceIcon();
        application.arguments = args;
        application.manipulatePdf(DEST);
    }

    public void manipulatePdf2(String src, String dest) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(src),new PdfWriter(dest));
        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);
        PdfButtonFormField ad = (PdfButtonFormField) form.getField("advertisement");
        // TODO No setLayout & setProportionalIcon & setImage on PdfFormField
        // ad.setLayout(PushbuttonField.LAYOUT_ICON_ONLY);
        // ad.setProportionalIcon(true);
        // ad.setImage(Image.getInstance(RESOURCE));
        form.removeField("advertisement");
        form.addField(ad);
        pdfDoc.close();
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        // Listing_07_27_Advertisement.main(arguments);
        manipulatePdf2(ADVERTISEMENT, DEST);
    }
}
