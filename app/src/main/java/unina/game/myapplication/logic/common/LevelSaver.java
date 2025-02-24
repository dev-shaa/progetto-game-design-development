package unina.game.myapplication.logic.common;

import com.badlogic.androidgames.framework.FileIO;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import unina.game.myapplication.Level1;
import unina.game.myapplication.Level2;
import unina.game.myapplication.Level3;
import unina.game.myapplication.Level4;
import unina.game.myapplication.core.Scene;

public class LevelSaver {

    private final FileIO fileIO;
    private final Class<? extends Scene>[] levels;
    private int latestCompletedLevel;

    private static LevelSaver instance;

    public static LevelSaver getInstance(FileIO fileIO) {
        if (instance == null)
            instance = new LevelSaver(fileIO);

        return instance;
    }

    private LevelSaver(FileIO fileIO) {
        levels = new Class[6];
        levels[0] = Level1.class;
        levels[1] = Level2.class;
        levels[2] = Level3.class;
        levels[3] = Level4.class;
        levels[4] = Level1.class;
        levels[5] = Level1.class;

        this.fileIO = fileIO;

        try (DataInputStream stream = new DataInputStream(fileIO.readFile("save.txt"))) {
            latestCompletedLevel = stream.readInt();
        } catch (IOException e) {
            latestCompletedLevel = -1;
        }
    }

    /**
     * Returns the numbers of level available.
     *
     * @return total numbers of levels
     */
    public int getLevelsCount() {
        return levels.length;
    }

    /**
     * Returns the level corresponding to the given index.
     *
     * @param index index of the level
     * @return class of the level scene
     */
    public Class<? extends Scene> getLevel(int index) {
        return levels[index];
    }

    /**
     * Returns the index of the latest completed level.
     *
     * @return the index of the latest completed level, {@code -1} if no levels were completed
     */
    public int getLatestCompletedLevel() {
        return latestCompletedLevel;
    }

    /**
     * Marks the given level as completed.
     *
     * @param index index of the level
     * @throws IOException if an error occurs during save
     */
    public void saveLevelAsCompleted(int index) throws IOException {
        if (index < latestCompletedLevel || index < 0 || index >= levels.length)
            return;

        try (DataOutputStream stream = new DataOutputStream(fileIO.writeFile("save.txt"))) {
            stream.writeInt(index);
            latestCompletedLevel = index;
        }
    }

    /**
     * Clears the save data and marks all levels as not completed.
     *
     * @throws IOException if an error occurs during clear
     */
    public void clearSave() throws IOException {
        try (DataOutputStream stream = new DataOutputStream(fileIO.writeFile("save.txt"))) {
            stream.writeInt(-1);
            latestCompletedLevel = -1;
        }
    }

}
