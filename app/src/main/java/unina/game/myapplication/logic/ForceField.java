package unina.game.myapplication.logic;

import java.util.HashSet;

import unina.game.myapplication.core.BehaviourComponent;
import unina.game.myapplication.core.physics.RigidBody;

public class ForceField extends BehaviourComponent {

    private float x, y;
    private final HashSet<RigidBody> bodies = new HashSet<>(4);

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
