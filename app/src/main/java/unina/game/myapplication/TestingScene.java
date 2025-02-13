package unina.game.myapplication;

import com.badlogic.androidgames.framework.Color;
import com.badlogic.androidgames.framework.Game;

import unina.game.myapplication.core.GameObject;
import unina.game.myapplication.core.Scene;
import unina.game.myapplication.core.physics.BoxCollider;
import unina.game.myapplication.core.physics.RigidBody;
import unina.game.myapplication.logic.common.RectRenderer;

public class TestingScene extends Scene {

    public TestingScene(Game game) {
        super(game);
    }

    @Override
    public void initialize() {
        super.initialize();

        GameObject gameObject = createGameObject();

        RigidBody rigidBody = gameObject.addComponent(RigidBody.class);
        rigidBody.setType(RigidBody.Type.STATIC);
        rigidBody.setCollider(BoxCollider.build(1, 1));

        RectRenderer renderer = gameObject.addComponent(RectRenderer.class);
        renderer.setSize(1, 1);
        renderer.setColor(Color.RED);
    }

}
