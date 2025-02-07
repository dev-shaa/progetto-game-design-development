package unina.game.myapplication;

import com.badlogic.androidgames.framework.Color;
import com.badlogic.androidgames.framework.Game;

import unina.game.myapplication.core.Camera;
import unina.game.myapplication.core.GameObject;
import unina.game.myapplication.core.Scene;
import unina.game.myapplication.core.animations.AnimationSequence;
import unina.game.myapplication.core.animations.MoveRigidBodyTo;
import unina.game.myapplication.core.physics.BoxCollider;
import unina.game.myapplication.core.physics.CircleCollider;
import unina.game.myapplication.core.physics.RigidBody;
import unina.game.myapplication.logic.ButtonInputComponent;
import unina.game.myapplication.logic.ButtonRenderComponent;
import unina.game.myapplication.logic.PhysicsButton;
import unina.game.myapplication.logic.PlatformBehaviourComponent;
import unina.game.myapplication.logic.PlatformDraggingComponent;
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
        rockRenderComponent.radius = 1;
        RigidBody rigidRock = RigidBody.build(RigidBody.Type.DYNAMIC, CircleCollider.build(1));
        GameObject rock = createGameObject(rockRenderComponent, rigidRock);
        rock.x = -5;
        rock.y = 16;

        //Piattaforma 1
        float platW = 8;
        float platH = 0.7f;
        PlatformRenderComponent platform1RenderComponent = new PlatformRenderComponent();
        platform1RenderComponent.color = Color.GREY;
        platform1RenderComponent.width = platW;
        platform1RenderComponent.height = platH;
        RigidBody rigidPlatform1 = RigidBody.build(RigidBody.Type.STATIC, BoxCollider.build(platW, platH));
        GameObject platform = createGameObject(platform1RenderComponent, rigidPlatform1);
        platform.x = -4;
        platform.y = 13;
        platform.angle = 140;

        //Piattaforma 2
        float plat2W = 17;
        float plat2H = 0.7f;
        PlatformRenderComponent platform2RenderComponent = new PlatformRenderComponent();
        platform2RenderComponent.color = Color.GREY;
        platform2RenderComponent.width = plat2W;
        platform2RenderComponent.height = plat2H;
        RigidBody rigidPlatform2 = RigidBody.build(RigidBody.Type.STATIC, BoxCollider.build(plat2W, plat2H));
        GameObject platform2 = createGameObject(platform2RenderComponent, rigidPlatform2);
        platform2.x = 9;
        platform2.y = 9;
        platform2.angle = 90;

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

        //Pulsante a pressione 1
        float phisic1W = 2;
        float phisic1H = 0.5f;
        PlatformRenderComponent phisic1RenderComponent = new PlatformRenderComponent();
        phisic1RenderComponent.color = Color.RED;
        phisic1RenderComponent.height = phisic1H;
        phisic1RenderComponent.width = phisic1W;
        RigidBody phisicSensor1 = RigidBody.build(RigidBody.Type.STATIC, BoxCollider.build(phisic1W, phisic1H, true));
        phisicSensor1.setSleepingAllowed(false);
        PhysicsButton physicsButton = PhysicsButton.build();
        //physicsButton.onCollisionEnter = () -> move(phisic1RenderComponent, phisicSensor1, bridgeAnimation, Color.GREEN, characterAnimation, characterSensor);
        //physicsButton.onCollisionExit = () -> move(phisicRenderComponent,Color.RED);
        GameObject pressure_plate1 = createGameObject(phisic1RenderComponent, phisicSensor1, physicsButton);
        pressure_plate1.x = 1;
        pressure_plate1.y = 1;

        //Piattaforma sotto la pedana 1
        float plat3W = 8;
        float plat3H = 0.5f;
        PlatformRenderComponent platformRenderComponent3 = new PlatformRenderComponent();
        platformRenderComponent3.color = Color.GREY;
        platformRenderComponent3.width = plat3W;
        platformRenderComponent3.height = plat3H;
        RigidBody rigidPlatform3 = RigidBody.build(RigidBody.Type.STATIC, BoxCollider.build(plat3W, plat3H));
        GameObject platform3 = createGameObject(platformRenderComponent3, rigidPlatform3);
        platform3.x = 3.5f;
        platform3.y = 0.5f;

        //Pulsante a pressione 2
        float phisic2W = 2;
        float phisic2H = 0.5f;
        PlatformRenderComponent phisic2RenderComponent = new PlatformRenderComponent();
        phisic2RenderComponent.color = Color.RED;
        phisic2RenderComponent.height = phisic2H;
        phisic2RenderComponent.width = phisic2W;
        RigidBody phisicSensor2 = RigidBody.build(RigidBody.Type.STATIC, BoxCollider.build(phisic2W, phisic2H, true));
        phisicSensor2.setSleepingAllowed(false);
        PhysicsButton physicsButton2 = PhysicsButton.build();
        //physicsButton.onCollisionEnter = () -> move(phisic1RenderComponent, phisicSensor1, bridgeAnimation, Color.GREEN, characterAnimation, characterSensor);
        //physicsButton.onCollisionExit = () -> move(phisicRenderComponent,Color.RED);
        GameObject pressure_plate2 = createGameObject(phisic2RenderComponent, phisicSensor2, physicsButton2);
        pressure_plate2.x = 6;
        pressure_plate2.y = 1;

        //Piattaforma di divisione pedane
        float plat4W = 7;
        float plat4H = 0.7f;
        PlatformRenderComponent platformRenderComponent4 = new PlatformRenderComponent();
        platformRenderComponent4.color = Color.GREY;
        platformRenderComponent4.width = plat4W;
        platformRenderComponent4.height = plat4H;
        RigidBody rigidPlatform4 = RigidBody.build(RigidBody.Type.STATIC, BoxCollider.build(plat4W, plat4H));
        GameObject platform4 = createGameObject(platformRenderComponent4, rigidPlatform4);
        platform4.x = 3.5f;
        platform4.y = 4;
        platform4.angle = 90;

        //Piattaforma scorrevole
        float dragPlatformWidth = 6;
        float dragPlatformHeight = 0.5f;
        PlatformRenderComponent platformDraggedRenderComponent = new PlatformRenderComponent();
        platformDraggedRenderComponent.color = Color.DARKCYAN;
        platformDraggedRenderComponent.width = dragPlatformWidth;
        platformDraggedRenderComponent.height = dragPlatformHeight;
        PlatformDraggingComponent platformDraggingComponent = new PlatformDraggingComponent();
        platformDraggingComponent.width = 10;
        platformDraggingComponent.height = 10;
        platformDraggingComponent.setStart(-4, 13);
        platformDraggingComponent.setEnd(5, 5);

        platformDraggedRenderComponent.setStart(-4, 13);
        platformDraggedRenderComponent.setEnd(5, 5);

        RigidBody rigidDrag = RigidBody.build(RigidBody.Type.KINEMATIC, BoxCollider.build(dragPlatformWidth, dragPlatformHeight));
        rigidDrag.setSleepingAllowed(false);
        platformDraggingComponent.rigidBody = rigidDrag;
        GameObject platformDragged = createGameObject(platformDraggedRenderComponent, platformDraggingComponent, rigidDrag);
        platformDragged.x = -4;
        platformDragged.y = 13;
        platformDragged.angle = 140;
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
