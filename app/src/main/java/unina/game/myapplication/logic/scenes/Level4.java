package unina.game.myapplication.logic.scenes;

import com.badlogic.androidgames.framework.Color;
import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Pixmap;
import com.badlogic.androidgames.framework.Sound;

import unina.game.myapplication.core.Camera;
import unina.game.myapplication.core.GameObject;
import unina.game.myapplication.core.animations.AnimationSequence;
import unina.game.myapplication.core.animations.EaseFunction;
import unina.game.myapplication.logic.common.animations.MoveRigidBodyTo;
import unina.game.myapplication.logic.common.animations.MoveToAnimation;
import unina.game.myapplication.core.animations.ParallelAnimation;
import unina.game.myapplication.logic.common.animations.WaitAnimation;
import unina.game.myapplication.core.physics.BoxCollider;
import unina.game.myapplication.core.physics.CircleCollider;
import unina.game.myapplication.core.physics.CursorJoint;
import unina.game.myapplication.core.physics.DistanceJoint;
import unina.game.myapplication.core.physics.RigidBody;
import unina.game.myapplication.core.rendering.SpriteRenderer;
import unina.game.myapplication.logic.common.Assets;
import unina.game.myapplication.logic.common.inputs.CursorJointInput;
import unina.game.myapplication.logic.common.renderers.LineRenderer;
import unina.game.myapplication.logic.common.PressurePlate;
import unina.game.myapplication.logic.common.inputs.Button;
import unina.game.myapplication.core.rendering.CircleRenderer;
import unina.game.myapplication.logic.common.CollisionSoundPlayer;
import unina.game.myapplication.logic.common.animations.ColorAnimation;
import unina.game.myapplication.logic.common.renderers.DottedLineRenderer;
import unina.game.myapplication.core.rendering.RectRenderer;

public class Level4 extends Level {

    private static final int PALETTE_BACKGROUND = 0xff005387;
    private static final int PALETTE_PRIMARY = 0xffECECE7;

    public Level4(Game game) {
        super(game);
    }

