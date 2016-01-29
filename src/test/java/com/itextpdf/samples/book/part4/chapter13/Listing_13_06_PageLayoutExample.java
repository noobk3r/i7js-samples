/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

package com.itextpdf.samples.book.part4.chapter13;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfVersion;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Property;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.samples.book.part1.chapter02.Listing_02_07_MovieParagraphs1;
import com.itextpdf.test.annotations.type.SampleTest;
import com.lowagie.database.DatabaseConnection;
import com.lowagie.database.HsqldbConnection;
import com.lowagie.filmfestival.Movie;
import com.lowagie.filmfestival.PojoFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class Listing_13_06_PageLayoutExample extends Listing_02_07_MovieParagraphs1 {
    public static final String DEST
            = "./target/test/resources/book/part4/chapter13/Listing_13_06_PageLayoutExample.pdf";
    public static final String RESULT1
            = "results/part4/chapter13/page_layout_single.pdf";
    public static final String RESULT2
            = "results/part4/chapter13/page_layout_column.pdf";
    public static final String RESULT3
            = "results/part4/chapter13/page_layout_columns_l.pdf";
    public static final String RESULT4
            = "results/part4/chapter13/page_layout_columns_r.pdf";
    public static final String RESULT5
            = "results/part4/chapter13/page_layout_pages_l.pdf";
    public static final String RESULT6
            = "results/part4/chapter13/page_layout_pages_r.pdf";

    public static void main(String args[]) throws IOException, SQLException {
        new Listing_13_06_PageLayoutExample().manipulatePdf(DEST);
    }

    /**
     * Creates a PDF with information about the movies
     *
     * @param dest             the name of the PDF file that will be created.
     * @param viewerPreference value for the viewerpreferences
     * @throws IOException
     * @throws SQLException
     */
    public void createPdf(String dest, int viewerPreference) throws IOException, SQLException {
        // Create a database connection
        DatabaseConnection connection = new HsqldbConnection("filmfestival");
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest), PdfVersion.PDF_1_5);
        Document doc = new Document(pdfDoc);
        // TODO No setViewerPreferences
        // writer.setViewerPreferences(viewerpreference);

        List<Movie> movies = PojoFactory.getMovies(connection);
        for (Movie movie : movies) {
            Paragraph p = createMovieInformation(movie);
            p.setTextAlignment(Property.TextAlignment.JUSTIFIED);
            p.setMarginLeft(18);
            p.setFirstLineIndent(-18);
            doc.add(p);
        }

        doc.close();
        connection.close();
    }


    @Override
    public void manipulatePdf(String dest) throws IOException, SQLException {
        // createPdf(RESULT1, PdfWriter.PageLayoutSinglePage);
        // createPdf(RESULT2, PdfWriter.PageLayoutOneColumn);
        // createPdf(RESULT3, PdfWriter.PageLayoutTwoColumnLeft);
        // createPdf(RESULT4, PdfWriter.PageLayoutTwoColumnRight);
        // createPdf(RESULT5, PdfWriter.PageLayoutTwoPageLeft);
        // createPdf(RESULT6, PdfWriter.PageLayoutTwoPageRight);
    }
}
