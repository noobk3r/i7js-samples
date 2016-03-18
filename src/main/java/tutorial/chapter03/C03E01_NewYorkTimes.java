package tutorial.chapter03;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.image.ImageFactory;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.ColumnDocumentRenderer;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Simple column renderer example.
 */
public class C03E01_NewYorkTimes {

    public static final String DEST = "results/chapter03/new_york_times.pdf";

    public static final String APPLE_IMG = "src/main/resources/img/ny_times_apple.jpg";
    public static final String APPLE_TXT = "src/main/resources/data/ny_times_apple.txt";
    public static final String FACEBOOK_IMG = "src/main/resources/img/ny_times_fb.jpg";
    public static final String FACEBOOK_TXT = "src/main/resources/data/ny_times_fb.txt";
    public static final String INST_IMG = "src/main/resources/img/ny_times_inst.jpg";
    public static final String INST_TXT = "src/main/resources/data/ny_times_inst.txt";

    static PdfFont timesNewRoman = null;
    static PdfFont timesNewRomanBold = null;

    public static void main(String[] args) throws Exception {
        timesNewRoman = PdfFontFactory.createFont(FontConstants.TIMES_ROMAN);
        timesNewRomanBold = PdfFontFactory.createFont(FontConstants.TIMES_BOLD);
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new C03E01_NewYorkTimes().createPdf(DEST);
    }

    protected void createPdf(String dest) throws Exception {

        String articleApple = new String(Files.readAllBytes(Paths.get(APPLE_TXT)), StandardCharsets.UTF_8);
        String articleFB = new String(Files.readAllBytes(Paths.get(FACEBOOK_TXT)), StandardCharsets.UTF_8);
        String articleInstagram = new String(Files.readAllBytes(Paths.get(INST_TXT)), StandardCharsets.UTF_8);

        //Initialize PDF writer
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);

        //Initialize PDF document
        PdfDocument pdf = new PdfDocument(writer);
        PageSize ps = PageSize.A5;

        // Initialize document
        Document doc = new Document(pdf, ps);

        //Set column parameters
        float offSet = 36;
        float columnWidth = (ps.getWidth() - offSet * 2 + 10) / 3;
        float columnHeight = ps.getHeight() - offSet * 2;

        //Define column areas
        Rectangle[] columns = {new Rectangle(offSet - 5, offSet, columnWidth, columnHeight),
                new Rectangle(offSet + columnWidth, offSet, columnWidth, columnHeight),
                new Rectangle(offSet + columnWidth * 2 + 5, offSet, columnWidth, columnHeight)};
        doc.setRenderer(new ColumnDocumentRenderer(doc, columns));

        Image apple = new Image(ImageFactory.getImage(APPLE_IMG)).setWidth(columnWidth);
        Image facebook = new Image(ImageFactory.getImage(FACEBOOK_IMG)).setWidth(columnWidth);
        Image inst = new Image(ImageFactory.getImage(INST_IMG)).setWidth(columnWidth);

        C03E01_NewYorkTimes.addArticle(doc, "Apple Encryption Engineers, if Ordered to Unlock iPhone, Might Resist", "By JOHN MARKOFF MARCH 18, 2016", apple, articleApple);
        C03E01_NewYorkTimes.addArticle(doc, "With ‘Smog Jog’ Through Beijing, Zuckerberg Stirs Debate on Air Pollution", "By PAUL MOZUR MARCH 18, 2016", facebook, articleFB);
        C03E01_NewYorkTimes.addArticle(doc, "Instagram May Change Your Feed, Personalizing It With an Algorithm","By MIKE ISAAC MARCH 15, 2016", inst, articleInstagram);

        doc.close();

    }

    public static void addArticle(Document doc, String title, String author, Image img, String text) throws IOException {
        Paragraph p1 = new Paragraph(title)
                .setFont(timesNewRomanBold)
                .setFontSize(14);
        doc.add(p1);
        doc.add(img);
        Paragraph p2 = new Paragraph()
                .setFont(timesNewRoman)
                .setFontSize(7)
                .setFontColor(Color.GRAY)
                .add(author);
        doc.add(p2);
        Paragraph p3 = new Paragraph()
                .setFont(timesNewRoman)
                .setFontSize(10)
                .add(text);
        doc.add(p3);
    }
}