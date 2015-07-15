package com.itextpdf.samples.book.chapter03;

import com.itextpdf.samples.GenericTest;
import org.junit.Ignore;

@Ignore("port when showTextAligned (or its analogue) is implemented")
public class Listing_03_07_FoobarFilmFestival extends GenericTest {

    public static final String DEST = "./target/test/resources/Listing_03_05_MovieTimeBlocks.pdf";

    public static void main(String[] args) throws Exception {
        new Listing_03_07_FoobarFilmFestival().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        super.manipulatePdf(dest);
    }
}
