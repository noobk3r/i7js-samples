/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

package com.itextpdf.samples.book.part2.chapter06;

import com.itextpdf.io.source.RandomAccessSourceFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.ReaderProperties;
import com.itextpdf.test.annotations.type.SampleTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.internal.ExactComparisonCriteria;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@Category(SampleTest.class)
public class Listing_06_02_MemoryInfo {
    public static final String RESULT
            = "./target/test/resources/book/part2/chapter06/Listing_06_02_MemoryInfo.txt";
    public static final String CMP_RESULT
            = "./src/test/resources/book/part2/chapter06/cmp_Listing_06_02_MemoryInfo.txt";
    public static final String MOVIE_TEMPLATES
            = "./src/test/resources/book/part1/chapter03/cmp_Listing_03_29_MovieTemplates.pdf";

    public static void main(String args[]) throws IOException, SQLException, NoSuchFieldException, IllegalAccessException {
        new Listing_06_02_MemoryInfo().manipulatePdf();
    }

    @Test
    public void manipulatePdf() {
        try {
            // Create a writer for a report file
            PrintWriter writer = new PrintWriter(RESULT);
            garbageCollect();
            // Do a full read
            fullRead(writer, MOVIE_TEMPLATES);
            // Do a partial read
            partialRead(writer, MOVIE_TEMPLATES);
            // Close the text file writer
            writer.close();
        } catch (Exception e) {
            Assert.fail();
        }
        // The test passes if there is no exception, because the results may vary on different machines
    }

    /**
     * Do a full read of a PDF file
     *
     * @param writer   a writer to a report file
     * @param filename the file to read
     * @throws IOException
     */
    public static void fullRead(PrintWriter writer, String filename)
            throws IOException {
        long before = getMemoryUse();
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(filename));
        pdfDoc.getNumberOfPages();
        writer.println(String.format("Memory used by full read: %d",
                getMemoryUse() - before));
        writer.flush();
        pdfDoc.close();
    }

    /**
     * Do a partial read of a PDF file
     *
     * @param writer   a writer to a report file
     * @param fileName the file to read
     * @throws IOException
     */
    public static void partialRead(PrintWriter writer, String fileName) throws IOException {
        long before = getMemoryUse();
        PdfReader reader = new PdfReader(fileName);

        PdfDocument pdfDocument = new PdfDocument(new PdfReader(new RandomAccessSourceFactory().createSource(new FileInputStream(fileName)), new ReaderProperties()));
        pdfDocument.getNumberOfPages();
        writer.println(String.format("Memory used by partial read: %d",
                getMemoryUse() - before));
        writer.flush();
        reader.close();
    }

    /**
     * Returns the current memory use.
     *
     * @return the current memory use
     */
    public static long getMemoryUse() {
        garbageCollect();
        garbageCollect();
        garbageCollect();
        garbageCollect();
        long totalMemory = Runtime.getRuntime().totalMemory();
        garbageCollect();
        garbageCollect();
        long freeMemory = Runtime.getRuntime().freeMemory();
        return (totalMemory - freeMemory);
    }

    /**
     * Makes sure all garbage is cleared from the memory.
     */
    public static void garbageCollect() {
        try {
            System.gc();
            Thread.sleep(200);
            System.runFinalization();
            Thread.sleep(200);
            System.gc();
            Thread.sleep(200);
            System.runFinalization();
            Thread.sleep(200);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

}
