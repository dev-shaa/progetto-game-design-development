package unina.game.myapplication.core.rendering;

import com.badlogic.androidgames.framework.Color;
import com.badlogic.androidgames.framework.Graphics;

import unina.game.myapplication.core.RenderComponent;

/**
 * Draws a circle on the screen.
 */
public final class CircleRenderer extends RenderComponent {

    public float radius = 0.5f;
    public int color = Color.WHITE;

    @Override
    public void onRemove() {
        super.onRemove();
        color = Color.WHITE;
        radius = 0.5f;
    }

    @Override
    public void render(float deltaTime, Graphics graphics) {
        graphics.drawCircle(getOwner().x, -getOwner().y, radius, color);
    }

    /**
     * Sets the radius of the circle, in world units.
     *
     * @param radius radius of the circle
     */
    public void setRadius(float radius) {
        this.radius = Math.max(0, radius);
    }

    /**
     * Sets the color tint of the circle.
     *
     * @param color color of the circle
     */
    public void setColor(int color) {
        this.color = color;
    }

}
