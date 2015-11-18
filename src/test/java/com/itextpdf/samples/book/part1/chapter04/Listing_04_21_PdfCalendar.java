package com.itextpdf.samples.book.part1.chapter04;

import com.itextpdf.basics.font.PdfEncodings;
import com.itextpdf.basics.geom.PageSize;
import com.itextpdf.basics.image.ImageFactory;
import com.itextpdf.core.color.Color;
import com.itextpdf.core.color.DeviceCmyk;
import com.itextpdf.core.font.PdfFont;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.Property;
import com.itextpdf.model.element.AreaBreak;
import com.itextpdf.model.element.Cell;
import com.itextpdf.model.element.Image;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Table;
import com.itextpdf.model.element.Text;
import com.itextpdf.samples.GenericTest;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Properties;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class Listing_04_21_PdfCalendar extends GenericTest {
    /**
     * The resulting PDF file.
     */
    public static final String DEST = "./target/test/resources/book/part1/chapter04/Listing_04_21_PdfCalendar.pdf";
    /**
     * The year for which we want to create a calendar
     */
    public static final int YEAR = 2011;
    /**
     * The language code for the calendar
     */
    public static final String LANGUAGE = "en";

    /**
     * Paths to the resources.
     */
    public static final String RESOURCE = "./src/test/resources/book/part1/chapter04/calendar/%tm.jpg";
    public static final String SPECIAL = "./src/test/resources/book/part1/chapter04/calendar/%d.txt";
    public static final String CONTENT = "./src/test/resources/book/part1/chapter04/calendar/content.txt";

    /**
     * Collection with special days
     */
    public static Properties specialDays = new Properties();
    /**
     * Collection with the description of the images
     */
    public static Properties content = new Properties();

    protected PdfFont normal;
    protected PdfFont small;
    protected PdfFont bold;

    public static void main(String args[]) throws IOException, SQLException {
        new Listing_04_21_PdfCalendar().manipulatePdf(DEST);
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
        // TODO Issue with windows fonts
        // "c://windows/fonts/arial.ttf"
        normal = PdfFont.createFont(pdfDoc, "./src/test/resources/font/FreeSans.ttf", PdfEncodings.WINANSI, true);
        // "c://windows/fonts/arial.ttf"
        small = PdfFont.createFont(pdfDoc, "./src/test/resources/font/FreeSans.ttf", PdfEncodings.WINANSI, true);
        // "c://windows/fonts/arialbd.ttf"
        bold = PdfFont.createFont(pdfDoc, "./src/test/resources/font/FreeSans.ttf", PdfEncodings.WINANSI, true);

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
            table.setWidth(504);
            // TODO Add facility to set default cell settings
            // table.getDefaultCell().setBackgroundColor(BaseColor.WHITE);
            // add the name of the month
            table.addCell(getMonthCell(calendar, locale));
            int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            int day = 1;
            int position = 2;
            // add empty cells
            while (position != calendar.get(Calendar.DAY_OF_WEEK)) {
                position = (position % 7) + 1;
                table.addCell(new Cell().add(new Paragraph("")).setBackgroundColor(Color.WHITE));
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
                table.addCell(new Cell().add(new Paragraph("")).setBackgroundColor(Color.WHITE));
            }
            // TODO cannot add a table on proper position via Document.add
            doc.add(table.setFixedPosition(169, 180, 504));
            if (11 != month) {
                doc.add(new AreaBreak());
            }
        }
        doc.close();
    }

    /**
     * Draws the image of the month to the calendar.
     *
     * @param calendar the month (to know which picture to use)
     * @param doc      the document model
     * @throws IOException
     */
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
                .setFontColor(new DeviceCmyk(0x00, 0x00, 0x00, 0x80))
                .setFontSize(8);
        doc.showTextAligned(p, 5, 5, calendar.get(Calendar.MONTH) + 1,
                Property.TextAlignment.LEFT, Property.VerticalAlignment.BOTTOM, 0);
        p = new Paragraph("Calendar generated using iText - example for the book iText in Action 2nd Edition")
                .setFont(small)
                .setFontColor(new DeviceCmyk(0x00, 0x00, 0x00, 0x80))
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
        cell.setBackgroundColor(Color.WHITE);
        // TODO No setUseDescender(boolean)
        // cell.setUseDescender(true);
        Paragraph p = new Paragraph(String.format(locale, "%1$tB %1$tY", calendar)).setFont(bold).setFontSize(14);
        p.setTextAlignment(Property.TextAlignment.CENTER);
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
        cell.setPadding(3);
        // set the background color, based on the type of day
        if (isSunday(calendar)) {
            cell.setBackgroundColor(Color.GRAY);
        } else if (isSpecialDay(calendar)) {
            cell.setBackgroundColor(Color.LIGHT_GRAY);
        } else {
            cell.setBackgroundColor(Color.WHITE);
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
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
            return true;
        if (specialDays.containsKey(String.format("%1$tm%1$td", calendar)))
            return true;
        return false;
    }
}

