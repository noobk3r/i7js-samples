package com.itextpdf.samples.book.part1.chapter03;

import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.samples.GenericTest;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore("Multiple font issues")
@Category(SampleTest.class)
public class Listing_03_07_FoobarFilmFestival extends GenericTest {

    public static final String DEST = "./target/test/resources/book/part1/chapter03/Listing_03_07_FoobarFilmFestival.pdf";

    public static void main(String[] args) throws Exception {
        new Listing_03_07_FoobarFilmFestival().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
//        //Initialize writer
//        FileOutputStream fos = new FileOutputStream(dest);
//        PdfWriter writer = new PdfWriter(fos);
//
//        //Initialize document
//        PdfDocument pdfDoc = new PdfDocument(writer);
//        Document doc = new Document(pdfDoc);
//
//        String foobar = "Foobar Film Festival";
//        // Measuring a String in Helvetica
//        PdfFont helvetica = new PdfType1Font(pdfDoc, new Type1Font(FontConstants.HELVETICA, ""));
//        float width_helv = helvetica.getFontProgram().getWidthPoint(foobar, 12);
//        Text c = new Text(foobar + ": " + width_helv).setFont(helvetica);
//        doc.add(new Paragraph(c));
//        // Text element might have various properties (word/char spacing, scaling, kerning, and so on), so
//        // in order to correctly get a width of a text(former chunk) we need to layout its renderer.
//        // For simplified version use font's methods for getting width by string.
//        float chunkWidth = new TextRenderer(c).layout(new LayoutContext(new LayoutArea(1, PageSize.Default))).getOccupiedArea().getBBox().getWidth();
//        doc.add(new Paragraph(String.format("Chunk width: %f", chunkWidth)));
//        // Measuring a String in Times
//       // FontProgram bf_times = FontProgram.createFont(
//         //       "c:/windows/fonts/times.ttf", BaseFont.WINANSI, BaseFont.EMBEDDED);
//        //Font times = new Font(bf_times, 12);
//        PdfFont times;
//        float width_times = times.getFontProgram().getWidthPoint(foobar, 12);
//        c = new Text(foobar + ": " + width_times).setFont(times);
//        doc.add(new Paragraph(c));
//        chunkWidth = new TextRenderer(c).layout(new LayoutContext(new LayoutArea(1, PageSize.Default))).getOccupiedArea().getBBox().getWidth();
//        doc.add(new Paragraph(String.format("Chunk width: %f", chunkWidth)));
//        doc.add(new Paragraph("\n"));
//        // Ascent and descent of the String
//        doc.add(new Paragraph("Ascent Helvetica: "
//                + bf_helv.getAscentPoint(foobar, 12)));
//        doc.add(new Paragraph("Ascent Times: "
//                + bf_times.getAscentPoint(foobar, 12)));
//        doc.add(new Paragraph("Descent Helvetica: "
//                + bf_helv.getDescentPoint(foobar, 12)));
//        doc.add(new Paragraph("Descent Times: "
//                + bf_times.getDescentPoint(foobar, 12)));
//        doc.add(Chunk.NEWLINE);
//        // Kerned text
//        width_helv = bf_helv.getWidthPointKerned(foobar, 12);
//        c = new Chunk(foobar + ": " + width_helv, helvetica);
//        doc.add(new Paragraph(c));
//        // Drawing lines to see where the text is added
//        PdfCanvas canvas = new PdfCanvas(pdfDoc.getPage(1)).
//            saveState().
//            setLineWidth(0.05f).
//            moveTo(400, 806).
//            lineTo(400, 626).
//            moveTo(508.7f, 806).
//            lineTo(508.7f, 626).
//            moveTo(280, 788).
//            lineTo(520, 788).
//            moveTo(280, 752).
//            lineTo(520, 752).
//            moveTo(280, 716).
//            lineTo(520, 716).
//            moveTo(280, 680).
//            lineTo(520, 680).
//            moveTo(280, 644).
//            lineTo(520, 644).
//            stroke().
//            restoreState();
//        // Adding text with PdfContentByte.showTextAligned()
//        canvas.beginText();
//        canvas.setFontAndSize(bf_helv, 12);
//        canvas.showTextAligned(Element.ALIGN_LEFT, foobar, 400, 788, 0);
//        canvas.showTextAligned(Element.ALIGN_RIGHT, foobar, 400, 752, 0);
//        canvas.showTextAligned(Element.ALIGN_CENTER, foobar, 400, 716, 0);
//        canvas.showTextAligned(Element.ALIGN_CENTER, foobar, 400, 680, 30);
//        canvas.showTextAlignedKerned(Element.ALIGN_LEFT, foobar, 400, 644, 0);
//        canvas.endText();
//        // More lines to see where the text is added
//        canvas.saveState().
//            setLineWidth(0.05f).
//            moveTo(200, 590).
//            lineTo(200, 410).
//            moveTo(400, 590).
//            lineTo(400, 410).
//            moveTo(80, 572).
//            lineTo(520, 572).
//            moveTo(80, 536).
//            lineTo(520, 536).
//            moveTo(80, 500).
//            lineTo(520, 500).
//            moveTo(80, 464).
//            lineTo(520, 464).
//            moveTo(80, 428).
//            lineTo(520, 428).
//            stroke().
//            restoreState();
//        // Adding text with ColumnText.showTextAligned()
//        Paragraph phrase = new Paragraph(foobar).setFont(times);
//        doc.showTextAligned(phrase, 200, 572, 1, Property.HorizontalAlignment.LEFT, Property.VerticalAlignment.TOP, 0);
//        doc.showTextAligned(phrase, 200, 536, 0, Property.HorizontalAlignment.LEFT, Property.VerticalAlignment.TOP, 0);
//        doc.showTextAligned(phrase, 200, 500, 0, Property.HorizontalAlignment.LEFT, Property.VerticalAlignment.TOP, 0);
//        doc.showTextAligned(phrase, 200, 464, 30, Property.HorizontalAlignment.LEFT, Property.VerticalAlignment.TOP, 0);
//        doc.showTextAligned(phrase, 200, 428, -30, Property.HorizontalAlignment.LEFT, Property.VerticalAlignment.TOP, 0);
//        // Chunk attributes
//        c = new Text(foobar).setFont(times);
//        c.setHorizontalScaling(0.5f);
//        phrase = new Paragraph(c);
//        doc.showTextAligned(phrase, 400, 572, 1, Property.HorizontalAlignment.LEFT, Property.VerticalAlignment.TOP, 0);
//        c = new Text(foobar).setFont(times);
//        c.setSkew(15, 15);
//        phrase = new Paragraph(c);
//        doc.showTextAligned(phrase, 400, 536, 1, Property.HorizontalAlignment.LEFT, Property.VerticalAlignment.TOP, 0);
//        doc.showTextAligned(phrase, 400, 536, 1, Property.HorizontalAlignment.LEFT, Property.VerticalAlignment.TOP, 0);
//        c = new Text(foobar).setFont(times);
//        c.setSkew(0, 25);
//        phrase = new Paragraph(c);
//        doc.showTextAligned(phrase, 400, 500, 1, Property.HorizontalAlignment.LEFT, Property.VerticalAlignment.TOP, 0);
//        c = new Text(foobar).setFont(times);
//        c.setTextRenderingMode(Property.TextRenderingMode.STROKE)
//                .setStrokeWidth(0.1f)
//                .setStrokeColor(Color.RED);
//        phrase = new Paragraph(c);
//        doc.showTextAligned(phrase, 400, 464, 1, Property.HorizontalAlignment.LEFT, Property.VerticalAlignment.TOP, 0);
//        c = new Text(foobar).setFont(times);
//        c.setTextRenderingMode(Property.TextRenderingMode.FILL_STROKE).
//                setStrokeWidth(1);
//        phrase = new Paragraph(c);
//        doc.showTextAligned(phrase, 400, 428, 1, Property.HorizontalAlignment.LEFT, Property.VerticalAlignment.TOP, 0);
//        // step 5
//        doc.close();
    }
}
