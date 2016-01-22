/**
 * Example written by Bruno Lowagie in answer to:
 * http://stackoverflow.com/questions/27844914/align-justified-for-itext-list-item
 */
package com.itextpdf.samples.sandbox.objects;

import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.Property;
import com.itextpdf.model.element.List;
import com.itextpdf.model.element.ListItem;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class ListAlignment extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/objects/list_alignment.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new ListAlignment().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException {
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);

        String text = "test 1 2 3 ";
        for (int i = 0; i < 5; i++) {
            text = text + text;
        }
        List list = new List();
        ListItem item = new ListItem(text);
        item.setTextAlignment(Property.TextAlignment.JUSTIFIED);
        list.add(item);

        text = "a b c align ";
        for (int i = 0; i < 5; i++) {
            text = text + text;
        }
        item = new ListItem(text);
        item.setTextAlignment(Property.TextAlignment.JUSTIFIED);
        list.add(item);

        text = "supercalifragilisticexpialidociousss ";
        for (int i = 0; i < 3; i++) {
            text = text + text;
        }
        item = new ListItem(text);
        item.setTextAlignment(Property.TextAlignment.JUSTIFIED);
        list.add(item);
        doc.add(list);

        doc.close();
    }
}
