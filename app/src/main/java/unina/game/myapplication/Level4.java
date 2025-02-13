package unina.game.myapplication;

import com.badlogic.androidgames.framework.Color;
import com.badlogic.androidgames.framework.Game;

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
import unina.game.myapplication.logic.ButtonInputComponent;
import unina.game.myapplication.logic.ButtonRenderComponent;
import unina.game.myapplication.logic.CursorJointInput;
import unina.game.myapplication.logic.PhysicsButton;
import unina.game.myapplication.logic.PlatformBehaviourComponent;
import unina.game.myapplication.logic.PlatformRenderComponent;
import unina.game.myapplication.logic.RockRenderComponent;
import unina.game.myapplication.logic.TestingRender;

public class Level4 extends Scene {
    public Level4(Game game) {
        super(game);
    }

    public void initialize() {
        super.initialize();

        Camera.getInstance().setSize(30);

        float floorW = 8;
        float floorH = 10;

        float bridgeW = 8;
        float bridgeH = 1;

        float platW = 8;
        float platH = 0.7f;
        float miniPlatW = 6;

        float phisic1W = 3.5f;
        float phisic1H = 1;

        float plat2W = 6;
        float plat2H = 1;

        //Pavimento
        PlatformRenderComponent floorRenderComponent1 = new PlatformRenderComponent();
        floorRenderComponent1.color = Color.GREY;
        floorRenderComponent1.width = floorW;
        floorRenderComponent1.height = floorH;
        RigidBody rigidFloor1 = RigidBody.build(RigidBody.Type.STATIC, BoxCollider.build(floorW, floorH));
        GameObject floor1 = createGameObject(floorRenderComponent1, rigidFloor1);
        floor1.x = 8;
        floor1.y = -30;

        //Ponte 1
        PlatformRenderComponent bridge1RenderComponent = new PlatformRenderComponent();
        bridge1RenderComponent.color = Color.GOLD;
        bridge1RenderComponent.width = bridgeW;
        bridge1RenderComponent.height = bridgeH;
        RigidBody rigidBridge1 = RigidBody.build(RigidBody.Type.KINEMATIC, BoxCollider.build(bridgeW,bridgeH));
        AnimationSequence bridge1Animation = AnimationSequence.build();
        GameObject bridge1 = createGameObject(bridge1RenderComponent, rigidBridge1, bridge1Animation);
        bridge1.x = -8;
        bridge1.y = -25.5f;

        //Ponte 2
        PlatformRenderComponent bridge2RenderComponent = new PlatformRenderComponent();
        bridge2RenderComponent.color = Color.GOLD;
        bridge2RenderComponent.width = bridgeW;
        bridge2RenderComponent.height = bridgeH;
        RigidBody rigidBridge2 = RigidBody.build(RigidBody.Type.KINEMATIC, BoxCollider.build(bridgeW,bridgeH));
        AnimationSequence bridge2Animation = AnimationSequence.build();
        GameObject bridge2 = createGameObject(bridge2RenderComponent, rigidBridge2, bridge2Animation);
        bridge2.y = -15.5f;

        //Palla demolitrice
        RockRenderComponent wreckingBallRenderComponent = new RockRenderComponent();
        wreckingBallRenderComponent.radius = 2;
        wreckingBallRenderComponent.color = Color.DARKCYAN;
        RigidBody wreakingBallRigidBody = RigidBody.build(RigidBody.Type.DYNAMIC, CircleCollider.build(2,10,1,1,false));
        wreakingBallRigidBody.setLinearDamping(0.2f);
        GameObject wreckingBall = createGameObject(wreckingBallRenderComponent,wreakingBallRigidBody);
        wreckingBall.y = 20;

        //Aggancio
        PlatformRenderComponent hookupRenderComponent = new PlatformRenderComponent();
        hookupRenderComponent.height = 1;
        hookupRenderComponent.width = 1;
        hookupRenderComponent.color = Color.GOLD;
        RigidBody hookupRigidBody = RigidBody.build(RigidBody.Type.STATIC, BoxCollider.build(1,1));
        GameObject platformGO = createGameObject(hookupRenderComponent,hookupRigidBody);
        platformGO.y = 30;

        //Joint
        CursorJoint cursorJoint = CursorJoint.build();
        cursorJoint.setRigidBody(wreakingBallRigidBody);

        CursorJointInput cursorJointInput = new CursorJointInput();
        cursorJointInput.setJoint(cursorJoint);
        cursorJointInput.setSize(4,4);
        cursorJointInput.setMaxForce(9000);
        GameObject cursorJointObject = createGameObject(cursorJointInput,cursorJoint);
        cursorJointObject.y = 20;

        DistanceJoint ballHookup = DistanceJoint.build(hookupRigidBody,wreakingBallRigidBody,10,1,10);
        createGameObject(ballHookup);

        //Piattaforma 1
        PlatformRenderComponent platform1RenderComponent = new PlatformRenderComponent();
        platform1RenderComponent.color = Color.GREY;
        platform1RenderComponent.width = platW;
        platform1RenderComponent.height = platH;
        RigidBody rigidPlatform1 = RigidBody.build(RigidBody.Type.STATIC, BoxCollider.build(platW, platH));
        GameObject platform1 = createGameObject(platform1RenderComponent, rigidPlatform1);
        platform1.x = -9;
        platform1.y = 7;
        platform1.angle = 160;

        //Piattaforma 2
        PlatformRenderComponent platform2RenderComponent = new PlatformRenderComponent();
        platform2RenderComponent.color = Color.GREY;
        platform2RenderComponent.width = platW;
        platform2RenderComponent.height = platH;
        RigidBody rigidPlatform2 = RigidBody.build(RigidBody.Type.STATIC, BoxCollider.build(platW, platH));
        GameObject platform2 = createGameObject(platform2RenderComponent, rigidPlatform2);
        platform2.x = 5;
        platform2.y = 2;
        platform2.angle = 160;

        //Piattaforma Prova
        PlatformRenderComponent platformPRenderComponent = new PlatformRenderComponent();
        platformPRenderComponent.color = Color.WHITE;
        platformPRenderComponent.width = platW;
        platformPRenderComponent.height = platH;
        RigidBody rigidPlatformP = RigidBody.build(RigidBody.Type.STATIC, BoxCollider.build(platW, platH));
        GameObject platformP = createGameObject(platformPRenderComponent, rigidPlatformP);
        platformP.x = -2;
        platformP.y = 4.5f;
        platformP.angle = 160;

        //Piattaforma 3
        PlatformRenderComponent platform3RenderComponent = new PlatformRenderComponent();
        platform3RenderComponent.color = Color.GREY;
        platform3RenderComponent.width = platW;
        platform3RenderComponent.height = platH;
        RigidBody rigidPlatform3 = RigidBody.build(RigidBody.Type.STATIC, BoxCollider.build(platW, platH));
        GameObject platform3 = createGameObject(platform3RenderComponent, rigidPlatform3);
        platform3.x = 14;
        platform3.y = -1;
        platform3.angle = 90;

        //Piattaforma 4
        PlatformRenderComponent platform4RenderComponent = new PlatformRenderComponent();
        platform4RenderComponent.color = Color.GREY;
        platform4RenderComponent.width = miniPlatW;
        platform4RenderComponent.height = platH;
        RigidBody rigidPlatform4 = RigidBody.build(RigidBody.Type.STATIC, BoxCollider.build(miniPlatW, platH));
        GameObject platform4 = createGameObject(platform4RenderComponent, rigidPlatform4);
        platform4.x = 9;
        platform4.y = -2;
        platform4.angle = 90;

        //Masso
        RockRenderComponent rockRenderComponent = new RockRenderComponent();
        rockRenderComponent.color = Color.GREY;
        rockRenderComponent.radius = 2;
        RigidBody rigidRock = RigidBody.build(RigidBody.Type.DYNAMIC, CircleCollider.build(2));
        rigidRock.setSleepingAllowed(false);
        GameObject rock = createGameObject(rockRenderComponent, rigidRock);
        rock.x = -10;
        rock.y = 9;

        //Personaggio
        float pgW = 2;
        float pgH = 2;
        TestingRender characterRender = new TestingRender();
        characterRender.width = pgW;
        characterRender.height = pgH;
        AnimationSequence characterAnimation = AnimationSequence.build();
        GameObject character = createGameObject(characterRender,characterAnimation);
        character.x = -8;
        character.y = -24;

        //Sensore Personaggio
        RigidBody characterBody = RigidBody.build(RigidBody.Type.STATIC,BoxCollider.build(pgW,pgH,true));
        characterBody.setSleepingAllowed(false);
        PhysicsButton characterBehaviour = PhysicsButton.build();
//        characterBehaviour.onCollisionEnter = () -> gameOver(platformDraggingComponent,platformDragging2Component);
        GameObject characterSensor = createGameObject(characterBehaviour,characterBody);
        characterSensor.x = -8;
        characterSensor.y = -24;

        //Pulsante a pressione 1
        PlatformRenderComponent phisic1RenderComponent = new PlatformRenderComponent();
        phisic1RenderComponent.color = Color.RED;
        phisic1RenderComponent.height = phisic1H;
        phisic1RenderComponent.width = phisic1W;
        RigidBody phisicSensor1 = RigidBody.build(RigidBody.Type.STATIC, BoxCollider.build(phisic1W, phisic1H, true));
        phisicSensor1.setSleepingAllowed(false);
        PhysicsButton physicsButton = PhysicsButton.build();
        physicsButton.onCollisionEnter = () -> moveWin(bridge2Animation, rigidBridge2, characterAnimation, characterSensor);
        GameObject pressure_plate1 = createGameObject(phisic1RenderComponent, phisicSensor1, physicsButton);
        pressure_plate1.x = 11.5f;
        pressure_plate1.y = -4;

        //Piattaforma sotto la pedana
        PlatformRenderComponent platform5RenderComponent = new PlatformRenderComponent();
        platform5RenderComponent.color = Color.GREY;
        platform5RenderComponent.width = plat2W;
        platform5RenderComponent.height = plat2H;
        RigidBody rigidPlatform5 = RigidBody.build(RigidBody.Type.STATIC, BoxCollider.build(plat2W, plat2H));
        GameObject platform5 = createGameObject(platform5RenderComponent, rigidPlatform5);
        platform5.x = 11.5f;
        platform5.y = -5;

        //Ponte 3
        float bridge3W = 4;
        float bridge3H = 0.7f;
        PlatformRenderComponent bridge3RenderComponent = new PlatformRenderComponent();
        bridge3RenderComponent.color = Color.GOLD;
        bridge3RenderComponent.width = bridge3W;
        bridge3RenderComponent.height = bridge3H;
        RigidBody rigidBridge = RigidBody.build(RigidBody.Type.KINEMATIC,BoxCollider.build(bridge3W,bridge3H));
        AnimationSequence bridge3Animation = AnimationSequence.build();
        PlatformBehaviourComponent bridge3BehaviourComponent = new PlatformBehaviourComponent();
        GameObject bridge3 = createGameObject(bridge3RenderComponent,rigidBridge, bridge3BehaviourComponent, bridge3Animation);
        bridge3.x = -7.5f;
        bridge3.y = 8.5f;
        bridge3.angle = 70;

        //Pulsante
        ButtonRenderComponent buttonRenderComponent = new ButtonRenderComponent();
        ButtonInputComponent buttonInputComponent = new ButtonInputComponent();
        buttonInputComponent.buttonRenderComponent = buttonRenderComponent;
        buttonRenderComponent.edge = 3;
        buttonRenderComponent.radius = 1;
        buttonInputComponent.width = 3;
        buttonInputComponent.height = 3;
        buttonInputComponent.runnable = () -> move1(bridge3Animation,rigidBridge);
        GameObject button = createGameObject(buttonRenderComponent, buttonInputComponent);
        button.x = -13;
        button.y = 3;
    }

    public void move1(AnimationSequence bridge, RigidBody rigidBridge) {
        float oldX = -7.5f;
        float oldY = 8.5f;
        float newX = (float) (oldX-4 * Math.cos(Math.toRadians(70)));
        float newY = (float) (oldY-4 * Math.sin(Math.toRadians(70)));

        bridge.add(MoveRigidBodyTo.build(rigidBridge, newX,newY, 0.5f));
        bridge.start();
    }

    public void moveWin(AnimationSequence bridge, RigidBody rigidBridge, AnimationSequence character, GameObject sensorCharacter) {
        removeGameObject(sensorCharacter);

        bridge.add(MoveRigidBodyTo.build(rigidBridge,0,-25.5f,1));
        bridge.start();

        character.add(WaitAnimation.build(1.2f));
        character.add(MoveToAnimation.build(character.getOwner(),8,-24,1));
        character.start();
    }
}
