package unina.game.myapplication.logic;

public class ButtonInputComponent extends PressableComponent {

    public Runnable runnable;
    public ButtonRenderComponent buttonRenderComponent;

    @Override
    protected void onPointerDown(int pointer, float x, float y) {
        super.onPointerDown(pointer, x, y);

        if (runnable != null) {
            if (buttonRenderComponent != null)
                buttonRenderComponent.buttonPressed = !buttonRenderComponent.buttonPressed;

            runnable.run();
        }
    }

}
