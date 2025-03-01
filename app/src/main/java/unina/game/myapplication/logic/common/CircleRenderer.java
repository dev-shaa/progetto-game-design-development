package unina.game.myapplication.logic.common;

import com.badlogic.androidgames.framework.Color;
import com.badlogic.androidgames.framework.Graphics;

import unina.game.myapplication.core.Camera;
import unina.game.myapplication.core.RenderComponent;

/**
 * Draws a circle on the screen.
 */
public class CircleRenderer extends RenderComponent {

    public float radius = 0.5f;
    public int color = Color.WHITE;

    private Camera camera;

    @Override
    public void onInitialize() {
        super.onInitialize();
        camera = Camera.getInstance();
    }

    @Override
    public void onRemove() {
        super.onRemove();
        camera = null;
        color = Color.WHITE;
        radius = 0.5f;
    }

    @Override
    public void render(float deltaTime, Graphics graphics) {
        float r = camera.worldToScreenSizeX(radius);
        float x = camera.worldToScreenX(getOwner().x);
        float y = camera.worldToScreenY(getOwner().y);

        graphics.drawCircle(x, y, r, color);
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
