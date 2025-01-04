package unina.game.myapplication.core.ui;

import com.badlogic.androidgames.framework.Color;
import com.badlogic.androidgames.framework.Graphics;
import com.badlogic.androidgames.framework.Pool;

public final class Button extends Element {

    private static final Pool<Button> pool = new Pool<>(Button::new, 8);

    public static Button build(float x, float y, float width, float height, Runnable onClick) {
        Button button = pool.get();
        button.setPosition(x, y);
        button.setSize(width, height);
        button.setOnClick(onClick);
        return button;
    }

    private Runnable onClick;
    private float width = 1, height = 1;

    private Button() {

    }

    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;
    }

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
        return x > this.x && x < this.x + width && y > this.y && y < this.y + height;
    }

    @Override
    public void draw(float deltaTime, Graphics graphics) {
        graphics.drawRect(x, y, width, height, Color.WHITE);
    }

    @Override
    public void onClick() {
        super.onClick();

        if (onClick != null)
            onClick.run();
    }

}
