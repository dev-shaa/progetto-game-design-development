package unina.game.myapplication.core.physics;

import com.badlogic.androidgames.framework.Pool;
import com.google.fpl.liquidfun.DistanceJointDef;
import com.google.fpl.liquidfun.World;

public final class DistanceJoint extends Joint {

    private static Pool<DistanceJoint> pool;

    private float length, dampingRatio, frequency;
    private com.google.fpl.liquidfun.Joint joint;

    public static DistanceJoint build(RigidBody anchor, float length, float frequency, float dampingRatio) {
        if (pool == null)
            pool = new Pool<>(DistanceJoint::new, 4);

        DistanceJoint joint = pool.get();

        joint.rigidBodyB = anchor;
        joint.length = length;
        joint.frequency = frequency;
        joint.dampingRatio = dampingRatio;

        return joint;
    }

    private DistanceJoint() {

    }

    @Override
    void createJoint(World world) {
        DistanceJointDef def = new DistanceJointDef();

        def.setBodyA(rigidBodyA.body);
        def.setBodyB(rigidBodyB.body);
        def.setLocalAnchorA(0, 0);
        def.setLocalAnchorB(0, 0);
        def.setLength(length);
        def.setDampingRatio(dampingRatio);
        def.setFrequencyHz(frequency);

        joint = world.createJoint(def);
        joint.setUserData(this);

        def.delete();
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

}
