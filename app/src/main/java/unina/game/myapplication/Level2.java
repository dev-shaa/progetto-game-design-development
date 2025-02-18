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
import unina.game.myapplication.core.animations.EaseFunction;
import unina.game.myapplication.core.animations.MoveToAnimation;
import unina.game.myapplication.core.animations.ParallelAnimation;
import unina.game.myapplication.core.animations.WaitAnimation;
import unina.game.myapplication.core.physics.BoxCollider;
import unina.game.myapplication.core.physics.CircleCollider;
import unina.game.myapplication.core.physics.RigidBody;
import unina.game.myapplication.core.rendering.SpriteRenderer;
import unina.game.myapplication.logic.DebugRenderer;
import unina.game.myapplication.logic.PhysicsButton;
import unina.game.myapplication.logic.PlatformDraggingComponent;
import unina.game.myapplication.logic.PlatformRenderComponent;
import unina.game.myapplication.logic.PressableComponent;
import unina.game.myapplication.logic.RockRenderComponent;
import unina.game.myapplication.logic.common.Button;
import unina.game.myapplication.logic.common.FadeAnimation;
import unina.game.myapplication.logic.common.RectRenderer;

public class Level2 extends Scene {

    private static final int PALETTE_BACKGROUND = 0xff237F52;
    private static final int PALETTE_PRIMARY = 0xffECECE7;

    private Sound buttonSound;
    private Sound buttonsAppearSound;
    private Sound movingPlatformSound;
    private Sound winSound;

    private boolean isPressed = false;

    private Pixmap backgroundImage;
    private Pixmap elementsImage;

    private RectRenderer fullScreenRenderer;
    private AnimationSequence animator;

    private SpriteRenderer characterRenderer;

    public Level2(Game game) {
        super(game);
    }

