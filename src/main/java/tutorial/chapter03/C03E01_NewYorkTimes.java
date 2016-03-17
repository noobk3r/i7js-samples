package tutorial.chapter03;

import com.itextpdf.io.image.ImageFactory;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.layout.LayoutArea;
import com.itextpdf.layout.renderer.ParagraphRenderer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple columns example.
 */
public class C03E01_NewYorkTimes {

    public static final String SENATOR = "src/main/resources/img/senator.jpg";
    public static final String CAR = "src/main/resources/img/car.jpg";

    public static final String DEST = "results/chapter03/new_york_times.pdf";

    public static void main(String args[]) throws IOException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new C03E01_NewYorkTimes().createPdf(DEST);
    }

    public void createPdf(String dest) throws IOException {

        //Initialize PDF writer
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);

        //Initialize PDF document
        PdfDocument pdf = new PdfDocument(writer);
        PageSize ps = PageSize.A5.rotate();
        PdfPage page = pdf.addNewPage(ps);

        // Initialize document
        Document document = new Document(pdf);

        //Set column parameters
        final float columnWidth = (ps.getWidth() - 100) / 3 - 5;
        final float columnHeight = ps.getHeight() / 2 - 100;

        Image senator = new Image(ImageFactory.getImage(SENATOR)).setWidth(columnWidth);
        Image car = new Image(ImageFactory.getImage(CAR)).setWidth(columnWidth);

        Paragraph p = new Paragraph("Ohio Looms Large in Both Races on Tuesday" +
                "By TRIP GABRIEL")
                .setFontSize(9)
                .add(car);

        p.setNextRenderer(new ParagraphRenderer(p) {
            @Override
            public List<Rectangle> initElementAreas(LayoutArea area) {
                List<Rectangle> areas = new ArrayList<Rectangle>();
                Rectangle rect = new Rectangle(50, 50, columnWidth, columnHeight);
                areas.add(rect);
                return areas;
            }
        });
        document.add(p);

        Paragraph p2 = new Paragraph("Ohio Looms Large in Both Races on Tuesday\n" +
                "By TRIP GABRIEL\n")
                .setFontSize(9)
                .add(car);
        p2.setNextRenderer(new ParagraphRenderer(p) {
            @Override
            public List<Rectangle> initElementAreas(LayoutArea area) {
                List<Rectangle> areas = new ArrayList<Rectangle>();
                Rectangle rect = new Rectangle(columnWidth + 55, 50, columnWidth, columnHeight);
                areas.add(rect);
                return areas;
            }
        });
        document.add(p2);

        /*Paragraph p2 = new Paragraph("\nOhio Looms Large in Both Races on Tuesday\n" +
                "By TRIP GABRIEL\n")
                .setFontSize(9)
                .add(senator)
                .add("\nOhio has emerged as the one large state voting this week where " +
                        "Mr. Trump appears vulnerable. On the Democratic side, Bernie " +
                        "Sanders hopes for a repeat of his upset in Michigan.\n");
        p2.add("\nHere's One Answer to Electric Cars' Lack of Range: Electric Road\n" +
                "By ANNA HIRTENSTEIN\n")
                .add(car)
                .add("\nA father-daughter team that founded the Scotland-based Tracked" +
                        "Electric Vehicle project where battery-powered cars recharge while" +
                        "they drive from a metal strip embedded in highways.\n");

        p2.setNextRenderer(new ParagraphRenderer(p2) {
            @Override
            public List<Rectangle> initElementAreas(LayoutArea area) {
                List<Rectangle> areas = new ArrayList<Rectangle>();
                Rectangle rect = new Rectangle(columnWidth + 55, 50, columnWidth, columnHeight);
                areas.add(rect);
                return areas;
            }
        });
        document.add(p2);

        Paragraph p3 = new Paragraph("\nOhio Looms Large in Both Races on Tuesday\n" +
                "By TRIP GABRIEL\n")
                .setFontSize(9)
                .add(senator)
                .add("\nOhio has emerged as the one large state voting this week where " +
                        "Mr. Trump appears vulnerable. On the Democratic side, Bernie " +
                        "Sanders hopes for a repeat of his upset in Michigan.\n");
        p3.add("\nHere's One Answer to Electric Cars' Lack of Range: Electric Road\n" +
                "By ANNA HIRTENSTEIN\n")
                .add(car)
                .add("\nA father-daughter team that founded the Scotland-based Tracked" +
                        "Electric Vehicle project where battery-powered cars recharge while" +
                        "they drive from a metal strip embedded in highways.\n");

        p3.setNextRenderer(new ParagraphRenderer(p2) {
            @Override
            public List<Rectangle> initElementAreas(LayoutArea area) {
                List<Rectangle> areas = new ArrayList<Rectangle>();
                Rectangle rect = new Rectangle(2 * columnWidth + 60, 50, columnWidth, columnHeight);
                areas.add(rect);
                return areas;
            }
        });
        document.add(p3);*/

        //Close document
        document.close();

    }
}