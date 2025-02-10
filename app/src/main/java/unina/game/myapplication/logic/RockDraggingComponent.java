package unina.game.myapplication.logic;

import unina.game.myapplication.core.Utility;
import unina.game.myapplication.core.physics.RigidBody;

public class RockDraggingComponent extends PressableComponent {

    public RigidBody rigidBody;
    private float sqrDistance;

    private float offsetX, offsetY;

    @Override
    protected void onPointerDown(int pointer, float x, float y) {
        super.onPointerDown(pointer, x, y);

        offsetX = rigidBody.getOwner().x - x;
        offsetY = rigidBody.getOwner().y - y;
    }

    @Override
    protected void onPointerDrag(int pointer, float x, float y) {
        super.onPointerDrag(pointer, x, y);

//        if (sqrDistance == 0)
//            return;

        rigidBody.setTransform(x, y);
    }
}
