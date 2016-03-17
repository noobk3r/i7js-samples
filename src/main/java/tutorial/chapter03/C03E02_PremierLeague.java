package tutorial.chapter03;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.image.ImageFactory;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.color.DeviceCmyk;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Property;
import com.itextpdf.layout.border.*;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.layout.LayoutArea;
import com.itextpdf.layout.renderer.CellRenderer;
import com.itextpdf.layout.renderer.DrawContext;
import com.itextpdf.layout.renderer.ParagraphRenderer;
import com.itextpdf.layout.renderer.TableRenderer;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Simple columns example.
 */
public class C03E02_PremierLeague {

    public static final String DATA = "src/main/resources/data/premier_league.csv";
    public static final String DEST = "results/chapter03/premier_league.pdf";

    Color greenColor = new DeviceCmyk(0.78f, 0, 0.81f, 0.21f);
    Color yellowColor = new DeviceCmyk(0, 0, 0.76f, 0.01f);
    Color redColor = new DeviceCmyk(0, 0.76f, 0.86f, 0.01f);
    Color blueColor = new DeviceCmyk(0.28f, 0.11f, 0, 0);

    public static void main(String args[]) throws IOException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new C03E02_PremierLeague().createPdf(DEST);
    }

    public void createPdf(String dest) throws IOException {

        //Initialize PDF writer
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);

        //Initialize PDF document
        PdfDocument pdf = new PdfDocument(writer);
        PageSize ps = PageSize.A4.rotate();

        // Initialize document
        Document document = new Document(pdf, ps);

        /*Table table = new Table(4);
        for(int aw = 0; aw < 16; aw++){
            table.addCell(new Cell().add("hi")).setBorder(Border.NO_BORDER);
        }*/

        PdfFont font = PdfFontFactory.createFont(FontConstants.HELVETICA);
        PdfFont bold = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD);
        Table table = new Table(new float[]{1.5f, 7, 2, 2, 2, 2, 3, 4, 4, 2});
        table.setWidthPercent(100)
            .setTextAlignment(Property.TextAlignment.CENTER)
            .setHorizontalAlignment(Property.HorizontalAlignment.CENTER)
            .setVerticalAlignment(Property.VerticalAlignment.MIDDLE);

        BufferedReader br = new BufferedReader(new FileReader(DATA));
        String line = br.readLine();
        process(table, line, bold, true);
        while ((line = br.readLine()) != null) {
            process(table, line, font, false);
        }
        br.close();

        //table.setNextRenderer(new TableBorderRenderer(table));
        document.add(table);

        //Close document
        document.close();

    }

    public void process(Table table, String line, PdfFont font, boolean isHeader) {
        StringTokenizer tokenizer = new StringTokenizer(line, ";");
        int columnNumber = 0;
        while (tokenizer.hasMoreTokens()) {
            if (isHeader) {
                Cell cell = getCell(tokenizer.nextToken());
                table.addHeaderCell(cell);
            } else {
                columnNumber++;
                Cell cell = new Cell().add(new Paragraph(tokenizer.nextToken()).
                        setFont(font))
                        .setBorder(new DottedBorder(Color.BLACK, 1));
                if (columnNumber == 4)
                    cell.setBackgroundColor(greenColor);
                else if (columnNumber == 5)
                    cell.setBackgroundColor(yellowColor);
                else if (columnNumber == 6)
                    cell.setBackgroundColor(redColor);
                else cell.setBackgroundColor(blueColor);
                table.addCell(cell);
            }
        }
    }

    class TableBorderRenderer extends TableRenderer {
        public TableBorderRenderer(Table modelElement) {
            super(modelElement);
        }

        @Override
        protected void drawBorders(PdfCanvas canvas) {
            Rectangle rect = getOccupiedAreaBBox();
            canvas
                    .saveState()
                    .rectangle(rect.getLeft(), rect.getBottom(), rect.getWidth(), rect.getHeight())
                    .stroke()
                    .restoreState();
        }
    }

    public Cell getCell(String content) {
        Cell cell = new Cell().add(new Paragraph(content));
        cell.setNextRenderer(new RoundedCornersCellRenderer(cell));
        cell.setPadding(5);
        cell.setBorder(null);
        return cell;
    }


    private class RoundedCornersCellRenderer extends CellRenderer {
        public RoundedCornersCellRenderer(Cell modelElement) {
            super(modelElement);
        }

        @Override
        public void draw(DrawContext drawContext) {
            float llx = getOccupiedAreaBBox().getX() + 1;
            float lly = getOccupiedAreaBBox().getY() + 1;
            float urx = getOccupiedAreaBBox().getX() + getOccupiedAreaBBox().getWidth() - 1;
            float ury = getOccupiedAreaBBox().getY() + getOccupiedAreaBBox().getHeight() - 1;
            float r = 4;
            float b = 0.4477f;
            PdfCanvas canvas = drawContext.getCanvas();
            canvas.moveTo(llx, lly);
            canvas.lineTo(urx, lly);
            canvas.lineTo(urx, ury - r);
            canvas.curveTo(urx, ury - r * b, urx - r * b, ury, urx - r, ury);
            canvas.lineTo(llx + r, ury);
            canvas.curveTo(llx + r * b, ury, llx, ury - r * b, llx, ury - r);
            canvas.lineTo(llx, lly);
            canvas.stroke();
            super.draw(drawContext);
        }
    }

}