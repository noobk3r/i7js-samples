package com.itextpdf.samples.sandbox.stamper;

import com.itextpdf.basics.image.ImageFactory;
import com.itextpdf.canvas.PdfCanvas;
import com.itextpdf.canvas.color.DeviceCmyk;
import com.itextpdf.core.pdf.*;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.element.Image;
import com.itextpdf.samples.GenericTest;
import org.junit.Ignore;
import org.junit.experimental.categories.Category;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

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

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(
                new FileInputStream(SRC)), new PdfWriter(new FileOutputStream(DEST)));

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
}
