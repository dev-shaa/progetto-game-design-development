package unina.game.myapplication.core.physics;

import com.google.fpl.liquidfun.Body;
import com.google.fpl.liquidfun.BodyDef;
import com.google.fpl.liquidfun.FixtureDef;

import unina.game.myapplication.core.GameObject;
import unina.game.myapplication.core.PhysicsComponent;

public final class RigidBody extends PhysicsComponent {

    public enum Type {
        KINEMATIC, DYNAMIC, STATIC;
    }

    /**
     * Creates a static RigidBody without a collider.
     *
     * @return a static RigidBody without a collider
     */
    public static RigidBody build() {
        return build(null);
    }

    /**
     * Creates a static RigidBody with the given collider.
     *
     * @param collider collider of the RigidBody
     * @return a static RigidBody with the given collider
     */
    public static RigidBody build(Collider collider) {
        return build(Type.STATIC, collider);
    }

    /**
     * Creates a RigidBody of the given type with the given collider.
     *
     * @param type     type of the RigidBody
     * @param collider collider of the RigidBody
     * @return a RigidBody of the given type and with the given collider
     */
    public static RigidBody build(Type type, Collider collider) {
        RigidBody component = new RigidBody();

        component.type = type;
        component.collider = collider;

        return component;
    }

    Body body;
    private Type type;
    private Collider collider;

    private RigidBody() {
        // Private constructor to avoid manual instantiation
    }

    @Override
    public void onInitialize() {
        super.onInitialize();

        BodyDef bodyDef = new BodyDef();
        bodyDef.setPosition(getOwner().x, getOwner().y);
        bodyDef.setAngle((float) Math.toRadians(getOwner().angle));

        switch (type) {
            case KINEMATIC:
                bodyDef.setType(com.google.fpl.liquidfun.BodyType.kinematicBody);
                break;
            case DYNAMIC:
                bodyDef.setType(com.google.fpl.liquidfun.BodyType.dynamicBody);
                break;
            case STATIC:
            default:
                bodyDef.setType(com.google.fpl.liquidfun.BodyType.staticBody);
                break;
        }

        body = world.createBody(bodyDef);
        bodyDef.delete();

        if (collider != null) {
            FixtureDef fixtureDef = collider.createFixture();
            body.createFixture(fixtureDef);

            fixtureDef.getShape().delete();
            fixtureDef.delete();

            collider.dispose();
            collider = null;
        }

        body.setUserData(this);
    }

    @Override
    public void onRemove() {
        super.onRemove();

        world.destroyBody(body);
        world = null;
        body = null;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        // Synchronize the GameObject transform to the actual body transform
        GameObject owner = getOwner();
        owner.x = body.getPositionX();
        owner.y = body.getPositionY();
        owner.angle = (float) Math.toDegrees(body.getAngle());
    }

}
