package com.itextpdf.samples.sandbox.tables;

import com.itextpdf.basics.image.ImageFactory;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Cell;
import com.itextpdf.model.element.Image;
import com.itextpdf.model.element.Table;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileOutputStream;
import java.net.MalformedURLException;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class ImageNextToEachOther extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/tables/image_next_to_each_other.pdf";
    public static final String IMG1 = "./src/test/resources/sandbox/tables/javaone2013.jpg";
    public static final String IMG2 = "./src/test/resources/sandbox/tables/berlin2013.jpg";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new ImageNextToEachOther().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);

        Table table = new Table(2);
        table.addCell(createImageCell(IMG1));
        table.addCell(createImageCell(IMG2));
        doc.add(table);

        doc.close();
    }

    public static Cell createImageCell(String path) throws MalformedURLException {
        Image img = new Image(ImageFactory.getImage(path));
        return new Cell().add(img.setAutoScale(true));
    }
}
