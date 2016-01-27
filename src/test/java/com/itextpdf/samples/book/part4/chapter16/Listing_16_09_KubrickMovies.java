/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

package com.itextpdf.samples.book.part4.chapter16;

import com.itextpdf.basics.font.FontConstants;
import com.itextpdf.basics.font.PdfEncodings;
import com.itextpdf.basics.image.ImageFactory;
import com.itextpdf.core.font.PdfFontFactory;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfName;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.pdf.action.PdfAction;
import com.itextpdf.core.pdf.action.PdfTargetDictionary;
import com.itextpdf.core.pdf.filespec.PdfFileSpec;
import com.itextpdf.core.pdf.navigation.PdfNamedDestination;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Cell;
import com.itextpdf.model.element.Image;
import com.itextpdf.model.element.Link;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Table;
import com.itextpdf.samples.GenericTest;
import com.itextpdf.test.annotations.type.SampleTest;
import com.lowagie.database.DatabaseConnection;
import com.lowagie.database.HsqldbConnection;
import com.lowagie.filmfestival.Movie;
import com.lowagie.filmfestival.PojoFactory;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class Listing_16_09_KubrickMovies extends GenericTest {
    public static final String FILENAME = "Listing_16_09_KubrickMovies.pdf";
    public static final String DEST = "./target/test/resources/book/part4/chapter16/" + FILENAME;
    public static final String RESOURCE = "resources/posters/%s.jpg";
    public static final float[] WIDTHS = {1, 7};

    public static void main(String args[]) throws Exception {
        new Listing_16_08_KubrickBox().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws Exception {
        FileOutputStream os = new FileOutputStream(DEST);
        os.write(createPdf());
        os.flush();
        os.close();
    }

    /**
     * Creates a Collection schema that can be used to organize the movies of Stanley Kubrick
     * in a collection: year, title, duration, DVD acquisition, filesize (filename is present, but hidden).
     *
     * @return a collection schema
     */
    // TODO No PdfCollection: PdfCollectionSchema, PdfCollectionField and so on
//    private PdfCollectionSchema getCollectionSchema() {
//        PdfCollectionSchema schema = new PdfCollectionSchema();
//
//        PdfCollectionField size = new PdfCollectionField("File size", PdfCollectionField.SIZE);
//        size.setOrder(4);
//        schema.addField("SIZE", size);
//
//        PdfCollectionField filename = new PdfCollectionField("File name", PdfCollectionField.FILENAME);
//        filename.setVisible(false);
//        schema.addField("FILE", filename);
//
//        PdfCollectionField title = new PdfCollectionField("Movie title", PdfCollectionField.TEXT);
//        title.setOrder(1);
//        schema.addField("TITLE", title);
//
//        PdfCollectionField duration = new PdfCollectionField("Duration", PdfCollectionField.NUMBER);
//        duration.setOrder(2);
//        schema.addField("DURATION", duration);
//
//        PdfCollectionField year = new PdfCollectionField("Year", PdfCollectionField.NUMBER);
//        year.setOrder(0);
//        schema.addField("YEAR", year);
//
//        return schema;
//    }
    public byte[] createPdf() throws IOException, SQLException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(baos));
        Document doc = new Document(pdfDoc);
        doc.add(new Paragraph("This document contains a collection of PDFs, one per Stanley Kubrick movie."));

//        PdfCollection collection = new PdfCollection(PdfCollection.DETAILS);
//        PdfCollectionSchema schema = getCollectionSchema();
//        collection.setSchema(schema);
//        PdfCollectionSort sort = new PdfCollectionSort("YEAR");
//        sort.setSortOrder(false);
//        collection.setSort(sort);
//        collection.setInitialDocument("Eyes Wide Shut");
//        writer.setCollection(collection);

        PdfFileSpec fs;
        // PdfCollectionItem item;
        DatabaseConnection connection = new HsqldbConnection("filmfestival");
        java.util.List<Movie> movies = PojoFactory.getMovies(connection, 1);
        connection.close();
        for (Movie movie : movies) {
            // fs = PdfFileSpecification.fileEmbedded(writer, null,
            //         String.format("kubrick_%s.pdf", movie.getImdb()),
            //         createMoviePage(movie));
            // fs.addDescription(movie.getTitle(), false);

            // item = new PdfCollectionItem(schema);
            // item.addItem("TITLE", movie.getMovieTitle(false));
            // if (movie.getMovieTitle(true) != null) {
            //     item.setPrefix("TITLE", movie.getMovieTitle(true));
            // }
            // item.addItem("DURATION", movie.getDuration());
            // item.addItem("YEAR", movie.getYear());
            // fs.addCollectionItem(item);
            // writer.addFileAttachment(fs);
        }
        doc.close();
        return baos.toByteArray();
    }

    public byte[] createMoviePage(Movie movie) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(baos));
        Document doc = new Document(pdfDoc);
        Paragraph p = new Paragraph(movie.getMovieTitle())
                .setFont(PdfFontFactory.createFont(FontConstants.HELVETICA, PdfEncodings.WINANSI, false)).setFontSize(16);
        doc.add(p);
        doc.add(new Paragraph("\n"));
        Table table = new Table(WIDTHS);
        table.addCell(new Cell().add(new Image(ImageFactory.getImage(String.format(RESOURCE, movie.getImdb())))
                .setAutoScale(true)));
        Cell cell = new Cell();
        cell.add(new Paragraph("Year: " + movie.getYear()));
        cell.add(new Paragraph("Duration: " + movie.getDuration()));
        table.addCell(cell);
        doc.add(table);
        // TODO Implement 'from box' TargetDictionary usage
        PdfTargetDictionary target = new PdfTargetDictionary(PdfName.P);
        target.put(PdfName.T, new PdfTargetDictionary(PdfName.P));
        Link link = new Link("Go to original document",
                PdfAction.createGoToE(new PdfNamedDestination("movies"), false, target));
        doc.add(new Paragraph(link));
        doc.close();
        return baos.toByteArray();
    }
}
