package unina.game.myapplication.core.physics;

import com.google.fpl.liquidfun.MouseJoint;
import com.google.fpl.liquidfun.MouseJointDef;

import java.util.Objects;

import unina.game.myapplication.core.PhysicsComponent;

public class CursorJoint extends PhysicsComponent {

    private RigidBody rigidBody;
    private MouseJoint joint;
    private float x, y, maxForce;

    @Override
    public void onInitialize() {
        super.onInitialize();

        Objects.requireNonNull(rigidBody, "RigidBody was not set before initialize");

        MouseJointDef def = new MouseJointDef();
        def.setBodyA(rigidBody.body);
        def.setBodyB(rigidBody.body);
        def.setTarget(rigidBody.body.getPositionX(), rigidBody.body.getPositionY());
        def.setMaxForce(maxForce);

        joint = world.createMouseJoint(def);
        joint.setUserData(this);

        // NOTE:
        // Set the target after the joint initialization, otherwise liquidfun shows incorrect behaviour
        // when the RigidBody position is not (0, 0)
        joint.setTarget(x, y);

        def.delete();
    }

    @Override
    public void onRemove() {
        super.onRemove();

        if (joint != null) {
            world.destroyJoint(joint);
            joint = null;
        }

        world = null;
        x = y = maxForce = 0;
    }

    /**
     * Returns the controlled RigidBody.
     *
     * @return the controlled RigidBody
     */
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
