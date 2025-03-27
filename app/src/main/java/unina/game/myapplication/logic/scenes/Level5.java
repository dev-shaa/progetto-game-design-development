package unina.game.myapplication.logic.scenes;

import com.badlogic.androidgames.framework.Color;
import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Pixmap;
import com.badlogic.androidgames.framework.Sound;

import unina.game.myapplication.core.Camera;
import unina.game.myapplication.core.GameObject;
import unina.game.myapplication.core.Utility;
import unina.game.myapplication.core.animations.AnimationSequence;
import unina.game.myapplication.core.animations.EaseFunction;
import unina.game.myapplication.core.animations.ParallelAnimation;
import unina.game.myapplication.core.physics.BoxCollider;
import unina.game.myapplication.core.physics.CursorJoint;
import unina.game.myapplication.core.physics.ParticleSystem;
import unina.game.myapplication.core.physics.PrismaticJoint;
import unina.game.myapplication.core.physics.RigidBody;
import unina.game.myapplication.core.rendering.ParticleSystemRenderer;
import unina.game.myapplication.core.rendering.SpriteRenderer;
import unina.game.myapplication.logic.common.Assets;
import unina.game.myapplication.logic.common.PressurePlate;
import unina.game.myapplication.logic.common.animations.ColorAnimation;
import unina.game.myapplication.logic.common.animations.MoveRigidBodyTo;
import unina.game.myapplication.logic.common.animations.MoveToAnimation;
import unina.game.myapplication.logic.common.animations.WaitAnimation;
import unina.game.myapplication.logic.common.inputs.CursorJointInput;
import unina.game.myapplication.logic.common.renderers.DottedLineRenderer;
import unina.game.myapplication.logic.common.renderers.DraggablePlatformLineRenderer;
import unina.game.myapplication.core.rendering.RectRenderer;

public class Level5 extends Level {

    private static final int PALETTE_BACKGROUND = 0xffF9A900;
    private static final int PALETTE_PRIMARY = 0xff2B2B2C;

    public Level5(Game game) {
        super(game);
    }

