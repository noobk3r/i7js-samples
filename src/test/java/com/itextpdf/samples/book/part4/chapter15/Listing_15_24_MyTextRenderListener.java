package com.itextpdf.samples.book.part4.chapter15;

import com.itextpdf.core.parser.EventData;
import com.itextpdf.core.parser.EventListener;
import com.itextpdf.core.parser.EventType;
import com.itextpdf.core.parser.TextRenderInfo;

import java.io.PrintWriter;
import java.util.Set;

public class Listing_15_24_MyTextRenderListener implements EventListener {
    /** The print writer to which the information will be written. */
    protected PrintWriter out;

    /**
     * Creates a RenderListener that will look for text.
     */
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