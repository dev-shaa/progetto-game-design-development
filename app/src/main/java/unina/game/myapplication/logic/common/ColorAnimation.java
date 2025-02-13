package unina.game.myapplication.logic.common;

import com.badlogic.androidgames.framework.Color;

import java.util.function.IntConsumer;

import unina.game.myapplication.core.animations.Animation;

public class ColorAnimation implements Animation {

    public static ColorAnimation build() {
        return new ColorAnimation();
    }

    public static ColorAnimation build(IntConsumer colorConsumer, int startColor, int endColor, float duration) {
        ColorAnimation animation = new ColorAnimation();

        animation.colorConsumer = colorConsumer;
        animation.startColor = startColor;
        animation.endColor = endColor;
        animation.duration = duration;

        return animation;
    }

    private IntConsumer colorConsumer;
    private int startColor, endColor;
    private float duration;
    private float current;

    private ColorAnimation() {

    }

    @Override
    public void start() {
        current = 0;
    }

    @Override
    public void dispose() {
        colorConsumer = null;
    }

    @Override
    public void process(float deltaTime) {
        if (isFinished())
            return;

        current = Math.min(current + deltaTime, duration);
        colorConsumer.accept(Color.lerp(startColor, endColor, current / duration));
    }

    @Override
    public boolean isFinished() {
        return current >= duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public void setStartColor(int startColor) {
        this.startColor = startColor;
    }

    public void setEndColor(int endColor) {
        this.endColor = endColor;
    }

}
