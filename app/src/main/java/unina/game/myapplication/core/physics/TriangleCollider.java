package unina.game.myapplication.core.physics;

import com.badlogic.androidgames.framework.Color;
import com.badlogic.androidgames.framework.Graphics;
import com.google.fpl.liquidfun.PolygonShape;
import com.google.fpl.liquidfun.Shape;

import unina.game.myapplication.core.Camera;

public final class TriangleCollider extends Collider {

    public static TriangleCollider build(float ax, float ay, float bx, float by, float cx, float cy) {
        TriangleCollider collider = new TriangleCollider();

        collider.ax = ax;
        collider.ay = ay;
        collider.bx = bx;
        collider.by = by;
        collider.cx = cx;
        collider.cy = cy;

        return collider;
    }

    public static TriangleCollider build(float ax, float ay, float bx, float by, float cx, float cy, float density, float restitution, float friction, boolean isSensor) {
        TriangleCollider collider = new TriangleCollider();

        collider.ax = ax;
        collider.ay = ay;
        collider.bx = bx;
        collider.by = by;
        collider.cx = cx;
        collider.cy = cy;
        collider.density = density;
        collider.restitution = restitution;
        collider.friction = friction;

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
    }

    @Override
    void onDrawGizmos(Camera camera, Graphics graphics) {
        super.onDrawGizmos(camera, graphics);

        // WARNING:
        // this is executed every frame for every collider and it is VERY expensive
        // but since this is only called in debug mode we can live with it

        float rx = camera.worldToScreenX(getOwner().getPositionX());
        float ry = camera.worldToScreenY(getOwner().getPositionY());

        float x1 = rx + camera.worldToScreenSizeX(ax);
        float y1 = ry - camera.worldToScreenSizeY(ay);

        float x2 = rx + camera.worldToScreenSizeX(bx);
        float y2 = ry - camera.worldToScreenSizeY(by);

        float x3 = rx + camera.worldToScreenSizeX(cx);
        float y3 = ry - camera.worldToScreenSizeY(cy);

        // Rotate for body rotation
        graphics.saveCanvas();
        graphics.rotateCanvas(getOwner().getOwner().angle, rx, ry);

        graphics.drawLine(x1, y1, x2, y2, Color.GREEN);
        graphics.drawLine(x2, y2, x3, y3, Color.GREEN);
        graphics.drawLine(x1, y1, x3, y3, Color.GREEN);

        graphics.restoreCanvas();

//        graphics.drawWireRect(x - w / 2, y - h / 2, w, h, isSensor() ? Color.RED : Color.GREEN);
    }

}
