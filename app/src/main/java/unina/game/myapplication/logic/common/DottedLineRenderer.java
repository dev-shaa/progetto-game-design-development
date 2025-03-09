package unina.game.myapplication.logic.common;

import com.badlogic.androidgames.framework.Graphics;

import unina.game.myapplication.core.Camera;
import unina.game.myapplication.core.RenderComponent;
import unina.game.myapplication.core.Utility;

public class DottedLineRenderer extends RenderComponent {

    private float x1, y1;
    private float x2, y2;
    private int count = 2;
    private float radius;
    private int color;

    @Override
    public void render(float deltaTime, Graphics graphics) {
        for (int i = 0; i < count; i++) {
            float t = (float) i / (count - 1);
            float smx = Utility.lerp(x1, x2, t);
            float smy = Utility.lerp(y1, y2, t);
            graphics.drawCircle(smx, -smy, radius, color);
        }
    }

    public void setPointA(float x, float y) {
        this.x1 = x;
        this.y1 = y;
    }

    public void setPointB(float x, float y) {
        this.x2 = x;
        this.y2 = y;
    }

    public void setCount(int count) {
        this.count = Math.max(2, count);
    }

    public void setRadius(float radius) {
        this.radius = Math.max(0, radius);
    }

    public void setColor(int color) {
        this.color = color;
    }

}
