package unina.game.myapplication.logic.common;

import com.badlogic.androidgames.framework.Color;

import unina.game.myapplication.core.animations.Animation;

public class FadeAnimation implements Animation {

    public synchronized static FadeAnimation build(FullScreenColorRenderer fullScreenColorRenderer, int startColor, int endColor, float duration) {
        return new FadeAnimation(fullScreenColorRenderer, startColor, endColor, duration);
    }

    private FullScreenColorRenderer fullScreenColorRenderer;
    private int startColor, endColor;
    private float duration;
    private float current;

    private FadeAnimation(FullScreenColorRenderer fullScreenColorRenderer, int startColor, int endColor, float duration) {
        this.fullScreenColorRenderer = fullScreenColorRenderer;
        this.startColor = startColor;
        this.endColor = endColor;
        this.duration = duration;
    }

    @Override
    public void start() {
        current = 0;
    }

    @Override
    public void dispose() {
        fullScreenColorRenderer = null;
    }

    @Override
    public void process(float deltaTime) {
        current = Math.min(current + deltaTime, duration);
        fullScreenColorRenderer.color = Color.lerp(startColor, endColor, current / duration);
    }

    @Override
    public boolean isFinished() {
        return current >= duration;
    }

}
