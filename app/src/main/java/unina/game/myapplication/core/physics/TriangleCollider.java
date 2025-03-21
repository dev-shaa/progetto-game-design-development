package unina.game.myapplication.core.physics;

import com.badlogic.androidgames.framework.Color;
import com.badlogic.androidgames.framework.Graphics;
import com.badlogic.androidgames.framework.Pool;
import com.google.fpl.liquidfun.PolygonShape;
import com.google.fpl.liquidfun.Shape;

import unina.game.myapplication.core.Camera;

public final class TriangleCollider extends Collider {

    private static Pool<TriangleCollider> pool;

    public static TriangleCollider build(float ax, float ay, float bx, float by, float cx, float cy) {
        return build(ax, ay, bx, by, cx, cy, 1, 0.2f, 0.5f, false);
    }

    public static TriangleCollider build(float ax, float ay, float bx, float by, float cx, float cy, float density, float restitution, float friction, boolean isSensor) {
        if (pool == null)
            pool = new Pool<>(TriangleCollider::new, 8);

        TriangleCollider collider = pool.get();

        collider.ax = ax;
        collider.ay = ay;
        collider.bx = bx;
        collider.by = by;
        collider.cx = cx;
        collider.cy = cy;
        collider.density = density;
        collider.restitution = restitution;
        collider.friction = friction;
        collider.isSensor = isSensor;

        return collider;
    }

    private float ax, ay, bx, by, cx, cy;

    private TriangleCollider() {

    }

    @Override
    Shape createShape() {
        PolygonShape shape = new PolygonShape();
        shape.setAsTriangle(ax, ay, bx, by, cx, cy);
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

        float x1 = rx + ax;
        float y1 = ry - ay;

        float x2 = rx + bx;
        float y2 = ry - by;

        float x3 = rx + cx;
        float y3 = ry - cy;

        // Rotate for body rotation
        graphics.saveCanvas();
        graphics.rotateCanvas(getOwner().getOwner().angle, rx, ry);

        graphics.drawLine(x1, y1, x2, y2, Color.GREEN);
        graphics.drawLine(x2, y2, x3, y3, Color.GREEN);
        graphics.drawLine(x1, y1, x3, y3, Color.GREEN);

        graphics.restoreCanvas();
    }

}
