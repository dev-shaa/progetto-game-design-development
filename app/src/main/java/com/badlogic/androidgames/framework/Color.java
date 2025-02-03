package com.badlogic.androidgames.framework;

public class Color {

    public static int convert(int r, int g, int b, int a) {
        return ((a & 0xff) << 24) |
                ((r & 0xff) << 16) |
                ((g & 0xff) << 8) |
                ((b & 0xff));
    }

    public static int lerp(int start, int end, float t) {
        int sa = (start & 0xff000000) >>> 24;
        int sr = (start & 0x00ff0000) >>> 16;
        int sg = (start & 0x0000ff00) >>> 8;
        int sb = (start & 0x000000ff);

        int ea = (end & 0xff000000) >>> 24;
        int er = (end & 0x00ff0000) >>> 16;
        int eg = (end & 0x0000ff00) >>> 8;
        int eb = (end & 0x000000ff);

        int a = (int) (sa + (ea - sa) * t);
        int r = (int) (sr + (er - sr) * t);
        int g = (int) (sg + (eg - sg) * t);
        int b = (int) (sb + (eb - sb) * t);

        return convert(r, g, b, a);
    }

    public static final int TRANSPARENT = 0x00000000;
    public static final int BLACK = 0xff000000;
    public static final int WHITE = 0xffffffff;
    public static final int GOLD = 0xffdaa520;
    public static final int DARKCYAN = 0xff008b8b;
    public static final int GREY = 0xff808080;
    public static final int RED = 0xffff0000;
    public static final int GREEN = 0xff00ff00;
    public static final int BLUE = 0xff0000ff;

}
