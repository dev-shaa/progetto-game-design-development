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

    public static float calcolaTproiezione(float x0, float y0, float x, float y, float angle)
    {
        float theta = (float) Math.toRadians(angle);
        float cosTheta = (float) Math.cos(theta);
        float sinTheta = (float) Math.sin(theta);
        return  (x-x0) * cosTheta + (y-y0) * sinTheta;
    }

}
