package unina.game.myapplication.core.animations;

import com.badlogic.androidgames.framework.Pool;

import java.util.Arrays;
import java.util.HashSet;

/**
 * A wrapper to execute multiple animations at the same time.
 */
public final class CompositeAnimation implements Animation {

    private static Pool<CompositeAnimation> pool;

    /**
     * Creates a new composite animation.
     *
     * @param animations animations to execute simultaneously.
     * @return a composite animation
     */
    public synchronized static CompositeAnimation build(Animation... animations) {
        if (pool == null)
            pool = new Pool<>(CompositeAnimation::new, 8);

        CompositeAnimation compositeAnimation = pool.get();
        compositeAnimation.animations.addAll(Arrays.asList(animations));
        return compositeAnimation;
    }

    private final HashSet<Animation> animations = new HashSet<>();

    private CompositeAnimation() {

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
