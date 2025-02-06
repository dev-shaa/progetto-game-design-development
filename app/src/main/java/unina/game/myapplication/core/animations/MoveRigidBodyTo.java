package unina.game.myapplication.core.animations;

import com.badlogic.androidgames.framework.Pool;

import unina.game.myapplication.core.GameObject;
import unina.game.myapplication.core.Utility;
import unina.game.myapplication.core.physics.RigidBody;

public class MoveRigidBodyTo implements Animation{
    public interface EaseFunction {

        MoveRigidBodyTo.EaseFunction LINEAR = x -> Utility.clamp(0, 1, x);
        MoveRigidBodyTo.EaseFunction CUBIC_IN_OUT = x -> x < 0.5 ? 4 * x * x * x : (float) (1 - Math.pow(-2 * x + 2, 3) / 2);

        float evaluate(float x);

    }

    private static Pool<MoveRigidBodyTo> pool;

    public static MoveRigidBodyTo build(RigidBody rigidBody, float targetX, float targetY, float duration) {
        return build(rigidBody, targetX, targetY, duration, MoveRigidBodyTo.EaseFunction.LINEAR);
    }

    public static MoveRigidBodyTo build(RigidBody rigidBody, float targetX, float targetY, float duration, MoveRigidBodyTo.EaseFunction easeFunction) {
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
    private MoveRigidBodyTo.EaseFunction easeFunction = MoveRigidBodyTo.EaseFunction.LINEAR;
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
        current = Math.min(current + deltaTime, duration);
        float t = easeFunction.evaluate(current / duration);

        float x = Utility.lerp(startX, targetX, t);
        float y = Utility.lerp(startY, targetY, t);

        rigidBody.setTransform(x,y);

//        rigidBody.x = Utility.lerp(startX, targetX, t);
//        rigidBody.y = Utility.lerp(startY, targetY, t);
    }

    @Override
    public boolean isFinished() {
        return current == duration;
    }
}
