package com.itextpdf.samples.book.chapter03;

import com.itextpdf.model.Property;
import com.itextpdf.model.element.Paragraph;
import com.lowagie.filmfestival.Movie;

public class Listing_03_18_MovieColumns3 extends Listing_03_16_MovieColumns1 {

    public static final String DEST = "./target/test/resources/Listing_03_18_MovieColumns3.pdf";

    public static void main(String[] args) throws Exception {
        new Listing_03_18_MovieColumns3().manipulatePdf(DEST);
    }

    @Override
    public Paragraph createMovieInformation(Movie movie) {
        return super.createMovieInformation(movie).
                setKeepTogether(true).
                setPaddingLeft(0).
                setFirstLineIndent(0).
                setHorizontalAlignment(Property.HorizontalAlignment.LEFT);
    }
}
