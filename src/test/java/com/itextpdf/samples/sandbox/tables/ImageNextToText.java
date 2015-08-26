package com.itextpdf.samples.sandbox.tables;

import com.itextpdf.basics.image.ImageFactory;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.model.Document;
import com.itextpdf.model.Property;
import com.itextpdf.model.element.Cell;
import com.itextpdf.model.element.Image;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Table;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileOutputStream;
import java.net.MalformedURLException;

public class ImageNextToText extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/tables/image_next_to_text.pdf";
    public static final String IMG1 = "./src/test/resources/sandbox/tables/javaone2013.jpg";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new ImageNextToText().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);
        Table table = new Table(new float[]{1, 2});
        table.setWidth(0);
        // TODO Implement setWidthPercentage(float)
        // table.setWidthPercentage(100);
        table.addCell(createImageCell(IMG1));
        table.addCell(createTextCell("This picture was taken at Java One.\nIt shows the iText crew at Java One in 2013."));
        doc.add(table);

        doc.close();
    }

    public static Cell createImageCell(String path) throws MalformedURLException {
        Image img = new Image(ImageFactory.getImage(path));
        Cell cell = new Cell().add(img.setAutoScaling(true));
        cell.setBorder(null);
        return cell;
    }

    public static Cell createTextCell(String text) {
        Cell cell = new Cell();
        Paragraph p = new Paragraph(text);
        p.setHorizontalAlignment(Property.HorizontalAlignment.RIGHT);
        cell.add(p);
        cell.setVerticalAlignment(Property.VerticalAlignment.BOTTOM);
        cell.setBorder(null);
        return cell;
    }
}
