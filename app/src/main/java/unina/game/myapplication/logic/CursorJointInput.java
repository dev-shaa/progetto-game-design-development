package unina.game.myapplication.logic;

import unina.game.myapplication.core.physics.CursorJoint;

public class CursorJointInput extends PressableComponent {

    private CursorJoint joint;

    @Override
    protected void onPointerDown(int pointer, float x, float y) {
        super.onPointerDown(pointer, x, y);
        joint.setMaxForce(5000);
    }

    @Override
    protected void onPointerDrag(int pointer, float x, float y) {
        super.onPointerDrag(pointer, x, y);
        joint.setTarget(x, y);
    }

    @Override
    protected void onPointerUp(int pointer, float x, float y) {
        super.onPointerUp(pointer, x, y);
        joint.setMaxForce(0);
    }

    public void setJoint(CursorJoint joint) {
        this.joint = joint;
    }

}
