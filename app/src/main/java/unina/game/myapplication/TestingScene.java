package unina.game.myapplication;

import com.badlogic.androidgames.framework.Game;

import unina.game.myapplication.core.Camera;
import unina.game.myapplication.core.GameObject;
import unina.game.myapplication.core.Scene;
import unina.game.myapplication.core.physics.BoxCollider;
import unina.game.myapplication.core.physics.RigidBody;
import unina.game.myapplication.logic.ButtonInputComponent;
import unina.game.myapplication.logic.ButtonRenderComponent;

public class TestingScene extends Scene {

    public TestingScene(Game game) {
        super(game);
    }

    @Override
    public void initialize() {
        super.initialize();
        Camera.getInstance().setSize(3);
        //RigidBody rigidBodyA = RigidBody.build(RigidBody.Type.KINEMATIC, BoxCollider.build(1, 1));
        ButtonRenderComponent buttonRenderComponent = new ButtonRenderComponent();
        ButtonInputComponent buttonInputComponent = new ButtonInputComponent();
        buttonRenderComponent.edge = 1;
        buttonRenderComponent.radius = 0.3f;
        createGameObject(buttonRenderComponent,buttonInputComponent);

    }

    @Override
    public void dispose() {
        super.dispose();
    }

}
