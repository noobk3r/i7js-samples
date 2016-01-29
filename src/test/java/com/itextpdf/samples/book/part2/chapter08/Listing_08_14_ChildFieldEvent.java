/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

package com.itextpdf.samples.book.part2.chapter08;

import com.itextpdf.core.geom.Rectangle;
import com.itextpdf.core.pdf.PdfArray;
import com.itextpdf.core.pdf.annot.PdfAnnotation;
import com.itextpdf.core.pdf.annot.PdfWidgetAnnotation;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.model.element.Cell;
import com.itextpdf.model.renderer.CellRenderer;
import com.itextpdf.model.renderer.DrawContext;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class Listing_08_14_ChildFieldEvent extends CellRenderer {
    protected PdfFormField field;
    protected float padding;

    public Listing_08_14_ChildFieldEvent(PdfFormField field, float padding, Cell modelElement) {
        super(modelElement);
        this.field = field;
        this.padding = padding;
    }

    @Override
    public void draw(DrawContext drawContext) {
        PdfWidgetAnnotation widget = field.getWidgets().get(0);
        Rectangle rect = widget.getRectangle().toRectangle();
        Rectangle bBox = getOccupiedAreaBBox();
        rect.setBbox(bBox.getLeft() + padding, bBox.getBottom() + padding,
                bBox.getRight() - padding, bBox.getTop() - padding);
        widget.setRectangle(new PdfArray(rect));
        widget.setHighlightMode(PdfAnnotation.HIGHLIGHT_INVERT);
        super.draw(drawContext);
    }
}

