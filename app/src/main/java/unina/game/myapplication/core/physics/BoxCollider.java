package unina.game.myapplication.core.physics;

import com.badlogic.androidgames.framework.Pool;
import com.google.fpl.liquidfun.PolygonShape;
import com.google.fpl.liquidfun.Shape;

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

        return box;
    }

    private float width, height;

    private BoxCollider() {

    }

    @Override
    Shape createShape() {
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width, height);
        return shape;
    }

    @Override
    void dispose() {
        super.dispose();
        pool.free(this);
    }

}
