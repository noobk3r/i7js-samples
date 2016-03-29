/*
 * Pdf document manipulation. Shrink/Scale page example.
 */
package tutorial.chapter06;

import com.itextpdf.kernel.geom.AffineTransform;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.xmp.XMPException;
import com.itextpdf.layout.Document;

import java.io.File;
import java.io.IOException;

public class C06E03_TheGoldenGateBridge_N_up {
    public static final String SOURCE = "src/main/resources/pdf/the_golden_gate_bridge.pdf";
    public static final String DEST = "results/chapter06/the_golden_gate_bridge_nup.pdf";

    public static void main(String args[]) throws IOException, XMPException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new C06E03_TheGoldenGateBridge_N_up().createPdf(DEST);
    }

    public void createPdf(String dest) throws IOException, XMPException {
        //Initialize PDF document
        PdfDocument pdf = new PdfDocument(new PdfWriter(dest));
        PdfDocument sourcePdf = new PdfDocument(new PdfReader(SOURCE));

        //Original page
        PdfPage originalPage = sourcePdf.getPage(1);

        //Original page size
        Rectangle originalPageSize = originalPage.getPageSize();

        Rectangle nUpPageSize = PageSize.A4.rotate();

        //N-up page
        PdfPage page = pdf.addNewPage(PageSize.A4.rotate());

        //Create page canvas
        PdfCanvas canvas = new PdfCanvas(page);

        AffineTransform transformationMatrix = AffineTransform.getScaleInstance(
                nUpPageSize.getWidth() / originalPageSize.getWidth() / 2f, nUpPageSize.getHeight() / originalPageSize.getHeight() / 2f);

        //Scale page
        canvas.concatMatrix(transformationMatrix);

        //Add page to N-up page
        canvas.addXObject(originalPage.copyAsFormXObject(pdf), 0, originalPageSize.getHeight());

        canvas.addXObject(originalPage.copyAsFormXObject(pdf), originalPageSize.getWidth(), originalPageSize.getHeight());

        canvas.addXObject(originalPage.copyAsFormXObject(pdf), 0, 0);

        canvas.addXObject(originalPage.copyAsFormXObject(pdf), originalPageSize.getWidth(), 0);

        pdf.close();
        sourcePdf.close();
    }
}
