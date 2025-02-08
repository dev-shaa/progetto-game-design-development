package com.badlogic.androidgames.framework.impl;

import android.content.Context;

import com.badlogic.androidgames.framework.Localization;

public class AndroidLocalization implements Localization {

    private final Context context;

    public AndroidLocalization(Context context) {
        this.context = context;
    }

    @Override
    public String getString(int id) {
        return context.getString(id);
    }

    @Override
    public String getString(int id, Object... args) {
        return context.getString(id, args);
    }

}
