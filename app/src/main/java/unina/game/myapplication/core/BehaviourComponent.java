package unina.game.myapplication.core;

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
    public abstract void update(float deltaTime);

}
