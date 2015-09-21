package com.itextpdf.samples.book.chapter03;

import com.itextpdf.basics.font.FontConstants;
import com.itextpdf.basics.font.FontFactory;
import com.itextpdf.basics.font.Type1Font;
import com.itextpdf.basics.image.ImageFactory;
import com.itextpdf.core.font.PdfFont;
import com.itextpdf.core.font.PdfType1Font;
import com.itextpdf.basics.geom.PageSize;
import com.itextpdf.basics.geom.Rectangle;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.pdf.xobject.PdfImageXObject;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Div;
import com.itextpdf.model.element.Image;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.layout.LayoutArea;
import com.itextpdf.model.renderer.DocumentRenderer;
import com.itextpdf.samples.GenericTest;
import com.lowagie.database.DatabaseConnection;
import com.lowagie.database.HsqldbConnection;
import com.lowagie.filmfestival.Movie;
import com.lowagie.filmfestival.PojoFactory;
import com.lowagie.filmfestival.PojoToElementFactory;

import org.junit.experimental.categories.Category;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Category(SampleTest.class)
public class Listing_03_20_ColumnMovies1 extends GenericTest {

    public static final String DEST = "./target/test/resources/Listing_03_20_ColumnMovies1/Listing_03_20_ColumnMovies1.pdf";

    public static final String RESOURCE = "src/test/resources/img/posters/%s.jpg";

    public static final Rectangle[] COLUMNS = {
            new Rectangle(36, 36, 188, 543), new Rectangle(230, 36, 188, 543),
            new Rectangle(424, 36, 188, 543) , new Rectangle(618, 36, 188, 543)
    };

    public static void main(String[] args) throws Exception {
        new Listing_03_20_ColumnMovies1().manipulatePdf(DEST);
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
            public LayoutArea getNextArea() {
                if (nextAreaNumber % 4 == 0) {
                    currentPageNumber = super.getNextArea().getPageNumber();
                }
                return (currentArea = new LayoutArea(currentPageNumber, COLUMNS[nextAreaNumber++ % 4].clone()));
            }
        });

        // Create a database connection
        DatabaseConnection connection = new HsqldbConnection("filmfestival");

        List<Movie> movies = PojoFactory.getMovies(connection);
        for (Movie movie : movies) {
            PdfImageXObject imageXObject = new PdfImageXObject(ImageFactory.getImage(String.format(RESOURCE, movie.getImdb())));
            Image img = new Image(imageXObject).
                scaleToFit(80, 1000);
            addContent(doc, movie, img);
        }

        doc.close();
    }

    public void addContent(Document doc, Movie movie, Image img) throws IOException {
        PdfFont bold = new PdfType1Font(doc.getPdfDocument(), (Type1Font) FontFactory.createFont(FontConstants.HELVETICA_BOLD, ""));
        PdfFont italic = new PdfType1Font(doc.getPdfDocument(), (Type1Font) FontFactory.createFont(FontConstants.HELVETICA_OBLIQUE, ""));
        PdfFont normal = new PdfType1Font(doc.getPdfDocument(), (Type1Font) FontFactory.createFont(FontConstants.HELVETICA, ""));

        Div div = new Div().
                setKeepTogether(true).
                setMarginBottom(15);

        div.add(img);
        div.add(new Paragraph(movie.getTitle()).setFont(bold).setMargins(0, 0, 0, 0));
        if (movie.getOriginalTitle() != null) {
            div.add(new Paragraph(movie.getOriginalTitle()).setFont(italic).setMargins(0, 0, 0, 0));
        }
        div.add(PojoToElementFactory.getDirectorList(movie));
        div.add(new Paragraph().setMargins(0, 0, 0, 0).addAll(PojoToElementFactory.getYearPhrase(movie, bold, normal)));
        div.add(new Paragraph().setMargins(0, 0, 0, 0).addAll(PojoToElementFactory.getDurationPhrase(movie, bold, normal)));
        div.add(PojoToElementFactory.getCountryList(movie));

        doc.add(div);
    }
}
