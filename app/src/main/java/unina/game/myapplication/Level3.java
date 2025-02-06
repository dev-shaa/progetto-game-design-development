package unina.game.myapplication;

import com.badlogic.androidgames.framework.Color;
import com.badlogic.androidgames.framework.Game;

import unina.game.myapplication.core.Camera;
import unina.game.myapplication.core.GameObject;
import unina.game.myapplication.core.Scene;
import unina.game.myapplication.core.animations.AnimationSequence;
import unina.game.myapplication.core.animations.MoveRigidBodyTo;
import unina.game.myapplication.core.animations.MoveToAnimation;
import unina.game.myapplication.core.physics.BoxCollider;
import unina.game.myapplication.core.physics.CircleCollider;
import unina.game.myapplication.core.physics.RigidBody;
import unina.game.myapplication.logic.ButtonInputComponent;
import unina.game.myapplication.logic.ButtonRenderComponent;
import unina.game.myapplication.logic.PlatformBehaviourComponent;
import unina.game.myapplication.logic.PlatformRenderComponent;
import unina.game.myapplication.logic.RockRenderComponent;
import unina.game.myapplication.logic.TestingRender;

public class Level3 extends Scene {
    public Level3(Game game) {
        super(game);
    }

    @Override
    public void initialize() {
        super.initialize();

        Camera.getInstance().setSize(20);

        float floorW = 6;
        float floorH = 10;

        //Piattaforma 1
        PlatformRenderComponent floorRenderComponent1 = new PlatformRenderComponent();
        floorRenderComponent1.color = Color.GREY;
        floorRenderComponent1.width = floorW;
        floorRenderComponent1.height = floorH;
        RigidBody rigidFloor1 = RigidBody.build(RigidBody.Type.STATIC, BoxCollider.build(floorW, floorH));
        GameObject floor1 = createGameObject(floorRenderComponent1, rigidFloor1);
        floor1.x = 6;
        floor1.y = -22;

        float bridgeW = 6;
        float bridgeH = 1;

        //Ponte 1
        PlatformRenderComponent bridge1RenderComponent = new PlatformRenderComponent();
        bridge1RenderComponent.color = Color.GOLD;
        bridge1RenderComponent.width = bridgeW;
        bridge1RenderComponent.height = bridgeH;
        AnimationSequence bridge1Animation = AnimationSequence.build();
        PlatformBehaviourComponent bridge1BehaviourComponent = new PlatformBehaviourComponent();
        GameObject bridge1 = createGameObject(bridge1RenderComponent, bridge1BehaviourComponent, bridge1Animation);
        //bridge.x = -1.35f;
        bridge1.y = -10;

        //Ponte 2
        PlatformRenderComponent bridge2RenderComponent = new PlatformRenderComponent();
        bridge2RenderComponent.color = Color.GOLD;
        bridge2RenderComponent.width = bridgeW;
        bridge2RenderComponent.height = bridgeH;
        AnimationSequence bridge2Animation = AnimationSequence.build();
        PlatformBehaviourComponent bridge2BehaviourComponent = new PlatformBehaviourComponent();
        GameObject bridge2 = createGameObject(bridge2RenderComponent, bridge2BehaviourComponent, bridge2Animation);
        bridge2.x = -6;
        bridge2.y = -17.5f;

        //Personaggio
        float pgW = 2;
        float pgH = 2;
        TestingRender characterRender = new TestingRender();
        characterRender.width = pgW;
        characterRender.height = pgH;
        AnimationSequence pgAnimation = AnimationSequence.build();
        GameObject character = createGameObject(characterRender,pgAnimation);
        character.x = -6;
        character.y = -16;

        //Masso
        RockRenderComponent rockRenderComponent = new RockRenderComponent();
        rockRenderComponent.color = Color.GREY;
        rockRenderComponent.radius = 2;
        RigidBody rigidRock = RigidBody.build(RigidBody.Type.DYNAMIC, CircleCollider.build(2));
        GameObject rock = createGameObject(rockRenderComponent, rigidRock);
        rock.x = -5;
        rock.y = 16;

        //Piattaforma 1
        float platW = 8;
        float platH = 0.7f;
        PlatformRenderComponent platformRenderComponent1 = new PlatformRenderComponent();
        platformRenderComponent1.color = Color.GREY;
        platformRenderComponent1.width = platW;
        platformRenderComponent1.height = platH;
        RigidBody rigidPlatform1 = RigidBody.build(RigidBody.Type.STATIC, BoxCollider.build(platW, platH));
        GameObject platform = createGameObject(platformRenderComponent1, rigidPlatform1);
        platform.x = -4;
        platform.y = 13;
        platform.angle = 140;

        //Ponte 3
        PlatformRenderComponent bridge3RenderComponent = new PlatformRenderComponent();
        bridge3RenderComponent.color = Color.GOLD;
        bridge3RenderComponent.width = 4;
        bridge3RenderComponent.height = 0.7f;
        RigidBody rigidBridge = RigidBody.build(RigidBody.Type.KINEMATIC,BoxCollider.build(4,0.7f));
        AnimationSequence bridge3Animation = AnimationSequence.build();
        PlatformBehaviourComponent bridge3BehaviourComponent = new PlatformBehaviourComponent();
        GameObject bridge3 = createGameObject(bridge3RenderComponent,rigidBridge, bridge3BehaviourComponent, bridge3Animation);
        bridge3.x = -2.5f;
        bridge3.y = 14.5f;
        bridge3.angle = 50;

        //Pulsante
        ButtonRenderComponent buttonRenderComponent = new ButtonRenderComponent();
        ButtonInputComponent buttonInputComponent = new ButtonInputComponent();
        buttonInputComponent.buttonRenderComponent = buttonRenderComponent;
        buttonRenderComponent.edge = 2;
        buttonRenderComponent.radius = 0.6f;
        buttonInputComponent.width = 2;
        buttonInputComponent.height = 2;
        buttonInputComponent.runnable = () -> move1(bridge3Animation,rigidBridge);
        GameObject button = createGameObject(buttonRenderComponent, buttonInputComponent);
        button.x = -6;
        button.y = 5;
    }

    public void move1(AnimationSequence bridge, RigidBody rigidBridge) {
        float oldX = -2.5f;
        float oldY = 14.5f;
        float newX = (float) (oldX-4 * Math.cos(Math.toRadians(50)));
        float newY = (float) (oldY-4 * Math.sin(Math.toRadians(50)));
        bridge.add(MoveRigidBodyTo.build(rigidBridge, newX,newY, 0.5f));
        bridge.start();
    }
}
