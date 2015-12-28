package com.itextpdf.samples.book.part4.chapter15;

import com.itextpdf.basics.font.FontConstants;
import com.itextpdf.canvas.PdfCanvas;
import com.itextpdf.core.color.DeviceRgb;
import com.itextpdf.core.font.PdfFont;
import com.itextpdf.core.pdf.PdfDictionary;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfName;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.pdf.action.PdfAction;
import com.itextpdf.core.pdf.action.PdfActionOcgState;
import com.itextpdf.core.pdf.layer.PdfLayer;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Link;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.samples.GenericTest;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class Listing_15_08_OptionalContentActionExample extends GenericTest {
    public static final String DEST
            = "./target/test/resources/book/part4/chapter15/Listing_15_08_OptionalContentActionExample.pdf";

    public static void main(String args[]) throws IOException {
        new Listing_15_08_OptionalContentActionExample().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException {
        //Initialize writer
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);

        //Initialize document
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);

        // Working with layers and writing to canvas.
        PdfLayer a1 = new PdfLayer("answer 1", pdfDoc);
        PdfLayer a2 = new PdfLayer("answer 2", pdfDoc);
        PdfLayer a3 = new PdfLayer("answer 3", pdfDoc);
        a1.setOn(false);
        a2.setOn(false);
        a3.setOn(false);

        PdfCanvas canvas = new PdfCanvas(pdfDoc.addNewPage())
                .setFontAndSize(PdfFont.createStandardFont(FontConstants.HELVETICA), 18);
        canvas.beginText().moveText(50, 766).showText("Q1: Who is the director of the movie 'Paths of Glory'?").endText();
        canvas.beginText().moveText(50, 718).showText("Q2: Who directed the movie 'Lawrence of Arabia'?").endText();
        canvas.beginText().moveText(50, 670).showText("Q3: Who is the director of 'House of Flying Daggers'?").endText();
        canvas.saveState();
        canvas.setFontAndSize(PdfFont.createStandardFont(FontConstants.HELVETICA), 18);
        canvas.setFillColor(new DeviceRgb(0xFF, 0x00, 0x00));
        canvas.beginLayer(a1).beginText().moveText(50, 742).showText("A1: Stanley Kubrick").endText().endLayer();
        canvas.beginLayer(a2).beginText().moveText(50, 694).showText("A2: David Lean").endText().endLayer();
        canvas.beginLayer(a3).beginText().moveText(50, 646).showText("A3: Zhang Yimou").endText().endLayer();
        canvas.restoreState();
        ArrayList<PdfDictionary> dictList;

        ArrayList<PdfActionOcgState> stateOn = new ArrayList<PdfActionOcgState>();
        dictList = new ArrayList<>();
        dictList.add(a1.getPdfObject());
        stateOn.add(new PdfActionOcgState(PdfName.ON, dictList));
        dictList = new ArrayList<>();
        dictList.add(a2.getPdfObject());
        stateOn.add(new PdfActionOcgState(PdfName.ON, dictList));
        dictList = new ArrayList<>();
        dictList.add(a3.getPdfObject());
        stateOn.add(new PdfActionOcgState(PdfName.ON, dictList));
        PdfAction actionOn = PdfAction.createSetOcgState(pdfDoc, stateOn, true);

        ArrayList<PdfActionOcgState> stateOff = new ArrayList<PdfActionOcgState>();
        dictList = new ArrayList<>();
        dictList.add(a1.getPdfObject());
        stateOff.add(new PdfActionOcgState(PdfName.OFF, dictList));
        dictList = new ArrayList<>();
        dictList.add(a2.getPdfObject());
        stateOff.add(new PdfActionOcgState(PdfName.OFF, dictList));
        dictList = new ArrayList<>();
        dictList.add(a3.getPdfObject());
        stateOff.add(new PdfActionOcgState(PdfName.OFF, dictList));
        PdfAction actionOff = PdfAction.createSetOcgState(pdfDoc, stateOff, true);

        dictList = new ArrayList<>();
        dictList.add(a1.getPdfObject());
        dictList.add(a2.getPdfObject());
        dictList.add(a3.getPdfObject());
        ArrayList<PdfActionOcgState> stateToggle = new ArrayList<>();
        stateToggle.add(new PdfActionOcgState(new PdfName("Toggle"), dictList));
        PdfAction actionToggle = PdfAction.createSetOcgState(pdfDoc, stateToggle, true);

        // TODO Ambiguity in parameters: compare 'paragraphs' above and below
//        ArrayList<PdfActionOcgState> stateToggle = new ArrayList<PdfActionOcgState>();
//        dictList = new ArrayList<>();
//        dictList.add(a1.getPdfObject());
//        stateToggle.add(new PdfActionOcgState(new PdfName("Toggle"), dictList));
//        dictList = new ArrayList<>();
//        dictList.add(a2.getPdfObject());
//        stateToggle.add(new PdfActionOcgState(new PdfName("Toggle"), dictList));
//        dictList = new ArrayList<>();
//        dictList.add(a3.getPdfObject());
//        stateToggle.add(new PdfActionOcgState(new PdfName("Toggle"), dictList));
//        PdfAction actionToggle  = PdfAction.createSetOcgState(pdfDoc, stateToggle, true);

        Paragraph p = new Paragraph("Change the state of the answers:");
        Link on = new Link(" on ", actionOn);
        p.add(on);
        Link off = new Link("/ off ", actionOff);
        p.add(off);
        Link toggle = new Link("/ toggle", actionToggle);
        p.add(toggle);
        doc.add(p);

        // Close the document
        doc.close();
    }
}
