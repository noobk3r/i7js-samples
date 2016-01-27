/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

package com.itextpdf.samples.book.part1.chapter05;

import com.itextpdf.basics.geom.Rectangle;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.samples.GenericTest;
import com.itextpdf.test.annotations.type.SampleTest;
import com.lowagie.database.DatabaseConnection;
import com.lowagie.database.HsqldbConnection;
import com.lowagie.filmfestival.Movie;
import com.lowagie.filmfestival.MovieComparator;
import com.lowagie.filmfestival.PojoFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class Listing_05_19_MovieHistory2 extends GenericTest {
    public static final String DEST =
            "./target/test/resources/book/part1/chapter05/Listing_05_19_MovieHistory2.pdf";

    public static void main(String args[]) throws IOException, SQLException {
        new Listing_05_19_MovieHistory2().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException, SQLException {
        // Create a database connection
        DatabaseConnection connection = new HsqldbConnection("filmfestival");
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
        Document doc = new Document(pdfDoc);
        doc.setMargins(54, 36, 54, 36);

        // TODO No onChapter and onSection events implementations
        // HeaderFooter event = new HeaderFooter();
        // writer.setPageEvent(event);
        // TODO No method toset Art Box by default for all pages, not a certain one
        pdfDoc.addNewPage().setArtBox(new Rectangle(36, 54, 523, 734));
        Set<Movie> movies =
                new TreeSet<Movie>(new MovieComparator(MovieComparator.BY_YEAR));
        movies.addAll(PojoFactory.getMovies(connection));
        int epoch = -1;
        int currentYear = 0;
        Paragraph title = null;
        // TODO No Chapter and Section
        // Chapter chapter = null;
        // Section section = null;
        // Section subsection = null;
//        for (Movie movie : movies) {
//            if (epoch < (movie.getYear() - 1940) / 10) {
//                epoch = (movie.getYear() - 1940) / 10;
//                if (chapter != null) {
//                    document.add(chapter);
//                }
//                title = new Paragraph(EPOCH[epoch], FONT[0]);
//                chapter = new Chapter(title, epoch + 1);
//            }
//            if (currentYear < movie.getYear()) {
//                currentYear = movie.getYear();
//                title = new Paragraph(String.format("The year %d", movie.getYear()), FONT[1]);
//                section = chapter.addSection(title);
//                section.setBookmarkTitle(String.valueOf(movie.getYear()));
//                section.setIndentation(30);
//                section.setBookmarkOpen(false);
//                section.setNumberStyle(Section.NUMBERSTYLE_DOTTED_WITHOUT_FINAL_DOT);
//                section.add(new Paragraph(String.format("Movies from the year %d:", movie.getYear())));
//            }
//            title = new Paragraph(movie.getMovieTitle(), FONT[2]);
//            subsection = section.addSection(title);
//            subsection.setIndentationLeft(20);
//            subsection.setNumberDepth(1);
//            subsection.add(new Paragraph("Duration: " + movie.getDuration(), FONT[3]));
//            subsection.add(new Paragraph("Director(s):", FONT[3]));
//            subsection.add(PojoToElementFactory.getDirectorList(movie));
//            subsection.add(new Paragraph("Countries:", FONT[3]));
//            subsection.add(PojoToElementFactory.getCountryList(movie));
//        }
//        document.add(chapter);
        pdfDoc.close();
        // close the database connection
        connection.close();
    }
}


