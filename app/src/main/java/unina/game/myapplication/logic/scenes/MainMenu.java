package unina.game.myapplication.logic.scenes;

import com.badlogic.androidgames.framework.Color;
import com.badlogic.androidgames.framework.Font;
import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Graphics;
import com.badlogic.androidgames.framework.Localization;
import com.badlogic.androidgames.framework.Music;
import com.badlogic.androidgames.framework.Pixmap;
import com.badlogic.androidgames.framework.Sound;

import unina.game.myapplication.R;
import unina.game.myapplication.core.Camera;
import unina.game.myapplication.core.GameObject;
import unina.game.myapplication.core.Scene;
import unina.game.myapplication.core.animations.AnimationSequence;
import unina.game.myapplication.core.animations.ParallelAnimation;
import unina.game.myapplication.core.rendering.SpriteRenderer;
import unina.game.myapplication.core.rendering.TextRenderer;
import unina.game.myapplication.logic.common.inputs.Button;
import unina.game.myapplication.logic.common.animations.ColorAnimation;
import unina.game.myapplication.logic.common.LevelSaver;
import unina.game.myapplication.core.rendering.RectRenderer;
import unina.game.myapplication.logic.common.animations.SoundFadeAnimation;
import unina.game.myapplication.logic.common.animations.SpriteRendererScaleAnimation;

public class MainMenu extends Scene {

    private static boolean firstTime = true;

    private Music backgroundMusic;

    public MainMenu(Game game) {
        super(game);
    }

