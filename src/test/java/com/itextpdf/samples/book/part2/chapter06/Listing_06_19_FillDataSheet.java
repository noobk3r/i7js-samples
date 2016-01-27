/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

package com.itextpdf.samples.book.part2.chapter06;

import com.itextpdf.basics.io.ByteArrayOutputStream;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.samples.GenericTest;

import com.lowagie.database.DatabaseConnection;
import com.lowagie.database.HsqldbConnection;
import com.lowagie.filmfestival.Director;
import com.lowagie.filmfestival.Movie;
import com.lowagie.filmfestival.PojoFactory;
import com.lowagie.filmfestival.Screening;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore("Document fonts")
@Category(SampleTest.class)
public class Listing_06_19_FillDataSheet extends GenericTest{
    public static final String DATASHEET
            = "./src/test/resources/pdfs/datasheet.pdf";
    public static final String DEST
            = "./target/test/resources/book/part2/chapter06/Listing_06_19_FillDataSheet.pdf";

    public static void main(String[] args) throws SQLException, IOException {
        new Listing_06_19_FillDataSheet().manipulatePdf(DEST);
    }

    @Override
    public void manipulatePdf(String dest) throws SQLException, IOException {
        // Create a database connection
        DatabaseConnection connection = new HsqldbConnection("filmfestival");
        // Get the movies
        PdfDocument pdfDocResult = new PdfDocument(new PdfWriter(new FileOutputStream(dest)));
        List<Movie> movies = PojoFactory.getMovies(connection);
        PdfReader reader;
        PdfDocument pdfDoc;
        ByteArrayOutputStream baos;
        // Fill out the data sheet form with data
        for (Movie movie : movies) {
            // @TODO replace this check with < 2007 when copying form fields is implemented
            if (movie.getYear() != 2007)
                continue;
            reader = new PdfReader(DATASHEET);
            baos = new ByteArrayOutputStream();
            pdfDoc = new PdfDocument(reader,
                    new PdfWriter(baos));
            PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);
            form.setGenerateAppearance(true);
            fill(form, movie);
            if (movie.getYear() == 2007)
                form.flatFields();
            pdfDoc.close();

            pdfDoc = new PdfDocument(new PdfReader(new ByteArrayInputStream(baos.toByteArray())));
            pdfDoc.copyPages(1, pdfDoc.getNumberOfPages(), pdfDocResult);
        }
        // Close the database connection
        connection.close();
        pdfDocResult.close();
    }

    /**
     * Fill out the fields using info from a Movie object.
     * @param form The form object
     * @param movie A movie POJO
     * @throws IOException
     */
    public static void fill(PdfAcroForm form, Movie movie)
            throws IOException  {
        form.getField("title").setValue(movie.getMovieTitle());
        form.getField("director").setValue(getDirectors(movie));
        form.getField("year").setValue(String.valueOf(movie.getYear()));
        form.getField("duration").setValue(String.valueOf(movie.getDuration()));
        form.getField("category").setValue(movie.getEntry().getCategory().getKeyword());
        for (Screening screening : movie.getEntry().getScreenings()) {
            form.getField(screening.getLocation().replace('.', '_')).setValue("Yes");
        }
    }

    /**
     * Gets the directors from a Movie object,
     * and concatenates them in a String.
     * @param movie a Movie object
     * @return a String containing director names
     */
    public static String getDirectors(Movie movie) {
        List<Director> directors = movie.getDirectors();
        StringBuffer buf = new StringBuffer();
        for (Director director : directors) {
            buf.append(director.getGivenName());
            buf.append(' ');
            buf.append(director.getName());
            buf.append(',');
            buf.append(' ');
        }
        int i = buf.length();
        if (i > 0)
            buf.delete(i - 2, i);
        return buf.toString();
    }
}
