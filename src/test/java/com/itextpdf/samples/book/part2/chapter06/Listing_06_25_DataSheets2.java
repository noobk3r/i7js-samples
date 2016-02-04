/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

package com.itextpdf.samples.book.part2.chapter06;

import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.forms.PdfAcroForm;

import com.lowagie.database.DatabaseConnection;
import com.lowagie.database.HsqldbConnection;
import com.lowagie.filmfestival.Movie;
import com.lowagie.filmfestival.PojoFactory;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class Listing_06_25_DataSheets2 extends Listing_06_24_DataSheets1 {
    public static final String DEST =
            "./target/test/resources/book/part2/chapter06/Listing_06_25_DataSheets2.pdf";

    public static void main(String args[]) throws IOException, SQLException {
        new Listing_06_25_DataSheets2().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws SQLException, IOException {
        PdfWriter writer = new PdfWriter(new FileOutputStream(dest));
        PdfDocument pdfDocResult = new PdfDocument(writer);
        addDataSheet(pdfDocResult);
    }

    public void addDataSheet(PdfDocument pdfDocResult) throws IOException, SQLException {
        DatabaseConnection connection = new HsqldbConnection("filmfestival");
        List<Movie> movies = PojoFactory.getMovies(connection);
        PdfReader reader;
        pdfDocResult.getWriter().setSmartCopyMode(true);

        ByteArrayOutputStream baos;

        // Loop over all the movies and fill out the data sheet
        for (Movie movie : movies) {
            reader = new PdfReader(DATASHEET);
            baos = new ByteArrayOutputStream();
            PdfDocument pdfDoc = new PdfDocument(reader, new PdfWriter(baos));
            PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);
            form.setGenerateAppearance(true);
            fill(form, movie);
            form.flattenFields();
            pdfDoc.close();
            pdfDoc = new PdfDocument(new PdfReader(new ByteArrayInputStream(baos.toByteArray())));
            pdfDoc.copyPages(1, pdfDoc.getNumberOfPages(), pdfDocResult);
        }

        // Close the database connection
        pdfDocResult.close();
        connection.close();
    }
}
