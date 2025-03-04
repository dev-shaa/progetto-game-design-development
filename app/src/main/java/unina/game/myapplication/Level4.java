package unina.game.myapplication;

import android.util.Log;

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
import unina.game.myapplication.core.animations.WaitAnimation;
import unina.game.myapplication.core.physics.BoxCollider;
import unina.game.myapplication.core.physics.CircleCollider;
import unina.game.myapplication.core.physics.CursorJoint;
import unina.game.myapplication.core.physics.DistanceJoint;
import unina.game.myapplication.core.physics.RigidBody;
import unina.game.myapplication.core.rendering.SpriteRenderer;
import unina.game.myapplication.logic.Assets;
import unina.game.myapplication.logic.CursorJointInput;
import unina.game.myapplication.logic.PhysicsButton;
import unina.game.myapplication.logic.PlatformRenderComponent;
import unina.game.myapplication.logic.common.Button;
import unina.game.myapplication.logic.common.CircleRenderer;
import unina.game.myapplication.logic.common.CollisionSoundPlayer;
import unina.game.myapplication.logic.common.DottedLineRenderer;
import unina.game.myapplication.logic.common.FadeAnimation;
import unina.game.myapplication.logic.common.RectRenderer;
import unina.game.myapplication.logic.menu.MainMenu;

public class Level4 extends Scene {

    private static final int PALETTE_BACKGROUND = 0xffF9A900;
    private static final int PALETTE_PRIMARY = 0xffECECE7;

    private Sound buttonSound;
    private Sound buttonsAppearSound;
    private Sound movingPlatformSound;
    private Sound winSound;
    private Sound rockCrushSound;
    private Sound fallSound;

    private Sound movingRock;
    private Music backgroundMusic;

    private Pixmap backgroundImage;
    private Pixmap elementsImage;

    private RectRenderer fullScreenRenderer;
    private AnimationSequence animator;
    private SpriteRenderer characterRenderer;

    public Level4(Game game) {
        super(game);
    }

