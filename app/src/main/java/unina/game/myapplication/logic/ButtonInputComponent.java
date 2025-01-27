package unina.game.myapplication.logic;

import android.util.Log;

import com.badlogic.androidgames.framework.Input;

import unina.game.myapplication.core.Camera;
import unina.game.myapplication.core.InputComponent;

public class ButtonInputComponent extends InputComponent {

    boolean isPressed = false;
    boolean wasPressed = false;
    public float size;
    public Runnable runnable;
    public ButtonRenderComponent buttonRenderComponent;

    @Override
    public void process(Input input) {
        float x;
        float y;
        x = input.getTouchX(0);
        y = input.getTouchY(0);
        x = Camera.getInstance().screenToWorldX(x);
        y = Camera.getInstance().screenToWorldY(y);
        float butX = getOwner().x;
        float butY = getOwner().y;
        boolean inside = (x < butX+size/2 && x > butX-size/2 && y < butY+size/2 && y > butY-size/2);
        if (input.isTouchDown(0) && !wasPressed && inside) {
            isPressed = !isPressed;
            buttonRenderComponent.buttonPressed = isPressed;
            if (runnable!=null)
                runnable.run();
        }
        wasPressed = input.isTouchDown(0);
    }
}
