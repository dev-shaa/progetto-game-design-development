package unina.game.myapplication.core.physics;

import com.badlogic.androidgames.framework.Pool;
import com.google.fpl.liquidfun.PrismaticJointDef;
import com.google.fpl.liquidfun.World;

public final class PrismaticJoint extends Joint {

    private static final Pool<PrismaticJoint> pool = new Pool<>(PrismaticJoint::new, 16);

    public static PrismaticJoint build(RigidBody anchor, float axisX, float axisY) {
        PrismaticJoint joint = pool.get();

        joint.rigidBodyB = anchor;
        joint.axisX = axisX;
        joint.axisY = axisY;

        return joint;
    }

    private float axisX, axisY;
    private com.google.fpl.liquidfun.Joint joint;

    private PrismaticJoint() {
    }

    @Override
    void createJoint(World world) {
        PrismaticJointDef def = new PrismaticJointDef();

        def.setBodyA(rigidBodyA.body);
        def.setBodyB(rigidBodyB.body);
        def.setLocalAnchorA(0, 0);
        def.setLocalAnchorB(0, 0);
        def.setLocalAxisA(axisX, axisY);

        joint = world.createJoint(def);

        def.delete();
    }

    @Override
    void dispose(World world) {
        super.dispose(world);

        world.destroyJoint(joint);
        joint = null;

        pool.free(this);
    }

}
