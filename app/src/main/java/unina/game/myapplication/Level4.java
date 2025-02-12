package unina.game.myapplication;

import com.badlogic.androidgames.framework.Color;
import com.badlogic.androidgames.framework.Game;

import unina.game.myapplication.core.Camera;
import unina.game.myapplication.core.GameObject;
import unina.game.myapplication.core.Scene;
import unina.game.myapplication.core.animations.AnimationSequence;
import unina.game.myapplication.core.physics.BoxCollider;
import unina.game.myapplication.core.physics.CircleCollider;
import unina.game.myapplication.core.physics.CursorJoint;
import unina.game.myapplication.core.physics.DistanceJoint;
import unina.game.myapplication.core.physics.RigidBody;
import unina.game.myapplication.logic.CursorJointInput;
import unina.game.myapplication.logic.PlatformBehaviourComponent;
import unina.game.myapplication.logic.PlatformRenderComponent;
import unina.game.myapplication.logic.RockRenderComponent;

public class Level4 extends Scene {
    public Level4(Game game) {
        super(game);
    }

    public void initialize() {
        super.initialize();

        Camera.getInstance().setSize(30);

        float floorW = 6;
        float floorH = 10;

        //Piattaforma 1
        PlatformRenderComponent floorRenderComponent1 = new PlatformRenderComponent();
        floorRenderComponent1.color = Color.GREY;
        floorRenderComponent1.width = floorW;
        floorRenderComponent1.height = floorH;
        RigidBody rigidFloor1 = RigidBody.build(RigidBody.Type.STATIC, BoxCollider.build(floorW, floorH));
        GameObject floor1 = createGameObject(floorRenderComponent1, rigidFloor1);
        floor1.x = 8;
        floor1.y = -30;

        float bridgeW = 6;
        float bridgeH = 1;

        //Ponte 1
        PlatformRenderComponent bridge1RenderComponent = new PlatformRenderComponent();
        bridge1RenderComponent.color = Color.GOLD;
        bridge1RenderComponent.width = bridgeW;
        bridge1RenderComponent.height = bridgeH;
        AnimationSequence bridge1Animation = AnimationSequence.build();
        PlatformBehaviourComponent bridge1BehaviourComponent = new PlatformBehaviourComponent();
        GameObject bridge1 = createGameObject(bridge1RenderComponent, bridge1BehaviourComponent, bridge1Animation);
        bridge1.x = -8;
        bridge1.y = -25.5f;

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
    }
}
