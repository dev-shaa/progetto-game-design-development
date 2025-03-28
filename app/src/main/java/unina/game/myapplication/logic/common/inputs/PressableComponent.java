package unina.game.myapplication.logic.common.inputs;

import com.badlogic.androidgames.framework.Color;
import com.badlogic.androidgames.framework.Graphics;
import com.badlogic.androidgames.framework.Input;

import unina.game.myapplication.core.Camera;
import unina.game.myapplication.core.InputComponent;

public abstract class PressableComponent extends InputComponent {

    public float width, height;
    public boolean interactable = true;
    private Camera camera;

    private boolean clicked;

    @Override
    public void onInitialize() {
        super.onInitialize();
        camera = Camera.getInstance();
        clicked = false;
    }

    @Override
    public void onRemove() {
        super.onRemove();
        camera = null;
        interactable = true;
    }

    @Override
    public final void process(Input.TouchEvent event) {
        if (!interactable)
            return;

        float pointerX = camera.screenToWorldX(event.x);
        float pointerY = camera.screenToWorldY(event.y);

        switch (event.type) {
            case Input.TouchEvent.TOUCH_DOWN:
                if (isPointerOver(pointerX, pointerY)) {
                    onPointerDown(event.pointer, pointerX, pointerY);
                    clicked = true;
                }
                break;
            case Input.TouchEvent.TOUCH_DRAGGED:
                if (clicked)
                    onPointerDrag(event.pointer, pointerX, pointerY);

                break;
            case Input.TouchEvent.TOUCH_UP:
                if (clicked) {
                    onPointerUp(event.pointer, pointerX, pointerY);
                    clicked = false;
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onDrawGizmos(Graphics graphics) {
        super.onDrawGizmos(graphics);
        graphics.drawWireRect(getOwner().x - width / 2, -getOwner().y - height / 2, width, height, Color.MAGENTA);
    }

    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;
    }

    public void setInteractable(boolean interactable) {
        this.interactable = interactable;
    }

    protected void onPointerDown(int pointer, float x, float y) {

    }

    protected void onPointerDrag(int pointer, float x, float y) {

    }

    protected void onPointerUp(int pointer, float x, float y) {

    }

    protected boolean isPointerOver(float pointerX, float pointerY) {
        float x = getOwner().x;
        float y = getOwner().y;
        return (pointerX > x - width / 2 && pointerX < x + width / 2 && pointerY < y + height / 2 && pointerY > y - height / 2);
    }

}