    @Override
    public void initialize() {
        super.initialize();

        backgroundImage = game.getGraphics().newPixmap("graphics/environment-brick-wall.png", Graphics.PixmapFormat.RGB565);
        elementsImage = game.getGraphics().newPixmap("graphics/elements-light.png", Graphics.PixmapFormat.ARGB8888);

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
        backgroundRenderer.setLayer(16);

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

        // Left floor
        GameObject leftFloor = createGameObject(-6, -14);

        if (DEBUG) {
            RectRenderer leftFloorRenderer = leftFloor.addComponent(RectRenderer.class);
            leftFloorRenderer.setSize(floorW, floorH);
            leftFloorRenderer.setColor(Color.MAGENTA);
            leftFloorRenderer.setLayer(64);
        }

        RigidBody leftFloorRigidBody = leftFloor.addComponent(RigidBody.class);
        leftFloorRigidBody.setType(RigidBody.Type.STATIC);
        leftFloorRigidBody.setCollider(BoxCollider.build(floorW, floorH));

        // Right floor
        GameObject rightFloor = createGameObject(6, -14);

        if (DEBUG) {
            PlatformRenderComponent rightFloorRenderer = rightFloor.addComponent(PlatformRenderComponent.class);
            rightFloorRenderer.color = Color.MAGENTA;
            rightFloorRenderer.width = floorW;
            rightFloorRenderer.height = floorH;
            rightFloorRenderer.setLayer(64);
        }

        RigidBody rightFloorRigidBody = rightFloor.addComponent(RigidBody.class);
        rightFloorRigidBody.setType(RigidBody.Type.STATIC);
        rightFloorRigidBody.setCollider(BoxCollider.build(floorW, floorH));

        //Piattaforma Masso
        GameObject rockPlatform = createGameObject(4.5f, 11, 30);

        float platW = 9;
        float platH = 0.5f;

        if (DEBUG) {
            PlatformRenderComponent rockPlatformRenderComponent = rockPlatform.addComponent(PlatformRenderComponent.class);
            rockPlatformRenderComponent.color = Color.MAGENTA;
            rockPlatformRenderComponent.width = platW;
            rockPlatformRenderComponent.height = platH;
            rockPlatformRenderComponent.setLayer(64);
        }

        RigidBody rcokPlatformRigidBody = rockPlatform.addComponent(RigidBody.class);
        rcokPlatformRigidBody.setType(RigidBody.Type.STATIC);
        rcokPlatformRigidBody.setCollider(BoxCollider.build(platW, platH));

        //Muro a sinistra
        float plat2W = 1;
        float plat2H = 20;

        GameObject leftWall = createGameObject(-9.25f, 5);

        if (DEBUG) {
            PlatformRenderComponent leftWallRenderComponent = leftWall.addComponent(PlatformRenderComponent.class);
            leftWallRenderComponent.color = Color.MAGENTA;
            leftWallRenderComponent.width = plat2W;
            leftWallRenderComponent.height = plat2H;
            leftWallRenderComponent.setLayer(64);
        }

        RigidBody LeftWallRigidBody2 = leftWall.addComponent(RigidBody.class);
        LeftWallRigidBody2.setType(RigidBody.Type.STATIC);
        LeftWallRigidBody2.setCollider(BoxCollider.build(plat2W, plat2H));

        //Muro a destra

        GameObject rightWall = createGameObject(9.25f, 5);

        if (DEBUG) {
            PlatformRenderComponent rightWallRenderComponent = rightWall.addComponent(PlatformRenderComponent.class);
            rightWallRenderComponent.color = Color.MAGENTA;
            rightWallRenderComponent.width = plat2W;
            rightWallRenderComponent.height = plat2H;
            rightWallRenderComponent.setLayer(64);
        }

        RigidBody RightWallRigidBody2 = rightWall.addComponent(RigidBody.class);
        RightWallRigidBody2.setType(RigidBody.Type.STATIC);
        RightWallRigidBody2.setCollider(BoxCollider.build(plat2W, plat2H));

        // Rock
        GameObject rock = createGameObject(5, 15);

        SpriteRenderer rockRenderer = rock.addComponent(SpriteRenderer.class);
        rockRenderer.setImage(elementsImage);
        rockRenderer.setSize(4, 4);
        rockRenderer.setSrcPosition(256, 0);
        rockRenderer.setSrcSize(128, 128);

        RigidBody rockRigidBody = rock.addComponent(RigidBody.class);
        rockRigidBody.setType(RigidBody.Type.DYNAMIC);
        rockRigidBody.setCollider(CircleCollider.build(2, 100, 0, 1, false));
        rockRigidBody.setSleepingAllowed(false);

        //Piattaforma scorrevole
        float dragPlatformWidth = 7.5f;
        float dragPlatformHeight = 0.5f;

        GameObject platformDragged = createGameObject(3, 12, 90);

        RigidBody rigidDrag = platformDragged.addComponent(RigidBody.class);
        rigidDrag.setType(RigidBody.Type.KINEMATIC);
        rigidDrag.setCollider(BoxCollider.build(dragPlatformWidth, dragPlatformHeight));
        rigidDrag.setSleepingAllowed(false);

        SpriteRenderer platformDraggedRenderComponent = platformDragged.addComponent(SpriteRenderer.class);
        platformDraggedRenderComponent.setImage(elementsImage);
        platformDraggedRenderComponent.setSrcPosition(128, 48);
        platformDraggedRenderComponent.setSrcSize(128, 32);
        platformDraggedRenderComponent.setSize(dragPlatformWidth, dragPlatformHeight);

        PlatformDraggingComponent platformDraggingComponent = platformDragged.addComponent(PlatformDraggingComponent.class);
        platformDraggingComponent.width = 10;
        platformDraggingComponent.height = 10;
        platformDraggingComponent.setStart(platformDragged.x, 12);
        platformDraggingComponent.setEnd(platformDragged.x, 4);
        platformDraggingComponent.rigidBody = rigidDrag;

        //Piattaforma scorrevole2
        float dragPlatform2Width = 6;
        float dragPlatform2Height = 0.5f;

        GameObject platformDragged2 = createGameObject(-6, 15, 140);

        RigidBody rigidDrag2 = platformDragged2.addComponent(RigidBody.class);
        rigidDrag2.setType(RigidBody.Type.KINEMATIC);
        rigidDrag2.setCollider(BoxCollider.build(dragPlatform2Width, dragPlatform2Height));
        rigidDrag2.setSleepingAllowed(false);

        SpriteRenderer platformDraggedR2enderComponent = platformDragged2.addComponent(SpriteRenderer.class);
        platformDraggedR2enderComponent.setImage(elementsImage);
        platformDraggedR2enderComponent.setSrcPosition(128, 48);
        platformDraggedR2enderComponent.setSrcSize(128, 32);
        platformDraggedR2enderComponent.setSize(dragPlatform2Width, dragPlatform2Height);

        PlatformDraggingComponent platformDragging2Component = platformDragged2.addComponent(PlatformDraggingComponent.class);
        platformDragging2Component.width = 10;
        platformDragging2Component.height = 10;
        platformDragging2Component.rigidBody = rigidDrag2;
        platformDragging2Component.setStart(platformDragged2.x, 15);
        platformDragging2Component.setEnd(platformDragged2.x, 7);

        // Bridge
        GameObject bridge = createGameObject(-2.5f, -6.6f);

        SpriteRenderer bridgeRenderComponent = bridge.addComponent(SpriteRenderer.class);
        bridgeRenderComponent.setImage(elementsImage);
        bridgeRenderComponent.setSrcPosition(128, 48);
        bridgeRenderComponent.setSrcSize(128, 32);
        bridgeRenderComponent.setSize(6.4f, 1);
        bridgeRenderComponent.setPivot(1f, 0f);
        bridgeRenderComponent.setLayer(-3);

        // Character
        GameObject character = createGameObject(-6, -6.5f);

        characterRenderer = character.addComponent(SpriteRenderer.class);
        characterRenderer.setImage(elementsImage);
        characterRenderer.setSize(2, 2);
        characterRenderer.setSrcPosition(0, 128);
        characterRenderer.setSrcSize(128, 128);
        characterRenderer.setPivot(0.5f, 1);
        characterRenderer.setLayer(-2);

        // Game Over trigger

        // NOTE:
        // The trigger is a separate object from the character renderer because it's easier to deal with

        // NOTE:
        // It may be better to make the trigger size NOT equals to the character size
        // - wider: ensure the trigger detects the rock falling
        // - less tall: event is triggered when the rock is already over the character, animations can have a better effect this way

        GameObject gameOverTriggerGO = createGameObject(-6, -6f);

        RigidBody gameOverTriggerRigidBody = gameOverTriggerGO.addComponent(RigidBody.class);
        gameOverTriggerRigidBody.setType(RigidBody.Type.STATIC);
        gameOverTriggerRigidBody.setCollider(BoxCollider.build(4, 1, true));

        PhysicsButton gameOverTrigger = gameOverTriggerGO.addComponent(PhysicsButton.class);
        gameOverTrigger.setOnCollisionEnter(() -> gameOver(platformDraggingComponent, platformDragging2Component));

        if (DEBUG) {
            // Visualize the trigger in debug mode
            RectRenderer debugTriggerRenderer = gameOverTriggerGO.addComponent(RectRenderer.class);
            debugTriggerRenderer.setColor(Color.MAGENTA);
            debugTriggerRenderer.setLayer(-16);
            debugTriggerRenderer.setSize(4, 1);
        }

        // Pressure plate
        float pressurePlateWidth = 3;
        float pressurePlateHeight = 1;
        GameObject pressurePlateGO = createGameObject(0, 3);

        PlatformRenderComponent pressurePlateRenderer = pressurePlateGO.addComponent(PlatformRenderComponent.class);
        pressurePlateRenderer.color = Color.RED;
        pressurePlateRenderer.width = pressurePlateWidth;
        pressurePlateRenderer.height = pressurePlateHeight;

        RigidBody pressurePlateRigidBody = pressurePlateGO.addComponent(RigidBody.class);
        pressurePlateRigidBody.setType(RigidBody.Type.STATIC);
        pressurePlateRigidBody.setCollider(BoxCollider.build(pressurePlateWidth, pressurePlateHeight, true));

        PhysicsButton pressurePlate = pressurePlateGO.addComponent(PhysicsButton.class);
        pressurePlate.setOnCollisionEnter(() -> {
            if (isPressed)
                return;

            isPressed = true;

            buttonSound.play(1);
            pressurePlateRenderer.color = Color.GREEN;
            pressurePlateRigidBody.setTransform(pressurePlateGO.x, pressurePlateGO.y - 0.5f);

            animator.clear();
            animator.add(WaitAnimation.build(0.4f), () -> movingPlatformSound.play(1));
            animator.add(MoveToAnimation.build(bridge, 3, bridge.y, 0.25f, EaseFunction.CUBIC_IN_OUT));
            animator.add(WaitAnimation.build(0.2f), () -> {
                characterRenderer.setSrcPosition(128, 128);
                winSound.play(1);
            });
            animator.add(MoveToAnimation.build(character, 6f, character.y, 1f, EaseFunction.CUBIC_IN_OUT));
            animator.add(FadeAnimation.build(fullScreenRenderer, Color.TRANSPARENT, Color.BLACK, 0.75f), () -> loadScene(Level2.class));
            animator.start();
//            animator.add(ParallelAnimation.build(
//                    MoveToAnimation.build(retryButtonGO, -4, 1, 0.5f),
//                    MoveToAnimation.build(nextLevelButtonGO, 4, 1, 0.5f)
//            ));
//            animator.add(ParallelAnimation.build(
//                    MoveToAnimation.build(retryButtonGO, -4, 2, 0.05f),
//                    MoveToAnimation.build(nextLevelButtonGO, 4, 2, 0.05f)
//            ));
//            animator.add(ParallelAnimation.build(
//                    MoveToAnimation.build(retryButtonGO, -4, 0, 0.25f),
//                    MoveToAnimation.build(nextLevelButtonGO, 4, 0, 0.25f)
//            ));
            animator.start();


//            prova2.setTransform(prova2.getOwner().x, prova2.getOwner().y - 0.5f);

        });

        // Pressure plate platform
        float pressurePlatePlatformWidth = 11;
        float pressurePlatePlatformHeight = 1;
        GameObject pressurePlatePlatformGO = createGameObject(3.4f, 2);

        if (DEBUG) {
            PlatformRenderComponent pressurePlatePlatformRenderer = pressurePlatePlatformGO.addComponent(PlatformRenderComponent.class);
            pressurePlatePlatformRenderer.color = Color.MAGENTA;
            pressurePlatePlatformRenderer.width = pressurePlatePlatformWidth;
            pressurePlatePlatformRenderer.height = pressurePlatePlatformHeight;
            pressurePlatePlatformRenderer.setLayer(64);
        }

        RigidBody pressurePlatePlatformRigidBody = pressurePlatePlatformGO.addComponent(RigidBody.class);
        pressurePlatePlatformRigidBody.setType(RigidBody.Type.STATIC);
        pressurePlatePlatformRigidBody.setCollider(BoxCollider.build(pressurePlatePlatformWidth, pressurePlatePlatformHeight));

        //Piattaforma noBugRight
        GameObject noBugPlatformRight = createGameObject(-2.5f, 2.5f, -45);

        if (DEBUG) {
            PlatformRenderComponent noBugPlatformRightRenderComponent = noBugPlatformRight.addComponent(PlatformRenderComponent.class);
            noBugPlatformRightRenderComponent.width = 1;
            noBugPlatformRightRenderComponent.height = 1;
            noBugPlatformRightRenderComponent.color = Color.MAGENTA;
            noBugPlatformRightRenderComponent.setLayer(64);
        }

        RigidBody noBugPlatfomrRightRigidBody = noBugPlatformRight.addComponent(RigidBody.class);
        noBugPlatfomrRightRigidBody.setType(RigidBody.Type.STATIC);
        noBugPlatfomrRightRigidBody.setCollider(BoxCollider.build(1, 1));

    }

