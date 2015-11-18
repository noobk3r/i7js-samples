package com.itextpdf.samples.book.part1.chapter02;

import com.itextpdf.basics.font.FontConstants;
import com.itextpdf.basics.image.ImageFactory;
import com.itextpdf.core.font.PdfFont;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Image;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.samples.GenericTest;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class Listing_02_29_RiverPhoenix extends GenericTest {
    public static final String DEST = "./target/test/resources/book/part1/chapter02/Listing_02_29_RiverPhoenix.pdf";

    protected PdfFont bold;

    public static void main(String args[]) throws IOException, SQLException {
        new Listing_02_28_MoviePosters3().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException, SQLException {
        //Initialize writer
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);

        // TODO No setStrictImageSequence(boolean) on FileOutputStream
        // TODO No setInitialLeading
        // writer.setStrictImageSequence(true);
        // writer.setInitialLeading(18);

        bold = PdfFont.createStandardFont(pdfDoc, FontConstants.HELVETICA_BOLD);

        // step 4
        doc.add(new Paragraph("Movies featuring River Phoenix").setFont(bold).setFixedLeading(18));
        doc.add(createParagraph(
                "My favorite movie featuring River Phoenix was ", "0092005").setFixedLeading(18));
        doc.add(createParagraph(
                "River Phoenix was nominated for an academy award for his role in ", "0096018").setFixedLeading(18));
        doc.add(createParagraph(
                "River Phoenix played the young Indiana Jones in ", "0097576").setFixedLeading(18));
        doc.add(createParagraph(
                "His best role was probably in ", "0102494").setFixedLeading(18));

        doc.close();
    }


    public Paragraph createParagraph(String text, String imdb) throws MalformedURLException {
        Paragraph p = new Paragraph(text);
        Image img = new Image(ImageFactory
                .getImage(String.format("./src/test/resources/book/part1/chapter02/posters/%s.jpg", imdb)));
        img.scaleToFit(1000, 72);
        img.setRotationAngle(Math.toRadians(-30));
        // TODO Cannot wrap Image with Text
        // TODO Cannot set offset as in Chunk constructor
        p.add(img);
        return p;
    }
}
