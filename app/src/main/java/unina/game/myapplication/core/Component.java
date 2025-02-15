package unina.game.myapplication.core;

import com.badlogic.androidgames.framework.Graphics;

public abstract class Component {

    public enum Type {
        PHYSICS, INPUT, ANIMATION, RENDER, BEHAVIOUR
    }

    GameObject owner;

    Component() {
        // Package-protected constructor to be sure all components are only of the defined types.
    }

    /**
     * Returns the GameObject to which this component is attached.
     *
     * @return owner of this component
     */
    public final GameObject getOwner() {
        return owner;
    }

    /**
     * Called when the component is being added to the scene.
     */
    public void onInitialize() {

    }

    /**
     * Called when the component is being removed from the scene.
     */
    public void onRemove() {
        owner = null;
    }

    /**
     * Returns the type of the component.
     *
     * @return type of the component
     */
    public abstract Type getType();

    /**
     * Used to draw debug data on the screen during development. It will not be called if debug mode is disabled.
     *
     * @param graphics graphics data
     */
    public void onDrawGizmos(Graphics graphics) {

    }

    /**
     * Returns the maximum size of the component pool.
     *
     * @return component pool size
     */
    public int getComponentPoolSize() {
        return 3;
    }

}
