package unina.game.myapplication.core.physics;

import com.google.fpl.liquidfun.RopeJointDef;
import com.google.fpl.liquidfun.Vec2;

import java.util.Objects;

import unina.game.myapplication.core.PhysicsComponent;

public final class RopeJoint extends PhysicsComponent {

    private RigidBody a, b;
    private float maxLength;
    private com.google.fpl.liquidfun.RopeJoint joint;

    @Override
    public void onInitialize() {
        super.onInitialize();

        Objects.requireNonNull(a, "RigidBody A was not set before initialize");
        Objects.requireNonNull(b, "RigidBody B was not set before initialize");

        Vec2 v = new Vec2(0, 0);

        RopeJointDef def = new RopeJointDef();
        def.setBodyA(a.body);
        def.setLocalAnchorA(v);
        def.setBodyB(b.body);
        def.setLocalAnchorB(v);
        def.setMaxLength(maxLength);

        joint = world.createRopeJoint(def);
        joint.setUserData(this);

        def.delete();
        v.delete();
    }

    @Override
    public void onRemove() {
        super.onRemove();

        if (joint != null) {
            world.destroyJoint(joint);
            joint = null;
        }

        world = null;
        a = b = null;
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
