package com.itextpdf.samples.book.part1.chapter04;

import com.itextpdf.basics.font.FontConstants;
import com.itextpdf.basics.image.ImageFactory;
import com.itextpdf.core.font.PdfFont;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.Property;
import com.itextpdf.model.border.Border;
import com.itextpdf.model.element.Cell;
import com.itextpdf.model.element.Image;
import com.itextpdf.model.element.List;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Table;
import com.itextpdf.samples.GenericTest;
import com.lowagie.database.DatabaseConnection;
import com.lowagie.database.HsqldbConnection;
import com.lowagie.filmfestival.Movie;
import com.lowagie.filmfestival.PojoFactory;
import com.lowagie.filmfestival.PojoToElementFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class Listing_04_14_MovieCompositeMode extends GenericTest {
    public static final String DEST =
            "./target/test/resources/book/part1/chapter04/Listing_04_14_MovieCompositeRole.pdf";
    public static final String RESOURCE = "./src/test/resources/book/part1/chapter02/posters/%s.jpg";

    protected PdfFont normal;
    protected PdfFont bold;
    protected PdfFont italic;

    public static void main(String args[]) throws IOException, SQLException {
        new Listing_04_14_MovieCompositeMode().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException, SQLException {
        // Create a database connection
        DatabaseConnection connection = new HsqldbConnection("filmfestival");

        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);

        normal = PdfFont.createStandardFont(FontConstants.HELVETICA); // 12
        bold = PdfFont.createStandardFont(FontConstants.HELVETICA_BOLD); // 12
        italic = PdfFont.createStandardFont(FontConstants.HELVETICA_OBLIQUE); // 12

        doc.add(new Paragraph("Movies:"));
        java.util.List<Movie> movies = PojoFactory.getMovies(connection);
        List list;
        Cell cell;
        for (Movie movie : movies) {
            // a table with two columns
            Table table = new Table(new float[]{1, 7});
            table.setWidthPercent(100);
            table.setMarginTop(5);
            // a cell with an image
            cell = new Cell().add(new Image(ImageFactory.getImage(String.format(RESOURCE, movie.getImdb()))).setAutoScale(true));
            cell.setBorder(Border.NO_BORDER);
            table.addCell(cell);
            cell = new Cell();
            // a cell with paragraphs and lists
            Paragraph p = new Paragraph(movie.getTitle()).setFont(bold);
            p.setTextAlignment(Property.TextAlignment.CENTER);
            p.setMarginTop(5);
            p.setMarginBottom(5);
            cell.add(p);
            cell.setBorder(Border.NO_BORDER);
            if (movie.getOriginalTitle() != null) {
                p = new Paragraph(movie.getOriginalTitle()).setFont(italic);
                p.setTextAlignment(Property.TextAlignment.RIGHT);
                cell.add(p);
            }
            list = PojoToElementFactory.getDirectorList(movie);
            list.setMarginLeft(30);
            cell.add(list);
            p = new Paragraph(
                    String.format("Year: %d", movie.getYear())).setFont(normal);
            p.setMarginLeft(15);
            p.setFixedLeading(24);
            cell.add(p);
            p = new Paragraph(
                    String.format("Run length: %d", movie.getDuration())).setFont(normal);
            p.setFixedLeading(14);
            p.setMarginLeft(30);
            cell.add(p);
            list = PojoToElementFactory.getCountryList(movie);
            list.setMarginLeft(40);
            cell.add(list);
            table.addCell(cell);
            // every movie corresponds with one table
            doc.add(table);
            // but the result looks like one big table
        }
        doc.close();
    }
}

