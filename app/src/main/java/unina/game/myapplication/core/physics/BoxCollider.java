package unina.game.myapplication.core.physics;

import android.util.Log;

import com.badlogic.androidgames.framework.Pool;
import com.google.fpl.liquidfun.FixtureDef;
import com.google.fpl.liquidfun.PolygonShape;

/**
 * A collider shaped as a 2D box.
 */
public final class BoxCollider extends Collider {

    private static final Pool<BoxCollider> pool = new Pool<>(BoxCollider::new, 16);

    /**
     * Creates a BoxCollider with the given width and height.
     *
     * @param width  width of the box
     * @param height height of the box
     * @return a new BoxCollider
     */
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

        return box;
    }

    private float width, height;
    private float density, restitution, friction;
    private boolean isSensor;

    private BoxCollider() {

    }

    @Override
    FixtureDef createFixture() {
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width, height);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.setShape(shape);
        fixtureDef.setDensity(density);
        fixtureDef.setRestitution(restitution);
        fixtureDef.setFriction(friction);
        fixtureDef.setIsSensor(isSensor);

        return fixtureDef;
    }

    @Override
    void dispose() {
        super.dispose();
        pool.free(this);
    }

}
