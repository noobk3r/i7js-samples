/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

package com.itextpdf.samples.book.part4.chapter15;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.parser.EventData;
import com.itextpdf.kernel.parser.EventListener;
import com.itextpdf.kernel.parser.EventType;
import com.itextpdf.kernel.parser.PdfContentStreamProcessor;
import com.itextpdf.kernel.parser.TextRenderInfo;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.test.annotations.type.SampleTest;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Set;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;
import org.xml.sax.SAXException;

@Ignore
@Category(SampleTest.class)
public class Listing_15_29_ShowTextMargins {
    public static final String DEST
            = "./samples/target/test/resources/book/part4/chapter15/Listing_15_29_ShowTextMargins.pdf";
    public static final String PREFACE
            = "./samples/src/test/resources/book/part4/chapter15/preface.pdf";

    public static void main(String args[])
            throws IOException, SQLException, ParserConfigurationException, SAXException {
        new Listing_15_29_ShowTextMargins().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest)
            throws IOException, SQLException, ParserConfigurationException, SAXException {
        addMarginRectangle(PREFACE, dest);
    }

    /**
     * Parses a PDF and ads a rectangle showing the text margin.
     *
     * @param src  the source PDF
     * @param dest the resulting PDF
     */
    public void addMarginRectangle(String src, String dest) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(src), new PdfWriter(dest));
        // TODO No predefined extraction strategies (like TextMarginFinder)
        TextMarginFinderListener finder;
        PdfContentStreamProcessor parser;
        for (int i = 1; i <= pdfDoc.getNumberOfPages(); i++) {
            finder  = new TextMarginFinderListener();
            parser = new PdfContentStreamProcessor(finder);
            parser.processPageContent(pdfDoc.getPage(i));
            PdfCanvas cb = new PdfCanvas(pdfDoc.getPage(i));
            cb.rectangle(finder.getLeft(), finder.getBottom(),
                    finder.getWidth(), finder.getHeight());
            cb.stroke();
        }
        pdfDoc.close();
    }


    class TextMarginFinderListener implements EventListener {
        private Rectangle textRectangle = null;

        public Set<EventType> getSupportedEvents() {
            return null;
        }

        public Rectangle union(Rectangle src1, Rectangle src2) {
            float x1 = Math.min(src1.getLeft(), src2.getLeft());
            float y1 = Math.min(src1.getBottom(), src2.getBottom());
            float x2 = Math.max(src1.getTop(), src2.getTop());
            float y2 = Math.max(src1.getTop(), src2.getTop());
            return new Rectangle(x1, y1, x2 - x1, y2 - y1);
        }

        @Override
        public void eventOccurred(EventData data, EventType type) {
            switch (type) {
                case RENDER_TEXT:
                    TextRenderInfo renderInfo = (TextRenderInfo) data;
                    if (textRectangle == null) {
                        textRectangle = renderInfo.getDescentLine().getBoundingRectange();
                    } else {
                        // TODO No sense as we do not the width at that moment
                        textRectangle = union(textRectangle, renderInfo.getDescentLine().getBoundingRectange());
                    }
                    textRectangle = union(textRectangle, renderInfo.getAscentLine().getBoundingRectange());
            }
        }

        public float getLeft() {
            return textRectangle.getLeft();
        }

        public float getBottom() {
            return textRectangle.getBottom();
        }

        public float getWidth() {
            return textRectangle.getWidth();
        }

        public float getHeight() {
            return textRectangle.getHeight();
        }
    }
}
