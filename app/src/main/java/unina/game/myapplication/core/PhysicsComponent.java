package unina.game.myapplication.core;

import com.google.fpl.liquidfun.World;

public class PhysicsComponent extends Component {

    protected World world;

    @Override
    public final Type getType() {
        return Type.PHYSICS;
    }

    public void update(float deltaTime) {

    }

}
