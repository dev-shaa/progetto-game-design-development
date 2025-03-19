package unina.game.myapplication.logic.common;

import unina.game.myapplication.core.BehaviourComponent;
import unina.game.myapplication.core.physics.RigidBody;

public final class PressurePlate extends BehaviourComponent {

    private int counter;
    public Runnable onCollisionEnter;
    public Runnable onCollisionExit;

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
    public void onCollisionEnter(RigidBody other, float relativeVelocityX, float relativeVelocityY) {
        super.onCollisionEnter(other, relativeVelocityX, relativeVelocityY);

        counter++;
        if (counter == 1 && onCollisionEnter != null)
            onCollisionEnter.run();
    }

    @Override
    public void onCollisionExit(RigidBody other, float relativeVelocityX, float relativeVelocityY) {
        super.onCollisionExit(other, relativeVelocityX, relativeVelocityY);

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
