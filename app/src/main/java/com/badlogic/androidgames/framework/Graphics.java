package com.badlogic.androidgames.framework;

public interface Graphics {

    enum PixmapFormat {
        ARGB8888, RGB565
    }

    enum Align {
        START, CENTER, END
    }

    Pixmap newPixmap(String fileName, PixmapFormat format);

    void clear(int color);

    void drawPixel(float x, float y, int color);

    void drawLine(float x, float y, float x2, float y2, int color);

    /**
     * Draws a rectangle on screen.
     *
     * @param x      the x coordinate of the top left corner
     * @param y      the y coordinate of the top left corner
     * @param width  the width of the rect
     * @param height the height of the rect
     * @param color  the color of the rect
     */
    default void drawRect(float x, float y, float width, float height, int color) {
        drawRect(x, y, width, height, 0, color);
    }

    /**
     * Draws a rectangle on screen, rotated by the given angle.
     *
     * @param x      the x coordinate of the top left corner
     * @param y      the y coordinate of the top left corner
     * @param width  the width of the rect
     * @param height the height of the rect
     * @param angle  the rotation of the rect
     * @param color  the color of the rect
     */
    default void drawRect(float x, float y, float width, float height, float angle, int color) {
        drawRect(x, y, width, height, angle, x + width / 2, y + height / 2, color);
    }

    void drawRect(float x, float y, float width, float height, float angle, float pivotX, float pivotY, int color);

    void drawCircle(float x, float y, float radius, int color);

    void drawPixmap(Pixmap pixmap, float x, float y, float angle, float dstWidth, float dstHeight, int srcX, int srcY, int srcWidth, int srcHeight, int color);

    void drawPixmap(Pixmap pixmap, float x, float y, float angle, float dstWidth, float dstHeight, int srcX, int srcY, int srcWidth, int srcHeight);

    void drawPixmap(Pixmap pixmap, float x, float y, float dstWidth, float dstHeight, int srcX, int srcY, int srcWidth, int srcHeight);

    void drawPixmap(Pixmap pixmap, float x, float y, int srcX, int srcY, int srcWidth, int srcHeight);

    void drawPixmap(Pixmap pixmap, float x, float y);

    void drawText(String text, float x, float y, float size, int color, Align horizontalAlign, Align verticalAlign);

    int getWidth();

    int getHeight();

    default float getAspectRatio() {
        return (float) getWidth() / getHeight();
    }

}
