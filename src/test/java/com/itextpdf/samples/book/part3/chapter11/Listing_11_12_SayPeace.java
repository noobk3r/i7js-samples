package com.itextpdf.samples.book.part3.chapter11;

import com.itextpdf.basics.font.PdfEncodings;
import com.itextpdf.basics.geom.PageSize;
import com.itextpdf.canvas.PdfCanvasConstants;
import com.itextpdf.core.color.DeviceGray;
import com.itextpdf.core.font.PdfFont;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.Property;
import com.itextpdf.model.border.Border;
import com.itextpdf.model.element.Cell;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Table;
import com.itextpdf.model.element.Text;
import com.itextpdf.samples.GenericTest;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.experimental.categories.Category;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

@Category(SampleTest.class)
// TODO find arabic font and use it
public class Listing_11_12_SayPeace extends GenericTest {

    public static final String DEST = "./target/test/resources/book/part3/chapter11/Listing_11_12_SayPeace.pdf";
    private static final String FONT = "src/test/resources/font/FreeSans.ttf";
    private static final String RESOURCE = "src/test/resources/xml/say_peace.xml";

    public static void main(String[] args) throws Exception {
        new Listing_11_12_SayPeace().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        //Initialize writer
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);

        //Initialize document
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc, PageSize.A4);

        SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
        parser.parse(new InputSource(new FileInputStream(RESOURCE)), new CustomHandler(document));

        //Close document
        document.close();
    }

    private static class CustomHandler extends DefaultHandler {

        /** The StringBuffer that holds the characters. */
        protected StringBuffer buf = new StringBuffer();

        /** The current cell. */
        protected Cell cell;

        /** The table that holds the text. */
        protected Table table;

        protected Document document;

        protected PdfFont f;

        public CustomHandler(Document document) {
            this.document = document;
            try {
                this.f = PdfFont.createFont(document.getPdfDocument(), FONT, PdfEncodings.IDENTITY_H, true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * @see org.xml.sax.ContentHandler#startElement(java.lang.String,
         *      java.lang.String, java.lang.String, org.xml.sax.Attributes)
         */
        public void startElement(String uri, String localName, String qName,
                                 Attributes attributes) throws SAXException {
            if ("message".equals(qName)) {
                buf = new StringBuffer();
                cell = new Cell();
                cell.setBorder(Border.NO_BORDER);
                if ("RTL".equals(attributes.getValue("direction"))) {
                    cell.setBaseDirection(Property.BaseDirection.RIGHT_TO_LEFT).
                            setHorizontalAlignment(Property.HorizontalAlignment.RIGHT);
                }
            }
            else if ("pace".equals(qName)) {
                table = new Table(1);
            }
        }

        /**
         * @see org.xml.sax.ContentHandler#endElement(java.lang.String,
         *      java.lang.String, java.lang.String)
         */
        public void endElement(String uri, String localName, String qName)
                throws SAXException {
            if ("big".equals(qName)) {
                Text bold = new Text(strip(buf)).setFont(f);
                bold.setTextRenderingMode(PdfCanvasConstants.TextRenderingMode.FILL_STROKE).
                        setStrokeWidth(0.5f).
                        setStrokeColor(DeviceGray.BLACK);
                Paragraph p = new Paragraph(bold);
                cell.add(p);
                buf = new StringBuffer();
            }
            else if ("message".equals(qName)) {
                Paragraph p = new Paragraph(strip(buf)).setFont(f);
                cell.add(p);
                table.addCell(cell);
                buf = new StringBuffer();
            }
            else if ("pace".equals(qName)) {
                    document.add(table);
            }
        }

        /**
         * @see org.xml.sax.ContentHandler#characters(char[], int, int)
         */
        public void characters(char[] ch, int start, int length)
                throws SAXException {
            buf.append(ch, start, length);
        }

        /**
         * Replaces all the newline characters by a space.
         *
         * @param buf
         *            the original StringBuffer
         * @return a String without newlines
         */
        protected String strip(StringBuffer buf) {
            int pos;
            while ((pos = buf.indexOf("\n")) != -1)
                buf.replace(pos, pos + 1, " ");
            while (buf.charAt(0) == ' ')
                buf.deleteCharAt(0);
            return buf.toString();
        }
    }
}
