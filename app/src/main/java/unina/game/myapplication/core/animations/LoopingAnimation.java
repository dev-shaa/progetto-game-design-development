package unina.game.myapplication.core.animations;

import java.util.Objects;

public final class LoopingAnimation extends Animation {

    private Animation animation;
    private Runnable onEnd;

    public LoopingAnimation(Animation animation, Runnable onEnd) {
        this.animation = Objects.requireNonNull(animation);
        this.onEnd = onEnd;
    }

    @Override
    public void start() {
        animation.start();
    }

    @Override
    public void dispose() {
        animation.dispose();
    }

    @Override
    public void process(float deltaTime) {
        if (animation.isFinished()) {
            if (onEnd != null)
                onEnd.run();

            animation.start();
        }

        animation.process(deltaTime);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    public void setAnimation(Animation animation) {
        this.animation = Objects.requireNonNull(animation);
    }

    public void setOnEnd(Runnable onEnd) {
        this.onEnd = onEnd;
    }

}
