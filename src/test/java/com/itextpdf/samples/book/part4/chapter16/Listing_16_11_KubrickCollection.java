/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

package com.itextpdf.samples.book.part4.chapter16;

import com.itextpdf.core.geom.Rectangle;
import com.itextpdf.basics.image.ImageFactory;
import com.itextpdf.core.pdf.PdfArray;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfName;
import com.itextpdf.core.pdf.PdfNumber;
import com.itextpdf.core.pdf.PdfString;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.pdf.action.PdfAction;
import com.itextpdf.core.pdf.action.PdfTargetDictionary;
import com.itextpdf.core.pdf.annot.PdfAnnotation;
import com.itextpdf.core.pdf.annot.PdfFileAttachmentAnnotation;
import com.itextpdf.core.pdf.filespec.PdfFileSpec;
import com.itextpdf.core.pdf.navigation.PdfDestination;
import com.itextpdf.core.pdf.navigation.PdfExplicitDestination;
import com.itextpdf.model.Document;
import com.itextpdf.model.border.Border;
import com.itextpdf.model.element.Cell;
import com.itextpdf.model.element.Image;
import com.itextpdf.model.element.Link;
import com.itextpdf.model.element.List;
import com.itextpdf.model.element.ListItem;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Table;
import com.itextpdf.model.renderer.CellRenderer;
import com.itextpdf.model.renderer.DrawContext;
import com.itextpdf.samples.GenericTest;
import com.itextpdf.test.annotations.type.SampleTest;
import com.lowagie.database.DatabaseConnection;
import com.lowagie.database.HsqldbConnection;
import com.lowagie.filmfestival.Movie;
import com.lowagie.filmfestival.PojoFactory;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class Listing_16_11_KubrickCollection extends GenericTest {
    public static final String DEST = "./target/test/resources/book/part4/chapter16/Listing_16_11_KubrickCollection.pdf";
    public static final String IMG_BOX = "./src/test/resources/book/part4/chapter16/kubrick_box.jpg";
    public static final String IMG_KUBRICK = "./src/test/resources/book/part4/chapter16/kubrick.jpg";
    public static final String TYPE_FIELD = "TYPE";
    public static final String TYPE_CAPTION = "File type";
    public static final String FILE_FIELD = "FILE";
    public static final String FILE_CAPTION = "File name";
    public String[] KEYS = {TYPE_FIELD, FILE_FIELD};

    public static void main(String args[]) throws Exception {
        new Listing_16_08_KubrickBox().manipulatePdf(DEST);
    }

    public void manipulatePdf(String destination) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(destination));
        Document doc = new Document(pdfDoc);
        // TODO No PdfCollection: PdfCollectionSchema, PdfCollectionField and so on
        // PdfCollection collection = new PdfCollection(PdfCollection.HIDDEN);
        // PdfCollectionSchema schema = getCollectionSchema();
        // collection.setSchema(schema);
        // PdfCollectionSort sort = new PdfCollectionSort(KEYS);
        // collection.setSort(sort);
        // writer.setCollection(collection);

        // PdfCollectionItem collectionitem = new PdfCollectionItem(schema);
        PdfFileSpec fs;
        fs = PdfFileSpec.createEmbeddedFileSpec(pdfDoc, IMG_KUBRICK, "Stanley Kubrick", "kubrick.jpg", null, null, false);
        // collectionitem.addItem(TYPE_FIELD, "JPEG");
        // fs.addCollectionItem(collectionitem);
        pdfDoc.addFileAttachment("Stanley Kubrick", fs);

        ByteArrayOutputStream txt = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(txt);
        Image img = new Image(ImageFactory.getImage(IMG_BOX));
        doc.add(img);
        List list = new List();
        list.setSymbolIndent(20);
        PdfDestination dest = PdfExplicitDestination.createFit(1);
        PdfTargetDictionary intermediate;
        PdfTargetDictionary target;
        Link link;
        ListItem item;
        PdfAction action = null;
        DatabaseConnection connection = new HsqldbConnection("filmfestival");
        Set<Movie> box = new TreeSet<Movie>();
        java.util.List<Movie> movies = PojoFactory.getMovies(connection, 1);
        box.addAll(movies);
        box.addAll(PojoFactory.getMovies(connection, 4));
        connection.close();
        for (Movie movie : box) {
            if (movie.getYear() > 1960) {
                out.println(String.format(
                        "%s;%s;%s", movie.getMovieTitle(), movie.getYear(), movie.getDuration()));
                item = new ListItem(movie.getMovieTitle());
                if (!"0278736".equals(movie.getImdb())) {
                    // TODO Implement 'from box' way to work with TargetDictionaries
                    target = new PdfTargetDictionary(PdfName.C);
                    target.put(PdfName.N, new PdfString(movie.getTitle()));
                    intermediate = new PdfTargetDictionary(PdfName.C);
                    intermediate.put(PdfName.P, new PdfNumber(1));
                    intermediate.put(PdfName.A, new PdfNumber(1));
                    intermediate.put(PdfName.T, target);
                    action = PdfAction.createGoToE(dest, true, intermediate);
                    link = new Link(" (see info)", action);
                    item.add(new Paragraph(link));
                }
                list.add(item);
            }
        }
        out.flush();
        out.close();
        doc.add(list);
        fs = PdfFileSpec.createEmbeddedFileSpec(pdfDoc, txt.toByteArray(),
                "Kubrick box: the movies", "kubrick.txt", null, null, null, false);
        // collectionitem.addItem(TYPE_FIELD, "TXT");
        // fs.addCollectionItem(collectionitem);
        pdfDoc.addFileAttachment("Kubrick box: the movies", fs);

        Table table = new Table(1);
        table.setMarginBottom(10);
        Cell cell = new Cell().add("All movies by Kubrick");
        cell.setBorder(Border.NO_BORDER);
        fs = PdfFileSpec.createEmbeddedFileSpec(pdfDoc, new Listing_16_09_KubrickMovies().createPdf(),
                null, Listing_16_09_KubrickMovies.FILENAME, null, null, null, false);
        // collectionitem.addItem(TYPE_FIELD, "PDF");
        // fs.addCollectionItem(collectionitem);
        target = new PdfTargetDictionary(PdfName.C);
        target.put(PdfName.P, new PdfString("movies", null));
        target.put(PdfName.A, new PdfString("The movies of Stanley Kubrick"));
        cell.setNextRenderer(new FileAttachmentRenderer(cell, "The movies of Stanley Kubrick", fs,
                true, PdfAction.createGoToE(dest, true, target), "movies"));
        table.addCell(cell);
        pdfDoc.addFileAttachment(Listing_16_09_KubrickMovies.FILENAME, fs);

        cell = new Cell().add("Kubrick DVDs");
        cell.setBorder(Border.NO_BORDER);
        fs = PdfFileSpec.createEmbeddedFileSpec(pdfDoc, new Listing_16_05_KubrickDvds().createPdf(),
                null, Listing_16_05_KubrickDvds.FILENAME, null, null, null, false);
        // collectionitem.addItem(TYPE_FIELD, "PDF");
        // fs.addCollectionItem(collectionitem);
        cell.setNextRenderer(new FileAttachmentRenderer(cell, "Kubrick DVDs", fs, false, null, null));

        table.addCell(cell);
        pdfDoc.addFileAttachment(Listing_16_05_KubrickDvds.FILENAME, fs);

        cell = new Cell().add("Kubrick documentary");
        cell.setBorder(Border.NO_BORDER);
        fs = PdfFileSpec.createEmbeddedFileSpec(pdfDoc, new Listing_16_06_KubrickDocumentary().createPdf(),
                null, Listing_16_06_KubrickDocumentary.FILENAME, null, null, null, false);
        // collectionitem.addItem(TYPE_FIELD, "PDF");
        // fs.addCollectionItem(collectionitem);
        cell.setNextRenderer(new FileAttachmentRenderer(cell, "Kubrick Documentary", fs, false, null, null));
        table.addCell(cell);
        pdfDoc.addFileAttachment(Listing_16_06_KubrickDocumentary.FILENAME, fs);

        doc.add(table);
        doc.close();
    }


    class FileAttachmentRenderer extends CellRenderer {
        protected boolean isExtendedEvent;
        protected PdfFileSpec fs;
        protected String description;

        protected PdfAction action;
        protected String top;

        public FileAttachmentRenderer(Cell modelElement, String description, PdfFileSpec fs,
                                      boolean isExtendedEvent, PdfAction action, String top) {
            super(modelElement);
            this.description = description;
            this.fs = fs;
            this.isExtendedEvent = isExtendedEvent;
            this.action = action;
            this.top = top;
        }

        @Override
        public void drawBorder(DrawContext drawContext) {
            super.drawBorder(drawContext);
            Rectangle rect = getOccupiedAreaBBox();
            PdfAnnotation annotation = new PdfFileAttachmentAnnotation(new Rectangle(rect.getLeft() - 20,
                    rect.getTop() - 15, 15, 10), fs);
            annotation.setName(new PdfString(description));
            if (isExtendedEvent) {
                annotation.setAction(action);
                PdfArray array = new PdfArray();
                array.add(drawContext.getDocument().getFirstPage().getPdfObject());
                array.add(PdfName.FitH);
                array.add(new PdfNumber(rect.getTop()));
                drawContext.getDocument().addNewName(new PdfString(top), array);
            }
            drawContext.getDocument().getLastPage().addAnnotation(annotation);

        }
    }

/**
 * Creates a Collection schema that can be used to organize the movies of Stanley Kubrick
 * in a collection: year, title, duration, DVD acquisition, filesize (filename is present, but hidden).
 * @return a collection schema
 */
//    private static PdfCollectionSchema getCollectionSchema() {
//        PdfCollectionSchema schema = new PdfCollectionSchema();
//
//        PdfCollectionField type = new PdfCollectionField(TYPE_CAPTION, PdfCollectionField.TEXT);
//        type.setOrder(0);
//        schema.addField(TYPE_FIELD, type);
//
//        PdfCollectionField filename = new PdfCollectionField(FILE_FIELD, PdfCollectionField.FILENAME);
//        filename.setOrder(1);
//        schema.addField(FILE_FIELD, filename);
//
//        return schema;
//    }
}
