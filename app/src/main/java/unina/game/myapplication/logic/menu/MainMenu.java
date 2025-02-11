package unina.game.myapplication.logic.menu;

import com.badlogic.androidgames.framework.Color;
import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Sound;

import unina.game.myapplication.core.Camera;
import unina.game.myapplication.core.GameObject;
import unina.game.myapplication.core.Scene;
import unina.game.myapplication.core.animations.AnimationSequence;
import unina.game.myapplication.core.animations.EaseFunction;
import unina.game.myapplication.core.animations.MoveToAnimation;
import unina.game.myapplication.logic.DebugRenderer;
import unina.game.myapplication.logic.common.Button;
import unina.game.myapplication.logic.common.FadeAnimation;
import unina.game.myapplication.logic.common.FullScreenColorRenderer;
import unina.game.myapplication.logic.common.LevelSaver;

public class MainMenu extends Scene {

    private static boolean firstTime = true;

    private Sound selectSound;
    private LevelSaver levelSaver;

    public MainMenu(Game game) {
        super(game);
    }

    @Override
    public void initialize() {
        super.initialize();

        levelSaver = new LevelSaver(game.getFileIO());
        //selectSound = game.getAudio().newSound("sounds/select_001.ogg");

        Button[] levelButtons = new Button[levelSaver.getLevelsCount()];

        FullScreenColorRenderer fadeRenderer = FullScreenColorRenderer.build();
        fadeRenderer.setColor(Color.TRANSPARENT);
        fadeRenderer.setLayer(Short.MAX_VALUE);
        createGameObject(fadeRenderer);

        AnimationSequence sequence = AnimationSequence.build();
        createGameObject(sequence);

        DebugRenderer selectLevelButtonRenderer = new DebugRenderer(2, 1);
        Button selectLevelButton = new Button(2, 1);
        createGameObject(selectLevelButton, selectLevelButtonRenderer);

        selectLevelButton.setOnClick(() -> {
//            selectSound.play(1);

            sequence.clear();
            sequence.add(MoveToAnimation.build(Camera.getInstance().getOwner(), 0, -20, 1f, EaseFunction.CUBIC_IN_OUT));
            sequence.start();
        });

        // Skip the landing page if returning here from another scene
        if (firstTime) {
            Camera.getInstance().getOwner().setTransform(0, 0, 0);
            firstTime = false;
        } else {
            Camera.getInstance().getOwner().setTransform(0, -20, 0);
        }

        for (int i = 0; i < levelSaver.getLevelsCount(); i++) {
            final Class<? extends Scene> level = levelSaver.getLevel(i);

            DebugRenderer buttonRenderer = new DebugRenderer(2, 1);
            Button button = new Button(2, 1);
            GameObject buttonGameObject = createGameObject(button, buttonRenderer);
            buttonGameObject.y = -20 - i * 1.5f;

            if (i <= levelSaver.getLatestCompletedLevel()) {
                button.interactable = true;
                buttonRenderer.color = Color.BLUE;
            } else {
                button.interactable = false;
                buttonRenderer.color = Color.GREY;
            }

            button.setOnClick(() -> {
//                selectSound.play(1);

                for (Button levelButton : levelButtons)
                    levelButton.interactable = false;

                sequence.clear();
                sequence.add(FadeAnimation.build(fadeRenderer, Color.TRANSPARENT, Color.BLACK, 1f), () -> loadScene(level));
                sequence.start();
            });

            levelButtons[i] = button;
        }
    }

    @Override
    public void dispose() {
        super.dispose();

        //selectSound.dispose();
    }

}
