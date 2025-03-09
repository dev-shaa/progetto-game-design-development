package unina.game.myapplication.core.physics;

import com.badlogic.androidgames.framework.Graphics;
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

    Body body; // NOTE: this may be referred directly from other physics component, but let's keep it hidden from the rest
    private Type type = Type.DYNAMIC;
    private boolean sleepingAllowed;
    private float linearDamping;
    private final HashSet<Collider> colliders = new HashSet<>(2);
    private final HashSet<Joint> joints = new HashSet<>(2);

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

        for (Collider collider : colliders)
            collider.createFixture(body);

        for (Joint joint : joints) {
            joint.createJoint(world);
            joint.rigidBodyB.joints.add(joint);
        }

        body.setUserData(this);
        body.setLinearDamping(linearDamping);
        body.setSleepingAllowed(sleepingAllowed);
    }

    @Override
    public void onRemove() {
        super.onRemove();

        for (Joint joint : joints) {
            if (joint.rigidBodyA != joint.rigidBodyB) {
                if (joint.rigidBodyA == this)
                    joint.rigidBodyB.joints.remove(joint);
                else
                    joint.rigidBodyA.joints.remove(joint);
            }

            joint.dispose(world);
        }

        joints.clear();

        for (Collider collider : colliders)
            collider.dispose();

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

    @Override
    public void onDrawGizmos(Graphics graphics) {
        super.onDrawGizmos(graphics);

        for (Collider collider : colliders) {
            collider.onDrawGizmos(graphics);
        }
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

    /**
     * Sets the RigidBody type.
     *
     * @param type type of the body
     */
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

    /**
     * Returns the x coordinate of this body.
     *
     * @return x coordinate of the body
     */
    public float getPositionX() {
        return body == null ? getOwner().x : body.getPositionX();
    }

    /**
     * Returns the y coordinate of this body.
     *
     * @return y coordinate of the body
     */
    public float getPositionY() {
        return body == null ? getOwner().y : body.getPositionY();
    }

    /**
     * Adds a collider to this body.
     *
     * @param collider collider to add
     */
    public void addCollider(Collider collider) {
        if (collider.owner == this)
            return;

        if (collider.owner != null)
            throw new RuntimeException("Collider is already attached to another RigidBody");

        collider.owner = this;
        colliders.add(collider);

        if (body != null)
            collider.createFixture(body);
    }

    /**
     * Removes a collider from this body.
     *
     * @param collider collider to remove
     */
    public void removeCollider(Collider collider) {
        if (collider.owner == this) {
            collider.dispose();
            colliders.remove(collider);
        }
    }

    /**
     * Adds a joint to this body. This will also add the joint to the other referenced body.
     * The joint can be removed from either one.
     *
     * @param joint joint to add
     */
    public void addJoint(Joint joint) {
        if (joint.rigidBodyA != null)
            throw new RuntimeException("Joint is already attached to another RigidBody");

        joint.rigidBodyA = this;
        joints.add(joint);

        if (body != null) {
            joint.createJoint(world);
            joint.rigidBodyB.joints.add(joint);
        }
    }

    /**
     * Removes a joint from this and the other referenced body.
     *
     * @param joint joint to remove
     */
    public void removeJoint(Joint joint) {
        if (joint.rigidBodyA == this || joint.rigidBodyB == this) {
            joint.rigidBodyA.joints.remove(joint);
            joint.rigidBodyB.joints.remove(joint);

            if (body != null)
                joint.dispose(world);
        }
    }

    @Override
    public int getComponentPoolSize() {
        return 16;
    }

}
