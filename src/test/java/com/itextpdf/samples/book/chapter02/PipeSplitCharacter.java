package com.itextpdf.samples.book.chapter02;

import com.itextpdf.model.hyphenation.ISplitCharacters;

public class PipeSplitCharacter implements ISplitCharacters {

    @Override
    public boolean isSplitCharacter(int charCode, String text, int charTextPos) {
        return (charCode == '|' || charCode <= ' ' || charCode == '-');
    }

}
