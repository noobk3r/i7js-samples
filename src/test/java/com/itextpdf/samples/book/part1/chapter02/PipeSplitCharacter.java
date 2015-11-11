package com.itextpdf.samples.book.part1.chapter02;

import com.itextpdf.basics.font.otf.GlyphLine;
import com.itextpdf.model.splitting.ISplitCharacters;

public class PipeSplitCharacter implements ISplitCharacters {

    @Override
    public boolean isSplitCharacter(int charCode, GlyphLine text, int charTextPos) {
        return (charCode == '|' || charCode <= ' ' || charCode == '-');
    }

}
