package com.badlogic.androidgames.framework.impl;

import java.io.IOException;
import java.io.InputStream;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.ArrayMap;

import com.badlogic.androidgames.framework.Graphics;
import com.badlogic.androidgames.framework.Pixmap;

public class AndroidGraphics implements Graphics {

    private final AssetManager assets;
    private final Bitmap frameBuffer;
    private final Canvas canvas;
    private final Paint paint;
    private final Rect srcRect = new Rect();
    private final Rect dstRect = new Rect();
    private final Rect textRect = new Rect();

    private final float[] colorMatrixArray;
    private final ArrayMap<Integer, ColorMatrixColorFilter> filters;

    public AndroidGraphics(AssetManager assets, Bitmap frameBuffer) {
        this.assets = assets;
        this.frameBuffer = frameBuffer;
        this.canvas = new Canvas(frameBuffer);
        this.paint = new Paint();

        this.colorMatrixArray = new float[4 * 5];
        this.filters = new ArrayMap<>();
    }

    @Override
    public Pixmap newPixmap(String fileName, PixmapFormat format) {
        Options options = new Options();
        options.inPreferredConfig = format == PixmapFormat.RGB565 ? Config.RGB_565 : Config.ARGB_8888;

        Bitmap bitmap;

        try (InputStream in = assets.open(fileName)) {
            bitmap = BitmapFactory.decodeStream(in);
            if (bitmap == null)
                throw new RuntimeException("Couldn't load bitmap from asset '" + fileName + "'");
        } catch (IOException e) {
            throw new RuntimeException("Couldn't load bitmap from asset '" + fileName + "'");
        }

        if (bitmap.getConfig() == Config.RGB_565)
            format = PixmapFormat.RGB565;
        else
            format = PixmapFormat.ARGB8888;

        return new AndroidPixmap(bitmap, format);
    }

    @Override
    public void clear(int color) {
        canvas.drawRGB((color & 0xff0000) >> 16, (color & 0xff00) >> 8, (color & 0xff));
    }

    @Override
    public void drawPixel(float x, float y, int color) {
        paint.setColor(color);
        canvas.drawPoint(x, y, paint);
    }

    @Override
    public void drawLine(float x, float y, float x2, float y2, int color) {
        paint.setColor(color);
        paint.setStrokeWidth(0);
        canvas.drawLine(x, y, x2, y2, paint);
    }

    @Override
    public void drawLine(float x, float y, float x2, float y2, float width, int color) {
        paint.setColor(color);
        paint.setStrokeWidth(width);
        canvas.drawLine(x, y, x2, y2, paint);
    }

    @Override
    public void drawRect(float x, float y, float width, float height, int color) {
        paint.setColor(color);
        paint.setStyle(Style.FILL);
        canvas.drawRect(x, y, x + width, y + height, paint);
    }

    @Override
    public void drawRect(float x, float y, float width, float height, float angle, float pivotX, float pivotY, int color) {
        canvas.save();
        canvas.rotate(-angle, pivotX, pivotY);
        drawRect(x, y, width, height, color);
        canvas.restore();
    }

    @Override
    public void drawCircle(float x, float y, float radius, int color) {
        paint.setColor(color);
        paint.setStyle(Style.FILL);
        canvas.drawCircle(x, y, radius, paint);
    }

    @Override
    public void drawPixmap(Pixmap pixmap, float x, float y, float angle, float dstWidth, float dstHeight, int srcX, int srcY, int srcWidth, int srcHeight, int color) {
        int alpha = Color.alpha(color);

        if (alpha == 0)
            return;

        if (color != Color.WHITE) {
            ColorMatrixColorFilter filter = filters.get(color);

            if (filter == null) {
                colorMatrixArray[0] = Color.red(color) / 255.0f;
                colorMatrixArray[6] = Color.green(color) / 255.0f;
                colorMatrixArray[12] = Color.blue(color) / 255.0f;
                colorMatrixArray[18] = alpha / 255.0f;
                filter = new ColorMatrixColorFilter(colorMatrixArray);

                filters.put(color, filter);
            }

            paint.setColorFilter(filter);
        } else {
            paint.setColor(Color.WHITE);
        }

        drawPixmap(pixmap, x, y, angle, dstWidth, dstHeight, srcX, srcY, srcWidth, srcHeight);

        paint.setColorFilter(null);
    }

