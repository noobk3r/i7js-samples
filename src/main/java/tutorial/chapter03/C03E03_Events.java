package tutorial.chapter03;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Property;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Yanina on 16.03.2016.
 */
public class C03E03_Events {

    public static final String DATA = "src/main/resources/data/united_states.csv";
    public static final String DEST = "results/chapter03/events.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new C03E03_Events().createPdf(DEST);
    }

    public void process(Table table, String line, PdfFont font, boolean isHeader) {
        StringTokenizer tokenizer = new StringTokenizer(line, ";");
        int c = 0;
        while (tokenizer.hasMoreTokens() && c++ < 3) {
            if (isHeader) {
                table.addHeaderCell(new Cell().add(new Paragraph(tokenizer.nextToken()).setFont(font)));
            } else {
                table.addCell(new Cell().add(new Paragraph(tokenizer.nextToken()).setFont(font)));
            }
        }
    }

    protected void createPdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(new FileOutputStream(DEST)));
        Document doc = new Document(pdfDoc);
        pdfDoc.addEventHandler(PdfDocumentEvent.END_PAGE, new WatermarkingEventHandler());

        PdfFont font = PdfFontFactory.createFont(FontConstants.HELVETICA);
        PdfFont bold = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD);

        Table table = new Table(new float[]{4, 1, 3});
        table.setWidthPercent(100);

        BufferedReader br = new BufferedReader(new FileReader(DATA));
        String line = br.readLine();
        process(table, line, bold, true);
        while ((line = br.readLine()) != null) {
            process(table, line, font, false);
        }
        br.close();

        doc.add(table);

        doc.close();
    }


    protected class WatermarkingEventHandler implements IEventHandler {
        @Override
        public void handleEvent(Event event) {
            PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
            PdfDocument pdfDoc = docEvent.getDocument();
            PdfPage page = docEvent.getPage();
            PdfFont font = null;
            try {
                font = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD);
            } catch (IOException e) {
                e.printStackTrace();
            }
            PdfCanvas canvas = new PdfCanvas(page.newContentStreamBefore(), page.getResources(), pdfDoc);
            new Canvas(canvas, pdfDoc, page.getPageSize())
                    .setProperty(Property.FONT_COLOR, Color.LIGHT_GRAY)
                    .setProperty(Property.FONT_SIZE, 60)
                    .setProperty(Property.FONT, font)
                    .showTextAligned(new Paragraph("WATERMARK"), 298, 421, pdfDoc.getPageNumber(page),
                            Property.TextAlignment.CENTER, Property.VerticalAlignment.MIDDLE, 45);
        }
    }
}
