package unina.game.myapplication.core.animations;

import com.badlogic.androidgames.framework.Pool;

import java.util.LinkedList;

public final class Sequence extends Animation {

    private static Pool<Sequence> pool;

    public static Sequence build() {
        if (pool == null)
            pool = new Pool<>(Sequence::new, 8);

        return pool.get();
    }

    private final LinkedList<Animation> animations;
    private Animation current;

    private Sequence() {
        animations = new LinkedList<>();
    }

    @Override
    public void start() {
        if (!animations.isEmpty()) {
            current = animations.remove();
            current.start();
        }
    }

    @Override
    public void dispose() {
        if (current != null) {
            current.dispose();
            current = null;
        }

        for (Animation animation : animations)
            animation.dispose();

        animations.clear();
    }

    @Override
    public void process(float deltaTime) {
        if (current == null) {
            if (animations.isEmpty())
                return;

            current = animations.remove();
            current.start();
        }

        if (current.isFinished()) {
            current.dispose();
            current = null;
        } else {
            current.process(deltaTime);
        }
    }

    @Override
    public boolean isFinished() {
        return current == null && animations.isEmpty();
    }

    public void addAnimation(Animation animation) {
        animations.add(animation);
    }

}
