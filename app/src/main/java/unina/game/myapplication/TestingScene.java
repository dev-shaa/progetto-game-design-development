package unina.game.myapplication;

import android.util.Log;

import com.badlogic.androidgames.framework.Color;
import com.badlogic.androidgames.framework.Game;

import unina.game.myapplication.core.Camera;
import unina.game.myapplication.core.GameObject;
import unina.game.myapplication.core.Scene;
import unina.game.myapplication.core.physics.BoxCollider;
import unina.game.myapplication.core.physics.RigidBody;
import unina.game.myapplication.logic.ButtonInputComponent;
import unina.game.myapplication.logic.ButtonRenderComponent;
import unina.game.myapplication.logic.PlatformDraggingComponent;
import unina.game.myapplication.logic.PlatformRenderComponent;

public class TestingScene extends Scene {

    public TestingScene(Game game) {
        super(game);
    }

    @Override
    public void initialize() {
        super.initialize();
        //Camera.getInstance().setSize(1);
        //RigidBody rigidBodyA = RigidBody.build(RigidBody.Type.KINEMATIC, BoxCollider.build(1, 1));
//        ButtonRenderComponent buttonRenderComponent = new ButtonRenderComponent();
//        ButtonInputComponent buttonInputComponent = new ButtonInputComponent();
//        buttonInputComponent.buttonRenderComponent = buttonRenderComponent;
//        buttonRenderComponent.edge = 1;
//        buttonRenderComponent.radius = 0.3f;
//        buttonInputComponent.size = 1;
//        buttonInputComponent.runnable = this::prova;
//        createGameObject(buttonRenderComponent,buttonInputComponent);

        PlatformRenderComponent platformRenderComponent = new PlatformRenderComponent();
        PlatformDraggingComponent platformDraggingComponent = new PlatformDraggingComponent();
        platformRenderComponent.width = 5;
        platformRenderComponent.height = 1;
        platformDraggingComponent.width = 5;
        platformDraggingComponent.height = 1;
        platformRenderComponent.color = Color.GOLD;
        RigidBody PlatformRigidBody = RigidBody.build(RigidBody.Type.KINEMATIC,BoxCollider.build(5,1));
        platformDraggingComponent.rigidBody = PlatformRigidBody;
        createGameObject(platformRenderComponent,platformDraggingComponent,PlatformRigidBody);
    }

    public void prova() {
        Log.d("prova","Ciao");
    }

    @Override
    public void dispose() {
        super.dispose();
    }

}
