package unina.game.myapplication.core.physics;

import com.google.fpl.liquidfun.RevoluteJointDef;
import com.google.fpl.liquidfun.World;

public class RevoluteJoint extends Joint {

    private com.google.fpl.liquidfun.Joint joint;

    public static RevoluteJoint build(RigidBody anchor) {
        RevoluteJoint joint = new RevoluteJoint();

        joint.rigidBodyB = anchor;

        return joint;
    }

    @Override
    void createJoint(World world) {
        RevoluteJointDef def = new RevoluteJointDef();
        def.initialize(rigidBodyA.body, rigidBodyB.body, rigidBodyA.body.getWorldCenter());

        joint = world.createJoint(def);

        def.delete();
    }

    @Override
    void dispose(World world) {
        super.dispose(world);

        world.destroyJoint(joint);
        joint = null;
    }

}
