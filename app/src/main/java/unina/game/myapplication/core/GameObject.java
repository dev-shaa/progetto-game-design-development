package unina.game.myapplication.core;

import com.badlogic.androidgames.framework.Pool;

import java.util.EnumMap;

public final class GameObject {

    private static final Pool<GameObject> pool = new Pool<>(GameObject::new, 32);

    /**
     * Creates a GameObject without components.
     *
     * @return an empty GameObject
     */
    static GameObject create() {
        return pool.get();
    }

    /**
     * Creates a GameObject with the specified components.
     *
     * @param components components to add
     * @return a GameObject with the given components
     */
    static GameObject create(Component... components) {
        GameObject gameObject = create();

        for (Component component : components) {
            if (component.owner != null)
                throw new RuntimeException("Component is already owned by another GameObject");

            if (gameObject.hasComponent(component.getType()))
                throw new RuntimeException("GameObject already has a component of the same type");

            component.owner = gameObject;
            gameObject.components.put(component.getType(), component);
        }

        return gameObject;
    }

    /**
     * The x coordinate of the GameObject, in world units.
     */
    public float x;

    /**
     * The y coordinate of the GameObject, in world units.
     */
    public float y;

    /**
     * The rotation angle of the GameObject, in degrees.
     */
    public float angle;

    private final EnumMap<Component.Type, Component> components = new EnumMap<>(Component.Type.class);

    /**
     * Creates an empty GameObject.
     */
    private GameObject() {
        this.x = this.y = this.angle = 0;
    }

    /**
     * Initializes the components attached to this GameObject.
     */
    void initialize() {
        for (Component component : getComponents())
            component.onInitialize();
    }

    /**
     * Disposes this GameObject and all attached components.
     */
    void dispose() {
        for (Component component : getComponents())
            component.onRemove();

        components.clear();
        pool.free(this);
    }

    /**
     * Sets the position and rotation of the GameObject. It shouldn't be used when the GameObject has a PhysicsComponent attached.
     *
     * @param x     x coordinate
     * @param y     y coordinate
     * @param angle angle in degrees
     */
    public void setTransform(float x, float y, float angle) {
        this.x = x;
        this.y = y;
        this.angle = angle;
    }

    /**
     * Checks if this GameObject has a component of the given type.
     *
     * @param type type of the component
     * @return {@code true} if the GameObject has a component of the given type, {@code false} otherwise.
     */
    public boolean hasComponent(Component.Type type) {
        return components.containsKey(type);
    }

    /**
     * Returns the component of the given type attached to this GameObject, if present.
     *
     * @param type type of the component
     * @return the component of the given type if present, {@code null} otherwise
     */
    public Component getComponent(Component.Type type) {
        return components.get(type);
    }

    /**
     * Returns an iterable object with all the components attached to this GameObject.
     *
     * @return an iterable of components
     */
    public Iterable<Component> getComponents() {
        return components.values();
    }

}
