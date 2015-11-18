package com.itextpdf.samples.book.part1.chapter05;

import com.itextpdf.basics.font.FontConstants;
import com.itextpdf.basics.font.PdfEncodings;
import com.itextpdf.basics.geom.PageSize;
import com.itextpdf.basics.geom.Rectangle;
import com.itextpdf.canvas.PdfCanvas;
import com.itextpdf.core.color.Color;
import com.itextpdf.core.font.PdfFont;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.Property;
import com.itextpdf.model.element.AreaBreak;
import com.itextpdf.model.element.Cell;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Table;
import com.itextpdf.model.renderer.CellRenderer;
import com.itextpdf.model.renderer.TableRenderer;
import com.itextpdf.samples.GenericTest;
import com.lowagie.database.DatabaseConnection;
import com.lowagie.database.HsqldbConnection;
import com.lowagie.filmfestival.Movie;
import com.lowagie.filmfestival.PojoFactory;
import com.lowagie.filmfestival.Screening;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class Listing_05_01_AlternatingBackground extends GenericTest {
    static public final String DEST =
            "./target/test/resources/book/part1/chapter05/Listing_05_01_AlternatingBackground.pdf";

    protected PdfFont bold;
    protected PdfFont boldItalic;
    protected PdfFont italic;
    protected PdfFont normal;

    public static void main(String args[]) throws IOException, SQLException {
        new Listing_05_01_AlternatingBackground().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException, SQLException {
        // create the database connection
        DatabaseConnection connection = new HsqldbConnection("filmfestival");

        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc, new PageSize(PageSize.A4).rotate());

        normal = PdfFont.createStandardFont(pdfDoc, FontConstants.HELVETICA, PdfEncodings.WINANSI);
        bold = PdfFont.createStandardFont(pdfDoc, FontConstants.HELVETICA_BOLD, PdfEncodings.WINANSI);
        italic = PdfFont.createStandardFont(pdfDoc, FontConstants.HELVETICA, PdfEncodings.WINANSI);
        boldItalic = PdfFont.createStandardFont(pdfDoc, FontConstants.HELVETICA_BOLD, PdfEncodings.WINANSI);

        List<Date> days = PojoFactory.getDays(connection);
        int d = 1;
        for (Date day : days) {
            if (1 != d) {
                doc.add(new AreaBreak());
            }
            Table table = getTable(connection, day);
            table.setNextRenderer(new AlternatingBackgroundTableRenderer(table, new Table.RowRange(0, 50)));
            doc.add(table);
            d++;
        }
        doc.close();
        connection.close();
    }

    public Table getTable(DatabaseConnection connection, Date day) throws UnsupportedEncodingException, SQLException {
        Table table = new Table(new float[]{2, 1, 2, 5, 1});
        table.setWidthPercent(100);
        // TODO No facility to set default cell properties
        // table.getDefaultCell().setPadding(3);
        // TODO No setUseAscender(boolean) and setUseDescender(boolean)
        // table.getDefaultCell().setUseAscender(true);
        // table.getDefaultCell().setUseDescender(true);
        // table.getDefaultCell().setColspan(5);
        // table.getDefaultCell().setBackgroundColor(BaseColor.RED);
        // table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addHeaderCell(new Cell(1, 5)
                .add(new Paragraph(day.toString()))
                .setPadding(3)
                .setBackgroundColor(Color.RED)
                .setTextAlignment(Property.TextAlignment.CENTER));
        // table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        // table.getDefaultCell().setColspan(1);
        // table.getDefaultCell().setBackgroundColor(BaseColor.ORANGE);
        table.addHeaderCell(new Cell()
                .add(new Paragraph("Location"))
                .setBackgroundColor(Color.ORANGE)
                .setHorizontalAlignment(Property.HorizontalAlignment.LEFT));
        table.addHeaderCell(new Cell()
                .add(new Paragraph("Time"))
                .setBackgroundColor(Color.ORANGE)
                .setHorizontalAlignment(Property.HorizontalAlignment.LEFT));
        table.addHeaderCell(new Cell()
                .add(new Paragraph("Run Length"))
                .setBackgroundColor(Color.ORANGE)
                .setHorizontalAlignment(Property.HorizontalAlignment.LEFT));
        table.addHeaderCell(new Cell()
                .add(new Paragraph("Title"))
                .setBackgroundColor(Color.ORANGE)
                .setHorizontalAlignment(Property.HorizontalAlignment.LEFT));
        table.addHeaderCell(new Cell()
                .add(new Paragraph("Year"))
                .setBackgroundColor(Color.ORANGE)
                .setHorizontalAlignment(Property.HorizontalAlignment.LEFT));
        table.addFooterCell(new Cell()
                .add(new Paragraph("Location"))
                .setBackgroundColor(Color.ORANGE)
                .setHorizontalAlignment(Property.HorizontalAlignment.LEFT));
        table.addFooterCell(new Cell()
                .add(new Paragraph("Time"))
                .setBackgroundColor(Color.ORANGE)
                .setHorizontalAlignment(Property.HorizontalAlignment.LEFT));
        table.addFooterCell(new Cell()
                .add(new Paragraph("Run Length"))
                .setBackgroundColor(Color.ORANGE)
                .setHorizontalAlignment(Property.HorizontalAlignment.LEFT));
        table.addFooterCell(new Cell()
                .add(new Paragraph("Title"))
                .setBackgroundColor(Color.ORANGE)
                .setHorizontalAlignment(Property.HorizontalAlignment.LEFT));
        table.addFooterCell(new Cell()
                .add(new Paragraph("Year"))
                .setBackgroundColor(Color.ORANGE)
                .setHorizontalAlignment(Property.HorizontalAlignment.LEFT));
        List<Screening> screenings = PojoFactory.getScreenings(connection, day);
        Movie movie;
        for (Screening screening : screenings) {
            movie = screening.getMovie();
            table.addCell(new Cell().add(new Paragraph(screening.getLocation())));
            table.addCell(new Cell().add(new Paragraph(String.format("%1$tH:%1$tM", screening.getTime()))));
            table.addCell(new Cell().add(new Paragraph(String.format("%d '", movie.getDuration()))));
            table.addCell(new Cell().add(new Paragraph(movie.getMovieTitle())));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(movie.getYear()))));
        }
        return table;
    }
}


