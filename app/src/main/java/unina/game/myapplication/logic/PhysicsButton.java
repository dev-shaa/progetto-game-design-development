package unina.game.myapplication.logic;

import com.badlogic.androidgames.framework.Pool;

import unina.game.myapplication.core.BehaviourComponent;
import unina.game.myapplication.core.GameObject;

public final class PhysicsButton extends BehaviourComponent {

    private static final Pool<PhysicsButton> pool = new Pool<>(PhysicsButton::new, 8);

    public static PhysicsButton build() {
        return pool.get();
    }

    private int counter;
    private Runnable onCollisionEnter;
    private Runnable onCollisionExit;

    private PhysicsButton() {

    }

    @Override
    public void onInitialize() {
        super.onInitialize();
        counter = 0;
    }

    @Override
    public void onRemove() {
        super.onRemove();

        onCollisionEnter = onCollisionExit = null;
        pool.free(this);
    }

    @Override
    public void onCollisionEnter(GameObject other) {
        super.onCollisionEnter(other);

        counter++;
        if (counter == 1 && onCollisionEnter != null)
            onCollisionEnter.run();
    }

    @Override
    public void onCollisionExit(GameObject other) {
        super.onCollisionExit(other);

        counter--;
        if (counter == 0 && onCollisionExit != null)
            onCollisionExit.run();
    }

    public void setOnCollisionEnter(Runnable onCollisionEnter) {
        this.onCollisionEnter = onCollisionEnter;
    }

    public void setOnCollisionExit(Runnable onCollisionExit) {
        this.onCollisionExit = onCollisionExit;
    }

}
