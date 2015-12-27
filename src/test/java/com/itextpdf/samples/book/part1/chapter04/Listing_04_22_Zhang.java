package com.itextpdf.samples.book.part1.chapter04;

import com.itextpdf.canvas.PdfCanvas;
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

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class Listing_04_22_Zhang extends GenericTest {
    public static final String DEST =
            "./target/test/resources/book/part1/chapter04/Listing_04_22_Zhang.pdf";
    public static final String RESOURCE = "./src/test/resources/book/part1/chapter02/posters/%s.jpg";

    public static void main(String args[]) throws IOException, SQLException {
        new Listing_04_22_Zhang().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException, SQLException {
        // create the database connection
        DatabaseConnection connection = new HsqldbConnection("filmfestival");

        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);

        // Create a table and fill it with movies
        List<Movie> movies = PojoFactory.getMovies(connection, 3);
        Table table = new Table(new float[]{1, 5, 5, 1});
        table.setWidth(600);
        for (Movie movie : movies) {
            table.addCell(String.valueOf(movie.getYear()));
            table.addCell(movie.getMovieTitle());
            table.addCell(movie.getOriginalTitle());
            table.addCell(String.valueOf(movie.getDuration()));
        }
        // set the total width of the table
        PdfCanvas canvas = new PdfCanvas(pdfDoc.getFirstPage());
        // draw the first two columns on one page
        // TODO No writeSelectedRows
        // table.writeSelectedRows(0, 2, 0, -1, 236, 806, canvas);
        doc.add(new AreaBreak());
        // draw the remaining two columns on the next page
        // table.writeSelectedRows(2, -1, 0, -1, 36, 806, canvas);

        doc.close();
        connection.close();
    }

    public Table getTable(DatabaseConnection connection, Date day) throws UnsupportedEncodingException, SQLException {
        // Create a table with 7 columns
        Table table = new Table(new float[]{2, 1, 2, 5, 1, 3, 2});
        // TODO setWidth(0) (analog of setWidthPercent(100)) doesn't work correct with headers and footers
        table.setWidthPercent(100);
        // TODO No faciliry to set default-cell properties
        // TODO No setUseAscender(boolean) and setUseDescender(boolean)
        // table.getDefaultCell().setUseAscender(true);
        // table.getDefaultCell().setUseDescender(true);
        // Add the first header row
        Cell cell = new Cell(1, 7).add(new Paragraph(day.toString()).setFontColor(Color.WHITE));
        cell.setBackgroundColor(Color.BLACK);
        cell.setHorizontalAlignment(Property.HorizontalAlignment.CENTER);
        table.addHeaderCell(cell);

        // set headers and footers
        table.addHeaderCell(new Cell().add("Location").setBackgroundColor(Color.LIGHT_GRAY));
        table.addHeaderCell(new Cell().add("Time").setBackgroundColor(Color.LIGHT_GRAY));
        table.addHeaderCell(new Cell().add("Run Length").setBackgroundColor(Color.LIGHT_GRAY));
        table.addHeaderCell(new Cell().add("Title").setBackgroundColor(Color.LIGHT_GRAY));
        table.addHeaderCell(new Cell().add("Year").setBackgroundColor(Color.LIGHT_GRAY));
        table.addHeaderCell(new Cell().add("Directors").setBackgroundColor(Color.LIGHT_GRAY));
        table.addHeaderCell(new Cell().add("Countries").setBackgroundColor(Color.LIGHT_GRAY));

        table.addFooterCell(new Cell().add("Location").setBackgroundColor(Color.LIGHT_GRAY));
        table.addFooterCell(new Cell().add("Time").setBackgroundColor(Color.LIGHT_GRAY));
        table.addFooterCell(new Cell().add("Run Length").setBackgroundColor(Color.LIGHT_GRAY));
        table.addFooterCell(new Cell().add("Title").setBackgroundColor(Color.LIGHT_GRAY));
        table.addFooterCell(new Cell().add("Year").setBackgroundColor(Color.LIGHT_GRAY));
        table.addFooterCell(new Cell().add("Directors").setBackgroundColor(Color.LIGHT_GRAY));
        table.addFooterCell(new Cell().add("Countries").setBackgroundColor(Color.LIGHT_GRAY));

        // Now let's loop over the screenings
        List<Screening> screenings = PojoFactory.getScreenings(connection, day);
        Movie movie;
        for (Screening screening : screenings) {
            movie = screening.getMovie();
            table.addCell(screening.getLocation());
            table.addCell(String.format("%1$tH:%1$tM", screening.getTime()));
            table.addCell(String.format("%d '", movie.getDuration()));
            table.addCell(movie.getMovieTitle());
            table.addCell(String.valueOf(movie.getYear()));
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

