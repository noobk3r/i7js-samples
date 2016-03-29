/*
 * Pdf document manipulation. Shrink/Scale page example.
 */
package tutorial.chapter06;

import com.itextpdf.kernel.geom.AffineTransform;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.kernel.xmp.XMPException;
import com.itextpdf.layout.Document;

import java.io.File;
import java.io.IOException;

public class C06E01_TheGoldenGateBridge_Scale_Shrink {
    public static final String SOURCE = "src/main/resources/pdf/the_golden_gate_bridge.pdf";
    public static final String DEST = "results/chapter06/the_golden_gate_bridge_scale_shrink.pdf";

    public static void main(String args[]) throws IOException, XMPException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new C06E01_TheGoldenGateBridge_Scale_Shrink().createPdf(DEST);
    }

    public void createPdf(String dest) throws IOException, XMPException {
        //Initialize PDF document
        PdfDocument pdf = new PdfDocument(new PdfWriter(dest));
        PdfDocument sourcePdf = new PdfDocument(new PdfReader(SOURCE));

        //Original page size
        PageSize a3 = PageSize.A3.rotate();

        //Add A4 page
        PdfPage page = pdf.addNewPage(PageSize.A4.rotate());

        //Shrink A3 page content by transformation matrix
        PdfCanvas canvas = new PdfCanvas(page);
        AffineTransform transformationMatrix = AffineTransform.getScaleInstance(
                page.getPageSize().getWidth() / a3.getWidth(), page.getPageSize().getHeight() / a3.getHeight());
        canvas.concatMatrix(transformationMatrix);
        PdfFormXObject pageCopy = sourcePdf.getPage(1).copyAsFormXObject(pdf);
        canvas.addXObject(pageCopy, 0, 0);

        //Add page with original A3 size
        pdf.addPage(sourcePdf.getPage(1).copyTo(pdf));

        //Add A2 page
        page = pdf.addNewPage(PageSize.A2.rotate());

        //Scale A3 page content by transformation matrix
        canvas = new PdfCanvas(page);
        transformationMatrix = AffineTransform.getScaleInstance(
                page.getPageSize().getWidth() / a3.getWidth(), page.getPageSize().getHeight() / a3.getHeight());
        canvas.concatMatrix(transformationMatrix);
        pageCopy = sourcePdf.getPage(1).copyAsFormXObject(pdf);
        canvas.addXObject(pageCopy, 0, 0);

        pdf.close();
        sourcePdf.close();
    }
}
