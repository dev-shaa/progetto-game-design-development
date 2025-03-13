package unina.game.myapplication.logic;

import com.badlogic.androidgames.framework.Color;
import com.badlogic.androidgames.framework.Graphics;

import unina.game.myapplication.core.GameObject;
import unina.game.myapplication.core.RenderComponent;

public class LineRenderer extends RenderComponent {

    private GameObject a, b;
    private float width = 0;
    private int color = Color.WHITE;

    @Override
    public void render(float deltaTime, Graphics graphics) {
        graphics.drawLine(a.x, -a.y, b.x, -b.y, width, color);
    }

    @Override
    public void onRemove() {
        super.onRemove();
        a = b = null;
        width = 0;
        color = Color.WHITE;
    }

    public void setA(GameObject a) {
        this.a = a;
    }

    public void setB(GameObject b) {
        this.b = b;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setColor(int color) {
        this.color = color;
    }

}
