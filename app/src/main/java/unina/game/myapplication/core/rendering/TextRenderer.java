package unina.game.myapplication.core.rendering;

import com.badlogic.androidgames.framework.Color;
import com.badlogic.androidgames.framework.Font;
import com.badlogic.androidgames.framework.Graphics;

import unina.game.myapplication.core.RenderComponent;

public class TextRenderer extends RenderComponent {

    private String text;
    private float size = 11;
    private Graphics.Align horizontalAlign = Graphics.Align.START;
    private Graphics.Align verticalAlign = Graphics.Align.START;
    private int color = Color.WHITE;
    private Font font;

    @Override
    public void onRemove() {
        super.onRemove();
        text = null;
        font = null;
        size = 11;
        color = Color.WHITE;
        horizontalAlign = verticalAlign = Graphics.Align.START;
    }

    @Override
    public void render(float deltaTime, Graphics graphics) {
        if (text == null)
            return;

        // Text spacing is weird at low sizes
        // Scale it before drawing and then restore che original canvas

        float sx = 0.025f;
        float x = getOwner().x / sx;
        float y = -getOwner().y / sx;

        graphics.saveCanvas();
        graphics.scaleCanvas(sx, sx);
        graphics.translateCanvas(x, y);

        graphics.setFont(font);
        graphics.drawText(text, 0, 0, size, color, horizontalAlign, verticalAlign);

        graphics.restoreCanvas();
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
