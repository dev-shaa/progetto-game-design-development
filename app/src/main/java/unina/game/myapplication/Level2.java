package unina.game.myapplication;

import com.badlogic.androidgames.framework.Color;
import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Graphics;
import com.badlogic.androidgames.framework.Music;
import com.badlogic.androidgames.framework.Pixmap;
import com.badlogic.androidgames.framework.Sound;

import unina.game.myapplication.core.Camera;
import unina.game.myapplication.core.GameObject;
import unina.game.myapplication.core.animations.AnimationSequence;
import unina.game.myapplication.core.animations.EaseFunction;
import unina.game.myapplication.core.animations.LoopingAnimation;
import unina.game.myapplication.core.animations.MoveToAnimation;
import unina.game.myapplication.core.animations.ParallelAnimation;
import unina.game.myapplication.core.animations.Sequence;
import unina.game.myapplication.core.animations.WaitAnimation;
import unina.game.myapplication.core.physics.BoxCollider;
import unina.game.myapplication.core.physics.CircleCollider;
import unina.game.myapplication.core.physics.RigidBody;
import unina.game.myapplication.core.rendering.SpriteRenderer;
import unina.game.myapplication.logic.common.ColorAnimation;
import unina.game.myapplication.logic.common.DraggablePlatformLineRenderer;
import unina.game.myapplication.logic.PhysicsButton;
import unina.game.myapplication.logic.PlatformDraggingComponent;
import unina.game.myapplication.logic.common.Button;
import unina.game.myapplication.logic.common.CollisionSoundPlayer;
import unina.game.myapplication.logic.common.DottedLineRenderer;
import unina.game.myapplication.logic.common.FadeAnimation;
import unina.game.myapplication.logic.common.Level;
import unina.game.myapplication.logic.common.RectRenderer;
import unina.game.myapplication.logic.menu.MainMenu;

public class Level2 extends Level {

    private static final int PALETTE_BACKGROUND = 0xff237F52;
    private static final int PALETTE_PRIMARY = 0xffECECE7;

    private Sound buttonSound;
    private Sound buttonsAppearSound;
    private Sound movingPlatformSound;
    private Sound movingRock;
    private Sound winSound;
    private Sound rockCrushSound;
    private Music backgroundMusic;

    private boolean isPressed = false;

    private Pixmap backgroundImage, elementsImage, elementsUIImage;

    public Level2(Game game) {
        super(game);
    }

    @Override
    public void initialize() {
        super.initialize();

        backgroundMusic = game.getAudio().newMusic("sounds/HappyLoops/intro.wav");
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.6f);

        backgroundImage = game.getGraphics().newPixmap("graphics/environment-brick-wall.png", Graphics.PixmapFormat.RGB565);
        elementsImage = game.getGraphics().newPixmap("graphics/elements-light.png", Graphics.PixmapFormat.ARGB8888);
        elementsUIImage = game.getGraphics().newPixmap("graphics/elements-ui.png", Graphics.PixmapFormat.RGB565);
        movingRock = game.getAudio().newSound("sounds/moving-rock.mp3");
        buttonSound = game.getAudio().newSound("sounds/kenney-interface-sounds/click_002.ogg");
        buttonsAppearSound = game.getAudio().newSound("sounds/kenney-ui-sounds/switch4.ogg");
        movingPlatformSound = game.getAudio().newSound("sounds/kenney-interface-sounds/error_001.ogg"); // FIXME: placeholder
        rockCrushSound = game.getAudio().newSound("sounds/rock-crush.mp3");
        winSound = game.getAudio().newSound("sounds/kenney-sax-jingles/jingles_SAX10.ogg");

        Camera.getInstance().setSize(20);

        //Background
        setClearColor(PALETTE_BACKGROUND);

        GameObject backgroundGO = createGameObject();
        SpriteRenderer backgroundRenderer = backgroundGO.addComponent(SpriteRenderer.class);
        backgroundRenderer.setImage(backgroundImage);
        backgroundRenderer.setSize(20, 20 / backgroundImage.getAspectRatio());
        backgroundRenderer.setLayer(16);

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

