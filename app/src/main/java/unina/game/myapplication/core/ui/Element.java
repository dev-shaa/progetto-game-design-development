package unina.game.myapplication.core.ui;

import com.badlogic.androidgames.framework.Graphics;

import unina.game.myapplication.core.Camera;

public abstract class Element {

    public static final float ALIGNMENT_START = 0;
    public static final float ALIGNMENT_CENTER = 0.5f;
    public static final float ALIGNMENT_END = 1;

    protected float x, y;
    private float width, height;
    private boolean enabled;
    private boolean interactable;

    private float horizontalAlignment, verticalAlignment;
    private float pivotX, pivotY;

    public Element() {
        x = y = 0;
        width = height = 64;
        horizontalAlignment = verticalAlignment = ALIGNMENT_START;
        pivotX = pivotY = ALIGNMENT_START;
        enabled = interactable = true;
    }

    /**
     * Sets the position of the element.
     *
     * @param x x position in screen space
     * @param y y position in screen space
     */
    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Sets the size of the element, in pixels.
     *
     * @param width  width of the element
     * @param height height of the element
     */
    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Returns the width of the element, in pixels.
     *
     * @return width of the element
     */
    public float getWidth() {
        return width;
    }

    /**
     * Returns the height of the element, in pixels.
     *
     * @return height of the element
     */
    public float getHeight() {
        return height;
    }

    /**
     * Sets the alignment anchors of the element.
     *
     * @param horizontalAlignment horizontal anchor
     * @param verticalAlignment   vertical anchor
     */
    public void setAlignment(float horizontalAlignment, float verticalAlignment) {
        this.horizontalAlignment = horizontalAlignment;
        this.verticalAlignment = verticalAlignment;
    }

    public void setPivot(float pivotX, float pivotY) {
        this.pivotX = pivotX;
        this.pivotY = pivotY;
    }

    final boolean onClickInternal(float x, float y) {
        if (!enabled || !interactable)
            return false;

        if (!isPointerOver(x, y))
            return false;

        onClick();
        return true;
    }

    protected void onClick() {

    }

    protected void draw(Graphics graphics, Camera camera) {

    }

    /**
     * Called when the element is added to the canvas.
     */
    public void onAdd() {

    }

    /**
     * Called when the element is removed from the canvas.
     */
    public void onRemove() {

    }

    /**
     * Checks if the element is enabled and should be interested by input events.
     *
     * @return the state of the element
     */
    public final boolean isEnabled() {
        return enabled;
    }

    /**
     * Sets the state of the element.
     *
     * @param enabled new state of the element
     */
    public final void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public final boolean isInteractable() {
        return interactable;
    }

    public final void setInteractable(boolean interactable) {
        this.interactable = interactable;
    }

    private boolean isPointerOver(float x, float y) {
        float minX = getMinX();
        float minY = getMinY();
        return x > minX && x < minX + width && y > minY && y < minY + height;
    }

    protected float getMinX() {
        return Camera.getInstance().getScreenWidth() * horizontalAlignment + x - width * pivotX;
    }

    protected float getMinY() {
        return Camera.getInstance().getScreenHeight() * verticalAlignment + y - height * pivotY;
    }

}
