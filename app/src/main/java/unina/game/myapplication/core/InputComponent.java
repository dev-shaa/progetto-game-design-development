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

    public abstract void process(Input.TouchEvent event);

}
