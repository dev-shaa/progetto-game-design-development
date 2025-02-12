package unina.game.myapplication.logic.common;

import unina.game.myapplication.logic.PressableComponent;

public class Button extends PressableComponent {

    private Runnable onClick;

    public static Button build(float width, float height, Runnable onClick) {
        Button button = new Button();
        button.setSize(width, height);
        button.setOnClick(onClick);
        return button;
    }

    private Button() {

    }

    @Deprecated
    public Button(float width, float height) {
        setSize(width, height);
    }

    @Override
    public void onRemove() {
        super.onRemove();
        onClick = null;
    }

    @Override
    protected void onPointerDown(int pointer, float x, float y) {
        super.onPointerDown(pointer, x, y);

        if (onClick != null)
            onClick.run();
    }

    public void setOnClick(Runnable onClick) {
        this.onClick = onClick;
    }

}
