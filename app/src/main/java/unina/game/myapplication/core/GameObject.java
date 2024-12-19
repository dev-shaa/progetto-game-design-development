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
    public static GameObject create() {
        return pool.get();
    }

    /**
     * Creates a GameObject with the specified components.
     *
     * @param components components to add
     * @return a GameObject with the given components
     */
    public static GameObject create(Component... components) {
        GameObject gameObject = pool.get();

        for (Component component : components) {
            component.owner = gameObject;
            gameObject.components.put(component.getType(), component);
        }

        return gameObject;
    }

    public float x;
    public float y;
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