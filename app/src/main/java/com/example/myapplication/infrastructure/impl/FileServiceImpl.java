package com.example.myapplication.infrastructure.impl;

import android.util.Log;

import com.example.myapplication.infrastructure.FileService;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * File Service implementation.
 */
public class FileServiceImpl implements FileService {

    private static final String TAG = "FileServiceImpl";

    @Override
    public void writeLinesToFile(Path filePath, List<String> lines) {
        try {
            Files.write(filePath, lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            Log.e(TAG, Objects.requireNonNull(e.getMessage()));
        }
    }

    @Override
    public List<String> readLinesFromFile(Path filePath) {
        List<String> lines = new ArrayList<>();
        try {
            lines = Files.readAllLines(filePath, StandardCharsets.UTF_8);
        } catch (IOException e) {
            Log.e(TAG, Objects.requireNonNull(e.getMessage()));
        }

        return lines;
    }

    @Override
    public void writeInputStreamToFile(InputStream inputStream, Path targetPath) {
        try {
            Files.copy(inputStream, targetPath);
        } catch (IOException e) {
            Log.e(TAG, Objects.requireNonNull(e.getMessage()));
        }
    }
}
