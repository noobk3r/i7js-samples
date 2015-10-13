/**
 * Example written by Bruno Lowagie in answer to:
 * http://stackoverflow.com/questions/29277611/itextsharp-continuing-ordered-list-on-second-page-with-a-number-other-than-1
 */
package com.itextpdf.samples.sandbox.objects;

import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.Property;
import com.itextpdf.model.element.List;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.IOException;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class ListInColumn extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/objects/list_in_column.pdf";
    public static final String SRC = "./src/test/resources/sandbox/objects/pages.pdf";

    public static void main(String[] args) throws IOException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new ListInColumn().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(SRC));
        // TODO copyPages doesn't give a possibility to write on copied pages
        PdfDocument pdfResultDoc = new PdfDocument(new PdfWriter(DEST));
        pdfDoc.copyPages(1, 2, pdfResultDoc);
        Document doc = new Document(pdfResultDoc);
//        //reader.selectPages("1-2");
//        Document doc = new Document(pdfDoc);
        List list = new List(Property.ListNumberingType.DECIMAL);
        for (int i = 0; i < 15; i++) {
            list.add("This is a list item. It will be repeated a number of times. "
                    + "This is done only for test purposes. "
                    + "I want a list item that is distributed over several lines.");
        }
        doc.add(list
                .setWidth(250)
                .setMarginLeft(200)
                .setMarginBottom(400)
                .setMarginTop(pdfDoc.getPage(1).getPageSize().getHeight() - 806));
        // Rectangle rect = new Rectangle(250, 400, 500 - 250, 806 - 400);
        doc.close();
    }
}
