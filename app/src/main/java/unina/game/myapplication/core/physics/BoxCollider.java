package unina.game.myapplication.core.physics;

import com.badlogic.androidgames.framework.Color;
import com.badlogic.androidgames.framework.Graphics;
import com.badlogic.androidgames.framework.Pool;
import com.google.fpl.liquidfun.PolygonShape;
import com.google.fpl.liquidfun.Shape;

import unina.game.myapplication.core.Camera;

/**
 * A collider shaped as a box.
 */
public final class BoxCollider extends Collider {

    private static Pool<BoxCollider> pool;

    public static BoxCollider build(float width, float height) {
        return build(width, height, false);
    }

    public static BoxCollider build(float width, float height, boolean isSensor) {
        return build(width, height, 1, 0.2f, 0.4f, isSensor);
    }

    public static BoxCollider build(float width, float height, float density, float restitution, float friction, boolean isSensor) {
        if (pool == null)
            pool = new Pool<>(BoxCollider::new, 16);

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
        shape.setAsBox(width, height, centerX, centerY, (float) Math.toRadians(angle));
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

        float rx = getOwner().getPositionX();
        float ry = -getOwner().getPositionY();

        float w = width * 2;
        float h = height * 2;
        float x = rx + centerX;
        float y = ry - centerY;

        // Rotate for collider local rotation
        graphics.saveCanvas();
        graphics.rotateCanvas(angle, x, y);

        // Rotate for body rotation
        graphics.saveCanvas();
        graphics.rotateCanvas(getOwner().getOwner().angle, rx, ry);

        graphics.drawWireRect(x - w / 2, y - h / 2, w, h, isSensor() ? Color.RED : Color.GREEN);

        graphics.restoreCanvas();
        graphics.restoreCanvas();
    }

    public void setCenter(float x, float y) {
        this.centerX = x;
        this.centerY = y;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

}
