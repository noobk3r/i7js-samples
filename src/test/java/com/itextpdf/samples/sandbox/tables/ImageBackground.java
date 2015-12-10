package com.itextpdf.samples.sandbox.tables;

import com.itextpdf.basics.font.FontConstants;
import com.itextpdf.basics.font.FontFactory;
import com.itextpdf.basics.font.Type1Font;
import com.itextpdf.basics.image.ImageFactory;
import com.itextpdf.canvas.PdfCanvas;
import com.itextpdf.core.color.DeviceGray;
import com.itextpdf.core.font.PdfFont;
import com.itextpdf.core.font.PdfType1Font;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Cell;
import com.itextpdf.model.element.Image;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Table;
import com.itextpdf.model.renderer.CellRenderer;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileOutputStream;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class ImageBackground extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/tables/image_background.pdf";
    public static final String IMG = "./src/test/resources/sandbox/tables/bruno.jpg";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new ImageBackground().manipulatePdf(DEST);
    }

    private class ImageBackgroundCellRenderer extends CellRenderer {
        protected Image img;

        public ImageBackgroundCellRenderer(Cell modelElement, Image img) {
            super(modelElement);
            this.img = img;
        }

        @Override
        public void draw(PdfDocument document, PdfCanvas canvas) {
            img.scaleToFit(getOccupiedAreaBBox().getWidth(), getOccupiedAreaBBox().getHeight());
            canvas.addXObject(img.getXObject(), getOccupiedAreaBBox());
            super.draw(document, canvas);
        }
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);

        Table table = new Table(1);
        table.setWidth(400);
        Cell cell = new Cell();
        PdfFont font = new PdfType1Font(pdfDoc, (Type1Font) FontFactory.createFont(FontConstants.HELVETICA));
        Paragraph p = new Paragraph("A cell with an image as background color.")
                .setFont(font).setFontColor(DeviceGray.WHITE).setFontSize(12);
        cell.add(p);
        Image img = new Image(ImageFactory.getImage(IMG));
        cell.setNextRenderer(new ImageBackgroundCellRenderer(cell, img));
        cell.setHeight(600 * img.getImageHeight() / img.getImageWidth());
        table.addCell(cell);
        doc.add(table);

        doc.close();
    }
}
