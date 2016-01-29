/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

package com.itextpdf.samples.book.part4.chapter15;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.core.geom.PageSize;
import com.itextpdf.core.geom.Rectangle;
import com.itextpdf.core.font.PdfFont;
import com.itextpdf.core.font.PdfFontFactory;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfVersion;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.pdf.canvas.PdfCanvas;
import com.itextpdf.core.pdf.layer.PdfLayer;
import com.itextpdf.core.pdf.xobject.PdfFormXObject;
import com.itextpdf.model.Canvas;
import com.itextpdf.model.Property;
import com.itextpdf.test.annotations.type.SampleTest;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class Listing_15_10_SvgLayers extends Listing_15_09_SvgToPdf {
    public static final String DEST
            = "./target/test/resources/book/part4/chapter15/Listing_15_10_SvgLayers.pdf";
    public static final String RUES
            = "./src/test/resources/book/part4/chapter15/foobarrues.svg";
    public static final String STRATEN
            = "./src/test/resources/book/part4/chapter15/foobarstraten.svg";

    public static void main(String args[]) throws IOException {
        new Listing_15_10_SvgLayers().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException {
        //Initialize writer
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);

        //Initialize document
        PdfDocument pdfDoc = new PdfDocument(writer, PdfVersion.PDF_1_5);
        pdfDoc.setDefaultPageSize(new PageSize(6000, 6000));

        // TODO No setViewerPreferences
        // writer.setViewerPreferences(PdfWriter.PageModeUseOC | PdfWriter.FitWindow);
        // writer.setPdfVersion(PdfWriter.VERSION_1_5);

        // CREATE GRID LAYER
        PdfLayer gridLayer = new PdfLayer("Grid", pdfDoc);
        gridLayer.setZoom(0.2f, 1);
        gridLayer.setOnPanel(false);
        // CREATE STREET LAYERS
        PdfLayer streetlayer = PdfLayer.createTitle(
                "Streets / Rues / Straten", pdfDoc);
        PdfLayer streetlayer_en = new PdfLayer("English", pdfDoc);
        streetlayer_en.setOn(true);
        streetlayer_en.setLanguage("en", true);
        PdfLayer streetlayer_fr = new PdfLayer("Fran\u00e7ais", pdfDoc);
        streetlayer_fr.setOn(false);
        streetlayer_fr.setLanguage("fr", false);
        PdfLayer streetlayer_nl = new PdfLayer("Nederlands", pdfDoc);
        streetlayer_nl.setOn(false);
        streetlayer_nl.setLanguage("nl", false);
        streetlayer.addChild(streetlayer_en);
        streetlayer.addChild(streetlayer_fr);
        streetlayer.addChild(streetlayer_nl);
        ArrayList<PdfLayer> radio = new ArrayList<PdfLayer>();
        radio.add(streetlayer_en);
        radio.add(streetlayer_fr);
        radio.add(streetlayer_nl);
        // TODO No addOCGRadioGroup on PdfDocument
        // writer.addOCGRadioGroup(radio);
        // CREATE MAP
        PdfCanvas canvas = new PdfCanvas(pdfDoc.addNewPage());
        PdfFormXObject map = new PdfFormXObject(new Rectangle(6000, 6000));
        // DRAW CITY
        // TODO First revise this method in SvgToPdf
        drawSvg(map, CITY);
        canvas.addXObject(map, 0, 0);
        PdfFormXObject streets = new PdfFormXObject(new Rectangle(6000, 6000));
        // DRAW STREETS
        drawSvg(streets, STREETS);
        streets.setLayer(streetlayer_en);
        canvas.addXObject(streets, 0, 0);
        PdfFormXObject rues = new PdfFormXObject(new Rectangle(6000, 6000));
        drawSvg(rues, RUES);
        rues.setLayer(streetlayer_fr);
        canvas.addXObject(rues, 0, 0);
        PdfFormXObject straten = new PdfFormXObject(new Rectangle(6000, 6000));;
        drawSvg(straten, STRATEN);
        straten.setLayer(streetlayer_nl);
        canvas.addXObject(straten, 0, 0);
        // DRAW GRID
        canvas.saveState();
        canvas.beginLayer(gridLayer);
        canvas.setStrokeColorGray(0.7f);
        canvas.setLineWidth(2);
        for (int i = 0; i < 8; i++) {
            canvas.moveTo(1250, 1500 + i * 500);
            canvas.lineTo(4750, 1500 + i * 500);
        }
        for (int i = 0; i < 8; i++) {
            canvas.moveTo(1250 + i * 500, 1500);
            canvas.lineTo(1250 + i * 500, 5000);
        }
        canvas.stroke();
        canvas.endLayer();
        canvas.restoreState();

        // CREATE INFO LAYERS
        PdfLayer cityInfoLayer = new PdfLayer("Foobar Info", pdfDoc);
        cityInfoLayer.setOn(false);
        PdfLayer hotelLayer = new PdfLayer("Hotel", pdfDoc);
        hotelLayer.setOn(false);
        cityInfoLayer.addChild(hotelLayer);
        PdfLayer parkingLayer = new PdfLayer("Parking", pdfDoc);
        parkingLayer.setOn(false);
        cityInfoLayer.addChild(parkingLayer);
        PdfLayer businessLayer = new PdfLayer("Industry", pdfDoc);
        businessLayer.setOn(false);
        cityInfoLayer.addChild(businessLayer);
        PdfLayer cultureLayer = PdfLayer.createTitle("Leisure and Culture", pdfDoc);
        PdfLayer goingoutLayer = new PdfLayer("Going out", pdfDoc);
        goingoutLayer.setOn(false);
        cultureLayer.addChild(goingoutLayer);
        PdfLayer restoLayer = new PdfLayer("Restaurants", pdfDoc);
        restoLayer.setOn(false);
        goingoutLayer.addChild(restoLayer);
        PdfLayer theatreLayer = new PdfLayer("(Movie) Theatres", pdfDoc);
        theatreLayer.setOn(false);
        goingoutLayer.addChild(theatreLayer);
        PdfLayer monumentLayer = new PdfLayer("Museums and Monuments", pdfDoc);
        monumentLayer.setOn(false);
        cultureLayer.addChild(monumentLayer);
        PdfLayer sportsLayer = new PdfLayer("Sports", pdfDoc);
        sportsLayer.setOn(false);
        cultureLayer.addChild(sportsLayer);
        // DRAW INFO
        PdfFont font = PdfFontFactory.createFont("./src/test/resources/font/FreeSans.ttf", PdfEncodings.WINANSI, true);
        canvas.saveState();
        canvas.beginText();
        canvas.setFillColorRgb(0x00, 0x00, 0xFF);
        canvas.setFontAndSize(font, 36);
        canvas.beginLayer(cityInfoLayer);

        Canvas canvasModel;
        canvasModel = new Canvas(canvas, pdfDoc, pdfDoc.getDefaultPageSize());

        canvasModel.showTextAligned(String.valueOf((char) 0x69), 2700, 3100, Property.TextAlignment.CENTER);
        canvasModel.showTextAligned(String.valueOf((char) 0x69), 3000, 2050, Property.TextAlignment.CENTER);
        canvasModel.showTextAligned(String.valueOf((char) 0x69), 3100, 2550, Property.TextAlignment.CENTER);

        canvas.beginLayer(hotelLayer);
        canvasModel.showTextAligned(String.valueOf((char) 0xe3), 2000, 1900, Property.TextAlignment.CENTER);
        canvasModel.showTextAligned(String.valueOf((char) 0xe3), 2100, 1950, Property.TextAlignment.CENTER);
        canvasModel.showTextAligned(String.valueOf((char) 0xe3), 2200, 2200, Property.TextAlignment.CENTER);
        canvasModel.showTextAligned(String.valueOf((char) 0xe3), 2700, 3000, Property.TextAlignment.CENTER);
        canvasModel.showTextAligned(String.valueOf((char) 0xe3), 2750, 3050, Property.TextAlignment.CENTER);
        canvasModel.showTextAligned(String.valueOf((char) 0xe3), 2500, 3500, Property.TextAlignment.CENTER);
        canvasModel.showTextAligned(String.valueOf((char) 0xe3), 2300, 2000, Property.TextAlignment.CENTER);
        canvasModel.showTextAligned(String.valueOf((char) 0xe3), 3250, 2200, Property.TextAlignment.CENTER);
        canvasModel.showTextAligned(String.valueOf((char) 0xe3), 3300, 2300, Property.TextAlignment.CENTER);
        canvasModel.showTextAligned(String.valueOf((char) 0xe3), 3400, 3050, Property.TextAlignment.CENTER);
        canvasModel.showTextAligned(String.valueOf((char) 0xe3), 3250, 3200, Property.TextAlignment.CENTER);
        canvasModel.showTextAligned(String.valueOf((char) 0xe3), 2750, 3800, Property.TextAlignment.CENTER);
        canvasModel.showTextAligned(String.valueOf((char) 0xe3), 2900, 3800, Property.TextAlignment.CENTER);
        canvasModel.showTextAligned(String.valueOf((char) 0xe3), 3000, 2400, Property.TextAlignment.CENTER);
        canvasModel.showTextAligned(String.valueOf((char) 0xe3), 2000, 2800, Property.TextAlignment.CENTER);
        canvasModel.showTextAligned(String.valueOf((char) 0xe3), 2600, 3200, Property.TextAlignment.CENTER);
        canvas.endLayer(); // hotelLayer

        canvas.beginLayer(parkingLayer);
        canvasModel.showTextAligned(String.valueOf((char) 0xe8), 2400, 2000, Property.TextAlignment.CENTER);
        canvasModel.showTextAligned(String.valueOf((char) 0xe8), 2100, 2600, Property.TextAlignment.CENTER);
        canvasModel.showTextAligned(String.valueOf((char) 0xe8), 2330, 2250, Property.TextAlignment.CENTER);
        canvasModel.showTextAligned(String.valueOf((char) 0xe8), 3000, 3900, Property.TextAlignment.CENTER);
        canvas.endLayer(); // parkingLayer

        canvas.beginLayer(businessLayer);
        canvas.setFillColorRgb(0xC0, 0xC0, 0xC0);
        canvasModel.showTextAligned(String.valueOf((char) 0x46), 3050, 3600, Property.TextAlignment.CENTER);
        canvasModel.showTextAligned(String.valueOf((char) 0x46), 3200, 3900, Property.TextAlignment.CENTER);
        canvasModel.showTextAligned(String.valueOf((char) 0x46), 3150, 3700, Property.TextAlignment.CENTER);
        canvasModel.showTextAligned(String.valueOf((char) 0x46), 3260, 3610, Property.TextAlignment.CENTER);
        canvasModel.showTextAligned(String.valueOf((char) 0x46), 3350, 3750, Property.TextAlignment.CENTER);
        canvasModel.showTextAligned(String.valueOf((char) 0x46), 3500, 4000, Property.TextAlignment.CENTER);
        canvasModel.showTextAligned(String.valueOf((char) 0x46), 3500, 3800, Property.TextAlignment.CENTER);
        canvasModel.showTextAligned(String.valueOf((char) 0x46), 3450, 3700, Property.TextAlignment.CENTER);
        canvasModel.showTextAligned(String.valueOf((char) 0x46), 3450, 3600, Property.TextAlignment.CENTER);
        canvas.endLayer(); // businessLayer

        canvas.endLayer(); // cityInfoLayer

        canvas.beginLayer(goingoutLayer);

        canvas.beginLayer(restoLayer);
        canvas.setFillColorRgb(0xFF, 0x14, 0x93);
        canvasModel.showTextAligned(String.valueOf((char) 0xe4), 2650, 3500, Property.TextAlignment.CENTER);
        canvasModel.showTextAligned(String.valueOf((char) 0xe4), 2400, 1900, Property.TextAlignment.CENTER);
        canvasModel.showTextAligned(String.valueOf((char) 0xe4), 2750, 3850, Property.TextAlignment.CENTER);
        canvasModel.showTextAligned(String.valueOf((char) 0xe4), 2700, 3200, Property.TextAlignment.CENTER);
        canvasModel.showTextAligned(String.valueOf((char) 0xe4), 2900, 3100, Property.TextAlignment.CENTER);
        canvasModel.showTextAligned(String.valueOf((char) 0xe4), 2850, 3000, Property.TextAlignment.CENTER);
        canvasModel.showTextAligned(String.valueOf((char) 0xe4), 2800, 2900, Property.TextAlignment.CENTER);
        canvasModel.showTextAligned(String.valueOf((char) 0xe4), 2300, 2900, Property.TextAlignment.CENTER);
        canvasModel.showTextAligned(String.valueOf((char) 0xe4), 1950, 2650, Property.TextAlignment.CENTER);
        canvasModel.showTextAligned(String.valueOf((char) 0xe4), 1800, 2750, Property.TextAlignment.CENTER);
        canvasModel.showTextAligned(String.valueOf((char) 0xe4), 3350, 3150, Property.TextAlignment.CENTER);
        canvasModel.showTextAligned(String.valueOf((char) 0xe4), 3400, 3100, Property.TextAlignment.CENTER);
        canvasModel.showTextAligned(String.valueOf((char) 0xe4), 3250, 3450, Property.TextAlignment.CENTER);
        canvas.endLayer(); // restoLayer

        canvas.beginLayer(theatreLayer);
        canvas.setFillColorRgb(0xDC, 0x14, 0x3C);
        canvasModel.showTextAligned(String.valueOf((char) 0xae), 2850, 3300, Property.TextAlignment.CENTER);
        canvasModel.showTextAligned(String.valueOf((char) 0xae), 3050, 2900, Property.TextAlignment.CENTER);
        canvasModel.showTextAligned(String.valueOf((char) 0xae), 2650, 2900, Property.TextAlignment.CENTER);
        canvasModel.showTextAligned(String.valueOf((char) 0xae), 2750, 2600, Property.TextAlignment.CENTER);
        canvasModel.showTextAligned(String.valueOf((char) 0xB8), 2800, 3350, Property.TextAlignment.CENTER);
        canvasModel.showTextAligned(String.valueOf((char) 0xB8), 2550, 2850, Property.TextAlignment.CENTER);
        canvasModel.showTextAligned(String.valueOf((char) 0xB8), 2850, 3300, Property.TextAlignment.CENTER);
        canvas.endLayer(); // theatreLayer

        canvas.endLayer(); // goingoutLayer

        canvas.beginLayer(monumentLayer);
        canvas.setFillColorRgb(0x00, 0x00, 0x00);
        canvasModel.showTextAligned(String.valueOf((char) 0x47), 3250, 2750, Property.TextAlignment.CENTER);
        canvasModel.showTextAligned(String.valueOf((char) 0x47), 2750, 2900, Property.TextAlignment.CENTER);
        canvasModel.showTextAligned(String.valueOf((char) 0x47), 2850, 3500, Property.TextAlignment.CENTER);
        canvasModel.showTextAligned(String.valueOf((char) 0xad), 2150, 3550, Property.TextAlignment.CENTER);
        canvasModel.showTextAligned(String.valueOf((char) 0xad), 3300, 2730, Property.TextAlignment.CENTER);
        canvasModel.showTextAligned(String.valueOf((char) 0xad), 2200, 2000, Property.TextAlignment.CENTER);
        canvasModel.showTextAligned(String.valueOf((char) 0xad), 2900, 3300, Property.TextAlignment.CENTER);
        canvasModel.showTextAligned(String.valueOf((char) 0xad), 2080, 3000, Property.TextAlignment.CENTER);
        canvas.endLayer(); // monumentLayer

        canvas.beginLayer(sportsLayer);
        canvasModel.showTextAligned(String.valueOf((char) 0x53), 2700, 4050, Property.TextAlignment.CENTER);
        canvasModel.showTextAligned(String.valueOf((char) 0x53), 2700, 3900, Property.TextAlignment.CENTER);
        canvasModel.showTextAligned(String.valueOf((char) 0x53), 2800, 3980, Property.TextAlignment.CENTER);
        canvasModel.showTextAligned(String.valueOf((char) 0x53), 1950, 2800, Property.TextAlignment.CENTER);
        canvasModel.showTextAligned(String.valueOf((char) 0x53), 3700, 2450, Property.TextAlignment.CENTER);
        canvas.endLayer(); // sportsLayer

        canvas.endText();
        canvas.restoreState();

        pdfDoc.close();
    }
}
