package unina.game.myapplication;

import com.badlogic.androidgames.framework.Color;
import com.badlogic.androidgames.framework.Game;

import unina.game.myapplication.core.GameObject;
import unina.game.myapplication.core.Scene;
import unina.game.myapplication.core.physics.BoxCollider;
import unina.game.myapplication.core.physics.CircleCollider;
import unina.game.myapplication.core.physics.RigidBody;
import unina.game.myapplication.logic.PhysicsButton;
import unina.game.myapplication.logic.PlatformDraggingComponent;
import unina.game.myapplication.logic.PlatformRenderComponent;
import unina.game.myapplication.logic.RockRenderComponent;

public class Level2 extends Scene {
    public Level2(Game game) {
        super(game);
    }

    @Override
    public void initialize() {
        super.initialize();

        //Piattaforma 1
        PlatformRenderComponent platformRenderComponent1 = new PlatformRenderComponent();
        platformRenderComponent1.color = Color.GREY;
        platformRenderComponent1.width = 7;
        platformRenderComponent1.height = 8;
        RigidBody rigidFloor1 = RigidBody.build(RigidBody.Type.STATIC, BoxCollider.build(7,8));
        GameObject floor1 = createGameObject(platformRenderComponent1,rigidFloor1);
        floor1.x = -5;
        floor1.y = -10;

        //Piattaforma 2
        PlatformRenderComponent platformRenderComponent2 = new PlatformRenderComponent();
        platformRenderComponent2.color = Color.GREY;
        platformRenderComponent2.width = 7;
        platformRenderComponent2.height = 8;
        RigidBody rigidFloor2 = RigidBody.build(RigidBody.Type.STATIC, BoxCollider.build(7,8));
        GameObject floor2 = createGameObject(platformRenderComponent2,rigidFloor2);
        floor2.x = 5;
        floor2.y = -10;

        //Piattaforma 3
        PlatformRenderComponent platformRenderComponent3 = new PlatformRenderComponent();
        platformRenderComponent3.color = Color.GREY;
        platformRenderComponent3.width = 6;
        platformRenderComponent3.height = 0.5f;
        RigidBody rigidPlatform = RigidBody.build(RigidBody.Type.STATIC, BoxCollider.build(6,0.5f));
        GameObject platform = createGameObject(platformRenderComponent3,rigidPlatform);
        platform.x = 2;
        platform.y = 5.5f;
        platform.angle = 30;

        //Masso
        RockRenderComponent rockRenderComponent = new RockRenderComponent();
        rockRenderComponent.color = Color.GREY;
        rockRenderComponent.radius = 1;
        RigidBody rigidRock = RigidBody.build(RigidBody.Type.DYNAMIC, CircleCollider.build(1));
        GameObject rock = createGameObject(rockRenderComponent,rigidRock);
        rock.x = 4;
        rock.y = 8;

        //Piattaforma scorrevole
        float dragPlatformWidth = 4.5f;
        float dragPlatformHeight = 0.5f;
        PlatformRenderComponent platformDraggedRenderComponent = new PlatformRenderComponent();
        platformDraggedRenderComponent.color = Color.GOLD;
        platformDraggedRenderComponent.width = dragPlatformWidth;
        platformDraggedRenderComponent.height = dragPlatformHeight;
        PlatformDraggingComponent platformDraggingComponent = new PlatformDraggingComponent();
        platformDraggingComponent.width = dragPlatformWidth;
        platformDraggingComponent.height = dragPlatformHeight;
        RigidBody rigidDrag = RigidBody.build(RigidBody.Type.KINEMATIC, BoxCollider.build(dragPlatformWidth,dragPlatformHeight));
        rigidDrag.setSleepingAllowed(false);
        platformDraggingComponent.rigidBody = rigidDrag;
        GameObject platformDragged = createGameObject(platformDraggedRenderComponent,platformDraggingComponent,rigidDrag);
        platformDragged.x = 3;
        platformDragged.y = 7;
        platformDragged.angle = 120;

        //Pulsante a pressione
        float phisicW = 3;
        float phisicH = 1;
        PlatformRenderComponent phisicRenderComponent = new PlatformRenderComponent();
        phisicRenderComponent.color = Color.RED;
        phisicRenderComponent.height = phisicH;
        phisicRenderComponent.width = phisicW;
        RigidBody phisicSensor = RigidBody.build(RigidBody.Type.STATIC,BoxCollider.build(phisicW,phisicH,true));
        phisicSensor.setSleepingAllowed(false);
        PhysicsButton physicsButton = PhysicsButton.build();
        physicsButton.onCollisionEnter = () -> move(phisicRenderComponent,Color.GREEN);
        physicsButton.onCollisionExit = () -> move(phisicRenderComponent,Color.RED);
        GameObject pressure_plate = createGameObject(phisicRenderComponent,phisicSensor,physicsButton);
        //pressure_plate.x = -3;
        //pressure_plate.y = -1;
        pressure_plate.y = -8;
    }

public void move(PlatformRenderComponent prova, int color) {
        prova.color = color;
    }
}
