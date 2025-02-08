package com.badlogic.androidgames.framework;

public interface Game {

    Input getInput();

    FileIO getFileIO();

    Graphics getGraphics();

    Audio getAudio();

    Localization getLocalization();

    void setScreen(Screen screen);

    Screen getCurrentScreen();

    Screen getStartScreen();

}