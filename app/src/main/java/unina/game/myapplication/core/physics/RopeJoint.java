package unina.game.myapplication.core.physics;

import com.badlogic.androidgames.framework.Pool;
import com.google.fpl.liquidfun.RopeJointDef;
import com.google.fpl.liquidfun.Vec2;
import com.google.fpl.liquidfun.World;

public final class RopeJoint extends Joint {

    private static Pool<RopeJoint> pool;

    public static RopeJoint build(RigidBody anchor, float maxLength) {
        if (pool == null)
            pool = new Pool<>(RopeJoint::new, 4);

        RopeJoint joint = pool.get();

        joint.rigidBodyB = anchor;
        joint.maxLength = maxLength;

        return joint;
    }

    private float maxLength;
    private com.google.fpl.liquidfun.RopeJoint joint;

    private RopeJoint() {

    }

    @Override
    void createJoint(World world) {
        Vec2 v = new Vec2(0, 0);

        RopeJointDef def = new RopeJointDef();
        def.setBodyA(rigidBodyA.body);
        def.setBodyB(rigidBodyB.body);
        def.setLocalAnchorA(v);
        def.setLocalAnchorB(v);
        def.setMaxLength(maxLength);

        joint = world.createRopeJoint(def);
        joint.setUserData(this);

        def.delete();
        v.delete();
    }

    @Override
    void dispose(World world) {
        super.dispose(world);

        if (joint != null) {
            world.destroyJoint(joint);
            joint = null;
        }

        pool.free(this);
    }

    public void setMaxLength(float maxLength) {
        this.maxLength = maxLength;

        if (joint != null)
            joint.setMaxLength(maxLength);
    }

}
