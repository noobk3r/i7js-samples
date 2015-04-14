package com.itextpdf.samples;

import com.itextpdf.basics.PdfException;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.model.Document;
import com.itextpdf.model.elements.IElement;
import com.itextpdf.model.elements.Paragraph;
import com.itextpdf.model.layout.DefaultLayoutMgr;
import com.itextpdf.model.layout.ILayoutMgr;
import com.itextpdf.model.layout.IPlaceElementResult;
import com.itextpdf.model.layout.shapes.BoxShape;
import com.itextpdf.model.layout.shapes.CircleShape;
import com.itextpdf.model.layout.shapes.ILayoutShape;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Building complex layout at a highest level
 */
public class Listing_99_02_ComplexLayout_Option2 {

    static public final String DEST = "./target/test/resources/Listing_99_02_ComplexLayout_Option2.pdf";

    public static void main(String args[]) throws IOException, PdfException {
        new Listing_99_02_ComplexLayout_Option2().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws FileNotFoundException, PdfException {
        //Initialize writer
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);

        //Initialize document
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);

        //Set up layout manager. The layout consist of 2 vertical stripes and circle in between of them.
        ILayoutMgr layoutMgr = new MyLayoutMgr(doc);
        List<ILayoutShape> shapes = new ArrayList<ILayoutShape>();
        shapes.add(new BoxShape(100, 100, 100, 500));
        shapes.add(new CircleShape(300, 350, 70));
        shapes.add(new BoxShape(400, 100, 100, 500));
        layoutMgr.setShapes(shapes);

        //Create paragraph and place it to document
        Paragraph p = new Paragraph("A very long text is here...");
        doc.add(p);

        //Close document
        doc.close();
    }

    static private class MyLayoutMgr extends DefaultLayoutMgr {

        public MyLayoutMgr(Document document) {
            super(document);
        }

        //Define custom action on layout overflow
        @Override
        public IPlaceElementResult overflow(IElement element) throws PdfException {
            List<ILayoutShape> shapes = new ArrayList<ILayoutShape>();
            shapes.add(new BoxShape(100, 100, 400, 500));
            setShapes(shapes);
            return super.overflow(element);
        }
    }


}
