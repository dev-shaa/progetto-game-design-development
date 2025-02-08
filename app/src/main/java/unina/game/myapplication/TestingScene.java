package unina.game.myapplication;

import android.util.Log;

import com.badlogic.androidgames.framework.Game;

import unina.game.myapplication.core.GameObject;
import unina.game.myapplication.core.Scene;
import unina.game.myapplication.core.animations.AnimationSequence;
import unina.game.myapplication.core.animations.EaseFunction;
import unina.game.myapplication.core.animations.MoveToAnimation;
import unina.game.myapplication.core.animations.WaitAnimation;
import unina.game.myapplication.logic.DebugRenderer;

public class TestingScene extends Scene {

    public TestingScene(Game game) {
        super(game);
    }

    @Override
    public void initialize() {
        super.initialize();

        DebugRenderer renderer = new DebugRenderer(2, 2);
        AnimationSequence animation = AnimationSequence.build();
        GameObject gameObject = createGameObject(renderer, animation);

        animation.add(WaitAnimation.build(1f));
        animation.add(MoveToAnimation.build(gameObject, 2, 2, 0.4f));
        animation.add(WaitAnimation.build(0.5f), () -> Log.d("Testing Scene", "Event!"));
        animation.add(MoveToAnimation.build(gameObject, -3, 0, 1, EaseFunction.CUBIC_IN_OUT));
        animation.start();
    }

}
