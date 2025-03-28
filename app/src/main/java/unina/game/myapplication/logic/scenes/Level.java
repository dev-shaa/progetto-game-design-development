package unina.game.myapplication.logic.scenes;

import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Music;
import com.badlogic.androidgames.framework.Pixmap;
import com.badlogic.androidgames.framework.Sound;

import java.io.IOException;

import unina.game.myapplication.core.Camera;
import unina.game.myapplication.core.GameObject;
import unina.game.myapplication.core.Scene;
import unina.game.myapplication.core.rendering.SpriteRenderer;
import unina.game.myapplication.logic.common.Assets;
import unina.game.myapplication.logic.common.LevelSaver;
import unina.game.myapplication.logic.common.inputs.Button;

/**
 * Base class for levels.
 */
public abstract class Level extends Scene {

    private LevelSaver levelSaver;
    private Music backgroundMusic;
    private Button menuButton, musicButton;

    public Level(Game game) {
        super(game);
    }

    @Override
    public final void initialize() {
        super.initialize();

        // Let the level initialize everything first
        onInitialize();

        levelSaver = LevelSaver.getInstance(game.getFileIO());

        backgroundMusic = getMusic(Assets.SOUND_MUSIC_LEVELS);
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(levelSaver.isMusicEnabled() ? 0.5f : 0f);

        final Pixmap uiPixmap = getImage(Assets.GRAPHICS_UI_SPRITES);
        final Sound uiButtonSound = getSound(Assets.SOUND_UI_BUTTON_CLICK);

        // NOTE:
        // we always place the buttons at the top and let have the same size regardless of camera size
        // we don't move the camera so it's okay
        float size = 1.6f * Camera.getInstance().getSizeX() / 10;

        GameObject musicButtonGO = createGameObject(Camera.getInstance().getSizeX() / 2 - size, Camera.getInstance().getSizeY() / 2 - size / 1.5f);

        SpriteRenderer musicButtonRenderer = musicButtonGO.addComponent(SpriteRenderer.class);
        musicButtonRenderer.setImage(uiPixmap);
        musicButtonRenderer.setSrcSize(128, 128);
        musicButtonRenderer.setSrcPosition(levelSaver.isMusicEnabled() ? 256 : 256 + 128, 0);
        musicButtonRenderer.setSize(size, size);
        musicButtonRenderer.setLayer(128);

        musicButton = musicButtonGO.addComponent(Button.class);
        musicButton.setSize(size, size);
        musicButton.setOnClick(() -> {
            uiButtonSound.play(1);

            saveMusicToggle(!levelSaver.isMusicEnabled());
            backgroundMusic.setVolume(levelSaver.isMusicEnabled() ? 0.5f : 0f);
            musicButtonRenderer.setSrcPosition(levelSaver.isMusicEnabled() ? 256 : 256 + 128, 0);
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
    }

    @Override
    public void resume() {
        super.resume();
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
     * Initializes the level.
     */
    protected abstract void onInitialize();

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
        if (getLevelIndex() >= levelSaver.getLevelsCount() - 1)
            loadScene(MainMenu.class);
        else
            loadScene(levelSaver.getLevel(getLevelIndex() + 1));
    }

    private void saveMusicToggle(boolean status) {
        try {
            levelSaver.saveMusicToggle(status);
        } catch (IOException e) {
            // It is a rare eventuality and it is not worth it to do something
            // Worst case scenario the user has to disable music again next time
        }
    }

    /**
     * Returns the level index.
     *
     * @return level index
     */
    protected abstract int getLevelIndex();

    /**
     * Enables or disables the UI buttons.
     *
     * @param enabled status of the buttons
     */
    protected final void setUIButtonsInteractable(boolean enabled) {
        menuButton.setInteractable(enabled);
        musicButton.setInteractable(enabled);
    }

}
