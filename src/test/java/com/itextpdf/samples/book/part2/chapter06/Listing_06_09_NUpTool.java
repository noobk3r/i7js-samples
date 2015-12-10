package com.itextpdf.samples.book.part2.chapter06;

import com.itextpdf.basics.geom.PageSize;
import com.itextpdf.basics.geom.Rectangle;
import com.itextpdf.canvas.PdfCanvas;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.pdf.xobject.PdfFormXObject;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.AreaBreak;
import com.itextpdf.samples.GenericTest;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Category(SampleTest.class) @Ignore
public class Listing_06_09_NUpTool extends GenericTest {
    public static final String DEST = "./target/test/resources/book/part2/chapter06/Listing_06_09_NUpTool.pdf";
    public static final String RESULT = "./target/test/resources/book/part2/chapter06/Listing_06_09_NUpTool%dup.pdf";
    public static final String RESOURCE = "./src/test/resources/book/part2/chapter06/loa.jpg";

    protected String[] arguments;

    public static void main(String args[]) throws IOException, SQLException {
        Listing_06_09_NUpTool nupTool = new Listing_06_09_NUpTool();
        nupTool.arguments = args;
        nupTool.manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException, SQLException {
        Listing_06_08_Stationery.main(arguments);
        new Listing_06_09_NUpTool().multiplePdf(Listing_06_08_Stationery.DEST, RESULT, 1);
        new Listing_06_09_NUpTool().multiplePdf(Listing_06_08_Stationery.DEST, RESULT, 2);
        new Listing_06_09_NUpTool().multiplePdf(Listing_06_08_Stationery.DEST, RESULT, 3);
        new Listing_06_09_NUpTool().multiplePdf(Listing_06_08_Stationery.DEST, RESULT, 4);
        // Create file to compare via CompareTool
        concatenateResults(DEST, new String[]{String.format(RESULT, 2), String.format(RESULT, 4),
                String.format(RESULT, 8), String.format(RESULT, 16)});
    }

    public void multiplePdf(String source, String destination, int pow) throws IOException {
        PdfReader reader = new PdfReader(source);
        PdfDocument srcDoc = new PdfDocument(reader);

        Rectangle pageSize = srcDoc.getDefaultPageSize();
        Rectangle newSize = (pow % 2) == 0 ?
                new Rectangle(pageSize.getWidth(), pageSize.getHeight()) :
                new Rectangle(pageSize.getHeight(), pageSize.getWidth());
        Rectangle unitSize = new Rectangle(pageSize.getWidth(), pageSize.getHeight());
        for (int i = 0; i < pow; i++) {
            unitSize = new Rectangle(unitSize.getHeight() / 2, unitSize.getWidth());
        }
        int n = (int) Math.pow(2, pow);
        int r = (int) Math.pow(2, pow / 2);
        int c = n / r;

        // Initialize result document
        FileOutputStream fos = new FileOutputStream(String.format(destination, n));
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc, new PageSize(newSize));
        pdfDoc.addNewPage();

        Rectangle currentSize;
        float offsetX, offsetY, factor;
        int total = srcDoc.getNumOfPages();
        for (int i = 1; i <= total; i++) {
            currentSize = srcDoc.getPage(i).getPageSize();
            factor = Math.min(
                    unitSize.getWidth() / currentSize.getWidth(),
                    unitSize.getHeight() / currentSize.getHeight());
            offsetX = unitSize.getWidth() * ((i % n) % c)
                    + (unitSize.getWidth() - (currentSize.getWidth() * factor)) / 2f;
            offsetY = newSize.getHeight() - (unitSize.getHeight() * (((i % n) / c) + 1))
                    + (unitSize.getHeight() - (currentSize.getHeight() * factor)) / 2f;
            PdfFormXObject page = srcDoc.getPage(i).copyAsFormXObject(pdfDoc);
            new PdfCanvas(pdfDoc.getLastPage().newContentStreamBefore(), pdfDoc.getLastPage().getResources(), pdfDoc)
                    .addXObject(page, factor, 0, 0, factor, offsetX, offsetY);
            if (i % n == 0 && i != total) {
                doc.add(new AreaBreak());
            }
        }

        doc.close();
        srcDoc.close();
    }

    // Only for testing reasons
    protected void concatenateResults(String dest, String[] names) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
        PdfDocument tempDoc;
        for (String name : names) {
            tempDoc = new PdfDocument(new PdfReader(name));
            tempDoc.copyPages(1, tempDoc.getNumOfPages(), pdfDoc);
            tempDoc.close();
        }
        pdfDoc.close();
    }
}
