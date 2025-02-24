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
import unina.game.myapplication.core.physics.CursorJoint;
import unina.game.myapplication.core.physics.DistanceJoint;
import unina.game.myapplication.core.physics.RigidBody;
import unina.game.myapplication.core.rendering.SpriteRenderer;
import unina.game.myapplication.logic.ButtonInputComponent;
import unina.game.myapplication.logic.ButtonRenderComponent;
import unina.game.myapplication.logic.CursorJointInput;
import unina.game.myapplication.logic.PhysicsButton;
import unina.game.myapplication.logic.PlatformBehaviourComponent;
import unina.game.myapplication.logic.PlatformRenderComponent;
import unina.game.myapplication.logic.RockRenderComponent;
import unina.game.myapplication.logic.TestingRender;
import unina.game.myapplication.logic.common.FadeAnimation;
import unina.game.myapplication.logic.common.RectRenderer;

public class Level4 extends Scene {

    private static final int PALETTE_BACKGROUND = 0xffF9A900;
    private static final int PALETTE_PRIMARY = 0xff2B2B2C;

    private Sound buttonSound;
    private Sound buttonsAppearSound;
    private Sound movingPlatformSound;
    private Sound winSound;

    private Sound fallSound;

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

        backgroundImage = game.getGraphics().newPixmap("graphics/background-level4.jpg", Graphics.PixmapFormat.RGB565);
        elementsImage = game.getGraphics().newPixmap("graphics/elements-light.png", Graphics.PixmapFormat.ARGB8888);

