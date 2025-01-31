package unina.game.myapplication.logic;

import com.badlogic.androidgames.framework.Input;

import unina.game.myapplication.core.Camera;
import unina.game.myapplication.core.InputComponent;

public abstract class PressableComponent extends InputComponent {

    public float width, height;
    private Camera camera;

    private boolean clicked;

    @Override
    public void onInitialize() {
        super.onInitialize();
        camera = Camera.getInstance();
    }

    @Override
    public void onRemove() {
        super.onRemove();
        clicked = false;
        camera = null;
    }

    @Override
    public final void process(Input.TouchEvent event) {
        float pointerX = camera.screenToWorldX(event.x);
        float pointerY = camera.screenToWorldY(event.y);

        switch (event.type) {
            case Input.TouchEvent.TOUCH_DOWN:
                if (isPointerOver(pointerX, pointerY))
                    onPointerDown(event.pointer, pointerX, pointerY);

                clicked = true;
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

    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;
    }

    protected void onPointerDown(int pointer, float x, float y) {

    }

    protected void onPointerDrag(int pointer, float x, float y) {

    }

    protected void onPointerUp(int pointer, float x, float y) {

    }

    private boolean isPointerOver(float pointerX, float pointerY) {
        float x = getOwner().x;
        float y = getOwner().y;
        return (pointerX > x - width / 2 && pointerX < x + width / 2 && pointerY < y + height / 2 && pointerY > y - height / 2);
    }

}
