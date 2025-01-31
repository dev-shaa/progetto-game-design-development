package unina.game.myapplication;

import com.badlogic.androidgames.framework.Color;
import com.badlogic.androidgames.framework.Game;

import unina.game.myapplication.core.GameObject;
import unina.game.myapplication.core.Scene;
import unina.game.myapplication.core.physics.BoxCollider;
import unina.game.myapplication.core.physics.RigidBody;
import unina.game.myapplication.logic.ButtonInputComponent;
import unina.game.myapplication.logic.ButtonRenderComponent;
import unina.game.myapplication.logic.PlatformBehaviourComponent;
import unina.game.myapplication.logic.PlatformRenderComponent;

public class Level1 extends Scene {
    public Level1(Game game) {
        super(game);
    }

    @Override
    public void initialize() {
        super.initialize();

        //Piattaforma 1
        PlatformRenderComponent platformRenderComponent1 = new PlatformRenderComponent();
        platformRenderComponent1.color = Color.GREY;
        platformRenderComponent1.width = 7;
        platformRenderComponent1.height = 8;
        RigidBody rigidFloor1 = RigidBody.build(RigidBody.Type.STATIC, BoxCollider.build(7, 8));
        GameObject floor1 = createGameObject(platformRenderComponent1, rigidFloor1);
        floor1.x = -5;
        floor1.y = -10;

        //Piattaforma 2
        PlatformRenderComponent platformRenderComponent2 = new PlatformRenderComponent();
        platformRenderComponent2.color = Color.GREY;
        platformRenderComponent2.width = 7;
        platformRenderComponent2.height = 8;
        RigidBody rigidFloor2 = RigidBody.build(RigidBody.Type.STATIC, BoxCollider.build(7, 8));
        GameObject floor2 = createGameObject(platformRenderComponent2, rigidFloor2);
        floor2.x = 5;
        floor2.y = -10;

        //Ponte
        PlatformRenderComponent bridgeRenderComponent = new PlatformRenderComponent();
        bridgeRenderComponent.color = Color.GOLD;
        bridgeRenderComponent.width = 3f;
        bridgeRenderComponent.height = 0.7f;
        //RigidBody rigidBridge = RigidBody.build(RigidBody.Type.KINEMATIC, BoxCollider.build(5,2));
        PlatformBehaviourComponent bridgeBehaviourComponent = new PlatformBehaviourComponent();
        GameObject bridge = createGameObject(bridgeRenderComponent, bridgeBehaviourComponent);
        //bridge.x = -1.35f;
        bridge.y = -2.55f;

        //Pulsante
        ButtonRenderComponent buttonRenderComponent = new ButtonRenderComponent();
        ButtonInputComponent buttonInputComponent = new ButtonInputComponent();
        buttonInputComponent.buttonRenderComponent = buttonRenderComponent;
        buttonRenderComponent.edge = 1;
        buttonRenderComponent.radius = 0.3f;
        buttonInputComponent.width = 1;
        buttonInputComponent.height = 1;
        buttonInputComponent.runnable = () -> move(bridgeBehaviourComponent, -4.55f);
        GameObject button = createGameObject(buttonRenderComponent, buttonInputComponent);
        button.y = 5;
    }


    public void move(PlatformBehaviourComponent bridge, float y) {
        bridge.destY = y - 1.8f;
        bridge.hasToMove = true;
    }
}
