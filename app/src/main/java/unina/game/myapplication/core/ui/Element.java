package unina.game.myapplication.core.ui;

import com.badlogic.androidgames.framework.Graphics;

public abstract class Element {

    public enum Alignment {
        START, CENTER, END
    }

    protected float x, y;
    protected Alignment horizontalAlignment, verticalAlignment;
    private boolean enabled;

    public Element() {
        x = y = 0;
        horizontalAlignment = verticalAlignment = Alignment.START;
        enabled = true;
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
     * Sets the alignment anchors of the element.
     *
     * @param horizontalAlignment horizontal anchor
     * @param verticalAlignment   vertical anchor
     */
    public void setAlignment(Alignment horizontalAlignment, Alignment verticalAlignment) {
        this.horizontalAlignment = horizontalAlignment;
        this.verticalAlignment = verticalAlignment;
    }

    /**
     * Checks if the given coordinates are considered over this element.
     *
     * @param x x position in screen space
     * @param y y position in screen space
     * @return {@code true} if the coordinates are over this element, {@code false} otherwise
     */
    public abstract boolean isPointerOver(float x, float y);

    /**
     * Called when the user clicks on this element.
     */
    public void onClick() {

    }

    /**
     * Draws this UI element.
     *
     * @param deltaTime elapsed time since last draw
     * @param graphics  graphics utility class
     */
    public abstract void draw(float deltaTime, Graphics graphics);

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

}
