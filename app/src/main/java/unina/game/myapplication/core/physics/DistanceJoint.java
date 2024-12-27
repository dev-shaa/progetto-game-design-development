package unina.game.myapplication.core.physics;

import com.badlogic.androidgames.framework.Pool;
import com.google.fpl.liquidfun.DistanceJointDef;
import com.google.fpl.liquidfun.Joint;
import com.google.fpl.liquidfun.Vec2;

import unina.game.myapplication.core.PhysicsComponent;

public class DistanceJoint extends PhysicsComponent {

    private static final Pool<DistanceJoint> pool = new Pool<>(DistanceJoint::new, 16);

    public static DistanceJoint build(RigidBody a, RigidBody b, float length) {
        DistanceJoint joint = pool.get();

        joint.a = a;
        joint.b = b;
        joint.length = length;

        return joint;
    }

    private RigidBody a;
    private RigidBody b;
    private float length;
    private Joint joint;

    private DistanceJoint() {
    }

    @Override
    public void onInitialize() {
        super.onInitialize();

        Vec2 anchorA = new Vec2(0, 0);
        Vec2 anchorB = new Vec2(0, 0);

        DistanceJointDef def = new DistanceJointDef();

        def.setBodyA(a.body);
        def.setBodyB(b.body);
        def.setLocalAnchorA(0, 0);
        def.setLocalAnchorB(0, 0);
        def.setLength(length);

        joint = world.createJoint(def);

        def.delete();
        anchorB.delete();
        anchorA.delete();
    }

    @Override
    public void onRemove() {
        super.onRemove();

        world.destroyJoint(joint);
        world = null;

        pool.free(this);
    }

}
