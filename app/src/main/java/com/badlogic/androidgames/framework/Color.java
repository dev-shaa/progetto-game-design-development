package com.badlogic.androidgames.framework;

public class Color {

    public static int convert(int r, int g, int b, int a) {
        return ((a & 0xff) << 24) |
                ((r & 0xff) << 16) |
                ((g & 0xff) << 8) |
                ((b & 0xff));
    }

    public static final int BLACK = 0xff000000;
    public static final int WHITE = 0xffffffff;
    public static final int GOLD = 0xffdaa520;
    public static final int DARKCYAN = 0xff008b8b;
    public static final int GREY = 0xff808080;
    public static final int RED = 0xffff0000;
    public static final int GREEN = 0xff00ff00;
    public static final int BLUE = 0xff0000ff;

}
