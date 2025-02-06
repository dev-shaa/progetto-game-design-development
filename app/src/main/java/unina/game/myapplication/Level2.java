package unina.game.myapplication;

import android.util.Log;

import com.badlogic.androidgames.framework.Color;
import com.badlogic.androidgames.framework.Game;

import unina.game.myapplication.core.Camera;
import unina.game.myapplication.core.GameObject;
import unina.game.myapplication.core.Scene;
import unina.game.myapplication.core.animations.AnimationSequence;
import unina.game.myapplication.core.animations.MoveToAnimation;
import unina.game.myapplication.core.animations.WaitAnimation;
import unina.game.myapplication.core.physics.BoxCollider;
import unina.game.myapplication.core.physics.CircleCollider;
import unina.game.myapplication.core.physics.RigidBody;
import unina.game.myapplication.logic.DebugRenderer;
import unina.game.myapplication.logic.PhysicsButton;
import unina.game.myapplication.logic.PlatformDraggingComponent;
import unina.game.myapplication.logic.PlatformRenderComponent;
import unina.game.myapplication.logic.PressableComponent;
import unina.game.myapplication.logic.RockRenderComponent;
import unina.game.myapplication.logic.TestingRender;
import unina.game.myapplication.logic.common.Button;

public class Level2 extends Scene {

    private boolean isPressed = false;

    public Level2(Game game) {
        super(game);
    }

