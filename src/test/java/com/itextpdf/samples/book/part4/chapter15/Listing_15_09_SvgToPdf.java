/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

package com.itextpdf.samples.book.part4.chapter15;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.model.Document;
import com.itextpdf.samples.GenericTest;
import com.itextpdf.test.annotations.type.SampleTest;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import org.apache.batik.bridge.BridgeContext;
import org.apache.batik.bridge.DocumentLoader;
import org.apache.batik.bridge.GVTBuilder;
import org.apache.batik.bridge.UserAgent;
import org.apache.batik.bridge.UserAgentAdapter;
import org.apache.batik.dom.svg.SAXSVGDocumentFactory;
import org.apache.batik.gvt.GraphicsNode;
import org.apache.batik.util.XMLResourceDescriptor;
import org.junit.Ignore;
import org.junit.experimental.categories.Category;
import org.w3c.dom.svg.SVGDocument;
import org.xml.sax.SAXException;

@Ignore
@Category(SampleTest.class)
public class Listing_15_09_SvgToPdf extends GenericTest {
    public static final String DEST
            = "./target/test/resources/book/part4/chapter15/Listing_15_09_SvgToPdf.pdf";
    /** The map (shapes). */
    public static final String CITY
            = "./src/test/resources/book/part4/chapter15/foobarcity.svg";
    /** The map (text = street names in English). */
    public static final String STREETS
            = "./src/test/resources/book/part4/chapter15/foobarstreets.svg";

    protected SAXSVGDocumentFactory factory;
    protected BridgeContext ctx;
    protected GVTBuilder builder;

    public Listing_15_09_SvgToPdf() {
        String parser = XMLResourceDescriptor.getXMLParserClassName();
        factory = new SAXSVGDocumentFactory(parser);

        UserAgent userAgent = new UserAgentAdapter();
        DocumentLoader loader = new DocumentLoader(userAgent);
        ctx = new BridgeContext(userAgent, loader);
        ctx.setDynamicState(BridgeContext.DYNAMIC);

        builder = new GVTBuilder();
    }

    public static void main(String args[])
            throws IOException, SQLException, ParserConfigurationException, SAXException {
        new Listing_15_09_SvgToPdf().manipulatePdf(DEST);
    }

    public void drawSvg(PdfFormXObject map, String resource) throws IOException {
        // TODO There is no PdfGraphics2D
        // Graphics2D g2d = new PdfGraphics2D(map, 6000, 6000);
        SVGDocument city = factory.createSVGDocument(new File(resource).toURL()
                .toString());
        GraphicsNode mapGraphics = builder.build(ctx, city);
        // mapGraphics.paint(g2d);
        // g2d.dispose();
    }

    public void manipulatePdf(String dest)
            throws IOException, SQLException, ParserConfigurationException, SAXException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
        Document doc = new Document(pdfDoc, new PageSize(6000, 6000));

        PdfFormXObject map = new PdfFormXObject(new Rectangle(6000, 6000));
        drawSvg(map, CITY);
        PdfCanvas canvas = new PdfCanvas(pdfDoc.getFirstPage()).addXObject(map, 0, 0);

        PdfFormXObject streets = new PdfFormXObject(new Rectangle(6000, 6000));
        drawSvg(streets, STREETS);
        canvas.addXObject(streets, 0, 0);
    }
}
