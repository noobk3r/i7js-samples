package com.itextpdf.samples.book.part1.chapter02;

import com.itextpdf.basics.font.otf.GlyphLine;
import com.itextpdf.model.splitting.ISplitCharacters;

public class PipeSplitCharacter implements ISplitCharacters {

    @Override
    public boolean isSplitCharacter(GlyphLine text, int glyphPos) {
        if (text.glyphs.get(glyphPos).unicode == null) {
            return false;
        }
        int charCode = text.glyphs.get(glyphPos).unicode;
        return (charCode == '|' || charCode <= ' ' || charCode == '-');
    }

}