    public void initialize() {
        super.initialize();

        Pixmap uiSpriteSheet = getImage("graphics/elements-ui.png");

        backgroundImage = game.getGraphics().newPixmap("graphics/background-level4.jpg", Graphics.PixmapFormat.RGB565);
        elementsImage = game.getGraphics().newPixmap("graphics/elements-light.png", Graphics.PixmapFormat.ARGB8888);

        buttonSound = game.getAudio().newSound("sounds/kenney-interface-sounds/click_002.ogg");
        buttonsAppearSound = game.getAudio().newSound("sounds/kenney-ui-sounds/switch4.ogg");
        movingPlatformSound = game.getAudio().newSound("sounds/kenney-interface-sounds/error_001.ogg"); // FIXME: placeholder
        winSound = game.getAudio().newSound("sounds/kenney-sax-jingles/jingles_SAX10.ogg");
        fallSound = game.getAudio().newSound("sounds/fall.mp3");
        movingRock = game.getAudio().newSound("sounds/moving-rock.mp3");
        rockCrushSound = game.getAudio().newSound("sounds/rock-crush.mp3");

        backgroundMusic = getMusic(Assets.SOUND_MUSIC_LEVELS);
        backgroundMusic.setLooping(true);
        if (MUSIC_ON)
            backgroundMusic.setVolume(0.5f);
        else
            backgroundMusic.setVolume(0);

        Camera.getInstance().setSize(30);


        //Background
        setClearColor(PALETTE_BACKGROUND);

        GameObject backgroundGO = createGameObject();
        SpriteRenderer backgroundRenderer = backgroundGO.addComponent(SpriteRenderer.class);
        backgroundRenderer.setImage(backgroundImage);
        backgroundRenderer.setSize(30, 30 / backgroundImage.getAspectRatio());
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

        float platformW = 8;
        float platformH = 2;
        GameObject platformCharacter = createGameObject(-8,0);

        RigidBody platformCharacterRigidBody = platformCharacter.addComponent(RigidBody.class);
        platformCharacterRigidBody.setType(RigidBody.Type.STATIC);
        platformCharacterRigidBody.setCollider(BoxCollider.build(platformW,platformH));

        if (DEBUG) {
            PlatformRenderComponent platformCharacterRenderComponent = platformCharacter.addComponent(PlatformRenderComponent.class);
            platformCharacterRenderComponent.setSize(platformW,platformH);
            platformCharacterRenderComponent.color = Color.MAGENTA;
            platformCharacterRenderComponent.setLayer(64);
        }

        GameObject platformExit = createGameObject(8,0);

        RigidBody platformExitRigidBody = platformExit.addComponent(RigidBody.class);
        platformExitRigidBody.setType(RigidBody.Type.STATIC);
        platformExitRigidBody.setCollider(BoxCollider.build(platformW,platformH));

        if (DEBUG) {
            PlatformRenderComponent platformExitRenderComponent = platformExit.addComponent(PlatformRenderComponent.class);
            platformExitRenderComponent.setSize(platformW,platformH);
            platformExitRenderComponent.color = Color.MAGENTA;
            platformExitRenderComponent.setLayer(64);
        }

        //Ponte Masso up
        float bridgeW = 8;
        float bridgeH = 1;
        GameObject bridgeRockUp = createGameObject(-8,17);

        SpriteRenderer bridgeRockUpRenderComponent = bridgeRockUp.addComponent(SpriteRenderer.class);
        bridgeRockUpRenderComponent.setImage(elementsImage);
        bridgeRockUpRenderComponent.setSrcPosition(128,48);
        bridgeRockUpRenderComponent.setSrcSize(128,32);
        bridgeRockUpRenderComponent.setSize(bridgeW,bridgeH);
        bridgeRockUpRenderComponent.setLayer(64);

        RigidBody bridgeRockUpRigidBody = bridgeRockUp.addComponent(RigidBody.class);
        bridgeRockUpRigidBody.setType(RigidBody.Type.KINEMATIC);
        bridgeRockUpRigidBody.setCollider(BoxCollider.build(bridgeW,bridgeH));

        //Ponte Masso down
        GameObject bridgeRockDown = createGameObject(0,15.5f);

        SpriteRenderer bridgeRockDownRenderComponent = bridgeRockDown.addComponent(SpriteRenderer.class);
        bridgeRockDownRenderComponent.setImage(elementsImage);
        bridgeRockDownRenderComponent.setSrcPosition(128,48);
        bridgeRockDownRenderComponent.setSrcSize(128,32);
        bridgeRockDownRenderComponent.setSize(bridgeW,bridgeH);
        bridgeRockDownRenderComponent.setLayer(64);

        RigidBody bridgeRockDownRigidBody = bridgeRockDown.addComponent(RigidBody.class);
        bridgeRockDownRigidBody.setType(RigidBody.Type.KINEMATIC);
        bridgeRockDownRigidBody.setCollider(BoxCollider.build(bridgeW,bridgeH));

        //Masso
        float rockRadius = 2;
        GameObject rock = createGameObject(-8,18);

        SpriteRenderer rockRenderComponent = rock.addComponent(SpriteRenderer.class);
        rockRenderComponent.setImage(elementsImage);
        rockRenderComponent.setSrcPosition(256,0);
        rockRenderComponent.setSrcSize(128,128);
        rockRenderComponent.setSize(4,4);
        rockRenderComponent.setLayer(64);

        RigidBody rockRigidBody = rock.addComponent(RigidBody.class);
        rockRigidBody.setType(RigidBody.Type.DYNAMIC);
        rockRigidBody.addCollider(CircleCollider.build(2,100,0,1,false));

        CollisionSoundPlayer rockCollisionSoundPlayer = rock.addComponent(CollisionSoundPlayer.class);
        rockCollisionSoundPlayer.setSound(movingRock);
        rockCollisionSoundPlayer.setVolume(0.7f);

        //Character
        GameObject character = createGameObject(-8, 1);

        SpriteRenderer characterRenderer = character.addComponent(SpriteRenderer.class);
        characterRenderer.setImage(elementsImage);
        characterRenderer.setSize(3, 3);
        characterRenderer.setSrcPosition(0, 128);
        characterRenderer.setSrcSize(128, 128);
        characterRenderer.setPivot(0.5f, 1);
        characterRenderer.setLayer(-2);

        GameObject gameOverTriggerGO = createGameObject(-8, 1);

        if (DEBUG) {
            // Visualize the trigger in debug mode
            RectRenderer debugTriggerRenderer = gameOverTriggerGO.addComponent(RectRenderer.class);
            debugTriggerRenderer.setColor(Color.RED);
            debugTriggerRenderer.setLayer(-16);
            debugTriggerRenderer.setSize(4, 1);
        }

        RigidBody gameOverTriggerRigidBody = gameOverTriggerGO.addComponent(RigidBody.class);
        gameOverTriggerRigidBody.setType(RigidBody.Type.STATIC);
        gameOverTriggerRigidBody.addCollider(BoxCollider.build(4, 1, true));

        PhysicsButton gameOverTrigger = gameOverTriggerGO.addComponent(PhysicsButton.class);
        gameOverTrigger.setOnCollisionEnter(() -> {
            rockCrushSound.play(1);
            characterRenderer.setSize(6,0.5f);

            animator.clear();
            animator.add(WaitAnimation.build(2));
            animator.add(FadeAnimation.build(fullScreenRenderer, Color.TRANSPARENT, Color.BLACK, 0.5f), () -> loadScene(Level4.class));
            animator.start();
        });

        //Pulsante ponte down
        CircleRenderer buttonBridgeDownCircleRender = createGameObject(8,13).addComponent(CircleRenderer.class);
        buttonBridgeDownCircleRender.setColor(PALETTE_PRIMARY);
        buttonBridgeDownCircleRender.setRadius(0.85f);

        GameObject buttonBridgeDown = createGameObject(8,13);

        SpriteRenderer buttonBridgeDownRenderComponent = buttonBridgeDown.addComponent(SpriteRenderer.class);
        buttonBridgeDownRenderComponent.setImage(elementsImage);
        buttonBridgeDownRenderComponent.setSrcPosition(0,0);
        buttonBridgeDownRenderComponent.setSrcSize(128,128);
        buttonBridgeDownRenderComponent.setSize(3,3);
        buttonBridgeDownRenderComponent.setLayer(0);

        Button buttonBridgeDownInputComponent = buttonBridgeDown.addComponent(Button.class);
        buttonBridgeDownInputComponent.setSize(3,3);

        //Linea BridgeDown
        GameObject lineBridgeDownGO = createGameObject();
        DottedLineRenderer lineBridgeDown = lineBridgeDownGO.addComponent(DottedLineRenderer.class);
        lineBridgeDown.setColor(PALETTE_PRIMARY);
        lineBridgeDown.setPointA(buttonBridgeDown.x - 2, buttonBridgeDown.y);
        lineBridgeDown.setPointB(bridgeRockDown.x,buttonBridgeDown.y);
        lineBridgeDown.setRadius(0.25f);
        lineBridgeDown.setCount(8);
        lineBridgeDown.setLayer(-4);

        buttonBridgeDownInputComponent.setOnClick(() -> {
            buttonBridgeDownInputComponent.setInteractable(false);
            buttonSound.play(1);
            buttonBridgeDownCircleRender.setColor(Color.GREY);
            lineBridgeDown.setColor(Color.GREY);

            animator.clear();
            animator.add(WaitAnimation.build(0.4f), () -> movingPlatformSound.play(1));
            animator.add(MoveRigidBodyTo.build(bridgeRockDownRigidBody,-8,bridgeRockDown.y,0.4f, EaseFunction.CUBIC_IN_OUT));
            animator.start();
        });

        //Ponte palla demolitrice WreckingBall
        float bridgeBallW = 8;
        float bridgeBallH = 1;
        GameObject bridgeWreckingBall = createGameObject(-4,-15,90);

        SpriteRenderer bridgeWreckingBallRenderComponent = bridgeWreckingBall.addComponent(SpriteRenderer.class);
        bridgeWreckingBallRenderComponent.setImage(elementsImage);
        bridgeWreckingBallRenderComponent.setSrcPosition(128,48);
        bridgeWreckingBallRenderComponent.setSrcSize(128,32);
        bridgeWreckingBallRenderComponent.setSize(bridgeBallW,bridgeBallH);
        bridgeWreckingBallRenderComponent.setLayer(-4);

        RigidBody bridgeWreckingBallRigidBody = bridgeWreckingBall.addComponent(RigidBody.class);
        bridgeWreckingBallRigidBody.setType(RigidBody.Type.KINEMATIC);
        bridgeWreckingBallRigidBody.setCollider(BoxCollider.build(bridgeBallW,bridgeBallH));

        //Pulsante ponte Palla
        CircleRenderer buttonBallCircleRender = createGameObject(8,-13).addComponent(CircleRenderer.class);
        buttonBallCircleRender.setColor(PALETTE_PRIMARY);
        buttonBallCircleRender.setRadius(0.85f);

        GameObject buttonBall = createGameObject(8,-13);

        SpriteRenderer buttonBallRenderComponent = buttonBall.addComponent(SpriteRenderer.class);
        buttonBallRenderComponent.setImage(elementsImage);
        buttonBallRenderComponent.setSrcPosition(0,0);
        buttonBallRenderComponent.setSrcSize(128,128);
        buttonBallRenderComponent.setSize(3,3);
        buttonBallRenderComponent.setLayer(0);

        Button buttonBallInputComponent = buttonBall.addComponent(Button.class);
        buttonBallInputComponent.setSize(3,3);

        //Linea Button Down
        GameObject lineButtonDownGO = createGameObject();
        DottedLineRenderer lineButtonDown = lineButtonDownGO.addComponent(DottedLineRenderer.class);
        lineButtonDown.setColor(PALETTE_PRIMARY);
        lineButtonDown.setPointA(buttonBall.x, buttonBall.y + 2.5f);
        lineButtonDown.setPointB(bridgeWreckingBall.x + 1.5f,buttonBall.y + 2.5f);
        lineButtonDown.setRadius(0.25f);
        lineButtonDown.setCount(10);
        lineButtonDown.setLayer(-4);

        buttonBallInputComponent.setOnClick(() -> {
            buttonSound.play(1);
            buttonBallCircleRender.setColor(Color.GREY);
            lineButtonDown.setColor(Color.GREY);
            buttonBridgeDownInputComponent.setInteractable(false);

            animator.clear();
            animator.add(WaitAnimation.build(0.4f),() -> movingPlatformSound.play(1));
            animator.add(MoveRigidBodyTo.build(bridgeWreckingBallRigidBody,bridgeWreckingBall.x,-23,0.4f));
            animator.add(WaitAnimation.build(0.4f),() -> movingPlatformSound.play(1));
            animator.add(MoveRigidBodyTo.build(bridgeRockUpRigidBody,0,bridgeRockUp.y,0.4f));
            animator.start();
        });

        //Ponte Personaggio
        GameObject bridgeCharacter = createGameObject(7,0.3f);

        SpriteRenderer bridgeCharacterRenderComponent = bridgeCharacter.addComponent(SpriteRenderer.class);
        bridgeCharacterRenderComponent.setImage(elementsImage);
        bridgeCharacterRenderComponent.setSrcPosition(128,48);
        bridgeCharacterRenderComponent.setSrcSize(128,32);
        bridgeCharacterRenderComponent.setSize(bridgeW,bridgeH);
        bridgeCharacterRenderComponent.setLayer(3);

        RigidBody bridgeCharacterRigidBody = bridgeCharacter.addComponent(RigidBody.class);
        bridgeCharacterRigidBody.setType(RigidBody.Type.KINEMATIC);
        bridgeCharacterRigidBody.addCollider(BoxCollider.build(bridgeW,bridgeH));

        //Palla demolitrice
        GameObject wreckingBall = createGameObject(-8,-15);

        CircleRenderer wreckingBallRenderComponent = wreckingBall.addComponent(CircleRenderer.class);
        wreckingBallRenderComponent.setColor(PALETTE_PRIMARY);
        wreckingBallRenderComponent.setRadius(2);
        wreckingBallRenderComponent.setLayer(3);

        RigidBody wreckingBallRigidBody = wreckingBall.addComponent(RigidBody.class);
        wreckingBallRigidBody.setType(RigidBody.Type.DYNAMIC);
        wreckingBallRigidBody.addCollider(CircleCollider.build(2,20,0,1,false));

        //Distance Joint
        DistanceJoint distanceJoint = DistanceJoint.build(platformCharacterRigidBody,15,10,1);
        wreckingBallRigidBody.addJoint(distanceJoint);

        //Cursor Joint
        CursorJoint cursorJoint = CursorJoint.build();
        wreckingBallRigidBody.addJoint(cursorJoint);

        CursorJointInput cursorJointInput = wreckingBall.addComponent(CursorJointInput.class);
        cursorJointInput.setJoint(cursorJoint);
        cursorJointInput.setSize(3,3);
        cursorJointInput.setMaxForce(9000);

        //Base
        GameObject platformBase = createGameObject(0,-25);

        if (DEBUG) {
            PlatformRenderComponent platformBaseRenderComponent = platformBase.addComponent(PlatformRenderComponent.class);
            platformBaseRenderComponent.setSize(7,1);
            platformBaseRenderComponent.color = Color.MAGENTA;
            platformBaseRenderComponent.setLayer(3);
        }

        RigidBody platformBaseRigidBody = platformBase.addComponent(RigidBody.class);
        platformBaseRigidBody.setType(RigidBody.Type.STATIC);
        platformBaseRigidBody.addCollider(BoxCollider.build(7,1));

        //Wall
        GameObject dynamicWall = createGameObject(0,-9,90);

        PlatformRenderComponent dynamicWallRenderComponent = dynamicWall.addComponent(PlatformRenderComponent.class);
        dynamicWallRenderComponent.setSize(30,1.5f);
        dynamicWallRenderComponent.color = PALETTE_PRIMARY;
        dynamicWallRenderComponent.setLayer(3);

        RigidBody dynamicWallRigidBody = dynamicWall.addComponent(RigidBody.class);
        dynamicWallRigidBody.setType(RigidBody.Type.DYNAMIC);
        dynamicWallRigidBody.addCollider(BoxCollider.build(30,1.5f,10,0,1,false));

        //Sensor
        GameObject wallSensorGO = createGameObject(8,-28);

        if (DEBUG) {
            PlatformRenderComponent wallSensorRenderComponent = wallSensorGO.addComponent(PlatformRenderComponent.class);
            wallSensorRenderComponent.setSize(15,2);
            wallSensorRenderComponent.color = Color.MAGENTA;
            wallSensorRenderComponent.setLayer(-3);
        }

        RigidBody wallSensorRigidBody = wallSensorGO.addComponent(RigidBody.class);
        wallSensorRigidBody.setType(RigidBody.Type.STATIC);
        wallSensorRigidBody.addCollider(BoxCollider.build(15,2,true));

        PhysicsButton wallSensor = wallSensorGO.addComponent(PhysicsButton.class);
        wallSensor.setOnCollisionEnter(() -> {
            wallSensorRigidBody.setSleepingAllowed(true);
            gameOverTriggerRigidBody.setSleepingAllowed(true);

            animator.clear();
            animator.add(WaitAnimation.build(0.4f), () -> movingPlatformSound.play(1));
            animator.add(MoveRigidBodyTo.build(bridgeCharacterRigidBody,0,0.3f,0.4f));
            animator.add(WaitAnimation.build(0.4f), () -> {
                characterRenderer.setSrcPosition(128,128);
                winSound.play(1);
            });
            animator.add(MoveToAnimation.build(character, 8, 1, 0.4f));
            animator.add(FadeAnimation.build(fullScreenRenderer, Color.TRANSPARENT, Color.BLACK, 0.75f), () -> loadScene(MainMenu.class));
            animator.start();
        });

        // Level selection button
        float levelSelectionButtonWidth = 6f;
        float levelSelectionButtonHeight = 6f;

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

        //Music Button
        GameObject musicButtonGO = createGameObject(10,29);

        SpriteRenderer musicButtonRender = musicButtonGO.addComponent(SpriteRenderer.class);
        if (MUSIC_ON)
            musicButtonRender.setImage(getImage("graphics/nota-musicale.png"));
        else
            musicButtonRender.setImage(getImage("graphics/nota-musicale-barra.png"));
        musicButtonRender.setSize(6,5);
        musicButtonRender.setLayer(100);

        Button musicButton = musicButtonGO.addComponent(Button.class);
        musicButton.setSize(6,5);
        musicButton.setOnClick(() -> {
            if (MUSIC_ON) {
                musicButtonRender.setImage(getImage("graphics/nota-musicale-barra.png"));
                backgroundMusic.setVolume(0.5f);
                MUSIC_ON = false;
            }
            else {
                musicButtonRender.setImage(getImage("graphics/nota-musicale.png"));
                backgroundMusic.setVolume(0);
                MUSIC_ON = true;
            }
        });
    }
}