        // Transition panel
        GameObject fade = createGameObject();
        RectRenderer fullScreenRenderer = fade.addComponent(RectRenderer.class);
        fullScreenRenderer.setSize(50, 50);
        fullScreenRenderer.setLayer(256);
        fullScreenRenderer.setColor(Color.TRANSPARENT);

        // Prompt Renderer
        GameObject promptGameObject = createGameObject(5, 11, 45);

        SpriteRenderer promptRenderer = promptGameObject.addComponent(SpriteRenderer.class);
        promptRenderer.setImage(elementsUIImage);
        promptRenderer.setSize(3, 3);
        promptRenderer.setSrcSize(128, 128);
        promptRenderer.setSrcPosition(0, 256);
        promptRenderer.setPivot(0.4f, 0);
        promptRenderer.setTint(Color.TRANSPARENT);
        promptRenderer.setLayer(128);

        AnimationSequence promptAnimator = promptGameObject.addComponent(AnimationSequence.class);

        Sequence promptAnimationSequence = Sequence.build();
        promptAnimationSequence.add(WaitAnimation.build(1));
        promptAnimationSequence.add(ParallelAnimation.build(
                MoveToAnimation.build(promptGameObject, 3, 12, 0.5f, EaseFunction.CUBIC_IN_OUT),
                ColorAnimation.build(promptRenderer::setTint, Color.TRANSPARENT, Color.WHITE, 0.4f)
        ));
        promptAnimationSequence.add(WaitAnimation.build(0.5f));
        promptAnimationSequence.add(MoveToAnimation.build(promptGameObject, 3, 6, 1, EaseFunction.CUBIC_IN_OUT));
        promptAnimationSequence.add(ColorAnimation.build(promptRenderer::setTint, Color.WHITE, Color.TRANSPARENT, 0.2f));

        promptAnimator.add(new LoopingAnimation(promptAnimationSequence, () -> promptGameObject.setTransform(5, 11, 45)));
        promptAnimator.start();

        // Animator
        GameObject animatorGO = createGameObject();
        AnimationSequence animator = animatorGO.addComponent(AnimationSequence.class);
        animator.add(FadeAnimation.build(fullScreenRenderer, Color.BLACK, Color.TRANSPARENT, 0.5f));
        animator.start();

        float floorW = 6;
        float floorH = 15;

        // Left floor
        GameObject leftFloor = createGameObject(-6, -14);

        RigidBody leftFloorRigidBody = leftFloor.addComponent(RigidBody.class);
        leftFloorRigidBody.setType(RigidBody.Type.STATIC);
        leftFloorRigidBody.addCollider(BoxCollider.build(floorW, floorH));

        // Rock Platform
        float rockPlatformWidth = 9;
        float rockPlatformHeight = 0.5f;

        GameObject rockPlatform = createGameObject(4.5f, 11, 30);

        RigidBody rockPlatformRigidBody = rockPlatform.addComponent(RigidBody.class);
        rockPlatformRigidBody.setType(RigidBody.Type.STATIC);
        rockPlatformRigidBody.addCollider(BoxCollider.build(rockPlatformWidth, rockPlatformHeight));

        // Left wall
        float plat2W = 1;
        float plat2H = 20;

        GameObject leftWall = createGameObject(-9.25f, 5);

        RigidBody LeftWallRigidBody2 = leftWall.addComponent(RigidBody.class);
        LeftWallRigidBody2.setType(RigidBody.Type.STATIC);
        LeftWallRigidBody2.addCollider(BoxCollider.build(plat2W, plat2H));

        // Right wall
        GameObject rightWall = createGameObject(9.25f, 5);

        RigidBody RightWallRigidBody2 = rightWall.addComponent(RigidBody.class);
        RightWallRigidBody2.setType(RigidBody.Type.STATIC);
        RightWallRigidBody2.addCollider(BoxCollider.build(1, 8));

        // Rock
        GameObject rock = createGameObject(5, 15);

        SpriteRenderer rockRenderer = rock.addComponent(SpriteRenderer.class);
        rockRenderer.setImage(elementsImage);
        rockRenderer.setSize(4, 4);
        rockRenderer.setSrcPosition(256, 0);
        rockRenderer.setSrcSize(128, 128);

        RigidBody rockRigidBody = rock.addComponent(RigidBody.class);
        rockRigidBody.setType(RigidBody.Type.DYNAMIC);
        rockRigidBody.addCollider(CircleCollider.build(2, 100, 0, 1, false));
        rockRigidBody.setSleepingAllowed(false);

