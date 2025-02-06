package unina.game.myapplication;

import com.badlogic.androidgames.framework.Color;
import com.badlogic.androidgames.framework.Game;

import unina.game.myapplication.core.Camera;
import unina.game.myapplication.core.GameObject;
import unina.game.myapplication.core.Scene;
import unina.game.myapplication.core.animations.AnimationSequence;
import unina.game.myapplication.core.animations.MoveToAnimation;
import unina.game.myapplication.core.animations.WaitAnimation;
import unina.game.myapplication.core.physics.BoxCollider;
import unina.game.myapplication.core.physics.RigidBody;
import unina.game.myapplication.logic.ButtonInputComponent;
import unina.game.myapplication.logic.ButtonRenderComponent;
import unina.game.myapplication.logic.DebugRenderer;
import unina.game.myapplication.logic.PlatformBehaviourComponent;
import unina.game.myapplication.logic.PlatformRenderComponent;
import unina.game.myapplication.logic.TestingRender;
import unina.game.myapplication.logic.common.Button;

public class Level1 extends Scene {
    public Level1(Game game) {
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
        RigidBody rigidFloor1 = RigidBody.build(RigidBody.Type.STATIC, BoxCollider.build(7, 8));
        GameObject floor1 = createGameObject(platformRenderComponent1, rigidFloor1);
        floor1.x = -6;
        floor1.y = -14;

        //Piattaforma 2
        PlatformRenderComponent platformRenderComponent2 = new PlatformRenderComponent();
        platformRenderComponent2.color = Color.GREY;
        platformRenderComponent2.width = floorW;
        platformRenderComponent2.height = floorH;
        RigidBody rigidFloor2 = RigidBody.build(RigidBody.Type.STATIC, BoxCollider.build(7, 8));
        GameObject floor2 = createGameObject(platformRenderComponent2, rigidFloor2);
        floor2.x = 6;
        floor2.y = -14;

        //Ponte
        float bridgeW = 6;
        float bridgeH = 1;
        PlatformRenderComponent bridgeRenderComponent = new PlatformRenderComponent();
        bridgeRenderComponent.color = Color.GOLD;
        bridgeRenderComponent.width = bridgeW;
        bridgeRenderComponent.height = bridgeH;
        AnimationSequence bridgeAnimation = AnimationSequence.build();
        PlatformBehaviourComponent bridgeBehaviourComponent = new PlatformBehaviourComponent();
        GameObject bridge = createGameObject(bridgeRenderComponent, bridgeBehaviourComponent, bridgeAnimation);
        //bridge.x = -1.35f;
        bridge.y = 0;

        //Personaggio
        float pgW = 2;
        float pgH = 2;
        TestingRender characterRender = new TestingRender();
        characterRender.width = pgW;
        characterRender.height = pgH;
        AnimationSequence pgAnimation = AnimationSequence.build();
        GameObject character = createGameObject(characterRender,pgAnimation);
        character.x = -6;
        character.y = -5.5f;

        //Pulsante
        ButtonRenderComponent buttonRenderComponent = new ButtonRenderComponent();
        ButtonInputComponent buttonInputComponent = new ButtonInputComponent();
        buttonInputComponent.buttonRenderComponent = buttonRenderComponent;
        buttonRenderComponent.edge = 2;
        buttonRenderComponent.radius = 0.6f;
        buttonInputComponent.width = 2;
        buttonInputComponent.height = 2;
        buttonInputComponent.runnable = () -> move(bridgeAnimation, pgAnimation, 0);
        GameObject button = createGameObject(buttonRenderComponent, buttonInputComponent);
        button.y = 5;
    }


    public void move (AnimationSequence bridge, AnimationSequence character, float y) {
        float dest = y - 1.8f;
        bridge.add(MoveToAnimation.build(bridge.getOwner(),0,-7,1));
        bridge.start();

        character.add(WaitAnimation.build(1.5f));
        character.add(MoveToAnimation.build(character.getOwner(),6,-5.5f,1));
        character.start();

        //Tasto per riprovare
        DebugRenderer buttonRetrayRenderComponent = new DebugRenderer(6,3);
        Button buttonRetray = new Button(6,3);
        buttonRetray.setOnClick(this::retray);
        AnimationSequence buttonRetrayAnimation = AnimationSequence.build();
        GameObject retray = createGameObject(buttonRetrayRenderComponent,buttonRetray, buttonRetrayAnimation);
        retray.x = -4;
        retray.y = -21;
        buttonRetrayAnimation.add(WaitAnimation.build(3));
        buttonRetrayAnimation.add(MoveToAnimation.build(retray,-4,1,0.5f));
        buttonRetrayAnimation.add(MoveToAnimation.build(retray,-4,2,0.05f));
        buttonRetrayAnimation.add(MoveToAnimation.build(retray,-4,0,0.25f));
        buttonRetrayAnimation.start();

        //Tasto per avanzare
        DebugRenderer buttonNextRenderComponent = new DebugRenderer(6,3);
        Button buttonNext = new Button(6,3);
        buttonNext.setOnClick(this::nextLevel);
        AnimationSequence buttonNextAnimation = AnimationSequence.build();
        GameObject toMenu = createGameObject(buttonNextRenderComponent, buttonNext, buttonNextAnimation);
        toMenu.x = 4;
        toMenu.y = -21;
        buttonNextAnimation.add(WaitAnimation.build(3));
        buttonNextAnimation.add(MoveToAnimation.build(toMenu,4,1,0.5f));
        buttonNextAnimation.add(MoveToAnimation.build(toMenu,4,2,0.05f));
        buttonNextAnimation.add(MoveToAnimation.build(toMenu,4,0,0.25f));
        buttonNextAnimation.start();
    }

    public void nextLevel() {
        loadScene(Level2.class);
    }

    public void retray() {
        loadScene(Level1.class);
    }
}
