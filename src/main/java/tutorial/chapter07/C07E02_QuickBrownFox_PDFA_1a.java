/*
 * PDF/A-1a example
 */
package tutorial.chapter07;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.kernel.xmp.XMPException;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;

import com.itextpdf.pdfa.PdfADocument;
import com.itextpdf.pdfa.PdfAConformanceLevel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


public class C07E02_QuickBrownFox_PDFA_1a {
    public static final String DOG = "src/main/resources/img/dog.bmp";
    public static final String FOX = "src/main/resources/img/fox.bmp";
    public static final String FONT = "src/main/resources/font/FreeSans.ttf";
    public static final String INTENT = "src/main/resources/color/sRGB_CS_profile.icm";
        
    public static final String DEST = "results/chapter07/quick_brown_fox_PDFA-1a.pdf";
    
    public static void main(String args[]) throws IOException, XMPException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new C07E02_QuickBrownFox_PDFA_1a().createPdf(DEST);
    }
    
    public void createPdf(String dest) throws IOException, XMPException {
        //Initialize PDFA document with output intent
        PdfADocument pdf = new PdfADocument(new PdfWriter(dest), PdfAConformanceLevel.PDF_A_1A,
                new PdfOutputIntent("Custom", "", "http://www.color.org", "sRGB IEC61966-2.1", new FileInputStream(INTENT)));

        //Initialize document
        Document document = new Document(pdf);

        //Make document tagged
        pdf.setTagged();

        //Set document metadata
        pdf.getCatalog().setViewerPreferences(new PdfViewerPreferences().setDisplayDocTitle(true));
        pdf.getCatalog().setLang(new PdfString("en-US"));
        PdfDocumentInfo info = pdf.getDocumentInfo();
        info.setTitle("iText7 PDF/A-1a example");

        //Create XMP meta data
        pdf.createXmpMetadata();

        Paragraph p = new Paragraph();
        //Embed font
        PdfFont font = PdfFontFactory.createFont(FONT, PdfEncodings.WINANSI, true);
        p.setFont(font);

        p.add(new Text("The quick brown "));

        Image foxImage = new Image(ImageFactory.getImage(FOX));

        //PDF/UA
        //Set alt text
        foxImage.getAccessibilityProperties().setAlternateDescription("Fox");

        p.add(foxImage);

        p.add(" jumps over the lazy ");

        Image dogImage = new Image(ImageFactory.getImage(DOG));

        //PDF/UA
        //Set alt text
        dogImage.getAccessibilityProperties().setAlternateDescription("Dog");

        p.add(dogImage);

        document.add(p);
        document.close();
    }
}
