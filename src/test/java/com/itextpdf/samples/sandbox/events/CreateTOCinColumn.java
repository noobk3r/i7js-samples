/**
 * Example written by Bruno Lowagie in answer to:
 * http://stackoverflow.com/questions/29092738/itext-chapter-title-and-columntext
 */

package com.itextpdf.samples.sandbox.events;

import com.itextpdf.basics.geom.Rectangle;
import com.itextpdf.core.pdf.PdfArray;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfName;
import com.itextpdf.core.pdf.PdfNumber;
import com.itextpdf.core.pdf.PdfOutline;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.pdf.navigation.PdfDestination;
import com.itextpdf.core.pdf.navigation.PdfExplicitDestination;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.AreaBreak;
import com.itextpdf.model.element.Cell;
import com.itextpdf.model.element.Link;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Table;
import com.itextpdf.model.element.Text;
import com.itextpdf.model.layout.LayoutArea;
import com.itextpdf.model.layout.LayoutResult;
import com.itextpdf.model.renderer.DocumentRenderer;
import com.itextpdf.model.renderer.DrawContext;
import com.itextpdf.model.renderer.TextRenderer;
import com.itextpdf.samples.GenericTest;

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
        doc.setRenderer(new ColumnDocumentRenderer(doc));
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


    protected class ColumnDocumentRenderer extends DocumentRenderer {
        protected int nextAreaNumber = 0;
        protected int currentPageNumber;

        public ColumnDocumentRenderer(Document document) {
            super(document);
        }

        @Override
        public LayoutArea getNextArea(LayoutResult overflowResult) {
            if (nextAreaNumber % 3 == 0) {
                currentPageNumber = super.getNextArea(overflowResult).getPageNumber();
                nextAreaNumber++;
                return (currentArea = new LayoutArea(currentPageNumber, new Rectangle(36, 36, 173, 770)));
            } else if (nextAreaNumber % 3 == 1) {
                nextAreaNumber++;
                return (currentArea = new LayoutArea(currentPageNumber, new Rectangle(213, 36, 173, 770)));
            } else {
                nextAreaNumber++;
                return (currentArea = new LayoutArea(currentPageNumber, new Rectangle(389, 36, 173, 770)));
            }
        }
    }
}

