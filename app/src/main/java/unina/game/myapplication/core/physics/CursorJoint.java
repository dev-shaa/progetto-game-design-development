package unina.game.myapplication.core.physics;

import com.badlogic.androidgames.framework.Pool;
import com.google.fpl.liquidfun.MouseJoint;
import com.google.fpl.liquidfun.MouseJointDef;

import unina.game.myapplication.core.PhysicsComponent;

public class CursorJoint extends PhysicsComponent {

    private static final Pool<CursorJoint> pool = new Pool<>(CursorJoint::new, 16);

    public static CursorJoint build(RigidBody body) {
        return build(body, Float.POSITIVE_INFINITY);
    }

    public static CursorJoint build(RigidBody body, float maxForce) {
        CursorJoint joint = pool.get();

        joint.rigidBody = body;
        joint.maxForce = maxForce;

        return joint;
    }

    private RigidBody rigidBody;
    private MouseJoint joint;
    private float maxForce;
    private float targetX, targetY;

    private CursorJoint() {

    }

    @Override
    public void onInitialize() {
        super.onInitialize();

        MouseJointDef jointDef = new MouseJointDef();
        jointDef.setBodyA(rigidBody.body);
        jointDef.setBodyB(rigidBody.body);
        jointDef.setMaxForce(maxForce);
        jointDef.setTarget(targetX, targetY);

        joint = world.createMouseJoint(jointDef);

        jointDef.delete();
    }

    @Override
    public void onRemove() {
        super.onRemove();

        if (joint != null)
            world.destroyJoint(joint);

        world = null;
        joint = null;
        pool.free(this);
    }

    public void setTarget(float x, float y) {
        if (joint != null) {
            joint.setTarget(x, y);
        } else {
            this.targetX = x;
            this.targetY = y;
        }
    }

}
