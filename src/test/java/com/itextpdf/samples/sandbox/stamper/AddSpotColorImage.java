/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

package com.itextpdf.samples.sandbox.stamper;

import com.itextpdf.io.image.ImageFactory;
import com.itextpdf.kernel.color.DeviceCmyk;
import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfNumber;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfStream;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.element.Image;
import com.itextpdf.samples.GenericTest;
import com.itextpdf.test.annotations.type.SampleTest;

import java.io.File;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class AddSpotColorImage extends GenericTest {
    public static final String SRC = "./src/test/resources/sandbox/stamper/image.pdf";
    public static final String DEST = "./target/test/resources/sandbox/stamper/add_spot_color_image.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new AddSpotColorImage().manipulatePdf(DEST);
    }

    public PdfArray getSeparationColorspace(PdfWriter writer, DeviceCmyk cmyk) {
        PdfArray array = new PdfArray(PdfName.Separation);
        array.add(new PdfName("mySpotColor"));
        array.add(PdfName.DeviceCMYK);
        PdfDictionary func = new PdfDictionary();
        func.put(PdfName.FunctionType, new PdfNumber(2));
        func.put(PdfName.Domain, new PdfArray(new float[]{0, 1}));
        func.put(PdfName.C0, new PdfArray(new float[]{0, 0, 0, 0}));
        // magic numbers are from itext5
        func.put(PdfName.C1, new PdfArray(cmyk.getColorValue()));
        func.put(PdfName.N, new PdfNumber(1));
        array.add(func);
        return array;
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(SRC), new PdfWriter(DEST));

        // suppose that this is our image data
        byte circleData[] = {(byte) 0x3c, (byte) 0x7e, (byte) 0xff,
                (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0x7e,
                (byte) 0x3c};
        // we have an image of 8 by 8 with 1 component and 1 bit per component
        // the 1 value is colored, the 0 value is transparent
        Image image = new Image(ImageFactory.getImage(8, 8, 1, 1, circleData, new int[]{0, 0}));
        // By default, the colorspace of such an image is DeviceGray
        // In other words: the image is in black and white
        // We want to use a separation colorspace:
        PdfArray colorspace = getSeparationColorspace(pdfDoc.getWriter(), new DeviceCmyk(0.8f, 0.3f, 0.3f, 0.1f));
        // We get the image as a stream object
        // TODO No PdfImage
        PdfStream stream = new PdfStream(image.getXObject().getPdfObject().getBytes());
//        PdfStream tempStream = new PdfStream(stream.getBytes()).makeIndirect(pdfDoc);
//        PdfIndirectReference ref = tempStream.getIndirectReference();
//        tempStream.flush();
//        stream.makeIndirect(pdfDoc, ref);
        // and we change its color space:
        stream.put(PdfName.ColorSpace, colorspace);
        stream.flush();
        // We add the stream to the writer
        // TODO Implement addToBody(PdfStream)
        // PdfObject ref = pdfDoc.addToBody(stream);
        // We adapt the original image
        // TODO Implement the way of smth like setDirectReference(reference)
        // image.setDirectReference(ref.getIndirectReference());
        image.scaleAbsolute(100, 100);
        image.setFixedPosition(100, 200);


        /**
         *         // TODO Implement addToBody(PdfStream)
         //
         //PdfStream stream = new PdfStream(baos.toByteArray()).makeIndirect(pdfDoc);
         //PdfIndirectReference ref = stream.getIndirectReference();
         //stream.flush();
         // PdfIndirectObject ref = pdfDoc.getWriter().addToBody(new PdfStream(baos.toByteArray()));
         */


        // Now we add the image to the existing PDF document
        PdfCanvas canvas = new PdfCanvas(pdfDoc.getFirstPage());
        canvas.addXObject(image.getXObject(), 100, 200, 100);

        pdfDoc.close();
    }
}
