package unina.game.myapplication.core.physics;

import com.google.fpl.liquidfun.DistanceJointDef;
import com.google.fpl.liquidfun.Joint;

import unina.game.myapplication.core.PhysicsComponent;

public class DistanceJoint extends PhysicsComponent {

    private RigidBody rigidBodyA, rigidBodyB;
    private float length, dampingRatio, frequency;
    private Joint joint;

    @Override
    public void onInitialize() {
        super.onInitialize();

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
    public void onRemove() {
        super.onRemove();

        if (joint != null) {
            world.destroyJoint(joint);
            joint = null;
        }

        world = null;
        rigidBodyA = rigidBodyB = null;
    }

    public void setRigidBodyA(RigidBody rigidBodyA) {
        this.rigidBodyA = rigidBodyA;
    }

    public void setRigidBodyB(RigidBody rigidBodyB) {
        this.rigidBodyB = rigidBodyB;
    }

    public void setLength(float length) {
        this.length = length;
    }

    public void setFrequency(float frequency) {
        this.frequency = frequency;
    }

    public void setDampingRatio(float dampingRatio) {
        this.dampingRatio = dampingRatio;
    }

}
