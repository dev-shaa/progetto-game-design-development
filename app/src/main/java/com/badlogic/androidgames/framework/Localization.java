package com.badlogic.androidgames.framework;

public interface Localization {

    String getString(int id);

    String getString(int id, Object... args);

}
