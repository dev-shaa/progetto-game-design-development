package unina.game.myapplication.core.animations;

import com.badlogic.androidgames.framework.Pool;

import unina.game.myapplication.core.GameObject;
import unina.game.myapplication.core.Utility;

/**
 * An animation which moves a GameObject from its current position to a target position.
 */
public class MoveToAnimation implements Animation {

    private static Pool<MoveToAnimation> pool;

    /**
     * Creates a new animation which linearly moves a GameObject to the target destination.
     *
     * @param gameObject GameObject to move
     * @param targetX    target x coordinate
     * @param targetY    target y coordinate
     * @param duration   duration of the animation
     * @return a move animation
     */
    public static MoveToAnimation build(GameObject gameObject, float targetX, float targetY, float duration) {
        return build(gameObject, targetX, targetY, duration, EaseFunction.LINEAR);
    }

    /**
     * Creates a new animation which moves a GameObject to the target destination with the specified ease function.
     *
     * @param gameObject   GameObject to move
     * @param targetX      target x coordinate
     * @param targetY      target y coordinate
     * @param duration     duration of the animation
     * @param easeFunction ease function
     * @return a move animation
     */
    public static MoveToAnimation build(GameObject gameObject, float targetX, float targetY, float duration, EaseFunction easeFunction) {
        if (pool == null)
            pool = new Pool<>(MoveToAnimation::new, 8);

        MoveToAnimation animation = pool.get();
        animation.gameObject = gameObject;
        animation.targetX = targetX;
        animation.targetY = targetY;
        animation.duration = duration;
        animation.easeFunction = easeFunction;
        return animation;
    }

    private GameObject gameObject;
    private float startX, startY;
    private float targetX, targetY;
    private float duration;
    private EaseFunction easeFunction;
    private float current;

    private MoveToAnimation() {

    }

    @Override
    public void start() {
        current = 0;
        startX = gameObject.x;
        startY = gameObject.y;
    }

    @Override
    public void dispose() {
        gameObject = null;
        pool.free(this);
    }

    @Override
    public void process(float deltaTime) {
        if (isFinished())
            return;

        current = Math.min(current + deltaTime, duration);
        float t = easeFunction.evaluate(current / duration);

        gameObject.x = Utility.lerp(startX, targetX, t);
        gameObject.y = Utility.lerp(startY, targetY, t);
    }

    @Override
    public boolean isFinished() {
        return current == duration;
    }

}
