package unina.game.myapplication.core.physics;

import com.google.fpl.liquidfun.Body;
import com.google.fpl.liquidfun.BodyDef;
import com.google.fpl.liquidfun.Fixture;
import com.google.fpl.liquidfun.FixtureDef;
import com.google.fpl.liquidfun.Vec2;

import unina.game.myapplication.core.GameObject;
import unina.game.myapplication.core.PhysicsComponent;

public final class RigidBody extends PhysicsComponent {

    public enum Type {
        KINEMATIC, DYNAMIC, STATIC
    }

    Body body;
    private Fixture fixture;
    private Type type;
    private Collider collider;
    private boolean sleepingAllowed;
    private float linearDamping = 0;

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
            fixture = body.createFixture(fixtureDef);

            fixtureDef.getShape().delete();
            fixtureDef.delete();

            collider.dispose();
            collider = null;
        }

        body.setUserData(this);
        body.setLinearDamping(linearDamping);
        body.setSleepingAllowed(sleepingAllowed);
    }

    @Override
    public void onRemove() {
        super.onRemove();

        world.destroyBody(body);
        world = null;

        collider = null;
        body = null;
        fixture = null;
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

    public void setCollider(Collider collider) {
        if (body == null) {
            this.collider = collider;
        } else {
            if (fixture != null) {
                body.destroyFixture(fixture);
                fixture = null;
            }

            if (collider != null) {
                FixtureDef fixtureDef = collider.createFixture();
                fixture = body.createFixture(fixtureDef);

                fixtureDef.getShape().delete();
                fixtureDef.delete();

                collider.dispose();
            }
        }
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

    @Override
    public int getComponentPoolSize() {
        return 16;
    }
    
}
