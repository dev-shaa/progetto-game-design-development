package unina.game.myapplication.logic.common;

import com.badlogic.androidgames.framework.Pool;

import unina.game.myapplication.core.Utility;
import unina.game.myapplication.core.animations.Animation;
import unina.game.myapplication.core.animations.EaseFunction;
import unina.game.myapplication.core.rendering.SpriteRenderer;

public class SpriteRendererScaleAnimation extends Animation {

    private static Pool<SpriteRendererScaleAnimation> pool;

    public static SpriteRendererScaleAnimation build(SpriteRenderer renderer, float startWidth, float startHeight, float endWidth, float endHeight, float duration) {
        return build(renderer, startWidth, startHeight, endWidth, endHeight, duration, EaseFunction.LINEAR);
    }

    public static SpriteRendererScaleAnimation build(SpriteRenderer renderer, float startWidth, float startHeight, float endWidth, float endHeight, float duration, EaseFunction easeFunction) {
        if (pool == null)
            pool = new Pool<>(SpriteRendererScaleAnimation::new, 2);

        SpriteRendererScaleAnimation animation = pool.get();
        animation.renderer = renderer;
        animation.startWidth = startWidth;
        animation.startHeight = startHeight;
        animation.endWidth = endWidth;
        animation.endHeight = endHeight;
        animation.duration = duration;
        animation.easeFunction = easeFunction;

        return animation;
    }

    private SpriteRenderer renderer;
    private float startWidth, startHeight;
    private float endWidth, endHeight;
    private float duration;
    private EaseFunction easeFunction;
    private float current;
    private float scale = 1;

    private SpriteRendererScaleAnimation() {

    }

    @Override
    public void start() {
        current = 0;
    }

    @Override
    public void dispose() {
        renderer.setSize(startWidth, startHeight);
        renderer = null;
        pool.free(this);
        scale = 1;
    }

    @Override
    public void process(float deltaTime) {
        current += scale * deltaTime;

        if (current > duration) {
            current = duration;
            scale = -1;
        } else if (current < 0) {
            current = 0;
            scale = 1;
        }

        float t = easeFunction.evaluate(current / duration);

        float width = Utility.lerp(startWidth, endWidth, t);
        float height = Utility.lerp(startHeight, endHeight, t);

        renderer.setSize(width, height);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

}
