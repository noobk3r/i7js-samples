/*
 * This example was written by Bruno Lowagie.
 * It is part of the iText 7 tutorial.
 */
package tutorial.chapter01;

import com.itextpdf.io.image.ImageFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.xobject.PdfImageXObject;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Simple image example.
 */
public class C01E03_QuickBrownFox1 {
    public static final String DOG = "src/main/resources/img/dog.bmp";
    public static final String FOX = "src/main/resources/img/fox.bmp";
        
    public static final String DEST = "results/chapter01/quick_brown_fox_1.pdf";
    
    public static void main(String args[]) throws IOException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new C01E03_QuickBrownFox1().createPdf(DEST);
    }
    
    public void createPdf(String dest) throws IOException {
        //Initialize PDF writer
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);

        //Initialize PDF document
        PdfDocument pdf = new PdfDocument(writer);
        
        // Initialize document
        Document document = new Document(pdf);
        
        // Compose Paragraph
        PdfImageXObject fox = new PdfImageXObject(ImageFactory.getImage(FOX));
        PdfImageXObject dog = new PdfImageXObject(ImageFactory.getImage(DOG));
        Paragraph p = new Paragraph("Quick brown ")
                .add(new Image(fox))
                .add(" jumps over the lazy ")
                .add(new Image(dog));
        // Add Paragraph to document
        document.add(p);
        
        //Close document
        document.close();
    }
}
