package unina.game.myapplication.logic;

import com.badlogic.androidgames.framework.Input;

import unina.game.myapplication.core.InputComponent;

public class ButtonInputComponent extends InputComponent {

    boolean isPressed = false;
    public ButtonRenderComponent buttonRenderComponent;

    @Override
    public void process(Input input) {
        if (input.isKeyPressed(0)) {
            isPressed = !isPressed;
            buttonRenderComponent.buttonPressed = true;
        }
    }
}
