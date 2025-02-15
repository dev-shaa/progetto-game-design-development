package unina.game.myapplication.logic;

import com.badlogic.androidgames.framework.Color;
import com.badlogic.androidgames.framework.Graphics;

import unina.game.myapplication.core.Camera;
import unina.game.myapplication.core.Utility;
import unina.game.myapplication.core.physics.RigidBody;

public class PlatformDraggingComponent extends PressableComponent {

    public RigidBody rigidBody;

    private float startX, startY;
    private float endX, endY;
    private float sqrDistance;

    private float offsetX, offsetY;

    @Override
    protected void onPointerDown(int pointer, float x, float y) {
        super.onPointerDown(pointer, x, y);

        offsetX = rigidBody.getOwner().x - x;
        offsetY = rigidBody.getOwner().y - y;
    }

    @Override
    protected void onPointerDrag(int pointer, float x, float y) {
        super.onPointerDrag(pointer, x, y);

        if (sqrDistance == 0)
            return;

        float t = ((x + offsetX - startX) * (endX - startX) + (y + offsetY - startY) * (endY - startY)) / sqrDistance;
        t = Utility.clamp(0, 1, t);

        float projectionX = Utility.lerp(startX, endX, t);
        float projectionY = Utility.lerp(startY, endY, t);

        rigidBody.setTransform(projectionX, projectionY);
    }

    public void setStart(float x, float y) {
        startX = x;
        startY = y;
        sqrDistance = (startX - endX) * (startX - endX) + (startY - endY) * (startY - endY);
    }

    public void setEnd(float x, float y) {
        endX = x;
        endY = y;
        sqrDistance = (startX - endX) * (startX - endX) + (startY - endY) * (startY - endY);
    }

    @Override
    public void onDrawGizmos(Graphics graphics) {
        super.onDrawGizmos(graphics);

        float x1 = Camera.getInstance().worldToScreenX(startX);
        float y1 = Camera.getInstance().worldToScreenY(startY);
        float x2 = Camera.getInstance().worldToScreenX(endX);
        float y2 = Camera.getInstance().worldToScreenY(endY);

        graphics.drawLine(x1, y1, x2, y2, Color.MAGENTA);
    }
}
