package unina.game.myapplication.core.physics;

import com.badlogic.androidgames.framework.Pool;
import com.google.fpl.liquidfun.CircleShape;
import com.google.fpl.liquidfun.FixtureDef;

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
        return build(radius, 1, 0.2f, 0.4f);
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
    public static CircleCollider build(float radius, float density, float restitution, float friction) {
        CircleCollider collider = pool.get();

        collider.radius = radius;
        collider.density = density;
        collider.restitution = restitution;
        collider.friction = friction;

        return collider;
    }

    private float radius;
    private float density, restitution, friction;

    private CircleCollider() {

    }

    @Override
    FixtureDef createFixture() {
        CircleShape shape = new CircleShape();
        shape.setRadius(radius);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.setShape(shape);
        fixtureDef.setDensity(density);
        fixtureDef.setRestitution(restitution);
        fixtureDef.setFriction(friction);

        return fixtureDef;
    }

    @Override
    void dispose() {
        super.dispose();
        pool.free(this);
    }

}
