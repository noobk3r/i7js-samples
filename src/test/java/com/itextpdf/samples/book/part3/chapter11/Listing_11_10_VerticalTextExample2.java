package com.itextpdf.samples.book.part3.chapter11;

import com.itextpdf.basics.font.PdfEncodings;
import com.itextpdf.basics.geom.PageSize;
import com.itextpdf.core.font.PdfFont;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.samples.GenericTest;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class Listing_11_10_VerticalTextExample2 extends GenericTest {
    public static final String DEST = "./target/test/resources/book/part3/chapter11/Listing_11_10_VerticalTextExample2.pdf";

    public static void main(String[] agrs) throws Exception {
        new Listing_11_10_VerticalTextExample2().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));

        Document doc = new Document(pdfDoc, new PageSize(420, 600));
        PdfFont font = PdfFont.createFont("KozMinPro-Regular", PdfEncodings.IDENTITY_V, false);

//        TODO No VerticalText
//        VerticalText vt = new VerticalText(writer.getDirectContent());
//        vt.setVerticalLayout(390, 570, 540, 12, 30);
//        font = new Font(bf, 20);
//        vt.addText(new Phrase(convertCIDs(TEXT1), font));
//        vt.go();
//        vt.setAlignment(Element.ALIGN_RIGHT);
//        vt.addText(new Phrase(convertCIDs(TEXT2), font));
//        vt.go();
        doc.close();
    }

    /**
     * Converts the CIDs of the horizontal characters of a String
     * into a String with vertical characters.
     * @param text The String with the horizontal characters
     * @return A String with vertical characters
     */
    public String convertCIDs(String text) {
        char cid[] = text.toCharArray();
        for (int k = 0; k < cid.length; ++k) {
            char c = cid[k];
            if (c == '\n')
                cid[k] = '\uff00';
            else
                cid[k] = (char) (c - ' ' + 8720);
        }
        return new String(cid);
    }
}