        buttonSound = game.getAudio().newSound("sounds/kenney-interface-sounds/click_002.ogg");
        buttonsAppearSound = game.getAudio().newSound("sounds/kenney-ui-sounds/switch4.ogg");
        movingPlatformSound = game.getAudio().newSound("sounds/kenney-interface-sounds/error_001.ogg"); // FIXME: placeholder
        winSound = game.getAudio().newSound("sounds/kenney-sax-jingles/jingles_SAX10.ogg");
        fallSound = game.getAudio().newSound("sounds/fall.mp3");

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



//        float platW = 8;
//        float platH = 0.7f;
//        float miniPlatW = 6;
//
//        float phisic1W = 3.5f;
//        float phisic1H = 1;
//
//        float plat2W = 6;
//        float plat2H = 1;
//
//        // Right floor
//        float floorW = 8;
//        float floorH = 10;
//        GameObject rightFloor = createGameObject(8, -25);
//
//        if (DEBUG) {
//            PlatformRenderComponent rightFloorRenderer = rightFloor.addComponent(PlatformRenderComponent.class);
//            rightFloorRenderer.color = Color.MAGENTA;
//            rightFloorRenderer.width = floorW;
//            rightFloorRenderer.height = floorH;
//            rightFloorRenderer.setLayer(64);
//        }
//
//        RigidBody rightFloorRigidBody = rightFloor.addComponent(RigidBody.class);
//        rightFloorRigidBody.setType(RigidBody.Type.STATIC);
//        rightFloorRigidBody.setCollider(BoxCollider.build(floorW, floorH));
//
//        //Bridge Character
//        float bridgeW = 8;
//        float bridgeH = 1;
//
//        GameObject bridgeCharacter = createGameObject(-8, -20.6f);
//
//        SpriteRenderer bridgeCharacterRenderComponent = bridgeCharacter.addComponent(SpriteRenderer.class);
//        bridgeCharacterRenderComponent.setImage(elementsImage);
//        bridgeCharacterRenderComponent.setSrcPosition(128, 48);
//        bridgeCharacterRenderComponent.setSrcSize(128, 32);
//        bridgeCharacterRenderComponent.setSize(bridgeW, bridgeH);
//        bridgeCharacterRenderComponent.setLayer(-3);
//
//        //Sensor bridge
//        GameObject sensorBridgeCharacter = createGameObject(-8, -20.6f);
//        RigidBody SensorBridgeCharacterRigidBody = sensorBridgeCharacter.addComponent(RigidBody.class);
//        SensorBridgeCharacterRigidBody.setType(RigidBody.Type.STATIC);
//        SensorBridgeCharacterRigidBody.setCollider(BoxCollider.build(bridgeW, bridgeH, true));
//        SensorBridgeCharacterRigidBody.setSleepingAllowed(false);
//
//        if (DEBUG) {
//            PlatformRenderComponent sensorBridgeCharacterRenderComponent = sensorBridgeCharacter.addComponent(PlatformRenderComponent.class);
//            sensorBridgeCharacterRenderComponent.color = Color.MAGENTA;
//            sensorBridgeCharacterRenderComponent.width = bridgeW;
//            sensorBridgeCharacterRenderComponent.height = bridgeH;
//            sensorBridgeCharacterRenderComponent.setLayer(-5);
//        }
//
//        //Ponte 2
//        GameObject bridge2 = createGameObject();
//
//        PlatformRenderComponent bridge2RenderComponent = bridge2.addComponent(PlatformRenderComponent.class);
//        bridge2RenderComponent.color = Color.GOLD;
//        bridge2RenderComponent.width = bridgeW;
//        bridge2RenderComponent.height = bridgeH;
//        RigidBody rigidBridge2 = bridge2.addComponent(RigidBody.class);
//        rigidBridge2.setType(RigidBody.Type.KINEMATIC);
//        rigidBridge2.setCollider(BoxCollider.build(bridgeW, bridgeH));
//        AnimationSequence bridge2Animation = bridge2.addComponent(AnimationSequence.class);
//        bridge2.y = -15.5f;
//
//        //Character
//        GameObject character = createGameObject(-8, -20);
//
//        characterRenderer = character.addComponent(SpriteRenderer.class);
//        characterRenderer.setImage(elementsImage);
//        characterRenderer.setSize(2, 2);
//        characterRenderer.setSrcPosition(0, 128);
//        characterRenderer.setSrcSize(128, 128);
//        characterRenderer.setPivot(0.5f, 1);
//        characterRenderer.setLayer(-2);
//
//        //Personaggio
////        float pgW = 2;
////        float pgH = 2;
////        GameObject character = createGameObject(-8, -24);
////
////        TestingRender characterRender = character.addComponent(TestingRender.class);
////        characterRender.width = pgW;
////        characterRender.height = pgH;
////        AnimationSequence characterAnimation = character.addComponent(AnimationSequence.class);
//
//        //Palla demolitrice
//        GameObject wreckingBall = createGameObject();
//
//        RockRenderComponent wreckingBallRenderComponent = wreckingBall.addComponent(RockRenderComponent.class);
//        wreckingBallRenderComponent.radius = 2;
//        wreckingBallRenderComponent.color = Color.DARKCYAN;
//        RigidBody wreakingBallRigidBody = wreckingBall.addComponent(RigidBody.class);
//        wreakingBallRigidBody.setType(RigidBody.Type.DYNAMIC);
//        wreakingBallRigidBody.setCollider(CircleCollider.build(2, 10, 1, 1, false));
//        wreakingBallRigidBody.setLinearDamping(0.2f);
//
//        wreckingBall.y = 20;
//
//        //Aggancio
//        GameObject platformGO = createGameObject();
//
//        PlatformRenderComponent hookupRenderComponent = platformGO.addComponent(PlatformRenderComponent.class);
//        hookupRenderComponent.height = 1;
//        hookupRenderComponent.width = 1;
//        hookupRenderComponent.color = Color.GOLD;
//        RigidBody hookupRigidBody = platformGO.addComponent(RigidBody.class);
//        hookupRigidBody.setType(RigidBody.Type.STATIC);
//        hookupRigidBody.setCollider(BoxCollider.build(1, 1));
//
//        platformGO.y = 30;
//
//        //Joint
//        GameObject cursorJointObject = createGameObject();
//
//        CursorJoint cursorJoint = cursorJointObject.addComponent(CursorJoint.class);
//        cursorJoint.setRigidBody(wreakingBallRigidBody);
//
//        CursorJointInput cursorJointInput = cursorJointObject.addComponent(CursorJointInput.class);
//        cursorJointInput.setJoint(cursorJoint);
//        cursorJointInput.setSize(4, 4);
//        cursorJointInput.setMaxForce(9000);
//
//        cursorJointObject.y = 20;
//
//        GameObject ballHookupGO = createGameObject();
//        DistanceJoint ballHookup = ballHookupGO.addComponent(DistanceJoint.class);
//        ballHookup.setRigidBodyA(hookupRigidBody);
//        ballHookup.setRigidBodyB(wreakingBallRigidBody);
//        ballHookup.setLength(10);
//        ballHookup.setDampingRatio(1);
//        ballHookup.setFrequency(10);
//
//        //Piattaforma 1
//        GameObject platform1 = createGameObject(-9, 7, 160);
//
//        PlatformRenderComponent platform1RenderComponent = platform1.addComponent(PlatformRenderComponent.class);
//        platform1RenderComponent.color = Color.GREY;
//        platform1RenderComponent.width = platW;
//        platform1RenderComponent.height = platH;
//        RigidBody rigidPlatform1 = platform1.addComponent(RigidBody.class);
//        rigidPlatform1.setType(RigidBody.Type.STATIC);
//        rigidPlatform1.setCollider(BoxCollider.build(platW, platH));
//
//        //Piattaforma 2
//        GameObject platform2 = createGameObject(5, 2, 160);
//
//        PlatformRenderComponent platform2RenderComponent = platform2.addComponent(PlatformRenderComponent.class);
//        platform2RenderComponent.color = Color.GREY;
//        platform2RenderComponent.width = platW;
//        platform2RenderComponent.height = platH;
//        RigidBody rigidPlatform2 = platform2.addComponent(RigidBody.class);
//        rigidPlatform2.setType(RigidBody.Type.STATIC);
//        rigidPlatform2.setCollider(BoxCollider.build(platW, platH));
//
//
////        //Piattaforma Prova
////        PlatformRenderComponent platformPRenderComponent = new PlatformRenderComponent();
////        platformPRenderComponent.color = Color.WHITE;
////        platformPRenderComponent.width = platW;
////        platformPRenderComponent.height = platH;
////        RigidBody rigidPlatformP = RigidBody.build(RigidBody.Type.STATIC, BoxCollider.build(platW, platH));
////        GameObject platformP = createGameObject(platformPRenderComponent, rigidPlatformP);
////        platformP.x = -2;
////        platformP.y = 4.5f;
////        platformP.angle = 160;
//
//        // Piattaforma 3
//        GameObject platform3 = createGameObject(14, -1, 90);
//
//        PlatformRenderComponent platform3RenderComponent = platform3.addComponent(PlatformRenderComponent.class);
//        platform3RenderComponent.color = Color.GREY;
//        platform3RenderComponent.width = platW;
//        platform3RenderComponent.height = platH;
//        RigidBody rigidPlatform3 = platform3.addComponent(RigidBody.class);
//        rigidPlatform3.setType(RigidBody.Type.STATIC);
//        rigidPlatform3.setCollider(BoxCollider.build(platW, platH));
//
//        // Piattaforma 4
//        GameObject platform4 = createGameObject();
//
//        PlatformRenderComponent platform4RenderComponent = platform4.addComponent(PlatformRenderComponent.class);
//        platform4RenderComponent.color = Color.GREY;
//        platform4RenderComponent.width = miniPlatW;
//        platform4RenderComponent.height = platH;
//        RigidBody rigidPlatform4 = platform4.addComponent(RigidBody.class);
//        rigidPlatform4.setType(RigidBody.Type.STATIC);
//        rigidPlatform4.setCollider(BoxCollider.build(miniPlatW, platH));
//
//        platform4.x = 9;
//        platform4.y = -2;
//        platform4.angle = 90;
//
//        //Piattaforma 5
//        GameObject platform5 = createGameObject();
//        PlatformRenderComponent platform5RenderComponent = platform5.addComponent(PlatformRenderComponent.class);
//        platform5RenderComponent.color = Color.GREY;
//        platform5RenderComponent.width = platW;
//        platform5RenderComponent.height = platH;
//        RigidBody rigidPlatform5 = platform5.addComponent(RigidBody.class);
//        rigidPlatform5.setType(RigidBody.Type.STATIC);
//        rigidPlatform5.setCollider(BoxCollider.build(platW, platH));
//
//        platform5.x = -14;
//        platform5.y = -14;
//        platform5.angle = 90;
//
//        //Masso
//        GameObject rock = createGameObject(-10, 9);
//        RockRenderComponent rockRenderComponent = rock.addComponent(RockRenderComponent.class);
//        rockRenderComponent.color = Color.GREY;
//        rockRenderComponent.radius = 2;
//        RigidBody rigidRock = rock.addComponent(RigidBody.class);
//        rigidRock.setType(RigidBody.Type.DYNAMIC);
//        rigidRock.setCollider(CircleCollider.build(2));
//        rigidRock.setSleepingAllowed(false);
//
//        //Pulsante a pressione 1
//        GameObject pressure_plate1 = createGameObject();
//        PlatformRenderComponent phisic1RenderComponent = pressure_plate1.addComponent(PlatformRenderComponent.class);
//        phisic1RenderComponent.color = Color.RED;
//        phisic1RenderComponent.height = phisic1H;
//        phisic1RenderComponent.width = phisic1W;
//
//        RigidBody phisicSensor1 = pressure_plate1.addComponent(RigidBody.class);
//        phisicSensor1.setType(RigidBody.Type.STATIC);
//        phisicSensor1.setCollider(BoxCollider.build(phisic1W, phisic1H, true));
//        phisicSensor1.setSleepingAllowed(false);
//
//        PhysicsButton physicsButton = pressure_plate1.addComponent(PhysicsButton.class);
////        physicsButton.onCollisionEnter = () -> moveWin(bridge2Animation, rigidBridge2, characterAnimation);
//
//        pressure_plate1.x = 11.5f;
//        pressure_plate1.y = -4;
//
//        //Piattaforma sotto la pedana
//        GameObject platform6 = createGameObject(11.5f, -5f);
//
//        PlatformRenderComponent platform6RenderComponent = platform6.addComponent(PlatformRenderComponent.class);
//        platform6RenderComponent.color = Color.GREY;
//        platform6RenderComponent.width = plat2W;
//        platform6RenderComponent.height = plat2H;
//        RigidBody rigidPlatform6 = platform6.addComponent(RigidBody.class);
//        rigidPlatform6.setType(RigidBody.Type.KINEMATIC);
//        rigidPlatform6.setCollider(BoxCollider.build(plat2W, plat2H));
//
//        //Ponte 3
//        float bridge3W = 4;
//        float bridge3H = 0.7f;
//        GameObject bridge3 = createGameObject(-7.5f, 8.5f, 70f);
//
//        PlatformRenderComponent bridge3RenderComponent = bridge3.addComponent(PlatformRenderComponent.class);
//        bridge3RenderComponent.color = Color.GOLD;
//        bridge3RenderComponent.width = bridge3W;
//        bridge3RenderComponent.height = bridge3H;
//        RigidBody rigidBridge = bridge3.addComponent(RigidBody.class);
//        rigidBridge.setType(RigidBody.Type.KINEMATIC);
//        rigidBridge.setCollider(BoxCollider.build(bridge3W, bridge3H));
//        AnimationSequence bridge3Animation = bridge3.addComponent(AnimationSequence.class);
//        PlatformBehaviourComponent bridge3BehaviourComponent = bridge3.addComponent(PlatformBehaviourComponent.class);
//
//        //Pulsante
//        GameObject button = createGameObject(-13, 3);
//        ButtonRenderComponent buttonRenderComponent = button.addComponent(ButtonRenderComponent.class);
//        ButtonInputComponent buttonInputComponent = button.addComponent(ButtonInputComponent.class);
//        buttonInputComponent.buttonRenderComponent = buttonRenderComponent;
//        buttonRenderComponent.edge = 3;
//        buttonRenderComponent.radius = 1;
//        buttonInputComponent.width = 3;
//        buttonInputComponent.height = 3;
//        buttonInputComponent.runnable = () -> move1(bridge3Animation, rigidBridge);
    }

    public void move1(AnimationSequence bridge, RigidBody rigidBridge) {
        float oldX = -7.5f;
        float oldY = 8.5f;
        float newX = (float) (oldX - 4 * Math.cos(Math.toRadians(70)));
        float newY = (float) (oldY - 4 * Math.sin(Math.toRadians(70)));

        bridge.add(MoveRigidBodyTo.build(rigidBridge, newX, newY, 0.5f));
        bridge.start();
    }

    public void moveWin(AnimationSequence bridge, RigidBody rigidBridge, AnimationSequence character) {
        bridge.add(MoveRigidBodyTo.build(rigidBridge, 0, -25.5f, 1));
        bridge.start();

        character.add(WaitAnimation.build(1.2f));
        character.add(MoveToAnimation.build(character.getOwner(), 8, -24, 1));
        character.start();
    }

    public void moveLose(AnimationSequence bridge, RigidBody rigidBridge, AnimationSequence character) {
        bridge.add(MoveRigidBodyTo.build(rigidBridge, -8, -40, 1));
        bridge.start();

        character.add(WaitAnimation.build(0.5f));
        character.add(MoveToAnimation.build(character.getOwner(), -8, -40, 1));
        character.start();
    }
}
