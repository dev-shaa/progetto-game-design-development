package unina.game.myapplication.core;

public abstract class Component {

    public enum Type {
        PHYSICS, INPUT, ANIMATION, DRAWABLE, BEHAVIOUR
    }

    GameObject owner;

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

}
