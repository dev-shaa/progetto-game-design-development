package unina.game.myapplication.logic.common;

import com.badlogic.androidgames.framework.Game;

import java.io.IOException;

import unina.game.myapplication.core.Scene;

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

    protected final void reloadLevel() {
        loadScene(getClass());
    }

    protected final void saveProgress() {
        try {
            levelSaver.saveLevelAsCompleted(getLevelIndex());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected final void loadNextLevel() {
        loadScene(levelSaver.getLevel(getLevelIndex() + 1));
    }

    protected abstract int getLevelIndex();

}
