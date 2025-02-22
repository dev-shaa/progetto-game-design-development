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
import unina.game.myapplication.core.animations.WaitAnimation;
import unina.game.myapplication.core.physics.BoxCollider;
import unina.game.myapplication.core.physics.CircleCollider;
import unina.game.myapplication.core.physics.RigidBody;
import unina.game.myapplication.core.rendering.SpriteRenderer;
import unina.game.myapplication.logic.common.DraggablePlatformLineRenderer;
import unina.game.myapplication.logic.PhysicsButton;
import unina.game.myapplication.logic.PlatformDraggingComponent;
import unina.game.myapplication.logic.PressableComponent;
import unina.game.myapplication.logic.common.Button;
import unina.game.myapplication.logic.common.CollisionSoundPlayer;
import unina.game.myapplication.logic.common.DottedLineRenderer;
import unina.game.myapplication.logic.common.FadeAnimation;
import unina.game.myapplication.logic.common.RectRenderer;
import unina.game.myapplication.logic.menu.MainMenu;

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
    private Pixmap elementsUIImage;

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
            RectRenderer rightFloorRenderer = rightFloor.addComponent(RectRenderer.class);
            rightFloorRenderer.color = Color.MAGENTA;
            rightFloorRenderer.width = floorW;
            rightFloorRenderer.height = floorH;
            rightFloorRenderer.setLayer(64);
        }

        RigidBody rightFloorRigidBody = rightFloor.addComponent(RigidBody.class);
        rightFloorRigidBody.setType(RigidBody.Type.STATIC);
        rightFloorRigidBody.setCollider(BoxCollider.build(floorW, floorH));

        // Rock Platform
        float rockPlatformWidth = 9;
        float rockPlatformHeight = 0.5f;

        GameObject rockPlatform = createGameObject(4.5f, 11, 30);

        RigidBody rockPlatformRigidBody = rockPlatform.addComponent(RigidBody.class);
        rockPlatformRigidBody.setType(RigidBody.Type.STATIC);
        rockPlatformRigidBody.setCollider(BoxCollider.build(rockPlatformWidth, rockPlatformHeight));

        if (DEBUG) {
            RectRenderer rockPlatformRenderComponent = rockPlatform.addComponent(RectRenderer.class);
            rockPlatformRenderComponent.color = Color.MAGENTA;
            rockPlatformRenderComponent.width = rockPlatformWidth;
            rockPlatformRenderComponent.height = rockPlatformHeight;
            rockPlatformRenderComponent.setLayer(64);
        }

        //Muro a sinistra
        float plat2W = 1;
        float plat2H = 20;

        GameObject leftWall = createGameObject(-9.25f, 5);

        if (DEBUG) {
            RectRenderer leftWallRenderComponent = leftWall.addComponent(RectRenderer.class);
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
            RectRenderer rightWallRenderComponent = rightWall.addComponent(RectRenderer.class);
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

        CollisionSoundPlayer rockCollisionSoundPlayer = rock.addComponent(CollisionSoundPlayer.class);
        rockCollisionSoundPlayer.setSound(movingPlatformSound);
        rockCollisionSoundPlayer.setVolume(0.2f);

        // Right draggable platform
        float rightDraggablePlatformWidth = 7f;
        float rightDraggablePlatformHeight = 0.5f;

        GameObject rightDraggablePlatformGO = createGameObject(3, 12, 90);

        RigidBody rightDraggablePlatformRigidBody = rightDraggablePlatformGO.addComponent(RigidBody.class);
        rightDraggablePlatformRigidBody.setType(RigidBody.Type.KINEMATIC);
        rightDraggablePlatformRigidBody.setCollider(BoxCollider.build(rightDraggablePlatformWidth, rightDraggablePlatformHeight));
        rightDraggablePlatformRigidBody.setSleepingAllowed(false);

        SpriteRenderer rightDraggablePlatformRenderer = rightDraggablePlatformGO.addComponent(SpriteRenderer.class);
        rightDraggablePlatformRenderer.setImage(elementsImage);
        rightDraggablePlatformRenderer.setSrcPosition(128, 48);
        rightDraggablePlatformRenderer.setSrcSize(128, 32);
        rightDraggablePlatformRenderer.setSize(rightDraggablePlatformWidth, rightDraggablePlatformHeight);

        PlatformDraggingComponent rightDraggablePlatform = rightDraggablePlatformGO.addComponent(PlatformDraggingComponent.class);
        rightDraggablePlatform.width = 10;
        rightDraggablePlatform.height = 10;
        rightDraggablePlatform.setStart(rightDraggablePlatformGO.x, 12);
        rightDraggablePlatform.setEnd(rightDraggablePlatformGO.x, 3f + rightDraggablePlatformWidth / 2f + 0.25f);
        rightDraggablePlatform.rigidBody = rightDraggablePlatformRigidBody;

        DraggablePlatformLineRenderer rightDraggablePlatformLineRenderer = createGameObject().addComponent(DraggablePlatformLineRenderer.class);
        rightDraggablePlatformLineRenderer.setStart(rightDraggablePlatformGO.x, 12 + rightDraggablePlatformWidth / 2 + 0.25f);
        rightDraggablePlatformLineRenderer.setEnd(rightDraggablePlatformGO.x, 3f);
        rightDraggablePlatformLineRenderer.setRadius(0.25f);
        rightDraggablePlatformLineRenderer.setColor(Color.WHITE);
        rightDraggablePlatformLineRenderer.setWidth(0.1f);
        rightDraggablePlatformLineRenderer.setLayer(-2);

        // Left draggable platform
        float leftDraggablePlatformWidth = 6;
        float leftDraggablePlatformHeight = 0.5f;

        GameObject leftDraggablePlatformGO = createGameObject(-6, 15, 140);

        RigidBody leftDraggablePlatformRigidBody = leftDraggablePlatformGO.addComponent(RigidBody.class);
        leftDraggablePlatformRigidBody.setType(RigidBody.Type.KINEMATIC);
        leftDraggablePlatformRigidBody.setCollider(BoxCollider.build(leftDraggablePlatformWidth, leftDraggablePlatformHeight));
        leftDraggablePlatformRigidBody.setSleepingAllowed(false);

        SpriteRenderer leftDraggablePlatformRenderer = leftDraggablePlatformGO.addComponent(SpriteRenderer.class);
        leftDraggablePlatformRenderer.setImage(elementsImage);
        leftDraggablePlatformRenderer.setSrcPosition(128, 48);
        leftDraggablePlatformRenderer.setSrcSize(128, 32);
        leftDraggablePlatformRenderer.setSize(leftDraggablePlatformWidth, leftDraggablePlatformHeight);

        PlatformDraggingComponent leftDraggablePlatform = leftDraggablePlatformGO.addComponent(PlatformDraggingComponent.class);
        leftDraggablePlatform.rigidBody = leftDraggablePlatformRigidBody;
        leftDraggablePlatform.setSize(10, 10);
        leftDraggablePlatform.setStart(leftDraggablePlatformGO.x, 15);
        leftDraggablePlatform.setEnd(leftDraggablePlatformGO.x, 7);

        DraggablePlatformLineRenderer leftDraggablePlatformLineRenderer = createGameObject().addComponent(DraggablePlatformLineRenderer.class);
        leftDraggablePlatformLineRenderer.setStart(leftDraggablePlatformGO.x, 15.5f);
        leftDraggablePlatformLineRenderer.setEnd(leftDraggablePlatformGO.x, 6.5f);
        leftDraggablePlatformLineRenderer.setRadius(0.25f);
        leftDraggablePlatformLineRenderer.setColor(Color.WHITE);
        leftDraggablePlatformLineRenderer.setLayer(-2);

        // Bridge
        GameObject bridge = createGameObject(-2.5f, -6.6f);

        SpriteRenderer bridgeRenderer = bridge.addComponent(SpriteRenderer.class);
        bridgeRenderer.setImage(elementsImage);
        bridgeRenderer.setSrcPosition(128, 48);
        bridgeRenderer.setSrcSize(128, 32);
        bridgeRenderer.setSize(6.4f, 1);
        bridgeRenderer.setPivot(1f, 0f);
        bridgeRenderer.setLayer(-3);

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
        gameOverTrigger.setOnCollisionEnter(() -> gameOver(rightDraggablePlatform, leftDraggablePlatform));

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

        RectRenderer pressurePlateRenderer = pressurePlateGO.addComponent(RectRenderer.class);
        pressurePlateRenderer.color = Color.RED;
        pressurePlateRenderer.width = pressurePlateWidth;
        pressurePlateRenderer.height = pressurePlateHeight;

        RigidBody pressurePlateRigidBody = pressurePlateGO.addComponent(RigidBody.class);
        pressurePlateRigidBody.setType(RigidBody.Type.STATIC);
        pressurePlateRigidBody.setCollider(BoxCollider.build(pressurePlateWidth, pressurePlateHeight, true));

        PhysicsButton pressurePlate = pressurePlateGO.addComponent(PhysicsButton.class);

        // Pressure plate platform
        float pressurePlatePlatformWidth = 11;
        float pressurePlatePlatformHeight = 1;
        GameObject pressurePlatePlatformGO = createGameObject(3.4f, 2);

        if (DEBUG) {
            RectRenderer pressurePlatePlatformRenderer = pressurePlatePlatformGO.addComponent(RectRenderer.class);
            pressurePlatePlatformRenderer.color = Color.MAGENTA;
            pressurePlatePlatformRenderer.width = pressurePlatePlatformWidth;
            pressurePlatePlatformRenderer.height = pressurePlatePlatformHeight;
            pressurePlatePlatformRenderer.setLayer(64);
        }

        RigidBody pressurePlatePlatformRigidBody = pressurePlatePlatformGO.addComponent(RigidBody.class);
        pressurePlatePlatformRigidBody.setType(RigidBody.Type.STATIC);
        pressurePlatePlatformRigidBody.setCollider(BoxCollider.build(pressurePlatePlatformWidth, pressurePlatePlatformHeight));

        //Linea tra pulsante e ponte
        GameObject lineRendererGO = createGameObject();
        DottedLineRenderer lineRenderer = lineRendererGO.addComponent(DottedLineRenderer.class);
        lineRenderer.setPointA(pressurePlateGO.x, pressurePlateGO.y - 1.25f);
        lineRenderer.setPointB(pressurePlateGO.x, bridge.y);
        lineRenderer.setCount(14);
        lineRenderer.setRadius(0.2f);
        lineRenderer.setColor(Color.GREY);
        lineRenderer.setLayer(-4);

        pressurePlate.setOnCollisionEnter(() -> {
            if (isPressed)
                return;

            isPressed = true;

            buttonSound.play(1);
            pressurePlateRenderer.color = Color.GREEN;
            pressurePlateRigidBody.setTransform(pressurePlateGO.x, pressurePlateGO.y - 0.5f);

            lineRenderer.setColor(PALETTE_PRIMARY);

            animator.clear();
            animator.add(WaitAnimation.build(0.4f), () -> movingPlatformSound.play(1));
            animator.add(MoveToAnimation.build(bridge, 3, bridge.y, 0.25f, EaseFunction.CUBIC_IN_OUT));
            animator.add(WaitAnimation.build(0.2f), () -> {
                characterRenderer.setSrcPosition(128, 128);
                winSound.play(1);
            });
            animator.add(MoveToAnimation.build(character, 8f, character.y, 1f, EaseFunction.CUBIC_IN_OUT));
            animator.add(FadeAnimation.build(fullScreenRenderer, Color.TRANSPARENT, Color.BLACK, 0.75f), () -> loadScene(Level2.class));
            animator.start();
            animator.start();
        });

        //Piattaforma noBugRight
        GameObject noBugPlatformRight = createGameObject(-2.5f, 2.5f, -45);

        if (DEBUG) {
            RectRenderer noBugPlatformRightRenderComponent = noBugPlatformRight.addComponent(RectRenderer.class);
            noBugPlatformRightRenderComponent.width = 1;
            noBugPlatformRightRenderComponent.height = 1;
            noBugPlatformRightRenderComponent.color = Color.MAGENTA;
            noBugPlatformRightRenderComponent.setLayer(64);
        }

        RigidBody noBugPlatfomrRightRigidBody = noBugPlatformRight.addComponent(RigidBody.class);
        noBugPlatfomrRightRigidBody.setType(RigidBody.Type.STATIC);
        noBugPlatfomrRightRigidBody.setCollider(BoxCollider.build(1, 1));

        // Exit cover rectangle
        GameObject exitCover = createGameObject(7.15f, -6.5f);
        RectRenderer exitCoverRenderer = exitCover.addComponent(RectRenderer.class);
        exitCoverRenderer.setSize(1.5f, 3f);
        exitCoverRenderer.setColor(PALETTE_BACKGROUND);
        exitCoverRenderer.setPivot(0, 1f);
        exitCoverRenderer.setLayer(16);

        // Level selection button
        float levelSelectionButtonWidth = 4f;
        float levelSelectionButtonHeight = 4f;

        elementsUIImage = game.getGraphics().newPixmap("graphics/elements-ui.png", Graphics.PixmapFormat.RGB565);
        GameObject menuButtonGO = createGameObject(-Camera.getInstance().getSizeX() / 2 + levelSelectionButtonWidth / 2, Camera.getInstance().getSizeY() / 2 - levelSelectionButtonHeight / 2 - 0.25f);

        SpriteRenderer menuButtonRenderer = menuButtonGO.addComponent(SpriteRenderer.class);
        menuButtonRenderer.setImage(elementsUIImage);
        menuButtonRenderer.setSrcPosition(0, 0);
        menuButtonRenderer.setSrcSize(128, 128);
        menuButtonRenderer.setSize(levelSelectionButtonWidth, levelSelectionButtonHeight);
        menuButtonRenderer.setLayer(32);
        menuButtonRenderer.setPivot(0.5f, 0.5f);

        Button menuButton = menuButtonGO.addComponent(Button.class);
        menuButton.setSize(levelSelectionButtonWidth, levelSelectionButtonHeight);
        menuButton.setOnClick(() -> {
            menuButton.setInteractable(false);
            buttonSound.play(1);

            animator.clear();
            animator.add(FadeAnimation.build(fullScreenRenderer, Color.TRANSPARENT, Color.BLACK, 0.75f), () -> loadScene(MainMenu.class));
            animator.start();
        });
    }

    @Override
    public void dispose() {
        super.dispose();

        animator = null;
        fullScreenRenderer = null;
        characterRenderer = null;

        elementsImage.dispose();
        elementsUIImage.dispose();
    }

    private void gameOver(PressableComponent... interactableComponents) {
        for (PressableComponent component : interactableComponents)
            component.interactable = false;

        // Animator
        characterRenderer.setSize(4, 0.5f);

        animator.clear();
        animator.add(WaitAnimation.build(2));
        animator.add(FadeAnimation.build(fullScreenRenderer, Color.TRANSPARENT, Color.BLACK, 0.5f), this::retry);
        animator.start();
    }

    private void retry() {
        loadScene(Level2.class);
    }

}
