/**
 * Example written for JavaOne 2016.
 * Differences between iText 5 and iText 7 are discussed in the JavaOne talk
 * "Oops, I broke my API" by Raf Hens and Bruno Lowagie.
 * This is the iText 7 version of one of the examples.
 */
package com.itextpdf.sandbox.javaone16;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.renderer.DrawContext;
import com.itextpdf.layout.renderer.IRenderer;
import com.itextpdf.layout.renderer.ParagraphRenderer;
import com.itextpdf.test.annotations.WrapToTest;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@WrapToTest
public class Text2PdfPageEvents2 {
    public static final String TEXT
        = "src/main/resources/txt/jekyll_hyde.txt";
    public static final String DEST
        = "results/javaone16/text2pdf_page_events2.pdf";

    public static void main(String[] args) throws IOException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
    	new Text2PdfPageEvents2().createPdf(DEST);
    }
    
    public void createPdf(String dest) throws IOException {
        PdfDocument pdf = new PdfDocument(new PdfWriter(dest));
        pdf.addEventHandler(PdfDocumentEvent.END_PAGE, new Footer());
        Document document = new Document(pdf)
            .setTextAlignment(TextAlignment.JUSTIFIED);
        BufferedReader br = new BufferedReader(new FileReader(TEXT));
        String line;
        PdfFont normal = PdfFontFactory.createFont(FontConstants.TIMES_ROMAN);
        boolean title = true;
        Border border = new SolidBorder(Color.BLUE, 1);
        while ((line = br.readLine()) != null) {
            if (title)
                document.add(new TitleParagraph(line));
            else
                document.add(new Paragraph(line).setFont(normal));
            title = line.isEmpty();
        }
        document.close();
    }
    
    protected class Footer implements IEventHandler {
        
        @Override
        public void handleEvent(Event event) {
            PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
            PdfDocument pdf = docEvent.getDocument();
            PdfPage page = docEvent.getPage();
            Rectangle pageSize = page.getPageSize();
            PdfCanvas pdfCanvas = new PdfCanvas(
                page.getLastContentStream(), page.getResources(), pdf);
            Canvas canvas = new Canvas(pdfCanvas, pdf, pageSize);
            float x = (pageSize.getLeft() + pageSize.getRight()) / 2;
            float y = pageSize.getBottom() + 15;
            canvas.showTextAligned(
                String.valueOf(pdf.getPageNumber(page)),
                x, y, TextAlignment.CENTER);
        }
    }
    
    public class TitleParagraph extends Paragraph {

        public TitleParagraph(String line) {
            super(line);
            try {
                setFont(PdfFontFactory.createFont(FontConstants.TIMES_BOLD));
            }
            catch (IOException ioe) {
            }
        }

        @Override
        protected IRenderer makeNewRenderer() {
            return new ParagraphRenderer(this) {
                @Override
                public void drawBorder(DrawContext drawContext) {
                    Rectangle occupiedAreaBBox = getOccupiedAreaBBox();
                    float[] margins = getMargins();
                    Rectangle rectangle = applyMargins(occupiedAreaBBox, margins, false);
                    PdfCanvas canvas = drawContext.getCanvas();
                    canvas.roundRectangle(rectangle.getX() - 1, rectangle.getY() - 1,
                    rectangle.getWidth() + 2, rectangle.getHeight() + 2, 5).stroke();
                }
            };
        }
        
        
    }
}
