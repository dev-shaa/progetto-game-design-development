package unina.game.myapplication.core.physics;

import com.google.fpl.liquidfun.Body;
import com.google.fpl.liquidfun.BodyDef;
import com.google.fpl.liquidfun.Vec2;

import java.util.HashSet;

import unina.game.myapplication.core.GameObject;
import unina.game.myapplication.core.PhysicsComponent;

public final class RigidBody extends PhysicsComponent {

    public enum Type {
        KINEMATIC, DYNAMIC, STATIC
    }

    Body body;
    private Type type;
    private boolean sleepingAllowed;
    private float linearDamping;
    private final HashSet<Collider> colliders = new HashSet<>(2);

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

        for (Collider c : colliders)
            c.createFixture(body);

        body.setUserData(this);
        body.setLinearDamping(linearDamping);
        body.setSleepingAllowed(sleepingAllowed);
    }

    @Override
    public void onRemove() {
        super.onRemove();

        for (Collider c : colliders)
            c.dispose();

        colliders.clear();

        world.destroyBody(body);
        world = null;

        body = null;
        sleepingAllowed = true;
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

    /**
     * Sets the position and rotation of the RigidBody. It shouldn't be used when the body is dynamic.
     *
     * @param x     x position
     * @param y     y position
     * @param angle rotation in degrees
     */
    public void setTransform(float x, float y, float angle) {
        if (body != null)
            body.setTransform(x, y, (float) Math.toRadians(angle));
    }

    /**
     * Sets the position of the RigidBody. It shouldn't be used when the body is dynamic.
     *
     * @param x x position
     * @param y y position
     */
    public void setTransform(float x, float y) {
        if (body != null)
            body.setTransform(x, y, body.getAngle());
    }

    @Deprecated
    public void setCollider(Collider collider) {
        addCollider(collider);
    }

    public void setType(Type type) {
        this.type = type;

        if (body != null) {
            switch (type) {
                case KINEMATIC:
                    body.setType(com.google.fpl.liquidfun.BodyType.kinematicBody);
                    break;
                case DYNAMIC:
                    body.setType(com.google.fpl.liquidfun.BodyType.dynamicBody);
                    break;
                case STATIC:
                default:
                    body.setType(com.google.fpl.liquidfun.BodyType.staticBody);
                    break;
            }
        }
    }

    public void setSleepingAllowed(boolean sleepingAllowed) {
        this.sleepingAllowed = sleepingAllowed;

        if (body != null)
            body.setSleepingAllowed(sleepingAllowed);
    }

    public void setLinearDamping(float linearDamping) {
        this.linearDamping = linearDamping;

        if (body != null)
            body.setLinearDamping(linearDamping);
    }

    public void addForce(float forceX, float forceY) {
        if (body != null) {
            Vec2 force = new Vec2(forceX, forceY);
            body.applyForceToCenter(force, true);
            force.delete();
        }
    }

    public float getPositionX() {
        return body == null ? getOwner().x : body.getPositionX();
    }

    public float getPositionY() {
        return body == null ? getOwner().y : body.getPositionY();
    }

    public void addCollider(Collider collider) {
        if (collider.owner == this)
            return;

        if (collider.owner != null)
            throw new RuntimeException();

        collider.owner = this;
        colliders.add(collider);

        if (body != null)
            collider.createFixture(body);
    }

    public void removeCollider(Collider collider) {
        if (collider.owner == this) {
            collider.dispose();
            colliders.remove(collider);
        }
    }

    @Override
    public int getComponentPoolSize() {
        return 16;
    }

}
