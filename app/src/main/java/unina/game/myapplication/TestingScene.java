package unina.game.myapplication;

import com.badlogic.androidgames.framework.Game;

import unina.game.myapplication.core.GameObject;
import unina.game.myapplication.core.Scene;
import unina.game.myapplication.core.physics.BoxCollider;
import unina.game.myapplication.core.physics.RigidBody;
import unina.game.myapplication.logic.ButtonRenderComponent;

public class TestingScene extends Scene {

    public TestingScene(Game game) {
        super(game);
    }

    @Override
    public void initialize() {
        super.initialize();

        RigidBody rigidBodyA = RigidBody.build(RigidBody.Type.KINEMATIC, BoxCollider.build(1, 1));
        GameObject a = GameObject.create(rigidBodyA, new ButtonRenderComponent());

        a.setTransform(0, 0, 0);

        addGameObject(a);
    }

    @Override
    public void dispose() {
        super.dispose();
    }

}
