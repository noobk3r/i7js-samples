package com.itextpdf.samples.book.part1.chapter03;

import com.itextpdf.basics.geom.Rectangle;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.Property;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Text;

import com.lowagie.filmfestival.Screening;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class Listing_03_15_MovieCalendar extends Listing_03_11_MovieTextInfo {

    public static final String DEST = "./target/test/resources/book/part1/chapter03/Listing_03_15_MovieCalendar.pdf";

    public static void main(String[] args) throws Exception {
        new Listing_03_15_MovieCalendar().manipulatePdf(DEST);
    }

    @Override
    protected void drawMovieInfo(Screening screening, Document doc, Text press) {
        super.drawMovieInfo(screening, doc, press);
        Rectangle rect = getPosition(screening);

        Paragraph p = new Paragraph().add(screening.getMovie().getMovieTitle()).
                setFixedPosition(rect.getX(), rect.getY(), rect.getWidth()).
                setHeight(rect.getHeight()).
                setTextAlignment(Property.TextAlignment.CENTER);
        doc.add(p);
    }
}
