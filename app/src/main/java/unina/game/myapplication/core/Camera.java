package unina.game.myapplication.core;

import com.badlogic.androidgames.framework.Graphics;

public final class Camera extends RenderComponent {

    private static Camera instance;

    /**
     * Returns the current active camera.
     *
     * @return current camera
     */
    public static Camera getInstance() {
        return instance;
    }

    private float halfSizeX, halfSizeY;
    private Graphics graphics;

    public Camera() {
        if (instance == null)
            instance = this;
    }

    @Override
    public void onRemove() {
        super.onRemove();

        graphics.restoreCanvas();
        graphics = null;

        if (instance == this)
            instance = null;
    }

    @Override
    public void render(float deltaTime, Graphics graphics) {
        graphics.restoreCanvas();
        graphics.saveCanvas();

        float sx = graphics.getWidth() / (halfSizeX * 2);

        graphics.translateCanvas((float) graphics.getWidth() / 2, (float) graphics.getHeight() / 2);
        graphics.scaleCanvas(sx, sx);
        graphics.translateCanvas(-getOwner().x, getOwner().y);
    }

    void setGraphics(Graphics graphics) {
        this.graphics = graphics;
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

    public float getSizeX() {
        return halfSizeX * 2;
    }

    public float getSizeY() {
        return halfSizeY * 2;
    }

    public float worldToScreenX(float v) {
        return worldToViewportX(v) * graphics.getWidth();
    }

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
        return Utility.inverseLerp(getOwner().x - halfSizeX, getOwner().x + halfSizeX, v);
    }

    public float worldToViewportY(float v) {
        return Utility.inverseLerp(getOwner().y + halfSizeY, getOwner().y - halfSizeY, v);
    }

    public float screenToWorldX(float v) {
        return Utility.lerp(getOwner().x - halfSizeX, getOwner().x + halfSizeX, screenToViewportX(v));
    }

    public float screenToWorldY(float v) {
        return Utility.lerp(getOwner().y + halfSizeY, getOwner().y - halfSizeY, screenToViewportY(v));
    }

    public float screenToViewportX(float x) {
        return Utility.inverseLerp(0, graphics.getWidth(), x);
    }

    public float screenToViewportY(float y) {
        return Utility.inverseLerp(0, graphics.getHeight(), y);
    }

    @Override
    public int getComponentPoolSize() {
        return 0;
    }

}
