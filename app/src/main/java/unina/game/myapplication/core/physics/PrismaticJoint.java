package unina.game.myapplication.core.physics;

import com.badlogic.androidgames.framework.Pool;
import com.google.fpl.liquidfun.Joint;
import com.google.fpl.liquidfun.PrismaticJointDef;

import unina.game.myapplication.core.PhysicsComponent;

public class PrismaticJoint extends PhysicsComponent {

    private static final Pool<PrismaticJoint> pool = new Pool<>(PrismaticJoint::new, 16);

    public static PrismaticJoint build(RigidBody a, RigidBody b, float axisX, float axisY) {
        PrismaticJoint joint = pool.get();

        joint.a = a;
        joint.b = b;
        joint.axisX = axisX;
        joint.axisY = axisY;

        return joint;
    }

    private RigidBody a, b;
    private float axisX, axisY;
    private Joint joint;

    private PrismaticJoint() {
    }

    @Override
    public void onInitialize() {
        super.onInitialize();

        PrismaticJointDef def = new PrismaticJointDef();

        def.setBodyA(a.body);
        def.setBodyB(b.body);
        def.setLocalAnchorA(0, 0);
        def.setLocalAnchorB(0, 0);
        def.setLocalAxisA(axisX, axisY);

        joint = world.createJoint(def);

        def.delete();
    }

    @Override
    public void onRemove() {
        super.onRemove();

        world.destroyJoint(joint);
        world = null;
        joint = null;
        a = b = null;

        pool.free(this);
    }

}
