/**
 * Example written by Bruno Lowagie.
 * http://stackoverflow.com/questions/28368317/itext-or-itextsharp-move-text-in-an-existing-pdf
 */
package com.itextpdf.samples.sandbox.merge;

import com.itextpdf.basics.geom.PageSize;
import com.itextpdf.basics.geom.Rectangle;
import com.itextpdf.canvas.PdfCanvas;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.pdf.xobject.PdfFormXObject;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.IOException;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class CutAndPaste extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/merge/page229_cut_paste.pdf";
    public static final String SRC = "./src/test/resources/sandbox/merge/page229.pdf";
    public static void main(String[] args) throws IOException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new CutAndPaste().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException {
        Rectangle toMove = new Rectangle(100, 500, 100, 100);

        PdfDocument srcDoc = new PdfDocument(new PdfReader(SRC));
        Rectangle pageSize = srcDoc.getFirstPage().getPageSize();

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(DEST));
        pdfDoc.addNewPage();
        Document doc = new Document(pdfDoc, new PageSize(pageSize));

        PdfFormXObject t_canvas1 = new PdfFormXObject(pageSize);
        PdfCanvas canvas1 = new PdfCanvas(t_canvas1, pdfDoc);

        PdfFormXObject page = srcDoc.getFirstPage().copyAsFormXObject(pdfDoc);

        canvas1.rectangle(0, 0, pageSize.getWidth(), pageSize.getHeight());
        canvas1.rectangle(toMove.getLeft(), toMove.getBottom(),
                toMove.getWidth(), toMove.getHeight());
        canvas1.eoClip();
        canvas1.newPath();
        canvas1.addXObject(page, 0, 0);

        PdfFormXObject t_canvas2 = new PdfFormXObject(pageSize);
        PdfCanvas canvas2 = new PdfCanvas(t_canvas2, pdfDoc);
        canvas2.rectangle(toMove.getLeft(), toMove.getBottom(),
                toMove.getWidth(), toMove.getHeight());
        canvas2.clip();
        canvas2.newPath();
        canvas2.addXObject(page, 0, 0);

        PdfCanvas canvas = new PdfCanvas(pdfDoc.getFirstPage());
        canvas.addXObject(t_canvas1, 0, 0);
        canvas.addXObject(t_canvas2, -20, -2);

        srcDoc.close();
        doc.close();
    }
}
