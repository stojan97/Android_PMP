package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.example.myapplication.infrastructure.FileService;
import com.example.myapplication.infrastructure.impl.FileServiceImpl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Dictionary application.
 * Provides globally accessible application state.
 * <p>
 * It won't cause memory leak because this class will be destroyed only
 * when the application is closed.
 */
public class DictionaryApplication extends Application {

    private static final String DICTIONARY_FILE_NAME = "dict.txt";

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        initDictionaryFile();
    }

    private static void initDictionaryFile() {
        FileService fileService = new FileServiceImpl();
        InputStream dictionaryInputStream = null;
        try {
            dictionaryInputStream = context.getAssets().open(DICTIONARY_FILE_NAME);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        fileService.writeInputStreamToFile(dictionaryInputStream, getDictionaryFilePath());
    }

    /**
     * Gets Dictionary file path from internal storage.
     *
     * @return the file path
     */
    public static Path getDictionaryFilePath() {
        String internalStoragePath = context.getFilesDir().getPath();
        return Paths.get(internalStoragePath, DICTIONARY_FILE_NAME);
    }

    /**
     * Gets the static Application context.
     *
     * @return the context
     */
    public static Context getContext() {
        return context;
    }

    /**
     * Gets the density from the Context display metrics.
     *
     * @return the density
     */
    public static float getApplicationDensity() {
        return context.getResources().getDisplayMetrics().density;
    }

}