class AlternatingBackgroundTableRenderer extends TableRenderer {
    private boolean isOdd = true;

    public AlternatingBackgroundTableRenderer(Table modelElement, Table.RowRange rowRange) {
        super(modelElement, rowRange);
    }

    @Override
    protected TableRenderer[] split(int row, boolean hasContent) {
        return super.split(row, hasContent);
    }

    @Override
    protected <T extends TableRenderer> T makeSplitRenderer(Table.RowRange rowRange) {
        return (T) new AlternatingBackgroundTableRenderer((Table) modelElement, rowRange);
    }

    @Override
    protected <T extends TableRenderer> T createSplitRenderer(Table.RowRange rowRange) {
        AlternatingBackgroundTableRenderer splitRenderer = makeSplitRenderer(rowRange);
        splitRenderer.parent = parent;
        splitRenderer.modelElement = modelElement;
        // TODO childRenderers will be populated twice during the relayout.
        // We should probably clean them before #layout().
        splitRenderer.childRenderers = childRenderers;
        splitRenderer.addAllProperties(getOwnProperties());
        splitRenderer.headerRenderer = headerRenderer;
        splitRenderer.footerRenderer = footerRenderer;
        return (T) splitRenderer;
    }

    @Override
    protected <T extends TableRenderer> T createOverflowRenderer(Table.RowRange rowRange) {
        AlternatingBackgroundTableRenderer overflowRenderer = makeOverflowRenderer(rowRange);
        overflowRenderer.parent = parent;
        overflowRenderer.modelElement = modelElement;
        overflowRenderer.addAllProperties(getOwnProperties());
        overflowRenderer.isOriginalNonSplitRenderer = false;
        return (T) overflowRenderer;
    }

    @Override
    protected <T extends TableRenderer> T makeOverflowRenderer(Table.RowRange rowRange) {
        return (T) new AlternatingBackgroundTableRenderer((Table) modelElement, rowRange);
    }

    @Override
    public void draw(PdfDocument document, PdfCanvas canvas) {
        for (int i = 0; i < rows.size() && null != rows.get(i) && null != rows.get(i)[0]; i++) {
            CellRenderer[] renderers = rows.get(i);
            Rectangle rect = new Rectangle(renderers[0].getOccupiedArea().getBBox().getLeft(),
                    renderers[0].getOccupiedArea().getBBox().getBottom(),
                    renderers[renderers.length - 1].getOccupiedArea().getBBox().getRight() -
                            renderers[0].getOccupiedArea().getBBox().getLeft(),
                    renderers[0].getOccupiedArea().getBBox().getHeight());
            canvas.saveState();
            if (isOdd) {
                canvas.setFillColor(Color.WHITE);
                isOdd = false;
            } else {
                canvas.setFillColor(Color.YELLOW);
                isOdd = true;
            }
            canvas.rectangle(rect);
            canvas.fill();
            canvas.stroke();
            canvas.restoreState();
        }
        super.draw(document, canvas);
    }
}

