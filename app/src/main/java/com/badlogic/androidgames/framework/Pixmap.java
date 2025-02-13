package com.badlogic.androidgames.framework;

import com.badlogic.androidgames.framework.Graphics.PixmapFormat;

public interface Pixmap {

    int getWidth();

    int getHeight();

    default float getAspectRatio() {
        return (float) getWidth() / getHeight();
    }

    PixmapFormat getFormat();

    void dispose();

}
