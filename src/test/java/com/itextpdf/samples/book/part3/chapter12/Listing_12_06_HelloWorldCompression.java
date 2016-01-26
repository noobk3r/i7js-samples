package com.itextpdf.samples.book.part3.chapter12;

import com.itextpdf.basics.font.FontConstants;
import com.itextpdf.core.font.PdfFont;
import com.itextpdf.core.font.PdfFontFactory;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfOutputStream;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfVersion;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.utils.CompareTool;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.core.xmp.XMPException;
import com.itextpdf.model.Document;
import com.itextpdf.model.Property;
import com.itextpdf.model.element.List;
import com.itextpdf.model.element.ListItem;
import com.itextpdf.samples.GenericTest;
import com.lowagie.database.DatabaseConnection;
import com.lowagie.database.HsqldbConnection;
import com.lowagie.filmfestival.Director;
import com.lowagie.filmfestival.Movie;
import com.lowagie.filmfestival.PojoFactory;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Assert;
import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class Listing_12_06_HelloWorldCompression extends GenericTest {
    public static final String DEST
            = "./target/test/resources/book/part3/chapter12/Listing_12_06_HelloWorldCompression.pdf";

    public static final String[] RESULT = {
            "./target/test/resources/book/part3/chapter12/Listing_12_06_HelloWorldCompression_compression_not_at_all.pdf",
            "./target/test/resources/book/part3/chapter12/Listing_12_06_HelloWorldCompression_compression_zero.pdf",
            "./target/test/resources/book/part3/chapter12/Listing_12_06_HelloWorldCompression_compression_normal.pdf",
            "./target/test/resources/book/part3/chapter12/Listing_12_06_HelloWorldCompression_compression_high.pdf",
            "./target/test/resources/book/part3/chapter12/Listing_12_06_HelloWorldCompression_compression_full.pdf",
            "./target/test/resources/book/part3/chapter12/Listing_12_06_HelloWorldCompression_compression_full_too.pdf",
            "./target/test/resources/book/part3/chapter12/Listing_12_06_HelloWorldCompression_compression_removed.pdf"
    };
    public static final String[] CMP_RESULT = {
            "./src/test/resources/book/part3/chapter12/cmp_Listing_12_06_HelloWorldCompression_compression_not_at_all.pdf",
            "./src/test/resources/book/part3/chapter12/cmp_Listing_12_06_HelloWorldCompression_compression_zero.pdf",
            "./src/test/resources/book/part3/chapter12/cmp_Listing_12_06_HelloWorldCompression_compression_normal.pdf",
            "./src/test/resources/book/part3/chapter12/cmp_Listing_12_06_HelloWorldCompression_compression_high.pdf",
            "./src/test/resources/book/part3/chapter12/cmp_Listing_12_06_HelloWorldCompression_compression_full.pdf",
            "./src/test/resources/book/part3/chapter12/cmp_Listing_12_06_HelloWorldCompression_compression_full_too.pdf",
            "./src/test/resources/book/part3/chapter12/cmp_Listing_12_06_HelloWorldCompression_compression_removed.pdf"
    };

//    public static final String RESULT1
//            = "./target/test/resources/book/part3/chapter12/Listing_12_06_HelloWorldCompression_compression_not_at_all.pdf";
//    public static final String RESULT2
//            = "./target/test/resources/book/part3/chapter12/Listing_12_06_HelloWorldCompression_compression_zero.pdf";
//    public static final String RESULT3
//            = "./target/test/resources/book/part3/chapter12/Listing_12_06_HelloWorldCompression_compression_normal.pdf";
//    public static final String RESULT4
//            = "./target/test/resources/book/part3/chapter12/Listing_12_06_HelloWorldCompression_compression_high.pdf";
//    public static final String RESULT5
//            = "./target/test/resources/book/part3/chapter12/Listing_12_06_HelloWorldCompression_compression_full.pdf";
//    public static final String RESULT6
//            = "./target/test/resources/book/part3/chapter12/Listing_12_06_HelloWorldCompression_compression_full_too.pdf";
//    public static final String RESULT7
//            = "./target/test/resources/book/part3/chapter12/Listing_12_06_HelloWorldCompression_compression_removed.pdf";

    protected PdfFont bold;
    protected PdfFont boldItalic;
    protected PdfFont italic;
    protected PdfFont normal;

    public static void main(String args[]) throws IOException, XMPException, SQLException {
        new Listing_12_06_HelloWorldCompression().manipulatePdf(DEST);
    }

    public void createPdf(String dest, int compression) throws IOException, XMPException, SQLException {
        PdfWriter writer = new PdfWriter(dest);
        switch(compression) {
            case -1:
                writer.setCompressionLevel(PdfOutputStream.NO_COMPRESSION);
                break;
            case 0:
                writer.setCompressionLevel(PdfOutputStream.DEFAULT_COMPRESSION);
                break;
            case 2:
                writer.setCompressionLevel(PdfOutputStream.BEST_COMPRESSION);
                break;
            case 3:
                writer.setFullCompression(true);
                break;
        }

        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);

        bold = PdfFontFactory.createStandardFont(FontConstants.HELVETICA_BOLD);
        boldItalic = PdfFontFactory.createStandardFont(FontConstants.HELVETICA_BOLDOBLIQUE);
        italic = PdfFontFactory.createStandardFont(FontConstants.HELVETICA_OBLIQUE);
        normal = PdfFontFactory.createStandardFont(FontConstants.HELVETICA);

        // Create database connection and statement
        DatabaseConnection connection = new HsqldbConnection("filmfestival");
        Statement stm = connection.createStatement();
        ResultSet rs = stm.executeQuery(
                "SELECT DISTINCT mc.country_id, c.country, count(*) AS c "
                        + "FROM film_country c, film_movie_country mc "
                        + "WHERE c.id = mc.country_id "
                        + "GROUP BY mc.country_id, country ORDER BY c DESC");

        // Create a new list
        List list = new List(Property.ListNumberingType.DECIMAL);
        // loop over the countries
        while (rs.next()) {
            // create a list item for the country
            ListItem item = new ListItem(
                    String.format("%s: %d movies",
                            rs.getString("country"), rs.getInt("c")));
            item.setFont(boldItalic);
            // create a movie list for each country
            List movielist = new List(Property.ListNumberingType.ENGLISH_LOWER);
            for(Movie movie :
                    PojoFactory.getMovies(connection, rs.getString("country_id"))) {
                ListItem movieitem = new ListItem(movie.getMovieTitle());
                List directorlist = new List();
                for (Director director : movie.getDirectors()) {
                    directorlist.add(
                            String.format("%s, %s",
                                    director.getName(), director.getGivenName()));
                }
                movieitem.add(directorlist);
                movielist.add(movieitem);
            }
            item.add(movielist);
            list.add(item);
        }
        doc.add(list);

        stm.close();
        connection.close();
        doc.close();
    }

    public void compressPdf(String src, String dest) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(src), new PdfWriter(dest), PdfVersion.PDF_1_5);
        //pdfDoc.getWriter().setCompressionLevel(PdfOutputStream.BEST_COMPRESSION);
        pdfDoc.getWriter().setFullCompression(true);
        pdfDoc.close();
    }

    public void decompressPdf(String src, String dest) throws IOException{
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(src), new PdfWriter(dest));
        pdfDoc.close();
    }

    public void manipulatePdf(String dest) throws IOException, XMPException, SQLException {
        createPdf(RESULT[0], -1);
        createPdf(RESULT[1], 0);
        createPdf(RESULT[2], 1);
        createPdf(RESULT[3], 2);
        createPdf(RESULT[4], 3);
        compressPdf(RESULT[1], RESULT[5]);
        decompressPdf(RESULT[5], RESULT[6]);
    }

    @Override
    protected void comparePdf(String dest, String cmp) throws Exception {
        CompareTool compareTool = new CompareTool();
        String outPath;
        for (int i = 0; i < RESULT.length; i++) {
            outPath = new File(RESULT[i]).getParent();
            if (compareXml) {
                if (!compareTool.compareXmls(RESULT[i], CMP_RESULT[i])) {
                    addError("The XML structures are different.");
                }
            } else {
                if (compareRenders) {
                    addError(compareTool.compareVisually(RESULT[i], CMP_RESULT[i], outPath, differenceImagePrefix));
                    addError(compareTool.compareLinkAnnotations(dest, cmp));
                } else {
                    addError(compareTool.compareByContent(RESULT[i], CMP_RESULT[i], outPath, differenceImagePrefix));
                }
                addError(compareTool.compareDocumentInfo(RESULT[i], CMP_RESULT[i]));
            }
        }

        if (errorMessage != null) Assert.fail(errorMessage);
    }
}
