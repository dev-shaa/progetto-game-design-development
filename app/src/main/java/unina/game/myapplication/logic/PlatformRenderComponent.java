package unina.game.myapplication.logic;

import com.badlogic.androidgames.framework.Color;
import com.badlogic.androidgames.framework.Graphics;

import unina.game.myapplication.core.Camera;
import unina.game.myapplication.core.RenderComponent;

public class PlatformRenderComponent extends RenderComponent {

    private float width;
    private float height;
    public int color;

    private float startX, startY;
    private float endX, endY;

    @Override
    public void render(float deltaTime, Graphics graphics) {
        float x = getOwner().x;
        float y = getOwner().y;
        float width;
        float height;
        Camera camera = Camera.getInstance();
        x = camera.worldToScreenX(x);
        y = camera.worldToScreenY(y);
        width = camera.worldToScreenSizeX(this.width);
        height = camera.worldToScreenSizeY(this.height);

        graphics.drawLine(
                camera.worldToScreenX(startX), camera.worldToScreenY(startY),
                camera.worldToScreenX(endX), camera.worldToScreenY(endY), Color.WHITE);
        graphics.drawRect(x - width / 2, y - height / 2, width, height, getOwner().angle, color);
    }

    public void setStart(float x, float y) {
        startX = x;
        startY = y;
    }

    public void setEnd(float x, float y) {
        endX = x;
        endY = y;
    }

    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;
    }

}
