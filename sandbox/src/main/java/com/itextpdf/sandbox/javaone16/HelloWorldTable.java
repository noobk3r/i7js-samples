/**
 * Example written for JavaOne 2016.
 * Differences between iText 5 and iText 7 are discussed in the JavaOne talk
 * "Oops, I broke my API" by Raf Hens and Bruno Lowagie.
 * This is the iText 7 version of one of the examples.
 */
package com.itextpdf.sandbox.javaone16;

import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import com.itextpdf.test.annotations.WrapToTest;
import java.io.File;
import java.io.IOException;

@WrapToTest
public class HelloWorldTable {
    public static final String DEST
        = "results/javaone16/hello_table.pdf";
    
    public static void main(String[] args) throws IOException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
    	new HelloWorldTable().createPdf(DEST);
    }

    public void createPdf(String dest) throws IOException {
        PdfDocument pdf = new PdfDocument(new PdfWriter(dest));
        try (Document document = new Document(pdf)) {
            Table table = new Table(3);
            Cell cell = new Cell(1, 3)
                .setTextAlignment(TextAlignment.CENTER)
                .add("Cell with colspan 3");
            table.addCell(cell);
            cell = new Cell(2, 1)
                .add("Cell with rowspan 2")
                .setVerticalAlignment(VerticalAlignment.MIDDLE);
            table.addCell(cell);
            table.addCell("Cell 1.1");
            table.addCell(new Cell().add("Cell 1.2"));
            table.addCell(new Cell()
                .add("Cell 2.1")
                .setBackgroundColor(Color.LIGHT_GRAY)
                .setMargin(5));
            table.addCell(new Cell()
                .add("Cell 1.2")
                .setBackgroundColor(Color.LIGHT_GRAY)
                .setPadding(5));
            document.add(table);
        }
    }
}
