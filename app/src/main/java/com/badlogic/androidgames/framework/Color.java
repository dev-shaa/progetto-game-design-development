package com.badlogic.androidgames.framework;

public class Color {

    public static int convert(int r, int g, int b, int a) {
        return ((a & 0xff) << 24) |
                ((r & 0xff) << 16) |
                ((g & 0xff) << 8) |
                ((b & 0xff));
    }

    public static final int BLACK = convert(0, 0, 0, 1);
    public static final int WHITE = convert(1, 1, 1, 1);
    public static final int RED = convert(1, 0, 0, 1);
    public static final int GREEN = convert(0, 1, 0, 1);
    public static final int BLUE = convert(0, 0, 1, 1);

}
