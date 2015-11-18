package com.itextpdf.samples.book.part1.chapter04;

import com.itextpdf.basics.geom.PageSize;
import com.itextpdf.core.color.Color;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.Property;
import com.itextpdf.model.element.AreaBreak;
import com.itextpdf.model.element.Cell;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Table;
import com.itextpdf.samples.GenericTest;
import com.lowagie.database.DatabaseConnection;
import com.lowagie.database.HsqldbConnection;
import com.lowagie.filmfestival.Movie;
import com.lowagie.filmfestival.PojoFactory;
import com.lowagie.filmfestival.PojoToElementFactory;
import com.lowagie.filmfestival.Screening;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class Listing_04_18_HeaderFooter1 extends GenericTest {
    static public final String DEST =
            "./target/test/resources/book/part1/chapter04/Listing_04_18_HeaderFooter1.pdf";
    public static final String RESOURCE = "./src/test/resources/book/part1/chapter02/posters/%s.jpg";

    public static void main(String args[]) throws IOException, SQLException {
        new Listing_04_18_HeaderFooter1().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException, SQLException {
        // create the database connection
        DatabaseConnection connection = new HsqldbConnection("filmfestival");

        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc, new PageSize(PageSize.A4).rotate());

        List<Date> days = PojoFactory.getDays(connection);
        int d = 1;
        for (Date day : days) {
            if (1 != d) {
                doc.add(new AreaBreak());
            }
            doc.add(getTable(connection, day));
            d++;
        }

        doc.close();
        connection.close();
    }

    public Table getTable(DatabaseConnection connection, Date day) throws UnsupportedEncodingException, SQLException {
        // Create a table with 7 columns
        Table table = new Table(new float[]{2, 1, 2, 5, 1, 3, 2});
        // TODO Ignore setWidth(0) doesn't work correct with headers and footers
        table.setWidthPercent(100);
        // TODO Implement a facility to set default-cell properties
        // TODO No setUseAscender(boolean) and setUseDescender(boolean)
        // table.getDefaultCell().setUseAscender(true);
        // table.getDefaultCell().setUseDescender(true);
        // Add the first header row
        Cell cell = new Cell(1, 7).add(new Paragraph(day.toString()).setFontColor(Color.WHITE));
        cell.setBackgroundColor(Color.BLACK);
        cell.setTextAlignment(Property.TextAlignment.CENTER);
        table.addHeaderCell(cell);

        // set headers and footers
        table.addHeaderCell(new Cell().add(new Paragraph("Location")).setBackgroundColor(Color.LIGHT_GRAY));
        table.addHeaderCell(new Cell().add(new Paragraph("Time")).setBackgroundColor(Color.LIGHT_GRAY));
        table.addHeaderCell(new Cell().add(new Paragraph("Run Length")).setBackgroundColor(Color.LIGHT_GRAY));
        table.addHeaderCell(new Cell().add(new Paragraph("Title")).setBackgroundColor(Color.LIGHT_GRAY));
        table.addHeaderCell(new Cell().add(new Paragraph("Year")).setBackgroundColor(Color.LIGHT_GRAY));
        table.addHeaderCell(new Cell().add(new Paragraph("Directors")).setBackgroundColor(Color.LIGHT_GRAY));
        table.addHeaderCell(new Cell().add(new Paragraph("Countries")).setBackgroundColor(Color.LIGHT_GRAY));

        table.addFooterCell(new Cell().add(new Paragraph("Location")).setBackgroundColor(Color.LIGHT_GRAY));
        table.addFooterCell(new Cell().add(new Paragraph("Time")).setBackgroundColor(Color.LIGHT_GRAY));
        table.addFooterCell(new Cell().add(new Paragraph("Run Length")).setBackgroundColor(Color.LIGHT_GRAY));
        table.addFooterCell(new Cell().add(new Paragraph("Title")).setBackgroundColor(Color.LIGHT_GRAY));
        table.addFooterCell(new Cell().add(new Paragraph("Year")).setBackgroundColor(Color.LIGHT_GRAY));
        table.addFooterCell(new Cell().add(new Paragraph("Directors")).setBackgroundColor(Color.LIGHT_GRAY));
        table.addFooterCell(new Cell().add(new Paragraph("Countries")).setBackgroundColor(Color.LIGHT_GRAY));

        // Now let's loop over the screenings
        List<Screening> screenings = PojoFactory.getScreenings(connection, day);
        Movie movie;
        for (Screening screening : screenings) {
            movie = screening.getMovie();
            table.addCell(new Cell().add(new Paragraph(screening.getLocation())));
            table.addCell(new Cell().add(new Paragraph(String.format("%1$tH:%1$tM", screening.getTime()))));
            table.addCell(new Cell().add(new Paragraph(String.format("%d '", movie.getDuration()))));
            table.addCell(new Cell().add(new Paragraph(movie.getMovieTitle())));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(movie.getYear()))));
            cell = new Cell();
            // TODO No setUseAscender(boolean) and setUseDescender(boolean)
            // cell.setUseAscender(true);
            // cell.setUseDescender(true);
            cell.add(PojoToElementFactory.getDirectorList(movie));
            table.addCell(cell);
            cell = new Cell();
            // cell.setUseAscender(true);
            // cell.setUseDescender(true);
            cell.add(PojoToElementFactory.getCountryList(movie));
            table.addCell(cell);
        }
        return table;
    }
}

