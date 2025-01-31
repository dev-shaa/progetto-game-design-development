package unina.game.myapplication.logic;

import unina.game.myapplication.core.Camera;
import unina.game.myapplication.core.physics.RigidBody;

public class PlatformDraggingComponent extends PressableComponent {

    public RigidBody rigidBody;

    @Override
    protected void onPointerDrag(int pointer, float x, float y) {
        super.onPointerDrag(pointer, x, y);

        x = Camera.getInstance().screenToWorldX(x);
        y = Camera.getInstance().screenToWorldY(y);

        rigidBody.setTransform(x, y);
    }

}
