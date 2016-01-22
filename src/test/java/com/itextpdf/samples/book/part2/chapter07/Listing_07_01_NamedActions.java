package com.itextpdf.samples.book.part2.chapter07;

import com.itextpdf.basics.font.FontConstants;
import com.itextpdf.canvas.PdfCanvas;
import com.itextpdf.core.font.PdfFont;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfName;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.pdf.action.PdfAction;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.Property;
import com.itextpdf.model.Style;
import com.itextpdf.model.border.Border;
import com.itextpdf.model.element.Cell;
import com.itextpdf.model.element.Link;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Table;
import com.itextpdf.samples.GenericTest;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class Listing_07_01_NamedActions extends GenericTest {
    public static final String DEST = "./target/test/resources/book/part2/chapter07/Listing_07_01_NamedActions.pdf";

    public static final String MOVIE_TEMPLATES = "./src/test/resources/book/part1/chapter03/cmp_Listing_03_29_MovieTemplates.pdf";

    public static void main(String args[]) throws IOException, SQLException {
        // new Listing_03_29_MovieTemplates().manipulatePdf(Listing_03_29_MovieTemplates.DEST);
        new Listing_07_01_NamedActions().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(MOVIE_TEMPLATES),
                new PdfWriter(DEST));
        Document doc = new Document(pdfDoc);

        PdfFont symbol = PdfFont.createStandardFont(FontConstants.SYMBOL);

        Table table = new Table(4).
            setFont(symbol).
            setFontSize(20);
        Style cellStyle = new Style().setBorder(Border.NO_BORDER)
                .setHorizontalAlignment(Property.HorizontalAlignment.CENTER);

        PdfAction action = PdfAction.createNamed(pdfDoc, PdfName.FirstPage);
        Link first = new Link(String.valueOf((char) 220), action);
        table.addCell(new Cell()
                .add(new Paragraph().add(first))
                .addStyle(cellStyle));

        action = PdfAction.createNamed(pdfDoc, PdfName.PrevPage);
        Link previous = new Link(String.valueOf((char) 172), action);
        table.addCell(new Cell()
                .add(new Paragraph().add(previous))
                .addStyle(cellStyle));

        action = PdfAction.createNamed(pdfDoc, PdfName.NextPage);
        Link next = new Link(String.valueOf((char) 174), action);
        table.addCell(new Cell()
                .add(new Paragraph().add(next))
                .addStyle(cellStyle));

        action = PdfAction.createNamed(pdfDoc, PdfName.LastPage);
        Link last = new Link(String.valueOf((char) 222), action);
        table.addCell(new Cell()
                .add(new Paragraph().add(last))
                .addStyle(cellStyle));

        // Add the table to each page
        PdfCanvas canvas;
        for (int i = 1; i <= pdfDoc.getNumberOfPages(); i++) {
            table.setFixedPosition(i, 696, 0, 120);
            doc.add(table);
        }
        doc.close();
    }
}
