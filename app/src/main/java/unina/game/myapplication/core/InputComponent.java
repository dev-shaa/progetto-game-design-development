package unina.game.myapplication.core;

import com.badlogic.androidgames.framework.Input;

/**
 * Component which processes user inputs.
 */
public abstract class InputComponent extends Component {

    @Override
    public final Type getType() {
        return Type.INPUT;
    }

    /**
     * Called every frame when the input is ready to be processed.
     *
     * @param input input to process
     */
    public abstract void process(Input input);

}
