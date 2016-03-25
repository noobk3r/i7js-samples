package tutorial.chapter04;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.kernel.pdf.action.PdfAction;
import com.itextpdf.kernel.pdf.annot.PdfAnnotation;
import com.itextpdf.kernel.pdf.annot.PdfLinkAnnotation;
import com.itextpdf.kernel.pdf.navigation.PdfDestination;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Link;
import com.itextpdf.layout.element.Paragraph;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Simple link annotation example.
 */
public class C04E01_02_LinkAnnotation {

    public static final String DEST = "results/chapter04/link_annotation.pdf";

    public static void main(String args[]) throws IOException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new C04E01_02_LinkAnnotation().createPdf(DEST);
    }

    public void createPdf(String dest) throws IOException {

        //Initialize PDF writer
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);

        //Initialize PDF document
        PdfDocument pdfDoc = new PdfDocument(writer);

        //Initialize document
        Document doc = new Document(pdfDoc);

        //Create link annotation
        PdfLinkAnnotation annotation = new PdfLinkAnnotation(new Rectangle(523, 770, 36, 36))
                .setAction(PdfAction.createURI("http://itextpdf.com/"))
                .setBorder(new PdfArray(new int[] {0,0,1}));
        Link link = new Link("here", annotation);
        Paragraph p = new Paragraph("The example of link annotation. Click ")
                .add(link.setUnderline())
                .add(" to learn more...");
        doc.add(p);

        //Close document
        doc.close();

    }
}