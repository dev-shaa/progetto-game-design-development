package unina.game.myapplication.core.physics;

import com.badlogic.androidgames.framework.Color;
import com.badlogic.androidgames.framework.Graphics;
import com.badlogic.androidgames.framework.Pool;
import com.google.fpl.liquidfun.PolygonShape;
import com.google.fpl.liquidfun.Shape;

import unina.game.myapplication.core.Camera;

/**
 * A collider shaped as a 2D box.
 */
public final class BoxCollider extends Collider {

    private static final Pool<BoxCollider> pool = new Pool<>(BoxCollider::new, 16);

    public static BoxCollider build(float width, float height) {
        return build(width, height, false);
    }

    public static BoxCollider build(float width, float height, boolean isSensor) {
        return build(width, height, 1, 0.2f, 0.4f, isSensor);
    }

    public static BoxCollider build(float width, float height, float density, float restitution, float friction, boolean isSensor) {
        BoxCollider box = pool.get();

        box.width = width / 2;
        box.height = height / 2;
        box.density = density;
        box.restitution = restitution;
        box.friction = friction;
        box.isSensor = isSensor;
        box.centerX = 0;
        box.centerY = 0;
        box.angle = 0;

        return box;
    }

    private float width, height;
    private float centerX, centerY;
    private float angle;

    private BoxCollider() {

    }

    @Override
    Shape createShape() {
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width, height, centerX, centerY, angle);
        return shape;
    }

    @Override
    void dispose() {
        super.dispose();
        pool.free(this);
    }

    @Override
    void onDrawGizmos(Graphics graphics) {
        super.onDrawGizmos(graphics);

        // WARNING:
        // this is executed every frame for every collider and it is VERY expensive
        // but since this is only called in debug mode we can live with it

        float rx = Camera.getInstance().worldToScreenX(getOwner().getPositionX());
        float ry = Camera.getInstance().worldToScreenY(getOwner().getPositionY());

        float w = Camera.getInstance().worldToScreenSizeX(width * 2);
        float h = Camera.getInstance().worldToScreenSizeY(height * 2);
        float x = rx + Camera.getInstance().worldToScreenSizeX(centerX) - w / 2;
        float y = ry - Camera.getInstance().worldToScreenSizeY(centerY) - h / 2;

        graphics.drawWireRect(x, y, w, h, getOwner().getOwner().angle, rx, ry, Color.GREEN);
    }

    public void setCenter(float x, float y) {
        this.centerX = x;
        this.centerY = y;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

}
