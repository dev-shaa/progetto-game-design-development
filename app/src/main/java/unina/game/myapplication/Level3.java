package unina.game.myapplication;

import com.badlogic.androidgames.framework.Color;
import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Graphics;
import com.badlogic.androidgames.framework.Pixmap;
import com.badlogic.androidgames.framework.Sound;

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
import unina.game.myapplication.core.rendering.SpriteRenderer;
import unina.game.myapplication.logic.DebugRenderer;
import unina.game.myapplication.logic.PhysicsButton;
import unina.game.myapplication.logic.PlatformBehaviourComponent;
import unina.game.myapplication.logic.PlatformDraggingComponent;
import unina.game.myapplication.logic.PlatformRenderComponent;
import unina.game.myapplication.logic.PressableComponent;
import unina.game.myapplication.logic.common.Button;
import unina.game.myapplication.logic.common.CircleRenderer;
import unina.game.myapplication.logic.common.DottedLineRenderer;
import unina.game.myapplication.logic.common.FadeAnimation;
import unina.game.myapplication.logic.common.RectRenderer;
import unina.game.myapplication.logic.menu.MainMenu;

public class Level3 extends Scene {

    private static final int PALETTE_BACKGROUND = 0xffF9A900;
    private static final int PALETTE_PRIMARY = 0xff2B2B2C;

    private Sound buttonSound;
    private Sound buttonsAppearSound;
    private Sound movingPlatformSound;
    private Sound winSound;

    private Pixmap backgroundImage;
    private Pixmap elementsImage;

    private RectRenderer fullScreenRenderer;
    private AnimationSequence animator;
    private SpriteRenderer characterRenderer;

    public Level3(Game game) {
        super(game);
    }

