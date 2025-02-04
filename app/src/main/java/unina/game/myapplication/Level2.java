package unina.game.myapplication;

import com.badlogic.androidgames.framework.Color;
import com.badlogic.androidgames.framework.Game;

import unina.game.myapplication.core.Camera;
import unina.game.myapplication.core.GameObject;
import unina.game.myapplication.core.Scene;
import unina.game.myapplication.core.physics.BoxCollider;
import unina.game.myapplication.core.physics.CircleCollider;
import unina.game.myapplication.core.physics.RigidBody;
import unina.game.myapplication.logic.PhysicsButton;
import unina.game.myapplication.logic.PlatformBehaviourComponent;
import unina.game.myapplication.logic.PlatformDraggingComponent;
import unina.game.myapplication.logic.PlatformRenderComponent;
import unina.game.myapplication.logic.RockRenderComponent;

public class Level2 extends Scene {

    private boolean isPressed = false;

    public Level2(Game game) {
        super(game);
    }

    @Override
    public void initialize() {
        super.initialize();

        Camera.getInstance().setSize(20);

        float floorW = 6;
        float floorH = 15;

        //Piattaforma 1
        PlatformRenderComponent platformRenderComponent1 = new PlatformRenderComponent();
        platformRenderComponent1.color = Color.GREY;
        platformRenderComponent1.width = floorW;
        platformRenderComponent1.height = floorH;
        RigidBody rigidFloor1 = RigidBody.build(RigidBody.Type.STATIC, BoxCollider.build(floorW, floorH));
        GameObject floor1 = createGameObject(platformRenderComponent1, rigidFloor1);
        floor1.x = -6;
        floor1.y = -14;

        //Piattaforma 2
        PlatformRenderComponent platformRenderComponent2 = new PlatformRenderComponent();
        platformRenderComponent2.color = Color.GREY;
        platformRenderComponent2.width = floorW;
        platformRenderComponent2.height = floorH;
        RigidBody rigidFloor2 = RigidBody.build(RigidBody.Type.STATIC, BoxCollider.build(floorW, floorH));
        GameObject floor2 = createGameObject(platformRenderComponent2, rigidFloor2);
        floor2.x = 6;
        floor2.y = -14;

        //Piattaforma 3
        float platW = 10;
        float platH = 0.5f;
        PlatformRenderComponent platformRenderComponent3 = new PlatformRenderComponent();
        platformRenderComponent3.color = Color.GREY;
        platformRenderComponent3.width = platW;
        platformRenderComponent3.height = platH;
        RigidBody rigidPlatform = RigidBody.build(RigidBody.Type.STATIC, BoxCollider.build(platW, platH));
        GameObject platform = createGameObject(platformRenderComponent3, rigidPlatform);
        platform.x = 4;
        platform.y = 13;
        platform.angle = 30;

        //Piattaforma 4
        float plat2W = 1;
        float plat2H = 8;
        PlatformRenderComponent platformRenderComponent4 = new PlatformRenderComponent();
        platformRenderComponent4.color = Color.GREY;
        platformRenderComponent4.width = plat2W;
        platformRenderComponent4.height = plat2H;
        RigidBody rigidPlatform2 = RigidBody.build(RigidBody.Type.STATIC, BoxCollider.build(plat2W, plat2H));
        GameObject platform2 = createGameObject(platformRenderComponent4, rigidPlatform2);
        platform2.x = -10;
        platform2.y = 5;

        //Masso
        RockRenderComponent rockRenderComponent = new RockRenderComponent();
        rockRenderComponent.color = Color.GREY;
        rockRenderComponent.radius = 2;
        RigidBody rigidRock = RigidBody.build(RigidBody.Type.DYNAMIC, CircleCollider.build(2));
        GameObject rock = createGameObject(rockRenderComponent, rigidRock);
        rock.x = 4;
        rock.y = 18;

        //Piattaforma scorrevole
        float dragPlatformWidth = 7.5f;
        float dragPlatformHeight = 0.5f;
        PlatformRenderComponent platformDraggedRenderComponent = new PlatformRenderComponent();
        platformDraggedRenderComponent.color = Color.GOLD;
        platformDraggedRenderComponent.width = dragPlatformWidth;
        platformDraggedRenderComponent.height = dragPlatformHeight;
        PlatformDraggingComponent platformDraggingComponent = new PlatformDraggingComponent();
        platformDraggingComponent.width = 10;
        platformDraggingComponent.height = 10;
//        platformDraggingComponent.x0 = 3;
//        platformDraggingComponent.y0 = 14;
//        platformDraggingComponent.angle = 120;
        platformDraggingComponent.setStart(3, 14);
        platformDraggingComponent.setEnd(5, 5);

        platformDraggedRenderComponent.setStart(3, 14);
        platformDraggedRenderComponent.setEnd(5, 5);

        RigidBody rigidDrag = RigidBody.build(RigidBody.Type.KINEMATIC, BoxCollider.build(dragPlatformWidth, dragPlatformHeight));
        rigidDrag.setSleepingAllowed(false);
        platformDraggingComponent.rigidBody = rigidDrag;
        GameObject platformDragged = createGameObject(platformDraggedRenderComponent, platformDraggingComponent, rigidDrag);
        platformDragged.x = 3;
        platformDragged.y = 14;
        platformDragged.angle = 120;

        //Piattaforma scorrevole2
        float dragPlatform2Width = 7.5f;
        float dragPlatform2Height = 0.5f;
        PlatformRenderComponent platformDragged2RenderComponent = new PlatformRenderComponent();
        platformDragged2RenderComponent.color = Color.GOLD;
        platformDragged2RenderComponent.width = dragPlatform2Width;
        platformDragged2RenderComponent.height = dragPlatform2Height;
        PlatformDraggingComponent platformDragging2Component = new PlatformDraggingComponent();
        platformDragging2Component.width = 10;
        platformDragging2Component.height = 10;
        RigidBody rigidDrag2 = RigidBody.build(RigidBody.Type.KINEMATIC, BoxCollider.build(dragPlatform2Width, dragPlatform2Height));
        rigidDrag2.setSleepingAllowed(false);
        platformDragging2Component.rigidBody = rigidDrag2;
        GameObject platformDragged2 = createGameObject(platformDragged2RenderComponent, platformDragging2Component, rigidDrag2);
        platformDragged2.x = -8;
        platformDragged2.y = 10;
//        platformDragging2Component.x0 = -8;
//        platformDragging2Component.y0 = 10;
//        platformDragging2Component.angle = 120;
        platformDragged2.angle = 140;

        //Ponte
        float bridgeW = 6;
        float bridgeH = 1;
        PlatformRenderComponent bridgeRenderComponent = new PlatformRenderComponent();
        bridgeRenderComponent.color = Color.GOLD;
        bridgeRenderComponent.width = bridgeW;
        bridgeRenderComponent.height = bridgeH;
        //RigidBody rigidBridge = RigidBody.build(RigidBody.Type.KINEMATIC, BoxCollider.build(5,2));
        PlatformBehaviourComponent bridgeBehaviourComponent = new PlatformBehaviourComponent();
        GameObject bridge = createGameObject(bridgeRenderComponent, bridgeBehaviourComponent);
        //bridge.x = -1.35f;
        bridge.y = 0;

        //Pulsante a pressione
        float phisicW = 3;
        float phisicH = 1;
        PlatformRenderComponent phisicRenderComponent = new PlatformRenderComponent();
        phisicRenderComponent.color = Color.RED;
        phisicRenderComponent.height = phisicH;
        phisicRenderComponent.width = phisicW;
        RigidBody phisicSensor = RigidBody.build(RigidBody.Type.STATIC, BoxCollider.build(phisicW, phisicH, true));
        phisicSensor.setSleepingAllowed(false);
        PhysicsButton physicsButton = PhysicsButton.build();
        physicsButton.onCollisionEnter = () -> move(phisicRenderComponent, phisicSensor, bridgeBehaviourComponent, Color.GREEN);
        //physicsButton.onCollisionExit = () -> move(phisicRenderComponent,Color.RED);
        GameObject pressure_plate = createGameObject(phisicRenderComponent, phisicSensor, physicsButton);
        //pressure_plate.x = -3;
        //pressure_plate.y = -1;
        pressure_plate.y = 5;

        //Piattaforma sotto la pedana
        float plat3W = 4;
        float plat3H = 1;
        PlatformRenderComponent platformRenderComponent5 = new PlatformRenderComponent();
        platformRenderComponent5.color = Color.GREY;
        platformRenderComponent5.width = plat3W;
        platformRenderComponent5.height = plat3H;
        RigidBody rigidPlatform3 = RigidBody.build(RigidBody.Type.STATIC, BoxCollider.build(plat3W, plat3H));
        GameObject platform3 = createGameObject(platformRenderComponent5, rigidPlatform3);
        //platform3.x = -10;
        platform3.y = 4;

    }

    public void move(PlatformRenderComponent prova, RigidBody prova2, PlatformBehaviourComponent bridge, int color) {
        if (!isPressed) {
            prova2.setTransform(prova2.getOwner().x, prova2.getOwner().y - 0.5f);
            prova.color = color;
            bridge.destY = -7;
            bridge.hasToMove = true;
            isPressed = true;
        }
    }
}
