package unina.game.myapplication.core;

import com.badlogic.androidgames.framework.Graphics;

public abstract class RenderComponent extends Component {

    private int layer;
    Scene scene;

    @Override
    public final Type getType() {
        return Type.RENDER;
    }

    @Override
    public void onRemove() {
        super.onRemove();
        scene = null;
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

        if (scene != null)
            scene.layerDirty = true;
    }

}
