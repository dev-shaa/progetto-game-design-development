package unina.game.myapplication.logic;

import com.badlogic.androidgames.framework.Color;
import com.badlogic.androidgames.framework.Graphics;

import unina.game.myapplication.core.Camera;
import unina.game.myapplication.core.RenderComponent;

public class DebugRenderer extends RenderComponent {

    public float width, height;
    public int color = Color.RED;
    public float pivotX, pivotY;

    private Camera camera;

    public DebugRenderer() {
        this(1, 1);
    }

    public DebugRenderer(float width, float height) {
        this.width = width;
        this.height = height;
        this.pivotX = this.pivotY = 0.5f;
    }

    @Override
    public void onInitialize() {
        super.onInitialize();
        camera = Camera.getInstance();
    }

    @Override
    public void onRemove() {
        super.onRemove();
        camera = null;
    }

    @Override
    public void render(float deltaTime, Graphics graphics) {
        float w = camera.worldToScreenSizeX(width);
        float h = camera.worldToScreenSizeY(height);
        float x = camera.worldToScreenX(getOwner().x) - pivotX * w;
        float y = camera.worldToScreenY(getOwner().y) - pivotY * h;

        graphics.drawRect(x, y, w, h, getOwner().angle, x, y, color);
//        graphics.drawLine(graphics.getWidth() / 2, 0, graphics.getWidth() / 2, graphics.getHeight(), Color.BLUE);
//        graphics.drawLine(0, graphics.getHeight() / 2, graphics.getWidth(), graphics.getHeight() / 2, Color.BLUE);
    }

    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;
    }

    public void setColor(int color) {
        this.color = color;
    }
    
}
