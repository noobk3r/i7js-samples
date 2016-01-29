/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

package com.itextpdf.samples.book.part1.chapter03;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.core.geom.PageSize;
import com.itextpdf.core.geom.Rectangle;
import com.itextpdf.core.font.PdfFont;
import com.itextpdf.core.font.PdfFontFactory;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.Property;
import com.itextpdf.model.element.Div;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.layout.LayoutArea;
import com.itextpdf.model.layout.LayoutResult;
import com.itextpdf.model.renderer.DocumentRenderer;
import com.itextpdf.samples.GenericTest;
import com.itextpdf.samples.book.part1.chapter02.StarSeparator;
import com.lowagie.database.DatabaseConnection;
import com.lowagie.database.HsqldbConnection;
import com.lowagie.filmfestival.Movie;
import com.lowagie.filmfestival.PojoFactory;
import com.lowagie.filmfestival.PojoToElementFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class Listing_03_20_ColumnMovies2 extends GenericTest {

    public static final String DEST = "./target/test/resources/book/part1/chapter03/Listing_03_20_ColumnMovies2.pdf";

    public static final Rectangle[] COLUMNS = {
            new Rectangle(36, 36, 188, 543), new Rectangle(230, 36, 188, 543),
            new Rectangle(424, 36, 188, 543) , new Rectangle(618, 36, 188, 543)
    };

    public static void main(String[] args) throws Exception {
        new Listing_03_20_ColumnMovies2().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        //Initialize writer
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);

        //Initialize document
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc, PageSize.A4.rotate());

        doc.setRenderer(new DocumentRenderer(doc) {
            int nextAreaNumber = 0;
            int currentPageNumber;

            @Override
            public LayoutArea updateCurrentArea(LayoutResult overflowResult) {
                if (nextAreaNumber % 4 == 0) {
                    currentPageNumber = super.updateCurrentArea(overflowResult).getPageNumber();
                }
                return (currentArea = new LayoutArea(currentPageNumber, COLUMNS[nextAreaNumber++ % 4].clone()));
            }
        });

        // Create a database connection
        DatabaseConnection connection = new HsqldbConnection("filmfestival");

        List<Movie> movies = PojoFactory.getMovies(connection);
        for (Movie movie : movies) {
            addContent(doc, movie);
        }

        doc.close();
    }

    public void addContent(Document doc, Movie movie) throws IOException {
        PdfFont bold = PdfFontFactory.createStandardFont(FontConstants.HELVETICA_BOLD);
        PdfFont italic = PdfFontFactory.createStandardFont(FontConstants.HELVETICA_OBLIQUE);
        PdfFont normal = PdfFontFactory.createStandardFont(FontConstants.HELVETICA);

        Div div = new Div().setKeepTogether(true);
        Paragraph p = new Paragraph(movie.getTitle()).setFont(bold).
            setTextAlignment(Property.TextAlignment.CENTER).
            setMargins(0, 0, 0, 0);
        div.add(p);
        if (movie.getOriginalTitle() != null) {
            p = new Paragraph(movie.getOriginalTitle()).setFont(italic).
                    setTextAlignment(Property.TextAlignment.RIGHT).
                    setMargins(0, 0, 0, 0);
            div.add(p);
        }
        p = new Paragraph().
                setMargins(0, 0, 0, 0).
                addAll(PojoToElementFactory.getYearPhrase(movie, bold, normal)).
                add(" ").
                addAll(PojoToElementFactory.getDurationPhrase(movie, bold, normal)).
                setTextAlignment(Property.TextAlignment.JUSTIFIED_ALL);
        div.add(p);
        div.add(new StarSeparator());

        doc.add(div);
    }
}