    @Override
    public void initialize() {
        super.initialize();

        Camera.getInstance().setSize(20);

        float floorW = 6;
        float floorH = 15;

        //Piattaforma 1
        PlatformRenderComponent platformRenderComponent1 = new PlatformRenderComponent();
        platformRenderComponent1.color = Color.GREY;
        platformRenderComponent1.width = floorW;
        platformRenderComponent1.height = floorH;
        RigidBody rigidFloor1 = RigidBody.build(RigidBody.Type.STATIC, BoxCollider.build(floorW, floorH));
        GameObject floor1 = createGameObject(platformRenderComponent1, rigidFloor1);
        floor1.x = -6;
        floor1.y = -14;

        //Piattaforma 2
        PlatformRenderComponent platformRenderComponent2 = new PlatformRenderComponent();
        platformRenderComponent2.color = Color.GREY;
        platformRenderComponent2.width = floorW;
        platformRenderComponent2.height = floorH;
        RigidBody rigidFloor2 = RigidBody.build(RigidBody.Type.STATIC, BoxCollider.build(floorW, floorH));
        GameObject floor2 = createGameObject(platformRenderComponent2, rigidFloor2);
        floor2.x = 6;
        floor2.y = -14;

        //Piattaforma 3
        float platW = 10;
        float platH = 0.5f;
        PlatformRenderComponent platformRenderComponent3 = new PlatformRenderComponent();
        platformRenderComponent3.color = Color.GREY;
        platformRenderComponent3.width = platW;
        platformRenderComponent3.height = platH;
        RigidBody rigidPlatform = RigidBody.build(RigidBody.Type.STATIC, BoxCollider.build(platW, platH));
        GameObject platform = createGameObject(platformRenderComponent3, rigidPlatform);
        platform.x = 4;
        platform.y = 13;
        platform.angle = 30;

        //Piattaforma 4
        float plat2W = 1;
        float plat2H = 8;
        PlatformRenderComponent platformRenderComponent4 = new PlatformRenderComponent();
        platformRenderComponent4.color = Color.GREY;
        platformRenderComponent4.width = plat2W;
        platformRenderComponent4.height = plat2H;
        RigidBody rigidPlatform2 = RigidBody.build(RigidBody.Type.STATIC, BoxCollider.build(plat2W, plat2H));
        GameObject platform2 = createGameObject(platformRenderComponent4, rigidPlatform2);
        platform2.x = -10;
        platform2.y = 5;

        //Masso
        RockRenderComponent rockRenderComponent = new RockRenderComponent();
        rockRenderComponent.color = Color.GREY;
        rockRenderComponent.radius = 2;
        RigidBody rigidRock = RigidBody.build(RigidBody.Type.DYNAMIC, CircleCollider.build(2));
        GameObject rock = createGameObject(rockRenderComponent, rigidRock);
        rock.x = 4;
        rock.y = 18;

        //Piattaforma scorrevole
        float dragPlatformWidth = 7.5f;
        float dragPlatformHeight = 0.5f;
        PlatformRenderComponent platformDraggedRenderComponent = new PlatformRenderComponent();
        platformDraggedRenderComponent.color = Color.GOLD;
        platformDraggedRenderComponent.width = dragPlatformWidth;
        platformDraggedRenderComponent.height = dragPlatformHeight;
        PlatformDraggingComponent platformDraggingComponent = new PlatformDraggingComponent();
        platformDraggingComponent.width = 10;
        platformDraggingComponent.height = 10;
        platformDraggingComponent.setStart(3, 14);
        platformDraggingComponent.setEnd(5, 5);

        platformDraggedRenderComponent.setStart(3, 14);
        platformDraggedRenderComponent.setEnd(5, 5);

        RigidBody rigidDrag = RigidBody.build(RigidBody.Type.KINEMATIC, BoxCollider.build(dragPlatformWidth, dragPlatformHeight));
        rigidDrag.setSleepingAllowed(false);
        platformDraggingComponent.rigidBody = rigidDrag;
        GameObject platformDragged = createGameObject(platformDraggedRenderComponent, platformDraggingComponent, rigidDrag);
        platformDragged.x = 3;
        platformDragged.y = 14;
        platformDragged.angle = 120;

        //Piattaforma scorrevole2
        float dragPlatform2Width = 7.5f;
        float dragPlatform2Height = 0.5f;
        PlatformRenderComponent platformDragged2RenderComponent = new PlatformRenderComponent();
        platformDragged2RenderComponent.color = Color.GOLD;
        platformDragged2RenderComponent.width = dragPlatform2Width;
        platformDragged2RenderComponent.height = dragPlatform2Height;
        PlatformDraggingComponent platformDragging2Component = new PlatformDraggingComponent();
        platformDragging2Component.width = 10;
        platformDragging2Component.height = 10;
        RigidBody rigidDrag2 = RigidBody.build(RigidBody.Type.KINEMATIC, BoxCollider.build(dragPlatform2Width, dragPlatform2Height));
        rigidDrag2.setSleepingAllowed(false);
        platformDragging2Component.rigidBody = rigidDrag2;
        GameObject platformDragged2 = createGameObject(platformDragged2RenderComponent, platformDragging2Component, rigidDrag2);
        platformDragged2.x = -12;
        platformDragged2.y = 14;
        platformDragging2Component.setStart(-12,14);
        platformDragging2Component.setEnd(-8,10);
        platformDragged2.angle = 140;

        //Ponte
        float bridgeW = 6;
        float bridgeH = 1;
        PlatformRenderComponent bridgeRenderComponent = new PlatformRenderComponent();
        bridgeRenderComponent.color = Color.GOLD;
        bridgeRenderComponent.width = bridgeW;
        bridgeRenderComponent.height = bridgeH;
        AnimationSequence bridgeAnimation = AnimationSequence.build();
        GameObject bridge = createGameObject(bridgeRenderComponent, bridgeAnimation);
        bridge.y = 0;

        //Personaggio
        float pgW = 2;
        float pgH = 2;
        TestingRender characterRender = new TestingRender();
        characterRender.width = pgW;
        characterRender.height = pgH;
        AnimationSequence characterAnimation = AnimationSequence.build();
        GameObject character = createGameObject(characterRender,characterAnimation);
        character.x = -6;
        character.y = -5.5f;

        //Sensore Personaggio
        RigidBody characterBody = RigidBody.build(RigidBody.Type.STATIC,BoxCollider.build(pgW,pgH,true));
        characterBody.setSleepingAllowed(false);
        PhysicsButton characterBehaviour = PhysicsButton.build();
        characterBehaviour.onCollisionEnter = () -> gameOver(platformDraggingComponent,platformDragging2Component);
        GameObject characterSensor = createGameObject(characterBehaviour,characterBody);
        characterSensor.x = -6;
        characterSensor.y = -5.5f;

        //Pulsante a pressione
        float phisicW = 3;
        float phisicH = 1;
        PlatformRenderComponent phisicRenderComponent = new PlatformRenderComponent();
        phisicRenderComponent.color = Color.RED;
        phisicRenderComponent.height = phisicH;
        phisicRenderComponent.width = phisicW;
        RigidBody phisicSensor = RigidBody.build(RigidBody.Type.STATIC, BoxCollider.build(phisicW, phisicH, true));
        phisicSensor.setSleepingAllowed(false);
        PhysicsButton physicsButton = PhysicsButton.build();
        physicsButton.onCollisionEnter = () -> move(phisicRenderComponent, phisicSensor, bridgeAnimation, Color.GREEN, characterAnimation, characterSensor);
        //physicsButton.onCollisionExit = () -> move(phisicRenderComponent,Color.RED);
        GameObject pressure_plate = createGameObject(phisicRenderComponent, phisicSensor, physicsButton);
        //pressure_plate.x = -3;
        //pressure_plate.y = -1;
        pressure_plate.y = 5;

        //Piattaforma sotto la pedana
        float plat3W = 4;
        float plat3H = 1;
        PlatformRenderComponent platformRenderComponent5 = new PlatformRenderComponent();
        platformRenderComponent5.color = Color.GREY;
        platformRenderComponent5.width = plat3W;
        platformRenderComponent5.height = plat3H;
        RigidBody rigidPlatform3 = RigidBody.build(RigidBody.Type.STATIC, BoxCollider.build(plat3W, plat3H));
        GameObject platform3 = createGameObject(platformRenderComponent5, rigidPlatform3);
        //platform3.x = -10;
        platform3.y = 4;
    }

