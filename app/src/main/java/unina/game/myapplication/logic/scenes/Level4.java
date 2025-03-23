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

    private boolean isWallDown = false;

    public Level4(Game game) {
        super(game);
    }

    @Override
    protected void onInitialize() {
        Pixmap backgroundImage = getImage(Assets.GRAPHICS_BACKGROUND_LEVEL_4);
        Pixmap elementsImage = getImage(Assets.GRAPHICS_GAME_SPRITES_LIGHT);

        Sound buttonSound = getSound(Assets.SOUND_GAME_BUTTON_CLICK);
        Sound movingPlatformSound = getSound(Assets.SOUND_GAME_PLATFORM_MOVE);
        Sound winSound = getSound(Assets.SOUND_GAME_WIN);
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
        RigidBody platformCharacterRigidBody = createGameObject(-8, 0).addComponent(RigidBody.class);
        platformCharacterRigidBody.setType(RigidBody.Type.STATIC);
        platformCharacterRigidBody.addCollider(BoxCollider.build(10, 2));

        // Exit platform
        RigidBody platformExitRigidBody = createGameObject(10, 0).addComponent(RigidBody.class);
        platformExitRigidBody.setType(RigidBody.Type.STATIC);
        platformExitRigidBody.addCollider(BoxCollider.build(6, 2));

        // Ponte Masso up
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

        // Game over trigger
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
            animator.add(ColorAnimation.build(fullScreenRenderer::setColor, Color.TRANSPARENT, Color.BLACK, 0.5f), this::reloadLevel);
            animator.start();
        });

        // Pulsante ponte down
        GameObject buttonBridgeDown = createGameObject(4, 20);

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

        SpriteRenderer buttonBridgeDownRenderComponent = buttonBridgeDown.addComponent(SpriteRenderer.class);
        buttonBridgeDownRenderComponent.setImage(elementsImage);
        buttonBridgeDownRenderComponent.setSrcPosition(0, 0);
        buttonBridgeDownRenderComponent.setSrcSize(128, 128);
        buttonBridgeDownRenderComponent.setSize(6, 6);
        buttonBridgeDownRenderComponent.setLayer(0);

        Button buttonBridgeDownInputComponent = buttonBridgeDown.addComponent(Button.class);
        buttonBridgeDownInputComponent.setSize(6, 6);
        buttonBridgeDownInputComponent.setOnInteractableChange(interactable -> {
            if (interactable) {
                buttonBridgeDownRenderComponent.setSrcPosition(0, 0);
                lineBridgeDown.setColor(Color.GREY);
                lineBridgeDownB.setColor(Color.GREY);
            } else {
                buttonBridgeDownRenderComponent.setSrcPosition(384, 0);
                lineBridgeDown.setColor(0xff009fff);
                lineBridgeDownB.setColor(0xff009fff);
            }
        });
        buttonBridgeDownInputComponent.setOnClick(() -> {
            buttonBridgeDownInputComponent.setInteractable(false);
            buttonSound.play(1);

            animator.clear();
            animator.add(WaitAnimation.build(0.1f), () -> movingPlatformSound.play(1));
            animator.add(MoveRigidBodyTo.build(bridgeRockDownRigidBody, -8, bridgeRockDown.y, 0.4f, EaseFunction.CUBIC_IN_OUT));
            animator.start();
        });

        // Wrecking ball barrier
        GameObject wreckingBallBarrierGO = createGameObject(-1, -17, 90);

        SpriteRenderer wreckingBallBarrierRenderer = wreckingBallBarrierGO.addComponent(SpriteRenderer.class);
        wreckingBallBarrierRenderer.setImage(elementsImage);
        wreckingBallBarrierRenderer.setSrcPosition(128, 48);
        wreckingBallBarrierRenderer.setSrcSize(128, 32);
        wreckingBallBarrierRenderer.setSize(16, 1);
        wreckingBallBarrierRenderer.setLayer(0);

        RigidBody wreckingBallBarrierRigidBody = wreckingBallBarrierGO.addComponent(RigidBody.class);
        wreckingBallBarrierRigidBody.setType(RigidBody.Type.KINEMATIC);
        wreckingBallBarrierRigidBody.addCollider(BoxCollider.build(16, 1));

        // Pulsante ponte Palla
        GameObject buttonBall = createGameObject(-9, -21);

        DottedLineRenderer lineButtonDown = createGameObject().addComponent(DottedLineRenderer.class);
        lineButtonDown.setColor(Color.GREY);
        lineButtonDown.setPointA(buttonBall.x + 3.5f, buttonBall.y);
        lineButtonDown.setPointB(wreckingBallBarrierGO.x - 1f, buttonBall.y);
        lineButtonDown.setRadius(0.25f);
        lineButtonDown.setCount(4);
        lineButtonDown.setLayer(65);

        DottedLineRenderer lineButtonRock = createGameObject().addComponent(DottedLineRenderer.class);
        lineButtonRock.setColor(Color.GREY);
        lineButtonRock.setPointA(buttonBall.x, buttonBall.y + 3.5f);
        lineButtonRock.setPointB(buttonBall.x, bridgeRockUp.y - 1f);
        lineButtonRock.setRadius(0.25f);
        lineButtonRock.setCount(20);
        lineButtonRock.setLayer(65);

        SpriteRenderer buttonBallRenderComponent = buttonBall.addComponent(SpriteRenderer.class);
        buttonBallRenderComponent.setImage(elementsImage);
        buttonBallRenderComponent.setSrcPosition(0, 0);
        buttonBallRenderComponent.setSrcSize(128, 128);
        buttonBallRenderComponent.setSize(6, 6);
        buttonBallRenderComponent.setLayer(0);

        Button buttonBallInputComponent = buttonBall.addComponent(Button.class);
        buttonBallInputComponent.setSize(6, 6);
        buttonBallInputComponent.setOnInteractableChange(interactable -> {
            if (interactable) {
                buttonBallRenderComponent.setSrcPosition(0, 0);
                lineButtonDown.setColor(PALETTE_PRIMARY);
                lineButtonRock.setColor(PALETTE_PRIMARY);
            } else {
                buttonBallRenderComponent.setSrcPosition(384, 0);
                lineButtonDown.setColor(0xff009fff);
                lineButtonRock.setColor(0xff009fff);
            }
        });
        buttonBallInputComponent.setOnClick(() -> {
            buttonSound.play(1);
            buttonBallInputComponent.setInteractable(false);

            animator.clear();
            animator.add(WaitAnimation.build(0.1f), () -> movingPlatformSound.play(1));
            animator.add(ParallelAnimation.build(
                    MoveRigidBodyTo.build(wreckingBallBarrierRigidBody, wreckingBallBarrierGO.x, -30, 0.5f),
                    MoveRigidBodyTo.build(bridgeRockUpRigidBody, 0, bridgeRockUp.y, 0.5f)
            ));
            animator.start();
        });

        // Bridge
        GameObject bridgeGO = createGameObject(11f, 0.35f);

        SpriteRenderer bridgeRenderer = bridgeGO.addComponent(SpriteRenderer.class);
        bridgeRenderer.setImage(elementsImage);
        bridgeRenderer.setSrcPosition(128, 48);
        bridgeRenderer.setSrcSize(128, 32);
        bridgeRenderer.setSize(10.5f, 1);

        // Bridge button
        GameObject bridgeButtonGO = createGameObject(7.5f, -12);

        DottedLineRenderer lineButtonCharacter = createGameObject().addComponent(DottedLineRenderer.class);
        lineButtonCharacter.setColor(Color.GREY);
        lineButtonCharacter.setPointA(bridgeButtonGO.x, bridgeButtonGO.y + 3f);
        lineButtonCharacter.setPointB(bridgeButtonGO.x, bridgeGO.y - 2.5f);
        lineButtonCharacter.setRadius(0.25f);
        lineButtonCharacter.setCount(8);

        SpriteRenderer bridgeButtonRenderer = bridgeButtonGO.addComponent(SpriteRenderer.class);
        bridgeButtonRenderer.setImage(elementsImage);
        bridgeButtonRenderer.setSrcPosition(0, 0);
        bridgeButtonRenderer.setSrcSize(128, 128);
        bridgeButtonRenderer.setSize(6, 6);

        Button bridgeButton = bridgeButtonGO.addComponent(Button.class);
        bridgeButton.setSize(6, 6);
        bridgeButton.setOnInteractableChange(interactable -> {
            if (interactable) {
                bridgeButtonRenderer.setSrcPosition(0, 0);
                lineButtonCharacter.setColor(Color.GREY);
            } else {
                bridgeButtonRenderer.setSrcPosition(384, 0);
                lineButtonCharacter.setColor(0xff009fff);
            }
        });
        bridgeButton.setOnClick(() -> {
            animator.clear();

            if (isWallDown) {
                saveProgress();
                setUIButtonsInteractable(false);

                movingPlatformSound.play(1);
                animator.add(MoveToAnimation.build(bridgeGO, 2.5f, 0.3f, 0.4f));
                animator.add(WaitAnimation.build(0.4f), () -> {
                    characterRenderer.setSrcPosition(128, 128);
                    winSound.play(1);
                });
                animator.add(MoveToAnimation.build(character, 15, 1, 1f, EaseFunction.CUBIC_IN_OUT));
                animator.add(ColorAnimation.build(fullScreenRenderer::setColor, Color.TRANSPARENT, Color.BLACK, 0.75f), () -> loadScene(MainMenu.class));
                animator.start();
            } else {
                bridgeButton.setInteractable(false);

                animator.add(WaitAnimation.build(0.25f), () -> movingPlatformSound.play(1));
                animator.add(MoveToAnimation.build(bridgeGO, 8.25f, 0.3f, 0.4f), () -> rockCrushSound.play(1));
                animator.add(WaitAnimation.build(0.1f));
                animator.add(MoveToAnimation.build(bridgeGO, bridgeGO.x, 0.3f, 0.4f), () -> bridgeButton.setInteractable(true));
            }

            animator.start();
        });


        // Wrecking ball anchor
        RigidBody anchorBall = createGameObject(-4, 0).addComponent(RigidBody.class);
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
        // NOTE: progressively shrink the width otherwise the stack is unstable and may fall by itself
        float boxWallWidth = 3, boxWallHeight = 5;
        createBox(1.5f, -25.5f + 2.5f, boxWallWidth, boxWallHeight);
        boxWallWidth *= 0.98f;
        createBox(1.5f, -25.5f + 2.5f + 5f, boxWallWidth, boxWallHeight);
        boxWallWidth *= 0.98f;
        createBox(1.5f, -25.5f + 2.5f + 5f + 5, boxWallWidth, boxWallHeight);
        boxWallWidth *= 0.98f;
        createBox(1.5f, -25.5f + 2.5f + 5f + 5 + 5, boxWallWidth, boxWallHeight);
        boxWallWidth *= 0.98f;
        createBox(1.5f, -25.5f + 2.5f + 5f + 5 + 5 + 5, boxWallWidth, boxWallHeight);
        boxWallWidth *= 0.98f;
        createBox(1.5f, -25.5f + 2.5f + 5f + 5 + 5 + 5 + 5, boxWallWidth, boxWallHeight);

        // Sensor
        GameObject wallSensorGO = createGameObject(0, -28);

        RigidBody wallSensorRigidBody = wallSensorGO.addComponent(RigidBody.class);
        wallSensorRigidBody.setType(RigidBody.Type.STATIC);
        wallSensorRigidBody.addCollider(BoxCollider.build(50, 2, true));

        PressurePlate wallSensor = wallSensorGO.addComponent(PressurePlate.class);
        wallSensor.setOnCollisionEnter(() -> {
            isWallDown = true;
            removeGameObject(wallSensorGO);
        });

        // Outer walls
        RigidBody outerWalls = createGameObject().addComponent(RigidBody.class);
        outerWalls.setType(RigidBody.Type.STATIC);
        outerWalls.addCollider(BoxCollider.build(1, 50)).setCenter(-13.5f, 0);
        outerWalls.addCollider(BoxCollider.build(1, 50)).setCenter(13.5f, 0);
    }

    private void createBox(float x, float y, float width, float height) {
        GameObject gameObject = createGameObject(x, y);

        RectRenderer renderer = gameObject.addComponent(RectRenderer.class);
        renderer.setSize(width, height);
        renderer.setColor(PALETTE_PRIMARY);

        RigidBody rigidBody = gameObject.addComponent(RigidBody.class);
        rigidBody.setType(RigidBody.Type.DYNAMIC);
        rigidBody.addCollider(BoxCollider.build(width, height));
    }

    @Override
    protected int getLevelIndex() {
        return 2;
    }

}
