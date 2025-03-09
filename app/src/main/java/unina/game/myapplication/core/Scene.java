package unina.game.myapplication.core;

import android.util.ArraySet;

import com.badlogic.androidgames.framework.Color;
import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Graphics;
import com.badlogic.androidgames.framework.Input;
import com.badlogic.androidgames.framework.Music;
import com.badlogic.androidgames.framework.Pixmap;
import com.badlogic.androidgames.framework.Screen;
import com.badlogic.androidgames.framework.Sound;
import com.google.fpl.liquidfun.World;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import unina.game.myapplication.core.physics.CollisionListener;

public abstract class Scene extends Screen {

    public static final boolean DEBUG = true; // TODO: SET TO FALSE WHEN BUILDING RELEASE VERSION

    private static final int VELOCITY_ITERATIONS = 8, POSITION_ITERATIONS = 3, PARTICLE_ITERATIONS = 3;
    private static final float GRAVITY_X = 0, GRAVITY_Y = -9.82f;

    private World world;
    private CollisionListener collisionListener;
    private GameObject camera;

    // TODO: check for possible better collection
    private final ArraySet<GameObject> gameObjects = new ArraySet<>(8);
    private final ArraySet<InputComponent> inputComponents = new ArraySet<>(4);
    private final ArraySet<PhysicsComponent> physicsComponents = new ArraySet<>(4);
    private final ArraySet<BehaviourComponent> behaviourComponents = new ArraySet<>(4);
    private final ArraySet<AnimationComponent> animationComponents = new ArraySet<>(4);
    private final ArrayList<RenderComponent> renderComponents = new ArrayList<>(8);

    private Scene sceneToBeLoaded;
    boolean layerDirty = false;

    private final BitSet gameObjectsOperations = new BitSet();
    private final ArrayList<GameObject> gameObjectsToOperate = new ArrayList<>();

    private int clearColor = Color.BLACK;
    private final HashMap<String, Pixmap> images = new HashMap<>();
    private final HashMap<String, Sound> sounds = new HashMap<>();
    private final HashMap<String, Music> musics = new HashMap<>();

    public Scene(Game game) {
        super(game);
    }

    @Override
    public void initialize() {
        collisionListener = new CollisionListener();

        world = new World(GRAVITY_X, GRAVITY_Y);
        world.setContactListener(collisionListener);

        camera = GameObject.create(this);
        Camera cameraComponent = camera.addComponent(Camera.class);
        cameraComponent.setGraphics(game.getGraphics());
        cameraComponent.setSize(10);
        camera.initialize();

        sceneToBeLoaded = null;
    }

    @Override
    public final void update(float deltaTime) {
        if (sceneToBeLoaded != null) {
            game.setScreen(sceneToBeLoaded);
            return;
        }

        applySceneChanges();

        world.step(deltaTime, VELOCITY_ITERATIONS, POSITION_ITERATIONS, PARTICLE_ITERATIONS);

        // Process physics component
        for (int i = 0; i < physicsComponents.size(); i++)
            physicsComponents.valueAt(i).update(deltaTime);

        collisionListener.forEachEnter(collision -> {
            if (collision.a.getOwner().hasComponent(Component.Type.BEHAVIOUR)) {
                BehaviourComponent behaviour = (BehaviourComponent) collision.a.getOwner().getComponent(Component.Type.BEHAVIOUR);
                behaviour.onCollisionEnter(collision.b, collision.relativeVelocityX, collision.relativeVelocityY);
            }

            if (collision.b.getOwner().hasComponent(Component.Type.BEHAVIOUR)) {
                BehaviourComponent behaviour = (BehaviourComponent) collision.b.getOwner().getComponent(Component.Type.BEHAVIOUR);
                behaviour.onCollisionEnter(collision.a, collision.relativeVelocityX, collision.relativeVelocityY);
            }
        });

        collisionListener.forEachExit(collision -> {
            if (collision.a.getOwner().hasComponent(Component.Type.BEHAVIOUR)) {
                BehaviourComponent behaviour = (BehaviourComponent) collision.a.getOwner().getComponent(Component.Type.BEHAVIOUR);
                behaviour.onCollisionExit(collision.b, collision.relativeVelocityX, collision.relativeVelocityY);
            }

            if (collision.b.getOwner().hasComponent(Component.Type.BEHAVIOUR)) {
                BehaviourComponent behaviour = (BehaviourComponent) collision.b.getOwner().getComponent(Component.Type.BEHAVIOUR);
                behaviour.onCollisionExit(collision.a, collision.relativeVelocityX, collision.relativeVelocityY);
            }
        });

        // Process input components
        List<Input.TouchEvent> events = game.getInput().getTouchEvents();

        for (int i = 0; i < events.size(); i++) {
            Input.TouchEvent event = events.get(i);

            for (int j = 0; j < inputComponents.size(); j++)
                inputComponents.valueAt(j).process(event);
        }

        // Process behaviour components
        for (int i = 0; i < behaviourComponents.size(); i++)
            behaviourComponents.valueAt(i).update(deltaTime);

        // Process animation components
        for (int i = 0; i < animationComponents.size(); i++)
            animationComponents.valueAt(i).update(deltaTime);
    }

