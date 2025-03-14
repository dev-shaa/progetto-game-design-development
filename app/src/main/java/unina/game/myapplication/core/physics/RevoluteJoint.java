package unina.game.myapplication.core.physics;

import com.badlogic.androidgames.framework.Pool;
import com.google.fpl.liquidfun.RevoluteJointDef;
import com.google.fpl.liquidfun.World;

public final class RevoluteJoint extends Joint {

    private static Pool<RevoluteJoint> pool;

    private com.google.fpl.liquidfun.Joint joint;

    public static RevoluteJoint build(RigidBody anchor) {
        if (pool == null)
            pool = new Pool<>(RevoluteJoint::new, 4);

        RevoluteJoint joint = pool.get();

        joint.rigidBodyB = anchor;

        return joint;
    }

    private RevoluteJoint() {

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

        if (joint != null) {
            world.destroyJoint(joint);
            joint = null;
        }

        pool.free(this);
    }

}
