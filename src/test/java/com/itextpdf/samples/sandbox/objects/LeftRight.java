/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

/**
 * Example written by Bruno Lowagie in answer to:
 * http://stackoverflow.com/questions/29575142/how-to-align-two-paragraphs-or-text-in-left-and-right-in-a-same-line-in-pdf
 */
package com.itextpdf.samples.sandbox.objects;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.Property;
import com.itextpdf.model.element.Cell;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Table;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class LeftRight extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/objects/left_right.pdf";

    public static void main(String[] args) throws IOException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new LeftRight().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(new FileOutputStream(DEST)));
        Document doc = new Document(pdfDoc);

        // TODO There is no VerticalPositionMark
        Paragraph glue = null; // new Paragraph(new VerticalPositionMark());
        Paragraph p = new Paragraph("Text to the left");
        //p.add(new (glue));
        p.add("Text to the right");
        doc.add(p);
        Table table = new Table(3);
        table.addCell(getCell("Text to the left", Property.TextAlignment.LEFT));
        table.addCell(getCell("Text in the middle", Property.TextAlignment.CENTER));
        table.addCell(getCell("Text to the right", Property.TextAlignment.RIGHT));
        doc.add(table);

        doc.close();
    }

    public Cell getCell(String text, Property.TextAlignment alignment) {
        Cell cell = new Cell().add(new Paragraph(text));
        cell.setPadding(0);
        cell.setTextAlignment(alignment);
        cell.setBorder(null);
        return cell;
    }
}
