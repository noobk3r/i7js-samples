package com.itextpdf.samples.book.chapter03;

import com.itextpdf.core.geom.Rectangle;
import com.itextpdf.model.Document;
import com.itextpdf.model.Property;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Text;
import com.lowagie.filmfestival.Screening;

public class Listing_03_15_MovieCalendar extends Listing_03_11_MovieTextInfo {

    public static final String DEST = "./target/test/resources/Listing_03_15_MovieCalendar.pdf";

    public static void main(String[] args) throws Exception {
        new Listing_03_15_MovieCalendar().manipulatePdf(DEST);
    }

    @Override
    protected void drawMovieInfo(Screening screening, Document doc, Text press) {
        super.drawMovieInfo(screening, doc, press);
        Rectangle rect = getPosition(screening);

        Paragraph p = new Paragraph().add(screening.getMovie().getMovieTitle()).
                setFixedPosition(rect.getX(), rect.getY()).
                setWidth(rect.getWidth()).
                setHeight(rect.getHeight()).
                setAlignment(Property.Alignment.CENTER);
        doc.add(p);
    }
}
