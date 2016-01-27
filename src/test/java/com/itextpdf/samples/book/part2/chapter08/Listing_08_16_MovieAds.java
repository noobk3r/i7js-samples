/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

package com.itextpdf.samples.book.part2.chapter08;

import com.itextpdf.basics.PdfException;
import com.itextpdf.basics.font.FontConstants;
import com.itextpdf.basics.geom.PageSize;
import com.itextpdf.basics.geom.Rectangle;
import com.itextpdf.basics.io.ByteArrayOutputStream;
import com.itextpdf.core.font.PdfFontFactory;
import com.itextpdf.core.pdf.canvas.PdfCanvas;
import com.itextpdf.core.color.DeviceGray;
import com.itextpdf.core.color.DeviceRgb;
import com.itextpdf.core.color.WebColors;
import com.itextpdf.core.font.PdfFont;
import com.itextpdf.core.pdf.PdfArray;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfPage;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.pdf.xobject.PdfFormXObject;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfButtonFormField;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.forms.fields.PdfTextFormField;
import com.itextpdf.model.Canvas;
import com.itextpdf.model.Document;
import com.itextpdf.model.Property;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Text;
import com.itextpdf.samples.GenericTest;
import com.lowagie.database.DatabaseConnection;
import com.lowagie.database.HsqldbConnection;
import com.lowagie.filmfestival.Director;
import com.lowagie.filmfestival.Movie;
import com.lowagie.filmfestival.PojoFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class Listing_08_16_MovieAds extends GenericTest {
    public static final String DEST
            = "./target/test/resources/book/part2/chapter08/Listing_08_16_MovieAds.pdf";
    /**
     * The resulting PDF: a small template for an individual ad.
     */
    public static final String TEMPLATE
            = "./target/test/resources/book/part2/chapter08/Listing_08_16_MovieAds_template.pdf";
    /**
     * The source PDF file: the template for the complete ad.
     */
    public static final String RESOURCE
            = "./src/test/resources/book/part2/chapter08/movie_overview.pdf";
    /**
     * Path to the movie posters
     */
    public static final String IMAGE
            = "./src/test/resources/book/part2/chapter08/posters/%s.jpg";
    /**
     * Field name for the poster
     */
    public static final String POSTER = "poster";
    /**
     * Field name for the text
     */
    public static final String TEXT = "text";
    /**
     * Field name for the year
     */
    public static final String YEAR = "year";

    public static void main(String[] args) throws Exception {
        new Listing_08_16_MovieAds().manipulatePdf(DEST);
    }

    public static float millimetersToPoints(float value) {
        return (value / 25.4f) * 72f;
    }

    /**
     * Create a small template that will be used for an individual ad.
     *
     * @param filename the filename of the add
     * @throws IOException
     */
    public void createTemplate(String filename) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(filename));
        Document doc = new Document(pdfDoc, new PageSize(millimetersToPoints(35), millimetersToPoints(50)));
        pdfDoc.addNewPage();
        // TODO No setViewerPreferences
        // writer.setViewerPreferences(PdfWriter.PageLayoutSinglePage);

        PdfButtonFormField poster = PdfFormField.createPushButton(pdfDoc, new Rectangle(millimetersToPoints(0),
                millimetersToPoints(25), millimetersToPoints(35) - millimetersToPoints(0),
                millimetersToPoints(50) - millimetersToPoints(25)), POSTER, "");
        poster.setBackgroundColor(new DeviceGray(0.4f));
        PdfAcroForm.getAcroForm(pdfDoc, true).addField(poster);
        // pdfDoc.getFirstPage().addAnnotation(poster.getWidgets().get(0));
        PdfTextFormField movie = PdfFormField.createText(pdfDoc, new Rectangle(millimetersToPoints(0),
                millimetersToPoints(7), millimetersToPoints(35) - millimetersToPoints(0),
                millimetersToPoints(25) - millimetersToPoints(7)), TEXT, "");
        movie.setMultiline(true);
        PdfAcroForm.getAcroForm(pdfDoc, true).addField(movie);
        PdfTextFormField screening = PdfFormField.createText(pdfDoc, new Rectangle(millimetersToPoints(0),
                millimetersToPoints(0), millimetersToPoints(35) - millimetersToPoints(0),
                millimetersToPoints(7) - millimetersToPoints(0)), YEAR, "");
        // TODO No setAlignment on PdfFormField
        // screening.setAlignment(Element.ALIGN_CENTER);
        // TODO DEVSIX-233
        // screening.setBackgroundColor(new DeviceGray(0.4f));
        // TODO No setTextColor on PdfFormField
        // screening.setTextColor(GrayColor.GRAYWHITE);
        PdfAcroForm.getAcroForm(pdfDoc, true).addField(screening);

        pdfDoc.close();
    }

    /**
     * Fill out the small template with information about the movie.
     *
     * @param filename the template for an individual ad
     * @param movie    the movie that needs to be in the ad
     * @return a byte[] containing an individual ad
     * @throws IOException
     */
    public byte[] fillTemplate(String filename, Movie movie) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfReader reader = new PdfReader(filename);
        PdfDocument pdfDoc = new PdfDocument(reader, new PdfWriter(baos));
        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);
        // change the background color of the poster and add a new icon
        DeviceRgb color = WebColors.getRGBColor("#" + movie.getEntry().getCategory().getColor());
        PdfButtonFormField bt = (PdfButtonFormField) form.getField(POSTER);
        // TODO No setImage & setLayout & setProportionalIcon in PdfFormField
        // bt.setLayout(PushbuttonField.LAYOUT_ICON_ONLY);
        // bt.setProportionalIcon(true);
        // bt.setImage(Image.getInstance(String.format(IMAGE, movie.getImdb())));
        bt.setBackgroundColor(color);
        form.removeField(POSTER);
        form.addField(bt);
        // write the text using the appropriate font size
        PdfCanvas canvas = new PdfCanvas(pdfDoc.getFirstPage());

        PdfArray f = form.getField(TEXT).getWidgets().get(0).getRectangle();
        Rectangle rect = new Rectangle(f.getAsFloat(0), f.getAsFloat(1), f.getAsFloat(2) - f.getAsFloat(0),
                f.getAsFloat(3) - f.getAsFloat(1));
        float size = 30;
        while (!addParagraph(pdfDoc, createMovieParagraph(pdfDoc, movie, size), canvas, rect) && size > 6) {
            size -= 0.2;
        }
        addParagraph(pdfDoc, createMovieParagraph(pdfDoc, movie, size), canvas, rect);
        // fill out the year and change the background color
        form.getField(YEAR).setBackgroundColor(color);
        form.getField(YEAR).setValue(String.valueOf(movie.getYear()));
        // flatten the form and close the stamper
        pdfDoc.close();
        return baos.toByteArray();
    }

    /**
     * Add a paragraph at an absolute position.
     *
     * @param p      the paragraph that needs to be added
     * @param canvas the canvas on which the paragraph needs to be drawn
     * @param rect   the field position
     * @return true if the paragraph didn't fit the rectangle
     */
    public boolean addParagraph(PdfDocument pdfDoc, Paragraph p, PdfCanvas canvas, Rectangle rect) {
        try {
            new Canvas(canvas, pdfDoc, rect).add(p);
        } catch (PdfException e) {
            if (PdfException.ElementCannotFitAnyArea.equals(e.getMessage())) {
                return false;
            } else {
                throw e;
            }
        }
        return true;
    }

    /**
     * Creates a paragraph containing info about a movie
     *
     * @param movie    the Movie pojo
     * @param fontsize the font size
     * @return a Paragraph object
     */
    public Paragraph createMovieParagraph(PdfDocument pdfDoc, Movie movie, float fontsize) {
        PdfFont normal = null;
        PdfFont bold = null;
        PdfFont italic = null;
        try {
            normal = PdfFontFactory.createStandardFont(FontConstants.HELVETICA);
            bold = PdfFontFactory.createStandardFont(FontConstants.HELVETICA_BOLD);
            italic = PdfFontFactory.createStandardFont(FontConstants.HELVETICA_OBLIQUE);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Paragraph p = new Paragraph().setFixedLeading(fontsize * 1.2f);
        p.setFont(normal).setFontSize(fontsize).setTextAlignment(Property.TextAlignment.JUSTIFIED);
        p.add(new Text(movie.getMovieTitle()).setFont(bold));
        if (movie.getOriginalTitle() != null) {
            p.add(" ");
            p.add(new Text(movie.getOriginalTitle()).setFont(italic));
        }
        p.add(new Text(String.format("; run length: %s", movie.getDuration())).setFont(normal));
        p.add(new Text("; directed by:").setFont(normal));
        for (Director director : movie.getDirectors()) {
            p.add(" ");
            p.add(director.getGivenName());
            p.add(", ");
            p.add(director.getName());
        }
        return p;
    }

    protected void manipulatePdf(String dest) throws Exception {
        createTemplate(TEMPLATE);
        // open the connection to the database
        DatabaseConnection connection = new HsqldbConnection("filmfestival");
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));

        PdfDocument stamper = null;
        ByteArrayOutputStream baos = null;
        PdfAcroForm form = null;
        int count = 0;
        for (Movie movie : PojoFactory.getMovies(connection)) {
            if (count == 0) {
                baos = new ByteArrayOutputStream();
                stamper = new PdfDocument(new PdfReader(RESOURCE), new PdfWriter(baos));
                form = PdfAcroForm.getAcroForm(stamper, true);
                form.flatFields();
            }
            count++;
            PdfReader ad = new PdfReader(new ByteArrayInputStream(fillTemplate(TEMPLATE, movie)));
            PdfDocument srcDoc = new PdfDocument(ad);
            PdfPage curPage = srcDoc.getFirstPage();
            PdfFormXObject xObject = curPage.copyAsFormXObject(stamper);
            PdfButtonFormField bt = (PdfButtonFormField) form.getField("movie_" + count);
            // TODO No setLayout & setProportionalIcon & setTemplate on PdfFormField
            // bt.setLayout(PushbuttonField.LAYOUT_ICON_ONLY);
            // bt.setProportionalIcon(true);
            // bt.setTemplate(page);
            form.removeField("movie_" + count);
            form.addField(bt);
            if (count == 16) {
                stamper.close();
                stamper = new PdfDocument(new PdfReader(new ByteArrayInputStream(baos.toByteArray())));
                stamper.copyPages(1, 1, pdfDoc);
                stamper.close();
                count = 0;
            }
        }
        if (count > 0) {
            stamper.close();
            stamper = new PdfDocument(new PdfReader(new ByteArrayInputStream(baos.toByteArray())));
            stamper.copyPages(1, 1, pdfDoc);
            stamper.close();
        }
        pdfDoc.close();
        // close the database connection
        connection.close();
    }
}
