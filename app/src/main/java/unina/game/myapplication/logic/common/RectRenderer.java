package unina.game.myapplication.logic.common;

import com.badlogic.androidgames.framework.Color;
import com.badlogic.androidgames.framework.Graphics;

import unina.game.myapplication.core.Camera;
import unina.game.myapplication.core.RenderComponent;

public class RectRenderer extends RenderComponent {

    public static RectRenderer build() {
        return new RectRenderer();
    }

    public float width, height;
    public float pivotX, pivotY;
    public int color;

    private Camera camera;

    public RectRenderer() {
        this.width = 1;
        this.height = 1;
        this.color = Color.WHITE;
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
        width = height = 1;
        pivotX = pivotY = 0.5f;
        color = Color.WHITE;
    }

    @Override
    public void render(float deltaTime, Graphics graphics) {
        float w = camera.worldToScreenSizeX(width);
        float h = camera.worldToScreenSizeY(height);
        float x = camera.worldToScreenX(getOwner().x) - pivotX * w;
        float y = camera.worldToScreenY(getOwner().y) - pivotY * h;

        graphics.drawRect(x, y, w, h, color);
    }

    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;
    }

    public void setPivot(float pivotX, float pivotY) {
        this.pivotX = pivotX;
        this.pivotY = pivotY;
    }

    public void setColor(int color) {
        this.color = color;
    }

}
