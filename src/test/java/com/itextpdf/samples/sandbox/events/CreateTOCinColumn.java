/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

/**
 * Example written by Bruno Lowagie in answer to:
 * http://stackoverflow.com/questions/29092738/itext-chapter-title-and-columntext
 */

package com.itextpdf.samples.sandbox.events;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfNumber;
import com.itextpdf.kernel.pdf.PdfOutline;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.navigation.PdfDestination;
import com.itextpdf.kernel.pdf.navigation.PdfExplicitDestination;
import com.itextpdf.layout.ColumnDocumentRenderer;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Link;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.renderer.DrawContext;
import com.itextpdf.layout.renderer.TextRenderer;
import com.itextpdf.samples.GenericTest;
import com.itextpdf.test.annotations.type.SampleTest;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class CreateTOCinColumn extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/events/create_toc_in_column.pdf";

    protected List<TOCEntry> list = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        new CreateTOCinColumn().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(new FileOutputStream(DEST)));
        Document doc = new Document(pdfDoc);
        Rectangle[] columns = {new Rectangle(36, 36, 173, 770), new Rectangle(213, 36, 173, 770), new Rectangle(389, 36, 173, 770)};
        doc.setRenderer(new ColumnDocumentRenderer(doc, columns));
        PdfOutline root = pdfDoc.getOutlines(false);
        if (root == null)
            root = new PdfOutline(pdfDoc);
        int start;
        int end;
        for (int i = 0; i <= 20; ) {
            start = (i * 10) + 1;
            i++;
            end = i * 10;
            String title = String.format("Numbers from %s to %s", start, end);
            Text c = new Text(title);
            // TODO Implement Anchors or summat because currently a click on outline transfers you only to the needed page not the particular paragraph
            TOCTextRenderer renderer = new TOCTextRenderer(c);
            renderer.setRoot(root);
            c.setNextRenderer(renderer);
            doc.add(new Paragraph(c));
            doc.add(createTable(start, end));
        }
        doc.add(new AreaBreak());
        for (TOCEntry entry : list) {
            Link c = new Link(entry.title, entry.dest);
            doc.add(new Paragraph(c));
        }
        doc.close();
    }

    protected Table createTable(int start, int end) {
        Table table = new Table(2);
        for (int i = start; i <= end; i++) {
            table.addCell(new Cell().add(new Paragraph(String.valueOf(i))));
            table.addCell(new Cell().add(new Paragraph("Test")));
        }
        return table;
    }

    protected class TOCEntry {
        protected String title;
        protected PdfDestination dest;

        public TOCEntry(String title, PdfArray array) {
            this.dest = new PdfExplicitDestination(array);
            this.title = title;
        }
    }

    protected class TOCTextRenderer extends TextRenderer {
        protected PdfOutline root;

        public TOCTextRenderer(Text modelElement) {
            super(modelElement);
        }

        public void setRoot(PdfOutline root) {
            this.root = root;
        }

        @Override
        public void draw(DrawContext drawContext) {
            super.draw(drawContext);
            Rectangle rect = getOccupiedAreaBBox();
            PdfArray array = new PdfArray();
            array.add(drawContext.getDocument().getLastPage().getPdfObject());
            array.add(PdfName.XYZ);
            array.add(new PdfNumber(rect.getLeft()));
            array.add(new PdfNumber(rect.getTop()));
            array.add(new PdfNumber(0));
            PdfOutline curOutline = root.addOutline(((Text) modelElement).getText());
            PdfDestination dest = PdfDestination.makeDestination(array);
            list.add(new TOCEntry(((Text) modelElement).getText(), array));
            curOutline.addDestination(dest);
        }
    }

}

