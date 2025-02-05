package unina.game.myapplication.logic.common;

import com.badlogic.androidgames.framework.Graphics;

import unina.game.myapplication.core.RenderComponent;

public class FullScreenColorRenderer extends RenderComponent {

    public synchronized static FullScreenColorRenderer build() {
        return new FullScreenColorRenderer();
    }

    public int color;

    private FullScreenColorRenderer() {

    }

    @Override
    public void render(float deltaTime, Graphics graphics) {
        graphics.drawRect(0, 0, graphics.getWidth(), graphics.getHeight(), color);
    }

    public void setColor(int color) {
        this.color = color;
    }

}
