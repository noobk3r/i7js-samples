package com.itextpdf.samples.book.part2.chapter07;

import com.itextpdf.basics.geom.Rectangle;
import com.itextpdf.canvas.PdfCanvas;
import com.itextpdf.core.pdf.PdfBoolean;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfName;
import com.itextpdf.core.pdf.PdfString;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.pdf.annot.PdfAnnotation;
import com.itextpdf.core.pdf.annot.PdfTextAnnotation;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Text;
import com.itextpdf.model.renderer.TextRenderer;
import com.itextpdf.samples.GenericTest;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class Listing_07_20_GenericAnnotations extends GenericTest {
    public static final String DEST = "./target/test/resources/book/part2/chapter07/Listing_07_20_GenericAnnotations.pdf";
    /**
     * Possible icons.
     */
    public static final String[] ICONS = {
            "Comment", "Key", "Note", "Help", "NewParagraph", "Paragraph", "Insert"
    };

    protected String[] arguments;

    public static void main(String args[]) throws IOException, SQLException {
        new Listing_07_20_GenericAnnotations().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException, SQLException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(DEST));
        Document doc = new Document(pdfDoc);

        Paragraph p = new Paragraph();
        Text text;
        // TODO No VerticalPosition
//        Text tab = new Text(new VerticalPositionMark());
        for (int i = 0; i < ICONS.length; i++) {
            text = new Text(ICONS[i]);
            text.setNextRenderer(new AnnotatedTextRenderer(text));
            p.add(text);
            p.add(new Text("           "));
        }
        doc.add(p);
        // step 5
        doc.close();
    }


    private class AnnotatedTextRenderer extends TextRenderer {
        protected String text;

        public AnnotatedTextRenderer(Text textElement) {
            super(textElement);
            text = textElement.getText();
        }

        @Override
        public void draw(PdfDocument document, PdfCanvas canvas) {
            super.draw(document, canvas);

            Rectangle rect = getOccupiedAreaBBox();
            PdfAnnotation annotation = new PdfTextAnnotation(document,
                    new Rectangle(
                            rect.getRight() + 10, rect.getBottom(),
                            rect.getWidth() + 20, rect.getHeight()));
            annotation.setTitle(new PdfString("Text annotation"));
            annotation.put(PdfName.Subtype, PdfName.Text);
            annotation.put(PdfName.Open, PdfBoolean.PdfFalse);
            annotation.put(PdfName.Contents,
                    new PdfString(String.format("Icon: %s", text)));
            annotation.put(PdfName.Name, new PdfName(text));
            document.getLastPage().addAnnotation(annotation);
        }
    }
}
