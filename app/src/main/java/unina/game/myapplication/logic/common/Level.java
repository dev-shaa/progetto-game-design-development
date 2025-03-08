package unina.game.myapplication.logic.common;

import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Music;
import com.badlogic.androidgames.framework.Pixmap;
import com.badlogic.androidgames.framework.Sound;

import java.io.IOException;

import unina.game.myapplication.core.Camera;
import unina.game.myapplication.core.GameObject;
import unina.game.myapplication.core.Scene;
import unina.game.myapplication.core.rendering.SpriteRenderer;
import unina.game.myapplication.logic.Assets;
import unina.game.myapplication.logic.menu.MainMenu;

/**
 * Base class for levels.
 */
public abstract class Level extends Scene {

    private static boolean ENABLE_MUSIC = true;

    private LevelSaver levelSaver;
    private Music backgroundMusic;
    private Button menuButton, musicButton;

    private boolean firstResume = true;

    public Level(Game game) {
        super(game);
    }

    @Override
    public void initialize() {
        super.initialize();
    }

    @Override
    public void resume() {
        super.resume();

        if (firstResume) {
            levelSaver = LevelSaver.getInstance(game.getFileIO());

            backgroundMusic = getMusic(getBackgroundMusic());
            backgroundMusic.setLooping(true);
            backgroundMusic.setVolume(ENABLE_MUSIC ? 0.5f : 0f);

            Pixmap uiPixmap = getImage("graphics/elements-ui.png");
            Sound uiButtonSound = getSound(Assets.SOUND_UI_BUTTON_CLICK);

            float size = 1.6f * Camera.getInstance().getSizeX() / 10;

            GameObject musicButtonGO = createGameObject(Camera.getInstance().getSizeX() / 2 - size, Camera.getInstance().getSizeY() / 2 - size / 1.5f);

            SpriteRenderer musicButtonRenderer = musicButtonGO.addComponent(SpriteRenderer.class);
            musicButtonRenderer.setImage(uiPixmap);
            musicButtonRenderer.setSrcSize(128, 128);
            musicButtonRenderer.setSrcPosition(ENABLE_MUSIC ? 256 : 256 + 128, 0);
            musicButtonRenderer.setSize(size, size);
            musicButtonRenderer.setLayer(128);

            musicButton = musicButtonGO.addComponent(Button.class);
            musicButton.setSize(size, size);
            musicButton.setOnClick(() -> {
                uiButtonSound.play(1);
                ENABLE_MUSIC = !ENABLE_MUSIC;
                backgroundMusic.setVolume(ENABLE_MUSIC ? 0.5f : 0f);
                musicButtonRenderer.setSrcPosition(ENABLE_MUSIC ? 256 : 256 + 128, 0);
            });

            GameObject menuButtonGO = createGameObject(-Camera.getInstance().getSizeX() / 2 + size, Camera.getInstance().getSizeY() / 2 - size / 1.5f);

            SpriteRenderer menuButtonRenderer = menuButtonGO.addComponent(SpriteRenderer.class);
            menuButtonRenderer.setImage(uiPixmap);
            menuButtonRenderer.setSrcPosition(0, 0);
            menuButtonRenderer.setSrcSize(128, 128);
            menuButtonRenderer.setSize(size, size);
            menuButtonRenderer.setLayer(128);

            menuButton = menuButtonGO.addComponent(Button.class);
            menuButton.setSize(size, size);
            menuButton.setOnClick(() -> {
                menuButton.setInteractable(false);
                musicButton.setInteractable(false);

                uiButtonSound.play(1);
                loadScene(MainMenu.class);
            });

            firstResume = false;
        }

        backgroundMusic.play();
    }

    @Override
    public void pause() {
        super.pause();
        backgroundMusic.pause();
    }

    @Override
    public void dispose() {
        super.dispose();
        backgroundMusic = null;
    }

    /**
     * Saves the level as completed.
     */
    protected final void saveProgress() {
        try {
            levelSaver.saveLevelAsCompleted(getLevelIndex());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Reloads this scene.
     */
    protected final void reloadLevel() {
        loadScene(getClass());
    }

    /**
     * Loads the next level.
     */
    protected final void loadNextLevel() {
        loadScene(levelSaver.getLevel(getLevelIndex() + 1));
    }

    /**
     * Returns the level index.
     *
     * @return level index
     */
    protected abstract int getLevelIndex();

    /**
     * Returns the path of the background music.
     *
     * @return background music path
     */
    protected abstract String getBackgroundMusic();

    protected final void setUIButtonsInteractable(boolean enabled) {
        menuButton.setInteractable(enabled);
        musicButton.setInteractable(enabled);
    }

}
