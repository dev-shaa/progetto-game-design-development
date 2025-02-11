package unina.game.myapplication.core.physics;

import com.badlogic.androidgames.framework.Pool;
import com.google.fpl.liquidfun.RopeJointDef;
import com.google.fpl.liquidfun.Vec2;

import java.util.Objects;

import unina.game.myapplication.core.PhysicsComponent;

public class RopeJoint extends PhysicsComponent {

    private static final Pool<RopeJoint> pool = new Pool<>(RopeJoint::new, 16);

    public static RopeJoint build() {
        return pool.get();
    }

    private RigidBody a, b;
    private float maxLength;
    private com.google.fpl.liquidfun.RopeJoint joint;

    private RopeJoint() {
    }

    @Override
    public void onInitialize() {
        super.onInitialize();

        Objects.requireNonNull(a, "RigidBody A was not set before initialize");
        Objects.requireNonNull(b, "RigidBody B was not set before initialize");

        RopeJointDef def = new RopeJointDef();

        Vec2 v = new Vec2(0, 0);

        def.setBodyA(a.body);
        def.setLocalAnchorA(v);

        def.setBodyB(b.body);
        def.setLocalAnchorB(v);

        v.delete();

        def.setMaxLength(maxLength);

        joint = world.createRopeJoint(def);

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

    public void setRigidBodyA(RigidBody a) {
        if (joint != null)
            throw new IllegalStateException("Can't set body after joint has been initialized");

        this.a = a;
    }

    public void setRigidBodyB(RigidBody b) {
        if (joint != null)
            throw new IllegalStateException("Can't set body after joint has been initialized");

        this.b = b;
    }

    public void setMaxLength(float maxLength) {
        this.maxLength = maxLength;

        if (joint != null)
            joint.setMaxLength(maxLength);
    }

}
