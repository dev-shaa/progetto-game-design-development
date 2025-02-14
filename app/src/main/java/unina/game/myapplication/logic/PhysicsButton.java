package unina.game.myapplication.logic;

import com.badlogic.androidgames.framework.Pool;

import unina.game.myapplication.core.BehaviourComponent;
import unina.game.myapplication.core.physics.RigidBody;

public final class PhysicsButton extends BehaviourComponent {

    private int counter;
    public Runnable onCollisionEnter;
    public Runnable onCollisionExit;

    public PhysicsButton() {

    }

    @Override
    public void onInitialize() {
        super.onInitialize();
        counter = 0;
    }

    @Override
    public void onRemove() {
        super.onRemove();
        onCollisionEnter = onCollisionExit = null;
    }

    @Override
    public void onCollisionEnter(RigidBody other) {
        super.onCollisionEnter(other);

        counter++;
        if (counter == 1 && onCollisionEnter != null)
            onCollisionEnter.run();
    }

    @Override
    public void onCollisionExit(RigidBody other) {
        super.onCollisionExit(other);

        counter--;
        if (counter == 0 && onCollisionExit != null)
            onCollisionExit.run();
    }

    public void setOnCollisionEnter(Runnable onCollisionEnter) {
        this.onCollisionEnter = onCollisionEnter;
    }

    public void setOnCollisionExit(Runnable onCollisionExit) {
        this.onCollisionExit = onCollisionExit;
    }

}
