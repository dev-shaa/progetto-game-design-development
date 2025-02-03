package unina.game.myapplication.core.animations;

import java.util.LinkedList;

import unina.game.myapplication.core.AnimationComponent;

public class Animator extends AnimationComponent {

    private final LinkedList<Animation> animations = new LinkedList<>();
    private final LinkedList<Runnable> events = new LinkedList<>();

    private Animation currentAnimation = null;

    @Override
    public void onInitialize() {
        super.onInitialize();
        currentAnimation = animations.poll();
    }

    @Override
    public void onRemove() {
        super.onRemove();

        currentAnimation = null;
        animations.clear();
        events.clear();
    }

    @Override
    public void update(float deltaTime) {
        if (currentAnimation == null)
            return;

        currentAnimation.process(deltaTime);

        if (currentAnimation.isFinished()) {
            currentAnimation.dispose();

            Runnable event = events.remove();

            if (event != null)
                event.run();

            if (animations.isEmpty()) {
                currentAnimation = null;
            } else {
                currentAnimation = animations.remove();
                currentAnimation.start();
            }
        }
    }

    public void addAnimation(Animation animation) {
        addAnimation(animation, null);
    }

    public void addAnimation(Animation animation, Runnable onEnd) {
        animations.add(animation);
        events.add(onEnd);
    }

}
