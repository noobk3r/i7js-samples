package com.itextpdf.samples.book.part1.chapter05;

import com.itextpdf.basics.font.PdfEncodings;
import com.itextpdf.basics.geom.PageSize;
import com.itextpdf.basics.geom.Rectangle;
import com.itextpdf.basics.image.ImageFactory;
import com.itextpdf.canvas.PdfCanvas;
import com.itextpdf.core.color.Color;
import com.itextpdf.core.color.DeviceCmyk;
import com.itextpdf.core.font.PdfFont;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.Property;
import com.itextpdf.model.border.Border;
import com.itextpdf.model.element.AreaBreak;
import com.itextpdf.model.element.Cell;
import com.itextpdf.model.element.Image;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Table;
import com.itextpdf.model.element.Text;
import com.itextpdf.model.renderer.CellRenderer;
import com.itextpdf.model.renderer.TableRenderer;
import com.itextpdf.samples.book.part1.chapter04.Listing_04_21_PdfCalendar;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class Listing_05_07_PdfCalendar extends Listing_04_21_PdfCalendar {
    public static final String DEST = "./target/test/resources/book/part1/chapter05/Listing_05_07_PdfCalendar.pdf";

    int[] cmykGreen = new int[]{0xFF, 0x00, 0xFF, 0x00};
    // TODO Cmyk colors do not render correct
    int[] cmykGray = new int[]{0x00, 0x00, 0x00, 0x80};
    int[] cmykWhite = new int[]{0x00, 0x00, 0x00, 0x00};
    int[] cmykYellow = new int[]{0x00, 0x00, 0xFF, 0x0F};

    public static void main(String args[]) throws IOException, SQLException {
        new Listing_05_07_PdfCalendar().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException, SQLException {
        Locale locale = new Locale(LANGUAGE);
        createPdf(dest, locale, YEAR);
    }

    public void createPdf(String dest, Locale locale, int year) throws IOException, SQLException {
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc, new PageSize(PageSize.A4).rotate());

        // fonts
        bold = PdfFont.createFont(pdfDoc, "c://windows/fonts/arialbd.ttf", PdfEncodings.WINANSI, true);
        normal = PdfFont.createFont(pdfDoc, "c://windows/fonts/arial.ttf", PdfEncodings.WINANSI, true);
        small = PdfFont.createFont(pdfDoc, "c://windows/fonts/arial.ttf", PdfEncodings.WINANSI, true);

        // collections
        specialDays.load(new FileInputStream(String.format(SPECIAL, YEAR)));
        content.load(new FileInputStream(CONTENT));

        Table table;
        Calendar calendar;
        // Loop over the months
        for (int month = 0; month < 12; month++) {
            calendar = new GregorianCalendar(year, month, 1);
            // draw the background
            drawImageAndText(calendar, doc);
            // create a table with 7 columns
            table = new Table(7);
            table.setBackgroundColor(Color.YELLOW);
            table.setWidth(504);
            table.setNextRenderer(new RoundedTableRenderer(table, new Table.RowRange(0, 6)));
            // add the name of the month
            table.addCell(getMonthCell(calendar, locale));
            int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            int day = 1;
            int position = 2;
            // add empty cells
            while (position != calendar.get(Calendar.DAY_OF_WEEK)) {
                position = (position % 7) + 1;
                Cell cell = new Cell().add(new Paragraph(""));
                cell.setNextRenderer(new RoundedCellRenderer(cell, cmykWhite, false));
                table.addCell(cell);
            }
            // add cells for each day
            while (day <= daysInMonth) {
                calendar = new GregorianCalendar(year, month, day++);
                position = (position % 7) + 1;
                table.addCell(getDayCell(calendar, locale));
            }
            // add empty cells
            while (position != 2) {
                position = (position % 7) + 1;
                Cell cell = new Cell().add(new Paragraph(""));
                cell.setNextRenderer(new RoundedCellRenderer(cell, cmykWhite, false));
                table.addCell(cell);
            }
            // TODO cannot add a table on proper position via Document.add
            doc.add(table.setFixedPosition(169, 180, 504));
            if (11 != month) {
                doc.add(new AreaBreak());
            }
        }
        doc.close();
    }

    public void drawImageAndText(Calendar calendar, Document doc) throws IOException {
        // get the image
        Image img = new Image(ImageFactory.getImage(String.format(RESOURCE, calendar)));
        img.scaleToFit(PageSize.A4.getHeight(), PageSize.A4.getWidth());
        img.setFixedPosition(
                (PageSize.A4.getHeight() - img.getImageScaledWidth()) / 2,
                (PageSize.A4.getWidth() - img.getImageScaledHeight()) / 2);
        doc.add(img);
        // add metadata
        Paragraph p = new Paragraph(String.format("%s - \u00a9 Katharine Osborne",
                content.getProperty(String.format("%tm.jpg", calendar))))
                .setFont(small)
                .setFontColor(new DeviceCmyk(cmykGray[0], cmykGray[1], cmykGray[2], cmykGray[3]))
                .setFontSize(8);
        doc.showTextAligned(p, 5, 5, calendar.get(Calendar.MONTH) + 1,
                Property.TextAlignment.LEFT, Property.VerticalAlignment.BOTTOM, 0);
        p = new Paragraph("Calendar generated using iText - example for the book iText in Action 2nd Edition")
                .setFont(small)
                .setFontColor(new DeviceCmyk(cmykGray[0], cmykGray[1], cmykGray[2], cmykGray[3]))
                .setFontSize(8);
        doc.showTextAligned(p, 893, 5, calendar.get(Calendar.MONTH) + 1,
                Property.TextAlignment.RIGHT, Property.VerticalAlignment.BOTTOM, 0);
    }

    /**
     * Creates a PdfPCell with the name of the month
     *
     * @param calendar a date
     * @param locale   a locale
     * @return a PdfPCell with rowspan 7, containing the name of the month
     */
    public Cell getMonthCell(Calendar calendar, Locale locale) {
        Cell cell = new Cell(1, 7);
        cell.setNextRenderer(new RoundedCellRenderer(cell, new int[]{0x00, 0x00, 0xFF, 0x0F}, false));
        // TODO No setUseDescender(boolean)
        // cell.setUseDescender(true);
        Paragraph p = new Paragraph(String.format(locale, "%1$tB %1$tY", calendar)).setFont(bold).setFontSize(14);
        p.setHorizontalAlignment(Property.HorizontalAlignment.CENTER);
        cell.add(p);
        return cell;
    }

    /**
     * Creates a PdfPCell for a specific day
     *
     * @param calendar a date
     * @param locale   a locale
     * @return a PdfPCell
     */
    public Cell getDayCell(Calendar calendar, Locale locale) {
        Cell cell = new Cell();
        cell.setPadding(10);
        cell.setBackgroundColor(Color.WHITE);
        if (isSunday(calendar) || isSpecialDay(calendar)) {
            cell.setBorder(Border.NO_BORDER);
            cell.setNextRenderer(new RoundedCellRenderer(cell, cmykGreen, true));
        } else {
            cell.setNextRenderer(new RoundedCellRenderer(cell, cmykWhite, true));
        }
        // set the content in the language of the locale
        Text text = new Text(String.format(locale, "%1$ta", calendar)).setFont(small).setFontSize(8);
        text.setTextRise(8);
        // a paragraph with the day
        Paragraph p = new Paragraph(text);
        // a separator
        // TODO No VerticalPositionMark
        // p.add(new Text(new VerticalPositionMark()));
        // and the number of the day
        p.add(new Text(String.format(locale, "%1$te", calendar)).setFont(normal).setFontSize(16));
        cell.add(p);
        return cell;
    }

    /**
     * Returns true for Sundays.
     *
     * @param calendar a date
     * @return true for Sundays
     */
    public boolean isSunday(Calendar calendar) {
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            return true;
        }
        return false;
    }

    /**
     * Returns true if the date was found in a list with special days (holidays).
     *
     * @param calendar a date
     * @return true for holidays
     */
    public boolean isSpecialDay(Calendar calendar) {
        if (specialDays.containsKey(String.format("%1$tm%1$td", calendar)))
            return true;
        return false;
    }


    protected class RoundedTableRenderer extends TableRenderer {
        public RoundedTableRenderer(Table modelElement, Table.RowRange rowRange) {
            super(modelElement, rowRange);
        }

        @Override
        public void drawBackground(PdfDocument document, PdfCanvas canvas) {
//            super.drawBackground(document, canvas);
            Rectangle rect = getOccupiedAreaBBox();
            canvas
                    .saveState()
                    .roundRectangle(rect.getX() - 3, rect.getBottom() - 3, rect.getWidth() + 6, rect.getHeight() + 6, 10)
                    // TODO Cmyk colors do not render correct
                    .setFillColorCmyk(cmykYellow[0], cmykYellow[1], cmykYellow[2], cmykYellow[3])
                    .setStrokeColorCmyk(cmykYellow[0], cmykYellow[1], cmykYellow[2], cmykYellow[3])
                    .fillStroke()
                    .restoreState();

        }

        @Override
        protected void drawBorders(PdfCanvas canvas) {
            //super.drawBorders(canvas);
        }
    }


    protected class RoundedCellRenderer extends CellRenderer {
        protected int[] cmykColor;
        protected boolean isColoredBackground;

        public RoundedCellRenderer(Cell modelElement, int[] cmykColor, boolean isColoredBackground) {
            super(modelElement);
            this.cmykColor = cmykColor;
            this.isColoredBackground = isColoredBackground;
        }

        @Override
        public void drawBackground(PdfDocument document, PdfCanvas canvas) {
            Rectangle rect = getOccupiedAreaBBox();
            canvas
                    .saveState()
                    .roundRectangle(rect.getLeft() + 2.5f, rect.getBottom() + 2.5f, rect.getWidth() - 5, rect.getHeight() - 5, 10)
                    .setStrokeColorCmyk(cmykColor[0], cmykColor[1], cmykColor[2], cmykColor[3])
                    .setLineWidth(1.5f);
            if (isColoredBackground) {
                canvas.setFillColor(new DeviceCmyk(0x00, 0x00, 0x00, 0x00)).fillStroke();
            } else {
                canvas.stroke();
            }
            canvas.restoreState();

        }

        @Override
        public void drawBorder(PdfDocument document, PdfCanvas canvas) {
            //super.drawBorder(document, canvas);
        }
    }
}

