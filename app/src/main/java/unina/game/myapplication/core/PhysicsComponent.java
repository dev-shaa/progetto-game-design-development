package unina.game.myapplication.core;

import com.google.fpl.liquidfun.World;

public class PhysicsComponent extends Component {

    public World world;

    @Override
    public final Type getType() {
        return Type.PHYSICS;
    }

    public void update(float deltaTime) {

    }

    public void onCollisionEnter(PhysicsComponent other) {

    }

    public void onCollisionExit(PhysicsComponent other) {

    }

}
