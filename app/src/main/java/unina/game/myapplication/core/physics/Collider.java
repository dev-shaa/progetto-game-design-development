package unina.game.myapplication.core.physics;

import com.google.fpl.liquidfun.FixtureDef;

public abstract class Collider {

    Collider() {

    }

    /**
     * Creates a fixture definition to be added to a body. Disposing of the resulting fixture should be handled by the caller.
     *
     * @return a new fixture to be added
     */
    abstract FixtureDef createFixture();

    /**
     * Disposes this collider.
     */
    void dispose() {
    }

}
