package unina.game.myapplication.core;

import com.badlogic.androidgames.framework.Graphics;

public abstract class RenderComponent extends Component {

    @Override
    public final Type getType() {
        return Type.DRAWABLE;
    }

    /**
     * Draws the component on screen.
     *
     * @param graphics graphics to draw
     */
    public abstract void render(float deltaTime, Graphics graphics);

}
