package unina.game.myapplication.core.physics;

import com.google.fpl.liquidfun.MouseJoint;
import com.google.fpl.liquidfun.MouseJointDef;

import java.util.Objects;

import unina.game.myapplication.core.PhysicsComponent;

public class CursorJoint extends PhysicsComponent {

    public static CursorJoint build() {
        return new CursorJoint();
    }

    private RigidBody rigidBody;
    private MouseJoint joint;
    private float x, y, maxForce;

    public CursorJoint() {

    }

    @Override
    public void onInitialize() {
        super.onInitialize();

        Objects.requireNonNull(rigidBody, "RigidBody was not set before initialize");

        MouseJointDef def = new MouseJointDef();
        def.setBodyA(rigidBody.body);
        def.setBodyB(rigidBody.body);
        def.setTarget(x, y);
        def.setMaxForce(maxForce);

        joint = world.createMouseJoint(def);

        def.delete();
    }

    @Override
    public void onRemove() {
        super.onRemove();
        world.destroyJoint(joint);
        world = null;
        joint = null;
        x = y = maxForce = 0;
    }

    public RigidBody getRigidBody() {
        return rigidBody;
    }

    /**
     * Sets the RigidBody affected by this joint.
     *
     * @param rigidBody rigidBody to affect
     * @throws IllegalStateException if this method is called after the component has been added and initialized to the scene
     */
    public void setRigidBody(RigidBody rigidBody) {
        if (joint != null)
            throw new IllegalStateException();

        this.rigidBody = rigidBody;
    }

    /**
     * Sets the target position for the body.
     *
     * @param x target x coordinate
     * @param y target y coordinate
     */
    public void setTarget(float x, float y) {
        this.x = x;
        this.y = y;

        if (joint != null)
            joint.setTarget(x, y);
    }

    /**
     * Sets the maximum force to apply.
     *
     * @param maxForce maximum force to apply
     */
    public void setMaxForce(float maxForce) {
        this.maxForce = maxForce;

        if (joint != null)
            joint.setMaxForce(maxForce);
    }

}
