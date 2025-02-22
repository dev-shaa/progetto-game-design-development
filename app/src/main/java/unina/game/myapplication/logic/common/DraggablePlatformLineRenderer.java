package unina.game.myapplication.logic.common;

import com.badlogic.androidgames.framework.Color;
import com.badlogic.androidgames.framework.Graphics;

import unina.game.myapplication.core.Camera;
import unina.game.myapplication.core.RenderComponent;

public class DraggablePlatformLineRenderer extends RenderComponent {

    private float startX, startY;
    private float endX, endY;
    private float handleRadius;
    private float width;
    private int color;

    private Camera camera;

    @Override
    public void onInitialize() {
        super.onInitialize();
        camera = Camera.getInstance();
    }

    @Override
    public void onRemove() {
        super.onRemove();
        color = Color.WHITE;
        handleRadius = 0.5f;
        camera = null;
    }

    @Override
    public void render(float deltaTime, Graphics graphics) {
        float radius = camera.worldToScreenSizeX(handleRadius);
        float x1 = camera.worldToScreenX(startX);
        float y1 = camera.worldToScreenY(startY);
        float x2 = camera.worldToScreenX(endX);
        float y2 = camera.worldToScreenY(endY);
        float screenWidth = camera.worldToScreenSizeX(width);

        graphics.drawLine(x1, y1, x2, y2, screenWidth, color);
        graphics.drawCircle(x1, y1, radius, color);
        graphics.drawCircle(x2, y2, radius, color);
    }

    public void setStart(float x, float y) {
        this.startX = x;
        this.startY = y;
    }

    public void setEnd(float x, float y) {
        this.endX = x;
        this.endY = y;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setRadius(float radius) {
        this.handleRadius = radius;
    }

    public void setWidth(float width) {
        this.width = width;
    }

}
