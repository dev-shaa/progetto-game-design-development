package unina.game.myapplication.core.animations;

import com.badlogic.androidgames.framework.Pool;

import unina.game.myapplication.core.GameObject;
import unina.game.myapplication.core.Utility;

public class MoveToAnimation implements Animation {

    private static Pool<MoveToAnimation> pool;

    public static MoveToAnimation build(GameObject gameObject, float targetX, float targetY, float duration) {
        return build(gameObject, targetX, targetY, duration, EaseFunction.LINEAR);
    }

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
