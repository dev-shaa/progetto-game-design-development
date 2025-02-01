package unina.game.myapplication.logic;

import com.badlogic.androidgames.framework.Color;
import com.badlogic.androidgames.framework.Graphics;

import unina.game.myapplication.core.Camera;
import unina.game.myapplication.core.RenderComponent;

public class PlatformRenderComponent extends RenderComponent {

    public float width;
    public float height;
    public int color;

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
        graphics.drawRect(x - width / 2, y - height / 2, width, height, getOwner().angle, color);
    }
}
