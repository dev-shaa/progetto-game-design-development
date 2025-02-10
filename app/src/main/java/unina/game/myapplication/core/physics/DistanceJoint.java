package unina.game.myapplication.core.physics;

import com.badlogic.androidgames.framework.Pool;
import com.google.fpl.liquidfun.DistanceJointDef;
import com.google.fpl.liquidfun.Joint;

import unina.game.myapplication.core.PhysicsComponent;

public class DistanceJoint extends PhysicsComponent {

    private static final Pool<DistanceJoint> pool = new Pool<>(DistanceJoint::new, 16);

    public static DistanceJoint build(RigidBody a, RigidBody b, float length, float dampingRatio, float frequency) {
        DistanceJoint joint = pool.get();

        joint.dampingRadio = dampingRatio;
        joint.a = a;
        joint.b = b;
        joint.length = length;
        joint.frequency = frequency;

        return joint;
    }

    private RigidBody a;
    private RigidBody b;
    private float length;
    private float dampingRadio;
    private float frequency;
    private Joint joint;

    private DistanceJoint() {
    }

    @Override
    public void onInitialize() {
        super.onInitialize();

        DistanceJointDef def = new DistanceJointDef();

        def.setBodyA(a.body);
        def.setBodyB(b.body);
        def.setLocalAnchorA(0, 0);
        def.setLocalAnchorB(0, 0);
        def.setLength(length);
        def.setDampingRatio(dampingRadio);
        def.setFrequencyHz(frequency);

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
