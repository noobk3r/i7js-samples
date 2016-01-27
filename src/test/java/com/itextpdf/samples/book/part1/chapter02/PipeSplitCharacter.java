/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

package com.itextpdf.samples.book.part1.chapter02;

import com.itextpdf.basics.font.otf.GlyphLine;
import com.itextpdf.model.splitting.ISplitCharacters;

public class PipeSplitCharacter implements ISplitCharacters {

    @Override
    public boolean isSplitCharacter(GlyphLine text, int glyphPos) {
        if (text.glyphs.get(glyphPos).getUnicode() == null) {
            return false;
        }
        int charCode = text.glyphs.get(glyphPos).getUnicode();
        return (charCode == '|' || charCode <= ' ' || charCode == '-');
    }

}
