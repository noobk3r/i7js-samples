package com.itextpdf.samples.book.part2.chapter07;

import com.itextpdf.basics.font.FontConstants;
import com.itextpdf.basics.geom.Rectangle;
import com.itextpdf.canvas.PdfCanvas;
import com.itextpdf.core.font.PdfFont;
import com.itextpdf.core.pdf.PdfArray;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfName;
import com.itextpdf.core.pdf.PdfPage;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.pdf.action.PdfAction;
import com.itextpdf.core.pdf.annot.PdfLinkAnnotation;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Canvas;
import com.itextpdf.model.Document;
import com.itextpdf.model.Property;
import com.itextpdf.model.border.Border;
import com.itextpdf.model.element.Cell;
import com.itextpdf.model.element.Link;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Table;
import com.itextpdf.model.layout.LayoutPosition;
import com.itextpdf.model.renderer.TextRenderer;
import com.itextpdf.samples.GenericTest;
import com.itextpdf.samples.book.part1.chapter03.Listing_03_29_MovieTemplates;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class Listing_07_01_NamedActions extends GenericTest {
    public static final String DEST = "./target/test/resources/book/part2/chapter07/Listing_07_01_NamedActions.pdf";

    public static void main(String args[]) throws IOException, SQLException {
        new Listing_03_29_MovieTemplates().manipulatePdf(Listing_03_29_MovieTemplates.DEST);
        new Listing_07_01_NamedActions().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(Listing_03_29_MovieTemplates.DEST),
                new PdfWriter(DEST));
        Document doc = new Document(pdfDoc);

        //Initialize writer
        PdfFont symbol = PdfFont.createStandardFont(pdfDoc, FontConstants.SYMBOL); // 20

        Table table = new Table(4);
        table.setWidth(120);
        // TODO Add facility to set default cell properties
        PdfAction action;

        action = new PdfAction(pdfDoc);
        action.put(PdfName.S, PdfName.Named);
        action.put(PdfName.N, PdfName.FirstPage);
        Link first = new Link(String.valueOf((char) 220), action);
        first.setFont(symbol);
        table.addCell(new Cell()
                .add(new Paragraph().add(first))
                .setBorder(Border.NO_BORDER)
                .setHorizontalAlignment(Property.HorizontalAlignment.CENTER));

        action = new PdfAction(pdfDoc);
        action.put(PdfName.S, PdfName.Named);
        action.put(PdfName.N, PdfName.PrevPage);
        Link previous = new Link(String.valueOf((char) 172), action);
        previous.setFont(symbol);
        table.addCell(new Cell()
                .add(new Paragraph().add(previous))
                .setBorder(Border.NO_BORDER)
                .setHorizontalAlignment(Property.HorizontalAlignment.CENTER));

        action = new PdfAction(pdfDoc);
        action.put(PdfName.S, PdfName.Named);
        action.put(PdfName.N, PdfName.NextPage);
        Link next = new Link(String.valueOf((char) 174), action);
        next.setFont(symbol);
        table.addCell(new Cell()
                .add(new Paragraph().add(next))
                .setBorder(Border.NO_BORDER)
                .setHorizontalAlignment(Property.HorizontalAlignment.CENTER));

        action = new PdfAction(pdfDoc);
        action.put(PdfName.S, PdfName.Named);
        action.put(PdfName.N, PdfName.LastPage);
        Link last = new Link(String.valueOf((char) 222), action);
        last.setFont(symbol);
        table.addCell(new Cell()
                .add(new Paragraph().add(last))
                .setBorder(Border.NO_BORDER)
                .setHorizontalAlignment(Property.HorizontalAlignment.CENTER));

        // Add the table to each page
        PdfCanvas canvas;
        for (int i = 1; i <= pdfDoc.getNumOfPages(); i++) {
            first.setNextRenderer(new PagedLinkRenderer(first, i));
            previous.setNextRenderer(new PagedLinkRenderer(previous, i));
            next.setNextRenderer(new PagedLinkRenderer(next, i));
            last.setNextRenderer(new PagedLinkRenderer(last, i));
            canvas = new PdfCanvas(pdfDoc.getPage(i));
            new Canvas(canvas, pdfDoc, new Rectangle(696, 0, 200, 40)).add(table);
        }
        doc.close();
    }


    class PagedLinkRenderer extends TextRenderer {
        protected int pageNum;

        public PagedLinkRenderer(Link link, int page) {
            super(link, link.getText());
            this.pageNum = page;
        }

        @Override
        public void draw(PdfDocument document, PdfCanvas canvas) {
            super.draw(document, canvas);

            int position = getPropertyAsInteger(Property.POSITION);
            if (position == LayoutPosition.RELATIVE) {
                applyAbsolutePositioningTranslation(false);
            }

            PdfLinkAnnotation linkAnnotation = ((Link) modelElement).getLinkAnnotation();
            linkAnnotation.setRectangle(new PdfArray(occupiedArea.getBBox()));

            if (position == LayoutPosition.RELATIVE) {
                applyAbsolutePositioningTranslation(true);
            }

            PdfPage page = document.getPage(pageNum);
            page.addAnnotation(linkAnnotation);
        }
    }
}
