package com.itextpdf.samples.book.part2.chapter06;

import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.samples.GenericTest;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class Listing_06_02_MemoryInfo extends GenericTest {
    public static final String RESULT
            = "./target/test/resources/book/part2/chapter06/Listing_06_02_MemoryInfo.txt";
    public static final String CMP_RESULT
            = "./src/test/resources/book/part2/chapter06/cmp_Listing_06_02_MemoryInfo.txt";

    public static final String MOVIE_TEMPLATES = "./src/test/resources/book/part1/chapter03/cmp_Listing_03_29_MovieTemplates.pdf";

    protected String[] arguments;

    public static void main(String args[]) throws IOException, SQLException, NoSuchFieldException, IllegalAccessException {
        Listing_06_02_MemoryInfo app = new Listing_06_02_MemoryInfo();
        app.arguments = args;
        app.manipulatePdf(RESULT);
    }

    public void manipulatePdf(String dest) throws IOException, SQLException, NoSuchFieldException, IllegalAccessException {
        // Listing_03_29_MovieTemplates.main(arguments);
        // Create a writer for a report file
        PrintWriter writer = new PrintWriter(new FileOutputStream(RESULT));
        garbageCollect();
        // Do a full read
        fullRead(writer, MOVIE_TEMPLATES);
        // Do a partial read
        partialRead(writer, MOVIE_TEMPLATES);
        // Close the text file writer
        writer.close();
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
        PdfReader reader = new PdfReader(fileName,null);

        PdfDocument pdfDocument = new PdfDocument(reader);
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

    @Override
    protected void comparePdf(String dest, String cmp) throws Exception {
//        //super.comparePdf(dest, cmp);
//        BufferedReader destReader = new BufferedReader(new InputStreamReader(new FileInputStream(dest)));
//        BufferedReader cmpReader = new BufferedReader(new InputStreamReader(new FileInputStream(cmp)));
//        String curDestStr;
//        String curCmpStr;
//        int row = 1;
//        while ((curDestStr = destReader.readLine()) != null) {
//            if ((curCmpStr = cmpReader.readLine()) != null) {
//                addError("The lengths of files are different.");
//            }
//            if (!curCmpStr.equals(curDestStr)) {
//                addError("The files are different on the row " + row);
//            }
//            row++;
//        }
//        if ((curCmpStr = cmpReader.readLine()) != null) {
//            addError("The lengths of files are different.");
//        }
    }

    @Override
    protected String getDest() {
        // dummy
        return RESULT;
    }

    @Override
    protected String getCmpPdf() {
        // dummy
        return CMP_RESULT;
    }
}
