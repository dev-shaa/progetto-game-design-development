package unina.game.myapplication.logic;

import com.badlogic.androidgames.framework.Color;

import java.util.function.IntConsumer;

import unina.game.myapplication.core.animations.Animation;

public class ColorChangeAnimation implements Animation {

    private float duration, currentTime;
    private int start, target;
    private IntConsumer colorConsumer;

    public ColorChangeAnimation(int start, int target, float duration, IntConsumer colorConsumer) {
        this.duration = duration;
        this.start = start;
        this.target = target;
        this.colorConsumer = colorConsumer;
    }

    @Override
    public void start() {

    }

    @Override
    public void dispose() {
        colorConsumer = null;
    }

    @Override
    public void process(float deltaTime) {
        currentTime += deltaTime;

        if (currentTime > duration)
            currentTime = duration;

        colorConsumer.accept(Color.lerp(start, target, currentTime / duration));
    }

    @Override
    public boolean isFinished() {
        return currentTime >= duration;
    }

}
