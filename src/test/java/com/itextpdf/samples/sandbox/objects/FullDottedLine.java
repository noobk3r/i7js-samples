/**
 * Example written by Bruno Lowagie in answer to:
 * http://stackoverflow.com/questions/20233630/itextsharp-how-to-add-a-full-line-break
 *
 * We create a Chunk and add a background color.
 */
package com.itextpdf.samples.sandbox.objects;

import com.itextpdf.basics.geom.Rectangle;
import com.itextpdf.canvas.PdfCanvas;
import com.itextpdf.canvas.draw.DottedLine;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class FullDottedLine extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/objects/full_dotted_line.pdf";

    public static void main(String[] args) throws IOException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new FullDottedLine().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(new FileOutputStream(DEST)));
        Document doc = new Document(pdfDoc);
        doc.add(new Paragraph("Before dotted line"));
        // TODO There is no separators, so we can draw DottedLine, but we don't know chunk's positions before rendering
        //DottedLineSeparator separator = new DottedLineSeparator();
        new DottedLine().draw(new PdfCanvas(pdfDoc.getFirstPage()), new Rectangle(300, 300, 400, 400));
        //separator.setPercentage(59500f / 523f);
        //linebreak = new Chunk(separator);
        //document.add(linebreak);
        doc.add(new Paragraph("After dotted line"));
        doc.close();
    }
}
