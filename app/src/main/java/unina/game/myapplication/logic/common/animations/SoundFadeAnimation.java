package unina.game.myapplication.logic.common.animations;

import com.badlogic.androidgames.framework.Music;

import unina.game.myapplication.core.Utility;
import unina.game.myapplication.core.animations.Animation;
import unina.game.myapplication.core.animations.EaseFunction;

public class SoundFadeAnimation extends Animation {

    public static SoundFadeAnimation build(Music music, float startVolume, float endVolume, float duration) {
        SoundFadeAnimation animation = new SoundFadeAnimation();

        animation.music = music;
        animation.startVolume = startVolume;
        animation.endVolume = endVolume;
        animation.duration = duration;
        animation.easeFunction = EaseFunction.LINEAR;

        return animation;
    }

    private Music music;
    private float startVolume, endVolume;
    private float duration;
    private EaseFunction easeFunction;
    private float current;

    private SoundFadeAnimation() {

    }

    @Override
    public void start() {
        current = 0;
    }

    @Override
    public void dispose() {
        music = null;
    }

    @Override
    public void process(float deltaTime) {
        if (isFinished())
            return;

        current = Math.min(current + deltaTime, duration);
        float t = easeFunction.evaluate(current / duration);
        float volume = Utility.lerp(startVolume, endVolume, t);
        music.setVolume(volume);
    }

    @Override
    public boolean isFinished() {
        return current >= duration;
    }

}
