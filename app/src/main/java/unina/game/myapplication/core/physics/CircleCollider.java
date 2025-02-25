package unina.game.myapplication.core.physics;

import com.badlogic.androidgames.framework.Pool;
import com.google.fpl.liquidfun.CircleShape;
import com.google.fpl.liquidfun.Shape;

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

    private float radius;

    private CircleCollider() {

    }

    @Override
    Shape createShape() {
        CircleShape shape = new CircleShape();
        shape.setRadius(radius);
        return shape;
    }

    @Override
    void dispose() {
        super.dispose();
        pool.free(this);
    }

}
