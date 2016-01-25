package com.itextpdf.samples.book.part2.chapter07;

import com.itextpdf.basics.geom.Rectangle;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.pdf.action.PdfAction;
import com.itextpdf.core.pdf.annot.PdfAnnotation;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.forms.fields.PdfButtonFormField;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.samples.GenericTest;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class Listing_07_27_Advertisement extends GenericTest {
    public static final String DEST = "./target/test/resources/book/part2/chapter07/Listing_07_27_Advertisement.pdf";

    /** Path to a resource. */
    public static final String RESOURCE = "./src/test/resources/book/part2/chapter07/hero.pdf";
    /** Path to a resource. */
    public static final String IMAGE = "./src/test/resources/book/part2/chapter07/close.png";
    /** The resulting PDF file. */

    public static final String NESTED_TABLES = "./src/test/resources/book/part1/chapter04/cmp_Listing_04_17_NestedTables.pdf";

    protected String[] arguments;

    public static void main(String args[]) throws IOException, SQLException {
        Listing_07_27_Advertisement application = new Listing_07_27_Advertisement();
        application.arguments = args;
        application.manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException, SQLException {
        // Listing_04_17_NestedTables.main(arguments);
        // Create a reader for the original document
        PdfReader reader = new PdfReader(NESTED_TABLES);
        // Create a reader for the advertisement resource
        PdfReader ad = new PdfReader(RESOURCE);
        // Create a stamper
        PdfDocument pdfDoc = new PdfDocument(reader, new PdfWriter(DEST));
        // Create the advertisement annotation for the menubar
        Rectangle rect = new Rectangle(400, 772, 545-400, 792-772);
        PdfButtonFormField button = PdfFormField.createPushButton(pdfDoc, rect, "click", "Close this advertisement");
        // TODO DEVSIX-233
        // button.setBackgroundColor(Color.RED);
        // button.setBorderColor(Color.RED);
        // TODO No setImage & setLayout & setIconHorizontalAdjustment on PdfFormField
        // button.setImage(Image.getInstance(IMAGE));
        // button.setLayout(PushbuttonField.LAYOUT_LABEL_LEFT_ICON_RIGHT);
        // button.setIconHorizontalAdjustment(1);
        PdfAnnotation menubar = button.getWidgets().get(0);
        String js = "var f1 = getField('click'); f1.display = display.hidden;"
                + "var f2 = getField('advertisement'); f2.display = display.hidden;";
        menubar.setAction(PdfAction.createJavaScript(js));
        // Add the annotation
        pdfDoc.getPage(1).addAnnotation(menubar);
        // Create the advertisement annotation for the content
        rect = new Rectangle(400, 550, 545-400, 772-550);
        button = PdfFormField.createPushButton(pdfDoc, rect, "advertisement",
                "Buy the book iText in Action 2nd edition");
        // TODO DEVSIX-233
        // button.setBackgroundColor(Color.WHITE);
        // button.setBorderColor(Color.RED);

        // TODO No setTemplate on Button
        // button.setTemplate(stamper.getImportedPage(ad, 1));
        // button.setLayout(PushbuttonField.LAYOUT_ICON_TOP_LABEL_BOTTOM);
        PdfAnnotation advertisement = button.getWidgets().get(0);
        advertisement.setAction(PdfAction.createURI("http://www.1t3xt.com/docs/book.php"));
        // Add the annotation
        pdfDoc.getPage(1).addAnnotation(advertisement);
        // Close the pdf document
        pdfDoc.close();
    }
}
