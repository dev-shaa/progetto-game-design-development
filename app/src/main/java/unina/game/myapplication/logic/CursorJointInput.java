package unina.game.myapplication.logic;

import unina.game.myapplication.core.physics.CursorJoint;

public class CursorJointInput extends PressableComponent {

    private CursorJoint joint;

    private float maxForce;
    private float offsetX, offsetY;
    private boolean snap;

    @Override
    public void onInitialize() {
        super.onInitialize();

        if (snap)
            joint.setMaxForce(maxForce);
        else
            joint.setMaxForce(0);

        offsetX = offsetY = 0;
    }

    @Override
    protected void onPointerDown(int pointer, float x, float y) {
        super.onPointerDown(pointer, x, y);

        joint.setMaxForce(maxForce);
        offsetX = joint.getRigidBody().getPositionX() - x;
        offsetY = joint.getRigidBody().getPositionY() - y;

        joint.setTarget(x + offsetX, y + offsetY);
    }

    @Override
    protected void onPointerDrag(int pointer, float x, float y) {
        super.onPointerDrag(pointer, x, y);
        joint.setTarget(x + offsetX, y + offsetY);
    }

    @Override
    protected void onPointerUp(int pointer, float x, float y) {
        super.onPointerUp(pointer, x, y);

        if (snap)
            joint.setTarget(joint.getRigidBody().getPositionX(), joint.getRigidBody().getPositionY());
        else
            joint.setMaxForce(0);

        offsetX = 0;
        offsetY = 0;
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

    public void setSnap(boolean snap) {
        this.snap = snap;
    }

}
