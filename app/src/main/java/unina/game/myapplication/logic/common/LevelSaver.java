package unina.game.myapplication.logic.common;

import com.badlogic.androidgames.framework.FileIO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import unina.game.myapplication.Level1;
import unina.game.myapplication.Level2;
import unina.game.myapplication.Level3;
import unina.game.myapplication.Level4;
import unina.game.myapplication.core.Scene;

public class LevelSaver {

    private final FileIO fileIO;
    private final Class<? extends Scene>[] levels;
    private int latestCompletedLevel = 0;

    public LevelSaver(FileIO fileIO) {
        levels = new Class[4];
        levels[0] = Level1.class;
        levels[1] = Level2.class;
        levels[2] = Level3.class;
        levels[3] = Level4.class;

        this.fileIO = fileIO;

        try (InputStream stream = fileIO.readFile("save.txt")) {
            latestCompletedLevel = stream.read();
        } catch (FileNotFoundException e) {
            latestCompletedLevel = 2;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int getLevelsCount() {
        return levels.length;
    }

    public Class<? extends Scene> getLevel(int index) {
        return levels[index];
    }

    public int getLatestCompletedLevel() {
        return latestCompletedLevel;
    }

    public void markAsCompleted(int index) throws IOException {
        if (index < 0 || index >= levels.length)
            return;

        try (OutputStream stream = fileIO.writeFile("save.txt")) {
            stream.write(index);
        }
    }

}
