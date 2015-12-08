package com.itextpdf.samples.book.part2.chapter06;

import com.itextpdf.basics.font.FontConstants;
import com.itextpdf.basics.geom.PageSize;
import com.itextpdf.basics.geom.Rectangle;
import com.itextpdf.basics.io.ByteArrayOutputStream;
import com.itextpdf.canvas.PdfCanvas;
import com.itextpdf.core.font.PdfFont;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Canvas;
import com.itextpdf.model.Document;
import com.itextpdf.model.Property;
import com.itextpdf.model.border.Border;
import com.itextpdf.model.border.SolidBorder;
import com.itextpdf.model.element.AreaBreak;
import com.itextpdf.model.element.Cell;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Table;
import com.itextpdf.samples.GenericTest;
import com.lowagie.database.DatabaseConnection;
import com.lowagie.database.HsqldbConnection;
import com.lowagie.filmfestival.Movie;
import com.lowagie.filmfestival.MovieComparator;
import com.lowagie.filmfestival.PojoFactory;
import com.lowagie.filmfestival.PojoToElementFactory;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Set;
import java.util.TreeSet;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class Listing_06_14_TwoPasses extends GenericTest {
    public static final String DEST = "./target/test/resources/book/part2/chapter06/Listing_06_14_TwoPasses.pdf";

    protected PdfFont bold;
    protected PdfFont italic;
    protected PdfFont normal;

    public static void main(String args[]) throws IOException, SQLException {
        new Listing_06_14_TwoPasses().manipulatePdf(DEST);
    }

    /**
     * Create a header table with page X of Y
     *
     * @param x the page number
     * @param y the total number of pages
     * @return a table that can be used as header
     */
    public static Table getHeaderTable(int x, int y) {
        Table table = new Table(2);
        table.setWidth(527);
        // TODO No setLockedWidth(boolean)
        // table.setLockedWidth(true);
        // TODO Add facility to set default cell properties
        table.setBorder(Border.NO_BORDER);
        table.addCell(new Cell()
                .add(new Paragraph("FOOBAR FILMFESTIVAL"))
                .setHeight(20)
                .setBorder(Border.NO_BORDER)
                .setBorderBottom(new SolidBorder(1)));
        table.addCell(new Cell()
                .setTextAlignment(Property.TextAlignment.RIGHT)
                .add(new Paragraph(String.format("Page %d of %d", x, y)))
                .setHeight(20)
                .setBorder(Border.NO_BORDER)
                .setBorderBottom(new SolidBorder(1)));
        return table;
    }

    public void manipulatePdf(String dest) throws IOException, SQLException {
        // FIRST PASS, CREATE THE PDF WITHOUT HEADER

        // Create a database connection
        DatabaseConnection connection = new HsqldbConnection("filmfestival");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc, new PageSize(PageSize.A4));
        doc.setMargins(54, 36, 36, 36);

        bold = PdfFont.createStandardFont(pdfDoc, FontConstants.HELVETICA_BOLD);
        italic = PdfFont.createStandardFont(pdfDoc, FontConstants.HELVETICA_OBLIQUE);
        normal = PdfFont.createStandardFont(pdfDoc, FontConstants.HELVETICA);

        Statement stm = connection.createStatement();
        ResultSet rs = stm.executeQuery(
                "SELECT country, id FROM film_country ORDER BY country");
        while (rs.next()) {
            doc.add(new Paragraph(rs.getString("country")).setFont(bold));
            doc.add(new Paragraph("\n"));
            Set<Movie> movies =
                    new TreeSet<Movie>(new MovieComparator(MovieComparator.BY_YEAR));
            movies.addAll(PojoFactory.getMovies(connection, rs.getString("id")));
            for (Movie movie : movies) {
                doc.add(new Paragraph(movie.getMovieTitle()).setFont(bold));
                if (movie.getOriginalTitle() != null)
                    doc.add(
                            new Paragraph(movie.getOriginalTitle()).setFont(italic));
                doc.add(new Paragraph(
                        String.format("Year: %d; run length: %d minutes",
                                movie.getYear(), movie.getDuration())).setFont(normal));
                doc.add(PojoToElementFactory.getDirectorList(movie));
            }
            if (!rs.isLast()) {
                doc.add(new AreaBreak());
            }
        }

        doc.close();
        connection.close();

        // SECOND PASS, ADD THE HEADER

        // Create a reader
        PdfReader reader = new PdfReader(new ByteArrayInputStream(baos.toByteArray()));
        // Create a pdf document
        pdfDoc = new PdfDocument(reader, new PdfWriter(new FileOutputStream(DEST)));

        // Loop over the pages and add a header to each page
        int n = pdfDoc.getNumOfPages();
        for (int i = 1; i <= n; i++) {
            new Canvas(new PdfCanvas(pdfDoc.getPage(i)), pdfDoc,
                    new Rectangle(34, 803, 100, 30)).add(getHeaderTable(i, n));
        }

        // Close the pdf document
        pdfDoc.close();
    }
}
