package unina.game.myapplication.core.physics;

import com.badlogic.androidgames.framework.Pool;
import com.google.fpl.liquidfun.PrismaticJointDef;
import com.google.fpl.liquidfun.Vec2;
import com.google.fpl.liquidfun.World;

public final class PrismaticJoint extends Joint {

    private static final Pool<PrismaticJoint> pool = new Pool<>(PrismaticJoint::new, 8);

    public static PrismaticJoint build(RigidBody anchor, float axisX, float axisY) {
        PrismaticJoint joint = pool.get();

        joint.rigidBodyB = anchor;
        joint.axisX = axisX;
        joint.axisY = axisY;

        return joint;
    }

    private float axisX, axisY;
    private boolean enableLimit;
    private float lowerLimit, upperLimit;
    private com.google.fpl.liquidfun.Joint joint;

    private PrismaticJoint() {
    }

    @Override
    void createJoint(World world) {
        Vec2 axis = new Vec2(axisX, axisY);

        PrismaticJointDef def = new PrismaticJointDef();
        def.initialize(rigidBodyA.body, rigidBodyB.body, rigidBodyB.body.getWorldCenter(), axis);
        def.setLocalAnchorA(0, 0);
        def.setLocalAnchorB(0, 0);
        def.setEnableLimit(enableLimit);
        def.setLowerTranslation(lowerLimit);
        def.setUpperTranslation(upperLimit);

        joint = world.createJoint(def);

        def.delete();
        axis.delete();
    }

    @Override
    void dispose(World world) {
        super.dispose(world);

        world.destroyJoint(joint);
        joint = null;

        pool.free(this);
    }

    public void setEnableLimit(boolean enableLimit) {
        this.enableLimit = enableLimit;
    }

    public void setLowerLimit(float lowerLimit) {
        this.lowerLimit = lowerLimit;
    }

    public void setUpperLimit(float upperLimit) {
        this.upperLimit = upperLimit;
    }

}