    @Override
    public void drawPixmap(Pixmap pixmap, float x, float y, float angle, float dstWidth, float dstHeight, float pivotPointX, float pivotPointY, int srcX, int srcY, int srcWidth, int srcHeight, int color) {
        int alpha = Color.alpha(color);

        if (alpha != 0) {
            canvas.save();
            canvas.rotate(-angle, pivotPointX, pivotPointY);

            if (color != Color.WHITE) {
                ColorMatrixColorFilter filter = filters.get(color);

                if (filter == null) {
                    colorMatrixArray[0] = Color.red(color) / 255.0f;
                    colorMatrixArray[6] = Color.green(color) / 255.0f;
                    colorMatrixArray[12] = Color.blue(color) / 255.0f;
                    colorMatrixArray[18] = alpha / 255.0f;
                    filter = new ColorMatrixColorFilter(colorMatrixArray);

                    filters.put(color, filter);
                }

                paint.setColorFilter(filter);
            } else {
                paint.setColor(Color.WHITE);
            }

            drawPixmap(pixmap, x, y, dstWidth, dstHeight, srcX, srcY, srcWidth, srcHeight);

            paint.setColorFilter(null);
            canvas.restore();
        }
    }

    @Override
    public void drawPixmap(Pixmap pixmap, float x, float y, float angle, float dstWidth, float dstHeight, int srcX, int srcY, int srcWidth, int srcHeight) {
        canvas.save();
        canvas.rotate(-angle, x + dstWidth / 2, y + dstHeight / 2);
        drawPixmap(pixmap, x, y, dstWidth, dstHeight, srcX, srcY, srcWidth, srcHeight);
        canvas.restore();
    }

    @Override
    public void drawPixmap(Pixmap pixmap, float x, float y, float dstWidth, float dstHeight, int srcX, int srcY, int srcWidth, int srcHeight) {
        srcRect.left = srcX;
        srcRect.top = srcY;
        srcRect.right = srcX + srcWidth;
        srcRect.bottom = srcY + srcHeight;

        dstRect.left = (int) x;
        dstRect.top = (int) y;
        dstRect.right = (int) (x + dstWidth);
        dstRect.bottom = (int) (y + dstHeight);

        canvas.drawBitmap(((AndroidPixmap) pixmap).bitmap, srcRect, dstRect, paint);
    }

    @Override
    public void drawPixmap(Pixmap pixmap, float x, float y, int srcX, int srcY, int srcWidth, int srcHeight) {
        drawPixmap(pixmap, x, y, (float) srcWidth, (float) srcHeight, srcX, srcY, srcWidth, srcHeight);
    }

    @Override
    public void drawPixmap(Pixmap pixmap, float x, float y) {
        canvas.drawBitmap(((AndroidPixmap) pixmap).bitmap, x, y, null);
    }

    @Override
    public void drawText(String text, float x, float y, float size, int color, Align horizontalAlign, Align verticalAlign) {
        paint.setTextSize(size);
        paint.setColor(color);

        switch (horizontalAlign) {
            case START:
                paint.setTextAlign(Paint.Align.LEFT);
                break;
            case CENTER:
                paint.setTextAlign(Paint.Align.CENTER);
                break;
            case END:
                paint.setTextAlign(Paint.Align.RIGHT);
                break;
        }

        switch (verticalAlign) {
            case START:
            case END:
                break;
            case CENTER:
                paint.getTextBounds(text, 0, text.length(), textRect);
                y -= textRect.centerY();
                break;
        }

        canvas.drawText(text, x, y, paint);
    }

    @Override
    public int getWidth() {
        return frameBuffer.getWidth();
    }

    @Override
    public int getHeight() {
        return frameBuffer.getHeight();
    }

}
