package unina.game.myapplication.core.physics;

import com.badlogic.androidgames.framework.Pool;
import com.google.fpl.liquidfun.MouseJoint;
import com.google.fpl.liquidfun.MouseJointDef;
import com.google.fpl.liquidfun.World;

public final class CursorJoint extends Joint {

    private static Pool<CursorJoint> pool;

    public static CursorJoint build() {
        if (pool == null)
            pool = new Pool<>(CursorJoint::new, 4);

        return pool.get();
    }

    private MouseJoint joint;
    private float x, y, maxForce;

    private CursorJoint() {

    }

    @Override
    void createJoint(World world) {
        rigidBodyB = rigidBodyA;

        MouseJointDef def = new MouseJointDef();
        def.setBodyA(rigidBodyA.body);
        def.setBodyB(rigidBodyA.body);
        def.setTarget(rigidBodyA.body.getPositionX(), rigidBodyA.body.getPositionY());
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
    void dispose(World world) {
        super.dispose(world);

        if (joint != null) {
            world.destroyJoint(joint);
            joint = null;
        }

        x = y = maxForce = 0;
        pool.free(this);
    }

    /**
     * Returns the controlled RigidBody.
     *
     * @return the controlled RigidBody
     */
    public RigidBody getRigidBody() {
        return rigidBodyA;
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
