package unina.game.myapplication.core.animations;

import java.util.LinkedList;

import unina.game.myapplication.core.AnimationComponent;

public class AnimationSequence extends AnimationComponent {

    public static AnimationSequence build() {
        return new AnimationSequence();
    }

    private final LinkedList<Animation> animations = new LinkedList<>();
    private final LinkedList<Runnable> events = new LinkedList<>();

    private Animation currentAnimation = null;
    private boolean stopped = true;

    private AnimationSequence() {

    }

    @Override
    public void onRemove() {
        super.onRemove();
        clear();
        stopped = true;
    }

    @Override
    public void update(float deltaTime) {
        if (stopped)
            return;

        if (currentAnimation == null) {
            if (animations.isEmpty())
                return;

            currentAnimation = animations.remove();
            currentAnimation.start();
        }

        if (currentAnimation.isFinished()) {
            currentAnimation.dispose();

            Runnable onEnd = events.poll();
            if (onEnd != null)
                onEnd.run();

            currentAnimation = null;
        } else {
            currentAnimation.process(deltaTime);
        }
    }

    public void start() {
        stopped = false;
    }

    public void stop() {
        stopped = true;
    }

    public void add(Animation animation) {
        add(animation, null);
    }

    public void add(Animation animation, Runnable onEnd) {
        animations.add(animation);
        events.add(onEnd);
    }

    public void clear() {
        if (!animations.isEmpty()) {
            for (Animation animation : animations)
                animation.dispose();

            animations.clear();
        }

        events.clear();
        currentAnimation = null;
    }

}
