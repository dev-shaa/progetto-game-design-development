package unina.game.myapplication.core.ui;

import com.badlogic.androidgames.framework.Color;
import com.badlogic.androidgames.framework.Graphics;
import com.badlogic.androidgames.framework.Pixmap;
import com.badlogic.androidgames.framework.Pool;

import unina.game.myapplication.core.Camera;

public final class Button extends Element {

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

    public static Button build(Pixmap image) {
        Button button = build();

        if (image != null)
            button.image = Image.build(image);

        return button;
    }

    private Runnable onClick;
    private Image image;

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

    public Image getImage() {
        return image;
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);

        if (image != null)
            image.setPosition(x, y);
    }

    @Override
    public void setSize(float width, float height) {
        super.setSize(width, height);

        if (image != null)
            image.setSize(width, height);
    }

    @Override
    public void onAdd() {
        super.onAdd();

        if (image != null)
            image.onAdd();
    }

    @Override
    public void onRemove() {
        super.onRemove();

        if (image != null)
            image.onRemove();

        pool.free(this);
    }

    @Override
    protected void onClick() {
        if (onClick != null)
            onClick.run();
    }

    @Override
    protected void draw(Graphics graphics, Camera camera) {
        super.draw(graphics, camera);

        if (image == null)
            graphics.drawRect(getMinX(), getMinY(), getWidth(), getHeight(), Color.WHITE);
        else
            image.draw(graphics, camera);
    }

}
