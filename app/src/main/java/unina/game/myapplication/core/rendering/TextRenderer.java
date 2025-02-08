package unina.game.myapplication.core.rendering;

import com.badlogic.androidgames.framework.Color;
import com.badlogic.androidgames.framework.Graphics;

import unina.game.myapplication.core.Camera;
import unina.game.myapplication.core.RenderComponent;

public class TextRenderer extends RenderComponent {

    public static TextRenderer build() {
        return new TextRenderer();
    }

    private String text;
    private float size = 11;
    private Graphics.Align align = Graphics.Align.END;

    private Camera camera;

    private TextRenderer() {

    }

    @Override
    public void onInitialize() {
        super.onInitialize();
        camera = Camera.getInstance();
    }

    @Override
    public void onRemove() {
        super.onRemove();
        camera = null;
    }

    @Override
    public void render(float deltaTime, Graphics graphics) {
        if (text != null) {
            float x = camera.worldToScreenX(getOwner().x);
            float y = camera.worldToScreenY(getOwner().y);
            graphics.drawText(text, x, y, size, Color.WHITE, align, Graphics.Align.START);
        }
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public void setAlign(Graphics.Align align) {
        this.align = align;
    }

}
