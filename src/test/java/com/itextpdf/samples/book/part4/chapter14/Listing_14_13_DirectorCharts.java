/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

package com.itextpdf.samples.book.part4.chapter14;

import com.itextpdf.core.geom.PageSize;
import com.itextpdf.core.geom.Rectangle;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.pdf.canvas.PdfCanvas;
import com.itextpdf.core.pdf.xobject.PdfFormXObject;
import com.itextpdf.samples.GenericTest;
import com.itextpdf.test.annotations.type.SampleTest;
import com.lowagie.database.DatabaseConnection;
import com.lowagie.database.HsqldbConnection;
import com.lowagie.filmfestival.Director;
import com.lowagie.filmfestival.PojoFactory;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class Listing_14_13_DirectorCharts extends GenericTest {
    public static final String DEST
            = "./target/test/resources/book/part4/chapter14/Listing_14_13_DirectorCharts.pdf";
    /** A query that needs to be visualized in a chart. */
    public static final String QUERY1 =
            "SELECT DISTINCT d.id, d.name, d.given_name, count(*) AS c "
                    + "FROM film_director d, film_movie_director md "
                    + "WHERE d.id = md.director_id "
                    + "GROUP BY d.id, d.name, d.given_name ORDER BY c DESC, name LIMIT 9";
    /** A query that needs to be visualized in a chart. */
    public static final String QUERY2 =
            "SELECT DISTINCT d.id, d.name, d.given_name, count(*) AS c "
                    + "FROM film_director d, film_movie_director md "
                    + "WHERE d.id = md.director_id "
                    + "GROUP BY d.id, d.name, d.given_name ORDER BY c DESC, name LIMIT 17";

    public static void main(String args[]) throws IOException {
        new Listing_14_13_DirectorCharts().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
        PdfCanvas cb = new PdfCanvas(pdfDoc.addNewPage());
        float width = PageSize.A4.getWidth();
        float height = PageSize.A4.getHeight() / 2;
        // Pie chart
        PdfFormXObject xObject = new PdfFormXObject(new Rectangle(width, height));
        // TODO No PdfGraphics2D
        // Graphics2D g2d1 = new PdfGraphics2D(pie, width, height);
        // Rectangle2D r2d1 = new Rectangle2D.Double(0, 0, width, height);
        // getPieChart().draw(g2d1, r2d1);
//        g2d1.dispose();
//        cb.addTemplate(pie, 0, height);
//        // Bar chart
//        PdfTemplate bar = cb.createTemplate(width, height);
//        Graphics2D g2d2 = new PdfGraphics2D(bar, width, height);
//        Rectangle2D r2d2 = new Rectangle2D.Double(0, 0, width, height);
//        getBarChart().draw(g2d2, r2d2);
//        g2d2.dispose();
//        cb.addTemplate(bar, 0, 0);

        pdfDoc.close();
    }

    public static JFreeChart getPieChart() throws SQLException, IOException {
        DatabaseConnection connection = new HsqldbConnection("filmfestival");
        Statement stm = connection.createStatement();
        ResultSet rs = stm.executeQuery(QUERY1);
        DefaultPieDataset dataset = new DefaultPieDataset();
        Director director;
        while (rs.next()) {
            director = PojoFactory.getDirector(rs);
            dataset.setValue(
                    String.format("%s, %s", director.getName(), director.getGivenName()),
                    rs.getInt("c"));
        }
        connection.close();
        return ChartFactory.createPieChart("Movies / directors", dataset,
                true, true, false);
    }

    public static JFreeChart getBarChart() throws SQLException, IOException {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        DatabaseConnection connection = new HsqldbConnection("filmfestival");
        Statement stm = connection.createStatement();
        ResultSet rs = stm.executeQuery(QUERY2);
        Director director;
        while (rs.next()) {
            director = PojoFactory.getDirector(rs);
            dataset.setValue(
                    rs.getInt("c"),
                    "movies",
                    String.format("%s, %s", director.getName(), director.getGivenName()));
        }
        connection.close();
        return ChartFactory.createBarChart("Movies / directors", "Director",
                "# Movies", dataset, PlotOrientation.HORIZONTAL, false,
                true, false);
    }
}