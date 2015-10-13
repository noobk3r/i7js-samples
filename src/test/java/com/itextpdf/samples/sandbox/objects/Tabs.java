/**
 * Example written by Michaël Demey to demonstrate the use of the new Tab functionality.
 *
 * The code adds three paragraphs:
 * 1. Without a tab
 * 2. With a leading tab
 * 3. With an inline tab
 */
package com.itextpdf.samples.sandbox.objects;

import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.Property;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Tab;
import com.itextpdf.model.element.TabStop;
import com.itextpdf.model.element.Text;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class Tabs extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/objects/tabs.pdf";
    public static final String FONT = "./src/test/resources/sandbox/objects/Cardo-Regular.ttf";

    public static void main(String[] args) throws IOException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new Tabs().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(new FileOutputStream(DEST)));
        Document doc = new Document(pdfDoc);

        Paragraph p = new Paragraph("Hello World.");
        doc.add(p);

        p = new Paragraph();
        p.addTabStops(new TabStop(56f, Property.TabAlignment.LEFT));
        p.add(new Tab());
        p.add("Hello World with tab.");
        doc.add(p);

        p = new Paragraph();
        p.addTabStops(new TabStop(56f, Property.TabAlignment.LEFT));
        p.add(new Text("Hello World with"));
        p.add(new Tab());
        p.add(new Text("an inline tab."));
        doc.add(p);

        doc.close();
    }
}