        CollisionSoundPlayer rockCollisionSoundPlayer = rock.addComponent(CollisionSoundPlayer.class);
        rockCollisionSoundPlayer.setSound(movingRock); // TODO: placeholder sound
        rockCollisionSoundPlayer.setVolume(0.7f);

        // Right draggable platform
        float rightDraggablePlatformWidth = 4f;
        float rightDraggablePlatformHeight = 1f;

        GameObject rightDraggablePlatformGO = createGameObject(3, 12, 90);

        RigidBody rightDraggablePlatformRigidBody = rightDraggablePlatformGO.addComponent(RigidBody.class);
        rightDraggablePlatformRigidBody.setType(RigidBody.Type.KINEMATIC);
        rightDraggablePlatformRigidBody.addCollider(BoxCollider.build(rightDraggablePlatformWidth, rightDraggablePlatformHeight));

        SpriteRenderer rightDraggablePlatformRenderer = rightDraggablePlatformGO.addComponent(SpriteRenderer.class);
        rightDraggablePlatformRenderer.setImage(elementsImage);
        rightDraggablePlatformRenderer.setSrcPosition(256, 418);
        rightDraggablePlatformRenderer.setSrcSize(128, 34);
        rightDraggablePlatformRenderer.setSize(rightDraggablePlatformWidth, rightDraggablePlatformHeight);

        PlatformDraggingComponent rightDraggablePlatform = rightDraggablePlatformGO.addComponent(PlatformDraggingComponent.class);
        rightDraggablePlatform.setSize(4, 4);
        rightDraggablePlatform.setStart(rightDraggablePlatformGO.x, 12);
        rightDraggablePlatform.setEnd(rightDraggablePlatformGO.x, 5.75f + rightDraggablePlatformWidth / 2f + 0.25f);
        rightDraggablePlatform.setRigidBody(rightDraggablePlatformRigidBody);

        DraggablePlatformLineRenderer rightDraggablePlatformLineRenderer = createGameObject().addComponent(DraggablePlatformLineRenderer.class);
        rightDraggablePlatformLineRenderer.setStart(rightDraggablePlatformGO.x, 12 + rightDraggablePlatformWidth / 2 + 0.25f);
        rightDraggablePlatformLineRenderer.setEnd(rightDraggablePlatformGO.x, 5.75f);
        rightDraggablePlatformLineRenderer.setRadius(0.25f);
        rightDraggablePlatformLineRenderer.setColor(Color.WHITE);
        rightDraggablePlatformLineRenderer.setWidth(0.15f);
        rightDraggablePlatformLineRenderer.setLayer(-2);

        // Left draggable platform
        float leftDraggablePlatformWidth = 4f;
        float leftDraggablePlatformHeight = 1f;

        GameObject leftDraggablePlatformGO = createGameObject(-5.5f, 15f, 130f);

        RigidBody leftDraggablePlatformRigidBody = leftDraggablePlatformGO.addComponent(RigidBody.class);
        leftDraggablePlatformRigidBody.setType(RigidBody.Type.KINEMATIC);
        leftDraggablePlatformRigidBody.addCollider(BoxCollider.build(leftDraggablePlatformWidth, leftDraggablePlatformHeight));
        leftDraggablePlatformRigidBody.setSleepingAllowed(false);

        SpriteRenderer leftDraggablePlatformRenderer = leftDraggablePlatformGO.addComponent(SpriteRenderer.class);
        leftDraggablePlatformRenderer.setImage(elementsImage);
        leftDraggablePlatformRenderer.setSrcPosition(256, 418);
        leftDraggablePlatformRenderer.setSrcSize(128, 34);
        leftDraggablePlatformRenderer.setSize(leftDraggablePlatformWidth, leftDraggablePlatformHeight);

        PlatformDraggingComponent leftDraggablePlatform = leftDraggablePlatformGO.addComponent(PlatformDraggingComponent.class);
        leftDraggablePlatform.setRigidBody(leftDraggablePlatformRigidBody);
        leftDraggablePlatform.setSize(4, 4);
        leftDraggablePlatform.setStart(leftDraggablePlatformGO.x, 15);
        leftDraggablePlatform.setEnd(leftDraggablePlatformGO.x, 7);

