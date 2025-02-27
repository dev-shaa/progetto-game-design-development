package unina.game.myapplication;

import com.badlogic.androidgames.framework.Color;
import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Graphics;
import com.badlogic.androidgames.framework.Music;
import com.badlogic.androidgames.framework.Pixmap;
import com.badlogic.androidgames.framework.Sound;

import unina.game.myapplication.core.Camera;
import unina.game.myapplication.core.GameObject;
import unina.game.myapplication.core.Scene;
import unina.game.myapplication.core.animations.AnimationSequence;
import unina.game.myapplication.core.animations.EaseFunction;
import unina.game.myapplication.core.animations.MoveRigidBodyTo;
import unina.game.myapplication.core.animations.MoveToAnimation;
import unina.game.myapplication.core.animations.ParallelAnimation;
import unina.game.myapplication.core.animations.WaitAnimation;
import unina.game.myapplication.core.physics.BoxCollider;
import unina.game.myapplication.core.physics.CircleCollider;
import unina.game.myapplication.core.physics.RigidBody;
import unina.game.myapplication.core.rendering.SpriteRenderer;
import unina.game.myapplication.logic.PhysicsButton;
import unina.game.myapplication.logic.PlatformDraggingComponent;
import unina.game.myapplication.logic.PlatformRenderComponent;
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
    private Sound movingPlatformSound;
    private Sound winSound, fallSound;

    private Music backgroundMusic;

    private Pixmap backgroundImage, elementsImage, uiSpriteSheet;

    private RectRenderer fullScreenRenderer;
    private AnimationSequence animator;
    private SpriteRenderer characterRenderer;

    private boolean isPressedLose = false;
    private boolean isPressedWin = false;

    public Level3(Game game) {
        super(game);
    }

    @Override
    public void initialize() {
        super.initialize();

        backgroundImage = game.getGraphics().newPixmap("graphics/environment-construction-site.png", Graphics.PixmapFormat.RGB565);
        elementsImage = game.getGraphics().newPixmap("graphics/elements-dark.png", Graphics.PixmapFormat.ARGB8888);
        uiSpriteSheet = game.getGraphics().newPixmap("graphics/elements-ui.png", Graphics.PixmapFormat.RGB565);

        buttonSound = game.getAudio().newSound("sounds/kenney-interface-sounds/click_002.ogg");
        movingPlatformSound = game.getAudio().newSound("sounds/kenney-interface-sounds/error_001.ogg"); // FIXME: placeholder
        winSound = game.getAudio().newSound("sounds/kenney-sax-jingles/jingles_SAX10.ogg");
        fallSound = game.getAudio().newSound("sounds/fall.wav");

        backgroundMusic = game.getAudio().newMusic("sounds/HappyLoops/intro.wav");
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(1);

        Camera.getInstance().setSize(20);

        // Background
        setClearColor(PALETTE_BACKGROUND);

        SpriteRenderer backgroundRenderer = createGameObject().addComponent(SpriteRenderer.class);
        backgroundRenderer.setImage(backgroundImage);
        backgroundRenderer.setSize(20, 20 / backgroundImage.getAspectRatio());
        backgroundRenderer.setLayer(16);

        // Transition panel
        fullScreenRenderer = createGameObject().addComponent(RectRenderer.class);
        fullScreenRenderer.setSize(50, 50);
        fullScreenRenderer.setLayer(Integer.MAX_VALUE);
        fullScreenRenderer.setColor(Color.TRANSPARENT);

        // Animator
        animator = createGameObject().addComponent(AnimationSequence.class);
        animator.add(FadeAnimation.build(fullScreenRenderer, Color.BLACK, Color.TRANSPARENT, 0.5f));
        animator.start();

        // Level selection button
        float levelSelectionButtonWidth = 4f;
        float levelSelectionButtonHeight = 4f;

        GameObject menuButtonGO = createGameObject(-Camera.getInstance().getSizeX() / 2 + levelSelectionButtonWidth / 2, Camera.getInstance().getSizeY() / 2 - levelSelectionButtonHeight / 2 - 0.25f);

        SpriteRenderer menuButtonRenderer = menuButtonGO.addComponent(SpriteRenderer.class);
        menuButtonRenderer.setImage(uiSpriteSheet);
        menuButtonRenderer.setSrcPosition(0, 0);
        menuButtonRenderer.setSrcSize(128, 128);
        menuButtonRenderer.setSize(levelSelectionButtonWidth, levelSelectionButtonHeight);
        menuButtonRenderer.setLayer(32);
        menuButtonRenderer.setPivot(0.5f, 0.5f);

        Button menuButton = menuButtonGO.addComponent(Button.class);
        menuButton.setSize(levelSelectionButtonWidth, levelSelectionButtonHeight);
        menuButton.setOnClick(() -> {
            // Prevent user from clicking again
            menuButton.setInteractable(false);

            // Play the sound
            buttonSound.play(1);

            // Fade animation and load main menu
            animator.clear();
            animator.add(FadeAnimation.build(fullScreenRenderer, Color.TRANSPARENT, Color.BLACK, 0.75f), () -> loadScene(MainMenu.class));
            animator.start();
        });

        //TODO cambiare nomi bridge

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
        GameObject rock = createGameObject(1, 14);

        SpriteRenderer rockRenderer = rock.addComponent(SpriteRenderer.class);
        rockRenderer.setImage(elementsImage);
        rockRenderer.setSize(2, 2);
        rockRenderer.setSrcPosition(256, 0);
        rockRenderer.setSrcSize(128, 128);

        RigidBody rockRigidBody = rock.addComponent(RigidBody.class);
        rockRigidBody.setType(RigidBody.Type.DYNAMIC);
        rockRigidBody.addCollider(CircleCollider.build(1, 100, 0, 1, false));
        rockRigidBody.setSleepingAllowed(false);

        // Right wall
        float rightWallWidth = 0.7f;
        float rightWallHeight = 17;

        GameObject rightWall = createGameObject(8, 7);

        RigidBody rightWallRigidBody = rightWall.addComponent(RigidBody.class);
        rightWallRigidBody.setType(RigidBody.Type.STATIC);
        rightWallRigidBody.addCollider(BoxCollider.build(rightWallWidth, rightWallHeight));

        if (DEBUG) {
            PlatformRenderComponent rightWallRenderComponent = rightWall.addComponent(PlatformRenderComponent.class);
            rightWallRenderComponent.color = Color.MAGENTA;
            rightWallRenderComponent.setSize(rightWallWidth, rightWallHeight);
            rightWallRenderComponent.setLayer(64);
        }

        // Left wall
        float leftWallWidht = 0.7f;
        float leftWallHeight = 8f;

        GameObject leftWall = createGameObject(-0.75f, 4.5f);

        RigidBody leftWallRigidBody = leftWall.addComponent(RigidBody.class);
        leftWallRigidBody.setType(RigidBody.Type.STATIC);
        leftWallRigidBody.addCollider(BoxCollider.build(leftWallWidht, leftWallHeight));

        if (DEBUG) {
            PlatformRenderComponent leftWallRenderComponent = leftWall.addComponent(PlatformRenderComponent.class);
            leftWallRenderComponent.color = Color.MAGENTA;
            leftWallRenderComponent.setSize(leftWallWidht, leftWallHeight);
            leftWallRenderComponent.setLayer(64);
        }

        //Ponte Masso
        GameObject bridgeRock = createGameObject(1, 13);

        RigidBody bridgeRockRigidBody = bridgeRock.addComponent(RigidBody.class);
        bridgeRockRigidBody.setType(RigidBody.Type.KINEMATIC);
        bridgeRockRigidBody.addCollider(BoxCollider.build(4, 0.7f));

        SpriteRenderer bridgeRockRender = bridgeRock.addComponent(SpriteRenderer.class);
        bridgeRockRender.setImage(elementsImage);
        bridgeRockRender.setSrcPosition(128, 48);
        bridgeRockRender.setSrcSize(128, 32);
        bridgeRockRender.setSize(4, 0.7f);
        bridgeRockRender.setLayer(-3);

//        PlatformRenderComponent bridgeRockRenderComponent = bridgeRock.addComponent(PlatformRenderComponent.class);
//        bridgeRockRenderComponent.color = Color.GOLD;
//        bridgeRockRenderComponent.width = 4;
//        bridgeRockRenderComponent.height = 0.7f;


        //Piattaforma scorrevole
        float dragPlatformWidth = 5;
        float dragPlatformHeight = 0.5f;

        GameObject platformDragged = createGameObject(5, 3f, 130);

        RigidBody rigidDrag = platformDragged.addComponent(RigidBody.class);
        rigidDrag.setType(RigidBody.Type.KINEMATIC);
        rigidDrag.addCollider(BoxCollider.build(dragPlatformWidth, dragPlatformHeight));
        rigidDrag.setSleepingAllowed(false);

        PlatformRenderComponent platformDraggedRenderComponent = platformDragged.addComponent(PlatformRenderComponent.class);
        platformDraggedRenderComponent.color = Color.DARKCYAN;
        platformDraggedRenderComponent.setSize(dragPlatformWidth, dragPlatformHeight);
        platformDraggedRenderComponent.setStart(-4, 13);
        platformDraggedRenderComponent.setEnd(5, 5);

        PlatformDraggingComponent platformDraggingComponent = platformDragged.addComponent(PlatformDraggingComponent.class);
        platformDraggingComponent.width = 10;
        platformDraggingComponent.height = 10;
        platformDraggingComponent.rigidBody = rigidDrag;
        platformDraggingComponent.setStart(1.7f, 6.6f);
        platformDraggingComponent.setEnd(5, 3f);

        //Button
        CircleRenderer buttonCircleRender = createGameObject(-6, 5).addComponent(CircleRenderer.class);
        buttonCircleRender.setRadius(0.6f);
        buttonCircleRender.setColor(PALETTE_PRIMARY);

        GameObject button = createGameObject(-6, 5);

        SpriteRenderer buttonRenderComponent = button.addComponent(SpriteRenderer.class);
        buttonRenderComponent.setImage(elementsImage);
        buttonRenderComponent.setSize(2, 2);
        buttonRenderComponent.setSrcPosition(0, 0);
        buttonRenderComponent.setSrcSize(128, 128);
        buttonRenderComponent.setLayer(3);

        Button buttonInputComponent = button.addComponent(Button.class);
        buttonInputComponent.setSize(3, 3);

        //Linea pulsante ponte orizzontale
        GameObject lineRendererVerticalGO = createGameObject();
        DottedLineRenderer lineVerticalRenderer = lineRendererVerticalGO.addComponent(DottedLineRenderer.class);
        lineVerticalRenderer.setPointA(button.x, bridgeRock.y);
        lineVerticalRenderer.setPointB(bridgeRock.x, bridgeRock.y);
        lineVerticalRenderer.setCount(10);
        lineVerticalRenderer.setRadius(0.2f);
        lineVerticalRenderer.setColor(PALETTE_PRIMARY);
        lineVerticalRenderer.setLayer(-4);

        //Linea pulsante ponte verticale
        GameObject lineRendererHorizontalGO = createGameObject();
        DottedLineRenderer lineHorizontalRenderer = lineRendererHorizontalGO.addComponent(DottedLineRenderer.class);
        lineHorizontalRenderer.setPointA(button.x, button.y + 1.25f);
        lineHorizontalRenderer.setPointB(button.x, bridgeRock.y);
        lineHorizontalRenderer.setCount(10);
        lineHorizontalRenderer.setRadius(0.2f);
        lineHorizontalRenderer.setColor(PALETTE_PRIMARY);
        lineHorizontalRenderer.setLayer(-4);

        buttonInputComponent.setOnClick(() -> {
            buttonInputComponent.setInteractable(false);
            buttonSound.play(1);
            buttonCircleRender.setColor(Color.GREY);
            lineVerticalRenderer.setColor(Color.GREY);
            lineHorizontalRenderer.setColor(Color.GREY);

            animator.clear();
            animator.add(WaitAnimation.build(0.4f), () -> movingPlatformSound.play(1));
            animator.add(MoveRigidBodyTo.build(bridgeRockRigidBody, bridgeRock.x - 4, bridgeRock.y, 0.25f, EaseFunction.CUBIC_IN_OUT));
            animator.start();
        });

        //Lose pressure plate
        float pressurePlateWidth = 2;
        float pressurePlateHeight = 0.5f;

        GameObject pressurePlateLoseGO = createGameObject(1, 1);

        PlatformRenderComponent pressurePlateLoseRenderer = pressurePlateLoseGO.addComponent(PlatformRenderComponent.class);
        pressurePlateLoseRenderer.color = Color.RED;
        pressurePlateLoseRenderer.setSize(pressurePlateWidth, pressurePlateHeight);

        RigidBody pressurePlateLoseRigidBody = pressurePlateLoseGO.addComponent(RigidBody.class);
        pressurePlateLoseRigidBody.setType(RigidBody.Type.STATIC);
        pressurePlateLoseRigidBody.addCollider(BoxCollider.build(pressurePlateWidth, pressurePlateHeight, true));

        PhysicsButton pressurePlateLose = pressurePlateLoseGO.addComponent(PhysicsButton.class);

        //Linea orizzontale lose
        GameObject lineRenderLoseHorizzontalGO = createGameObject();
        DottedLineRenderer lineRendererLoseHorizzontal = lineRenderLoseHorizzontalGO.addComponent(DottedLineRenderer.class);
        lineRendererLoseHorizzontal.setPointA(pressurePlateLoseGO.x - 1.5f, pressurePlateLoseGO.y);
        lineRendererLoseHorizzontal.setPointB(-5, pressurePlateLoseGO.y);
        lineRendererLoseHorizzontal.setColor(PALETTE_PRIMARY);
        lineRendererLoseHorizzontal.setRadius(0.2f);
        lineRendererLoseHorizzontal.setLayer(-3);
        lineRendererLoseHorizzontal.setCount(7);

        //Linea verticale lose
        GameObject lineRenderLoseVerticalGO = createGameObject();
        DottedLineRenderer lineRendererLoseVertical = lineRenderLoseVerticalGO.addComponent(DottedLineRenderer.class);
        lineRendererLoseVertical.setPointA(-5, pressurePlateLoseGO.y);
        lineRendererLoseVertical.setPointB(-5, bridge1.y + 0.5f);
        lineRendererLoseVertical.setColor(PALETTE_PRIMARY);
        lineRendererLoseVertical.setRadius(0.2f);
        lineRendererLoseVertical.setLayer(-3);
        lineRendererLoseVertical.setCount(10);

        // Win pressure plate
        GameObject pressurePlateWinGO = createGameObject(6, 1);

        PlatformRenderComponent pressurePlateWinRenderer = pressurePlateWinGO.addComponent(PlatformRenderComponent.class);
        pressurePlateWinRenderer.color = Color.BLUE;
        pressurePlateWinRenderer.setSize(pressurePlateWidth, pressurePlateHeight);

        RigidBody pressurePlateWinRigidBody = pressurePlateWinGO.addComponent(RigidBody.class);
        pressurePlateWinRigidBody.setType(RigidBody.Type.STATIC);
        pressurePlateWinRigidBody.addCollider(BoxCollider.build(pressurePlateWidth, pressurePlateHeight, true));

        PhysicsButton pressurePlateWin = pressurePlateWinGO.addComponent(PhysicsButton.class);

        //Linea verticale win
        GameObject lineRenderWinVerticalGO = createGameObject();
        DottedLineRenderer lineRendererWinVertical = lineRenderWinVerticalGO.addComponent(DottedLineRenderer.class);
        lineRendererWinVertical.setPointA(pressurePlateWinGO.x, pressurePlateWinGO.y - 1);
        lineRendererWinVertical.setPointB(pressurePlateWinGO.x, bridgeCharacter.y + 0.5f);
        lineRendererWinVertical.setColor(PALETTE_PRIMARY);
        lineRendererWinVertical.setRadius(0.2f);
        lineRendererWinVertical.setLayer(-3);
        lineRendererWinVertical.setCount(10);

        pressurePlateLose.setOnCollisionEnter(() -> {
            if (isPressedLose)
                return;

            isPressedLose = true;

            buttonSound.play(1);
            pressurePlateLoseRenderer.color = Color.GREEN;
            pressurePlateLoseRigidBody.setTransform(pressurePlateLoseGO.x, pressurePlateLoseGO.y - 0.25f);

            lineRendererLoseHorizzontal.setColor(Color.GREY);
            lineRendererLoseVertical.setColor(Color.GREY);

            animator.clear();
            animator.add(WaitAnimation.build(0.4f), () -> movingPlatformSound.play(1));
            animator.add(MoveToAnimation.build(bridge1, -9, bridge1.y, 0.35f));
            animator.add(WaitAnimation.build(0.2f), () -> {
                fallSound.play(1);
                characterRenderer.setSrcPosition(128, 128);
                character.angle = 90;
            });
            animator.add(MoveToAnimation.build(character, character.x, -20, 0.25f));
            animator.add(FadeAnimation.build(fullScreenRenderer, Color.TRANSPARENT, Color.BLACK, 0.75f), () -> loadScene(Level3.class));
            animator.start();
        });

        pressurePlateWin.setOnCollisionEnter(() -> {
            if (isPressedWin)
                return;

            menuButton.setInteractable(false);

            isPressedWin = true;

            buttonSound.play(1);
            pressurePlateWinRenderer.color = Color.GREEN;
            pressurePlateWinRigidBody.setTransform(pressurePlateWinGO.x, pressurePlateWinGO.y - 0.25f);

            lineRendererWinVertical.setColor(Color.GREY);

            animator.clear();
            animator.add(ParallelAnimation.build(
                    WaitAnimation.build(0.4f),
                    MoveToAnimation.build(menuButtonGO, menuButtonGO.x - 20, menuButtonGO.y, 0.25f)
            ), () -> movingPlatformSound.play(1));
            animator.add(MoveToAnimation.build(bridgeCharacter, 3.8f, bridgeCharacter.y, 0.35f));
            animator.add(WaitAnimation.build(0.2f), () -> {
                characterRenderer.setSrcPosition(128, 128);
                winSound.play(1);
            });
            animator.add(MoveToAnimation.build(character, 6, character.y, 0.3f));
            animator.add(FadeAnimation.build(fullScreenRenderer, Color.TRANSPARENT, Color.BLACK, 0.75f), () -> loadScene(Level4.class));
            animator.start();
        });

        //Piattaforma sotto la pedana 1
        float plat3W = 8;
        float plat3H = 0.5f;
        GameObject platform3 = createGameObject(3.5f, 0.5f);
        PlatformRenderComponent platformRenderComponent3 = platform3.addComponent(PlatformRenderComponent.class);
        platformRenderComponent3.color = PALETTE_PRIMARY;
        platformRenderComponent3.setSize(plat3W, plat3H);
        RigidBody rigidPlatform3 = platform3.addComponent(RigidBody.class);
        rigidPlatform3.setType(RigidBody.Type.STATIC);
        rigidPlatform3.addCollider(BoxCollider.build(plat3W, plat3H));

        //Piattaforma di divisione pedane
        float plat4W = 4;
        float plat4H = 0.7f;
        GameObject platform4 = createGameObject(3.5f, 2.5f, 90);

        PlatformRenderComponent platformRenderComponent4 = platform4.addComponent(PlatformRenderComponent.class);
        platformRenderComponent4.color = PALETTE_PRIMARY;
        platformRenderComponent4.setSize(plat4W, plat4H);
        RigidBody rigidPlatform4 = platform4.addComponent(RigidBody.class);
        rigidPlatform4.setType(RigidBody.Type.STATIC);
        rigidPlatform4.addCollider(BoxCollider.build(plat4W, plat4H));

    }

    @Override
    public void pause() {
        super.pause();
        backgroundMusic.pause();
    }

    @Override
    public void resume() {
        super.resume();
        backgroundMusic.play();
    }

    @Override
    public void dispose() {
        super.dispose();

        backgroundImage.dispose();
        elementsImage.dispose();
        uiSpriteSheet.dispose();

        buttonSound.dispose();
        movingPlatformSound.dispose();
        winSound.dispose();
        fallSound.dispose();

        backgroundMusic.dispose();
    }

}
