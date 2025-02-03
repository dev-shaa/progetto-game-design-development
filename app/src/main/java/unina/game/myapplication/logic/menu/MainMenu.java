package unina.game.myapplication.logic.menu;

import com.badlogic.androidgames.framework.Color;
import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Sound;

import unina.game.myapplication.Level1;
import unina.game.myapplication.core.Camera;
import unina.game.myapplication.core.GameObject;
import unina.game.myapplication.core.Scene;
import unina.game.myapplication.core.animations.MoveToAnimation;
import unina.game.myapplication.logic.ColorChangeAnimation;
import unina.game.myapplication.logic.DebugRenderer;
import unina.game.myapplication.core.animations.Animator;
import unina.game.myapplication.logic.FullScreenColorRenderer;

public class MainMenu extends Scene {

    private Sound sound;
    private Button selectLevelButton;
    private Button levelButtons;

    public MainMenu(Game game) {
        super(game);
    }

    @Override
    public void initialize() {
        super.initialize();

        DebugRenderer levelButtonRenderer = new DebugRenderer(2, 1);
        levelButtons = new Button(2, 1);
        levelButtons.interactable = false;
        levelButtons.setOnClick(() -> {
            Animator animator = new Animator();
            FullScreenColorRenderer fullScreenColorRenderer = new FullScreenColorRenderer();
            createGameObject(fullScreenColorRenderer);

            animator.addAnimation(
                    new ColorChangeAnimation(Color.TRANSPARENT, Color.BLACK, 0.333f, fullScreenColorRenderer::setColor),
                    () -> loadScene(Level1.class));

            createGameObject(animator);
        });
        GameObject go = createGameObject(levelButtons, levelButtonRenderer);
        go.x = 2;
        go.y = -10;


        DebugRenderer selectLevelButtonRenderer = new DebugRenderer(2, 1);
        selectLevelButton = new Button(2, 1);
        selectLevelButton.interactable = true;
        createGameObject(selectLevelButton, selectLevelButtonRenderer);

        selectLevelButton.setOnClick(() -> {
            selectLevelButton.interactable = false;

            Animator animator = new Animator();
            animator.addAnimation(
                    MoveToAnimation.build(Camera.getInstance().getOwner(), go.x, go.y, 0.75f, MoveToAnimation.EaseFunction.CUBIC_IN_OUT),
                    () -> levelButtons.interactable = true);

            createGameObject(animator);
        });
    }

    @Override
    public void dispose() {
        super.dispose();
    }

}
