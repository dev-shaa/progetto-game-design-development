package unina.game.myapplication;

import com.badlogic.androidgames.framework.Color;
import com.badlogic.androidgames.framework.Game;

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
        RigidBody rigidRock = RigidBody.build(RigidBody.Type.DYNAMIC, CircleCollider.build(2));
        RockDraggingComponent draggingComponent = new RockDraggingComponent();
        draggingComponent.rigidBody = rigidRock;
        draggingComponent.width = 4;
        draggingComponent.height = 4;
        GameObject rock = createGameObject(rockRenderComponent,draggingComponent, rigidRock);

        PlatformRenderComponent platformRenderComponent = new PlatformRenderComponent();
        platformRenderComponent.height = 1;
        platformRenderComponent.width = 2;
        platformRenderComponent.color = Color.GREY;
        RigidBody platform = RigidBody.build(RigidBody.Type.STATIC, BoxCollider.build(2,1));
        GameObject platformGO = createGameObject(platformRenderComponent,platform);
        platformGO.y = -3;

        PlatformRenderComponent ropeRenderComponent = new PlatformRenderComponent();
        ropeRenderComponent.height = 1;
        ropeRenderComponent.width = 2;
        ropeRenderComponent.color = Color.GREY;
        RigidBody rigidRope = RigidBody.build(RigidBody.Type.DYNAMIC, BoxCollider.build(2,1));
        GameObject rope = createGameObject(ropeRenderComponent, rigidRope);
        rope.y = -3;

        DistanceJoint ropeRock = DistanceJoint.build(rigidRope, rigidRock,2);
        DistanceJoint ropePlatform = DistanceJoint.build(rigidRope,platform,2);

    }

}
