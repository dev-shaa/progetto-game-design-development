package unina.game.myapplication.logic;

import com.badlogic.androidgames.framework.Input;

import unina.game.myapplication.core.Camera;
import unina.game.myapplication.core.InputComponent;
import unina.game.myapplication.core.physics.RigidBody;

public class PlatformDraggingComponent extends InputComponent {

    public RigidBody rigidBody;
    boolean isPressed = false;
    boolean wasPressed = false;
    boolean wasPlatfromSelected = false;
    public float width;
    public float height;
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
        boolean inside = (x < butX+width/2 && x > butX-width/2 && y < butY+height/2 && y > butY-height/2);
        if (input.isTouchDown(0) && !wasPressed && inside) {
            wasPlatfromSelected = true;
        }
        else if (wasPressed && input.isTouchDown(0) && wasPlatfromSelected)
        {
            rigidBody.setTransform(x,y);
        } else if (wasPressed && !input.isTouchDown(0) && wasPlatfromSelected) {
            wasPlatfromSelected = false;
        }
        wasPressed = input.isTouchDown(0);
    }
}