        DraggablePlatformLineRenderer leftDraggablePlatformLineRenderer = createGameObject().addComponent(DraggablePlatformLineRenderer.class);
        leftDraggablePlatformLineRenderer.setStart(leftDraggablePlatformGO.x, 15.5f);
        leftDraggablePlatformLineRenderer.setEnd(leftDraggablePlatformGO.x, 6.5f);
        leftDraggablePlatformLineRenderer.setRadius(0.25f);
        leftDraggablePlatformLineRenderer.setColor(Color.WHITE);
        leftDraggablePlatformLineRenderer.setLayer(-2);

        rightDraggablePlatform.setOnDrag(() -> {
            removeGameObject(promptGameObject);
            rightDraggablePlatform.setOnDrag(null);
            leftDraggablePlatform.setOnDrag(null);
        });
        leftDraggablePlatform.setOnDrag(() -> {
            removeGameObject(promptGameObject);
            rightDraggablePlatform.setOnDrag(null);
            leftDraggablePlatform.setOnDrag(null);
        });

        // Bridge
        GameObject bridge = createGameObject(2.5f, -6.6f);

        SpriteRenderer bridgeRenderer = bridge.addComponent(SpriteRenderer.class);
        bridgeRenderer.setImage(elementsImage);
        bridgeRenderer.setSrcPosition(128, 48);
        bridgeRenderer.setSrcSize(128, 32);
        bridgeRenderer.setSize(6.4f, 1);
        bridgeRenderer.setPivot(0f, 0f);
        bridgeRenderer.setLayer(-3);

        // Character
        GameObject character = createGameObject(-6, -6.5f);

        SpriteRenderer characterRenderer = character.addComponent(SpriteRenderer.class);
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
        gameOverTriggerRigidBody.addCollider(BoxCollider.build(4, 1, true));

        PhysicsButton gameOverTrigger = gameOverTriggerGO.addComponent(PhysicsButton.class);
        gameOverTrigger.setOnCollisionEnter(() -> {
            rockCrushSound.play(1);
            rightDraggablePlatform.setInteractable(false);
            leftDraggablePlatform.setInteractable(false);

            // Animator
            characterRenderer.setSize(4, 0.5f);

            animator.clear();
            animator.add(WaitAnimation.build(2));
            animator.add(FadeAnimation.build(fullScreenRenderer, Color.TRANSPARENT, Color.BLACK, 0.5f), () -> loadScene(Level2.class));
            animator.start();
        });

        // Pressure plate
        float pressurePlateWidth = 4;
        float pressurePlateHeight = 4;
        GameObject pressurePlateGO = createGameObject(6.75f, 0.35f);

        SpriteRenderer pressurePlateRenderer = pressurePlateGO.addComponent(SpriteRenderer.class);
        pressurePlateRenderer.setImage(elementsImage);
        pressurePlateRenderer.setPivot(0.5f, 1f);
        pressurePlateRenderer.setSize(pressurePlateWidth, pressurePlateHeight);
        pressurePlateRenderer.setSrcSize(128, 128);
        pressurePlateRenderer.setSrcPosition(0, 384);

        BoxCollider pressurePlateCollider = BoxCollider.build(pressurePlateWidth, 0.25f, true);
        pressurePlateCollider.setCenter(0, 0.5f);

        RigidBody pressurePlateRigidBody = pressurePlateGO.addComponent(RigidBody.class);
        pressurePlateRigidBody.setType(RigidBody.Type.STATIC);
        pressurePlateRigidBody.addCollider(pressurePlateCollider);

        PhysicsButton pressurePlate = pressurePlateGO.addComponent(PhysicsButton.class);

        // Pressure plate platform
        float pressurePlatePlatformWidth = 6.5f;
        GameObject pressurePlatePlatformGO = createGameObject(1.5f, 0.25f);

        BoxCollider pressurePlatePlatformCollider = BoxCollider.build(pressurePlatePlatformWidth, 1);
        pressurePlatePlatformCollider.setCenter(0, 0.5f);

        BoxCollider pressurePlatePlatformSecondaryCollider = BoxCollider.build(4, 0.5f);
        pressurePlatePlatformSecondaryCollider.setCenter(pressurePlatePlatformWidth / 2 + 2, 0.25f);

