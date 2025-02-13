package unina.game.myapplication.core.animations;

import com.badlogic.androidgames.framework.Pool;

import java.util.Arrays;
import java.util.HashSet;

/**
 * A wrapper to execute multiple animations at the same time.
 */
public final class ParallelAnimation extends Animation {

    private static Pool<ParallelAnimation> pool;

    /**
     * Creates a new composite animation.
     *
     * @param animations animations to execute simultaneously.
     * @return a composite animation
     */
    public synchronized static ParallelAnimation build(Animation animation, Animation... animations) {
        if (pool == null)
            pool = new Pool<>(ParallelAnimation::new, 8);

        ParallelAnimation parallelAnimation = pool.get();
        parallelAnimation.animations.add(animation);
        parallelAnimation.animations.addAll(Arrays.asList(animations));
        return parallelAnimation;
    }

    private final HashSet<Animation> animations = new HashSet<>();

    private ParallelAnimation() {

    }

    @Override
    public void start() {
        for (Animation animation : animations)
            animation.start();
    }

    @Override
    public void dispose() {
        for (Animation animation : animations)
            animation.dispose();

        animations.clear();
        pool.free(this);
    }

    @Override
    public void process(float deltaTime) {
        for (Animation animation : animations)
            animation.process(deltaTime);
    }

    @Override
    public boolean isFinished() {
        for (Animation animation : animations) {
            if (!animation.isFinished())
                return false;
        }

        return true;
    }

}
