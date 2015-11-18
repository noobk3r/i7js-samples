package com.itextpdf.samples.book.part1.chapter04;

import com.itextpdf.basics.font.FontConstants;
import com.itextpdf.basics.geom.PageSize;
import com.itextpdf.basics.image.ImageFactory;
import com.itextpdf.core.color.Color;
import com.itextpdf.core.color.DeviceRgb;
import com.itextpdf.core.font.PdfFont;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.Property;
import com.itextpdf.model.border.Border;
import com.itextpdf.model.element.AreaBreak;
import com.itextpdf.model.element.Cell;
import com.itextpdf.model.element.Image;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Table;
import com.itextpdf.model.element.Text;
import com.itextpdf.samples.GenericTest;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.html.WebColors;
import com.lowagie.database.DatabaseConnection;
import com.lowagie.database.HsqldbConnection;
import com.lowagie.filmfestival.Movie;
import com.lowagie.filmfestival.PojoFactory;
import com.lowagie.filmfestival.PojoToElementFactory;
import com.lowagie.filmfestival.Screening;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class Listing_04_17_NestedTables extends GenericTest {
    static public final String DEST =
            "./target/test/resources/book/part1/chapter04/Listing_04_17_NestedTables.pdf";
    public static final String RESOURCE = "./src/test/resources/book/part1/chapter02/posters/%s.jpg";
    public HashMap<String, com.itextpdf.basics.image.Image> images =
            new HashMap<String, com.itextpdf.basics.image.Image>();

    protected PdfFont bold;

    public static void main(String args[]) throws IOException, SQLException {
        new Listing_04_17_NestedTables().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException, SQLException {
        // create the database connection
        DatabaseConnection connection = new HsqldbConnection("filmfestival");

        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc, new PageSize(PageSize.A4));

        bold = PdfFont.createStandardFont(pdfDoc, FontConstants.HELVETICA_BOLD); // 12

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
    }

    public Table getTable(DatabaseConnection connection, Date day)
            throws UnsupportedEncodingException, SQLException, MalformedURLException {
        // Create a table with only one column
        Table table = new Table(1);
        // add the cell with the date
        Cell cell = new Cell().add(new Paragraph(day.toString()).setFontColor(Color.WHITE));
        cell.setBackgroundColor(Color.BLACK);
        cell.setTextAlignment(Property.TextAlignment.CENTER);
        table.addCell(cell);
        // add the movies as nested tables
        List<Screening> screenings = PojoFactory.getScreenings(connection, day);
        for (Screening screening : screenings) {
            table.addCell(new Cell().add(getTable(screening)));
        }
        return table;
    }

    private Table getTable(Screening screening) throws MalformedURLException {
        // Create a table with 4 columns
        Table table = new Table(new float[]{1, 5, 10, 10});
        table.setWidth(0);
        // Get the movie
        Movie movie = screening.getMovie();
        // A cell with the title as a nested table spanning the complete row
        Cell cell = new Cell(1, 4);
        // nesting is done with addElement() in this example
        cell.add(fullTitle(screening));
        cell.setBorder(Border.NO_BORDER);
        // TODO Get rid of itext5 code
        BaseColor color = WebColors.getRGBColor(
                "#" + screening.getMovie().getEntry().getCategory().getColor());
        cell.setBackgroundColor(new DeviceRgb(color.getRed(), color.getGreen(), color.getBlue()));
        table.addCell(cell);
        // empty cell
        cell = new Cell();
        cell.setBorder(Border.NO_BORDER);
        // TODO No setUseAscender(boolean) and setUseDescender(boolean)
        // cell.setUseAscender(true);
        // cell.setUseDescender(true);
        table.addCell(cell);
        // cell with the movie poster
        cell = new Cell().add(getImage(movie.getImdb()));
        cell.setBorder(Border.NO_BORDER);
        table.addCell(cell);
        // cell with the list of directors
        cell = new Cell();
        cell.add(PojoToElementFactory.getDirectorList(movie));
        cell.setBorder(Border.NO_BORDER);
        // cell.setUseAscender(true);
        // cell.setUseDescender(true);
        table.addCell(cell);
        // cell with the list of countries
        cell = new Cell();
        cell.add(PojoToElementFactory.getCountryList(movie));
        cell.setBorder(Border.NO_BORDER);
        // cell.setUseAscender(true);
        // cell.setUseDescender(true);
        table.addCell(cell);
        return table;
    }

    private Table fullTitle(Screening screening) {
        Table table = new Table(new float[]{3, 15, 2});
        table.setWidth(0);
        // cell 1: location and time
        Cell cell = new Cell();
        cell.setBorder(Border.NO_BORDER);
        cell.setBackgroundColor(Color.WHITE);
        // cell.setUseAscender(true);
        // cell.setUseDescender(true);
        String s = String.format("%s \u2013 %2$tH:%2$tM",
                screening.getLocation(), screening.getTime().getTime());
        cell.add(new Paragraph(s).setTextAlignment(Property.TextAlignment.CENTER));
        table.addCell(cell);
        // cell 2: English and original title 
        Movie movie = screening.getMovie();
        Paragraph p = new Paragraph();
        p.add(new Text(movie.getMovieTitle()).setFont(bold));
        p.setFixedLeading(16);
        if (movie.getOriginalTitle() != null) {
            p.add(new Text(" (" + movie.getOriginalTitle() + ")"));
        }
        cell = new Cell();
        cell.add(p);
        cell.setBorder(Border.NO_BORDER);
        // cell.setUseAscender(true);
        // cell.setUseDescender(true);
        table.addCell(cell);
        // cell 3 duration
        cell = new Cell();
        cell.setBorder(Border.NO_BORDER);
        cell.setBackgroundColor(Color.WHITE);
        // cell.setUseAscender(true);
        // cell.setUseDescender(true);
        p = new Paragraph(String.format("%d'", movie.getDuration()));
        p.setTextAlignment(Property.TextAlignment.CENTER);
        cell.add(p);
        table.addCell(cell);
        return table;
    }

    public Image getImage(String imdb) throws MalformedURLException {
        com.itextpdf.basics.image.Image img = images.get(imdb);
        if (img == null) {
            img = ImageFactory.getImage(String.format(RESOURCE, imdb));
            images.put(imdb, img);
        }
        return new Image(img).scaleToFit(80, 72);
    }
}

