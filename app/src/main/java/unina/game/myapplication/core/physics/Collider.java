package unina.game.myapplication.core.physics;

import com.google.fpl.liquidfun.Body;
import com.google.fpl.liquidfun.Fixture;
import com.google.fpl.liquidfun.FixtureDef;
import com.google.fpl.liquidfun.Shape;

public abstract class Collider {

    RigidBody owner;
    Fixture fixture;

    protected float density, restitution, friction;
    protected boolean isSensor;

    Collider() {

    }

    abstract Shape createShape();

    /**
     * Disposes this collider.
     */
    void dispose() {
        if (fixture != null) {
            fixture.getBody().destroyFixture(fixture);
            fixture = null;
        }

        owner = null;
    }

    final void createFixture(Body body) {
        Shape shape = createShape();

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.setShape(shape);
        fixtureDef.setDensity(density);
        fixtureDef.setRestitution(restitution);
        fixtureDef.setFriction(friction);
        fixtureDef.setIsSensor(isSensor);

        fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);

        shape.delete();
        fixtureDef.delete();
    }

    public RigidBody getOwner() {
        return owner;
    }

    public final float getDensity() {
        return density;
    }

    public final void setDensity(float density) {
        this.density = density;

        if (fixture != null)
            fixture.setDensity(density);
    }

    public final float getRestitution() {
        return restitution;
    }

    public final void setRestitution(float restitution) {
        this.restitution = restitution;

        if (fixture != null)
            fixture.setRestitution(restitution);
    }

    public final float getFriction() {
        return friction;
    }

    public final void setFriction(float friction) {
        this.friction = friction;

        if (fixture != null)
            fixture.setFriction(friction);
    }

    public final boolean isSensor() {
        return isSensor;
    }

    public final void setSensor(boolean sensor) {
        isSensor = sensor;

        if (fixture != null)
            fixture.setSensor(sensor);
    }

}
