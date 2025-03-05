package com.badlogic.androidgames.framework.impl;

import android.graphics.Typeface;

import com.badlogic.androidgames.framework.Font;

public class AndroidFont implements Font {

    private final Typeface typeface;

    AndroidFont(Typeface typeface) {
        this.typeface = typeface;
    }

    public final Typeface getTypeface() {
        return typeface;
    }

}
