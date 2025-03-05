package unina.game.myapplication.core.rendering;

import com.badlogic.androidgames.framework.Color;
import com.badlogic.androidgames.framework.Font;
import com.badlogic.androidgames.framework.Graphics;

import unina.game.myapplication.core.Camera;
import unina.game.myapplication.core.RenderComponent;

public class TextRenderer extends RenderComponent {

    private String text;
    private float size = 11;
    private Graphics.Align horizontalAlign = Graphics.Align.START;
    private Graphics.Align verticalAlign = Graphics.Align.START;
    private int color = Color.WHITE;
    private Font font;

    private Camera camera;

    @Override
    public void onInitialize() {
        super.onInitialize();
        camera = Camera.getInstance();
    }

    @Override
    public void onRemove() {
        super.onRemove();
        text = null;
        camera = null;
        font = null;
        size = 11;
        color = Color.WHITE;
        horizontalAlign = verticalAlign = Graphics.Align.START;
    }

    @Override
    public void render(float deltaTime, Graphics graphics) {
        if (text == null)
            return;

        float x = camera.worldToScreenX(getOwner().x);
        float y = camera.worldToScreenY(getOwner().y);

        graphics.setFont(font);
        graphics.drawText(text, x, y, size, color, horizontalAlign, verticalAlign);
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public void setHorizontalAlign(Graphics.Align align) {
        this.horizontalAlign = align;
    }

    public void setVerticalAlign(Graphics.Align verticalAlign) {
        this.verticalAlign = verticalAlign;
    }

    public void setColor(int color) {
        this.color = color;
    }

}