    @Override
    protected void onInitialize() {
        Camera.getInstance().setSize(20);

        setClearColor(PALETTE_BACKGROUND);

        Pixmap spriteSheet = getImage(Assets.GRAPHICS_GAME_SPRITES_DARK);
        Sound movingPlatformSound = getSound(Assets.SOUND_GAME_PLATFORM_MOVE);
        Sound winSound = getSound(Assets.SOUND_GAME_WIN);
        Sound drowningSound = getSound(Assets.SOUND_GAME_DROWNING);

        // Background
        SpriteRenderer backgroundRenderer = createGameObject().addComponent(SpriteRenderer.class);
        backgroundRenderer.setImage(getImage(Assets.GRAPHICS_BACKGROUND_LEVEL_3));
        backgroundRenderer.setSize(Camera.getInstance().getSizeX(), Camera.getInstance().getSizeY());
        backgroundRenderer.setLayer(128);

        // Transition Panel
        RectRenderer fullScreenRenderer = createGameObject().addComponent(RectRenderer.class);
        fullScreenRenderer.setSize(50, 50);
        fullScreenRenderer.setLayer(Integer.MAX_VALUE);
        fullScreenRenderer.setColor(Color.TRANSPARENT);

        // Water
        GameObject waterPlatform = createGameObject();

        ParticleSystem waterPlatformParticleSystem = waterPlatform.addComponent(ParticleSystem.class);
        waterPlatformParticleSystem.setRadius(0.3f);
        waterPlatformParticleSystem.addParticlesGroup(-5f, 12, 3, 4, ParticleSystem.FLAG_GROUP_SOLID, ParticleSystem.FLAG_PARTICLE_WATER);
//        waterPlatformParticleSystem.addParticlesGroup(-6.25f, -14, 5, 0.25f, ParticleSystem.FLAG_GROUP_SOLID, ParticleSystem.FLAG_PARTICLE_WATER);

        ParticleSystemRenderer waterPlatformRenderer = waterPlatform.addComponent(ParticleSystemRenderer.class);
        waterPlatformRenderer.setParticleSystem(waterPlatformParticleSystem);
        waterPlatformRenderer.setColor(0xffECECE7);

        // Water container
        GameObject waterContainerGo = createGameObject(-7f, 10f, -45);
        RigidBody waterContainerRigidBody = waterContainerGo.addComponent(RigidBody.class);
        waterContainerRigidBody.setType(RigidBody.Type.STATIC);
        waterContainerRigidBody.addCollider(BoxCollider.build(6, 0.5f));
        waterContainerRigidBody.addCollider(BoxCollider.build(5, 0.5f)).setCenter(0f, 5.5f);
        waterContainerRigidBody.addCollider(BoxCollider.build(0.5f, 5f)).setCenter(-2.5f, 2.75f);

        // Puzzle
        createPlatform(-5 + 2.25f, 10.25f, -2.5f + 2.25f, 12.5f + 0.25f, 45, 6f, 0.5f, 2, 2);
        createPlatform(-2f + 2, 7f, 0.5f + 2, 9.5f, 45, 7f, 0.5f, 2, 2);

        createPlatform(2f, 10.5f, -2f, 14.5f, -45, 6, 0.5f, 3, 3);
        createPlatform(0f, 3f, -4.5f, 7.5f, -45, 3.7f, 0.5f, 3, 3);

        // Funnel
        GameObject funnelGO = createGameObject(3f, -6f);

        RigidBody funnelRigidBody = funnelGO.addComponent(RigidBody.class);
        funnelRigidBody.setType(RigidBody.Type.STATIC);
        BoxCollider ballDeflectorLeftCollider = funnelRigidBody.addCollider(BoxCollider.build(10f, 0.5f));
        BoxCollider ballDeflectorRightCollider = funnelRigidBody.addCollider(BoxCollider.build(10f, 0.5f));
        ballDeflectorLeftCollider.setCollisionFilter(0x0002, 0x0002);
        ballDeflectorRightCollider.setCollisionFilter(0x0002, 0x0002);
        ballDeflectorLeftCollider.setCenter(-3, 3);
        ballDeflectorLeftCollider.setAngle(-70);
        ballDeflectorRightCollider.setCenter(3, 3);
        ballDeflectorRightCollider.setAngle(70);

        RigidBody outerWalls = createGameObject().addComponent(RigidBody.class);
        outerWalls.setType(RigidBody.Type.STATIC);
        outerWalls.addCollider(BoxCollider.build(1, 50)).setCenter(-9, 0);
        outerWalls.addCollider(BoxCollider.build(1, 50)).setCenter(9, 0);

        // Character
        GameObject character = createGameObject(-6.5f, -15f);

        SpriteRenderer characterRenderer = character.addComponent(SpriteRenderer.class);
        characterRenderer.setImage(spriteSheet);
        characterRenderer.setSize(2, 2);
        characterRenderer.setSrcPosition(0, 128);
        characterRenderer.setSrcSize(128, 128);
        characterRenderer.setPivot(0.5f, 1f);
        characterRenderer.setLayer(-2);

        // Animator
        AnimationSequence animationSequence = createGameObject().addComponent(AnimationSequence.class);
        animationSequence.add(ColorAnimation.build(fullScreenRenderer::setColor, Color.BLACK, Color.TRANSPARENT, 0.5f));
        animationSequence.start();

        // Game Over Detection Mechanism
        GameObject gameOverTriggerGO = createGameObject(-6.25f, -15f);

        RigidBody gameOverTriggerRigidBody = gameOverTriggerGO.addComponent(RigidBody.class);
        gameOverTriggerRigidBody.setType(RigidBody.Type.STATIC);
        gameOverTriggerRigidBody.addCollider(BoxCollider.build(3, 1f, true)).setCollisionFilter(0x0004, 0x0004);

        PressurePlate gameOverTrigger = gameOverTriggerGO.addComponent(PressurePlate.class);
        gameOverTrigger.setOnCollisionEnter(() -> {
            characterRenderer.setSrcPosition(256, 128);
            drowningSound.play(1);

            animationSequence.clear();
            animationSequence.add(WaitAnimation.build(3f));
            animationSequence.add(ColorAnimation.build(fullScreenRenderer::setColor, Color.TRANSPARENT, Color.BLACK, 0.5f), this::reloadLevel);
            animationSequence.start();
            removeGameObject(gameOverTriggerGO);
        });

        RigidBody gameOverPressurePlateAnchor = createGameObject(-6.25f, -13f).addComponent(RigidBody.class);
        gameOverPressurePlateAnchor.setType(RigidBody.Type.STATIC);

        GameObject gameOverPressurePlateGO = createGameObject(gameOverPressurePlateAnchor.getPositionX(), gameOverPressurePlateAnchor.getPositionY());

        RigidBody gameOverPressurePlate = gameOverPressurePlateGO.addComponent(RigidBody.class);
        gameOverPressurePlate.setType(RigidBody.Type.DYNAMIC);
        gameOverPressurePlate.addCollider(BoxCollider.build(3.5f, 0.2f, 0.05f, 0, 0, false));

        PrismaticJoint gameOverPressurePlateJoint = gameOverPressurePlate.addJoint(PrismaticJoint.build(gameOverPressurePlateAnchor, 0, -1));
        gameOverPressurePlateJoint.setEnableLimit(true);
        gameOverPressurePlateJoint.setLowerLimit(-1.8f);
        gameOverPressurePlateJoint.setUpperLimit(0);
        gameOverPressurePlateJoint.setEnableMotor(true);
        gameOverPressurePlateJoint.setMaxMotorForce(15);

        // Floor
        RigidBody floor = createGameObject(0, -16).addComponent(RigidBody.class);
        floor.setType(RigidBody.Type.STATIC);
        floor.addCollider(BoxCollider.build(5, 2f)).setCenter(-6, 0);
        floor.addCollider(BoxCollider.build(7, 2f)).setCenter(5, 0);

        // Character barrier
        GameObject characterWallGO = createGameObject(-4f, -13f, 90);

        RigidBody characterWall = characterWallGO.addComponent(RigidBody.class);
        characterWall.setType(RigidBody.Type.KINEMATIC);
        characterWall.addCollider(BoxCollider.build(3.75f, 1f));

        SpriteRenderer characterWallRenderer = characterWallGO.addComponent(SpriteRenderer.class);
        characterWallRenderer.setImage(spriteSheet);
        characterWallRenderer.setSize(3.75f, 1f);
        characterWallRenderer.setSrcPosition(128, 48);
        characterWallRenderer.setSrcSize(128, 32);

        // Pressure plate
        RigidBody anchor = createGameObject(3, -5).addComponent(RigidBody.class);
        anchor.setType(RigidBody.Type.STATIC);

        GameObject pressurePlateGO = createGameObject(anchor.getPositionX(), anchor.getPositionY());

        RigidBody pressurePlate = pressurePlateGO.addComponent(RigidBody.class);
        pressurePlate.setType(RigidBody.Type.DYNAMIC);
        pressurePlate.addCollider(BoxCollider.build(4, 1f, 0.5f, 0, 0, false))
                .setCollisionFilter(0x0004, 0x0004);

        PrismaticJoint joint = pressurePlate.addJoint(PrismaticJoint.build(anchor, 0, -1));
        joint.setEnableLimit(true);
        joint.setLowerLimit(-2.25f);
        joint.setUpperLimit(0);
        joint.setEnableMotor(true);
        joint.setMaxMotorForce(100);

        SpriteRenderer pressurePlateRenderer = pressurePlateGO.addComponent(SpriteRenderer.class);
        pressurePlateRenderer.setImage(spriteSheet);
        pressurePlateRenderer.setSize(4, 1);
        pressurePlateRenderer.setSrcPosition(256, 418);
        pressurePlateRenderer.setSrcSize(128, 34);

        SpriteRenderer bridgeTriggerRenderer = createGameObject(3f, -8.25f).addComponent(SpriteRenderer.class);
        bridgeTriggerRenderer.setImage(spriteSheet);
        bridgeTriggerRenderer.setPivot(0.5f, 1f);
        bridgeTriggerRenderer.setSize(4, 4);
        bridgeTriggerRenderer.setSrcSize(128, 128);
        bridgeTriggerRenderer.setSrcPosition(0, 384);

        DottedLineRenderer dottedLineRendererA = createDottedLine(3f, -8.5f, 3f, -14.5f, 8);

        // Bridge
        GameObject bridge = createGameObject(0, -15.1f);

        SpriteRenderer bridgeRenderer = bridge.addComponent(SpriteRenderer.class);
        bridgeRenderer.setImage(spriteSheet);
        bridgeRenderer.setSrcPosition(128, 48);
        bridgeRenderer.setSrcSize(128, 32);
        bridgeRenderer.setSize(6.4f, 1);
        bridgeRenderer.setPivot(0f, 0f);

        GameObject bridgeTriggerGo = createGameObject(3f, -7.75f);

        RigidBody bridgeTriggerRigidBody = bridgeTriggerGo.addComponent(RigidBody.class);
        bridgeTriggerRigidBody.setType(RigidBody.Type.STATIC);
        bridgeTriggerRigidBody.addCollider(BoxCollider.build(4, 0.25f, true));

        PressurePlate bridgeTrigger = bridgeTriggerGo.addComponent(PressurePlate.class);
        bridgeTrigger.setOnCollisionEnter(() -> {
            saveProgress();
            setUIButtonsInteractable(false);

            dottedLineRendererA.setColor(PALETTE_PRIMARY);
            bridgeTriggerRenderer.setSrcPosition(128, 384);

            removeGameObject(gameOverTriggerGO);
            removeGameObject(bridgeTriggerGo);

            animationSequence.clear();
            animationSequence.add(WaitAnimation.build(0.8f), () -> movingPlatformSound.play(1));
            animationSequence.add(ParallelAnimation.build(
                    MoveToAnimation.build(bridge, -3.5f, bridge.y, 0.25f, EaseFunction.CUBIC_IN_OUT),
                    MoveRigidBodyTo.build(characterWall, characterWall.getPositionX(), characterWall.getPositionY() - 5, 0.25f, EaseFunction.CUBIC_IN_OUT)
            ));
            animationSequence.add(WaitAnimation.build(0.2f), () -> {
                characterRenderer.setSrcPosition(128, 128);
                winSound.play(1);
            });
            animationSequence.add(MoveToAnimation.build(character, 10f, character.y, 1f, EaseFunction.CUBIC_IN_OUT));
            animationSequence.add(ColorAnimation.build(fullScreenRenderer::setColor, Color.TRANSPARENT, Color.BLACK, 0.75f), this::loadNextLevel);
            animationSequence.start();
        });


    }

