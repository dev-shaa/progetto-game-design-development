package unina.game.myapplication.logic;

import com.badlogic.androidgames.framework.Pool;

import java.util.HashSet;

import unina.game.myapplication.core.BehaviourComponent;
import unina.game.myapplication.core.physics.RigidBody;

public class ForceField extends BehaviourComponent {

    private static final Pool<ForceField> pool = new Pool<>(ForceField::new, 4);

    public static ForceField build(float forceX, float forceY) {
        ForceField field = pool.get();
        field.setForce(forceX, forceY);
        return field;
    }

    private float x, y;
    private final HashSet<RigidBody> bodies = new HashSet<>(4);

    private ForceField() {

    }

    @Override
    public void onRemove() {
        super.onRemove();
        x = y = 0;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        for (RigidBody rigidbody : bodies) {
            rigidbody.addForce(x, y);
        }
    }

    @Override
    public void onCollisionEnter(RigidBody other, float relativeVelocityX, float relativeVelocityY) {
        super.onCollisionEnter(other, relativeVelocityX, relativeVelocityY);
        bodies.add(other);
    }

    @Override
    public void onCollisionExit(RigidBody other, float relativeVelocityX, float relativeVelocityY) {
        super.onCollisionExit(other, relativeVelocityX, relativeVelocityY);
        bodies.remove(other);
    }

    public void setForce(float x, float y) {
        this.x = x;
        this.y = y;
    }

}
