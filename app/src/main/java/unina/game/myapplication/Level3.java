package unina.game.myapplication;

import com.badlogic.androidgames.framework.Color;
import com.badlogic.androidgames.framework.Game;

import unina.game.myapplication.core.Camera;
import unina.game.myapplication.core.GameObject;
import unina.game.myapplication.core.Scene;
import unina.game.myapplication.core.animations.AnimationSequence;
import unina.game.myapplication.core.animations.MoveRigidBodyTo;
import unina.game.myapplication.core.animations.MoveToAnimation;
import unina.game.myapplication.core.animations.WaitAnimation;
import unina.game.myapplication.core.physics.BoxCollider;
import unina.game.myapplication.core.physics.CircleCollider;
import unina.game.myapplication.core.physics.RigidBody;
import unina.game.myapplication.logic.ButtonInputComponent;
import unina.game.myapplication.logic.ButtonRenderComponent;
import unina.game.myapplication.logic.DebugRenderer;
import unina.game.myapplication.logic.PhysicsButton;
import unina.game.myapplication.logic.PlatformBehaviourComponent;
import unina.game.myapplication.logic.PlatformDraggingComponent;
import unina.game.myapplication.logic.PlatformRenderComponent;
import unina.game.myapplication.logic.PressableComponent;
import unina.game.myapplication.logic.RockRenderComponent;
import unina.game.myapplication.logic.TestingRender;
import unina.game.myapplication.logic.common.Button;
import unina.game.myapplication.logic.menu.MainMenu;

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
        GameObject floor1 = createGameObject(6, -22);
        PlatformRenderComponent floorRenderComponent1 = floor1.addComponent(PlatformRenderComponent.class);
        floorRenderComponent1.color = Color.GREY;
        floorRenderComponent1.width = floorW;
        floorRenderComponent1.height = floorH;
        RigidBody rigidFloor1 = floor1.addComponent(RigidBody.class);
        rigidFloor1.setType(RigidBody.Type.STATIC);
        rigidFloor1.setCollider(BoxCollider.build(floorW, floorH));


        float bridgeW = 6;
        float bridgeH = 1;

        //Ponte 1
        GameObject bridge1 = createGameObject(0, -10);

        PlatformRenderComponent bridge1RenderComponent = bridge1.addComponent(PlatformRenderComponent.class);
        bridge1RenderComponent.color = Color.GOLD;
        bridge1RenderComponent.width = bridgeW;
        bridge1RenderComponent.height = bridgeH;
        AnimationSequence bridge1Animation = bridge1.addComponent(AnimationSequence.class);
        PlatformBehaviourComponent bridge1BehaviourComponent = bridge1.addComponent(PlatformBehaviourComponent.class);

        //Ponte 2
        GameObject bridge2 = createGameObject(-6, -17.5f);
        PlatformRenderComponent bridge2RenderComponent = bridge2.addComponent(PlatformRenderComponent.class);
        bridge2RenderComponent.color = Color.GOLD;
        bridge2RenderComponent.width = bridgeW;
        bridge2RenderComponent.height = bridgeH;
        AnimationSequence bridge2Animation = bridge2.addComponent(AnimationSequence.class);
        PlatformBehaviourComponent bridge2BehaviourComponent = bridge2.addComponent(PlatformBehaviourComponent.class);


        //Personaggio
        float pgW = 2;
        float pgH = 2;
        GameObject character = createGameObject(-6, -16);

        TestingRender characterRender = character.addComponent(TestingRender.class);
        characterRender.width = pgW;
        characterRender.height = pgH;
        AnimationSequence pgAnimation = character.addComponent(AnimationSequence.class);


        //Masso
        GameObject rock = createGameObject(-5, 16);

        RockRenderComponent rockRenderComponent = rock.addComponent(RockRenderComponent.class);
        rockRenderComponent.color = Color.GREY;
        rockRenderComponent.radius = 1;
        RigidBody rigidRock = rock.addComponent(RigidBody.class);
        rigidRock.setType(RigidBody.Type.DYNAMIC);
        rigidRock.setCollider(CircleCollider.build(1));
        rigidRock.setSleepingAllowed(false);

        //Piattaforma 1
        float platW = 8;
        float platH = 0.7f;
        GameObject platform = createGameObject(-4, 13, 140);

        PlatformRenderComponent platform1RenderComponent = platform.addComponent(PlatformRenderComponent.class);
        platform1RenderComponent.color = Color.GREY;
        platform1RenderComponent.width = platW;
        platform1RenderComponent.height = platH;
        RigidBody rigidPlatform1 = platform.addComponent(RigidBody.class);
        rigidPlatform1.setType(RigidBody.Type.STATIC);
        rigidPlatform1.setCollider(BoxCollider.build(platW, platH));

        //Piattaforma 2
        float plat2W = 17;
        float plat2H = 0.7f;
        GameObject platform2 = createGameObject(9, 9, 90);

        PlatformRenderComponent platform2RenderComponent = platform2.addComponent(PlatformRenderComponent.class);
        platform2RenderComponent.color = Color.GREY;
        platform2RenderComponent.width = plat2W;
        platform2RenderComponent.height = plat2H;
        RigidBody rigidPlatform2 = platform2.addComponent(RigidBody.class);
        rigidPlatform2.setType(RigidBody.Type.STATIC);
        rigidPlatform2.setCollider(BoxCollider.build(plat2W, plat2H));

        //Ponte 3
        GameObject bridge3 = createGameObject(-2.5f, 14.5f, 50);
        PlatformRenderComponent bridge3RenderComponent = bridge3.addComponent(PlatformRenderComponent.class);
        bridge3RenderComponent.color = Color.GOLD;
        bridge3RenderComponent.width = 4;
        bridge3RenderComponent.height = 0.7f;
        RigidBody rigidBridge = bridge3.addComponent(RigidBody.class);
        rigidBridge.setType(RigidBody.Type.KINEMATIC);
        rigidBridge.setCollider(BoxCollider.build(4, 0.7f));
        AnimationSequence bridge3Animation = bridge3.addComponent(AnimationSequence.class);
        PlatformBehaviourComponent bridge3BehaviourComponent = bridge3.addComponent(PlatformBehaviourComponent.class);

        //Piattaforma scorrevole
        float dragPlatformWidth = 6;
        float dragPlatformHeight = 0.5f;

        GameObject platformDragged = createGameObject(-4, 13, 140);

        RigidBody rigidDrag = platformDragged.addComponent(RigidBody.class);
        rigidDrag.setType(RigidBody.Type.KINEMATIC);
        rigidDrag.setCollider(BoxCollider.build(dragPlatformWidth, dragPlatformHeight));
        rigidDrag.setSleepingAllowed(false);

        PlatformRenderComponent platformDraggedRenderComponent = platformDragged.addComponent(PlatformRenderComponent.class);
        platformDraggedRenderComponent.color = Color.DARKCYAN;
        platformDraggedRenderComponent.width = dragPlatformWidth;
        platformDraggedRenderComponent.height = dragPlatformHeight;
        platformDraggedRenderComponent.setStart(-4, 13);
        platformDraggedRenderComponent.setEnd(5, 5);

        PlatformDraggingComponent platformDraggingComponent = platformDragged.addComponent(PlatformDraggingComponent.class);
        platformDraggingComponent.width = 10;
        platformDraggingComponent.height = 10;
        platformDraggingComponent.rigidBody = rigidDrag;
        platformDraggingComponent.setStart(-4, 13);
        platformDraggingComponent.setEnd(5, 5);

        //Pulsante
        GameObject button = createGameObject(-6, 5);

        ButtonRenderComponent buttonRenderComponent = button.addComponent(ButtonRenderComponent.class);
        ButtonInputComponent buttonInputComponent = button.addComponent(ButtonInputComponent.class);
        buttonInputComponent.buttonRenderComponent = buttonRenderComponent;
        buttonRenderComponent.edge = 2;
        buttonRenderComponent.radius = 0.6f;
        buttonInputComponent.width = 2;
        buttonInputComponent.height = 2;
        buttonInputComponent.runnable = () -> move1(bridge3Animation, rigidBridge);

        //Pulsante a pressione 1
        float phisic1W = 2;
        float phisic1H = 0.5f;

        GameObject pressure_plate1 = createGameObject(1, 1);

        PlatformRenderComponent phisic1RenderComponent = pressure_plate1.addComponent(PlatformRenderComponent.class);
        phisic1RenderComponent.color = Color.RED;
        phisic1RenderComponent.height = phisic1H;
        phisic1RenderComponent.width = phisic1W;
        RigidBody phisicSensor1 = pressure_plate1.addComponent(RigidBody.class);
        phisicSensor1.setType(RigidBody.Type.STATIC);
        phisicSensor1.setCollider(BoxCollider.build(phisic1W, phisic1H, true));
        phisicSensor1.setSleepingAllowed(false);
        PhysicsButton physicsButton = pressure_plate1.addComponent(PhysicsButton.class);
        physicsButton.onCollisionEnter = () -> moveRED(bridge2Animation, pgAnimation, platformDraggingComponent);
