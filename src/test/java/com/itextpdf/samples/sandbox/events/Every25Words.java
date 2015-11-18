/**
 * Example written by Bruno Lowagie in answer to:
 * http://stackoverflow.com/questions/28709603/draw-a-line-every-n-words-using-itextsharp
 */
package com.itextpdf.samples.sandbox.events;

import com.itextpdf.basics.geom.Rectangle;
import com.itextpdf.canvas.PdfCanvas;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Text;
import com.itextpdf.model.renderer.TextRenderer;
import com.itextpdf.samples.GenericTest;

import java.io.*;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class Every25Words extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/events/every25words.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new Every25Words().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(new FileOutputStream(DEST)));
        Document doc = new Document(pdfDoc);
        // writer.setInitialLeading(16);
        String[] words = readFile().split("\\s+");
        Paragraph paragraph = new Paragraph();
        Text chunk = null;
        int i = 0;
        for (String word : words) {
            if (chunk != null) {
                paragraph.add(new Text(" "));
            }
            chunk = new Text(word);
            chunk.setNextRenderer(new Word25TextRenderer(chunk, ++i));
            paragraph.add(chunk);
        }
        doc.add(paragraph);
        pdfDoc.close();
    }


    public String readFile() throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader in = new BufferedReader(new InputStreamReader(
                new FileInputStream("./src/test/resources/sandbox/events/liber1_1_la.txt"), "UTF8"));
        String str;
        while ((str = in.readLine()) != null) {
            sb.append(str);
        }
        return sb.toString();
    }


    private class Word25TextRenderer extends TextRenderer {
        private int count = 0;

        public Word25TextRenderer(Text textElement, int count) {
            super(textElement);
            this.count = count;
        }

        @Override
        public void draw(PdfDocument document, PdfCanvas canvas) {
            super.draw(document, canvas);
            if (0 == count % 25) {
                Rectangle rect = getOccupiedAreaBBox();
                int pageNumber = getOccupiedArea().getPageNumber();
                canvas.saveState();
                canvas.setLineDash(5, 5);
                canvas.moveTo(document.getPage(pageNumber).getPageSize().getLeft(), rect.getBottom());
                canvas.lineTo(rect.getRight(), rect.getBottom());
                canvas.lineTo(rect.getRight(), rect.getTop());
                canvas.lineTo(document.getDefaultPageSize().getRight(), rect.getTop());
                canvas.stroke();
                canvas.restoreState();
            }
        }
    }
}
