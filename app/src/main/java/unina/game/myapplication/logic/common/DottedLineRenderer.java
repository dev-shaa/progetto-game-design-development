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

    private Camera camera;

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
        float sx1 = camera.worldToScreenX(x1);
        float sy1 = camera.worldToScreenY(y1);

        float sx2 = camera.worldToScreenX(x2);
        float sy2 = camera.worldToScreenY(y2);
        float screenRadius = camera.worldToScreenSizeX(radius);

        for (int i = 0; i < count; i++) {
            float t = (float) i / (count - 1);
            float smx = Utility.lerp(sx1, sx2, t);
            float smy = Utility.lerp(sy1, sy2, t);
            graphics.drawCircle(smx, smy, screenRadius, color);
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
