package unina.game.myapplication.core;

import unina.game.myapplication.core.physics.RigidBody;

/**
 * A component focused on executing entities logic.
 */
public abstract class BehaviourComponent extends Component {

    @Override
    public final Type getType() {
        return Type.BEHAVIOUR;
    }

    /**
     * Method called each frame.
     *
     * @param deltaTime time elapsed since the last frame, in seconds.
     */
    public void update(float deltaTime) {

    }

    /**
     * Called when another GameObject starts colliding with this.
     * It will only be called if a RigidBody is attached to the same GameObject.
     *
     * @param other the other GameObject
     */
    public void onCollisionEnter(RigidBody other, float relativeVelocityX, float relativeVelocityY) {

    }

    /**
     * Called when another GameObject stops colliding with this.
     * It will only be called if a RigidBody is attached to the same GameObject.
     *
     * @param other the other GameObject
     */
    public void onCollisionExit(RigidBody other, float relativeVelocityX, float relativeVelocityY) {

    }


}
