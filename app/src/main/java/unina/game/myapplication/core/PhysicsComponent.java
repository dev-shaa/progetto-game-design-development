package unina.game.myapplication.core;

import com.google.fpl.liquidfun.World;

import unina.game.myapplication.core.physics.RigidBody;

public class PhysicsComponent extends Component {

    protected World world;

    @Override
    public final Type getType() {
        return Type.PHYSICS;
    }

    public void update(float deltaTime) {

    }

    public void onCollisionEnter(RigidBody other) {

    }

    public void onCollisionExit(RigidBody other) {

    }

}
