/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

package com.itextpdf.samples.book.part4.chapter13;

import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.kernel.xmp.XMPException;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.samples.GenericTest;
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
public class Listing_13_13_Bookmarks2NamedDestinations extends GenericTest {
    public static final String DEST = "./target/test/resources/book/part4/chapter13/Listing_13_13_Bookmarks2NamedDestinations.pdf";
    public static final String LAUNCH_ACTIONS = "./src/test/resources/book/part2/chapter07/cmp_Listing_07_10_LaunchAction.pdf";
    public static final String RESULT1 = "results/part4/chapter13/bookmarks.pdf";
    public static final String RESULT2 = "results/part4/chapter13/named_destinations.pdf";
    public static final String RESULT3 = "results/part4/chapter13/named_destinations.xml";
    public static final String[] EPOCH =
            {"Forties", "Fifties", "Sixties", "Seventies", "Eighties",
                    "Nineties", "Twenty-first Century"};

    /** The fonts for the title. */
//    public static final Font[] FONT = new Font[4];
//    static {
//        FONT[0] = new Font(FontFamily.HELVETICA, 24);
//        FONT[1] = new Font(FontFamily.HELVETICA, 18);
//        FONT[2] = new Font(FontFamily.HELVETICA, 14);
//        FONT[3] = new Font(FontFamily.HELVETICA, 12, Font.BOLD);
//    }

    public static void main(String args[]) throws IOException, SQLException, XMPException {
        new Listing_13_13_Bookmarks2NamedDestinations().manipulatePdf(DEST);
    }

    public void createPdf(String dest) throws IOException, SQLException {
        DatabaseConnection connection = new HsqldbConnection("filmfestival");
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
        Set<Movie> movies =
                new TreeSet<Movie>(new MovieComparator(MovieComparator.BY_YEAR));
        movies.addAll(PojoFactory.getMovies(connection));
        int epoch = -1;
        int currentYear = 0;
        Paragraph title = null;
//        TODO No Chapter and Section
//        Chapter chapter = null;
//        Section section = null;

//        for (Movie movie : movies) {
//            // add the chapter if we're in a new epoch
//            if (epoch < (movie.getYear() - 1940) / 10) {
//                epoch = (movie.getYear() - 1940) / 10;
//                if (chapter != null) {
//                    document.add(chapter);
//                }
//                title = new Paragraph(EPOCH[epoch], FONT[0]);
//                chapter = new Chapter(title, epoch + 1);
//                chapter.setBookmarkTitle(EPOCH[epoch]);
//            }
//            // switch to a new year
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
//            section.add(title);
//            section.add(new Paragraph("Duration: " + movie.getDuration(), FONT[3]));
//            section.add(new Paragraph("Director(s):", FONT[3]));
//            section.add(PojoToElementFactory.getDirectorList(movie));
//            section.add(new Paragraph("Countries:", FONT[3]));
//            section.add(PojoToElementFactory.getCountryList(movie));
//        }
//        document.add(chapter);
        pdfDoc.close();
        connection.close();
    }

    public void changePdf(String src, String dest) throws IOException {
        PdfReader reader = new PdfReader(src);
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(src), new PdfWriter(dest));
        PdfDictionary root = pdfDoc.getCatalog().getPdfObject();
        PdfDictionary outlines = root.getAsDictionary(PdfName.Outlines);
        if (outlines == null)
            return;
        PdfArray dests = new PdfArray();
        addKids(dests, outlines.getAsDictionary(PdfName.First));
        if (dests.size() == 0)
            return;
        // PdfIndirectReference ref = pdfDoc.getCatalog().add(dests);
        PdfDictionary nametree = new PdfDictionary();
        //nametree.put(PdfName.Names, ref);
        PdfDictionary names = new PdfDictionary();
        names.put(PdfName.Dests, nametree);
        root.put(PdfName.Names, names);
        pdfDoc.close();
    }

    public static void addKids(PdfArray dests, PdfDictionary outline) {
        while (outline != null) {
            dests.add(outline.getAsString(PdfName.Title));
            dests.add(outline.getAsArray(PdfName.Dest));
            addKids(dests, outline.getAsDictionary(PdfName.First));
            outline = outline.getAsDictionary(PdfName.Next);
        }
    }

    @Override
    protected void manipulatePdf(String dest) throws IOException, SQLException, XMPException {
        createPdf(RESULT1);
        changePdf(RESULT1, RESULT2);
        // TODO Revise LinkActions first
        // new LinkActions().createXml(RESULT2, RESULT3);
    }
}
