package unina.game.myapplication.logic.common;

import com.badlogic.androidgames.framework.Color;
import com.badlogic.androidgames.framework.Pool;

import unina.game.myapplication.core.animations.Animation;

public class FadeAnimation implements Animation {

    private static Pool<FadeAnimation> pool;

    public synchronized static FadeAnimation build(RectRenderer fullScreenColorRenderer, int startColor, int endColor, float duration) {
        if (pool == null)
            pool = new Pool<>(FadeAnimation::new, 2);

        FadeAnimation fade = pool.get();

        fade.rectRenderer = fullScreenColorRenderer;
        fade.startColor = startColor;
        fade.endColor = endColor;
        fade.duration = duration;

        return fade;
    }

    private RectRenderer rectRenderer;
    private int startColor, endColor;
    private float duration;
    private float current;

    private FadeAnimation() {

    }

    @Override
    public void start() {
        current = 0;
    }

    @Override
    public void dispose() {
        rectRenderer = null;
        pool.free(this);
    }

    @Override
    public void process(float deltaTime) {
        if (isFinished())
            return;

        current = Math.min(current + deltaTime, duration);
        rectRenderer.color = Color.lerp(startColor, endColor, current / duration);
    }

    @Override
    public boolean isFinished() {
        return current >= duration;
    }

}
