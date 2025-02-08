package unina.game.myapplication.logic.common;

import com.badlogic.androidgames.framework.Color;
import com.badlogic.androidgames.framework.Graphics;

import unina.game.myapplication.core.Camera;
import unina.game.myapplication.core.RenderComponent;

public class ButtonRenderer extends RenderComponent {

    public static ButtonRenderer build(float width, float height) {
        ButtonRenderer renderer = new ButtonRenderer();
        renderer.setSize(width, height);
        return renderer;
    }

    public float width, height;
    public int color = Color.RED;

    public String text;
    public int textColor = Color.WHITE;
    public float textSize;
    public Graphics.Align textHorizontalAlign = Graphics.Align.CENTER;
    public Graphics.Align textVerticalAlign = Graphics.Align.CENTER;

    private ButtonRenderer() {

    }

    @Override
    public void render(float deltaTime, Graphics graphics) {
        float w = Camera.getInstance().worldToScreenSizeX(width);
        float h = Camera.getInstance().worldToScreenSizeY(height);
        float x = Camera.getInstance().worldToScreenX(getOwner().x);
        float y = Camera.getInstance().worldToScreenY(getOwner().y);

        graphics.drawRect(x - w / 2, y - h / 2, w, h, getOwner().angle, color);

        if (text != null)
            graphics.drawText(text, x, y, textSize, textColor, textHorizontalAlign, textVerticalAlign);
    }

    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;
    }

    public void setTextAlignment(Graphics.Align horizontal, Graphics.Align vertical) {
        this.textHorizontalAlign = horizontal;
        this.textVerticalAlign = vertical;
    }

}
