package unina.game.myapplication.logic;

import com.badlogic.androidgames.framework.Input;

import unina.game.myapplication.core.InputComponent;

public class PlatformDraggingComponent extends InputComponent {

    PlatformBehaviourComponent platformBehaviourComponent;
    @Override
    public void process(Input input) {
        if (input.isTouchDown(2)) {
            platformBehaviourComponent.hasToMove = true;
        }
    }
}
