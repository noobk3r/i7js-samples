package com.itextpdf.samples.book.part1.chapter02;

import com.itextpdf.model.Property;
import com.itextpdf.model.element.Div;
import com.itextpdf.model.element.Paragraph;

public class StarSeparator extends Div {

    public StarSeparator() {
        super();

        Paragraph[] content = {new Paragraph("*"), new Paragraph("*  *")};
        for (Paragraph p : content) {
            p.setFontSize(10).
                    setHorizontalAlignment(Property.HorizontalAlignment.CENTER).
                    setMargins(0, 0, 0, 0);
            add(p);
        }
        content[1].setMarginTop(-4);

        setMargins(0, 0, 0, 0);
    }

}
