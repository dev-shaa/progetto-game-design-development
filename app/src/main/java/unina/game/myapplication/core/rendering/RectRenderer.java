package unina.game.myapplication.core.rendering;

import com.badlogic.androidgames.framework.Color;
import com.badlogic.androidgames.framework.Graphics;

import unina.game.myapplication.core.RenderComponent;

/**
 * Draws a rectangle on screen.
 */
public final class RectRenderer extends RenderComponent {

    public float width, height;
    public float pivotX, pivotY;
    public int color;

    public RectRenderer() {
        this.width = 1;
        this.height = 1;
        this.color = Color.WHITE;
        this.pivotX = this.pivotY = 0.5f;
    }

    @Override
    public void onRemove() {
        super.onRemove();
        width = height = 1;
        pivotX = pivotY = 0.5f;
        color = Color.WHITE;
    }

    @Override
    public void render(float deltaTime, Graphics graphics) {
        float x = getOwner().x - pivotX * width;
        float y = -getOwner().y - pivotY * height;

        graphics.saveCanvas();
        graphics.rotateCanvas(getOwner().angle, x + width / 2, y + height / 2);
        graphics.drawRect(x, y, width, height, color);
        graphics.restoreCanvas();
    }

    /**
     * Sets the size of the rectangle.
     *
     * @param width  width of the rectangle
     * @param height height of the rectangle
     */
    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Sets the pivot point of the rectangle, where (0, 0) is the top left corner and (1, 1) is the bottom right.
     *
     * @param pivotX x coordinate of the pivot point
     * @param pivotY y coordinate of the pivot point
     */
    public void setPivot(float pivotX, float pivotY) {
        this.pivotX = pivotX;
        this.pivotY = pivotY;
    }

    /**
     * Sets the color of the rectangle.
     *
     * @param color color of the rectangle
     * @see Color
     */
    public void setColor(int color) {
        this.color = color;
    }

}