//        physicsButton.onCollisionExit = () -> move(phisicRenderComponent,Color.RED);


        // Win pressure plate
        float winPressurePlateWidth = 2;
        float winPressurePlateHeight = 0.5f;

        GameObject winPressurePlate = createGameObject(6, 1);

        RigidBody winPressurePlateRigidBody = winPressurePlate.addComponent(RigidBody.class);
        winPressurePlateRigidBody.setType(RigidBody.Type.STATIC);
        winPressurePlateRigidBody.setCollider(BoxCollider.build(winPressurePlateWidth, winPressurePlateHeight, true));
        winPressurePlateRigidBody.setSleepingAllowed(false);

        PlatformRenderComponent winPressurePlateRenderer = winPressurePlate.addComponent(PlatformRenderComponent.class);
        winPressurePlateRenderer.color = Color.BLUE;
        winPressurePlateRenderer.height = winPressurePlateHeight;
        winPressurePlateRenderer.width = winPressurePlateWidth;

        PhysicsButton winPressurePlateBehaviour = winPressurePlate.addComponent(PhysicsButton.class);
        winPressurePlateBehaviour.onCollisionEnter = () -> moveBLU(bridge1Animation, pgAnimation, platformDraggingComponent);

        //Piattaforma sotto la pedana 1
        float plat3W = 8;
        float plat3H = 0.5f;
        GameObject platform3 = createGameObject(3.5f, 0.5f);
        PlatformRenderComponent platformRenderComponent3 = platform3.addComponent(PlatformRenderComponent.class);
        platformRenderComponent3.color = Color.GREY;
        platformRenderComponent3.width = plat3W;
        platformRenderComponent3.height = plat3H;
        RigidBody rigidPlatform3 = platform3.addComponent(RigidBody.class);
        rigidPlatform3.setType(RigidBody.Type.STATIC);
        rigidPlatform3.setCollider(BoxCollider.build(plat3W, plat3H));

        //Piattaforma di divisione pedane
        float plat4W = 7;
        float plat4H = 0.7f;
        GameObject platform4 = createGameObject(3.5f, 4, 90);

        PlatformRenderComponent platformRenderComponent4 = platform4.addComponent(PlatformRenderComponent.class);
        platformRenderComponent4.color = Color.GREY;
        platformRenderComponent4.width = plat4W;
        platformRenderComponent4.height = plat4H;
        RigidBody rigidPlatform4 = platform4.addComponent(RigidBody.class);
        rigidPlatform4.setType(RigidBody.Type.STATIC);
        rigidPlatform4.setCollider(BoxCollider.build(plat4W, plat4H));
    }

    public void move1(AnimationSequence bridge, RigidBody rigidBridge) {
        float oldX = -2.5f;
        float oldY = 14.5f;
        float newX = (float) (oldX - 4 * Math.cos(Math.toRadians(50)));
        float newY = (float) (oldY - 4 * Math.sin(Math.toRadians(50)));

        bridge.add(MoveRigidBodyTo.build(rigidBridge, newX, newY, 0.5f));
        bridge.start();
    }

    public void moveRED(AnimationSequence bridge, AnimationSequence character, PressableComponent platform) {
        platform.interactable = false;

        bridge.add(MoveToAnimation.build(bridge.getOwner(), -16, -17.5f, 1));
        bridge.start();

        character.add(WaitAnimation.build(1.5f));
        character.add(MoveToAnimation.build(character.getOwner(), -6, -25, 1));
        character.start();

        //Tasto per riprovare
        GameObject retray = createGameObject(-4, -24);

        DebugRenderer buttonRetrayRenderComponent = retray.addComponent(DebugRenderer.class);
        buttonRetrayRenderComponent.setSize(6, 3);

        Button buttonRetray = retray.addComponent(Button.class);
        buttonRetray.setSize(6, 3);
        buttonRetray.setOnClick(this::retray);

        AnimationSequence buttonRetrayAnimation = retray.addComponent(AnimationSequence.class);
        buttonRetrayAnimation.add(WaitAnimation.build(3));
        buttonRetrayAnimation.add(MoveToAnimation.build(retray, -4, 1, 0.5f));
        buttonRetrayAnimation.add(MoveToAnimation.build(retray, -4, 2, 0.05f));
        buttonRetrayAnimation.add(MoveToAnimation.build(retray, -4, 0, 0.25f));
        buttonRetrayAnimation.start();

        //Tasto per tornare al menu
        GameObject toMenu = createGameObject(4, -24);

        DebugRenderer buttonMenuRenderComponent = toMenu.addComponent(DebugRenderer.class);
        buttonMenuRenderComponent.setSize(6, 3);

        Button buttonMenu = toMenu.addComponent(Button.class);
        buttonMenu.setSize(6, 3);
        buttonMenu.setOnClick(this::toMenu);

        AnimationSequence buttonMenuAnimation = toMenu.addComponent(AnimationSequence.class);
        buttonRetrayAnimation.add(WaitAnimation.build(3));
        buttonMenuAnimation.add(MoveToAnimation.build(toMenu, -4, 1, 0.5f));
        buttonMenuAnimation.add(MoveToAnimation.build(toMenu, -4, 2, 0.05f));
        buttonMenuAnimation.add(MoveToAnimation.build(toMenu, -4, 0, 0.25f));
        buttonMenuAnimation.start();
    }

    public void moveBLU(AnimationSequence bridge, AnimationSequence character, PressableComponent platform) {
        platform.interactable = false;

        bridge.add(MoveToAnimation.build(bridge.getOwner(), 0, -17.5f, 1));
        bridge.start();

        character.add(WaitAnimation.build(1.5f));
        character.add(MoveToAnimation.build(character.getOwner(), 6, -16, 1));
        character.start();

        //Tasto per riprovare
        GameObject retray = createGameObject(-4, -24);

        DebugRenderer buttonRetrayRenderComponent = retray.addComponent(DebugRenderer.class);
        buttonRetrayRenderComponent.setSize(6, 3);

        Button buttonRetray = retray.addComponent(Button.class);
        buttonRetray.setSize(6, 3);
        buttonRetray.setOnClick(this::retray);

        AnimationSequence buttonRetrayAnimation = retray.addComponent(AnimationSequence.class);
        buttonRetrayAnimation.add(WaitAnimation.build(3));
        buttonRetrayAnimation.add(MoveToAnimation.build(retray, -4, 1, 0.5f));
        buttonRetrayAnimation.add(MoveToAnimation.build(retray, -4, 2, 0.05f));
        buttonRetrayAnimation.add(MoveToAnimation.build(retray, -4, 0, 0.25f));
        buttonRetrayAnimation.start();

        //Tasto per avanzare
        GameObject toMenu = createGameObject(4, -24);

        DebugRenderer buttonNextRenderComponent = toMenu.addComponent(DebugRenderer.class);
        buttonNextRenderComponent.setSize(6, 3);

        Button buttonNext = toMenu.addComponent(Button.class);
        buttonNext.setSize(6, 3);
        buttonNext.setOnClick(this::nextLevel);

        AnimationSequence buttonNextAnimation = toMenu.addComponent(AnimationSequence.class);
        buttonNextAnimation.add(WaitAnimation.build(3));
        buttonNextAnimation.add(MoveToAnimation.build(toMenu, 4, 1, 0.5f));
        buttonNextAnimation.add(MoveToAnimation.build(toMenu, 4, 2, 0.05f));
        buttonNextAnimation.add(MoveToAnimation.build(toMenu, 4, 0, 0.25f));
        buttonNextAnimation.start();
    }

    public void retray() {
        loadScene(Level3.class);
    }

    public void toMenu() {
        loadScene(MainMenu.class);
    }

    public void nextLevel() {
        loadScene(TestingScene.class);
    }

}
