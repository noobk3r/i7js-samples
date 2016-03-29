/*
 * Pdf document manipulation. Tiles generating.
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

public class C06E02_TheGoldenGateBridge_Tiles {
    public static final String SOURCE = "src/main/resources/pdf/the_golden_gate_bridge.pdf";
    public static final String DEST = "results/chapter06/the_golden_gate_bridge_tiles.pdf";

    public static void main(String args[]) throws IOException, XMPException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new C06E02_TheGoldenGateBridge_Tiles().createPdf(DEST);
    }

    public void createPdf(String dest) throws IOException, XMPException {
        //Initialize PDF document
        PdfDocument pdf = new PdfDocument(new PdfWriter(dest));
        PdfDocument sourcePdf = new PdfDocument(new PdfReader(SOURCE));

        //Original page
        PdfPage originalPage = sourcePdf.getPage(1);

        //Original page size
        Rectangle originalPageSize = originalPage.getPageSize();

        //Tile size
        Rectangle tileSize = PageSize.A4.rotate();


        AffineTransform transformationMatrix = AffineTransform.getScaleInstance(
                tileSize.getWidth() / originalPageSize.getWidth() * 2f, tileSize.getHeight() / originalPageSize.getHeight() * 2f);


        //The first tile
        PdfPage page = pdf.addNewPage(PageSize.A4.rotate());

        //Create page canvas
        PdfCanvas canvas = new PdfCanvas(page);

        //Scale page
        canvas.concatMatrix(transformationMatrix);

        //Add tile as form XObject
        canvas.addXObject(originalPage.copyAsFormXObject(pdf), 0, -originalPageSize.getHeight() / 2f);

        //The second tile
        page = pdf.addNewPage(PageSize.A4.rotate());

        canvas = new PdfCanvas(page);

        canvas.concatMatrix(transformationMatrix);

        canvas.addXObject(originalPage.copyAsFormXObject(pdf), -originalPageSize.getWidth() / 2f, -originalPageSize.getHeight() / 2f);

        //The third tile
        page = pdf.addNewPage(PageSize.A4.rotate());

        canvas = new PdfCanvas(page);

        canvas.concatMatrix(transformationMatrix);

        canvas.addXObject(originalPage.copyAsFormXObject(pdf), 0, 0);

        //The fourth tile
        page = pdf.addNewPage(PageSize.A4.rotate());

        canvas = new PdfCanvas(page);

        canvas.concatMatrix(transformationMatrix);

        canvas.addXObject(originalPage.copyAsFormXObject(pdf), -originalPageSize.getWidth() / 2f, 0);

        pdf.close();
        sourcePdf.close();
    }
}
