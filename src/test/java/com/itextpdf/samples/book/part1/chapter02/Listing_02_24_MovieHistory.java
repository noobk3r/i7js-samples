/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

package com.itextpdf.samples.book.part1.chapter02;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfOutline;
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.navigation.PdfDestination;
import com.itextpdf.kernel.pdf.navigation.PdfNamedDestination;
import com.itextpdf.layout.Property;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.samples.GenericTest;
import com.lowagie.database.DatabaseConnection;
import com.lowagie.database.HsqldbConnection;
import com.lowagie.filmfestival.Movie;
import com.lowagie.filmfestival.MovieComparator;
import com.lowagie.filmfestival.PojoFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Set;
import java.util.TreeSet;

import com.lowagie.filmfestival.PojoToElementFactory;
import org.junit.Ignore;
import org.junit.experimental.categories.Category;


@Category(SampleTest.class)
public class Listing_02_24_MovieHistory extends GenericTest {
    public static final String DEST = "./target/test/resources/book/part1/chapter02/Listing_02_24_MovieHistory.pdf";

    public static final String[] EPOCH =
            {"Forties", "Fifties", "Sixties", "Seventies", "Eighties",
                    "Nineties", "Twenty-first Century"};

    public static void main(String args[]) throws IOException, SQLException {
        new Listing_02_24_MovieHistory().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException, SQLException {
        //Initialize writer
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);

        PdfFont[] fonts = new PdfFont[4];
        fonts[0] = PdfFontFactory.createFont(FontConstants.HELVETICA); // 24
        fonts[1] = PdfFontFactory.createFont(FontConstants.HELVETICA); // 18
        fonts[2] = PdfFontFactory.createFont(FontConstants.HELVETICA); // 14
        fonts[3] = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD); // 12

        // Make the connection to the database
        DatabaseConnection connection = new HsqldbConnection("filmfestival");
        Set<Movie> movies =
                new TreeSet<>(new MovieComparator(MovieComparator.BY_YEAR));
        movies.addAll(PojoFactory.getMovies(connection));
        int epoch = -1;
        int currentYear = 0;
        Paragraph title = null;
        pdfDoc.getOutlines(false);
        PdfOutline rootOutLine = new PdfOutline(pdfDoc);
        PdfOutline firstLevel = null;
        PdfOutline secondLevel = null;
        PdfOutline thirdLevel = null;
        for (Movie movie : movies) {

            // add the chapter if we're in a new epoch
            if (epoch < (movie.getYear() - 1940) / 10) {
                epoch = (movie.getYear() - 1940) / 10;
                if (pdfDoc.getNumberOfPages() != 0) {
                    doc.add(new AreaBreak());
                }

                title = new Paragraph(EPOCH[epoch]).setFont(fonts[0]).setFontSize(18).setBold();
                title.setProperty(Property.DESTINATION, EPOCH[epoch]);
                doc.add(title);

                firstLevel = rootOutLine.addOutline(EPOCH[epoch]);
                firstLevel.addDestination(PdfDestination.makeDestination(new PdfString(EPOCH[epoch])));
            }
            // switch to a new year
            if (currentYear < movie.getYear()) {
                currentYear = movie.getYear();
                title = new Paragraph(
                        String.format("The year %d", movie.getYear())).setFont(fonts[1]).setFontSize(16);
                title.setProperty(Property.DESTINATION, String.valueOf(movie.getYear()));
                doc.add(title);
                secondLevel = firstLevel.addOutline(String.valueOf(movie.getYear()));
                secondLevel.addDestination(PdfDestination.makeDestination(new PdfString(String.valueOf(movie.getYear()))));


                doc.add(new Paragraph(
                        String.format("Movies from the year %d:", movie.getYear())));
            }

            title = new Paragraph(movie.getMovieTitle()).setFont(fonts[2]).setMarginLeft(20);
            title.setProperty(Property.DESTINATION, movie.getMovieTitle());
            doc.add(title);
            thirdLevel = secondLevel.addOutline(movie.getMovieTitle());
            thirdLevel.addDestination(PdfDestination.makeDestination(new PdfString(movie.getMovieTitle())));


            doc.add(new Paragraph("Duration: " + movie.getDuration()).setFont(fonts[3]));
            doc.add(new Paragraph("Director(s):").setFont(fonts[3]));
            doc.add(PojoToElementFactory.getDirectorList(movie));
            doc.add(new Paragraph("Countries:").setFont( fonts[3]));
            doc.add(PojoToElementFactory.getCountryList(movie));
        }
        // doc.add(chapter);
        doc.close();
        connection.close();
    }
}