    @Override
    protected int getLevelIndex() {
        return 3;
    }

    private void createPlatform(float startX, float startY, float endX, float endY, float angle, float width, float height, float inputSizeX, float inputSizeY) {
        float centerX = startX + (endX - startX) / 2;
        float centerY = startY + (endY - startY) / 2;

        GameObject anchor = createGameObject(centerX, centerY);

        RigidBody anchorRigidBody = anchor.addComponent(RigidBody.class);
        anchorRigidBody.setType(RigidBody.Type.STATIC);

        GameObject platform = createGameObject(startX, startY, angle);

//        RectRenderer renderer = platform.addComponent(RectRenderer.class);
//        renderer.setSize(width, height);
        SpriteRenderer rightDraggablePlatformRenderer = platform.addComponent(SpriteRenderer.class);
        rightDraggablePlatformRenderer.setImage(getImage(Assets.GRAPHICS_GAME_SPRITES_DARK));
        rightDraggablePlatformRenderer.setSrcPosition(256, 384);
        rightDraggablePlatformRenderer.setSrcSize(256, 34);
        rightDraggablePlatformRenderer.setSize(width, height);


        float halfDistance = Utility.distance(startX, startY, endX, endY) / 2;
        float axisX = endX - startX;
        float axisY = endY - startY;

        PrismaticJoint joint = PrismaticJoint.build(anchorRigidBody, axisX, axisY);
        joint.setEnableLimit(true);
        joint.setLowerLimit(-halfDistance);
        joint.setUpperLimit(halfDistance);

        CursorJoint cursorJoint = CursorJoint.build();
        cursorJoint.setTarget(platform.x, platform.y);

        RigidBody rigidBody = platform.addComponent(RigidBody.class);
        rigidBody.setType(RigidBody.Type.DYNAMIC);
        rigidBody.addCollider(BoxCollider.build(width, height));
        rigidBody.addJoint(joint);
        rigidBody.addJoint(cursorJoint);

        CursorJointInput input = platform.addComponent(CursorJointInput.class);
        input.setJoint(cursorJoint);
        input.setSize(inputSizeX, inputSizeY);
        input.setMaxForce(1000000);
        input.setSnap(true);

        DraggablePlatformLineRenderer line = createGameObject().addComponent(DraggablePlatformLineRenderer.class);
        line.setStart(startX, startY);
        line.setEnd(endX, endY);
        line.setRadius(0.25f);
        line.setColor(PALETTE_PRIMARY);
        line.setWidth(0.1f);
        line.setLayer(-2);
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

}
