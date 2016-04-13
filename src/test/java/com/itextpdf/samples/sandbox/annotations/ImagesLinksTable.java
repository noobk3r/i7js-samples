/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

/**
 * This example was written by Bruno Lowagie in answer to the following question:
 * http://stackoverflow.com/questions/32839816/export-hyperlink-to-pdf-file-from-itextsharp-library-in-c-sharp
 */
package com.itextpdf.samples.sandbox.annotations;

import com.itextpdf.io.image.ImageFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.action.PdfAction;
import com.itextpdf.kernel.pdf.annot.PdfAnnotation;
import com.itextpdf.kernel.pdf.annot.PdfLinkAnnotation;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Property;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.renderer.DrawContext;
import com.itextpdf.layout.renderer.ImageRenderer;
import com.itextpdf.samples.GenericTest;
import com.itextpdf.test.annotations.type.SampleTest;

import java.io.File;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class ImagesLinksTable extends GenericTest {
    public static final String IMG = "./src/test/resources/img/info.png";
    public static final String DEST = "./target/test/resources/sandbox/annotations/images_links_table.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new ImagesLinksTable().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(DEST));
        Document doc = new Document(pdfDoc);
        Table table = new Table(3);
        Image img = new Image(ImageFactory.getImage(IMG));
        Paragraph anchor = new Paragraph().add(img);
        anchor.setProperty(Property.ACTION, PdfAction.createURI("http://lowagie.com/"));
        table.addCell(anchor);
        table.addCell("A");
        table.addCell("B");
        table.addCell("C");

        // The second way (in itext5 example) to achieve the result is identical with the first one (in itext7)

        Image img2 = new Image(ImageFactory.getImage(IMG));
        img2.setNextRenderer(new LinkImageRenderer(img2));
        table.addCell(img2);

        doc.add(table);
        doc.close();
    }


    protected class LinkImageRenderer extends ImageRenderer {
        public LinkImageRenderer(Image image) {
            super(image);
        }

        @Override
        public void draw(DrawContext drawContext) {
            super.draw(drawContext);
            PdfAnnotation a = new PdfLinkAnnotation(getOccupiedAreaBBox())
                    .setAction(PdfAction.createURI("http://lowagie.com/bio"))
                    .setBorder(null);
            drawContext.getDocument().getLastPage().addAnnotation(a);
        }
    }
}
