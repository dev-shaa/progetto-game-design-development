package unina.game.myapplication;

import com.badlogic.androidgames.framework.Color;
import com.badlogic.androidgames.framework.Game;

import unina.game.myapplication.core.Camera;
import unina.game.myapplication.core.GameObject;
import unina.game.myapplication.core.Scene;
import unina.game.myapplication.core.animations.AnimationSequence;
import unina.game.myapplication.core.animations.CompositeAnimation;
import unina.game.myapplication.core.animations.MoveToAnimation;
import unina.game.myapplication.core.animations.WaitAnimation;
import unina.game.myapplication.core.physics.BoxCollider;
import unina.game.myapplication.core.physics.CircleCollider;
import unina.game.myapplication.core.physics.RigidBody;
import unina.game.myapplication.logic.DebugRenderer;
import unina.game.myapplication.logic.PhysicsButton;
import unina.game.myapplication.logic.PlatformDraggingComponent;
import unina.game.myapplication.logic.PlatformRenderComponent;
import unina.game.myapplication.logic.PressableComponent;
import unina.game.myapplication.logic.RockRenderComponent;
import unina.game.myapplication.logic.TestingRender;
import unina.game.myapplication.logic.common.Button;
import unina.game.myapplication.logic.menu.MainMenu;

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

        // Left floor
        GameObject leftFloor = createGameObject(-6, -14);

        PlatformRenderComponent leftFloorRenderer = leftFloor.addComponent(PlatformRenderComponent.class);
        leftFloorRenderer.color = Color.GREY;
        leftFloorRenderer.width = floorW;
        leftFloorRenderer.height = floorH;

        RigidBody leftFloorRigidBody = leftFloor.addComponent(RigidBody.class);
        leftFloorRigidBody.setType(RigidBody.Type.STATIC);
        leftFloorRigidBody.setCollider(BoxCollider.build(floorW, floorH));

        // Right floor
        GameObject rightFloor = createGameObject(6, -14);

        PlatformRenderComponent rightFloorRenderer = rightFloor.addComponent(PlatformRenderComponent.class);
        rightFloorRenderer.color = Color.GREY;
        rightFloorRenderer.width = floorW;
        rightFloorRenderer.height = floorH;

        RigidBody rightFloorRigidBody = rightFloor.addComponent(RigidBody.class);
        rightFloorRigidBody.setType(RigidBody.Type.STATIC);
        rightFloorRigidBody.setCollider(BoxCollider.build(floorW, floorH));

        //Piattaforma 3
        GameObject platform = createGameObject(4, 13, 30);

        float platW = 10;
        float platH = 0.5f;
        PlatformRenderComponent platformRenderComponent3 = platform.addComponent(PlatformRenderComponent.class);
        platformRenderComponent3.color = Color.GREY;
        platformRenderComponent3.width = platW;
        platformRenderComponent3.height = platH;

        RigidBody rigidPlatform = platform.addComponent(RigidBody.class);
        rigidPlatform.setType(RigidBody.Type.STATIC);
        rigidPlatform.setCollider(BoxCollider.build(platW, platH));


        //Piattaforma 4
        float plat2W = 1;
        float plat2H = 8;

        GameObject platform2 = createGameObject(-10, 5);

        PlatformRenderComponent platformRenderComponent4 = platform2.addComponent(PlatformRenderComponent.class);
        platformRenderComponent4.color = Color.GREY;
        platformRenderComponent4.width = plat2W;
        platformRenderComponent4.height = plat2H;

        RigidBody rigidPlatform2 = platform2.addComponent(RigidBody.class);
        rigidPlatform2.setType(RigidBody.Type.STATIC);
        rigidPlatform2.setCollider(BoxCollider.build(plat2W, plat2H));

        //Masso
        GameObject rock = createGameObject(4, 18);

        RockRenderComponent rockRenderComponent = rock.addComponent(RockRenderComponent.class);
        rockRenderComponent.color = Color.GREY;
        rockRenderComponent.radius = 2;
        RigidBody rigidRock = rock.addComponent(RigidBody.class);
        rigidRock.setType(RigidBody.Type.DYNAMIC);
        rigidRock.setCollider(CircleCollider.build(2));

        //Piattaforma scorrevole
        float dragPlatformWidth = 7.5f;
        float dragPlatformHeight = 0.5f;

        GameObject platformDragged = createGameObject(3, 14, 120);

        RigidBody rigidDrag = platformDragged.addComponent(RigidBody.class);
        rigidDrag.setType(RigidBody.Type.KINEMATIC);
        rigidDrag.setCollider(BoxCollider.build(dragPlatformWidth, dragPlatformHeight));
        rigidDrag.setSleepingAllowed(false);

        PlatformRenderComponent platformDraggedRenderComponent = platformDragged.addComponent(PlatformRenderComponent.class);
        platformDraggedRenderComponent.setStart(3, 14);
        platformDraggedRenderComponent.setEnd(5, 5);
        platformDraggedRenderComponent.color = Color.DARKCYAN;
        platformDraggedRenderComponent.width = dragPlatformWidth;
        platformDraggedRenderComponent.height = dragPlatformHeight;

        PlatformDraggingComponent platformDraggingComponent = platformDragged.addComponent(PlatformDraggingComponent.class);
        platformDraggingComponent.width = 10;
        platformDraggingComponent.height = 10;
        platformDraggingComponent.setStart(3, 14);
        platformDraggingComponent.setEnd(5, 5);
        platformDraggingComponent.rigidBody = rigidDrag;

        //Piattaforma scorrevole2
        float dragPlatform2Width = 7.5f;
        float dragPlatform2Height = 0.5f;

        GameObject platformDragged2 = createGameObject(-12, 14, 140);

        RigidBody rigidDrag2 = platformDragged2.addComponent(RigidBody.class);
        rigidDrag2.setType(RigidBody.Type.KINEMATIC);
        rigidDrag2.setCollider(BoxCollider.build(dragPlatform2Width, dragPlatform2Height));
        rigidDrag2.setSleepingAllowed(false);

        PlatformRenderComponent platformDragged2RenderComponent = platformDragged2.addComponent(PlatformRenderComponent.class);
        platformDragged2RenderComponent.color = Color.DARKCYAN;
        platformDragged2RenderComponent.width = dragPlatform2Width;
        platformDragged2RenderComponent.height = dragPlatform2Height;

        PlatformDraggingComponent platformDragging2Component = platformDragged2.addComponent(PlatformDraggingComponent.class);
        platformDragging2Component.width = 10;
        platformDragging2Component.height = 10;
        platformDragging2Component.rigidBody = rigidDrag2;
        platformDragging2Component.setStart(-12, 14);
        platformDragging2Component.setEnd(-8, 10);

        // Bridge
        float bridgeWidth = 6;
        float bridgeHeight = 1;

        GameObject bridge = createGameObject();

        PlatformRenderComponent bridgeRenderComponent = bridge.addComponent(PlatformRenderComponent.class);
        bridgeRenderComponent.color = Color.GOLD;
        bridgeRenderComponent.width = bridgeWidth;
        bridgeRenderComponent.height = bridgeHeight;

        // Character
        float characterWidth = 2;
        float characterHeight = 2;

        GameObject character = createGameObject(-6, -5.5f);

        TestingRender characterRenderer = character.addComponent(TestingRender.class);
        characterRenderer.width = characterWidth;
        characterRenderer.height = characterHeight;

        // Game Over trigger
        GameObject gameOverTriggerGO = createGameObject(-6, -5.5f);

        RigidBody gameOverTriggerRigidBody = gameOverTriggerGO.addComponent(RigidBody.class);
        gameOverTriggerRigidBody.setType(RigidBody.Type.STATIC);
        gameOverTriggerRigidBody.setCollider(BoxCollider.build(characterWidth, characterHeight, true));
        gameOverTriggerRigidBody.setSleepingAllowed(false);

        PhysicsButton gameOverTrigger = gameOverTriggerGO.addComponent(PhysicsButton.class);
        gameOverTrigger.setOnCollisionEnter(() -> gameOver(platformDraggingComponent, platformDragging2Component));

        // Pressure plate
        float pressurePlateWidth = 3;
        float pressurePlateHeight = 1;
        GameObject pressurePlateGO = createGameObject(0, 5);

        PlatformRenderComponent pressurePlateRenderer = pressurePlateGO.addComponent(PlatformRenderComponent.class);
        pressurePlateRenderer.color = Color.RED;
        pressurePlateRenderer.width = pressurePlateWidth;
        pressurePlateRenderer.height = pressurePlateHeight;

        RigidBody pressurePlateRigidBody = pressurePlateGO.addComponent(RigidBody.class);
        pressurePlateRigidBody.setType(RigidBody.Type.STATIC);
        pressurePlateRigidBody.setCollider(BoxCollider.build(pressurePlateWidth, pressurePlateHeight, true));
        pressurePlateRigidBody.setSleepingAllowed(false);

        PhysicsButton pressurePlate = pressurePlateGO.addComponent(PhysicsButton.class);
        pressurePlate.setOnCollisionEnter(() -> move(pressurePlateRenderer, pressurePlateRigidBody, bridge, character, gameOverTriggerGO));

        // Pressure plate platform
        float pressurePlatePlatformWidth = 4;
        float pressurePlatePlatformHeight = 1;
        GameObject pressurePlatePlatformGO = createGameObject(0, 4);

        PlatformRenderComponent pressurePlatePlatformRenderer = pressurePlatePlatformGO.addComponent(PlatformRenderComponent.class);
        pressurePlatePlatformRenderer.color = Color.GREY;
        pressurePlatePlatformRenderer.width = pressurePlatePlatformWidth;
        pressurePlatePlatformRenderer.height = pressurePlatePlatformHeight;

        RigidBody pressurePlatePlatformRigidBody = pressurePlatePlatformGO.addComponent(RigidBody.class);
        pressurePlatePlatformRigidBody.setType(RigidBody.Type.STATIC);
        pressurePlatePlatformRigidBody.setCollider(BoxCollider.build(pressurePlatePlatformWidth, pressurePlatePlatformHeight));
    }

    private void move(PlatformRenderComponent prova, RigidBody prova2, GameObject bridge, GameObject character, GameObject characterSensor) {
        if (isPressed)
            return;

        isPressed = true;

        prova.color = Color.GREEN;
        prova2.setTransform(prova2.getOwner().x, prova2.getOwner().y - 0.5f);

        removeGameObject(characterSensor);

        // Retry button
        GameObject retryButtonGO = createGameObject(-4, -24);

        DebugRenderer retryButtonRenderer = retryButtonGO.addComponent(DebugRenderer.class);
        retryButtonRenderer.setSize(6, 3);

        Button retryButton = retryButtonGO.addComponent(Button.class);
        retryButton.setSize(6, 3);
        retryButton.setOnClick(this::retry);

        // Next level button
        GameObject nextLevelButtonGO = createGameObject(4, -24);

        DebugRenderer nextLevelButtonRenderer = nextLevelButtonGO.addComponent(DebugRenderer.class);
        nextLevelButtonRenderer.setSize(6, 3);

        Button nextLevelButton = nextLevelButtonGO.addComponent(Button.class);
        nextLevelButton.setSize(6, 3);
        nextLevelButton.setOnClick(this::nextLevel);

        // Animator
        GameObject animatorGO = createGameObject();
        AnimationSequence animator = animatorGO.addComponent(AnimationSequence.class);
        animator.add(MoveToAnimation.build(bridge, 0, -7, 1));
        animator.add(MoveToAnimation.build(character, 6, -5.5f, 1));
        animator.add(CompositeAnimation.build(
                MoveToAnimation.build(retryButtonGO, -4, 1, 0.5f),
                MoveToAnimation.build(nextLevelButtonGO, 4, 1, 0.5f)
        ));
        animator.add(CompositeAnimation.build(
                MoveToAnimation.build(retryButtonGO, -4, 2, 0.05f),
                MoveToAnimation.build(nextLevelButtonGO, 4, 2, 0.05f)
        ));
        animator.add(CompositeAnimation.build(
                MoveToAnimation.build(retryButtonGO, -4, 0, 0.25f),
                MoveToAnimation.build(nextLevelButtonGO, 4, 0, 0.25f)
        ));
        animator.start();
    }

    private void gameOver(PressableComponent... interactableComponents) {
        for (PressableComponent component : interactableComponents)
            component.interactable = false;

        // Retry button
        GameObject retryButtonGO = createGameObject(4, -21);

        DebugRenderer retryButtonRenderer = retryButtonGO.addComponent(DebugRenderer.class);
        retryButtonRenderer.setSize(6, 3);

        Button retryButton = retryButtonGO.addComponent(Button.class);
        retryButton.setSize(6, 3);
        retryButton.setOnClick(this::retry);

        // Menu button
        GameObject menuButtonGO = createGameObject(-4, -21);

        DebugRenderer menuButtonRenderer = menuButtonGO.addComponent(DebugRenderer.class);
        menuButtonRenderer.setSize(6, 3);

        Button menuButton = menuButtonGO.addComponent(Button.class);
        menuButton.setSize(6, 3);
        menuButton.setOnClick(this::toMenu);

        // Animator
        GameObject animatorGO = createGameObject();
        AnimationSequence animator = animatorGO.addComponent(AnimationSequence.class);
        animator.add(CompositeAnimation.build(
                MoveToAnimation.build(retryButtonGO, 4, 1, 0.5f),
                MoveToAnimation.build(menuButtonGO, -4, 1, 0.5f)
        ));
        animator.add(CompositeAnimation.build(
                MoveToAnimation.build(retryButtonGO, 4, 2, 0.05f),
                MoveToAnimation.build(menuButtonGO, -4, 2, 0.05f)
        ));
        animator.add(CompositeAnimation.build(
                MoveToAnimation.build(retryButtonGO, 4, 0, 0.25f),
                MoveToAnimation.build(menuButtonGO, -4, 0, 0.25f)
        ));
        animator.start();
    }

    private void retry() {
        loadScene(Level2.class);
    }

    private void toMenu() {
        loadScene(MainMenu.class);
    }

    private void nextLevel() {
        loadScene(Level3.class);
    }

}
