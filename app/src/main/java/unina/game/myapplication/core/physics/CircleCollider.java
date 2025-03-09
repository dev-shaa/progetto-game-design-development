package unina.game.myapplication.core.physics;

import com.badlogic.androidgames.framework.Color;
import com.badlogic.androidgames.framework.Graphics;
import com.badlogic.androidgames.framework.Pool;
import com.google.fpl.liquidfun.CircleShape;
import com.google.fpl.liquidfun.Shape;

import unina.game.myapplication.core.Camera;

/**
 * A collider shaped as a circle.
 */
public final class CircleCollider extends Collider {

    private static final Pool<CircleCollider> pool = new Pool<>(CircleCollider::new, 16);

    /**
     * Creates a CircleCollider with the given radius.
     *
     * @param radius radius of the circle
     * @return a new CircleCollider
     */
    public static CircleCollider build(float radius) {
        return build(radius, false);
    }

    public static CircleCollider build(float radius, boolean isSensor) {
        return build(radius, 1, 0.2f, 0.4f, isSensor);
    }

    /**
     * Creates a CircleCollider with the given properties.
     *
     * @param radius      radius of the circle
     * @param density     density of the circle
     * @param restitution elasticity of the circle
     * @param friction    friction of the circle
     * @return a new CircleCollider
     */
    public static CircleCollider build(float radius, float density, float restitution, float friction, boolean isSensor) {
        CircleCollider collider = pool.get();

        collider.radius = radius;
        collider.density = density;
        collider.restitution = restitution;
        collider.friction = friction;
        collider.isSensor = isSensor;

        return collider;
    }

    private float centerX, centerY;
    private float radius;

    private CircleCollider() {

    }

    @Override
    Shape createShape() {
        CircleShape shape = new CircleShape();
        shape.setRadius(radius);
        shape.setPosition(centerX, centerY);
        return shape;
    }

    @Override
    void dispose() {
        super.dispose();
        pool.free(this);
        centerX = centerY = 0;
    }

    @Override
    void onDrawGizmos(Graphics graphics) {
        super.onDrawGizmos(graphics);

        // WARNING:
        // this is executed every frame for every collider and it is VERY expensive
        // but since this is only called in debug mode we can live with it

        float rx = getOwner().getPositionX() + centerX;
        float ry = getOwner().getPositionY() + centerY;

        graphics.drawWireCircle(rx, -ry, radius, Color.GREEN);
    }

    public void setCenter(float x, float y) {
        this.centerX = x;
        this.centerY = y;
    }

}
