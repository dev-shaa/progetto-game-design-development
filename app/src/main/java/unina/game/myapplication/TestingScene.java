package unina.game.myapplication;

import com.badlogic.androidgames.framework.Color;
import com.badlogic.androidgames.framework.Game;

import java.util.ArrayList;

import unina.game.myapplication.core.Camera;
import unina.game.myapplication.core.GameObject;
import unina.game.myapplication.core.Scene;
import unina.game.myapplication.core.physics.BoxCollider;
import unina.game.myapplication.core.physics.CircleCollider;
import unina.game.myapplication.core.physics.DistanceJoint;
import unina.game.myapplication.core.physics.RigidBody;
import unina.game.myapplication.logic.PlatformRenderComponent;
import unina.game.myapplication.logic.RockDraggingComponent;
import unina.game.myapplication.logic.RockRenderComponent;

public class TestingScene extends Scene {

    public TestingScene(Game game) {
        super(game);
    }

    @Override
    public void initialize() {
        super.initialize();


        Camera.getInstance().setSize(20);
        RockRenderComponent rockRenderComponent = new RockRenderComponent();
        rockRenderComponent.radius = 2;
        rockRenderComponent.color = Color.GREY;
        RigidBody rigidRock = RigidBody.build(RigidBody.Type.DYNAMIC, CircleCollider.build(2,10,1,1,false));
        RockDraggingComponent draggingComponent = new RockDraggingComponent();
        draggingComponent.rigidBody = rigidRock;
        draggingComponent.width = 4;
        draggingComponent.height = 4;
        GameObject rock = createGameObject(rockRenderComponent,draggingComponent, rigidRock);
        rock.y = 0;

        PlatformRenderComponent platformRenderComponent = new PlatformRenderComponent();
        platformRenderComponent.height = 1;
        platformRenderComponent.width = 2;
        platformRenderComponent.color = Color.GREY;
        RigidBody platform = RigidBody.build(RigidBody.Type.STATIC, BoxCollider.build(2,1));
        GameObject platformGO = createGameObject(platformRenderComponent,platform);
        platformGO.y = 10;

//        PlatformRenderComponent ropeRenderComponent = new PlatformRenderComponent();
//        ropeRenderComponent.height = 1;
//        ropeRenderComponent.width = 1;
//        ropeRenderComponent.color = Color.GREY;
//        RigidBody rigidRope = RigidBody.build(RigidBody.Type.DYNAMIC, BoxCollider.build(2,1));
//        GameObject rope = createGameObject(ropeRenderComponent, rigidRope);
//        rope.y = -3;
//
//        DistanceJoint ropeRock = DistanceJoint.build(rigidRope, rigidRock,1);
//        DistanceJoint ropePlatform = DistanceJoint.build(rigidRope,platform,1);
//
//        createGameObject(ropePlatform);
//        createGameObject(ropeRock);

        int max = 10;

        ArrayList <RigidBody> rigidRopes = new ArrayList<>();
        for (int i = 0; i < max; i++) {
            float radius = 0.2f;
            RockRenderComponent render = new RockRenderComponent();
            render.radius = 0.2f;
            render.color = Color.GREY;
            RigidBody rigid = RigidBody.build(RigidBody.Type.DYNAMIC,CircleCollider.build(radius));
            GameObject rope = createGameObject(render,rigid);
            rope.y = 10 - i;
            rigidRopes.add(rigid);
            if (i == 0) {
                DistanceJoint ropePlatform = DistanceJoint.build(rigid,platform,0.2f,1,-1);
                createGameObject(ropePlatform);
            } else if (i == max-1) {
                DistanceJoint ropeRope = DistanceJoint.build(rigid,rigidRopes.get(i-1),0.2f,1,-1);
                createGameObject(ropeRope);
                DistanceJoint ropeRock = DistanceJoint.build(rigid,rigidRock,0.2f,1,-1);
                createGameObject(ropeRock);
            } else {
                DistanceJoint ropeRope = DistanceJoint.build(rigid,rigidRopes.get(i-1),0.2f,1,-1);
                createGameObject(ropeRope);
            }
        }

    }

}