    public void initialize() {
        super.initialize();

        Pixmap backgroundImage = getImage("graphics/environment-bus-stop.png");
        Pixmap elementsImage = getImage("graphics/elements-light.png");

        Sound buttonSound = getSound("sounds/kenney-interface-sounds/click_002.ogg");
        Sound movingPlatformSound = getSound("sounds/kenney-interface-sounds/error_001.ogg"); // FIXME: placeholder
        Sound winSound = getSound("sounds/kenney-sax-jingles/jingles_SAX10.ogg");
        Sound movingRock = getSound("sounds/moving-rock.mp3");
        Sound rockCrushSound = getSound("sounds/rock-crush.mp3");

        Camera.getInstance().setSize(30);

        // Background
        setClearColor(PALETTE_BACKGROUND);

        SpriteRenderer backgroundRenderer = createGameObject().addComponent(SpriteRenderer.class);
        backgroundRenderer.setImage(backgroundImage);
        backgroundRenderer.setSize(30, 30 / backgroundImage.getAspectRatio());
        backgroundRenderer.setLayer(64);

        // Transition panel
        RectRenderer fullScreenRenderer = createGameObject().addComponent(RectRenderer.class);
        fullScreenRenderer.setSize(100, 100);
        fullScreenRenderer.setLayer(Integer.MAX_VALUE);
        fullScreenRenderer.setColor(Color.TRANSPARENT);

        // Animator
        AnimationSequence animator = createGameObject().addComponent(AnimationSequence.class);
        animator.add(ColorAnimation.build(fullScreenRenderer::setColor, Color.BLACK, Color.TRANSPARENT, 0.5f));
        animator.start();

        // Character platform
        float platformH = 2;

        RigidBody platformCharacterRigidBody = createGameObject(-8, 0).addComponent(RigidBody.class);
        platformCharacterRigidBody.setType(RigidBody.Type.STATIC);
        platformCharacterRigidBody.addCollider(BoxCollider.build(10, platformH));

        //Exit platform
        RigidBody platformExitRigidBody = createGameObject(12, 0).addComponent(RigidBody.class);
        platformExitRigidBody.setType(RigidBody.Type.STATIC);
        platformExitRigidBody.addCollider(BoxCollider.build(4, platformH));

        //Ponte Masso up
        GameObject bridgeRockUp = createGameObject(-8, 13);

        SpriteRenderer bridgeRockUpRenderComponent = bridgeRockUp.addComponent(SpriteRenderer.class);
        bridgeRockUpRenderComponent.setImage(elementsImage);
        bridgeRockUpRenderComponent.setSrcPosition(128, 0);
        bridgeRockUpRenderComponent.setSrcSize(128, 128);
        bridgeRockUpRenderComponent.setSize(8, 6);
        bridgeRockUpRenderComponent.setLayer(64);

        RigidBody bridgeRockUpRigidBody = bridgeRockUp.addComponent(RigidBody.class);
        bridgeRockUpRigidBody.setType(RigidBody.Type.KINEMATIC);
        bridgeRockUpRigidBody.addCollider(BoxCollider.build(8, 2));

        // Ponte Masso down
        GameObject bridgeRockDown = createGameObject(-2, 11);

        SpriteRenderer bridgeRockDownRenderComponent = bridgeRockDown.addComponent(SpriteRenderer.class);
        bridgeRockDownRenderComponent.setImage(elementsImage);
        bridgeRockDownRenderComponent.setSrcPosition(128, 0);
        bridgeRockDownRenderComponent.setSrcSize(128, 128);
        bridgeRockDownRenderComponent.setSize(8, 6);
        bridgeRockDownRenderComponent.setLayer(64);

        RigidBody bridgeRockDownRigidBody = bridgeRockDown.addComponent(RigidBody.class);
        bridgeRockDownRigidBody.setType(RigidBody.Type.KINEMATIC);
        bridgeRockDownRigidBody.addCollider(BoxCollider.build(8, 2));

        // Rock
        GameObject rock = createGameObject(-8, 18);

        SpriteRenderer rockRenderComponent = rock.addComponent(SpriteRenderer.class);
        rockRenderComponent.setImage(elementsImage);
        rockRenderComponent.setSrcPosition(256, 0);
        rockRenderComponent.setSrcSize(128, 128);
        rockRenderComponent.setSize(4, 4);
        rockRenderComponent.setLayer(64);

        RigidBody rockRigidBody = rock.addComponent(RigidBody.class);
        rockRigidBody.setType(RigidBody.Type.DYNAMIC);
        rockRigidBody.addCollider(CircleCollider.build(2, 100, 0, 1, false));
        rockRigidBody.setSleepingAllowed(false);

        CollisionSoundPlayer rockCollisionSoundPlayer = rock.addComponent(CollisionSoundPlayer.class);
        rockCollisionSoundPlayer.setSound(movingRock);
        rockCollisionSoundPlayer.setVolume(0.7f);

        // Character
        GameObject character = createGameObject(-8, 1);

        SpriteRenderer characterRenderer = character.addComponent(SpriteRenderer.class);
        characterRenderer.setImage(elementsImage);
        characterRenderer.setSize(3, 3);
        characterRenderer.setSrcPosition(0, 128);
        characterRenderer.setSrcSize(128, 128);
        characterRenderer.setPivot(0.5f, 1);
        characterRenderer.setLayer(-2);

        GameObject gameOverTriggerGO = createGameObject(-8, 1);

        RigidBody gameOverTriggerRigidBody = gameOverTriggerGO.addComponent(RigidBody.class);
        gameOverTriggerRigidBody.setType(RigidBody.Type.STATIC);
        gameOverTriggerRigidBody.addCollider(BoxCollider.build(4, 1, true));

        PressurePlate gameOverTrigger = gameOverTriggerGO.addComponent(PressurePlate.class);
        gameOverTrigger.setOnCollisionEnter(() -> {
            rockCrushSound.play(1);
            characterRenderer.setSize(6, 0.5f);

            animator.clear();
            animator.add(WaitAnimation.build(2));
            animator.add(ColorAnimation.build(fullScreenRenderer::setColor, Color.TRANSPARENT, Color.BLACK, 0.5f), () -> loadScene(Level4.class));
            animator.start();
        });

        // Pulsante ponte down
        GameObject buttonBridgeDown = createGameObject(4, 20);

        SpriteRenderer buttonBridgeDownRenderComponent = buttonBridgeDown.addComponent(SpriteRenderer.class);
        buttonBridgeDownRenderComponent.setImage(elementsImage);
        buttonBridgeDownRenderComponent.setSrcPosition(0, 0);
        buttonBridgeDownRenderComponent.setSrcSize(128, 128);
        buttonBridgeDownRenderComponent.setSize(6, 6);
        buttonBridgeDownRenderComponent.setLayer(0);

        Button buttonBridgeDownInputComponent = buttonBridgeDown.addComponent(Button.class);
        buttonBridgeDownInputComponent.setSize(6, 6);

        CircleRenderer buttonBridgeDownCircleRender = createGameObject(buttonBridgeDown.x, buttonBridgeDown.y).addComponent(CircleRenderer.class);
        buttonBridgeDownCircleRender.setColor(PALETTE_PRIMARY);
        buttonBridgeDownCircleRender.setRadius(2f);

        // Linea Bridge Down
        DottedLineRenderer lineBridgeDown = createGameObject().addComponent(DottedLineRenderer.class);
        lineBridgeDown.setColor(Color.GREY);
        lineBridgeDown.setPointA(buttonBridgeDown.x, buttonBridgeDown.y - 3.5f);
        lineBridgeDown.setPointB(buttonBridgeDown.x, bridgeRockDown.y);
        lineBridgeDown.setRadius(0.25f);
        lineBridgeDown.setCount(8);
        lineBridgeDown.setLayer(65);

        DottedLineRenderer lineBridgeDownB = createGameObject().addComponent(DottedLineRenderer.class);
        lineBridgeDownB.setColor(Color.GREY);
        lineBridgeDownB.setPointA(buttonBridgeDown.x, bridgeRockDown.y);
        lineBridgeDownB.setPointB(-3, bridgeRockDown.y);
        lineBridgeDownB.setRadius(0.25f);
        lineBridgeDownB.setCount(8);
        lineBridgeDownB.setLayer(65);

        buttonBridgeDownInputComponent.setOnClick(() -> {
            buttonBridgeDownInputComponent.setInteractable(false);
            buttonSound.play(1);
            buttonBridgeDownCircleRender.setColor(Color.GREY);
            lineBridgeDown.setColor(0xff009fff);
            lineBridgeDownB.setColor(0xff009fff);

            animator.clear();
            animator.add(WaitAnimation.build(0.1f), () -> movingPlatformSound.play(1));
            animator.add(MoveRigidBodyTo.build(bridgeRockDownRigidBody, -8, bridgeRockDown.y, 0.4f, EaseFunction.CUBIC_IN_OUT));
            animator.start();
        });

        //Ponte palla demolitrice WreckingBall
        float bridgeBallW = 8;
        float bridgeBallH = 1;
        GameObject bridgeWreckingBall = createGameObject(-1, -15, 90);

        SpriteRenderer bridgeWreckingBallRenderComponent = bridgeWreckingBall.addComponent(SpriteRenderer.class);
        bridgeWreckingBallRenderComponent.setImage(elementsImage);
        bridgeWreckingBallRenderComponent.setSrcPosition(128, 48);
        bridgeWreckingBallRenderComponent.setSrcSize(128, 32);
        bridgeWreckingBallRenderComponent.setSize(bridgeBallW, bridgeBallH);
        bridgeWreckingBallRenderComponent.setLayer(0);

        RigidBody bridgeWreckingBallRigidBody = bridgeWreckingBall.addComponent(RigidBody.class);
        bridgeWreckingBallRigidBody.setType(RigidBody.Type.KINEMATIC);
        bridgeWreckingBallRigidBody.addCollider(BoxCollider.build(bridgeBallW, bridgeBallH));

        //Pulsante ponte Palla
        CircleRenderer buttonBallCircleRender = createGameObject(-10, -20).addComponent(CircleRenderer.class);
        buttonBallCircleRender.setColor(PALETTE_PRIMARY);
        buttonBallCircleRender.setRadius(0.85f);

        GameObject buttonBall = createGameObject(-10, -20);

        SpriteRenderer buttonBallRenderComponent = buttonBall.addComponent(SpriteRenderer.class);
        buttonBallRenderComponent.setImage(elementsImage);
        buttonBallRenderComponent.setSrcPosition(0, 0);
        buttonBallRenderComponent.setSrcSize(128, 128);
        buttonBallRenderComponent.setSize(3, 3);
        buttonBallRenderComponent.setLayer(0);

        Button buttonBallInputComponent = buttonBall.addComponent(Button.class);
        buttonBallInputComponent.setSize(3, 3);

        //Linea Button Down
        GameObject lineButtonDownGO = createGameObject();
        DottedLineRenderer lineButtonDown = lineButtonDownGO.addComponent(DottedLineRenderer.class);
        lineButtonDown.setColor(PALETTE_PRIMARY);
        lineButtonDown.setPointA(buttonBall.x, buttonBall.y + 2.5f);
        lineButtonDown.setPointB(bridgeWreckingBall.x + 1.5f, buttonBall.y + 2.5f);
        lineButtonDown.setRadius(0.25f);
        lineButtonDown.setCount(10);
        lineButtonDown.setLayer(-4);

        buttonBallInputComponent.setOnClick(() -> {
            buttonSound.play(1);
            buttonBallCircleRender.setColor(Color.GREY);
            lineButtonDown.setColor(0xff009fff);
            buttonBridgeDownInputComponent.setInteractable(false);

            animator.clear();
            animator.add(WaitAnimation.build(0.1f), () -> movingPlatformSound.play(1));
            animator.add(ParallelAnimation.build(
                    MoveRigidBodyTo.build(bridgeWreckingBallRigidBody, bridgeWreckingBall.x, -23, 0.5f),
                    MoveRigidBodyTo.build(bridgeRockUpRigidBody, 0, bridgeRockUp.y, 0.5f)
            ));
            animator.start();
        });

        //Ponte Personaggio
        GameObject bridgeCharacter = createGameObject(7, 0.3f);

        SpriteRenderer bridgeCharacterRenderComponent = bridgeCharacter.addComponent(SpriteRenderer.class);
        bridgeCharacterRenderComponent.setImage(elementsImage);
        bridgeCharacterRenderComponent.setSrcPosition(128, 48);
        bridgeCharacterRenderComponent.setSrcSize(128, 32);
        bridgeCharacterRenderComponent.setSize(8, 1);
        bridgeCharacterRenderComponent.setLayer(3);

        //Pulsante ponte personaggio
        //TODO continuare
        CircleRenderer buttonBridgeCharacterCircleRender = createGameObject(-10, -20).addComponent(CircleRenderer.class);
        buttonBridgeCharacterCircleRender.setColor(PALETTE_PRIMARY);
        buttonBridgeCharacterCircleRender.setRadius(0.85f);

        GameObject buttonBridgeCharacter = createGameObject(-10, -20);

        SpriteRenderer buttonBridgeCharacterRenderComponent = buttonBridgeCharacter.addComponent(SpriteRenderer.class);
        buttonBridgeCharacterRenderComponent.setImage(elementsImage);
        buttonBridgeCharacterRenderComponent.setSrcPosition(0, 0);
        buttonBridgeCharacterRenderComponent.setSrcSize(128, 128);
        buttonBridgeCharacterRenderComponent.setSize(3, 3);
        buttonBridgeCharacterRenderComponent.setLayer(0);

        Button buttonBridgeCharacterInputComponent = buttonBridgeCharacter.addComponent(Button.class);
        buttonBridgeCharacterInputComponent.setSize(3, 3);


        //Anchor Palla
        RigidBody anchorBall = createGameObject(-4,0).addComponent(RigidBody.class);
        anchorBall.setType(RigidBody.Type.STATIC);


        // Wrecking ball
        GameObject wreckingBall = createGameObject(-8, -15);

        CircleRenderer wreckingBallRenderComponent = wreckingBall.addComponent(CircleRenderer.class);
        wreckingBallRenderComponent.setColor(PALETTE_PRIMARY);
        wreckingBallRenderComponent.setRadius(2);
        wreckingBallRenderComponent.setLayer(3);

        RigidBody wreckingBallRigidBody = wreckingBall.addComponent(RigidBody.class);
        wreckingBallRigidBody.setType(RigidBody.Type.DYNAMIC);
        wreckingBallRigidBody.addCollider(CircleCollider.build(2, 5, 0, 1, false));

        DistanceJoint distanceJoint = DistanceJoint.build(anchorBall, 15, 10, 1);
        wreckingBallRigidBody.addJoint(distanceJoint);

        CursorJoint cursorJoint = CursorJoint.build();
        wreckingBallRigidBody.addJoint(cursorJoint);

        CursorJointInput cursorJointInput = wreckingBall.addComponent(CursorJointInput.class);
        cursorJointInput.setJoint(cursorJoint);
        cursorJointInput.setSize(4, 4);
        cursorJointInput.setMaxForce(9000);

        //Wreking ball line
        LineRenderer wreckingBallLineRenderer = createGameObject().addComponent(LineRenderer.class);
        wreckingBallLineRenderer.setA(anchorBall.getOwner());
        wreckingBallLineRenderer.setB(wreckingBall);
        wreckingBallLineRenderer.setColor(Color.WHITE);
        wreckingBallLineRenderer.setWidth(0.2f);

        // Base
        RigidBody platformBaseRigidBody = createGameObject(0, -25).addComponent(RigidBody.class);
        platformBaseRigidBody.setType(RigidBody.Type.STATIC);
        platformBaseRigidBody.addCollider(BoxCollider.build(7, 1));

        // Wall
        float fooW = 3;
        foo(1.5f, -25.5f + 2.5f, fooW, 5);
        foo(1.5f, -25.5f + 2.5f + 5f, fooW, 5);
        foo(1.5f, -25.5f + 2.5f + 5f + 5, fooW, 5);
        foo(1.5f, -25.5f + 2.5f + 5f + 5 + 5, fooW, 5);
        foo(1.5f, -25.5f + 2.5f + 5f + 5 + 5 + 5, fooW, 5);
        foo(1.5f, -25.5f + 2.5f + 5f + 5 + 5 + 5 + 5, fooW, 5);
//        GameObject dynamicWall = createGameObject(0, -9);
//
//        RectRenderer dynamicWallRenderComponent = dynamicWall.addComponent(RectRenderer.class);
//        dynamicWallRenderComponent.setSize(1.5f, 30);
//        dynamicWallRenderComponent.setColor(PALETTE_PRIMARY);
//        dynamicWallRenderComponent.setLayer(3);
//
//        RigidBody dynamicWallRigidBody = dynamicWall.addComponent(RigidBody.class);
//        dynamicWallRigidBody.setType(RigidBody.Type.DYNAMIC);
//        dynamicWallRigidBody.addCollider(BoxCollider.build(1.5f, 30f, 10, 0, 1, false));

        // Sensor
        GameObject wallSensorGO = createGameObject(0, -28);

        RigidBody wallSensorRigidBody = wallSensorGO.addComponent(RigidBody.class);
        wallSensorRigidBody.setType(RigidBody.Type.STATIC);
        wallSensorRigidBody.addCollider(BoxCollider.build(50, 2, true));

        PressurePlate wallSensor = wallSensorGO.addComponent(PressurePlate.class);
        wallSensor.setOnCollisionEnter(() -> {
            saveProgress();
            setUIButtonsInteractable(false);

            animator.clear();
            animator.add(WaitAnimation.build(0.25f), () -> movingPlatformSound.play(1));
            animator.add(MoveToAnimation.build(bridgeCharacter, 0, 0.3f, 0.4f));
            animator.add(WaitAnimation.build(0.4f), () -> {
                characterRenderer.setSrcPosition(128, 128);
                winSound.play(1);
            });
            animator.add(MoveToAnimation.build(character, 8, 1, 0.4f));
            animator.add(ColorAnimation.build(fullScreenRenderer::setColor, Color.TRANSPARENT, Color.BLACK, 0.75f), () -> loadScene(MainMenu.class));
            animator.start();
            removeGameObject(wallSensorGO);
        });
    }

    private void foo(float x, float y, float width, float height) {
        // Wall
        GameObject dynamicWall = createGameObject(x, y);

        RectRenderer dynamicWallRenderComponent = dynamicWall.addComponent(RectRenderer.class);
        dynamicWallRenderComponent.setSize(width, height);
        dynamicWallRenderComponent.setColor(PALETTE_PRIMARY);

        RigidBody dynamicWallRigidBody = dynamicWall.addComponent(RigidBody.class);
        dynamicWallRigidBody.setType(RigidBody.Type.DYNAMIC);
        dynamicWallRigidBody.addCollider(BoxCollider.build(width, height));
    }

    @Override
    protected int getLevelIndex() {
        return 3;
    }

    @Override
    protected String getBackgroundMusic() {
        return Assets.SOUND_MUSIC_LEVELS;
    }

}
