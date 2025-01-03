package unina.game.myapplication.core;

import android.util.ArraySet;

import com.badlogic.androidgames.framework.Color;
import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Graphics;
import com.badlogic.androidgames.framework.Input;
import com.badlogic.androidgames.framework.Screen;
import com.google.fpl.liquidfun.World;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;

import unina.game.myapplication.core.physics.CollisionListener;

public abstract class Scene extends Screen {

    private static final int VELOCITY_ITERATIONS = 8, POSITION_ITERATIONS = 3, PARTICLE_ITERATIONS = 3;
    private static final float GRAVITY_X = 0, GRAVITY_Y = -9.82f;

    private World world;
    private CollisionListener collisionListener;
    private Camera camera;

    // TODO: check for possible better collection
    private final Collection<GameObject> gameObjects = new ArraySet<>(8);
    private final Collection<InputComponent> inputComponents = new ArraySet<>(4);
    private final Collection<PhysicsComponent> physicsComponents = new ArraySet<>(4);
    private final Collection<BehaviourComponent> behaviourComponents = new ArraySet<>(4);
    private final Collection<AnimationComponent> animationComponents = new ArraySet<>(4);
    private final ArrayList<RenderComponent> renderComponents = new ArrayList<>(8);

    private Scene sceneToBeLoaded;
    private final Collection<GameObject> gameObjectsToAdd = new ArrayList<>();
    private final Collection<GameObject> gameObjectsToRemove = new ArrayList<>();
    boolean layerDirty = false;

    public Scene(Game game) {
        super(game);
    }

    @Override
    public void initialize() {
        collisionListener = new CollisionListener();

        world = new World(GRAVITY_X, GRAVITY_Y);
        world.setContactListener(collisionListener);

        camera = new Camera(game.getGraphics());
        Camera.instance = camera;

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

        // TODO: check for lambda expression memory optimization
        // https://stackoverflow.com/questions/27524445/does-a-lambda-expression-create-an-object-on-the-heap-every-time-its-executed

        physicsComponents.forEach(component -> component.update(deltaTime));

        collisionListener.forEachEnter((a, b) -> {
            a.onCollisionEnter(b);
            b.onCollisionEnter(a);
        });

        collisionListener.forEachExit((a, b) -> {
            a.onCollisionExit(b);
            b.onCollisionExit(a);
        });

        Input input = game.getInput();
        inputComponents.forEach(component -> component.process(input));
        behaviourComponents.forEach(component -> component.update(deltaTime));
        animationComponents.forEach(component -> component.update(deltaTime));
    }

    @Override
    public final void present(float deltaTime) {
        Graphics graphics = game.getGraphics();

        // Clear the screen
        graphics.clear(Color.BLACK);

        // Sort the renderers by layer
        if (layerDirty) {
            renderComponents.sort(Comparator.comparingInt(RenderComponent::getLayer));
            layerDirty = false;
        }

        // Render each component
        renderComponents.forEach(component -> component.render(deltaTime, graphics));
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        world.delete();
        sceneToBeLoaded = null;

        for (GameObject gameObject : gameObjects)
            gameObject.dispose();

        for (GameObject gameObject : gameObjectsToAdd)
            gameObject.dispose();

        for (GameObject gameObject : gameObjectsToRemove)
            gameObject.dispose();

        renderComponents.clear();
        inputComponents.clear();
        physicsComponents.clear();
        behaviourComponents.clear();
        animationComponents.clear();

        gameObjects.clear();
        gameObjectsToAdd.clear();
        gameObjectsToRemove.clear();

        camera = null;
    }

    /**
     * Loads the given scene.
     *
     * @param scene scene to load
     */
    public final void loadScene(Scene scene) {
        // It could be called multiple times during a frame, since the new scene is loaded at the start of the next one.
        // Only the first call should be considered.

        if (sceneToBeLoaded == null)
            sceneToBeLoaded = scene;
    }

    /**
     * Enqueues a GameObject to be added in the scene at the start of the next frame.
     *
     * @param gameObject GameObject to add
     */
    public final void addGameObject(GameObject gameObject) {
        gameObjectsToAdd.add(gameObject);
    }

    /**
     * Enqueues a GameObject to be removed from the scene at the start of the next frame.
     *
     * @param gameObject GameObject to remove
     */
    public final void removeGameObject(GameObject gameObject) {
        gameObjectsToRemove.add(gameObject);
    }

    private void applySceneChanges() {
        for (GameObject gameObject : gameObjectsToAdd) {
            if (!gameObjects.add(gameObject))
                continue;

            for (Component component : gameObject.getComponents()) {
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
                    case DRAWABLE:
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

            gameObject.initialize();
        }

        for (GameObject gameObject : gameObjectsToRemove) {
            if (!gameObjects.remove(gameObject))
                continue;

            gameObject.dispose();

            for (Component component : gameObject.getComponents()) {
                switch (component.getType()) {
                    case PHYSICS:
                        physicsComponents.remove((PhysicsComponent) component);
                        break;
                    case BEHAVIOUR:
                        behaviourComponents.remove((BehaviourComponent) component);
                        break;
                    case INPUT:
                        inputComponents.remove((InputComponent) component);
                        break;
                    case DRAWABLE:
                        renderComponents.remove((RenderComponent) component);
                        layerDirty = true;
                        break;
                    case ANIMATION:
                        animationComponents.remove((AnimationComponent) component);
                        break;
                    default:
                        break;
                }
            }
        }

        gameObjectsToAdd.clear();
        gameObjectsToRemove.clear();
    }

}