    @Override
    public void initialize() {
        super.initialize();

        backgroundImage = game.getGraphics().newPixmap("graphics/background-level3.jpg", Graphics.PixmapFormat.RGB565);
        elementsImage = game.getGraphics().newPixmap("graphics/elements-dark.png", Graphics.PixmapFormat.ARGB8888);

        buttonSound = game.getAudio().newSound("sounds/kenney-interface-sounds/click_002.ogg");
        buttonsAppearSound = game.getAudio().newSound("sounds/kenney-ui-sounds/switch4.ogg");
        movingPlatformSound = game.getAudio().newSound("sounds/kenney-interface-sounds/error_001.ogg"); // FIXME: placeholder
        winSound = game.getAudio().newSound("sounds/kenney-sax-jingles/jingles_SAX10.ogg");

        Camera.getInstance().setSize(20);

        //Background
        setClearColor(PALETTE_BACKGROUND);

        GameObject backgroundGO = createGameObject();
        SpriteRenderer backgroundRenderer = backgroundGO.addComponent(SpriteRenderer.class);
        backgroundRenderer.setImage(backgroundImage);
        backgroundRenderer.setSize(20, 20 / backgroundImage.getAspectRatio());
        backgroundRenderer.setLayer(-10);

        // Transition panel
        GameObject fade = createGameObject();
        fullScreenRenderer = fade.addComponent(RectRenderer.class);
        fullScreenRenderer.setSize(50, 50);
        fullScreenRenderer.setLayer(Integer.MAX_VALUE);
        fullScreenRenderer.setColor(Color.TRANSPARENT);

        // Animator
        GameObject animatorGO = createGameObject();
        animator = animatorGO.addComponent(AnimationSequence.class);
        animator.add(FadeAnimation.build(fullScreenRenderer, Color.BLACK, Color.TRANSPARENT, 0.5f));
        animator.start();

        float floorW = 6;
        float floorH = 15;

        // Right floor
        GameObject rightFloor = createGameObject(6, -14);

        if (DEBUG) {
            PlatformRenderComponent rightFloorRenderer = rightFloor.addComponent(PlatformRenderComponent.class);
            rightFloorRenderer.color = PALETTE_PRIMARY;
            rightFloorRenderer.width = floorW;
            rightFloorRenderer.height = floorH;
            rightFloorRenderer.setLayer(64);
        }

        RigidBody rightFloorRigidBody = rightFloor.addComponent(RigidBody.class);
        rightFloorRigidBody.setType(RigidBody.Type.STATIC);
        rightFloorRigidBody.setCollider(BoxCollider.build(floorW, floorH));

        // Bridge 1
        GameObject bridge1 = createGameObject(-2.5f, -6.6f);

        SpriteRenderer bridge1RenderComponent = bridge1.addComponent(SpriteRenderer.class);
        bridge1RenderComponent.setImage(elementsImage);
        bridge1RenderComponent.setSrcPosition(128, 48);
        bridge1RenderComponent.setSrcSize(128, 32);
        bridge1RenderComponent.setSize(5f, 1);
        bridge1RenderComponent.setPivot(1f, 0f);
        bridge1RenderComponent.setLayer(-3);

        // Bridge Character
        GameObject bridgeCharacter = createGameObject(8.5f, -6.6f);

        SpriteRenderer bridgeCharacterRenderComponent = bridgeCharacter.addComponent(SpriteRenderer.class);
        bridgeCharacterRenderComponent.setImage(elementsImage);
        bridgeCharacterRenderComponent.setSrcPosition(128, 48);
        bridgeCharacterRenderComponent.setSrcSize(128, 32);
        bridgeCharacterRenderComponent.setSize(6.4f, 1);
        bridgeCharacterRenderComponent.setPivot(1f, 0f);
        bridgeCharacterRenderComponent.setLayer(-3);

        // Character
        GameObject character = createGameObject(-6, -6.5f);

        characterRenderer = character.addComponent(SpriteRenderer.class);
        characterRenderer.setImage(elementsImage);
        characterRenderer.setSize(2, 2);
        characterRenderer.setSrcPosition(0, 128);
        characterRenderer.setSrcSize(128, 128);
        characterRenderer.setPivot(0.5f, 1);
        characterRenderer.setLayer(-2);

        // Rock
        GameObject rock = createGameObject(-5, 16);

        SpriteRenderer rockRenderer = rock.addComponent(SpriteRenderer.class);
        rockRenderer.setImage(elementsImage);
        rockRenderer.setSize(2, 2);
        rockRenderer.setSrcPosition(256, 0);
        rockRenderer.setSrcSize(128, 128);

        RigidBody rockRigidBody = rock.addComponent(RigidBody.class);
        rockRigidBody.setType(RigidBody.Type.DYNAMIC);
        rockRigidBody.setCollider(CircleCollider.build(1, 100, 0, 1, false));
        rockRigidBody.setSleepingAllowed(false);

        //Platform Rock
        float platW = 8;
        float platH = 0.7f;
        GameObject platformRock = createGameObject(-4, 13, 140);

        PlatformRenderComponent platformRockRenderComponent = platformRock.addComponent(PlatformRenderComponent.class);
        platformRockRenderComponent.color = PALETTE_PRIMARY;
        platformRockRenderComponent.width = platW;
        platformRockRenderComponent.height = platH;
        RigidBody platformRockRigidBody = platformRock.addComponent(RigidBody.class);
        platformRockRigidBody.setType(RigidBody.Type.STATIC);
        platformRockRigidBody.setCollider(BoxCollider.build(platW, platH));

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

        //Button
        CircleRenderer buttonCircleRender = createGameObject(-6,5).addComponent(CircleRenderer.class);
        buttonCircleRender.setRadius(0.6f);
        buttonCircleRender.setColor(PALETTE_PRIMARY);

        GameObject button = createGameObject(-6,5);

        SpriteRenderer buttonRenderComponent = button.addComponent(SpriteRenderer.class);
        buttonRenderComponent.setImage(elementsImage);
        buttonRenderComponent.setSize(2, 2);
        buttonRenderComponent.setSrcPosition(0, 0);
        buttonRenderComponent.setSrcSize(128, 128);
        buttonRenderComponent.setLayer(3);

        Button buttonInputComponent = button.addComponent(Button.class);
        buttonInputComponent.setSize(3, 3);
        buttonInputComponent.setOnClick(() -> {
            buttonCircleRender.setColor(Color.WHITE);
        });

        //Linea pulsante ponte orizzontale
        GameObject lineRendererVerticalGO = createGameObject();
        DottedLineRenderer lineVerticalRenderer = lineRendererVerticalGO.addComponent(DottedLineRenderer.class);
        lineVerticalRenderer.setPointA(button.x + 1.25f, button.y);
        lineVerticalRenderer.setPointB(bridge3.x, button.y);
        lineVerticalRenderer.setCount(4);
        lineVerticalRenderer.setRadius(0.2f);
        lineVerticalRenderer.setColor(Color.GREY);
        lineVerticalRenderer.setLayer(-4);

        //Linea pulsante ponte verticale
        GameObject lineRendererGO = createGameObject();
        DottedLineRenderer lineRenderer = lineRendererGO.addComponent(DottedLineRenderer.class);
        lineRenderer.setPointA(bridge3.x, button.y);
        lineRenderer.setPointB(bridge3.x, bridge3.y);
        lineRenderer.setCount(12);
        lineRenderer.setRadius(0.2f);
        lineRenderer.setColor(Color.GREY);
        lineRenderer.setLayer(-4);

//        //Pulsante
//        GameObject button = createGameObject(-6, 5);
//
//        ButtonRenderComponent buttonRenderComponent = button.addComponent(ButtonRenderComponent.class);
//        ButtonInputComponent buttonInputComponent = button.addComponent(ButtonInputComponent.class);
//        buttonInputComponent.buttonRenderComponent = buttonRenderComponent;
//        buttonRenderComponent.edge = 2;
//        buttonRenderComponent.radius = 0.6f;
//        buttonInputComponent.width = 2;
//        buttonInputComponent.height = 2;
//        buttonInputComponent.runnable = () -> move1(bridge3Animation, rigidBridge);

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
        //physicsButton.onCollisionEnter = () -> moveRED(bridge2Animation, pgAnimation, platformDraggingComponent);
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
        //winPressurePlateBehaviour.onCollisionEnter = () -> moveBLU(bridge1Animation, pgAnimation, platformDraggingComponent);

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
