package unina.game.myapplication.core;

import com.badlogic.androidgames.framework.Color;
import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Graphics;
import com.badlogic.androidgames.framework.Screen;
import com.google.fpl.liquidfun.World;

public abstract class Scene extends Screen {

    private static final int VELOCITY_ITERATIONS = 8, POSITION_ITERATIONS = 3, PARTICLE_ITERATIONS = 3;
    private static final float GRAVITY_X = 0, GRAVITY_Y = 9.82f;

    private World world;

    public Scene(Game game) {
        super(game);
    }

    @Override
    public void initialize() {
        world = new World(GRAVITY_X, GRAVITY_Y);
    }

    @Override
    public final void update(float deltaTime) {
        world.step(deltaTime, VELOCITY_ITERATIONS, POSITION_ITERATIONS, PARTICLE_ITERATIONS);
        world.clearForces(); // NOTE: according to documentation, this should be called automatically if not disabled beforehand
    }

    @Override
    public final void present(float deltaTime) {
        Graphics graphics = game.getGraphics();
        graphics.clear(Color.BLACK);
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
    }

    /**
     * Loads the given scene.
     *
     * @param scene scene to load
     */
    public final void loadScene(Scene scene) {

    }

    /**
     * Enqueues a GameObject to be added in the scene at the start of the next frame.
     *
     * @param gameObject GameObject to add
     */
    public final void addGameObject(GameObject gameObject) {

    }

    /**
     * Enqueues a GameObject to be removed from the scene at the start of the next frame.
     *
     * @param gameObject GameObject to remove
     */
    public final void removeGameObject(GameObject gameObject) {

    }

}