    public void move(PlatformRenderComponent prova, RigidBody prova2, AnimationSequence bridge, int color, AnimationSequence character, GameObject characterSensor) {
        if (!isPressed) {
            prova2.setTransform(prova2.getOwner().x, prova2.getOwner().y - 0.5f);
            prova.color = color;
            bridge.add(MoveToAnimation.build(bridge.getOwner(),0,-7,1));
            bridge.start();
            isPressed = true;

            removeGameObject(characterSensor);
            character.add(WaitAnimation.build(1.5f));
            character.add(MoveToAnimation.build(character.getOwner(),6,-5.5f,1));
            character.start();

            //Tasto per riprovare
            DebugRenderer buttonRetrayRenderComponent = new DebugRenderer(6,3);
            Button buttonRetray = new Button(6,3);
            buttonRetray.setOnClick(this::retray);
            AnimationSequence buttonRetrayAnimation = AnimationSequence.build();
            GameObject retray = createGameObject(buttonRetrayRenderComponent,buttonRetray, buttonRetrayAnimation);
            retray.x = -4;
            retray.y = -24;
            buttonRetrayAnimation.add(WaitAnimation.build(3));
            buttonRetrayAnimation.add(MoveToAnimation.build(retray,-4,1,0.5f));
            buttonRetrayAnimation.add(MoveToAnimation.build(retray,-4,2,0.05f));
            buttonRetrayAnimation.add(MoveToAnimation.build(retray,-4,0,0.25f));
            buttonRetrayAnimation.start();

            //Tasto per avanzare
            DebugRenderer buttonNextRenderComponent = new DebugRenderer(6,3);
            Button buttonNext = new Button(6,3);
            //buttonNext.setOnClick(this::nextLevel);
            AnimationSequence buttonNextAnimation = AnimationSequence.build();
            GameObject toMenu = createGameObject(buttonNextRenderComponent, buttonNext, buttonNextAnimation);
            toMenu.x = 4;
            toMenu.y = -24;
            buttonNextAnimation.add(WaitAnimation.build(3));
            buttonNextAnimation.add(MoveToAnimation.build(toMenu,4,1,0.5f));
            buttonNextAnimation.add(MoveToAnimation.build(toMenu,4,2,0.05f));
            buttonNextAnimation.add(MoveToAnimation.build(toMenu,4,0,0.25f));
            buttonNextAnimation.start();
        }
    }

    public void gameOver(PressableComponent... components) {
        Log.d("Game Over","HAI PERSO");
        for (PressableComponent component : components) {
            component.interactable = false;
        }

        //Tasto per riprovare
        DebugRenderer buttonRetrayRenderComponent = new DebugRenderer(6,3);
        Button buttonRetray = new Button(6,3);
        buttonRetray.setOnClick(this::retray);
        AnimationSequence buttonRetrayAnimation = AnimationSequence.build();
        GameObject retray = createGameObject(buttonRetrayRenderComponent,buttonRetray, buttonRetrayAnimation);
        retray.x = 4;
        retray.y = -21;
        buttonRetrayAnimation.add(MoveToAnimation.build(retray,4,1,0.5f));
        buttonRetrayAnimation.add(MoveToAnimation.build(retray,4,2,0.05f));
        buttonRetrayAnimation.add(MoveToAnimation.build(retray,4,0,0.25f));
        buttonRetrayAnimation.start();

        //Tasto per tornare al menu
        DebugRenderer buttonMenuRenderComponent = new DebugRenderer(6,3);
        Button buttonMenu = new Button(6,3);
        buttonMenu.setOnClick(this::toMenu);
        AnimationSequence buttonMenuAnimation = AnimationSequence.build();
        GameObject toMenu = createGameObject(buttonMenuRenderComponent, buttonMenu, buttonMenuAnimation);
        toMenu.x = -4;
        toMenu.y = -21;
        buttonMenuAnimation.add(MoveToAnimation.build(toMenu,-4,1,0.5f));
        buttonMenuAnimation.add(MoveToAnimation.build(toMenu,-4,2,0.05f));
        buttonMenuAnimation.add(MoveToAnimation.build(toMenu,-4,0,0.25f));
        buttonMenuAnimation.start();
    }

    public void retray() {
        loadScene(Level2.class);
    }

    public void  toMenu() {
        loadScene(Level1.class);
    }
}
