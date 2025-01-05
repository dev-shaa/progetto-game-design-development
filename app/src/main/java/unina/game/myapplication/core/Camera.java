package unina.game.myapplication.core;

import com.badlogic.androidgames.framework.Graphics;

public final class Camera {

    static Camera instance;

    /**
     * Returns the current active camera.
     *
     * @return current camera
     */
    public static Camera getInstance() {
        return instance;
    }

    private float x, y;
    private float halfSizeX, halfSizeY;
    private final Graphics graphics;

    Camera(Graphics graphics) {
        this.graphics = graphics;
        setPosition(0, 0);
        setSize(10);
    }

    /**
     * Sets the world space position of the camera.
     *
     * @param x x coordinate in world space
     * @param y y coordinate in world space
     */
    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Sets the horizontal world space visible from the camera.
     *
     * @param size the horizontal world units visible from the camera
     */
    public void setSize(float size) {
        this.halfSizeX = size / 2;
        this.halfSizeY = size / (2 * graphics.getAspectRatio());
    }

    /**
     * Converts the x coordinate from world space to screen space.
     *
     * @param v the x coordinate in world space
     * @return the x coordinate in screen space
     */
    public float worldToScreenX(float v) {
        return worldToViewportX(v) * graphics.getWidth();
    }

    /**
     * Converts the y coordinate from world space to screen space.
     *
     * @param v the y coordinate in world space
     * @return the y coordinate in screen space
     */
    public float worldToScreenY(float v) {
        return worldToViewportY(v) * graphics.getHeight();
    }

    public float worldToScreenSizeX(float v) {
        return graphics.getWidth() * v / (halfSizeX * 2);
    }

    public float worldToScreenSizeY(float v) {
        return graphics.getHeight() * v / (halfSizeY * 2);
    }

    public float worldToViewportX(float v) {
        return Utility.inverseLerp(x - halfSizeX, x + halfSizeX, v);
    }

    public float worldToViewportY(float v) {
        return Utility.inverseLerp(y + halfSizeY, y - halfSizeY, v);
    }

    public float screenToWorldX(float v) {
        return Utility.lerp(x - halfSizeX, x + halfSizeX, screenToViewportX(v));
    }

    public float screenToWorldY(float v) {
        return Utility.lerp(x + halfSizeY, x - halfSizeY, screenToViewportY(v));
    }

    /**
     * Converts the given x coordinate from screen space to viewport space.
     *
     * @param x screen space value
     * @return the non-clamped corresponding value in viewport space
     */
    public float screenToViewportX(float x) {
        return Utility.inverseLerp(0, graphics.getWidth(), x);
    }

    /**
     * Converts the given y coordinate from screen space to viewport space.
     *
     * @param y screen space value
     * @return the non-clamped corresponding value in viewport space
     */
    public float screenToViewportY(float y) {
        return Utility.inverseLerp(0, graphics.getHeight(), y);
    }

    /**
     * Returns the width of the screen.
     *
     * @return screen width
     */
    public float getScreenWidth() {
        return graphics.getWidth();
    }

    /**
     * Returns the height of the screen.
     *
     * @return screen height
     */
    public float getScreenHeight() {
        return graphics.getHeight();
    }

}