        BoxCollider pressurePlatePlatformThirdCollider = BoxCollider.build(2, 1);
        pressurePlatePlatformThirdCollider.setAngle(-45);
        pressurePlatePlatformThirdCollider.setCenter(-4.25f, 1.25f);

        RigidBody pressurePlatePlatformRigidBody = pressurePlatePlatformGO.addComponent(RigidBody.class);
        pressurePlatePlatformRigidBody.setType(RigidBody.Type.STATIC);
        pressurePlatePlatformRigidBody.addCollider(pressurePlatePlatformCollider);
        pressurePlatePlatformRigidBody.addCollider(pressurePlatePlatformSecondaryCollider);
        pressurePlatePlatformRigidBody.addCollider(pressurePlatePlatformThirdCollider);

        // Bridge activation line
        DottedLineRenderer lineRendererA = createDottedLine(6.75f, 0.15f, 6.75f, -0.4f, 2);
        DottedLineRenderer lineRendererB = createDottedLine(6.75f, -0.4f, 8.25f, -0.4f, 3);
        DottedLineRenderer lineRendererC = createDottedLine(8.25f, -0.4f, 8.25f, -7.15f, 9);
        DottedLineRenderer lineRendererD = createDottedLine(3.75f, -7.15f, 8.25f, -7.15f, 6);

        pressurePlate.setOnCollisionEnter(() -> {
            if (isPressed)
                return;

            isPressed = true;

            menuButton.setInteractable(false);
            removeGameObject(menuButtonGO);

            buttonSound.play(1);
            pressurePlateRenderer.setSrcPosition(128, 384);

            lineRendererA.setColor(0xff009fff);
            lineRendererB.setColor(0xff009fff);
            lineRendererC.setColor(0xff009fff);
            lineRendererD.setColor(0xff009fff);

            animator.clear();
            animator.add(WaitAnimation.build(0.8f), () -> movingPlatformSound.play(1));
            animator.add(MoveToAnimation.build(bridge, -2.75f, bridge.y, 0.25f, EaseFunction.CUBIC_IN_OUT));
            animator.add(WaitAnimation.build(0.2f), () -> {
                characterRenderer.setSrcPosition(128, 128);
                winSound.play(1);
            });
            animator.add(MoveToAnimation.build(character, 8f, character.y, 1f, EaseFunction.CUBIC_IN_OUT));
            animator.add(FadeAnimation.build(fullScreenRenderer, Color.TRANSPARENT, Color.BLACK, 0.75f), this::loadNextLevel);
            animator.start();

            saveProgress();
        });

        menuButton.setOnClick(() -> {
            // Prevent user from clicking again
            menuButton.setInteractable(false);

            // Disable the pressure plate so the win condition can't happen in the small interval when fading to menu
            pressurePlate.setOnCollisionEnter(null);

            // Play the sound
            buttonSound.play(1);

            // Fade animation and load main menu
            animator.clear();
            animator.add(FadeAnimation.build(fullScreenRenderer, Color.TRANSPARENT, Color.BLACK, 0.75f), () -> loadScene(MainMenu.class));
            animator.start();
        });
    }

    @Override
    public void resume() {
        super.resume();
        backgroundMusic.play();
    }

    @Override
    public void pause() {
        super.pause();
        backgroundMusic.pause();
    }

    @Override
    public void dispose() {
        super.dispose();

        backgroundMusic.stop();
        backgroundMusic.dispose();
        buttonsAppearSound.dispose();

        elementsImage.dispose();
        elementsUIImage.dispose();
        backgroundImage.dispose();
    }

    private DottedLineRenderer createDottedLine(float startX, float startY, float endX, float endY, int count) {
        DottedLineRenderer lineRenderer = createGameObject().addComponent(DottedLineRenderer.class);
        lineRenderer.setPointA(startX, startY);
        lineRenderer.setPointB(endX, endY);
        lineRenderer.setCount(count);
        lineRenderer.setRadius(0.2f);
        lineRenderer.setColor(Color.GREY);
        lineRenderer.setLayer(128);
        return lineRenderer;
    }

    @Override
    protected int getLevelIndex() {
        return 1;
    }

}
