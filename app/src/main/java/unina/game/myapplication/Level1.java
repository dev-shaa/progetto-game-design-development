package unina.game.myapplication;

import com.badlogic.androidgames.framework.Color;
import com.badlogic.androidgames.framework.Game;

import unina.game.myapplication.core.GameObject;
import unina.game.myapplication.core.Scene;
import unina.game.myapplication.core.physics.BoxCollider;
import unina.game.myapplication.core.physics.RigidBody;
import unina.game.myapplication.logic.ButtonInputComponent;
import unina.game.myapplication.logic.ButtonRenderComponent;
import unina.game.myapplication.logic.PlatformRenderComponent;

public class Level1 extends Scene {
    public Level1(Game game) {
        super(game);
    }

    private boolean platformRotated = false;

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

        //Ponte elevatoio
        PlatformRenderComponent platformRenderComponent3 = new PlatformRenderComponent();
        platformRenderComponent3.color = Color.GOLD;
        platformRenderComponent3.height = 3.5f;
        platformRenderComponent3.width = 0.7f;
        RigidBody rigidBridge = RigidBody.build(RigidBody.Type.KINEMATIC, BoxCollider.build(5,2));
        GameObject bridge = createGameObject(platformRenderComponent3,rigidBridge);
        bridge.x = -1.35f;
        bridge.y = -4.55f;

        //Pulsante
        ButtonRenderComponent buttonRenderComponent = new ButtonRenderComponent();
        ButtonInputComponent buttonInputComponent = new ButtonInputComponent();
        buttonInputComponent.buttonRenderComponent = buttonRenderComponent;
        buttonRenderComponent.edge = 1;
        buttonRenderComponent.radius = 0.3f;
        buttonInputComponent.size = 1;
        buttonInputComponent.runnable = () -> rotate(rigidBridge, -1.35f, -4.55f, 90, 3.5f, 0.7f);
        GameObject button = createGameObject(buttonRenderComponent,buttonInputComponent);
        button.y = 5;
    }



    public void rotate(RigidBody body, float x, float y, float angle, float width, float height) {
        if (platformRotated)
            body.setTransform(x - height / 2, y + width / 2,0);
        else
            body.setTransform(x + height / 2, y - width / 2,angle);
        platformRotated = !platformRotated;
    }
}
