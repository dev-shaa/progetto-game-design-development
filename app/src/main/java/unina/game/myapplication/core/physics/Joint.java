package unina.game.myapplication.core.physics;

import com.google.fpl.liquidfun.World;

public abstract class Joint {

    RigidBody rigidBodyA, rigidBodyB;

    Joint() {

    }

    abstract void createJoint(World world);

    void dispose(World world) {
        rigidBodyA = null;
        rigidBodyB = null;
    }

}
