package unina.game.myapplication.logic;

import com.badlogic.androidgames.framework.Color;
import com.badlogic.androidgames.framework.Graphics;

import unina.game.myapplication.core.Camera;
import unina.game.myapplication.core.RenderComponent;

public class ButtonRenderComponent extends RenderComponent {
    
    public float edge;
    public float radius;
    public boolean buttonPressed = false;
    @Override
    public void render(float deltaTime, Graphics graphics) {
        float x = getOwner().x;
        float y = getOwner().y;
        float edge;
        float radius;
        int color;
        Camera camera = Camera.getInstance();
        x = camera.worldToScreenX(x);
        y = camera.worldToScreenY(y);
        edge = camera.worldToScreenSizeX(this.edge);
        radius = Camera.getInstance().worldToScreenSizeX(this.radius);
        graphics.drawRect(x-(float)edge/2,y-(float)edge/2,edge,edge, Color.GREY);
        if (buttonPressed)
            color = Color.GREEN;
        else
            color = Color.RED;
        graphics.drawCircle(x,y,radius,color);
    }
}
