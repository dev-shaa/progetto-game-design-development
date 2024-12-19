package unina.game.myapplication.core.physics;

import unina.game.myapplication.core.PhysicsComponent;

public final class Collision {

    PhysicsComponent a, b;

    @Override
    public int hashCode() {
        return a.hashCode() ^ b.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Collision))
            return false;

        Collision otherCollision = (Collision) other;

        return (a.equals(otherCollision.a) && b.equals(otherCollision.b)) ||
                (a.equals(otherCollision.b) && b.equals(otherCollision.a));
    }

}
