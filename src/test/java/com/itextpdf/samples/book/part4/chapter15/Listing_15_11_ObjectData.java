/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

package com.itextpdf.samples.book.part4.chapter15;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfNumber;
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.CanvasTag;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.tagging.PdfStructElem;
import com.itextpdf.kernel.pdf.tagging.PdfStructTreeRoot;
import com.itextpdf.samples.GenericTest;
import com.itextpdf.test.annotations.type.SampleTest;
import com.lowagie.database.DatabaseConnection;
import com.lowagie.database.HsqldbConnection;
import com.lowagie.filmfestival.Director;
import com.lowagie.filmfestival.Movie;
import com.lowagie.filmfestival.PojoFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class Listing_15_11_ObjectData extends GenericTest {
    public static final String DEST
            = "./target/test/resources/book/part4/chapter15/Listing_15_11_ObjectData.pdf";
    public static final String RESOURCE
            = "./src/test/resources/img/posters/%s.jpg";
    public static final String SELECTDIRECTORS
            = "SELECT DISTINCT d.id, d.name, d.given_name, count(*) AS c "
                    + "FROM film_director d, film_movie_director md "
                    + "WHERE d.id = md.director_id AND d.id < 8 "
                    + "GROUP BY d.id, d.name, d.given_name ORDER BY id";

    public static void main(String args[]) throws IOException, SQLException {
        new Listing_15_11_ObjectData().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException, SQLException {
        //Initialize writer
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);

        //Initialize document
        PdfDocument pdfDoc = new PdfDocument(writer);
        pdfDoc.setTagged();
        pdfDoc.setUserProperties(true);

        pdfDoc.getStructTreeRoot().getRoleMap().put(new PdfName("Directors"), PdfName.H);
        for (int i = 1; i < 8; i++) {
            pdfDoc.getStructTreeRoot().getRoleMap().put(new PdfName("director" + i), PdfName.P);
        }

        DatabaseConnection connection = new HsqldbConnection("filmfestival");

        PdfStructTreeRoot tree = pdfDoc.getStructTreeRoot();
        PdfStructElem top = tree.addKid(new PdfStructElem(pdfDoc, new PdfName("Directors")));

        Map<Integer, PdfStructElem> directors = new HashMap<Integer, PdfStructElem>();
        Statement stm = connection.createStatement();
        ResultSet rs = stm.executeQuery(SELECTDIRECTORS);
        int id;
        Director director;
        PdfStructElem e;
        while (rs.next()) {
            id = rs.getInt("id");
            director = PojoFactory.getDirector(rs);
            e = top.addKid(new PdfStructElem(pdfDoc, new PdfName("director" + id)));
            PdfDictionary userproperties = new PdfDictionary();
            userproperties.put(PdfName.O, new PdfName("UserProperties"));
            PdfArray properties = new PdfArray();
            PdfDictionary property1 = new PdfDictionary();
            property1.put(PdfName.N, new PdfString("Name"));
            property1.put(PdfName.V, new PdfString(director.getName()));
            properties.add(property1);
            PdfDictionary property2 = new PdfDictionary();
            property2.put(PdfName.N, new PdfString("Given name"));
            property2.put(PdfName.V, new PdfString(director.getGivenName()));
            properties.add(property2);
            PdfDictionary property3 = new PdfDictionary();
            property3.put(PdfName.N, new PdfString("Posters"));
            property3.put(PdfName.V, new PdfNumber(rs.getInt("c")));
            properties.add(property3);
            userproperties.put(PdfName.P, properties);
            e.put(PdfName.A, userproperties);
            directors.put(id, e);
        }

        Map<Movie, Integer> map = new TreeMap<Movie, Integer>();
        for (int i = 1; i < 8; i++) {
            List<Movie> movies = PojoFactory.getMovies(connection, i);
            for (Movie movie : movies) {
                map.put(movie, i);
            }
        }

        PdfCanvas canvas = new PdfCanvas(pdfDoc.addNewPage());
        ImageData img;
        float x = 11.5f;
        float y = 769.7f;
        for (Map.Entry<Movie, Integer> entry : map.entrySet()) {
            img = ImageDataFactory.create(String.format(RESOURCE, entry.getKey().getImdb()));
            com.itextpdf.layout.element.Image image = new com.itextpdf.layout.element.Image(img);
            directors.get(entry.getValue()).addKid(new PdfStructElem(pdfDoc, image.getRole()));
            canvas.openTag(new CanvasTag(image.getRole()));
            canvas.addImage(img, x + (45 - 30) / 2, y, 30, true);
            canvas.closeTag();
            x += 48;
            if (x > 578) {
                x = 11.5f;
                y -= 84.2f;
            }
        }
        pdfDoc.close();
    }
}
