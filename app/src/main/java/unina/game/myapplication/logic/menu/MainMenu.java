package unina.game.myapplication.logic.menu;

import com.badlogic.androidgames.framework.Color;
import com.badlogic.androidgames.framework.Game;

import unina.game.myapplication.TestingScene;
import unina.game.myapplication.core.GameObject;
import unina.game.myapplication.core.Scene;
import unina.game.myapplication.core.ui.Button;
import unina.game.myapplication.core.ui.Canvas;
import unina.game.myapplication.core.ui.CanvasRenderer;
import unina.game.myapplication.core.ui.Element;

public class MainMenu extends Scene {

    public MainMenu(Game game) {
        super(game);
    }

    @Override
    public void initialize() {
        super.initialize();

        Button selectLevelButton = Button.build();
        selectLevelButton.setAlignment(Element.Alignment.CENTER, Element.Alignment.CENTER);
        selectLevelButton.setPosition(0, 50);
        selectLevelButton.setSize(150, 50);
        selectLevelButton.setColor(Color.RED);
        selectLevelButton.setOnClick(() -> loadScene(new TestingScene(game)));

        Button helpButton = Button.build();
        helpButton.setAlignment(Element.Alignment.CENTER, Element.Alignment.CENTER);
        helpButton.setPosition(0, -50);
        helpButton.setSize(150, 50);

        Canvas canvas = Canvas.build();
        CanvasRenderer canvasRenderer = CanvasRenderer.build(canvas);
        GameObject canvasGameObject = GameObject.create(canvas, canvasRenderer);

        canvas.addElement(selectLevelButton);
        canvas.addElement(helpButton);

        addGameObject(canvasGameObject);
    }

}
