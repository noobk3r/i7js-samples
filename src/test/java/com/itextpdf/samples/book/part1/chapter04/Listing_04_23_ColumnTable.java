/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

package com.itextpdf.samples.book.part1.chapter04;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.core.geom.PageSize;
import com.itextpdf.core.geom.Rectangle;
import com.itextpdf.core.color.Color;
import com.itextpdf.core.font.PdfFont;
import com.itextpdf.core.font.PdfFontFactory;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.model.Document;
import com.itextpdf.model.Property;
import com.itextpdf.model.Style;
import com.itextpdf.model.element.AreaBreak;
import com.itextpdf.model.element.Cell;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Table;
import com.itextpdf.model.layout.LayoutArea;
import com.itextpdf.model.layout.LayoutResult;
import com.itextpdf.model.renderer.DocumentRenderer;
import com.itextpdf.samples.GenericTest;
import com.itextpdf.test.annotations.type.SampleTest;
import com.lowagie.database.DatabaseConnection;
import com.lowagie.database.HsqldbConnection;
import com.lowagie.filmfestival.Movie;
import com.lowagie.filmfestival.PojoFactory;
import com.lowagie.filmfestival.Screening;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class Listing_04_23_ColumnTable extends GenericTest {
    public static final String DEST =
            "./target/test/resources/book/part1/chapter04/Listing_04_23_ColumnTable.pdf";
    public static final String RESOURCE =
            "./src/test/resources/book/part1/chapter02/posters/%s.jpg";

    protected PdfFont bold;
    protected Date date;

    public static void main(String args[]) throws IOException, SQLException {
        new Listing_04_23_ColumnTable().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException, SQLException {
        // create the database connection
        DatabaseConnection connection = new HsqldbConnection("filmfestival");

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
        Document doc = new Document(pdfDoc, new PageSize(PageSize.A4).rotate());

        bold = PdfFontFactory.createStandardFont(FontConstants.HELVETICA_BOLD, PdfEncodings.WINANSI);

        ColumnDocumentRenderer renderer = new ColumnDocumentRenderer(doc);
        doc.setRenderer(renderer);
        List<Date> days = PojoFactory.getDays(connection);
        // Loop over the festival days
        date = days.get(0);
        for (int i = 0; i < days.size(); i++) {
            // add content to the column
            doc.add(getTable(connection, days.get(i)));

            if (days.size()-1 != i) {
                date = days.get(i + 1);
                if (0 == renderer.nextAreaNumber % 2) {
                    doc.add(new AreaBreak());
                } else {
                    doc.add(new AreaBreak());
                    doc.add(new AreaBreak());
                }
            }
        }

        doc.close();
        connection.close();
    }

    public static Table getHeaderTable(Date day, int page, PdfFont font) {
        Table header = new Table(3);
        header.setWidthPercent(100);
        Style style = new Style().setBackgroundColor(Color.BLACK).setFontColor(Color.WHITE).setFont(font);
        Paragraph p = new Paragraph("Foobar Film Festival").addStyle(style);
        header.addCell(new Cell().add(p));
        p = new Paragraph(day.toString()).addStyle(style);
        header.addCell(new Cell().add(p).setTextAlignment(Property.TextAlignment.CENTER));
        p = new Paragraph(String.format("page %d", page)).addStyle(style);
        header.addCell(new Cell().add(p).setTextAlignment(Property.TextAlignment.RIGHT));
        return header;
    }

    public Table getTable(DatabaseConnection connection, Date day)
            throws SQLException, IOException {
        Table table = new Table(new float[]{2, 1, 2, 5, 1});
        table.setWidthPercent(100);
        // TODO No setUseAscender(boolean)
        // table.getDefaultCell().setUseAscender(true);
        // table.getDefaultCell().setUseDescender(true);
        Style style = new Style().setBackgroundColor(Color.LIGHT_GRAY);
        table.addHeaderCell(new Cell().add("Location").addStyle(style));
        table.addHeaderCell(new Cell().add("Time").addStyle(style));
        table.addHeaderCell(new Cell().add("Run Length").addStyle(style));
        table.addHeaderCell(new Cell().add("Title").addStyle(style));
        table.addHeaderCell(new Cell().add("Year").addStyle(style));
        table.addFooterCell(new Cell().add("Location").addStyle(style));
        table.addFooterCell(new Cell().add("Time").addStyle(style));
        table.addFooterCell(new Cell().add("Run Length").addStyle(style));
        table.addFooterCell(new Cell().add("Title").addStyle(style));
        table.addFooterCell(new Cell().add("Year").addStyle(style));

        List<Screening> screenings = PojoFactory.getScreenings(connection, day);
        Movie movie;
        for (Screening screening : screenings) {
            movie = screening.getMovie();
            table.addCell(screening.getLocation());
            table.addCell(String.format("%1$tH:%1$tM", screening.getTime()));
            table.addCell(String.format("%d '", movie.getDuration()));
            table.addCell(movie.getMovieTitle());
            table.addCell(String.valueOf(movie.getYear()));
        }
        return table;
    }


    protected class ColumnDocumentRenderer extends DocumentRenderer {
        protected int nextAreaNumber = 0;

        public ColumnDocumentRenderer(Document document) {
            super(document);
        }

        @Override
         public LayoutArea updateCurrentArea(LayoutResult overflowResult) {
            if (nextAreaNumber % 2 == 0) {
                currentPageNumber = super.updateCurrentArea(overflowResult).getPageNumber();

                addChild(getHeaderTable(date, currentPageNumber, bold).createRendererSubTree());

                nextAreaNumber++;
                currentArea = new LayoutArea(currentPageNumber, new Rectangle(36, 36, 383, 450));
            } else {
                nextAreaNumber++;
                currentArea = new LayoutArea(currentPageNumber, new Rectangle(423, 36, 383, 450));
            }
            return currentArea;
        }
    }
}

