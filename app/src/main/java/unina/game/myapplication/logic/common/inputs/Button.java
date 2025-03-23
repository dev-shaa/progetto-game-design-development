package unina.game.myapplication.logic.common.inputs;

import java.util.function.Consumer;

public class Button extends PressableComponent {

    private Runnable onClick;
    private Consumer<Boolean> onInteractableChange;

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

    @Override
    public void setInteractable(boolean interactable) {
        super.setInteractable(interactable);

        if (onInteractableChange != null)
            onInteractableChange.accept(interactable);
    }

    public void setOnClick(Runnable onClick) {
        this.onClick = onClick;
    }

    public void setOnInteractableChange(Consumer<Boolean> onInteractableChange) {
        this.onInteractableChange = onInteractableChange;
    }

}
