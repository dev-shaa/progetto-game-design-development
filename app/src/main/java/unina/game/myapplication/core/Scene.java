package unina.game.myapplication.core;

import android.util.ArraySet;

import com.badlogic.androidgames.framework.Color;
import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Graphics;
import com.badlogic.androidgames.framework.Input;
import com.badlogic.androidgames.framework.Screen;
import com.google.fpl.liquidfun.World;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
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
    private final HashSet<GameObject> gameObjects = new HashSet<>(8);
    private final List<InputComponent> inputComponents = new ArrayList<>(4);
    private final Collection<PhysicsComponent> physicsComponents = new ArraySet<>(4);
    private final ArraySet<BehaviourComponent> behaviourComponents = new ArraySet<>(4);
    private final ArraySet<AnimationComponent> animationComponents = new ArraySet<>(4);
    private final ArrayList<RenderComponent> renderComponents = new ArrayList<>(8);

    private Scene sceneToBeLoaded;
    boolean layerDirty = false;

    private final BitSet gameObjectsOperations = new BitSet();
    private final ArrayList<GameObject> gameObjectsToOperate = new ArrayList<>();

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

        // TODO: check for lambda expression memory optimization
        // https://stackoverflow.com/questions/27524445/does-a-lambda-expression-create-an-object-on-the-heap-every-time-its-executed

        physicsComponents.forEach(component -> component.update(deltaTime));

        collisionListener.forEachEnter((a, b) -> {
            if (a.getOwner().hasComponent(Component.Type.BEHAVIOUR)) {
                BehaviourComponent behaviour = (BehaviourComponent) a.getOwner().getComponent(Component.Type.BEHAVIOUR);
                behaviour.onCollisionEnter(b);
            }

            if (b.getOwner().hasComponent(Component.Type.BEHAVIOUR)) {
                BehaviourComponent behaviour = (BehaviourComponent) b.getOwner().getComponent(Component.Type.BEHAVIOUR);
                behaviour.onCollisionEnter(a);
            }
        });

        collisionListener.forEachExit((a, b) -> {
            if (a.getOwner().hasComponent(Component.Type.BEHAVIOUR)) {
                BehaviourComponent behaviour = (BehaviourComponent) a.getOwner().getComponent(Component.Type.BEHAVIOUR);
                behaviour.onCollisionExit(b);
            }

            if (b.getOwner().hasComponent(Component.Type.BEHAVIOUR)) {
                BehaviourComponent behaviour = (BehaviourComponent) b.getOwner().getComponent(Component.Type.BEHAVIOUR);
                behaviour.onCollisionExit(a);
            }
        });

        List<Input.TouchEvent> events = game.getInput().getTouchEvents();

        for (int i = 0; i < events.size(); i++) {
            Input.TouchEvent event = events.get(i);

            for (int j = 0; j < inputComponents.size(); j++)
                inputComponents.get(j).process(event);
        }

        for (int i = 0; i < behaviourComponents.size(); i++)
            behaviourComponents.valueAt(i).update(deltaTime);

        for (int i = 0; i < animationComponents.size(); i++)
            animationComponents.valueAt(i).update(deltaTime);
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

//        if (DEBUG) {
//            for (int i = -50; i < 50; i++) {
//                int color = i % 5 == 0 ? Color.WHITE : Color.GREY;
//
//                if (i == 0)
//                    color = Color.RED;
//
//                float x1 = Camera.getInstance().worldToScreenX(i);
//                graphics.drawLine(x1, 0, x1, graphics.getHeight(), color);
//
//                float y1 = Camera.getInstance().worldToScreenY(i);
//                graphics.drawLine(0, y1, graphics.getWidth(), y1, color);
//            }
//        }

        // Render each component
        for (int i = 0; i < renderComponents.size(); i++)
            renderComponents.get(i).render(deltaTime, graphics);

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

    private void applySceneChanges() {
        for (int i = 0; i < gameObjectsToOperate.size(); i++) {
            GameObject gameObject = gameObjectsToOperate.get(i);
            boolean add = gameObjectsOperations.get(i);

            if (add) {
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

                gameObject.initialize();
            } else if (gameObjects.remove(gameObject)) {
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
                        case RENDER:
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

                gameObject.dispose();
            }
        }

        gameObjectsToOperate.clear();
        gameObjectsOperations.clear();
    }

}
