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

    /**
     * Start or resume the animation sequence.
     */
    public void start() {
        stopped = false;
    }

    /**
     * Stop the animation sequence.
     */
    public void stop() {
        stopped = true;
    }

    /**
     * Add an animation to the sequence.
     *
     * @param animation animation to add
     */
    public void add(Animation animation) {
        add(animation, null);
    }

    /**
     * Add an animation to the sequence and an event that will be run at the end.
     *
     * @param animation animation to add
     * @param onEnd     event to run at the end
     */
    public void add(Animation animation, Runnable onEnd) {
        animations.add(animation);
        events.add(onEnd);
    }

    /**
     * Stop and remove all animations from the sequence.
     */
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