    @Override
    public void dispose() {
        super.dispose();

        animator = null;
        fullScreenRenderer = null;
        characterRenderer = null;

        elementsImage.dispose();
    }

    private void move(PlatformRenderComponent prova, RigidBody prova2, GameObject bridge, GameObject character, GameObject characterSensor) {
        if (isPressed)
            return;

        isPressed = true;

        prova.color = Color.GREEN;
        prova2.setTransform(prova2.getOwner().x, prova2.getOwner().y - 0.5f);

        removeGameObject(characterSensor);

        // Retry button
        GameObject retryButtonGO = createGameObject(-4, -24);

        DebugRenderer retryButtonRenderer = retryButtonGO.addComponent(DebugRenderer.class);
        retryButtonRenderer.setSize(6, 3);

        Button retryButton = retryButtonGO.addComponent(Button.class);
        retryButton.setSize(6, 3);
        retryButton.setOnClick(this::retry);

        // Next level button
        GameObject nextLevelButtonGO = createGameObject(4, -24);

        DebugRenderer nextLevelButtonRenderer = nextLevelButtonGO.addComponent(DebugRenderer.class);
        nextLevelButtonRenderer.setSize(6, 3);

        Button nextLevelButton = nextLevelButtonGO.addComponent(Button.class);
        nextLevelButton.setSize(6, 3);
        nextLevelButton.setOnClick(this::nextLevel);

        // Animator
//        GameObject animatorGO = createGameObject();
//        AnimationSequence animator = animatorGO.addComponent(AnimationSequence.class);
        animator.clear();
        animator.add(MoveToAnimation.build(bridge, 2.8f, bridge.y, 1));
        animator.add(MoveToAnimation.build(character, 6, character.y, 1));
        animator.add(ParallelAnimation.build(
                MoveToAnimation.build(retryButtonGO, -4, 1, 0.5f),
                MoveToAnimation.build(nextLevelButtonGO, 4, 1, 0.5f)
        ));
        animator.add(ParallelAnimation.build(
                MoveToAnimation.build(retryButtonGO, -4, 2, 0.05f),
                MoveToAnimation.build(nextLevelButtonGO, 4, 2, 0.05f)
        ));
        animator.add(ParallelAnimation.build(
                MoveToAnimation.build(retryButtonGO, -4, 0, 0.25f),
                MoveToAnimation.build(nextLevelButtonGO, 4, 0, 0.25f)
        ));
        animator.start();
    }

