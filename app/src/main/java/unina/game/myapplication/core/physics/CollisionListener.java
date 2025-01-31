package unina.game.myapplication.core.physics;

import com.badlogic.androidgames.framework.Pool;
import com.google.fpl.liquidfun.Body;
import com.google.fpl.liquidfun.Contact;
import com.google.fpl.liquidfun.ContactListener;
import com.google.fpl.liquidfun.Fixture;

import java.util.HashSet;
import java.util.function.BiConsumer;

import unina.game.myapplication.core.GameObject;

public final class CollisionListener extends ContactListener {

    private final Pool<Collision> collisionPool;

    private final HashSet<Collision> enterBuffer = new HashSet<>();
    private final HashSet<Collision> exitBuffer = new HashSet<>();

    public CollisionListener() {
        this(32);
    }

    public CollisionListener(int maxSize) {
        collisionPool = new Pool<>(Collision::new, maxSize);
    }

    public void forEachEnter(BiConsumer<RigidBody, RigidBody> action) {
        for (Collision collision : enterBuffer) {
            action.accept(collision.a, collision.b);
            collisionPool.free(collision);
        }

        enterBuffer.clear();
    }

    public void forEachExit(BiConsumer<RigidBody, RigidBody> action) {
        for (Collision collision : exitBuffer) {
            action.accept(collision.a, collision.b);
            collisionPool.free(collision);
        }

        exitBuffer.clear();
    }

    @Override
    public void beginContact(Contact contact) {
        super.beginContact(contact);
        addContact(contact, enterBuffer);
    }

    @Override
    public void endContact(Contact contact) {
        super.endContact(contact);
        addContact(contact, exitBuffer);
    }

    private void addContact(Contact contact, HashSet<Collision> buffer) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        Body ba = fa.getBody();
        Body bb = fb.getBody();

        RigidBody ga = (RigidBody) ba.getUserData();
        RigidBody gb = (RigidBody) bb.getUserData();

        Collision collision = collisionPool.get();
        collision.a = ga;
        collision.b = gb;

        buffer.add(collision);
    }

}
