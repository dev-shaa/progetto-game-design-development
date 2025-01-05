package unina.game.myapplication.core.ui;

import com.badlogic.androidgames.framework.Color;
import com.badlogic.androidgames.framework.Graphics;
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

    private Runnable onClick;
    private float width = 128, height = 128;
    private int color = Color.WHITE;

    private Button() {

    }

    /**
     * Sets the size of the button, in pixels.
     *
     * @param width  width of the button
     * @param height height of the button
     */
    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Sets the color of the button.
     *
     * @param color color of the button
     * @see Color
     */
    public void setColor(int color) {
        this.color = color;
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
    public boolean isPointerOver(float x, float y) {
        float minX = getMinX();
        float minY = getMinY();
        return x > minX && x < minX + width && y > minY && y < minY + height;
    }

    @Override
    public void draw(float deltaTime, Graphics graphics) {
        graphics.drawRect(getMinX(), getMinY(), width, height, color);
    }

    @Override
    public void onClick() {
        super.onClick();

        if (onClick != null)
            onClick.run();
    }

    private float getMinX() {
        float minX = 0;

        switch (horizontalAlignment) {
            case START:
                minX = x;
                break;
            case CENTER:
                minX = (Camera.getInstance().getScreenWidth() - width) / 2 + x;
                break;
            case END:
                minX = Camera.getInstance().getScreenWidth() - width - x;
                break;
        }

        return minX;
    }

    private float getMinY() {
        float minY = 0;

        switch (verticalAlignment) {
            case START:
                minY = y;
                break;
            case CENTER:
                minY = (Camera.getInstance().getScreenHeight() - height) / 2 - y;
                break;
            case END:
                minY = Camera.getInstance().getScreenHeight() - height - y;
                break;
        }

        return minY;
    }

}
