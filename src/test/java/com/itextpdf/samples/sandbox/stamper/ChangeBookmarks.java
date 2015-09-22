/**
 * Example written by Bruno Lowagie in answer to:
 * http://stackoverflow.com/questions/24217657/set-inherit-zoomaction-property-to-bookmark-in-the-pdf-file
 */
package com.itextpdf.samples.sandbox.stamper;

import com.itextpdf.core.pdf.*;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.samples.GenericTest;
import org.junit.experimental.categories.Category;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

@Category(SampleTest.class)
public class ChangeBookmarks extends GenericTest {
    public static final String SRC = "./src/test/resources/sandbox/stamper/bookmarks.pdf";
    public static final String DEST = "./target/test/resources/sandbox/stamper/change_bookmarks.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new ChangeBookmarks().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(
                new FileInputStream(SRC)), new PdfWriter(new FileOutputStream(DEST)));
        PdfOutline outlines = pdfDoc.getOutlines(false);
        List<PdfOutline> children = outlines.getAllChildren().get(0).getAllChildren();
        changeList(children);
        pdfDoc.close();
    }

    public void changeList(List<PdfOutline> list) {
        for (PdfOutline entry : list) {
            PdfArray array = ((PdfArray) entry.getContent().get(PdfName.Dest));
            for (int i = 0; i < array.size(); i++) {
                if (PdfName.Fit.equals(array.get(i))) {
                    // one should be more cautious but since we know the source file...
                    array.set(i, PdfName.FitV);
                    array.add(new PdfNumber(60));
                }
            }
        }
    }

}
