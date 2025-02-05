package com.badlogic.androidgames.framework.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;

import com.badlogic.androidgames.framework.FileIO;

public class AndroidFileIO implements FileIO {

    private final AssetManager assets;
    private final String externalStoragePath;
    private final String persistentDataPath;

    public AndroidFileIO(AssetManager assets, Context context) {
        this.assets = assets;
        this.externalStoragePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
        this.persistentDataPath = context.getExternalFilesDir("data").getAbsolutePath();
    }

    @Override
    public InputStream readAsset(String fileName) throws IOException {
        return assets.open(fileName);
    }

    @Override
    public InputStream readFile(String fileName) throws IOException {
        return new FileInputStream(persistentDataPath + File.separator + fileName);
    }

    @Override
    public OutputStream writeFile(String fileName) throws IOException {
        return new FileOutputStream(persistentDataPath + File.separator + fileName);
    }

    @Override
    public String getPersistentDataPath() {
        return persistentDataPath;
    }

}