    private void gameOver(PressableComponent... interactableComponents) {
        for (PressableComponent component : interactableComponents)
            component.interactable = false;


//        TextRenderer prova = TextRenderer.build();
//        prova.setText("Sei Morto");
//        prova.setSize(20);
        // Retry button
//        GameObject retryButtonGO = createGameObject(4, -21);
//
//        DebugRenderer retryButtonRenderer = retryButtonGO.addComponent(DebugRenderer.class);
//        retryButtonRenderer.setSize(6, 3);
//
//        Button retryButton = retryButtonGO.addComponent(Button.class);
//        retryButton.setSize(6, 3);
//        retryButton.setOnClick(this::retry);

        // Menu button
//        GameObject menuButtonGO = createGameObject(-4, -21);
//
//        DebugRenderer menuButtonRenderer = menuButtonGO.addComponent(DebugRenderer.class);
//        menuButtonRenderer.setSize(6, 3);
//
//        Button menuButton = menuButtonGO.addComponent(Button.class);
//        menuButton.setSize(6, 3);
//        menuButton.setOnClick(this::toMenu);

        // Animator
        characterRenderer.setSize(4, 0.5f);

        animator.clear();
        animator.add(WaitAnimation.build(2));
        animator.add(FadeAnimation.build(fullScreenRenderer, Color.TRANSPARENT, Color.BLACK, 0.5f), this::retry);
//        animator.add(ParallelAnimation.build(
//                MoveToAnimation.build(retryButtonGO, 4, 1, 0.5f),
//                MoveToAnimation.build(menuButtonGO, -4, 1, 0.5f)
//        ));
//        animator.add(ParallelAnimation.build(
//                MoveToAnimation.build(retryButtonGO, 4, 2, 0.05f),
//                MoveToAnimation.build(menuButtonGO, -4, 2, 0.05f)
//        ));
//        animator.add(ParallelAnimation.build(
//                MoveToAnimation.build(retryButtonGO, 4, 0, 0.25f),
//                MoveToAnimation.build(menuButtonGO, -4, 0, 0.25f)
//        ));
        animator.start();
    }

    private void retry() {
        loadScene(Level2.class);
    }

//    private void toMenu() {
//        loadScene(MainMenu.class);
//    }

    private void nextLevel() {
        loadScene(Level3.class);
    }

}
