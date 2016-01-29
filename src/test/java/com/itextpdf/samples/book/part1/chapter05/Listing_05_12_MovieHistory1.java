/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

package com.itextpdf.samples.book.part1.chapter05;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.element.Paragraph;
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
public class Listing_05_12_MovieHistory1 extends GenericTest {
    public static final String DEST =
            "./target/test/resources/book/part1/chapter05/Listing_05_12_MovieHistory1.pdf";

    public static void main(String args[]) throws IOException, SQLException {
        new Listing_05_12_MovieHistory1().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException, SQLException {
        // Create a database connection
        DatabaseConnection connection = new HsqldbConnection("filmfestival");

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
        // IMPORTANT: set linear page mode!
        // TODO No setLinearPageMode
        // writer.setLinearPageMode();
        // TODO No onChapter and onSection events implementations
        // ChapterSectionTOC event = new ChapterSectionTOC();
        // writer.setPageEvent(event);
        // add the chapters
        Set<Movie> movies =
                new TreeSet<Movie>(new MovieComparator(MovieComparator.BY_YEAR));
        movies.addAll(PojoFactory.getMovies(connection));
        int epoch = -1;
        int currentYear = 0;
        Paragraph title = null;
        // TODO NO Chapter and Section
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
//                title = new Paragraph(
//                        String.format("The year %d", movie.getYear()), FONT[1]);
//                section = chapter.addSection(title);
//                section.setBookmarkTitle(String.valueOf(movie.getYear()));
//                section.setIndentation(30);
//                section.setBookmarkOpen(false);
//                section.setNumberStyle(Section.NUMBERSTYLE_DOTTED_WITHOUT_FINAL_DOT);
//                section.add(new Paragraph(
//                        String.format("Movies from the year %d:", movie.getYear())));
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
        // add the TOC starting on the next page
//        int toc = pdfDoc.getNumberOfPages();
//        for (Paragraph p : event.titles) {
//            document.add(p);
//        }
//        // always go to a new page before reordering pages.
//        document.newPage();
//        // get the total number of pages that needs to be reordered
//        int total = writer.reorderPages(null);
//        // change the order
//        int[] order = new int[total];
//        for (int i = 0; i < total; i++) {
//            order[i] = i + toc;
//            if (order[i] > total)
//                order[i] -= total;
//        }
//        // apply the new order
//        writer.reorderPages(order);
        pdfDoc.close();
        // Close the database connection
        connection.close();
    }
}


