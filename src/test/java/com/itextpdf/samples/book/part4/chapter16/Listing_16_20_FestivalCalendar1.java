package com.itextpdf.samples.book.part4.chapter16;

import com.itextpdf.core.pdf.PdfDeveloperExtension;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfVersion;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.samples.GenericTest;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class Listing_16_20_FestivalCalendar1 extends GenericTest {
    public static final String DEST = "./target/test/resources/book/part4/chapter16/Listing_16_20_FestivalCalendar1.pdf";
    public static final String RESOURCE = "resources/swf/FestivalCalendar1.swf";

    public static void main(String args[]) throws Exception {
        new Listing_16_20_FestivalCalendar1().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest), PdfVersion.PDF_1_7);
        pdfDoc.getCatalog().addDeveloperExtension(PdfDeveloperExtension.ADOBE_1_7_EXTENSIONLEVEL3);
        // // we prepare a RichMediaAnnotation
        // TODO No RichMedia: RichMediaAnnotation, RichMediaParams, RichMediaActivation and so on
        // RichMediaAnnotation richMedia = new RichMediaAnnotation(writer, new Rectangle(36, 400, 559,806));
        // // we embed the swf file
        // PdfFileSpec fs = PdfFileSpec.createEmbeddedFileSpec(RESOURCE, "FestivalCalendar1.swf", false);
        // we declare the swf file as an asset
        // PdfIndirectReference asset = richMedia.addAsset("FestivalCalendar1.swf", fs);
        // we create a configuration
        // RichMediaConfiguration configuration = new RichMediaConfiguration(PdfName.FLASH);
        // RichMediaInstance instance = new RichMediaInstance(PdfName.FLASH);
        // RichMediaParams flashVars = new RichMediaParams();
        // String vars = new String("&day=2011-10-13");
        // flashVars.setFlashVars(vars);
        // instance.setParams(flashVars);
        // instance.setAsset(asset);
        // configuration.addInstance(instance);
        // // we add the configuration to the annotation
        // PdfIndirectReference configurationRef = richMedia.addConfiguration(configuration);
        // activation of the rich media
        // RichMediaActivation activation = new RichMediaActivation();
        // activation.setConfiguration(configurationRef);
        // richMedia.setActivation(activation);
        // // we add the annotation
        // PdfAnnotation richMediaAnnotation = richMedia.createAnnotation();
        // richMediaAnnotation.setFlags(PdfAnnotation.FLAGS_PRINT);
        // writer.addAnnotation(richMediaAnnotation);
        pdfDoc.close();
    }
}
