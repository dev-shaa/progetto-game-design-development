package unina.game.myapplication.core.animations;

import com.badlogic.androidgames.framework.Pool;

import unina.game.myapplication.core.Utility;
import unina.game.myapplication.core.physics.RigidBody;

public class MoveRigidBodyTo implements Animation {

    private static Pool<MoveRigidBodyTo> pool;

    public static MoveRigidBodyTo build(RigidBody rigidBody, float targetX, float targetY, float duration) {
        return build(rigidBody, targetX, targetY, duration, EaseFunction.LINEAR);
    }

    public static MoveRigidBodyTo build(RigidBody rigidBody, float targetX, float targetY, float duration, EaseFunction easeFunction) {
        if (pool == null)
            pool = new Pool<>(MoveRigidBodyTo::new, 8);

        MoveRigidBodyTo animation = pool.get();
        animation.rigidBody = rigidBody;
        animation.targetX = targetX;
        animation.targetY = targetY;
        animation.duration = duration;
        animation.easeFunction = easeFunction;
        return animation;
    }

    private RigidBody rigidBody;
    private float startX, startY;
    private float targetX, targetY;
    private float duration;
    private EaseFunction easeFunction;
    private float current;

    private MoveRigidBodyTo() {

    }

    @Override
    public void start() {
        current = 0;
        startX = rigidBody.getOwner().x;
        startY = rigidBody.getOwner().y;
    }

    @Override
    public void dispose() {
        rigidBody = null;
        pool.free(this);
    }

    @Override
    public void process(float deltaTime) {
        if (isFinished())
            return;

        current = Math.min(current + deltaTime, duration);
        float t = easeFunction.evaluate(current / duration);

        float x = Utility.lerp(startX, targetX, t);
        float y = Utility.lerp(startY, targetY, t);

        rigidBody.setTransform(x, y);
    }

    @Override
    public boolean isFinished() {
        return current == duration;
    }
}
