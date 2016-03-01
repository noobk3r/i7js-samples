/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

package com.itextpdf.samples.book.part4.chapter15;

import com.itextpdf.kernel.PdfException;
import com.itextpdf.io.image.ImageFactory;
import com.itextpdf.kernel.parser.EventData;
import com.itextpdf.kernel.parser.EventListener;
import com.itextpdf.kernel.parser.EventType;
import com.itextpdf.kernel.parser.ImageRenderInfo;
import com.itextpdf.kernel.pdf.xobject.PdfImageXObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Set;

public class Listing_15_31_MyImageRenderListener implements EventListener {
    protected String path;
    // TODO Change when the sample will be revised
    protected String extension = "jpg";
    protected int i = 1;

    public Listing_15_31_MyImageRenderListener(String path) {
        this.path = path;
    }

    public void eventOccurred(EventData data, EventType type) {
        switch (type) {
            case RENDER_IMAGE:
                try {
                    String filename;
                    FileOutputStream os;
                    ImageRenderInfo renderInfo = (ImageRenderInfo) data;
                    PdfImageXObject image = renderInfo.getImage();
                    if (image == null)  {
                        return;
                    }
                    // TODO No possibility to find Image extension in this particular situation(DEVSIX-484)
                    try {
                        System.out.println(ImageFactory.getImage(image.getImageBytes(true)).getOriginalType());
                    }
                    catch (PdfException e) {
                        System.out.println("bad");
                        break;
                    }
                    filename = String.format(path, i++, extension);
                    os = new FileOutputStream(filename);
                    os.write(image.getPdfObject().getBytes());
                    os.flush();
                    os.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
                break;

            default:
                break;
        }
    }

    public Set<EventType> getSupportedEvents() {
        return null;
    }
}
