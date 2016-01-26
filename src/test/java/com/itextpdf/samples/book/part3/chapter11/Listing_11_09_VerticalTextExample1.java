package com.itextpdf.samples.book.part3.chapter11;

import com.itextpdf.basics.geom.PageSize;
import com.itextpdf.core.font.PdfFont;
import com.itextpdf.core.font.PdfFontFactory;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.samples.GenericTest;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class Listing_11_09_VerticalTextExample1 extends GenericTest {
    public static final String DEST
            = "./target/test/resources/book/part3/chapter11/Listing_11_09_VerticalTextExample1.pdf";
    public static final String MOVIE
            = "\u4e03\u4eba\u306e\u4f8d";
    public static final String TEXT1
            = "You embarrass me. You're overestimating me. "
            + "Listen, I'm not a man with any special skill, "
            + "but I've had plenty of experience in battles; losing battles, all of them.";
    public static final String TEXT2
            = "In short, that's all I am. Drop such an idea for your own good.";

    public static void main(String[] agrs) throws Exception {
        new Listing_11_09_VerticalTextExample1().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));

        Document doc = new Document(pdfDoc, new PageSize(420, 600));
        PdfFont font = PdfFontFactory.createFont("KozMinPro-Regular", "UniJIS-UCS2-V", false);

//        TODO No VerticalText
//        VerticalText vt = new VerticalText(writer.getDirectContent());
//        vt.setVerticalLayout(390, 570, 540, 12, 30);
//        vt.addText(new Chunk(MOVIE, font));
//        vt.go();
//        vt.addText(new Phrase(TEXT1, font));
//        vt.go();
//        vt.setAlignment(Element.ALIGN_RIGHT);
//        vt.addText(new Phrase(TEXT2, font));
//        vt.go();
        doc.close();
    }
}
