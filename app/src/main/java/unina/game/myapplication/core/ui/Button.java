package unina.game.myapplication.core.ui;

import com.badlogic.androidgames.framework.Pool;

public final class Button extends PixmapElement {

    private static final Pool<Button> pool = new Pool<>(Button::new, 8);

    /**
     * Creates a UI button for a Canvas.
     *
     * @return a UI button
     */
    public static Button build() {
        Button button = pool.get();
        button.setEnabled(true);
        return button;
    }

    private Runnable onClick;

    private Button() {

    }

    /**
     * Sets the action to perform when the button is clicked.
     *
     * @param onClick action to perform on click
     */
    public void setOnClick(Runnable onClick) {
        this.onClick = onClick;
    }

    @Override
    public void onRemove() {
        super.onRemove();
        pool.free(this);
    }

    @Override
    protected void onClick() {
        if (onClick != null)
            onClick.run();
    }

}
