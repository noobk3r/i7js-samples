package com.itextpdf.samples.book.part1.chapter03;

import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Property;
import com.itextpdf.model.element.Paragraph;

import com.lowagie.filmfestival.Movie;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class Listing_03_18_MovieColumns3 extends Listing_03_16_MovieColumns1 {

    public static final String DEST = "./target/test/resources/book/part1/chapter03/Listing_03_18_MovieColumns3.pdf";

    public static void main(String[] args) throws Exception {
        new Listing_03_18_MovieColumns3().manipulatePdf(DEST);
    }

    @Override
    public Paragraph createMovieInformation(Movie movie) {
        return super.createMovieInformation(movie).
                setKeepTogether(true).
                setPaddingLeft(0).
                setFirstLineIndent(0).
                setTextAlignment(Property.TextAlignment.LEFT);
    }
}