    @Override
    public void initialize() {
        super.initialize();

        Pixmap menuBackgroundImage = getImage("graphics/environment-main-menu.jpg");
        Pixmap backgroundImage = getImage("graphics/environment-level-selection.png");
        Pixmap spritesImage = getImage("graphics/elements-ui.png");

        Sound selectSound = getSound("sounds/kenney-interface-sounds/click_002.ogg");

        Font playButtonFont = game.getGraphics().newFont("fonts/RG2014D.ttf");
        Font cabinFont = game.getGraphics().newFont("fonts/Cabin-Bold.ttf");

        backgroundMusic = getMusic("sounds/HappyLoops/intro.wav");
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(1);

        SpriteRenderer menuBackground = createGameObject(0, 0).addComponent(SpriteRenderer.class);
        menuBackground.setImage(menuBackgroundImage);
        menuBackground.setSrcPosition(0, 0);
        menuBackground.setSrcSize(412, 892);
        menuBackground.setSize(Camera.getInstance().getSizeX(), Camera.getInstance().getSizeY());
        menuBackground.setLayer(0);

        Localization localization = game.getLocalization();

        LevelSaver levelSaver = LevelSaver.getInstance(game.getFileIO());
        Button[] levelButtons = new Button[levelSaver.getLevelsCount()];

        RectRenderer fadeRenderer = createGameObject().addComponent(RectRenderer.class);
        fadeRenderer.setSize(100, 100);
        fadeRenderer.setColor(Color.TRANSPARENT);
        fadeRenderer.setLayer(Short.MAX_VALUE);

        AnimationSequence animator = createGameObject().addComponent(AnimationSequence.class);
        animator.add(ParallelAnimation.build(
                ColorAnimation.build(fadeRenderer::setColor, Color.BLACK, Color.TRANSPARENT, 0.5f),
                SoundFadeAnimation.build(backgroundMusic, 0, 1, 0.5f)
        ));
        animator.start();

        // Main menu
        GameObject playButtonGO = createGameObject(0, -5.5f);

        TextRenderer playButtonRenderer = playButtonGO.addComponent(TextRenderer.class);
        playButtonRenderer.setHorizontalAlign(Graphics.Align.CENTER);
        playButtonRenderer.setVerticalAlign(Graphics.Align.CENTER);
        playButtonRenderer.setColor(0xffECECE7);
        playButtonRenderer.setSize(32);
        playButtonRenderer.setFont(playButtonFont);
        playButtonRenderer.setText(localization.getString(R.string.menu_button_play));

        Button playButton = playButtonGO.addComponent(Button.class);
        playButton.setSize(8, 2);
        playButton.setOnClick(() -> {
            animator.clear();
            animator.add(ColorAnimation.build(fadeRenderer::setColor, Color.TRANSPARENT, Color.BLACK, 0.5f), () -> Camera.getInstance().getOwner().setTransform(30, 0, 0));
            animator.add(ColorAnimation.build(fadeRenderer::setColor, Color.BLACK, Color.TRANSPARENT, 0.5f), () -> {
                for (Button levelButton : levelButtons)
                    levelButton.setInteractable(true);
            });
            animator.start();
        });

        // Level selection
        setClearColor(0xff2B2B2C);

        SpriteRenderer levelSelectionBackgroundRenderer = createGameObject(30, 0).addComponent(SpriteRenderer.class);
        levelSelectionBackgroundRenderer.setImage(backgroundImage);
        levelSelectionBackgroundRenderer.setSrcPosition(0, 0);
        levelSelectionBackgroundRenderer.setSrcSize(412, 892);
        levelSelectionBackgroundRenderer.setSize(Camera.getInstance().getSizeX(), Camera.getInstance().getSizeY());
        levelSelectionBackgroundRenderer.setLayer(128);

        GameObject levelSelectionLabelGO = createGameObject(-3.5f + 30, 6.5f);
        TextRenderer levelSelectionLabel = levelSelectionLabelGO.addComponent(TextRenderer.class);
        levelSelectionLabel.setText(localization.getString(R.string.select_level));
        levelSelectionLabel.setSize(32);
        levelSelectionLabel.setFont(cabinFont);
        levelSelectionLabel.setHorizontalAlign(Graphics.Align.START);
        levelSelectionLabel.setColor(Color.WHITE);

        RectRenderer line = createGameObject(-3 + 30, 5).addComponent(RectRenderer.class);
        line.setSize(0.25f, Math.max(2 * levelSaver.getLevelsCount(), 12));
        line.setPivot(0.5f, 0f);
        line.setColor(0xffF9A900);

        AnimationSequence buttonsAnimator = createGameObject().addComponent(AnimationSequence.class);

        for (int i = 0; i < levelSaver.getLevelsCount(); i++) {
            final Class<? extends Scene> level = levelSaver.getLevel(i);

            TextRenderer label = createGameObject(-2f + 30, 4f - i * 2f).addComponent(TextRenderer.class);
            label.setSize(24);
            label.setColor(Color.WHITE);
            label.setLayer(2);
            label.setFont(cabinFont);
            label.setHorizontalAlign(Graphics.Align.START);
            label.setVerticalAlign(Graphics.Align.CENTER);
            label.setText(localization.getString(R.string.level, i + 1));

            GameObject buttonGameObject = createGameObject(30, 4f - i * 2f);

            Button button = buttonGameObject.addComponent(Button.class);
            button.setSize(7f, 1.5f);
            button.setInteractable(!firstTime);

            SpriteRenderer indicator = createGameObject(-3 + 30, 4 - i * 2f).addComponent(SpriteRenderer.class);
            indicator.setImage(spritesImage);
            indicator.setSize(2, 2);
            indicator.setSrcSize(128, 128);

            if (i <= levelSaver.getLatestCompletedLevel()) {
                indicator.setSrcPosition(128, 128);
            } else if (i == levelSaver.getLatestCompletedLevel() + 1) {
                indicator.setSrcPosition(0, 128);
                buttonsAnimator.add(SpriteRendererScaleAnimation.build(indicator, 2, 2, 2.15f, 2.15f, 0.5f));
                buttonsAnimator.start();
            } else {
                indicator.setSrcPosition(128, 0);
                button.setInteractable(false);
            }

            button.setOnClick(() -> {
                for (Button levelButton : levelButtons)
                    levelButton.interactable = false;

                selectSound.play(1);

                animator.clear();
                animator.add(ParallelAnimation.build(
                                ColorAnimation.build(fadeRenderer::setColor, Color.TRANSPARENT, Color.BLACK, 1.5f),
                                SoundFadeAnimation.build(backgroundMusic, 1, 0, 1.5f)
                        ),
                        () -> loadScene(level));
                animator.start();
            });

            levelButtons[i] = button;
        }

        // Skip the landing page if returning here from another scene
        if (firstTime) {
            Camera.getInstance().getOwner().setTransform(0, 0, 0);
            firstTime = false;
        } else {
            Camera.getInstance().getOwner().setTransform(30, 0, 0);
        }
    }

    @Override
    public void pause() {
        super.pause();
        backgroundMusic.pause();
    }

    @Override
    public void resume() {
        super.resume();
        backgroundMusic.play();
    }

}
