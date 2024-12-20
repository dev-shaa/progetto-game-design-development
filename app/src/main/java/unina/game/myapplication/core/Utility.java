package unina.game.myapplication.core;

public final class Utility {

    private Utility() {

    }

    /**
     * Linearly interpolates between {@code a} and {@code b} by {@code t}.
     *
     * @param a the start value
     * @param b the end value
     * @param t the interpolation value
     * @return interpolated result between the two values
     */
    public static float lerp(float a, float b, float t) {
        return a + (b - a) * t;
    }

    public static float inverseLerp(float a, float b, float v) {
        return (v - a) / (b - a);
    }

}
