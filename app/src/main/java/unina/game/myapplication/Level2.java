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
import unina.game.myapplication.logic.menu.MainMenu;

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
        GameObject floor1 = createGameObject(-6, -14);

        PlatformRenderComponent platformRenderComponent1 = floor1.addComponent(PlatformRenderComponent.class);
        platformRenderComponent1.color = Color.GREY;
        platformRenderComponent1.width = floorW;
        platformRenderComponent1.height = floorH;

        RigidBody rigidFloor1 = floor1.addComponent(RigidBody.class);
        rigidFloor1.setType(RigidBody.Type.STATIC);
        rigidFloor1.setCollider(BoxCollider.build(floorW, floorH));

        //Piattaforma 2
        GameObject floor2 = createGameObject(6, -14);

        PlatformRenderComponent platformRenderComponent2 = floor2.addComponent(PlatformRenderComponent.class);
        platformRenderComponent2.color = Color.GREY;
        platformRenderComponent2.width = floorW;
        platformRenderComponent2.height = floorH;

        RigidBody rigidFloor2 = floor2.addComponent(RigidBody.class);
        rigidFloor2.setType(RigidBody.Type.STATIC);
        rigidFloor2.setCollider(BoxCollider.build(floorW, floorH));

        //Piattaforma 3
        GameObject platform = createGameObject(4, 13, 30);

        float platW = 10;
        float platH = 0.5f;
        PlatformRenderComponent platformRenderComponent3 = platform.addComponent(PlatformRenderComponent.class);
        platformRenderComponent3.color = Color.GREY;
        platformRenderComponent3.width = platW;
        platformRenderComponent3.height = platH;

        RigidBody rigidPlatform = platform.addComponent(RigidBody.class);
        rigidPlatform.setType(RigidBody.Type.STATIC);
        rigidPlatform.setCollider(BoxCollider.build(platW, platH));


        //Piattaforma 4
        float plat2W = 1;
        float plat2H = 8;

        GameObject platform2 = createGameObject(-10, 5);

        PlatformRenderComponent platformRenderComponent4 = platform2.addComponent(PlatformRenderComponent.class);
        platformRenderComponent4.color = Color.GREY;
        platformRenderComponent4.width = plat2W;
        platformRenderComponent4.height = plat2H;

        RigidBody rigidPlatform2 = platform2.addComponent(RigidBody.class);
        rigidPlatform2.setType(RigidBody.Type.STATIC);
        rigidPlatform2.setCollider(BoxCollider.build(plat2W, plat2H));

        //Masso
        GameObject rock = createGameObject(4, 18);

        RockRenderComponent rockRenderComponent = rock.addComponent(RockRenderComponent.class);
        rockRenderComponent.color = Color.GREY;
        rockRenderComponent.radius = 2;
        RigidBody rigidRock = rock.addComponent(RigidBody.class);
        rigidRock.setType(RigidBody.Type.DYNAMIC);
        rigidRock.setCollider(CircleCollider.build(2));

        //Piattaforma scorrevole
        float dragPlatformWidth = 7.5f;
        float dragPlatformHeight = 0.5f;

        GameObject platformDragged = createGameObject(3, 14, 120);

        RigidBody rigidDrag = platformDragged.addComponent(RigidBody.class);
        rigidDrag.setType(RigidBody.Type.KINEMATIC);
        rigidDrag.setCollider(BoxCollider.build(dragPlatformWidth, dragPlatformHeight));
        rigidDrag.setSleepingAllowed(false);

        PlatformRenderComponent platformDraggedRenderComponent = platformDragged.addComponent(PlatformRenderComponent.class);
        platformDraggedRenderComponent.setStart(3, 14);
        platformDraggedRenderComponent.setEnd(5, 5);
        platformDraggedRenderComponent.color = Color.DARKCYAN;
        platformDraggedRenderComponent.width = dragPlatformWidth;
        platformDraggedRenderComponent.height = dragPlatformHeight;

        PlatformDraggingComponent platformDraggingComponent = platformDragged.addComponent(PlatformDraggingComponent.class);
        platformDraggingComponent.width = 10;
        platformDraggingComponent.height = 10;
        platformDraggingComponent.setStart(3, 14);
        platformDraggingComponent.setEnd(5, 5);
        platformDraggingComponent.rigidBody = rigidDrag;

        //Piattaforma scorrevole2
        float dragPlatform2Width = 7.5f;
        float dragPlatform2Height = 0.5f;

        GameObject platformDragged2 = createGameObject(-12, 14, 140);

        RigidBody rigidDrag2 = platformDragged2.addComponent(RigidBody.class);
        rigidDrag2.setType(RigidBody.Type.KINEMATIC);
        rigidDrag2.setCollider(BoxCollider.build(dragPlatform2Width, dragPlatform2Height));
        rigidDrag2.setSleepingAllowed(false);

        PlatformRenderComponent platformDragged2RenderComponent = platformDragged2.addComponent(PlatformRenderComponent.class);
        platformDragged2RenderComponent.color = Color.DARKCYAN;
        platformDragged2RenderComponent.width = dragPlatform2Width;
        platformDragged2RenderComponent.height = dragPlatform2Height;

        PlatformDraggingComponent platformDragging2Component = platformDragged2.addComponent(PlatformDraggingComponent.class);
        platformDragging2Component.width = 10;
        platformDragging2Component.height = 10;
        platformDragging2Component.rigidBody = rigidDrag2;
        platformDragging2Component.setStart(-12, 14);
        platformDragging2Component.setEnd(-8, 10);

        //Ponte
        float bridgeW = 6;
        float bridgeH = 1;

        GameObject bridge = createGameObject();

        PlatformRenderComponent bridgeRenderComponent = bridge.addComponent(PlatformRenderComponent.class);
        bridgeRenderComponent.color = Color.GOLD;
        bridgeRenderComponent.width = bridgeW;
        bridgeRenderComponent.height = bridgeH;
        AnimationSequence bridgeAnimation = bridge.addComponent(AnimationSequence.class);

        //Personaggio
        float pgW = 2;
        float pgH = 2;

        GameObject character = createGameObject(-6, -5.5f);

        TestingRender characterRender = character.addComponent(TestingRender.class);
        characterRender.width = pgW;
        characterRender.height = pgH;
        AnimationSequence characterAnimation = character.addComponent(AnimationSequence.class);

        //Sensore Personaggio
        GameObject characterSensor = createGameObject(-6, -5.5f);
        RigidBody characterBody = characterSensor.addComponent(RigidBody.class);
        characterBody.setType(RigidBody.Type.STATIC);
        characterBody.setCollider(BoxCollider.build(pgW, pgH, true));
        characterBody.setSleepingAllowed(false);
        PhysicsButton characterBehaviour = characterSensor.addComponent(PhysicsButton.class);
        characterBehaviour.onCollisionEnter = () -> gameOver(platformDraggingComponent, platformDragging2Component);

        //Pulsante a pressione
        float phisicW = 3;
        float phisicH = 1;
        GameObject pressure_plate = createGameObject(0, 5);

        PlatformRenderComponent phisicRenderComponent = pressure_plate.addComponent(PlatformRenderComponent.class);
        phisicRenderComponent.color = Color.RED;
        phisicRenderComponent.height = phisicH;
        phisicRenderComponent.width = phisicW;
        RigidBody phisicSensor = pressure_plate.addComponent(RigidBody.class);
        phisicSensor.setType(RigidBody.Type.STATIC);
        phisicSensor.setCollider(BoxCollider.build(phisicW, phisicH, true));
        phisicSensor.setSleepingAllowed(false);
        PhysicsButton physicsButton = pressure_plate.addComponent(PhysicsButton.class);
        physicsButton.onCollisionEnter = () -> move(phisicRenderComponent, phisicSensor, bridgeAnimation, Color.GREEN, characterAnimation, characterSensor);
        //physicsButton.onCollisionExit = () -> move(phisicRenderComponent,Color.RED);

        //Piattaforma sotto la pedana
        float plat3W = 4;
        float plat3H = 1;
        GameObject platform3 = createGameObject(0, 4);

        PlatformRenderComponent platformRenderComponent5 = platform3.addComponent(PlatformRenderComponent.class);
        platformRenderComponent5.color = Color.GREY;
        platformRenderComponent5.width = plat3W;
        platformRenderComponent5.height = plat3H;
        RigidBody rigidPlatform3 = platform3.addComponent(RigidBody.class);
        rigidPlatform3.setType(RigidBody.Type.STATIC);
        rigidPlatform3.setCollider(BoxCollider.build(plat3W, plat3H));

    }

    public void move(PlatformRenderComponent prova, RigidBody prova2, AnimationSequence bridge, int color, AnimationSequence character, GameObject characterSensor) {
        if (!isPressed) {
            prova2.setTransform(prova2.getOwner().x, prova2.getOwner().y - 0.5f);
            prova.color = color;
            bridge.add(MoveToAnimation.build(bridge.getOwner(), 0, -7, 1));
            bridge.start();
            isPressed = true;

            removeGameObject(characterSensor);
            character.add(WaitAnimation.build(1.5f));
            character.add(MoveToAnimation.build(character.getOwner(), 6, -5.5f, 1));
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
            toMenu.x = 4;
            toMenu.y = -24;

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
    }

    public void gameOver(PressableComponent... components) {
        Log.d("Game Over", "HAI PERSO");
        for (PressableComponent component : components) {
            component.interactable = false;
        }

        //Tasto per riprovare
        GameObject retray = createGameObject(4, -21);

        DebugRenderer buttonRetrayRenderComponent = retray.addComponent(DebugRenderer.class);
        buttonRetrayRenderComponent.setSize(6, 3);

        Button buttonRetray = retray.addComponent(Button.class);
        buttonRetray.setSize(6, 3);
        buttonRetray.setOnClick(this::retray);

        AnimationSequence buttonRetrayAnimation = retray.addComponent(AnimationSequence.class);
        buttonRetrayAnimation.add(MoveToAnimation.build(retray, 4, 1, 0.5f));
        buttonRetrayAnimation.add(MoveToAnimation.build(retray, 4, 2, 0.05f));
        buttonRetrayAnimation.add(MoveToAnimation.build(retray, 4, 0, 0.25f));
        buttonRetrayAnimation.start();

        //Tasto per tornare al menu
        GameObject toMenu = createGameObject(-4, -21);

        DebugRenderer buttonMenuRenderComponent = toMenu.addComponent(DebugRenderer.class);
        buttonMenuRenderComponent.setSize(6, 3);

        Button buttonMenu = toMenu.addComponent(Button.class);
        buttonMenu.setSize(6, 3);
        buttonMenu.setOnClick(this::toMenu);

        AnimationSequence buttonMenuAnimation = toMenu.addComponent(AnimationSequence.class);
        buttonMenuAnimation.add(MoveToAnimation.build(toMenu, -4, 1, 0.5f));
        buttonMenuAnimation.add(MoveToAnimation.build(toMenu, -4, 2, 0.05f));
        buttonMenuAnimation.add(MoveToAnimation.build(toMenu, -4, 0, 0.25f));
        buttonMenuAnimation.start();
    }

    public void retray() {
        loadScene(Level2.class);
    }

    public void toMenu() {
        loadScene(MainMenu.class);
    }

    public void nextLevel() {
        loadScene(Level3.class);
    }
}
