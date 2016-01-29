/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

package com.itextpdf.samples.sandbox.tables;

import com.itextpdf.core.geom.PageSize;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Cell;
import com.itextpdf.model.element.List;
import com.itextpdf.model.element.ListItem;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Table;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileOutputStream;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class ListInCell extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/tables/list_in_cell.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new ListInCell().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc, PageSize.A4.rotate());

        // This is how not to do it (but it works anyway):
        // We create a list:
        List list = new List();
        list.add(new ListItem("Item 1"));
        list.add(new ListItem("Item 2"));
        list.add(new ListItem("Item 3"));

        // We wrap this list in a phrase:
        // TODO Can't add list to paragraph
        //Paragraph phrase = new Paragraph();
        //phrase.add(list);
        // We add this phrase to a cell
        //Cell phraseCell = new Cell();
        //phraseCell.add(phrase);

        // We add the cell to a table:
        Table phraseTable = new Table(2);
        phraseTable.setMarginTop(5);
        phraseTable.addCell("List wrapped in a phrase:");
        //phraseTable.addCell(phraseCell);

        // TODO Can't add table to paragraph
        // We wrap the phrase table in another table:
        //Paragraph phraseTableWrapper = new Paragraph();
        //phraseTableWrapper.add(phraseTable);

        // We add these nested tables to the document:
        doc.add(new Paragraph("A list, wrapped in a phrase, wrapped in a cell, " +
                "wrapped in a table, wrapped in a phrase:"));
        //doc.add(phraseTableWrapper);

        // This is how to do it:

        // We add the list directly to a cell:
        Cell cell = new Cell();
        cell.add(list);

        // We add the cell to the table:
        Table table = new Table(2);
        table.setMarginTop(5);
        table.addCell("List placed directly into cell");
        table.addCell(cell);

        // We add the table to the document:
        doc.add(new Paragraph("A list, wrapped in a cell, wrapped in a table:"));
        doc.add(table);

        // Avoid adding tables to phrase (but it works anyway):

        // TODO Can't add table to paragraph
        // tableWrapper.add(table);
        //Paragraph tableWrapper = new Paragraph();
        doc.add(new Paragraph("A list, wrapped in a cell, wrapped in a table, wrapped in a phrase:"));
        //doc.add(tableWrapper);

        doc.close();
    }
}
