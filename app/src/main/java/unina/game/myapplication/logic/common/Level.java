package unina.game.myapplication.logic.common;

import com.badlogic.androidgames.framework.Game;

import java.io.IOException;

import unina.game.myapplication.core.Scene;

/**
 * Base class for levels.
 */
public abstract class Level extends Scene {

    private LevelSaver levelSaver;

    public Level(Game game) {
        super(game);
    }

    @Override
    public void initialize() {
        super.initialize();
        levelSaver = LevelSaver.getInstance(game.getFileIO());
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

}
