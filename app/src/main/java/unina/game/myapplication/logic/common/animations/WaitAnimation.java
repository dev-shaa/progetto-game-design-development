package unina.game.myapplication.logic.common.animations;

import com.badlogic.androidgames.framework.Pool;

import unina.game.myapplication.core.animations.Animation;

public final class WaitAnimation extends Animation {

    private static Pool<WaitAnimation> pool;

    public synchronized static WaitAnimation build(float duration) {
        if (pool == null)
            pool = new Pool<>(WaitAnimation::new, 8);

        WaitAnimation animation = pool.get();
        animation.duration = duration;
        return animation;
    }

    private float duration;
    private float currentTime;

    private WaitAnimation() {

    }

    @Override
    public void start() {
        currentTime = 0;
    }

    @Override
    public void dispose() {
        pool.free(this);
    }

    @Override
    public void process(float deltaTime) {
        currentTime += deltaTime;
    }

    @Override
    public boolean isFinished() {
        return currentTime >= duration;
    }

}