    @Override
    public final void present(float deltaTime) {
        Graphics graphics = game.getGraphics();

        // Clear the screen
        graphics.clear(clearColor);

        // Sort the renderers by layer
        if (layerDirty) {
            renderComponents.sort(Comparator.comparingInt(RenderComponent::getLayer));
            layerDirty = false;
        }

        Camera.getInstance().update();

        // Render each component
        for (int i = 0; i < renderComponents.size(); i++)
            renderComponents.get(i).render(deltaTime, graphics);

        // DEBUG ONLY: draw gizmos
        if (DEBUG) {
            for (GameObject gameObject : gameObjects) {
                for (Component component : gameObject.getComponents())
                    component.onDrawGizmos(graphics);
            }
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        sceneToBeLoaded = null;

        for (Music music : musics.values())
            music.dispose();

        for (Sound sounds : sounds.values())
            sounds.dispose();

        for (Pixmap image : images.values())
            image.dispose();

        musics.clear();
        sounds.clear();
        images.clear();

        for (GameObject gameObject : gameObjects)
            gameObject.dispose();

        for (GameObject gameObject : gameObjectsToOperate)
            gameObject.dispose();

        world.delete();

        renderComponents.clear();
        inputComponents.clear();
        physicsComponents.clear();
        behaviourComponents.clear();
        animationComponents.clear();

        gameObjects.clear();
        gameObjectsToOperate.clear();
        gameObjectsOperations.clear();

        camera.dispose();
    }

    /**
     * Loads the scene of the specified class.
     *
     * @param scene class of the scene to be loaded
     * @throws RuntimeException if the given class doesn't have an accessible constructor that accepts only a Game parameter
     */
    public final void loadScene(Class<? extends Scene> scene) {
        // It could be called multiple times during a frame, since the new scene is loaded at the start of the next one.
        // Only the first call should be considered.
        if (sceneToBeLoaded != null)
            return;

        // Reflection magic, handle with care
        try {
            sceneToBeLoaded = scene.getConstructor(Game.class).newInstance(game);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates a new GameObject which will be added in the scene at the start of the next frame.
     *
     * @return the created GameObject
     */
    public final GameObject createGameObject() {
        GameObject gameObject = GameObject.create(this);

        gameObjectsOperations.set(gameObjectsToOperate.size());
        gameObjectsToOperate.add(gameObject);

        return gameObject;
    }

    /**
     * Creates a new GameObject which will be added in the scene at the start of the next frame.
     *
     * @param x x coordinate
     * @param y y coordinate
     * @return the created GameObject placed at the given position
     */
    public final GameObject createGameObject(float x, float y) {
        GameObject gameObject = createGameObject();

        gameObject.x = x;
        gameObject.y = y;

        return gameObject;
    }

    /**
     * Creates a new GameObject which will be added in the scene at the start of the next frame.
     *
     * @param x     x coordinate
     * @param y     y coordinate
     * @param angle rotation in degrees
     * @return the created GameObject placed at the given position and rotation
     */
    public final GameObject createGameObject(float x, float y, float angle) {
        GameObject gameObject = createGameObject();

        gameObject.x = x;
        gameObject.y = y;
        gameObject.angle = angle;

        return gameObject;
    }

    /**
     * Enqueues a GameObject to be removed from the scene at the start of the next frame.
     *
     * @param gameObject GameObject to remove
     */
    public final void removeGameObject(GameObject gameObject) {
        if (gameObject == camera)
            throw new RuntimeException("Can't remove Camera");

        if (gameObject != null && gameObject.scene == this) {
            gameObjectsOperations.clear(gameObjectsToOperate.size());
            gameObjectsToOperate.add(gameObject);
        }
    }

    /**
     * Sets the color to use when clearing the screen at the start of the render process.
     *
     * @param clearColor clear color
     */
    public final void setClearColor(int clearColor) {
        this.clearColor = clearColor;
    }

    /**
     * Loads a pixmap. It must not be disposed manually.
     *
     * @param name name of the pixmap
     * @return the desired pixmap
     */
    protected final Pixmap getImage(String name) {
        Pixmap image = images.get(name);

        if (image == null) {
            image = game.getGraphics().newPixmap(name, Graphics.PixmapFormat.ARGB8888);
            images.put(name, image);
        }

        return image;
    }

    /**
     * Loads a sound. It must not be disposed manually.
     *
     * @param name name of the sound
     * @return the desired sound
     */
    protected final Sound getSound(String name) {
        Sound sound = sounds.get(name);

        if (sound == null) {
            sound = game.getAudio().newSound(name);
            sounds.put(name, sound);
        }

        return sound;
    }

    /**
     * Loads a music. It must not be disposed manually.
     *
     * @param name name of the music
     * @return the desired music
     */
    protected final Music getMusic(String name) {
        Music music = musics.get(name);

        if (music == null) {
            music = game.getAudio().newMusic(name);
            musics.put(name, music);
        }

        return music;
    }

    private void applySceneChanges() {
        for (int i = 0; i < gameObjectsToOperate.size(); i++) {
            GameObject gameObject = gameObjectsToOperate.get(i);
            boolean add = gameObjectsOperations.get(i);

            if (add) {
                if (!gameObjects.add(gameObject))
                    continue;

                for (Component component : gameObject.getComponents())
                    addComponent(component);

                gameObject.initialize();
            } else if (gameObjects.remove(gameObject)) {
                for (Component component : gameObject.getComponents())
                    removeComponent(component);

                gameObject.dispose();
            }
        }

        gameObjectsToOperate.clear();
        gameObjectsOperations.clear();
    }

    private void addComponent(Component component) {
        switch (component.getType()) {
            case PHYSICS:
                physicsComponents.add((PhysicsComponent) component);
                ((PhysicsComponent) component).world = world;
                break;
            case BEHAVIOUR:
                behaviourComponents.add((BehaviourComponent) component);
                break;
            case INPUT:
                inputComponents.add((InputComponent) component);
                break;
            case RENDER:
                ((RenderComponent) component).scene = this;
                renderComponents.add((RenderComponent) component);
                layerDirty = true;
                break;
            case ANIMATION:
                animationComponents.add((AnimationComponent) component);
                break;
            default:
                break;
        }
    }

    private void removeComponent(Component component) {
        switch (component.getType()) {
            case PHYSICS:
                physicsComponents.remove((PhysicsComponent) component);
                return;
            case BEHAVIOUR:
                behaviourComponents.remove((BehaviourComponent) component);
                return;
            case INPUT:
                inputComponents.remove((InputComponent) component);
                return;
            case RENDER:
                if (renderComponents.remove((RenderComponent) component)) {
                    layerDirty = true;
                }
                return;
            case ANIMATION:
                animationComponents.remove((AnimationComponent) component);
                return;
            default:
        }
    }

}
