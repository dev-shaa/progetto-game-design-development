package unina.game.myapplication.logic;

import unina.game.myapplication.core.physics.CursorJoint;

public class CursorJointInput extends PressableComponent {

    private CursorJoint joint;

    private float maxForce;

    @Override
    public void onInitialize() {
        super.onInitialize();
        joint.setMaxForce(0);
    }

    @Override
    protected void onPointerDown(int pointer, float x, float y) {
        super.onPointerDown(pointer, x, y);
        joint.setMaxForce(maxForce);
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

    @Override
    protected boolean isPointerOver(float pointerX, float pointerY) {
        float x = joint.getRigidBody().getOwner().x;
        float y = joint.getRigidBody().getOwner().y;
        return (pointerX > x - width / 2 && pointerX < x + width / 2 && pointerY < y + height / 2 && pointerY > y - height / 2);
    }

    public void setMaxForce(float maxForce) {
        this.maxForce = maxForce;
    }
}
