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

    @Override
    public void onRemove() {
        super.onRemove();
        color = Color.WHITE;
        handleRadius = 0.5f;
    }

    @Override
    public void render(float deltaTime, Graphics graphics) {
        graphics.drawLine(startX, -startY, endX, -endY, width, color);
        graphics.drawCircle(startX, -startY, handleRadius, color);
        graphics.drawCircle(endX, -endY, handleRadius, color);
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
