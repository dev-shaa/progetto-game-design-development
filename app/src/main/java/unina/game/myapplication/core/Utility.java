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

    /**
     * Limits a value to a range between a minimum and a maximum value.
     *
     * @param min min value
     * @param max max value
     * @param v   value to clamp
     * @return the min value if {@code v < min}, the max value if {@code v > max}, the value itself if {@code min <= v <= max}
     */
    public static float clamp(float min, float max, float v) {
        if (v < min)
            return min;
        else
            return Math.min(v, max);
    }

    public static float moveTowards(float current, float destination, float amount) {
        if (current < destination)
            return Math.min(current + amount, destination);
        else if (current > destination)
            return Math.max(current - amount, destination);
        else
            return current;
    }

    public static float sqrDistance(float x1, float y1, float x2, float y2) {
        return (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2);
    }

    public static float distance(float x1, float y1, float x2, float y2) {
        return (float) Math.sqrt(sqrDistance(x1, y1, x2, y2));
    }

}
