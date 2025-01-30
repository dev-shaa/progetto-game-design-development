package unina.game.myapplication.logic;

import com.badlogic.androidgames.framework.Graphics;

import unina.game.myapplication.core.Camera;
import unina.game.myapplication.core.RenderComponent;

public class RockRenderComponent extends RenderComponent {

    public float radius;
    public int color;
    @Override
    public void render(float deltaTime, Graphics graphics) {
        float x = getOwner().x;
        float y = getOwner().y;
        float radius;
        Camera camera = Camera.getInstance();
        x = camera.worldToScreenX(x);
        y = camera.worldToScreenY(y);
        radius = camera.worldToScreenSizeX(this.radius);
        graphics.drawCircle(x,y,radius,color);
    }
}
