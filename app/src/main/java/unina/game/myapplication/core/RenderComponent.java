package unina.game.myapplication.core;

import com.badlogic.androidgames.framework.Graphics;

public abstract class RenderComponent extends Component {

    private int layer;

    @Override
    public final Type getType() {
        return Type.RENDER;
    }

    @Override
    public void onRemove() {
        super.onRemove();
        layer = 0;
    }

    /**
     * Draws the component on screen.
     *
     * @param graphics graphics to draw
     */
    public abstract void render(float deltaTime, Graphics graphics);

    /**
     * Returns the renderer's drawing order.
     * Lower values means the component will be drawn behind other renderers.
     *
     * @return the drawing layer
     */
    public final int getLayer() {
        return layer;
    }

    /**
     * Sets the renderer's drawing order.
     * Lower values means the component will be drawn behind other renderers.
     *
     * @param layer the drawing layer
     */
    public final void setLayer(int layer) {
        this.layer = layer;
        getOwner().scene.layerDirty = true; // Not the cleanest thing to do but this is part of the core package so it's fine
    }

}
