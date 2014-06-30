package com.itextpdf.samples;

import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.model.Document;
import com.itextpdf.model.elements.Paragraph;
import com.itextpdf.model.layout.DefaultLayoutMgr;
import com.itextpdf.model.layout.ILayoutMgr;
import com.itextpdf.model.layout.IPlaceElementResult;
import com.itextpdf.model.layout.shapes.BoxShape;
import com.itextpdf.model.layout.shapes.CircleShape;
import com.itextpdf.model.layout.shapes.ILayoutShape;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Example demonstrates how to build complex layouts using layout manager
 */
public class Listing_99_02_ComplexLayout_Option1 {

    static private final String RESULT = "./result.pdf";

    public static void main(String args[]) throws IOException {

        //Initialize writer
        FileOutputStream fos = new FileOutputStream(RESULT);
        PdfWriter writer = new PdfWriter(fos);

        //Initialize document
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);

        //Set up layout manager. The layout consist of 2 vertical stripes and circle in between of them.
        ILayoutMgr layoutMgr = doc.getLayoutMgr();
        List<ILayoutShape> shapes = new ArrayList<ILayoutShape>();
        shapes.add(new BoxShape(100, 100, 100, 500));
        shapes.add(new CircleShape(300, 350, 70));
        shapes.add(new BoxShape(400, 100, 100, 500));
        layoutMgr.setShapes(shapes);

        //Create paragraph and place it to layout
        Paragraph p = new Paragraph("A very long text is here...");
        IPlaceElementResult result = layoutMgr.placeElement(p);

        //If paragraph does not fit to layout completely. This approach is similar to what we have now in ColumnText.
        if (result.getPlacementStatus() == IPlaceElementResult.NoMoreSpace) {
            doc.newPage();
            shapes = new ArrayList<ILayoutShape>();
            shapes.add(new BoxShape(100, 100, 400, 500));
            layoutMgr.setShapes(shapes);
            layoutMgr.placeElement(p);
        }

        //Close document
        doc.close();

    }

}
