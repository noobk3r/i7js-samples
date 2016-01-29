/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

package com.itextpdf.samples.book.part1.chapter03;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Property;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.layout.LayoutArea;
import com.itextpdf.layout.layout.LayoutResult;
import com.itextpdf.layout.renderer.DocumentRenderer;
import com.itextpdf.samples.GenericTest;

import com.lowagie.database.DatabaseConnection;
import com.lowagie.database.HsqldbConnection;
import com.lowagie.filmfestival.Country;
import com.lowagie.filmfestival.Director;
import com.lowagie.filmfestival.Movie;
import com.lowagie.filmfestival.PojoFactory;

import java.io.FileOutputStream;
import java.util.List;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class Listing_03_16_MovieColumns1 extends GenericTest {

    public static final String DEST = "./target/test/resources/book/part1/chapter03/Listing_03_16_MovieColumns1.pdf";

    protected PdfFont normal, bold, italic, boldItalic;

    public static void main(String[] args) throws Exception {
        new Listing_03_16_MovieColumns1().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        //Initialize writer
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);

        //Initialize document
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);

        normal = PdfFontFactory.createStandardFont(FontConstants.HELVETICA);
        bold = PdfFontFactory.createStandardFont(FontConstants.HELVETICA_BOLD);
        italic = PdfFontFactory.createStandardFont(FontConstants.HELVETICA_OBLIQUE);
        boldItalic = PdfFontFactory.createStandardFont(FontConstants.HELVETICA_BOLDOBLIQUE);

        doc.setProperty(Property.FONT, normal);

        doc.setRenderer(new DocumentRenderer(doc) {
            int nextAreaNumber = 0;
            int currentPageNumber;

            @Override
            public LayoutArea updateCurrentArea(LayoutResult overflowResult) {
                if (nextAreaNumber % 2 == 0) {
                    currentPageNumber = super.updateCurrentArea(overflowResult).getPageNumber();
                    nextAreaNumber++;
                    return (currentArea = new LayoutArea(currentPageNumber, new Rectangle(36, 36, 260, 770)));
                } else {
                    nextAreaNumber++;
                    return (currentArea = new LayoutArea(currentPageNumber, new Rectangle(299, 36, 260, 770)));
                }
            }
        });

        DatabaseConnection connection = new HsqldbConnection("filmfestival");
        List<Movie> movies = PojoFactory.getMovies(connection);
        for (Movie movie : movies) {
            doc.add(createMovieInformation(movie));
        }

        doc.close();
    }

    public Paragraph createMovieInformation(Movie movie) {
        Paragraph p = new Paragraph().
                setTextAlignment(Property.TextAlignment.JUSTIFIED).
                setPaddingLeft(27).
                setFirstLineIndent(-27).
                setMultipliedLeading(1.2f);

        p.add(new Text("Title: ").setFont(boldItalic));
        p.add(new Text(movie.getMovieTitle()).setFont(normal));
        p.add(" ");
        if (movie.getOriginalTitle() != null) {
            p.add(new Text("Original title: ").setFont(boldItalic));
            p.add(new Text(movie.getOriginalTitle() != null ? movie.getOriginalTitle() : "").setFont(italic));
            p.add(" ");
        }
        p.add(new Text("Country: ").setFont(boldItalic));
        for (Country country : movie.getCountries()) {
            p.add(country.getCountry());
            p.add(" ");
        }
        p.add(new Text("Director: ").setFont(boldItalic));
        for (Director director : movie.getDirectors()) {
            p.add(new Text(director.getName() + ", ").setFont(bold));
            p.add(new Text(director.getGivenName() + " "));
        }
        p.add(new Text("Year: ").setFont(boldItalic));
        p.add(new Text(String.valueOf(movie.getYear())));
        p.add(new Text(" Duration: ").setFont(boldItalic));
        p.add(new Text(String.valueOf(movie.getDuration())));
        p.add(new Text(" minutes"));
        // TODO No LineSeparator
        //p.add(new LineSeparator(0.3f, 100, null, Element.ALIGN_CENTER, -2));
        return p;
    }

}
