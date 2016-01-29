/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

package com.itextpdf.samples.book.part4.chapter15;

import com.itextpdf.kernel.parser.EventData;
import com.itextpdf.kernel.parser.EventListener;
import com.itextpdf.kernel.parser.EventType;
import com.itextpdf.kernel.parser.TextRenderInfo;

import java.io.PrintWriter;
import java.util.Set;

public class Listing_15_24_MyTextRenderListener implements EventListener {
    protected PrintWriter out;

    public Listing_15_24_MyTextRenderListener(PrintWriter out) {
        this.out = out;
    }

    public void eventOccurred(EventData data, EventType type) {
        switch (type) {
            case BEGIN_TEXT:
                out.print("<");
                break;

            case RENDER_TEXT:
                TextRenderInfo renderInfo = (TextRenderInfo) data;
                out.print("<");
                out.print(renderInfo.getText());
                out.print(">");
                break;

            case END_TEXT:
                out.println(">");
                break;
            default:
                break;
        }
    }

    public Set<EventType> getSupportedEvents() {
        return null;
    }
}