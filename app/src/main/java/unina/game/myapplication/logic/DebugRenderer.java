package unina.game.myapplication.logic;

import com.badlogic.androidgames.framework.Color;
import com.badlogic.androidgames.framework.Graphics;

import unina.game.myapplication.core.Camera;
import unina.game.myapplication.core.RenderComponent;

public class DebugRenderer extends RenderComponent {

    public float width, height;
    public int color = Color.RED;

    public DebugRenderer(float width, float height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void render(float deltaTime, Graphics graphics) {
        float w = Camera.getInstance().worldToScreenSizeX(width);
        float h = Camera.getInstance().worldToScreenSizeY(height);
        float x = Camera.getInstance().worldToScreenX(getOwner().x) - w / 2;
        float y = Camera.getInstance().worldToScreenY(getOwner().y) - h / 2;

        graphics.drawRect(x, y, w, h, getOwner().angle, color);
        graphics.drawLine(graphics.getWidth() / 2, 0, graphics.getWidth() / 2, graphics.getHeight(), Color.WHITE);
        graphics.drawLine(0, graphics.getHeight() / 2, graphics.getWidth(), graphics.getHeight() / 2, Color.WHITE);
    }

}
