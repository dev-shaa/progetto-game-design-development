package unina.game.myapplication.core.animations;

import com.badlogic.androidgames.framework.Pool;

import java.util.ArrayList;

public final class Sequence extends Animation {

    private static Pool<Sequence> pool;

    public static Sequence build() {
        if (pool == null)
            pool = new Pool<>(Sequence::new, 8);

        return pool.get();
    }

    private final ArrayList<Animation> animations;
    private Animation current;
    private int currentIndex;

    private Sequence() {
        animations = new ArrayList<>(4);
        currentIndex = -1;
    }

    @Override
    public void start() {
        if (!animations.isEmpty()) {
            currentIndex = 0;
            current = animations.get(currentIndex);
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
        currentIndex = -1;
    }

    @Override
    public void process(float deltaTime) {
        if (current == null) {
            if (animations.isEmpty())
                return;

            current = animations.get(currentIndex++);
            current.start();
        }

        if (current.isFinished()) {
            current = null;
        } else {
            current.process(deltaTime);
        }
    }

    @Override
    public boolean isFinished() {
        return current == null && currentIndex == animations.size();
    }

    public void add(Animation animation) {
        animations.add(animation);
    }

}
