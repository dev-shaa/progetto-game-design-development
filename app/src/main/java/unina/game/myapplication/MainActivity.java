package unina.game.myapplication;

import android.os.Bundle;

import com.badlogic.androidgames.framework.Screen;
import com.badlogic.androidgames.framework.impl.AndroidGame;

import unina.game.myapplication.logic.scenes.MainMenu;

public class MainActivity extends AndroidGame {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        System.loadLibrary("liquidfun");
        System.loadLibrary("liquidfun_jni");

        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getWidth() {
        return 892;
    }

    @Override
    protected int getHeight() {
        return 412;
    }

    @Override
    public Screen getStartScreen() {
        return new MainMenu(this);
    }

}