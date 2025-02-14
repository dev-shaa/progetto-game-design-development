package unina.game.myapplication.core;

import android.util.ArrayMap;

import com.badlogic.androidgames.framework.PoolManual;

import java.util.EnumMap;

public final class GameObject {

    private static final ArrayMap<Class<?>, PoolManual<Component>> componentPools = new ArrayMap<>();

    /**
     * Creates a GameObject without components.
     *
     * @return an empty GameObject
     */
    static GameObject create(Scene scene) {
        GameObject go = new GameObject();
        go.scene = scene;
        return go;
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

    Scene scene;

    private final EnumMap<Component.Type, Component> components = new EnumMap<>(Component.Type.class);
    private boolean initialized;

    /**
     * Creates an empty GameObject.
     */
    GameObject() {
        this.x = this.y = this.angle = 0;
    }

    /**
     * Initializes the components attached to this GameObject.
     */
    void initialize() {
        for (Component component : getComponents())
            component.onInitialize();

        initialized = true;
    }

    /**
     * Disposes this GameObject and all attached components.
     */
    void dispose() {
        for (Component component : getComponents())
            disposeComponent(component);

        components.clear();
        scene = null;
        initialized = false;
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

    /**
     * Adds a component of the given type to this GameObject.
     *
     * @param type class of the component to add
     * @param <T>  type of the component to add
     * @return the added component
     * @throws IllegalStateException if the GameObject has already been initialized
     * @throws RuntimeException      if the GameObject already has a component of the same type, or if the desired component doesn't have a public no-arg constructor
     */
    public <T extends Component> T addComponent(Class<T> type) {
        if (initialized)
            throw new IllegalStateException("GameObject was already initialized");

        try {
            PoolManual<Component> pool = componentPools.get(type);

            @SuppressWarnings("unchecked")
            T component = pool == null || pool.isEmpty() ? type.getConstructor().newInstance() : (T) pool.get();

            if (component.owner != null)
                throw new RuntimeException("Component is already owned by another GameObject");

            if (hasComponent(component.getType()))
                throw new RuntimeException("GameObject already has a component of the same type");

            component.owner = this;
            components.put(component.getType(), component);

            return component;
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Could not create instance of component, make sure it has a public no-args constructor", e);
        }
    }

    private void disposeComponent(Component component) {
        component.onRemove();

        if (component.getComponentPoolSize() > 0) {
            Class<?> type = component.getClass();
            PoolManual<Component> pool = componentPools.get(type);

            if (pool == null) {
                pool = new PoolManual<>(component.getComponentPoolSize());
                componentPools.put(type, pool);
            }

            pool.free(component);
        }
    }

}
