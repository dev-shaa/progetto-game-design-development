package unina.game.myapplication;

import android.os.Bundle;

import com.badlogic.androidgames.framework.Screen;
import com.badlogic.androidgames.framework.impl.AndroidGame;

public class MainActivity extends AndroidGame {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        System.loadLibrary("liquidfun");
        System.loadLibrary("liquidfun_jni");

        super.onCreate(savedInstanceState);
    }

    @Override
    public Screen getStartScreen() {
        return null;
    }

}