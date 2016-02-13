///**
// * This example was written on the basis of Bruno Lowagie's answer to the following question on StackOverflow:
// * http://stackoverflow.com/questions/30053684/how-to-add-border-to-paragraph-in-itext-pdf-library-in-java
// */
//package com.itextpdf.samples.sandbox.itext6presentation;
//
//import com.itextpdf.core.color.Color;
//import com.itextpdf.core.geom.Rectangle;
//import com.itextpdf.core.pdf.PdfDocument;
//import com.itextpdf.core.pdf.PdfWriter;
//import com.itextpdf.model.Document;
//import com.itextpdf.model.border.SolidBorder;
//import com.itextpdf.model.element.Paragraph;
//import com.itextpdf.model.renderer.DrawContext;
//import com.itextpdf.model.renderer.ParagraphRenderer;
//
//import java.io.IOException;
//
//public class BorderForParagraph {
//    public static final String DEST = "./samples/target/test/resources/sandbox/itext6presentation/border_for_paragraph.pdf";
//
//    public static void main(String[] args) throws Exception {
//        new BorderForParagraph().manipulatePdf();
//    }
//
//    protected void manipulatePdf() throws IOException {
//        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(DEST));
//        Document doc = new Document(pdfDoc);
//        doc.add(new Paragraph("Hello! Take a look at the paragraphs below."));
//        doc.add(new Paragraph("This is a simple paragraph without any border. Do you wish to have some?"));
//        // There are "out-of-box" rendering methods, providing a simple way to use borders, change background color, etc.
//        doc.add(new Paragraph("This is a paragraph with a standard border. But let's show you something special.")
//                .setBorder(new SolidBorder(Color.RED, 1)));
//        // Setting special rendering on the certain paragraph (no need to activate/deactivate onParagraph event)
//        Paragraph renderedParagraph = new Paragraph("Look at this paragraph. Isn't it fantastic? " +
//                "By setting the paragraph renderer, we can provide a border, a background color," +
//                " change the line width of the border and many other things.");
//        renderedParagraph.setNextRenderer(new BorderParagraphRenderer(renderedParagraph));
//        doc.add(renderedParagraph);
//        doc.add(new Paragraph("This is a simple paragraph again. If you want more, remember about renderers! Bye!"));
//        pdfDoc.close();
//    }
//
//
//    class BorderParagraphRenderer extends ParagraphRenderer {
//        public BorderParagraphRenderer(Paragraph modelElement) {
//            super(modelElement);
//        }
//
//        @Override
//        public void drawBorder(DrawContext drawContext) {
//            Rectangle rect = getBorderBBox();
//            // Handy PdfCanvas usage
//            drawContext.getCanvas()
//                    .saveState()
//                    .setStrokeColor(Color.GREEN)
//                    .roundRectangle(rect.getLeft(), rect.getBottom(), rect.getWidth(), rect.getHeight(), 7)
//                    .stroke()
//                    .restoreState();
//        }
//    }
//}
