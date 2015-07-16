package com.itextpdf.samples.book.chapter03;

import com.itextpdf.basics.font.FontConstants;
import com.itextpdf.basics.font.Type1Font;
import com.itextpdf.canvas.PdfCanvas;
import com.itextpdf.canvas.color.Color;
import com.itextpdf.core.font.PdfFont;
import com.itextpdf.core.font.PdfType1Font;
import com.itextpdf.core.geom.PageSize;
import com.itextpdf.core.geom.Rectangle;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfPage;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.model.Document;
import com.itextpdf.model.Property;
import com.itextpdf.model.element.AreaBreak;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Text;
import com.lowagie.database.DatabaseConnection;
import com.lowagie.database.HsqldbConnection;
import com.lowagie.filmfestival.PojoFactory;
import com.lowagie.filmfestival.Screening;

import java.io.FileOutputStream;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class Listing_03_11_MovieTextInfo extends Listing_03_05_MovieTimeBlocks {

    public static final String DEST = "./target/test/resources/Listing_03_11_MovieTextInfo.pdf";

    /** The different time slots. */
    public static String[] TIME =
            { "09:30", "10:00", "10:30", "11:00", "11:30", "12:00",
                    "00:30", "01:00", "01:30", "02:00", "02:30", "03:00",
                    "03:30", "04:00", "04:30", "05:00", "05:30", "06:00",
                    "06:30", "07:00", "07:30", "08:00", "08:30", "09:00",
                    "09:30", "10:00", "10:30", "11:00", "11:30", "12:00",
                    "00:30", "01:00" };

    public static void main(String[] args) throws Exception {
        new Listing_03_11_MovieTextInfo().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        //Initialize writer
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);

        //Initialize document
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc, new PageSize(PageSize.A4.getHeight(), PageSize.A4.getWidth(), 0, 0, 0, 0));

        PdfFont font = new PdfType1Font(pdfDoc, new Type1Font(FontConstants.HELVETICA, ""));
        doc.setProperty(Property.FONT, font);

        Text press = new Text("P").
                setFont(font).
                setFontSize(HEIGHT_LOCATION / 2).
                setFontColor(Color.White);

        try {
            DatabaseConnection connection = new HsqldbConnection("filmfestival");
            locations = PojoFactory.getLocations(connection);
            List<Date> days = PojoFactory.getDays(connection);
            List<Screening> screenings;
            int d = 1;
            for (Date day : days) {
                PdfPage page = pdfDoc.addNewPage();
                if (d != 1)
                    doc.add(new AreaBreak());
                PdfCanvas over = new PdfCanvas(page.newContentStreamAfter(), page.getResources());
                PdfCanvas under = new PdfCanvas(page.newContentStreamBefore(), page.getResources());

                drawTimeTable(under);
                drawTimeSlots(over);
                drawInfo(doc);
                drawDateInfo(day, d++, doc);
                screenings = PojoFactory.getScreenings(connection, day);
                for (Screening screening : screenings) {
                    drawBlock(screening, under, over);
                    drawMovieInfo(screening, doc, press);
                }
            }
            connection.close();
        }
        catch(SQLException sqle) {
            sqle.printStackTrace();
            //document.add(new Paragraph("Database error: " + sqle.getMessage()));
        }

        doc.close();
    }

    /**
     * Draws some text on every calendar sheet.
     *
     */
    protected void drawInfo(Document doc) {
        float x, y;
        x = (OFFSET_LEFT + OFFSET_LOCATION) / 2;
        y = OFFSET_BOTTOM + HEIGHT + 24;
        doc.add(new Paragraph("FOOBAR FILM FESTIVAL").setFontSize(18)
                .setFixedPosition(x, y).setWidth(WIDTH).setHorizontalAlignment(Property.HorizontalAlignment.CENTER));

        x = OFFSET_LOCATION + WIDTH_LOCATION / 2f - 3;
        y = OFFSET_BOTTOM;
        doc.add(new Paragraph("The Majestic").setFontSize(18)
                .setFixedPosition(x, y)
                .setRotationAngle(Math.PI/2)
                .setWidth(HEIGHT_LOCATION * 2)
                .setHorizontalAlignment(Property.HorizontalAlignment.CENTER));

        y = OFFSET_BOTTOM + HEIGHT_LOCATION * 2;
        doc.add(new Paragraph("Googolplex").setFontSize(18)
                .setFixedPosition(x, y)
                .setRotationAngle(Math.PI / 2)
                .setWidth(HEIGHT_LOCATION * 4)
                .setHorizontalAlignment(Property.HorizontalAlignment.CENTER));

        y = OFFSET_BOTTOM + HEIGHT_LOCATION * 6f;
        doc.add(new Paragraph("Cinema Paradiso").setFontSize(18)
                .setFixedPosition(x, y)
                .setRotationAngle(Math.PI / 2)
                .setWidth(HEIGHT_LOCATION * 3)
                .setHorizontalAlignment(Property.HorizontalAlignment.CENTER));

        x = OFFSET_LOCATION + WIDTH_LOCATION - 6;
        for (int i = 0; i < LOCATIONS; i++) {
            y = OFFSET_BOTTOM + ((8 - i) * HEIGHT_LOCATION);
            doc.add(new Paragraph(locations.get(i)).setFontSize(12)
                    .setFixedPosition(x, y)
                    .setRotationAngle(Math.PI / 2)
                    .setWidth(HEIGHT_LOCATION)
                    .setHorizontalAlignment(Property.HorizontalAlignment.CENTER));
        }

        y = OFFSET_BOTTOM + HEIGHT;
        for (int i = 0; i < TIMESLOTS; i++) {
            x = OFFSET_LEFT + (i * WIDTH_TIMESLOT);
            doc.add(new Paragraph(TIME[i]).setFontSize(6)
                    .setFixedPosition(x, y).setHorizontalAlignment(Property.HorizontalAlignment.LEFT)
                    .setRotationAngle(Math.PI / 4).setRotationAlignment(Property.HorizontalAlignment.LEFT));
        }
    }
    /**
     * Draws some text on every calendar sheet.
     *
     */
    protected void drawDateInfo(Date day, int d, Document doc) {
        float x, y;
        x = OFFSET_LOCATION;
        y = OFFSET_BOTTOM + HEIGHT + 12;

        Paragraph p1 = new Paragraph("Day " + d).
                setFontSize(18).
                setFixedPosition(d, x, y);

        x = OFFSET_LEFT;

        Paragraph p2 = new Paragraph(day.toString()).
                setFontSize(18).
                setFixedPosition(d, x, y).
                setWidth(WIDTH).
                setHorizontalAlignment(Property.HorizontalAlignment.RIGHT);

        doc.add(p1).add(p2);
    }

    /**
     * Draws the info about the movie.
     */
    protected void drawMovieInfo(Screening screening, Document doc, Text press) {
        if (screening.isPress()) {
            Rectangle rect = getPosition(screening);

            Paragraph p = new Paragraph().add(press).
                    setFixedPosition(rect.getX(), rect.getY()).
                    setWidth(rect.getWidth()).
                    setHeight(rect.getHeight()).
                    setHorizontalAlignment(Property.HorizontalAlignment.CENTER);
            doc.add(p);
        }
    }
}
