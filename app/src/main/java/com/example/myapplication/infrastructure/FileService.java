package com.example.myapplication.infrastructure;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;

/**
 * File service that provides read/write functions for the Android file system.
 */
public interface FileService {

    /**
     * Replaces all the lines in the file at filePath, with the given lines.
     *
     * @param filePath the file path
     * @param lines the lines
     */
    void writeLinesToFile(Path filePath, List<String> lines);

    /**
     * Reads all the lines from the file at filePath.
     *
     * @param filePath the file path
     * @return the lines
     */
    List<String> readLinesFromFile(Path filePath);

    /**
     * Writes all the bytes from input stream to the file at targetPath.
     *
     * @param inputStream the input stream of bytes
     * @param targetPath the file target path
     */
    void writeInputStreamToFile(InputStream inputStream, Path targetPath);
}
