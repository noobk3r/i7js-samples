/**
 * Example written by Bruno Lowagie in answer to:
 * http://stackoverflow.com/questions/27820159/itext-list-how-to-remove-symbol-from-list
 */
package com.itextpdf.samples.sandbox.objects;

import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.*;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class RemoveListSymbol extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/objects/remove_list_symbol.pdf";

    public static void main(String[] args) throws IOException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new RemoveListSymbol().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(new FileOutputStream(DEST)));
        Document doc = new Document(pdfDoc);

        List list = new List();
        list.setListSymbol("");
        list.add(new ListItem("Item 1"));
        list.add(new ListItem("Item 2"));
        list.add(new ListItem("Item 3"));

        Paragraph phrase = new Paragraph();
        // TODO Cannot add List on Paragraph
        // phrase.add(list);

        Cell phraseCell = new Cell();
        phraseCell.add(phrase);

        Table phraseTable = new Table(2);
        phraseTable.setMarginTop(5);
        phraseTable.addCell(new Cell().add(new Paragraph("List wrapped in a phrase:")));
        phraseTable.addCell(phraseCell);

        Paragraph phraseTableWrapper = new Paragraph();
        // TODO Cannot add table on paragraph
        // phraseTableWrapper.add(phraseTable);

        doc.add(new Paragraph("A list, wrapped in a phrase, wrapped in a cell," +
                " wrapped in a table, wrapped in a phrase:"));
        doc.add(phraseTableWrapper);

        Cell cell = new Cell();
        cell.add(list);

        Table table = new Table(2);
        table.setMarginTop(5);
        table.addCell(new Cell().add(new Paragraph("List placed directly into cell")));
        table.addCell(cell);

        doc.add(new Paragraph("A list, wrapped in a cell, wrapped in a table:"));
        doc.add(table);

        Paragraph tableWrapper = new Paragraph();
        // TODO Cannot add table on paragraph
        // tableWrapper.add(table);
        doc.add(new Paragraph("A list, wrapped in a cell, wrapped in a table, wrapped in a phrase:"));
        doc.add(tableWrapper);

        doc.close();
    }
}
