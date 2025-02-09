package unina.game.myapplication.core.animations;

import unina.game.myapplication.core.GameObject;
import unina.game.myapplication.core.Utility;

public class RotateToAnimation implements Animation {

    public static RotateToAnimation build(GameObject gameObject, float targetAngle, float duration) {
        return new RotateToAnimation(gameObject, targetAngle, duration);
    }

    private GameObject gameObject;
    private float startAngle, targetAngle;
    private float duration;
    private float currentTime;

    public RotateToAnimation(GameObject gameObject, float targetAngle, float duration) {
        this.gameObject = gameObject;
        this.targetAngle = targetAngle;
        this.duration = duration;
    }

    @Override
    public void start() {
        startAngle = gameObject.angle;
        currentTime = 0;
    }

    @Override
    public void dispose() {
        gameObject = null;
    }

    @Override
    public void process(float deltaTime) {
        if (isFinished())
            return;

        currentTime = Math.min(currentTime + deltaTime, duration);
        float t = currentTime / duration;

        gameObject.angle = Utility.lerp(startAngle, targetAngle, t);
    }

    @Override
    public boolean isFinished() {
        return currentTime == duration;
    }

}
