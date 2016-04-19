/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

package com.itextpdf.samples.book.part4.chapter13;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.kernel.pdf.navigation.PdfDestination;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Property;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Div;
import com.itextpdf.samples.book.part2.chapter07.Listing_07_02_LinkActions;
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

import com.lowagie.filmfestival.PojoToElementFactory;
import org.junit.Ignore;
import org.junit.experimental.categories.Category;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

@Ignore
@Category(SampleTest.class)
public class Listing_13_13_Bookmarks2NamedDestinations extends GenericTest {
    public static final String DEST = "./target/test/resources/book/part4/chapter13/Listing_13_13_Bookmarks2NamedDestinations.pdf";
    public static final String LAUNCH_ACTIONS = "./src/test/resources/book/part2/chapter07/cmp_Listing_07_10_LaunchAction.pdf";
    public static final String RESULT1 = "./target/test/resources/book/part4/chapter13/Listing_13_13_Bookmarks2NamedDestinations.pdf";
    public static final String RESULT2 = "./target/test/resources/book/part4/chapter13/Listing_13_13_Bookmarks2NamedDestinations_named_destinations.pdf";
    public static final String RESULT3 = "./target/test/resources/book/part4/chapter13/Listing_13_13_Bookmarks2NamedDestinations_named_destinations.xml";
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

    public static void main(String args[]) throws IOException, SQLException, XMPException, TransformerException, ParserConfigurationException {
        new Listing_13_13_Bookmarks2NamedDestinations().manipulatePdf(DEST);
    }

    public void createPdf(String dest) throws IOException, SQLException {
        DatabaseConnection connection = new HsqldbConnection("filmfestival");
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
        Document doc = new Document(pdfDoc);

        PdfFont font = PdfFontFactory.createFont(FontConstants.HELVETICA);
        PdfFont bold = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD);

        Set<Movie> movies = new TreeSet<>(new MovieComparator(MovieComparator.BY_YEAR));
        movies.addAll(PojoFactory.getMovies(connection));
        int epoch = -1;
        int currentYear = 0;
        Div firstLevelParagraph = null;
        Div secondLevelParagraph = null;
        Div thirdLevelParagraph = null;

        String firstLevelTitle = null;
        String secondLevelTitle = null;
        String thirdLevelTitle = null;

        PdfOutline rootOutLine = pdfDoc.getOutlines(false);
        PdfOutline firstLevel = null;
        PdfOutline secondLevel = null;
        int firstLevelOrder = 0;
        int secondLevelOrder = 0;
        int thirdLevelOrder = 0;

        for (Movie movie : movies) {
            // add the chapter if we're in a new epoch
            if (epoch < (movie.getYear() - 1940) / 10) {
                epoch = (movie.getYear() - 1940) / 10;
                if (null != firstLevelParagraph) {
                    doc.add(firstLevelParagraph);
                    doc.add(new AreaBreak());
                }
                // chapter
                firstLevelOrder++;
                secondLevelOrder = 0;
                firstLevelTitle = firstLevelOrder + " " + EPOCH[epoch];
                firstLevelParagraph = new Div().add(new Paragraph(firstLevelTitle).setFont(font).setFontSize(24).setBold());
                firstLevelParagraph.setProperty(Property.DESTINATION, firstLevelTitle);
                firstLevel = rootOutLine.addOutline(firstLevelTitle);
                firstLevel.addDestination(PdfDestination.makeDestination(new PdfString(firstLevelTitle)));
            }
            // switch to a new year
            if (currentYear < movie.getYear()) {
                currentYear = movie.getYear();
                // section
                secondLevelOrder++;
                thirdLevelOrder = 0;
                secondLevelTitle = firstLevelOrder + ". " + secondLevelOrder + " " +
                        String.format("The year %d", movie.getYear());
                secondLevelParagraph = new Div().add(new Paragraph(secondLevelTitle).setFont(font).setFontSize(18));
                secondLevelParagraph.setProperty(Property.DESTINATION, secondLevelTitle);
                secondLevel = firstLevel.addOutline(secondLevelTitle);
                secondLevel.addDestination(PdfDestination.makeDestination(new PdfString(secondLevelTitle)));

                secondLevelParagraph.add(new Paragraph(
                        String.format("Movies from the year %d:", movie.getYear())).setMarginLeft(10));

                firstLevelParagraph.add(secondLevelParagraph);

            }
            // subsection
            thirdLevelOrder++;
            thirdLevelTitle = thirdLevelOrder + " " + movie.getMovieTitle();
            thirdLevelParagraph = new Div().add(new Paragraph(thirdLevelTitle).setFont(font).setFontSize(14).setMarginLeft(20));

            thirdLevelParagraph.add(new Paragraph("Duration: " + movie.getDuration()).setFont(bold).setMarginLeft(20));
            thirdLevelParagraph.add(new Paragraph("Director(s):").setFont(bold).setMarginLeft(20));
            thirdLevelParagraph.add(PojoToElementFactory.getDirectorList(movie).setMarginLeft(20));
            thirdLevelParagraph.add(new Paragraph("Countries:").setFont(bold).setMarginLeft(20));
            thirdLevelParagraph.add(PojoToElementFactory.getCountryList(movie).setMarginLeft(20));

            secondLevelParagraph.add(thirdLevelParagraph);
        }
        doc.add(firstLevelParagraph);

        doc.close();



        connection.close();
    }

    public void changePdf(String src, String dest) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(src), new PdfWriter(dest));
        PdfDictionary root = pdfDoc.getCatalog().getPdfObject();
        PdfDictionary outlines = root.getAsDictionary(PdfName.Outlines);
        if (outlines == null) {
            return;
        }
        PdfArray dests = new PdfArray();
        addKids(dests, outlines.getAsDictionary(PdfName.First));
        if (dests.size() == 0) {
            return;
        }
        // TODO Think
        PdfIndirectReference ref = dests.makeIndirect(pdfDoc).getIndirectReference();
        PdfDictionary nametree = new PdfDictionary();
        nametree.put(PdfName.Names, ref);
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
    protected void manipulatePdf(String dest) throws IOException, SQLException, XMPException, TransformerException, ParserConfigurationException {
        createPdf(RESULT1);
        changePdf(RESULT1, RESULT2);
        new Listing_07_02_LinkActions().createXml(RESULT2, RESULT3);
    }
}
